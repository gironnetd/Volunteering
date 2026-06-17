package com.sc.fr.christianisme.layers.service.pictures.services;

import android.util.Log;
import android.util.SparseArray;

import com.sc.fr.christianisme.OnelittleAngelApplication;
import com.sc.fr.christianisme.layers.dao.pictures.PicturesDaoInterface;
import com.sc.fr.christianisme.transverse.eventbus.events.pictures.PicturesAllLoadedEvent;
import com.sc.fr.christianisme.transverse.eventbus.models.PictureEventBus;
import com.sc.fr.christianisme.injector.Injector;
import com.sc.fr.christianisme.layers.service.MotherBusinessService;
import com.sc.fr.christianisme.layers.service.ServiceManagerInterface;
import com.sc.fr.christianisme.layers.service.pictures.interfaces.PicturesAllServiceInterface;
import com.sc.fr.christianisme.layers.service.presentations.interfaces.PresentationsAllServiceInterface;
import com.sc.fr.christianisme.transverse.orms.realm.models.Picture;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class PicturesAllService extends MotherBusinessService implements PicturesAllServiceInterface {

  private static final String TAG = "PicturesAllService";

  /**
   *
   */
  private PresentationsAllServiceInterface presentationsAllServiceInterface = null;

  /**
   *
   */
  private PicturesAllLoadedEvent picturesAllLoadedEvent;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public PicturesAllService(ServiceManagerInterface srvManager) {
    super(srvManager);
    /*
    The authors to display (the cache)
   */
    SparseArray<Picture> picturesList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * Clean your resource when your service die
   *
   * @param srvManager
   */
  @Override
  public void onDestroy(ServiceManagerInterface srvManager) {
    super.onDestroy(srvManager);
  }

  /**
   * Load all the pictures from the database asynchronously
   */
  @Override
  public void loadAllPicturesAsync() {
    //Log.d(TAG, "loadPictures() called with: " + "");
    OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindAllPicturesRunnable);
  }

  private void loadAllPicturesSync(){
    // Load data from DB
    PicturesDaoInterface picturesDaoInterface = Injector.getDaoManager().getPicturesDao();
    //send back the answer using eventBus
    postAllPicturesLoadedEvent(picturesDaoInterface.findAllPictures());
    picturesDaoInterface =null;
  }

  /**
   * The runnable to execute
   */
  private final DaoFindAllPicturesRunnable daoFindAllPicturesRunnable = new DaoFindAllPicturesRunnable();
  /**
   * @picture Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable
   */
  private class DaoFindAllPicturesRunnable implements Runnable {
    @Override
    public void run() {
      loadAllPicturesSync();
    }
  }

  /**
   * Broadcast all the pictures Loaded event
   */
  private void postAllPicturesLoadedEvent(List<Picture> picturesAll) {

    List<PictureEventBus> pictures = new ArrayList<>();

    for(Picture a : picturesAll){
      pictures.add(new PictureEventBus(a));
    }

    if(picturesAllLoadedEvent ==null){
      picturesAllLoadedEvent = new PicturesAllLoadedEvent(pictures);
    }else{
      picturesAllLoadedEvent.setPictures(pictures);
    }
    //Log.e(TAG, "postCitiesLoadedEvent posted" );
    EventBus.getDefault().post(picturesAllLoadedEvent);
  }
}
