package com.sc.en.quotes.layers.service.pictures.services;

import android.util.SparseArray;

import com.sc.en.quotes.layers.dao.pictures.PicturesDaoInterface;
import com.sc.en.quotes.layers.service.MotherBusinessService;
import com.sc.en.quotes.layers.service.pictures.interfaces.PicturesByIdAuthorServiceInterface;
import com.sc.en.quotes.transverse.orms.realm.models.Picture;
import com.sc.en.quotes.injector.Injector;
import com.sc.en.quotes.OnelittleAngelApplication;
import com.sc.en.quotes.layers.service.ServiceManagerInterface;
import com.sc.en.quotes.transverse.eventbus.events.pictures.PicturesByIdAuthorLoadedEvent;
import com.sc.en.quotes.transverse.eventbus.models.PictureEventBus;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class PicturesByIdAuthorService extends MotherBusinessService implements PicturesByIdAuthorServiceInterface {

  private static final String TAG = "PicutresByIdAuthorService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Picture>> picturesByIdAuthorList = null;

  /**
   *
   */
  private PicturesByIdAuthorLoadedEvent picturesByIdAuthorLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public PicturesByIdAuthorService(ServiceManagerInterface srvManager) {
    super(srvManager);
    picturesByIdAuthorList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param idAuthor
   */
  @Override
  public void loadPicturesByIdAuthorAsync(int idAuthor) {
    //  Log.e(TAG, "loadPicturesByIdAuthorAsync() called with: " + "cityId = [" + idAuthor + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (picturesByIdAuthorList.get(idAuthor)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postPicturesByIdAuthorDataLoadedEvent(picturesByIdAuthorList.get(idAuthor),idAuthor);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoPicturesByIdAuthorLoadRunnable(idAuthor));
    }
  }

  private void loadPicturesByIdAuthorSync(int idAuthor){
    // Log.e(TAG, "loadPicturesByIdAuthorSync() called with: " + "idAuthor = [" + idAuthor + "]");
    // Load data from database
    /*

   */
    PicturesDaoInterface picturesDaoInterface = Injector.getDaoManager().getPicturesDao();
    List<Picture> picturesByIdAuthor = picturesDaoInterface.findPicturesByIdAuthor(idAuthor);
    picturesDaoInterface = null;
    //update your own cache
    picturesByIdAuthorList.put(idAuthor,picturesByIdAuthor);
    //send send back the answer using eventBus
    postPicturesByIdAuthorDataLoadedEvent(picturesByIdAuthor,idAuthor);
  }

  /**
   * @picture Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoPicturesByIdAuthorLoadRunnable implements Runnable {
    final int idAuthor;

    public DaoPicturesByIdAuthorLoadRunnable(int idAuthor) {
      this.idAuthor = idAuthor;
    }

    @Override
    public void run() {
      loadPicturesByIdAuthorSync(idAuthor);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postPicturesByIdAuthorDataLoadedEvent(List<Picture> picturesByIdAuthor,int idAuthor) {
    //  Log.e(TAG, "postAuthorByIdAuthorDataLoadedEvent() called as found Author with " +
    //    pictureByIdAuthorLoadedEvent.getAuthorByIdAuthor().getQuotes().size() + " elements and idAuthor="+idAuthor);
    List<PictureEventBus> pictures = new ArrayList<>();

    for(Picture p : picturesByIdAuthor){
      pictures.add(new PictureEventBus(p));
    }

    if(picturesByIdAuthorLoadedEvent==null){
      picturesByIdAuthorLoadedEvent= new PicturesByIdAuthorLoadedEvent(pictures, idAuthor);
    }else{
      picturesByIdAuthorLoadedEvent.setPicturesByIdAuthor(pictures);
      picturesByIdAuthorLoadedEvent.setIdAuthor(idAuthor);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + pictureByIdAuthorLoadedEvent.getAuthorByIdAuthor() + " elements and idAuthor ="+pictureByIdAuthorLoadedEvent.getIdAuthor());
    EventBus.getDefault().post(picturesByIdAuthorLoadedEvent);
  }

}
