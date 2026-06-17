package com.sc.en.quotes.layers.service.centuries.services;

import android.support.v4.util.ArrayMap;

import com.sc.en.quotes.layers.dao.centuries.CenturiesDaoInterface;
import com.sc.en.quotes.layers.service.MotherBusinessService;
import com.sc.en.quotes.layers.service.centuries.interfaces.CenturyByNameServiceInterface;
import com.sc.en.quotes.transverse.eventbus.events.centuries.CenturyByNameLoadedEvent;
import com.sc.en.quotes.transverse.eventbus.models.CenturyEventBus;
import com.sc.en.quotes.transverse.orms.realm.models.Century;
import com.sc.en.quotes.injector.Injector;
import com.sc.en.quotes.OnelittleAngelApplication;
import com.sc.en.quotes.layers.service.ServiceManagerInterface;

import org.greenrobot.eventbus.EventBus;

public class CenturyByNameService extends MotherBusinessService implements CenturyByNameServiceInterface {

  private static final String TAG = "CenturyByNameService";

  /**
   * The authors to display (the cache)
   */
  private ArrayMap<String,Century> centuriesByNameList = null;

  /**
   *
   */
  private CenturyByNameLoadedEvent centuryByNameLoadedEvent;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public CenturyByNameService(ServiceManagerInterface srvManager) {
    super(srvManager);
    centuriesByNameList = new ArrayMap<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param name
   */
  @Override
  public void loadCenturyByNameAsync(String name) {
    //  Log.e(TAG, "loadCenturyByNameAsync() called with: " + "cityId = [" + name + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (centuriesByNameList.get(name)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postCenturyByNameDataLoadedEvent(centuriesByNameList.get(name),name);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoCenturyByNameLoadRunnable(name));
    }
  }

  private void loadCenturyByNameSync(String name){
    // Log.e(TAG, "loadCenturyByNameSync() called with: " + "name = [" + name + "]");
    // Load data from database
    /*

   */
    CenturiesDaoInterface centuriesDaoInterface = Injector.getDaoManager().getCenturiesDao();
    Century centuryByName = centuriesDaoInterface.findCenturyByName(name);
    centuriesDaoInterface = null;
    //update your own cache
    centuriesByNameList.put(name,centuryByName);
    //send send back the answer using eventBus
    postCenturyByNameDataLoadedEvent(centuryByName,name);
  }

  /**
   * @author Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoCenturyByNameLoadRunnable implements Runnable {
    final String name;

    public DaoCenturyByNameLoadRunnable(String name) {
      this.name = name;
    }

    @Override
    public void run() {
      loadCenturyByNameSync(name);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postCenturyByNameDataLoadedEvent(Century centuryByName,String name) {
    //  Log.e(TAG, "postAuthorByNameDataLoadedEvent() called as found Author with " +
    //    authorByNameLoadedEvent.getAuthorByName().getQuotes().size() + " elements and name="+name);
    CenturyEventBus century = new CenturyEventBus(centuryByName);


    if(centuryByNameLoadedEvent==null){
      centuryByNameLoadedEvent= new CenturyByNameLoadedEvent(century, name);
    }else{
      centuryByNameLoadedEvent.setCenturyByName(century);
      centuryByNameLoadedEvent.setName(name);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + centuryByNameLoadedEvent.getAuthorByName() + " elements and name ="+centuryByNameLoadedEvent.getName());
    EventBus.getDefault().post(centuryByNameLoadedEvent);
  }

}
