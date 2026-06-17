package com.sc.fr.christianisme.layers.service.centuries.services;

import android.util.SparseArray;

import com.sc.fr.christianisme.OnelittleAngelApplication;
import com.sc.fr.christianisme.layers.service.MotherBusinessService;
import com.sc.fr.christianisme.layers.service.ServiceManagerInterface;
import com.sc.fr.christianisme.layers.service.centuries.interfaces.CenturyByIdBookServiceInterface;
import com.sc.fr.christianisme.transverse.eventbus.models.CenturyEventBus;
import com.sc.fr.christianisme.injector.Injector;
import com.sc.fr.christianisme.layers.dao.centuries.CenturiesDaoInterface;
import com.sc.fr.christianisme.transverse.eventbus.events.centuries.CenturyByIdBookLoadedEvent;
import com.sc.fr.christianisme.transverse.orms.realm.models.Century;

import org.greenrobot.eventbus.EventBus;

public class CenturyByIdBookService extends MotherBusinessService implements CenturyByIdBookServiceInterface {

  private static final String TAG = "CenturyByIdBookService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Century> centuriesByIdBookList = null;

  /**
   *
   */
  private CenturyByIdBookLoadedEvent centuryByIdBookLoadedEvent;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public CenturyByIdBookService(ServiceManagerInterface srvManager) {
    super(srvManager);
    centuriesByIdBookList = new SparseArray<>();
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
  public void loadCenturyByIdBookAsync(int idBook) {
    //  Log.e(TAG, "loadBookByIdBookAsync() called with: " + "cityId = [" + idBook + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (centuriesByIdBookList.get(idBook)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postCenturyByIdBookDataLoadedEvent(centuriesByIdBookList.get(idBook),idBook);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoCenturyByIdBookLoadRunnable(idBook));
    }
  }

  private void loadBookByIdBookSync(int idBook){
    // Log.e(TAG, "loadBookByIdBookSync() called with: " + "idBook = [" + idBook + "]");
    // Load data from database
    /*

   */
    CenturiesDaoInterface centuriesDaoInterface = Injector.getDaoManager().getCenturiesDao();
    Century centuryByIdBook = centuriesDaoInterface.findCenturyByIdBook(idBook);
    centuriesDaoInterface = null;
    //update your own cache
    centuriesByIdBookList.put(idBook,centuryByIdBook);
    //send send back the answer using eventBus
    postCenturyByIdBookDataLoadedEvent(centuryByIdBook,idBook);
  }

  /**
   * @century Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoCenturyByIdBookLoadRunnable implements Runnable {
    final int idBook;

    public DaoCenturyByIdBookLoadRunnable(int idBook) {
      this.idBook = idBook;
    }

    @Override
    public void run() {
      loadBookByIdBookSync(idBook);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postCenturyByIdBookDataLoadedEvent(Century centuryByIdBook,int idBook) {
    //  Log.e(TAG, "postAuthorByIdBookDataLoadedEvent() called as found Author with " +
    //    centuryByIdBookLoadedEvent.getAuthorByIdBook().getQuotes().size() + " elements and idBook="+idBook);
    CenturyEventBus century = new CenturyEventBus(centuryByIdBook);


    if(centuryByIdBookLoadedEvent==null){
      centuryByIdBookLoadedEvent= new CenturyByIdBookLoadedEvent(century, idBook);
    }else{
      centuryByIdBookLoadedEvent.setCenturyByIdBook(century);
      centuryByIdBookLoadedEvent.setIdBook(idBook);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + centuryByIdBookLoadedEvent.getAuthorByIdBook() + " elements and idBook ="+centuryByIdBookLoadedEvent.getIdBook());
    EventBus.getDefault().post(centuryByIdBookLoadedEvent);
  }

}
