package com.sc.en.confucianism.layers.service.pictures.services;

import android.util.SparseArray;

import com.sc.en.confucianism.OnelittleAngelApplication;
import com.sc.en.confucianism.transverse.orms.realm.models.Picture;
import com.sc.en.confucianism.layers.service.MotherBusinessService;
import com.sc.en.confucianism.transverse.eventbus.events.pictures.PicturesByIdMovementLoadedEvent;
import com.sc.en.confucianism.transverse.eventbus.models.PictureEventBus;
import com.sc.en.confucianism.injector.Injector;
import com.sc.en.confucianism.layers.dao.pictures.PicturesDaoInterface;
import com.sc.en.confucianism.layers.service.pictures.interfaces.PicturesByIdMovementServiceInterface;
import com.sc.en.confucianism.layers.service.ServiceManagerInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class PicturesByIdMovementService extends MotherBusinessService implements PicturesByIdMovementServiceInterface {

  private static final String TAG = "PicutresByIdMovementService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Picture>> picturesByIdMovementList = null;

  /**
   *
   */
  private PicturesByIdMovementLoadedEvent picturesByIdMovementLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public PicturesByIdMovementService(ServiceManagerInterface srvManager) {
    super(srvManager);
    picturesByIdMovementList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param idMovement
   */
  @Override
  public void loadPicturesByIdMovementAsync(int idMovement) {
    //  Log.e(TAG, "loadPicturesByIdMovementAsync() called with: " + "cityId = [" + idMovement + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (picturesByIdMovementList.get(idMovement)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postPicturesByIdMovementDataLoadedEvent(picturesByIdMovementList.get(idMovement),idMovement);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoPicturesByIdMovementLoadRunnable(idMovement));
    }
  }

  private void loadPicturesByIdMovementSync(int idMovement){
    // Log.e(TAG, "loadPicturesByIdMovementSync() called with: " + "idMovement = [" + idMovement + "]");
    // Load data from database
    /*

   */
    PicturesDaoInterface picturesDaoInterface = Injector.getDaoManager().getPicturesDao();
    List<Picture> picturesByIdMovement = picturesDaoInterface.findPicturesByIdMovement(idMovement);
    picturesDaoInterface = null;
    //update your own cache
    picturesByIdMovementList.put(idMovement,picturesByIdMovement);
    //send send back the answer using eventBus
    postPicturesByIdMovementDataLoadedEvent(picturesByIdMovement,idMovement);
  }

  /**
   * @picture Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoPicturesByIdMovementLoadRunnable implements Runnable {
    final int idMovement;

    public DaoPicturesByIdMovementLoadRunnable(int idMovement) {
      this.idMovement = idMovement;
    }

    @Override
    public void run() {
      loadPicturesByIdMovementSync(idMovement);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postPicturesByIdMovementDataLoadedEvent(List<Picture> picturesByIdMovement,int idMovement) {
    //  Log.e(TAG, "postPictureByIdMovementDataLoadedEvent() called as found Picture with " +
    //    pictureByIdMovementLoadedEvent.getPictureByIdMovement().getQuotes().size() + " elements and idMovement="+idMovement);
    List<PictureEventBus> pictures = new ArrayList<>();

    for(Picture p : picturesByIdMovement){
      pictures.add(new PictureEventBus(p));
    }

    if(picturesByIdMovementLoadedEvent==null){
      picturesByIdMovementLoadedEvent= new PicturesByIdMovementLoadedEvent(pictures, idMovement);
    }else{
      picturesByIdMovementLoadedEvent.setPicturesByIdMovement(pictures);
      picturesByIdMovementLoadedEvent.setIdMovement(idMovement);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + pictureByIdMovementLoadedEvent.getPictureByIdMovement() + " elements and idMovement ="+pictureByIdMovementLoadedEvent.getIdMovement());
    EventBus.getDefault().post(picturesByIdMovementLoadedEvent);
  }

}
