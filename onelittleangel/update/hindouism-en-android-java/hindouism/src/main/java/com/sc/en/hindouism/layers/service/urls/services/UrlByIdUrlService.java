package com.sc.en.hindouism.layers.service.urls.services;

import android.util.SparseArray;

import com.sc.en.hindouism.OnelittleAngelApplication;
import com.sc.en.hindouism.layers.dao.urls.UrlsDaoInterface;
import com.sc.en.hindouism.layers.service.MotherBusinessService;
import com.sc.en.hindouism.layers.service.ServiceManagerInterface;
import com.sc.en.hindouism.layers.service.urls.interfaces.UrlByIdUrlServiceInterface;
import com.sc.en.hindouism.transverse.eventbus.events.urls.UrlByIdUrlLoadedEvent;
import com.sc.en.hindouism.transverse.eventbus.models.UrlEventBus;
import com.sc.en.hindouism.transverse.orms.realm.models.Url;
import com.sc.en.hindouism.injector.Injector;

import org.greenrobot.eventbus.EventBus;

public class UrlByIdUrlService extends MotherBusinessService implements UrlByIdUrlServiceInterface {

  private static final String TAG = "UrlByIdUrlService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Url> urlsByIdUrlList = null;

  /**
   *
   */
  private UrlByIdUrlLoadedEvent urlByIdUrlLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public UrlByIdUrlService(ServiceManagerInterface srvManager) {
    super(srvManager);
    urlsByIdUrlList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param idUrl
   */
  @Override
  public void loadUrlByIdUrlAsync(int idUrl) {
    //  Log.e(TAG, "loadUrlByIdUrlAsync() called with: " + "cityId = [" + idUrl + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (urlsByIdUrlList.get(idUrl)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postUrlByIdUrlDataLoadedEvent(urlsByIdUrlList.get(idUrl),idUrl);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new UrlByIdUrlService.DaoUrlByIdUrlLoadRunnable(idUrl));
    }
  }

  private void loadUrlByIdUrlSync(int idUrl){
    // Log.e(TAG, "loadUrlByIdUrlSync() called with: " + "idUrl = [" + idUrl + "]");
    // Load data from database
    /*

   */
    UrlsDaoInterface urlsDaoInterface = Injector.getDaoManager().getUrlsDao();
    Url urlByIdUrl = urlsDaoInterface.findUrlByIdUrl(idUrl);
    urlsDaoInterface = null;
    //update your own cache
    urlsByIdUrlList.put(idUrl,urlByIdUrl);
    //send send back the answer using eventBus
    postUrlByIdUrlDataLoadedEvent(urlByIdUrl,idUrl);
  }

  /**
   * @url Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoUrlByIdUrlLoadRunnable implements Runnable {
    final int idUrl;

    public DaoUrlByIdUrlLoadRunnable(int idUrl) {
      this.idUrl = idUrl;
    }

    @Override
    public void run() {
      loadUrlByIdUrlSync(idUrl);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postUrlByIdUrlDataLoadedEvent(Url urlByIdUrl,int idUrl) {
    //  Log.e(TAG, "postUrlByIdUrlDataLoadedEvent() called as found Url with " +
    //    urlByIdUrlLoadedEvent.getUrlByIdUrl().getQuotes().size() + " elements and idUrl="+idUrl);
    UrlEventBus url = new UrlEventBus(urlByIdUrl);

    if(urlByIdUrlLoadedEvent==null){
      urlByIdUrlLoadedEvent= new UrlByIdUrlLoadedEvent(url, idUrl);
    }else{
      urlByIdUrlLoadedEvent.setUrlByIdUrl(url);
      urlByIdUrlLoadedEvent.setIdUrl(idUrl);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + urlByIdUrlLoadedEvent.getUrlByIdUrl() + " elements and idUrl ="+urlByIdUrlLoadedEvent.getIdUrl());
    EventBus.getDefault().post(urlByIdUrlLoadedEvent);
  }

}
