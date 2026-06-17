package com.sc.fr.hindouisme.layers.service.centuries.services;

import android.util.SparseArray;

import com.sc.fr.hindouisme.OnelittleAngelApplication;
import com.sc.fr.hindouisme.layers.service.centuries.interfaces.CenturyByIdCenturyServiceInterface;
import com.sc.fr.hindouisme.transverse.eventbus.models.CenturyEventBus;
import com.sc.fr.hindouisme.transverse.orms.realm.models.Century;
import com.sc.fr.hindouisme.injector.Injector;
import com.sc.fr.hindouisme.layers.dao.centuries.CenturiesDaoInterface;
import com.sc.fr.hindouisme.layers.service.MotherBusinessService;
import com.sc.fr.hindouisme.layers.service.ServiceManagerInterface;
import com.sc.fr.hindouisme.transverse.eventbus.events.centuries.CenturyByIdCenturyLoadedEvent;

import org.greenrobot.eventbus.EventBus;

public class CenturyByIdCenturyService extends MotherBusinessService implements CenturyByIdCenturyServiceInterface {

  private static final String TAG = "CenturyByIdCenturyService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Century> centuriesByIdCenturyList = null;

  /**
   *
   */
  private CenturyByIdCenturyLoadedEvent centuryByIdCenturyLoadedEvent;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public CenturyByIdCenturyService(ServiceManagerInterface srvManager) {
    super(srvManager);
    centuriesByIdCenturyList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param idCentury
   */
  @Override
  public void loadCenturyByIdCenturyAsync(int idCentury) {
    //  Log.e(TAG, "loadCenturyByIdAuthorAsync() called with: " + "cityId = [" + idAuthor + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (centuriesByIdCenturyList.get(idCentury)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postAuthorByIdAuthorDataLoadedEvent(centuriesByIdCenturyList.get(idCentury),idCentury);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoCenturyByIdCenturyLoadRunnable(idCentury));
    }
  }

  private void loadCenturyByIdCenturySync(int idCentury){
    // Log.e(TAG, "loadCenturyByIdAuthorSync() called with: " + "idAuthor = [" + idAuthor + "]");
    // Load data from database
    /*

   */
    CenturiesDaoInterface centuriesDaoInterface = Injector.getDaoManager().getCenturiesDao();
    Century centuryByIdCentury = centuriesDaoInterface.findCenturyByIdCentury(idCentury);
    centuriesDaoInterface = null;
    //update your own cache
    centuriesByIdCenturyList.put(idCentury, centuryByIdCentury);
    //send send back the answer using eventBus
    postAuthorByIdAuthorDataLoadedEvent(centuryByIdCentury,idCentury);
  }

  /**
   * @author Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoCenturyByIdCenturyLoadRunnable implements Runnable {
    final int idCentury;

    public DaoCenturyByIdCenturyLoadRunnable(int idCentury) {
      this.idCentury = idCentury;
    }

    @Override
    public void run() {
      loadCenturyByIdCenturySync(idCentury);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postAuthorByIdAuthorDataLoadedEvent(Century centuryByIdCentury,int idCentury) {
    //  Log.e(TAG, "postAuthorByIdAuthorDataLoadedEvent() called as found Author with " +
    //    authorByIdAuthorLoadedEvent.getAuthorByIdAuthor().getQuotes().size() + " elements and idAuthor="+idAuthor);
    CenturyEventBus century = new CenturyEventBus(centuryByIdCentury);


    if(centuryByIdCenturyLoadedEvent==null){
      centuryByIdCenturyLoadedEvent= new CenturyByIdCenturyLoadedEvent(century,idCentury);
    }else{
      centuryByIdCenturyLoadedEvent.setCenturyByIdCentury(century);
      centuryByIdCenturyLoadedEvent.setIdCentury(idCentury);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + centuryByIdCenturyLoadedEvent.getCenturyByIdCentury() + " elements and idCentury ="+centuryByIdCenturyLoadedEvent.getIdCentury());
    EventBus.getDefault().post(centuryByIdCenturyLoadedEvent);
  }

}
