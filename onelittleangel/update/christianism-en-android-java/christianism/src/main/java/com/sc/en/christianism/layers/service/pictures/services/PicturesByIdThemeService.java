package com.sc.en.christianism.layers.service.pictures.services;

import android.util.SparseArray;

import com.sc.en.christianism.OnelittleAngelApplication;
import com.sc.en.christianism.layers.dao.pictures.PicturesDaoInterface;
import com.sc.en.christianism.layers.service.pictures.interfaces.PicturesByIdThemeServiceInterface;
import com.sc.en.christianism.layers.service.MotherBusinessService;
import com.sc.en.christianism.transverse.eventbus.events.pictures.PicturesByIdThemeLoadedEvent;
import com.sc.en.christianism.transverse.eventbus.models.PictureEventBus;
import com.sc.en.christianism.transverse.orms.realm.models.Picture;
import com.sc.en.christianism.injector.Injector;
import com.sc.en.christianism.layers.service.ServiceManagerInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class PicturesByIdThemeService extends MotherBusinessService implements PicturesByIdThemeServiceInterface {

  private static final String TAG = "PicutresByIdAuthorService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Picture>> picturesByIdThemeList = null;

  /**
   *
   */
  private PicturesByIdThemeLoadedEvent picturesByIdThemeLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public PicturesByIdThemeService(ServiceManagerInterface srvManager) {
    super(srvManager);
    picturesByIdThemeList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param idTheme
   */
  @Override
  public void loadPicturesByIdThemeAsync(int idTheme) {
    //  Log.e(TAG, "loadPictureByIdThemeAsync() called with: " + "cityId = [" + idTheme + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (picturesByIdThemeList.get(idTheme)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postPicturesByIdThemeDataLoadedEvent(picturesByIdThemeList.get(idTheme),idTheme);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoPicturesByIdThemeLoadRunnable(idTheme));
    }
  }

  private void loadPictureByIdThemeSync(int idTheme){
    // Log.e(TAG, "loadPictureByIdThemeSync() called with: " + "idTheme = [" + idTheme + "]");
    // Load data from database
    /*

   */
    PicturesDaoInterface picturesDaoInterface = Injector.getDaoManager().getPicturesDao();
    List<Picture> picturesByIdTheme = picturesDaoInterface.findPicturesByIdTheme(idTheme);
    picturesDaoInterface = null;
    //update your own cache
    picturesByIdThemeList.put(idTheme,picturesByIdTheme);
    //send send back the answer using eventBus
    postPicturesByIdThemeDataLoadedEvent(picturesByIdTheme,idTheme);
  }

  /**
   * @picture Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoPicturesByIdThemeLoadRunnable implements Runnable {
    final int idTheme;

    public DaoPicturesByIdThemeLoadRunnable(int idTheme) {
      this.idTheme = idTheme;
    }

    @Override
    public void run() {
      loadPictureByIdThemeSync(idTheme);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postPicturesByIdThemeDataLoadedEvent(List<Picture> picturesByIdTheme,int idTheme) {
    //  Log.e(TAG, "postPictureByIdThemeDataLoadedEvent() called as found Picture with " +
    //    pictureByIdThemeLoadedEvent.getPictureByIdTheme().getQuotes().size() + " elements and idTheme="+idTheme);
    List<PictureEventBus> pictures = new ArrayList<>();

    for(Picture p : picturesByIdTheme){
      pictures.add(new PictureEventBus(p));
    }

    if(picturesByIdThemeLoadedEvent==null){
      picturesByIdThemeLoadedEvent= new PicturesByIdThemeLoadedEvent(pictures,idTheme);
    }else{
      picturesByIdThemeLoadedEvent.setPicturesByIdTheme(pictures);
      picturesByIdThemeLoadedEvent.setIdTheme(idTheme);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + pictureByIdThemeLoadedEvent.getPictureByIdTheme() + " elements and idTheme ="+pictureByIdThemeLoadedEvent.getIdTheme());
    EventBus.getDefault().post(picturesByIdThemeLoadedEvent);
  }

}
