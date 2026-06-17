package com.sc.fr.christianisme.layers.service.presentations.services;

import android.util.Log;
import android.util.SparseArray;

import com.sc.fr.christianisme.OnelittleAngelApplication;
import com.sc.fr.christianisme.layers.dao.presentations.PresentationsDaoInterface;
import com.sc.fr.christianisme.layers.service.MotherBusinessService;
import com.sc.fr.christianisme.layers.service.ServiceManagerInterface;
import com.sc.fr.christianisme.transverse.eventbus.models.PresentationEventBus;
import com.sc.fr.christianisme.injector.Injector;
import com.sc.fr.christianisme.layers.service.presentations.interfaces.PresentationsAllServiceInterface;
import com.sc.fr.christianisme.transverse.eventbus.events.presentations.PresentationsAllLoadedEvent;
import com.sc.fr.christianisme.transverse.orms.realm.models.Presentation;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class PresentationsAllService extends MotherBusinessService implements PresentationsAllServiceInterface {

  private static final String TAG = "PresentationsAllService";

  /**
   *
   */
  private PresentationsAllServiceInterface presentationsAllServiceInterface = null;

  /**
   *
   */
  private PresentationsAllLoadedEvent presentationsAllLoadedEvent;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public PresentationsAllService(ServiceManagerInterface srvManager) {
    super(srvManager);
    /*
    The presentations to display (the cache)
   */
    SparseArray<Presentation> presentationsList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * Load all the presentations from the database asynchronously
   */
  @Override
  public void loadAllPresentationsAsync() {
    //Log.d(TAG, "loadPresentations() called with: " + "");
    OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindAllPresentationsRunnable);
  }

  private void loadAllPresentationsSync(){
    // Load data from DB
    PresentationsDaoInterface presentationsDaoInterface = Injector.getDaoManager().getPresentationsDao();
    //send back the answer using eventBus
    postAllPresentationsLoadedEvent(presentationsDaoInterface.findAllPresentations());
    presentationsDaoInterface =null;
  }

  /**
   * The runnable to execute
   */
  private final DaoFindAllPresentationsRunnable daoFindAllPresentationsRunnable = new DaoFindAllPresentationsRunnable();
  /**
   * @presentation Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable
   */
  private class DaoFindAllPresentationsRunnable implements Runnable {
    @Override
    public void run() {
      loadAllPresentationsSync();
    }
  }

  /**
   * Broadcast all the presentations Loaded event
   */
  private void postAllPresentationsLoadedEvent(List<Presentation> presentationsAll) {

    List<PresentationEventBus> presentations = new ArrayList<>();

    for(Presentation a : presentationsAll){
      presentations.add(new PresentationEventBus(a));
    }

    if(presentationsAllLoadedEvent ==null){
      presentationsAllLoadedEvent = new PresentationsAllLoadedEvent(presentations);
    }else{
      presentationsAllLoadedEvent.setPresentations(presentations);
    }
    //Log.e(TAG, "postCitiesLoadedEvent posted" );
    EventBus.getDefault().post(presentationsAllLoadedEvent);
  }
}
