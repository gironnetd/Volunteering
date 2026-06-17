package com.sc.en.confucianism.layers.service.urls.services;

import android.util.SparseArray;

import com.sc.en.confucianism.OnelittleAngelApplication;
import com.sc.en.confucianism.layers.dao.urls.UrlsDaoInterface;
import com.sc.en.confucianism.layers.service.urls.interfaces.UrlsByIdSourceServiceInterface;
import com.sc.en.confucianism.transverse.eventbus.models.UrlEventBus;
import com.sc.en.confucianism.layers.service.MotherBusinessService;
import com.sc.en.confucianism.transverse.eventbus.events.urls.UrlsByIdSourceLoadedEvent;
import com.sc.en.confucianism.transverse.orms.realm.models.Url;
import com.sc.en.confucianism.injector.Injector;
import com.sc.en.confucianism.layers.service.ServiceManagerInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class UrlsByIdSourceService extends MotherBusinessService implements UrlsByIdSourceServiceInterface {

  private static final String TAG = "UrlsByIdSourceAndSourceTypeService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Url>> urlsByIdSourceList = null;

  /**
   *
   */
  private UrlsByIdSourceLoadedEvent urlsByIdSourceLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public UrlsByIdSourceService(ServiceManagerInterface srvManager) {
    super(srvManager);
    urlsByIdSourceList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param sourceType
   * @param idSource
   */
  @Override
  public void loadUrlsByIdSourceAsync(String sourceType, int idSource) {
//    //  Log.e(TAG, "loadAuthorByIdThemeAsync() called with: " + "cityId = [" + idTheme + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (urlsByIdSourceList.get(idSource)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postUrlsByIdSourceDataLoadedEvent(urlsByIdSourceList.get(idSource), idSource, sourceType);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoUrlsByIdSourceLoadRunnable(idSource, sourceType));
    }
  }

  private void loadUrlsByIdSourceSync(int idSource, String sourceType){
//    // Log.e(TAG, "loadAuthorByIdThemeSync() called with: " + "idTheme = [" + idTheme + "]");
    // Load data from database
    /*

   */
    UrlsDaoInterface urlsDaoInterface = Injector.getDaoManager().getUrlsDao();
    List<Url> urlsByIdSource = urlsDaoInterface.findUrlsByIdSource(sourceType, idSource);
    urlsDaoInterface = null;
    //update your own cache
    urlsByIdSourceList.put(idSource, urlsByIdSource);
    //send send back the answer using eventBus
    postUrlsByIdSourceDataLoadedEvent(urlsByIdSource, idSource, sourceType);
  //  postAuthorByIdThemeDataLoadedEvent(authorsByIdTheme,idTheme);
  }

  /**
   * @author Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoUrlsByIdSourceLoadRunnable implements Runnable {
    final int idSource;
    final String sourceType;

    public DaoUrlsByIdSourceLoadRunnable(int idSource, String sourceType) {
      this.idSource = idSource;
      this.sourceType = sourceType;
    }

    @Override
    public void run() {
      loadUrlsByIdSourceSync(idSource, sourceType);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postUrlsByIdSourceDataLoadedEvent(List<Url> urlsByIdSource,int idSource,String sourceType) {
    List<UrlEventBus> urls = new ArrayList<>();

    for(Url u : urlsByIdSource){
      urls.add(new UrlEventBus(u));
    }

    if(urlsByIdSourceLoadedEvent==null){
      urlsByIdSourceLoadedEvent= new UrlsByIdSourceLoadedEvent(urls, idSource, sourceType);
    }else{
      urlsByIdSourceLoadedEvent.setUrlsByIdSource(urls);
      urlsByIdSourceLoadedEvent.setSourceType(sourceType);
      urlsByIdSourceLoadedEvent.setIdSource(idSource);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + urlBySourceTypeLoadedEvent.getUrlBySourceType() + " elements and sourceType ="+urlBySourceTypeLoadedEvent.getSourceType());
    EventBus.getDefault().post(urlsByIdSourceLoadedEvent);
  }
}
