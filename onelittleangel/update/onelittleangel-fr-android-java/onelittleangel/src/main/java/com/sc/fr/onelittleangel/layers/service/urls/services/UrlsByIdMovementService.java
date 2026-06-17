package com.sc.fr.onelittleangel.layers.service.urls.services;

import android.util.SparseArray;

import com.sc.fr.onelittleangel.injector.Injector;
import com.sc.fr.onelittleangel.OnelittleAngelApplication;
import com.sc.fr.onelittleangel.layers.dao.urls.UrlsDaoInterface;
import com.sc.fr.onelittleangel.layers.service.MotherBusinessService;
import com.sc.fr.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.fr.onelittleangel.layers.service.urls.interfaces.UrlsByIdMovementServiceInterface;
import com.sc.fr.onelittleangel.transverse.eventbus.events.urls.UrlsByIdMovementLoadedEvent;
import com.sc.fr.onelittleangel.transverse.eventbus.models.UrlEventBus;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Url;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class UrlsByIdMovementService extends MotherBusinessService implements UrlsByIdMovementServiceInterface {

  private static final String TAG = "UrlsByIdMovementService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Url>> urlsByIdMovementList = null;

  /**
   *
   */
  private UrlsByIdMovementLoadedEvent urlsByIdMovementLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public UrlsByIdMovementService(ServiceManagerInterface srvManager) {
    super(srvManager);
    urlsByIdMovementList = new SparseArray<>();
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
  public void loadUrlsByIdMovementAsync(int idMovement) {
    //  Log.e(TAG, "loadUrlsByIdMovementAsync() called with: " + "cityId = [" + idMovement + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (urlsByIdMovementList.get(idMovement)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postUrlByIdMovementDataLoadedEvent(urlsByIdMovementList.get(idMovement),idMovement);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new UrlsByIdMovementService.DaoUrlByIdMovementLoadRunnable(idMovement));
    }
  }

  private void loadUrlsByIdMovementSync(int idMovement){
    // Log.e(TAG, "loadUrlsByIdMovementSync() called with: " + "idMovement = [" + idMovement + "]");
    // Load data from database
    /*

   */
    UrlsDaoInterface urlsDaoInterface = Injector.getDaoManager().getUrlsDao();
    List<Url> urlsByIdMovement = urlsDaoInterface.findUrlsByIdMovement(idMovement);
    urlsDaoInterface = null;
    //update your own cache
    urlsByIdMovementList.put(idMovement,urlsByIdMovement);
    //send send back the answer using eventBus
    postUrlByIdMovementDataLoadedEvent(urlsByIdMovement,idMovement);
  }

  /**
   * @url Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoUrlByIdMovementLoadRunnable implements Runnable {
    final int idMovement;

    public DaoUrlByIdMovementLoadRunnable(int idMovement) {
      this.idMovement = idMovement;
    }

    @Override
    public void run() {
      loadUrlsByIdMovementSync(idMovement);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postUrlByIdMovementDataLoadedEvent(List<Url> urlsByIdMovement,int idMovement) {
    //  Log.e(TAG, "postUrlByIdMovementDataLoadedEvent() called as found Url with " +
    //    urlByIdMovementLoadedEvent.getUrlByIdMovement().getQuotes().size() + " elements and idMovement="+idMovement);
    List<UrlEventBus> urls = new ArrayList<>();

    for(Url u : urlsByIdMovement){
      urls.add(new UrlEventBus(u));
    }

    if(urlsByIdMovementLoadedEvent==null){
      urlsByIdMovementLoadedEvent= new UrlsByIdMovementLoadedEvent(urls, idMovement);
    }else{
      urlsByIdMovementLoadedEvent.setUrlsByIdMovement(urls);
      urlsByIdMovementLoadedEvent.setIdMovement(idMovement);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + urlByIdMovementLoadedEvent.getUrlByIdMovement() + " elements and idMovement ="+urlByIdMovementLoadedEvent.getIdMovement());
    EventBus.getDefault().post(urlsByIdMovementLoadedEvent);
  }

}
