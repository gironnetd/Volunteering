package com.sc.en.quotes.layers.service.centuries.services;

import android.util.SparseArray;

import com.sc.en.quotes.layers.dao.centuries.CenturiesDaoInterface;
import com.sc.en.quotes.layers.service.MotherBusinessService;
import com.sc.en.quotes.transverse.eventbus.models.CenturyEventBus;
import com.sc.en.quotes.transverse.orms.realm.models.Century;
import com.sc.en.quotes.injector.Injector;
import com.sc.en.quotes.OnelittleAngelApplication;
import com.sc.en.quotes.layers.service.ServiceManagerInterface;
import com.sc.en.quotes.layers.service.centuries.interfaces.CenturyByIdAuthorServiceInterface;
import com.sc.en.quotes.transverse.eventbus.events.centuries.CenturyByIdAuthorLoadedEvent;

import org.greenrobot.eventbus.EventBus;

public class CenturyByIdAuthorService extends MotherBusinessService implements CenturyByIdAuthorServiceInterface {

  private static final String TAG = "CenturyByIdAuthorService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Century> centuriesByIdAuthorList = null;

  /**
   *
   */
  private CenturyByIdAuthorLoadedEvent centuryByIdAuthorLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public CenturyByIdAuthorService(ServiceManagerInterface srvManager) {
    super(srvManager);
    centuriesByIdAuthorList = new SparseArray<>();
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
  public void loadCenturyByIdAuthorAsync(int idAuthor) {
    //  Log.e(TAG, "loadAuthorByIdAuthorAsync() called with: " + "cityId = [" + idAuthor + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (centuriesByIdAuthorList.get(idAuthor)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postCenturyByIdAuthorDataLoadedEvent(centuriesByIdAuthorList.get(idAuthor),idAuthor);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoCenturyByIdAuthorLoadRunnable(idAuthor));
    }
  }

  private void loadAuthorByIdAuthorSync(int idAuthor){
    // Log.e(TAG, "loadAuthorByIdAuthorSync() called with: " + "idAuthor = [" + idAuthor + "]");
    // Load data from database
    /*

   */
    CenturiesDaoInterface centuriesDaoInterface = Injector.getDaoManager().getCenturiesDao();
    Century centuryByIdAuthor = centuriesDaoInterface.findCenturyByIdAuthor(idAuthor);
    centuriesDaoInterface = null;
    //update your own cache
    centuriesByIdAuthorList.put(idAuthor,centuryByIdAuthor);
    //send send back the answer using eventBus
    postCenturyByIdAuthorDataLoadedEvent(centuryByIdAuthor,idAuthor);
  }

  /**
   * @author Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoCenturyByIdAuthorLoadRunnable implements Runnable {
    final int idAuthor;

    public DaoCenturyByIdAuthorLoadRunnable(int idAuthor) {
      this.idAuthor = idAuthor;
    }

    @Override
    public void run() {
      loadAuthorByIdAuthorSync(idAuthor);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postCenturyByIdAuthorDataLoadedEvent(Century centuryByIdAuthor,int idAuthor) {
    //  Log.e(TAG, "postAuthorByIdAuthorDataLoadedEvent() called as found Author with " +
    //    authorByIdAuthorLoadedEvent.getAuthorByIdAuthor().getQuotes().size() + " elements and idAuthor="+idAuthor);
    CenturyEventBus century = new CenturyEventBus(centuryByIdAuthor);

    if(centuryByIdAuthorLoadedEvent==null){
      centuryByIdAuthorLoadedEvent= new CenturyByIdAuthorLoadedEvent(century, idAuthor);
    }else{
      centuryByIdAuthorLoadedEvent.setCenturyByIdAuthor(century);
      centuryByIdAuthorLoadedEvent.setIdAuthor(idAuthor);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + centuryByIdAuthorLoadedEvent.getAuthorByIdAuthor() + " elements and idAuthor ="+centuryByIdAuthorLoadedEvent.getIdAuthor());
    EventBus.getDefault().post(centuryByIdAuthorLoadedEvent);
  }

}
