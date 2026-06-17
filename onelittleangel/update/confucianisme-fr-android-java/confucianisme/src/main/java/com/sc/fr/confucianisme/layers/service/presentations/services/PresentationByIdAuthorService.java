package com.sc.fr.confucianisme.layers.service.presentations.services;

import android.util.SparseArray;

import com.sc.fr.confucianisme.OnelittleAngelApplication;
import com.sc.fr.confucianisme.layers.service.MotherBusinessService;
import com.sc.fr.confucianisme.layers.service.ServiceManagerInterface;
import com.sc.fr.confucianisme.transverse.eventbus.events.presentations.PresentationByIdAuthorLoadedEvent;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Presentation;
import com.sc.fr.confucianisme.injector.Injector;
import com.sc.fr.confucianisme.layers.dao.presentations.PresentationsDaoInterface;
import com.sc.fr.confucianisme.layers.service.presentations.interfaces.PresentationByIdAuthorServiceInterface;
import com.sc.fr.confucianisme.transverse.eventbus.models.PresentationEventBus;

import org.greenrobot.eventbus.EventBus;

public class PresentationByIdAuthorService extends MotherBusinessService implements PresentationByIdAuthorServiceInterface {

  private static final String TAG = "PresentationByIdAuthorService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Presentation> presentationsByIdAuthorList = null;

  /**
   *
   */
  private PresentationByIdAuthorLoadedEvent presentationByIdAuthorLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public PresentationByIdAuthorService(ServiceManagerInterface srvManager) {
    super(srvManager);
    presentationsByIdAuthorList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param idAuthor
   */
  @Override
  public void loadPresentationByIdAuthorAsync(int idAuthor) {
    //  Log.e(TAG, "loadAuthorByIdAuthorAsync() called with: " + "cityId = [" + idAuthor + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (presentationsByIdAuthorList.get(idAuthor)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postPresentationByIdAuthorDataLoadedEvent(presentationsByIdAuthorList.get(idAuthor),idAuthor);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoPresentationByIdAuthorLoadRunnable(idAuthor));
    }
  }

  private void loadAuthorByIdAuthorSync(int idAuthor){
    // Log.e(TAG, "loadAuthorByIdAuthorSync() called with: " + "idAuthor = [" + idAuthor + "]");
    // Load data from database
    /*

   */
    PresentationsDaoInterface presentationsDaoInterface = Injector.getDaoManager().getPresentationsDao();
    Presentation presentationByIdAuthor = presentationsDaoInterface.findPresentationByIdAuthor(idAuthor);
    presentationsDaoInterface = null;
    //update your own cache
    presentationsByIdAuthorList.put(idAuthor,presentationByIdAuthor);
    //send send back the answer using eventBus
    postPresentationByIdAuthorDataLoadedEvent(presentationByIdAuthor,idAuthor);
  }

  /**
   * @presentation Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoPresentationByIdAuthorLoadRunnable implements Runnable {
    final int idAuthor;

    public DaoPresentationByIdAuthorLoadRunnable(int idAuthor) {
      this.idAuthor = idAuthor;
    }

    @Override
    public void run() {
      loadAuthorByIdAuthorSync(idAuthor);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postPresentationByIdAuthorDataLoadedEvent(Presentation presentationByIdAuthor,int idAuthor) {
    //  Log.e(TAG, "postAuthorByIdAuthorDataLoadedEvent() called as found Author with " +
    //    presentationByIdAuthorLoadedEvent.getAuthorByIdAuthor().getQuotes().size() + " elements and idAuthor="+idAuthor);
    PresentationEventBus presentation = new PresentationEventBus(presentationByIdAuthor);


    if(presentationByIdAuthorLoadedEvent==null){
      presentationByIdAuthorLoadedEvent= new PresentationByIdAuthorLoadedEvent(idAuthor, presentation);
    }else{
      presentationByIdAuthorLoadedEvent.setPresentationByIdAuthor(presentation);
      presentationByIdAuthorLoadedEvent.setIdAuthor(idAuthor);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + presentationByIdAuthorLoadedEvent.getAuthorByIdAuthor() + " elements and idAuthor ="+presentationByIdAuthorLoadedEvent.getIdAuthor());
    EventBus.getDefault().post(presentationByIdAuthorLoadedEvent);
  }

}
