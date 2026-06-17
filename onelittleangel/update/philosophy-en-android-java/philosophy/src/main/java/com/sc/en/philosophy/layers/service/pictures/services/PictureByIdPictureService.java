package com.sc.en.philosophy.layers.service.pictures.services;

import android.util.SparseArray;

import com.sc.en.philosophy.OnelittleAngelApplication;
import com.sc.en.philosophy.layers.dao.pictures.PicturesDaoInterface;
import com.sc.en.philosophy.layers.service.MotherBusinessService;
import com.sc.en.philosophy.layers.service.ServiceManagerInterface;
import com.sc.en.philosophy.layers.service.pictures.interfaces.PictureByIdPictureServiceInterface;
import com.sc.en.philosophy.transverse.eventbus.events.pictures.PictureByIdPictureLoadedEvent;
import com.sc.en.philosophy.transverse.eventbus.models.PictureEventBus;
import com.sc.en.philosophy.transverse.orms.realm.models.Picture;
import com.sc.en.philosophy.injector.Injector;

import org.greenrobot.eventbus.EventBus;

public class PictureByIdPictureService extends MotherBusinessService implements PictureByIdPictureServiceInterface {

  private static final String TAG = "PictureByIdPictureService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Picture> picturesByIdPictureList = null;

  /**
   *
   */
  private PictureByIdPictureLoadedEvent pictureByIdPictureLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public PictureByIdPictureService(ServiceManagerInterface srvManager) {
    super(srvManager);
    picturesByIdPictureList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param idPicture
   */
  @Override
  public void loadPictureByIdPictureAsync(int idPicture) {
    //  Log.e(TAG, "loadPictureByIdPictureAsync() called with: " + "cityId = [" + idPicture + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (picturesByIdPictureList.get(idPicture)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postPictureByIdPictureDataLoadedEvent(picturesByIdPictureList.get(idPicture),idPicture);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new PictureByIdPictureService.DaoPictureByIdPictureLoadRunnable(idPicture));
    }
  }

  private void loadPictureByIdPictureSync(int idPicture){
    // Log.e(TAG, "loadPictureByIdPictureSync() called with: " + "idPicture = [" + idPicture + "]");
    // Load data from database
    /*

   */
    PicturesDaoInterface picturesDaoInterface = Injector.getDaoManager().getPicturesDao();
    Picture pictureByIdPicture = picturesDaoInterface.findPictureByIdPicture(idPicture);
    picturesDaoInterface = null;
    //update your own cache
    picturesByIdPictureList.put(idPicture,pictureByIdPicture);
    //send send back the answer using eventBus
    postPictureByIdPictureDataLoadedEvent(pictureByIdPicture,idPicture);
  }

  /**
   * @picture Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoPictureByIdPictureLoadRunnable implements Runnable {
    final int idPicture;

    public DaoPictureByIdPictureLoadRunnable(int idPicture) {
      this.idPicture = idPicture;
    }

    @Override
    public void run() {
      loadPictureByIdPictureSync(idPicture);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postPictureByIdPictureDataLoadedEvent(Picture pictureByIdPicture,int idPicture) {
    //  Log.e(TAG, "postPictureByIdPictureDataLoadedEvent() called as found Picture with " +
    //    pictureByIdPictureLoadedEvent.getPictureByIdPicture().getQuotes().size() + " elements and idPicture="+idPicture);
    PictureEventBus picture = new PictureEventBus(pictureByIdPicture);

    if(pictureByIdPictureLoadedEvent==null){
      pictureByIdPictureLoadedEvent= new PictureByIdPictureLoadedEvent(picture, idPicture);
    }else{
      pictureByIdPictureLoadedEvent.setPictureByIdPicture(picture);
      pictureByIdPictureLoadedEvent.setIdPicture(idPicture);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + pictureByIdPictureLoadedEvent.getPictureByIdPicture() + " elements and idPicture ="+pictureByIdPictureLoadedEvent.getIdPicture());
    EventBus.getDefault().post(pictureByIdPictureLoadedEvent);
  }

}
