package com.sc.fr.onelittleangel.layers.service.movements.services;

import android.util.SparseArray;

import com.sc.fr.onelittleangel.injector.Injector;
import com.sc.fr.onelittleangel.OnelittleAngelApplication;
import com.sc.fr.onelittleangel.layers.dao.movements.MovementsDaoInterface;
import com.sc.fr.onelittleangel.layers.service.movements.interfaces.MovementsAllServiceInterface;
import com.sc.fr.onelittleangel.layers.service.MotherBusinessService;
import com.sc.fr.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.fr.onelittleangel.transverse.eventbus.events.movements.MovementsAllLoadedEvent;
import com.sc.fr.onelittleangel.transverse.eventbus.models.MovementEventBus;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Movement;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MovementsAllService extends MotherBusinessService implements MovementsAllServiceInterface {

  private static final String TAG = "MovementsAllService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Movement> movementsList = null;

  /**
   *
   */
  private MovementsAllLoadedEvent movementsAllLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public MovementsAllService(ServiceManagerInterface srvManager) {
    super(srvManager);
    movementsList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    movementsList = null;
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
   *
   */
  @Override
  public void loadAllMovementsAsync() {
  //  Log.d(TAG, "loadAuthors() called with: " + "");
    OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindAllMovementsRunnable);

  }

  private void loadAllMovementsSync(){
    // Load data from DB
    /*

   */
    MovementsDaoInterface movementsDaoInterface = Injector.getDaoManager().getMovementsDao();
    //send back the answer using eventBus
    postAllMovementsLoadedEvent(movementsDaoInterface.findAllMovements());
    movementsDaoInterface =null;
  }

  /**
   * The runnable to execute
   */
  private final DaoFindAllMovementsRunnable daoFindAllMovementsRunnable = new DaoFindAllMovementsRunnable();
  /**
   * @author Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable
   */
  private class DaoFindAllMovementsRunnable implements Runnable {
    @Override
    public void run() {
      loadAllMovementsSync();
    }
  }

  /**
   * Broadcast all the authors Loaded event
   */
  private void postAllMovementsLoadedEvent(List<Movement> movementsAll) {

    List<MovementEventBus> movements = new ArrayList<>();

    for(Movement m : movementsAll){
      movements.add(new MovementEventBus(m));
    }

    if(movementsAllLoadedEvent ==null){
      movementsAllLoadedEvent = new MovementsAllLoadedEvent(movements);
    }else{
      movementsAllLoadedEvent.setMovements(movements);
    }
  //  Log.e(TAG, "postCitiesLoadedEvent posted" );
    EventBus.getDefault().post(movementsAllLoadedEvent);
  }
}
