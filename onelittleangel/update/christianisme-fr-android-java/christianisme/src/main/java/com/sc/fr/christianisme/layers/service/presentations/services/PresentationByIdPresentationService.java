package com.sc.fr.christianisme.layers.service.presentations.services;

import android.util.SparseArray;

import com.sc.fr.christianisme.OnelittleAngelApplication;
import com.sc.fr.christianisme.layers.dao.presentations.PresentationsDaoInterface;
import com.sc.fr.christianisme.layers.service.MotherBusinessService;
import com.sc.fr.christianisme.layers.service.ServiceManagerInterface;
import com.sc.fr.christianisme.layers.service.presentations.interfaces.PresentationByIdPresentationServiceInterface;
import com.sc.fr.christianisme.transverse.eventbus.models.PresentationEventBus;
import com.sc.fr.christianisme.injector.Injector;
import com.sc.fr.christianisme.transverse.eventbus.events.presentations.PresentationByIdPresentationLoadedEvent;
import com.sc.fr.christianisme.transverse.orms.realm.models.Presentation;

import org.greenrobot.eventbus.EventBus;

public class PresentationByIdPresentationService extends MotherBusinessService implements PresentationByIdPresentationServiceInterface {

  private static final String TAG = "PresentationByIdPresentationService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Presentation> presentationsByIdPresentationList = null;

  /**
   *
   */
  private PresentationByIdPresentationLoadedEvent presentationByIdPresentationLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public PresentationByIdPresentationService(ServiceManagerInterface srvManager) {
    super(srvManager);
    presentationsByIdPresentationList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param idPresentation
   */
  @Override
  public void loadPresentationByIdPresentationAsync(int idPresentation) {
    //  Log.e(TAG, "loadPresentationByIdPresentationAsync() called with: " + "cityId = [" + idPresentation + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (presentationsByIdPresentationList.get(idPresentation)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postPresentationByIdPresentationDataLoadedEvent(presentationsByIdPresentationList.get(idPresentation),idPresentation);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoPresentationByIdPresentationLoadRunnable(idPresentation));
    }
  }

  private void loadPresentationByIdPresentationSync(int idPresentation){
    // Log.e(TAG, "loadPresentationByIdPresentationSync() called with: " + "idPresentation = [" + idPresentation + "]");
    // Load data from database
    /*

   */
    PresentationsDaoInterface presentationsDaoInterface = Injector.getDaoManager().getPresentationsDao();
    Presentation presentationByIdPresentation = presentationsDaoInterface.findPresentationByIdPresentation(idPresentation);
    presentationsDaoInterface = null;
    //update your own cache
    presentationsByIdPresentationList.put(idPresentation,presentationByIdPresentation);
    //send send back the answer using eventBus
    postPresentationByIdPresentationDataLoadedEvent(presentationByIdPresentation,idPresentation);
  }

  /**
   * @presentation Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoPresentationByIdPresentationLoadRunnable implements Runnable {
    final int idPresentation;

    public DaoPresentationByIdPresentationLoadRunnable(int idPresentation) {
      this.idPresentation = idPresentation;
    }

    @Override
    public void run() {
      loadPresentationByIdPresentationSync(idPresentation);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postPresentationByIdPresentationDataLoadedEvent(Presentation presentationByIdPresentation,int idPresentation) {
    //  Log.e(TAG, "postPresentationByIdPresentationDataLoadedEvent() called as found Presentation with " +
    //    presentationByIdPresentationLoadedEvent.getPresentationByIdPresentation().getQuotes().size() + " elements and idPresentation="+idPresentation);
    PresentationEventBus presentation = new PresentationEventBus(presentationByIdPresentation);

    if(presentationByIdPresentationLoadedEvent==null){
      presentationByIdPresentationLoadedEvent= new PresentationByIdPresentationLoadedEvent(presentation,idPresentation);
    }else{
      presentationByIdPresentationLoadedEvent.setPresentationByIdPresentation(presentation);
      presentationByIdPresentationLoadedEvent.setIdPresentation(idPresentation);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + presentationByIdPresentationLoadedEvent.getPresentationByIdPresentation() + " elements and idPresentation ="+presentationByIdPresentationLoadedEvent.getIdPresentation());
    EventBus.getDefault().post(presentationByIdPresentationLoadedEvent);
  }

}
