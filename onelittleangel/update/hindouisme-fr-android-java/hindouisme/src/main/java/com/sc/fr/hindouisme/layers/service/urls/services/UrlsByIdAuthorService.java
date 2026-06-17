package com.sc.fr.hindouisme.layers.service.urls.services;

import android.util.SparseArray;

import com.sc.fr.hindouisme.OnelittleAngelApplication;
import com.sc.fr.hindouisme.injector.Injector;
import com.sc.fr.hindouisme.layers.dao.urls.UrlsDaoInterface;
import com.sc.fr.hindouisme.layers.service.MotherBusinessService;
import com.sc.fr.hindouisme.layers.service.ServiceManagerInterface;
import com.sc.fr.hindouisme.layers.service.urls.interfaces.UrlsByIdAuthorserviceInterface;
import com.sc.fr.hindouisme.transverse.eventbus.events.urls.UrlsByIdAuthorLoadedEvent;
import com.sc.fr.hindouisme.transverse.eventbus.models.UrlEventBus;
import com.sc.fr.hindouisme.transverse.orms.realm.models.Url;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class UrlsByIdAuthorService extends MotherBusinessService implements UrlsByIdAuthorserviceInterface {

  private static final String TAG = "UrlsByIdAuthorService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Url>> urlsByIdAuthorList = null;

  /**
   *
   */
  private UrlsByIdAuthorLoadedEvent urlsByIdAuthorLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public UrlsByIdAuthorService(ServiceManagerInterface srvManager) {
    super(srvManager);
    urlsByIdAuthorList = new SparseArray<>();
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
  public void loadUrlsByIdAuthorAsync(int idAuthor) {
    //  Log.e(TAG, "LoadUrlsByIdAuthorAsync() called with: " + "cityId = [" + idAuthor + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (urlsByIdAuthorList.get(idAuthor)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postUrlsByIdAuthorDataLoadedEvent(urlsByIdAuthorList.get(idAuthor),idAuthor);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoUrlsByIdAuthorLoadRunnable(idAuthor));
    }
  }

  private void LoadUrlsByIdAuthorSync(int idAuthor){
    // Log.e(TAG, "LoadUrlsByIdAuthorSync() called with: " + "idAuthor = [" + idAuthor + "]");
    // Load data from database
    /*

   */
    UrlsDaoInterface urlsDaoInterface = Injector.getDaoManager().getUrlsDao();
    List<Url> urlsByIdAuthor = urlsDaoInterface.findUrlsByIdAuthor(idAuthor);
    urlsDaoInterface = null;
    //update your own cache
    urlsByIdAuthorList.put(idAuthor,urlsByIdAuthor);
    //send send back the answer using eventBus
    postUrlsByIdAuthorDataLoadedEvent(urlsByIdAuthor,idAuthor);
  }

  /**
   * @url Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoUrlsByIdAuthorLoadRunnable implements Runnable {
    final int idAuthor;

    public DaoUrlsByIdAuthorLoadRunnable(int idAuthor) {
      this.idAuthor = idAuthor;
    }

    @Override
    public void run() {
      LoadUrlsByIdAuthorSync(idAuthor);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postUrlsByIdAuthorDataLoadedEvent(List<Url> urlsByIdAuthor,int idAuthor) {
    //  Log.e(TAG, "postAuthorByIdAuthorDataLoadedEvent() called as found Author with " +
    //    urlByIdAuthorLoadedEvent.getAuthorByIdAuthor().getQuotes().size() + " elements and idAuthor="+idAuthor);
    List<UrlEventBus> urls = new ArrayList<>();

    for(Url u : urlsByIdAuthor){
      urls.add(new UrlEventBus(u));
    }

    if(urlsByIdAuthorLoadedEvent==null){
      urlsByIdAuthorLoadedEvent= new UrlsByIdAuthorLoadedEvent(urls, idAuthor);
    }else{
      urlsByIdAuthorLoadedEvent.setUrlsByIdAuthor(urls);
      urlsByIdAuthorLoadedEvent.setIdAuthor(idAuthor);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + urlByIdAuthorLoadedEvent.getAuthorByIdAuthor() + " elements and idAuthor ="+urlByIdAuthorLoadedEvent.getIdAuthor());
    EventBus.getDefault().post(urlsByIdAuthorLoadedEvent);
  }

}
