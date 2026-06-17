package com.sc.en.taoism.layers.service.presentations.services;

import android.util.SparseArray;

import com.sc.en.taoism.OnelittleAngelApplication;
import com.sc.en.taoism.layers.dao.presentations.PresentationsDaoInterface;
import com.sc.en.taoism.layers.service.MotherBusinessService;
import com.sc.en.taoism.layers.service.presentations.interfaces.PresentationByIdMovementServiceInterface;
import com.sc.en.taoism.transverse.eventbus.events.presentations.PresentationByIdMovementLoadedEvent;
import com.sc.en.taoism.transverse.eventbus.models.PresentationEventBus;
import com.sc.en.taoism.transverse.orms.realm.models.Presentation;
import com.sc.en.taoism.injector.Injector;
import com.sc.en.taoism.layers.service.ServiceManagerInterface;

import org.greenrobot.eventbus.EventBus;

public class PresentationByIdMovementService extends MotherBusinessService implements PresentationByIdMovementServiceInterface {

  private static final String TAG = "PresentationByIdMovementService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Presentation> presentationsByIdMovementList = null;

  /**
   *
   */
  private PresentationByIdMovementLoadedEvent presentationByIdMovementLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public PresentationByIdMovementService(ServiceManagerInterface srvManager) {
    super(srvManager);
    presentationsByIdMovementList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param idMovement
   */
  @Override
  public void loadPresentationByIdMovementAsync(int idMovement) {
    //  Log.e(TAG, "loadPresentationByIdMovementAsync() called with: " + "cityId = [" + idMovement + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (presentationsByIdMovementList.get(idMovement)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postPresentationByIdMovementDataLoadedEvent(presentationsByIdMovementList.get(idMovement),idMovement);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new PresentationByIdMovementService.DaoPresentationByIdMovementLoadRunnable(idMovement));
    }
  }

  private void loadPresentationByIdMovementSync(int idMovement){
    // Log.e(TAG, "loadPresentationByIdMovementSync() called with: " + "idMovement = [" + idMovement + "]");
    // Load data from database
    /*

   */
    PresentationsDaoInterface presentationsDaoInterface = Injector.getDaoManager().getPresentationsDao();
    Presentation presentationByIdMovement = presentationsDaoInterface.findPresentationByIdMovement(idMovement);
    presentationsDaoInterface = null;
    //update your own cache
    presentationsByIdMovementList.put(idMovement,presentationByIdMovement);
    //send send back the answer using eventBus
    postPresentationByIdMovementDataLoadedEvent(presentationByIdMovement,idMovement);
  }

  /**
   * @presentation Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoPresentationByIdMovementLoadRunnable implements Runnable {
    final int idMovement;

    public DaoPresentationByIdMovementLoadRunnable(int idMovement) {
      this.idMovement = idMovement;
    }

    @Override
    public void run() {
      loadPresentationByIdMovementSync(idMovement);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postPresentationByIdMovementDataLoadedEvent(Presentation presentationByIdMovement,int idMovement) {
    //  Log.e(TAG, "postPresentationByIdMovementDataLoadedEvent() called as found Presentation with " +
    //    presentationByIdMovementLoadedEvent.getPresentationByIdMovement().getQuotes().size() + " elements and idMovement="+idMovement);
    PresentationEventBus presentation = new PresentationEventBus(presentationByIdMovement);


    if(presentationByIdMovementLoadedEvent==null){
      presentationByIdMovementLoadedEvent= new PresentationByIdMovementLoadedEvent(presentation, idMovement);
    }else{
      presentationByIdMovementLoadedEvent.setPresentationByIdMovement(presentation);
      presentationByIdMovementLoadedEvent.setIdMovement(idMovement);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + presentationByIdMovementLoadedEvent.getPresentationByIdMovement() + " elements and idMovement ="+presentationByIdMovementLoadedEvent.getIdMovement());
    EventBus.getDefault().post(presentationByIdMovementLoadedEvent);
  }

}
