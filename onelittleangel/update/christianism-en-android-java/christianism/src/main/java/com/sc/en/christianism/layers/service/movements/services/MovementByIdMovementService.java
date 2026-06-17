package com.sc.en.christianism.layers.service.movements.services;

import android.util.SparseArray;

import com.sc.en.christianism.OnelittleAngelApplication;
import com.sc.en.christianism.layers.dao.movements.MovementsDaoInterface;
import com.sc.en.christianism.layers.service.ServiceManagerInterface;
import com.sc.en.christianism.layers.service.movements.interfaces.MovementByIdMovementServiceInterface;
import com.sc.en.christianism.transverse.eventbus.events.movements.MovementByIdMovementLoadedEvent;
import com.sc.en.christianism.injector.Injector;
import com.sc.en.christianism.layers.service.MotherBusinessService;
import com.sc.en.christianism.transverse.eventbus.models.MovementEventBus;
import com.sc.en.christianism.transverse.orms.realm.models.Movement;

import org.greenrobot.eventbus.EventBus;

public class MovementByIdMovementService extends MotherBusinessService implements MovementByIdMovementServiceInterface {

  private static final String TAG = "MovementByIdMovementService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Movement> movementsByIdMovementList = null;

  /**
   *
   */
  private MovementByIdMovementLoadedEvent movementByIdMovementLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public MovementByIdMovementService(ServiceManagerInterface srvManager) {
    super(srvManager);
    movementsByIdMovementList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    movementsByIdMovementList = null;
  }

  /**
   * @param idMovement
   */
  @Override
  public void loadMovementByIdMovementAsync(int idMovement) {
    //  Log.e(TAG, "loadMovementByIdMovementAsync() called with: " + "cityId = [" + idMovement + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (movementsByIdMovementList.get(idMovement)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postAuthorByIdMovementDataLoadedEvent(movementsByIdMovementList.get(idMovement),idMovement);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoMovementByIdMovementLoadRunnable(idMovement));
    }
  }

  private void loadMovementByIdMovementSync(int idMovement){
    // Log.e(TAG, "loadMovementByIdMovementSync() called with: " + "idMovement = [" + idMovement + "]");
    // Load data from database
    /*

   */
    MovementsDaoInterface movementsDaoInterface = Injector.getDaoManager().getMovementsDao();
    Movement movementByIdMovement = movementsDaoInterface.findMovementByIdMovement(idMovement);
    movementsDaoInterface = null;
    //update your own cache
    movementsByIdMovementList.put(idMovement,movementByIdMovement);
    //send send back the answer using eventBus
    postAuthorByIdMovementDataLoadedEvent(movementByIdMovement,idMovement);
  }

  /**
   * @movement Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoMovementByIdMovementLoadRunnable implements Runnable {
    final int idMovement;

    public DaoMovementByIdMovementLoadRunnable(int idMovement) {
      this.idMovement = idMovement;
    }

    @Override
    public void run() {
      loadMovementByIdMovementSync(idMovement);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postAuthorByIdMovementDataLoadedEvent(Movement movementByIdMovement,int idMovement) {
    //  Log.e(TAG, "postAuthorByIdMovementDataLoadedEvent() called as found Author with " +
    //    movementByIdMovementLoadedEvent.getAuthorByIdMovement().getQuotes().size() + " elements and idMovement="+idMovement);
    MovementEventBus movement = new MovementEventBus(movementByIdMovement);

    if(movementByIdMovementLoadedEvent==null){
      movementByIdMovementLoadedEvent= new MovementByIdMovementLoadedEvent(movement,idMovement);
    }else{
      movementByIdMovementLoadedEvent.setMovementByIdMovement(movement);
      movementByIdMovementLoadedEvent.setIdMovement(idMovement);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + movementByIdMovementLoadedEvent.getAuthorByIdMovement() + " elements and idMovement ="+movementByIdMovementLoadedEvent.getIdMovement());
    EventBus.getDefault().post(movementByIdMovementLoadedEvent);
  }

}
