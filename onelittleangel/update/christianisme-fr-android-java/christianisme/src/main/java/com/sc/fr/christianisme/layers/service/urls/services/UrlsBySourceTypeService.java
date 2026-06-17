package com.sc.fr.christianisme.layers.service.urls.services;

import android.support.v4.util.ArrayMap;

import com.sc.fr.christianisme.OnelittleAngelApplication;
import com.sc.fr.christianisme.layers.dao.urls.UrlsDaoInterface;
import com.sc.fr.christianisme.layers.service.MotherBusinessService;
import com.sc.fr.christianisme.layers.service.ServiceManagerInterface;
import com.sc.fr.christianisme.layers.service.urls.interfaces.UrlsBySourceTypeServiceInterface;
import com.sc.fr.christianisme.transverse.eventbus.events.urls.UrlsBySourceTypeLoadedEvent;
import com.sc.fr.christianisme.transverse.eventbus.models.UrlEventBus;
import com.sc.fr.christianisme.transverse.orms.realm.models.Url;
import com.sc.fr.christianisme.injector.Injector;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class UrlsBySourceTypeService extends MotherBusinessService implements UrlsBySourceTypeServiceInterface {

  private static final String TAG = "UrlsBySourceTypeService";

  /**
   * The authors to display (the cache)
   */
  private ArrayMap<String, List<Url>> urlsBySourceTypeList = null;

  /**
   *
   */
  private UrlsBySourceTypeLoadedEvent urlsBySourceTypeLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public UrlsBySourceTypeService(ServiceManagerInterface srvManager) {
    super(srvManager);
    urlsBySourceTypeList = new ArrayMap<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param sourceType
   */
  @Override
  public void loadUrlsBySourceTypeAsync(String sourceType) {
    //  Log.e(TAG, "loadUrlsBySourceTypeAsync() called with: " + "cityId = [" + sourceType + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (urlsBySourceTypeList.get(sourceType)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postUrlsBySourceTypeDataLoadedEvent(urlsBySourceTypeList.get(sourceType),sourceType);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoUrlsBySourceTypeLoadRunnable(sourceType));
    }
  }

  private void loadUrlsBySourceTypeSync(String sourceType){
    // Log.e(TAG, "loadUrlsBySourceTypeSync() called with: " + "sourceType = [" + sourceType + "]");
    // Load data from database
    /*

   */
    UrlsDaoInterface urlsDaoInterface = Injector.getDaoManager().getUrlsDao();
    List<Url> urlsBySourceType = urlsDaoInterface.findUrlsBySourceType(sourceType);
    urlsDaoInterface = null;
    //update your own cache
    urlsBySourceTypeList.put(sourceType,urlsBySourceType);
    //send send back the answer using eventBus
    postUrlsBySourceTypeDataLoadedEvent(urlsBySourceType,sourceType);
  }

  /**
   * @url Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoUrlsBySourceTypeLoadRunnable implements Runnable {
    final String sourceType;

    public DaoUrlsBySourceTypeLoadRunnable(String sourceType) {
      this.sourceType = sourceType;
    }

    @Override
    public void run() {
      loadUrlsBySourceTypeSync(sourceType);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postUrlsBySourceTypeDataLoadedEvent(List<Url> urlsBySourceType,String sourceType) {
    //  Log.e(TAG, "postUrlBySourceTypeDataLoadedEvent() called as found Url with " +
    //    urlBySourceTypeLoadedEvent.getUrlBySourceType().getQuotes().size() + " elements and sourceType="+sourceType);
    List<UrlEventBus> urls = new ArrayList<>();

    for(Url u : urlsBySourceType){
      urls.add(new UrlEventBus(u));
    }

    if(urlsBySourceTypeLoadedEvent==null){
      urlsBySourceTypeLoadedEvent= new UrlsBySourceTypeLoadedEvent(urls, sourceType);
    }else{
      urlsBySourceTypeLoadedEvent.setUrlsBySourceType(urls);
      urlsBySourceTypeLoadedEvent.setSourceType(sourceType);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + urlBySourceTypeLoadedEvent.getUrlBySourceType() + " elements and sourceType ="+urlBySourceTypeLoadedEvent.getSourceType());
    EventBus.getDefault().post(urlsBySourceTypeLoadedEvent);
  }

}
