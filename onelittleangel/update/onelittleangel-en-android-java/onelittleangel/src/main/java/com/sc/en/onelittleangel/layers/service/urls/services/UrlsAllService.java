package com.sc.en.onelittleangel.layers.service.urls.services;

import android.util.Log;
import android.util.SparseArray;

import com.sc.en.onelittleangel.layers.dao.urls.UrlsDaoInterface;
import com.sc.en.onelittleangel.layers.service.MotherBusinessService;
import com.sc.en.onelittleangel.layers.service.urls.interfaces.UrlsAllServiceInterface;
import com.sc.en.onelittleangel.transverse.eventbus.events.urls.UrlsAllLoadedEvent;
import com.sc.en.onelittleangel.transverse.eventbus.models.UrlEventBus;
import com.sc.en.onelittleangel.transverse.orms.realm.models.Url;
import com.sc.en.onelittleangel.injector.Injector;
import com.sc.en.onelittleangel.OnelittleAngelApplication;
import com.sc.en.onelittleangel.layers.service.ServiceManagerInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class UrlsAllService extends MotherBusinessService implements UrlsAllServiceInterface {

  private static final String TAG = "UrlsAllService";

  /**
   *
   */
  private UrlsAllServiceInterface urlsAllServiceInterface = null;
  
  /**
   *
   */
  private UrlsAllLoadedEvent urlsAllLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public UrlsAllService(ServiceManagerInterface srvManager) {
    super(srvManager);
    /*
    The authors to display (the cache)
   */
    SparseArray<Url> urlsList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }


  /**
   * Load all the urls from the database asynchronously
   */
  @Override
  public void loadAllUrlsAsync() {
    //Log.d(TAG, "loadUrls() called with: " + "");
    OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindAllUrlsRunnable);
  }

  private void loadAllUrlsSync(){
    // Load data from DB
    UrlsDaoInterface urlsDaoInterface = Injector.getDaoManager().getUrlsDao();
    //send back the answer using eventBus
    postAllUrlsLoadedEvent(urlsDaoInterface.findAllUrls());
    urlsDaoInterface =null;
  }

  /**
   * The runnable to execute
   */
  private final DaoFindAllUrlsRunnable daoFindAllUrlsRunnable = new DaoFindAllUrlsRunnable();
  /**
   * @url Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable
   */
  private class DaoFindAllUrlsRunnable implements Runnable {
    @Override
    public void run() {
      loadAllUrlsSync();
    }
  }

  /**
   * Broadcast all the urls Loaded event
   */
  private void postAllUrlsLoadedEvent(List<Url> urlsAll) {

    List<UrlEventBus> urls = new ArrayList<>();

    for(Url a : urlsAll){
      urls.add(new UrlEventBus(a));
    }

    if(urlsAllLoadedEvent ==null){
      urlsAllLoadedEvent = new UrlsAllLoadedEvent(urls);
    }else{
      urlsAllLoadedEvent.setUrls(urls);
    }
    //Log.e(TAG, "postCitiesLoadedEvent posted" );
    EventBus.getDefault().post(urlsAllLoadedEvent);
  }
}
