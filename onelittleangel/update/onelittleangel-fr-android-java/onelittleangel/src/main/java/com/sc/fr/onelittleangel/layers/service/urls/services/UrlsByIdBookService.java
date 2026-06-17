package com.sc.fr.onelittleangel.layers.service.urls.services;

import android.util.SparseArray;

import com.sc.fr.onelittleangel.injector.Injector;
import com.sc.fr.onelittleangel.OnelittleAngelApplication;
import com.sc.fr.onelittleangel.layers.dao.urls.UrlsDaoInterface;
import com.sc.fr.onelittleangel.layers.service.MotherBusinessService;
import com.sc.fr.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.fr.onelittleangel.layers.service.urls.interfaces.UrlsByIdBookServiceInterface;
import com.sc.fr.onelittleangel.transverse.eventbus.events.urls.UrlsByIdBookLoadedEvent;
import com.sc.fr.onelittleangel.transverse.eventbus.models.UrlEventBus;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Url;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class UrlsByIdBookService extends MotherBusinessService implements UrlsByIdBookServiceInterface {

  private static final String TAG = "UrlsByIdBookService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Url>> urlsByIdBookList = null;

  /**
   *
   */
  private UrlsByIdBookLoadedEvent urlsByIdBookLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public UrlsByIdBookService(ServiceManagerInterface srvManager) {
    super(srvManager);
    urlsByIdBookList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param idBook
   */
  @Override
  public void loadUrlsByIdBookAsync(int idBook) {
    //  Log.e(TAG, "loadUrlsByIdBookAsync() called with: " + "cityId = [" + idBook + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (urlsByIdBookList.get(idBook)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postUrlByIdBookDataLoadedEvent(urlsByIdBookList.get(idBook),idBook);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new UrlsByIdBookService.DaoUrlByIdBookLoadRunnable(idBook));
    }
  }

  private void loadUrlsByIdBookSync(int idBook){
    // Log.e(TAG, "loadUrlsByIdBookSync() called with: " + "idBook = [" + idBook + "]");
    // Load data from database
    /*

   */
    UrlsDaoInterface urlsDaoInterface = Injector.getDaoManager().getUrlsDao();
    List<Url> urlsByIdBook = urlsDaoInterface.findUrlsByIdBook(idBook);
    urlsDaoInterface = null;
    //update your own cache
    urlsByIdBookList.put(idBook,urlsByIdBook);
    //send send back the answer using eventBus
    postUrlByIdBookDataLoadedEvent(urlsByIdBook,idBook);
  }

  /**
   * @url Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoUrlByIdBookLoadRunnable implements Runnable {
    final int idBook;

    public DaoUrlByIdBookLoadRunnable(int idBook) {
      this.idBook = idBook;
    }

    @Override
    public void run() {
      loadUrlsByIdBookSync(idBook);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postUrlByIdBookDataLoadedEvent(List<Url> urlsByIdBook,int idBook) {
    //  Log.e(TAG, "postUrlByIdBookDataLoadedEvent() called as found Url with " +
    //    urlByIdBookLoadedEvent.getUrlByIdBook().getQuotes().size() + " elements and idBook="+idBook);
    List<UrlEventBus> urls = new ArrayList<>();

    for(Url u : urlsByIdBook){
      urls.add(new UrlEventBus(u));
    }

    if(urlsByIdBookLoadedEvent==null){
      urlsByIdBookLoadedEvent= new UrlsByIdBookLoadedEvent(urls, idBook);
    }else{
      urlsByIdBookLoadedEvent.setUrlsByIdBook(urls);
      urlsByIdBookLoadedEvent.setIdBook(idBook);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + urlByIdBookLoadedEvent.getUrlByIdBook() + " elements and idBook ="+urlByIdBookLoadedEvent.getIdBook());
    EventBus.getDefault().post(urlsByIdBookLoadedEvent);
  }

}
