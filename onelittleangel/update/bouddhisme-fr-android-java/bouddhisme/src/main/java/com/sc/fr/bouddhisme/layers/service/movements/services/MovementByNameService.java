package com.sc.fr.bouddhisme.layers.service.movements.services;

import android.support.v4.util.ArrayMap;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessService;
import com.sc.fr.bouddhisme.layers.service.movements.interfaces.MovementByNameServiceInterface;
import com.sc.fr.bouddhisme.transverse.eventbus.events.movements.MovementByNameLoadedEvent;
import com.sc.fr.bouddhisme.transverse.eventbus.models.MovementEventBus;
import com.sc.fr.bouddhisme.injector.Injector;
import com.sc.fr.bouddhisme.layers.dao.movements.MovementsDaoInterface;
import com.sc.fr.bouddhisme.layers.service.ServiceManagerInterface;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Movement;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;

public class MovementByNameService extends MotherBusinessService implements MovementByNameServiceInterface {

  private static final String TAG = "MovementByNameService";

  /**
   * The authors to display (the cache)
   */
  private ArrayMap<String,Movement> movementsByNameList = null;

  /**
   *
   */
  private MovementByNameLoadedEvent movementByNameLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public MovementByNameService(ServiceManagerInterface srvManager) {
    super(srvManager);
    movementsByNameList = new ArrayMap<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    movementsByNameList = null;
  }

  /**
   * @param name
   */
  @Override
  public Observable<Movement> loadMovementByNameAsync(String name) {
    //  Log.e(TAG, "LoadMovementByNameAsync() called with: " + "cityId = [" + name + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (movementsByNameList.get(name)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      //postMovementByNameDataLoadedEvent(movementsByNameList.get(name),name);
      return Observable.just(movementsByNameList.get(name));
    } else {
      // then launch it
    //  OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoMovementByNameLoadRunnable(name));
      return loadMovementByNameSync(name);
    }
  }

  private Observable<Movement> loadMovementByNameSync(String name){
    // Log.e(TAG, "LoadMovementByNameSync() called with: " + "name = [" + name + "]");
    // Load data from database
    /*

   */
    MovementsDaoInterface movementsDaoInterface = Injector.getDaoManager().getMovementsDao();
    Movement movementByName = movementsDaoInterface.findMovementByName(name);
    movementsDaoInterface = null;
    //update your own cache
    movementsByNameList.put(name,movementByName);
    //send send back the answer using eventBus
  //  postMovementByNameDataLoadedEvent(movementByName,name);
    return Observable.just(movementByName);
  }

  /**
   * @movement Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoMovementByNameLoadRunnable implements Runnable {
    final String name;

    public DaoMovementByNameLoadRunnable(String name) {
      this.name = name;
    }

    @Override
    public void run() {
      loadMovementByNameSync(name);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postMovementByNameDataLoadedEvent(Movement movementByName,String name) {
    //  Log.e(TAG, "postAuthorByNameDataLoadedEvent() called as found Author with " +
    //    movementByNameLoadedEvent.getAuthorByName().getQuotes().size() + " elements and name="+name);
    MovementEventBus movement = new MovementEventBus(movementByName);


    if(movementByNameLoadedEvent==null){
      movementByNameLoadedEvent= new MovementByNameLoadedEvent(movement, name);
    }else{
      movementByNameLoadedEvent.setMovementByName(movement);
      movementByNameLoadedEvent.setName(name);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + movementByNameLoadedEvent.getAuthorByName() + " elements and name ="+movementByNameLoadedEvent.getName());
    EventBus.getDefault().post(movementByNameLoadedEvent);
  }

}
