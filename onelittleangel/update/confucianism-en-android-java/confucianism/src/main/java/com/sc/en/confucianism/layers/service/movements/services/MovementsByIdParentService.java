package com.sc.en.confucianism.layers.service.movements.services;

import android.util.SparseArray;

import com.sc.en.confucianism.OnelittleAngelApplication;
import com.sc.en.confucianism.layers.dao.movements.MovementsDaoInterface;
import com.sc.en.confucianism.layers.service.MotherBusinessService;
import com.sc.en.confucianism.layers.service.movements.interfaces.MovementsByIdParentServiceInterface;
import com.sc.en.confucianism.transverse.eventbus.models.MovementEventBus;
import com.sc.en.confucianism.transverse.orms.realm.models.Movement;
import com.sc.en.confucianism.transverse.eventbus.events.movements.MovementsByIdParentLoadedEvent;
import com.sc.en.confucianism.injector.Injector;
import com.sc.en.confucianism.layers.service.ServiceManagerInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MovementsByIdParentService extends MotherBusinessService implements MovementsByIdParentServiceInterface {

  private static final String TAG = "MovementByIdParentService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Movement>> movementsByIdParentList = null;

  /**
   *
   */
  private MovementsByIdParentLoadedEvent movementsByIdParentLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public MovementsByIdParentService(ServiceManagerInterface srvManager) {
    super(srvManager);
    movementsByIdParentList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    movementsByIdParentList = null;
  }

  /**
   * @param idParent
   */
  @Override
  public void loadMovementsByIdParentAsync(int idParent) {
    //  Log.e(TAG, "loadMovementByIdParentAsync() called with: " + "cityId = [" + idParent + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (movementsByIdParentList.get(idParent)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postMovementsByIdParentDataLoadedEvent(movementsByIdParentList.get(idParent),idParent);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoMovementsByIdParentLoadRunnable(idParent));
    }
  }

  private void loadMovementByIdParentSync(int idParent){
    // Log.e(TAG, "loadMovementByIdParentSync() called with: " + "idParent = [" + idParent + "]");
    // Load data from database
    /*

   */
    MovementsDaoInterface movementsDaoInterface = Injector.getDaoManager().getMovementsDao();
    List<Movement> movementsByIdParent = movementsDaoInterface.findMovementsByIdParent(idParent);
    movementsDaoInterface = null;
    //update your own cache
    movementsByIdParentList.put(idParent,movementsByIdParent);
    //send send back the answer using eventBus
    postMovementsByIdParentDataLoadedEvent(movementsByIdParent,idParent);
  }

  /**
   * @movement Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoMovementsByIdParentLoadRunnable implements Runnable {
    final int idParent;

    public DaoMovementsByIdParentLoadRunnable(int idParent) {
      this.idParent = idParent;
    }

    @Override
    public void run() {
      loadMovementByIdParentSync(idParent);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postMovementsByIdParentDataLoadedEvent(List<Movement> movementsByIdParent,int idParent) {
    //  Log.e(TAG, "postAuthorByIdParentDataLoadedEvent() called as found Author with " +
    //    movementByIdParentLoadedEvent.getAuthorByIdParent().getQuotes().size() + " elements and idParent="+idParent);
    List<MovementEventBus> movements = new ArrayList<>();

    for(Movement m : movementsByIdParent){
      movements.add(new MovementEventBus(m));
    }

    if(movementsByIdParentLoadedEvent==null){
      movementsByIdParentLoadedEvent= new MovementsByIdParentLoadedEvent(movements, idParent);
    }else{
      movementsByIdParentLoadedEvent.setMovementsByIdParent(movements);
      movementsByIdParentLoadedEvent.setIdParent(idParent);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + movementByIdParentLoadedEvent.getAuthorByIdParent() + " elements and idParent ="+movementByIdParentLoadedEvent.getIdParent());
    EventBus.getDefault().post(movementsByIdParentLoadedEvent);
  }

}
