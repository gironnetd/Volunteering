package com.sc.en.islam.layers.service.centuries.services;

import android.util.Log;
import android.util.SparseArray;

import com.sc.en.islam.OnelittleAngelApplication;
import com.sc.en.islam.layers.service.ServiceManagerInterface;
import com.sc.en.islam.layers.service.centuries.interfaces.CenturiesAllServiceInterface;
import com.sc.en.islam.transverse.eventbus.models.CenturyEventBus;
import com.sc.en.islam.transverse.orms.realm.models.Century;
import com.sc.en.islam.layers.service.MotherBusinessService;
import com.sc.en.islam.injector.Injector;
import com.sc.en.islam.layers.dao.centuries.CenturiesDaoInterface;
import com.sc.en.islam.transverse.eventbus.events.centuries.CenturiesAllLoadedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class CenturiesAllService extends MotherBusinessService implements CenturiesAllServiceInterface {


  private static final String TAG = "CenturiesAllService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Century> centuriesList = null;
  /**
   *
   */
  private CenturiesAllServiceInterface centuriesAllServiceInterface = null;

  /**
   *
   */
  private CenturiesAllLoadedEvent centuriesAllLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public CenturiesAllService(ServiceManagerInterface srvManager) {
    super(srvManager);
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * Clean your resource when your service die
   *
   * @param srvManager
   */
  @Override
  public void onDestroy(ServiceManagerInterface srvManager) {
    super.onDestroy(srvManager);
  }

  /**
   * Load all the authors from the database asynchronously
   */
  @Override
  public void loadAllCenturiesAsync() {
    //Log.d(TAG, "loadCenturys() called with: " + "");
    OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindAllCenturiesRunnable);
  }

  private void loadAllCenturiesSync(){
    // Load data from DB
    CenturiesDaoInterface centuriesDaoInterface = Injector.getDaoManager().getCenturiesDao();
    //send back the answer using eventBus
    postAllCenturysLoadedEvent(centuriesDaoInterface.findAllCenturies());
    centuriesDaoInterface =null;
  }

  /**
   * The runnable to execute
   */
  private final DaoFindAllCenturiesRunnable daoFindAllCenturiesRunnable = new DaoFindAllCenturiesRunnable();
  /**
   * @author Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable
   */
  private class DaoFindAllCenturiesRunnable implements Runnable {
    @Override
    public void run() {
      loadAllCenturiesSync();
    }
  }

  /**
   * Broadcast all the centuries Loaded event
   */
  private void postAllCenturysLoadedEvent(List<Century> centuriesAll) {

    List<CenturyEventBus> centuries = new ArrayList<>();

    for(Century a : centuriesAll){
      centuries.add(new CenturyEventBus(a));
    }

    if(centuriesAllLoadedEvent ==null){
      centuriesAllLoadedEvent = new CenturiesAllLoadedEvent(centuries);
    }else{
      centuriesAllLoadedEvent.setCenturies(centuries);
    }
    //Log.e(TAG, "postCitiesLoadedEvent posted" );
    EventBus.getDefault().post(centuriesAllLoadedEvent);
  }
}
