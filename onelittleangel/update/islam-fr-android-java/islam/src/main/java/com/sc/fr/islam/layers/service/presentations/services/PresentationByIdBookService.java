package com.sc.fr.islam.layers.service.presentations.services;

import android.util.SparseArray;

import com.sc.fr.islam.OnelittleAngelApplication;
import com.sc.fr.islam.layers.dao.presentations.PresentationsDaoInterface;
import com.sc.fr.islam.layers.service.presentations.interfaces.PresentationByIdBookServiceInterface;
import com.sc.fr.islam.transverse.eventbus.models.PresentationEventBus;
import com.sc.fr.islam.transverse.orms.realm.models.Presentation;
import com.sc.fr.islam.injector.Injector;
import com.sc.fr.islam.layers.service.MotherBusinessService;
import com.sc.fr.islam.layers.service.ServiceManagerInterface;
import com.sc.fr.islam.transverse.eventbus.events.presentations.PresentationByIdBookLoadedEvent;

import org.greenrobot.eventbus.EventBus;

public class PresentationByIdBookService extends MotherBusinessService implements PresentationByIdBookServiceInterface {

  private static final String TAG = "PresentationByIdBookService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Presentation> presentationsByIdBookList = null;

  /**
   *
   */
  private PresentationByIdBookLoadedEvent presentationByIdBookLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public PresentationByIdBookService(ServiceManagerInterface srvManager) {
    super(srvManager);
    presentationsByIdBookList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param idBook
   */
  @Override
  public void loadPresentationByIdBookAsync(int idBook) {
    //  Log.e(TAG, "loadPresentationByIdBookAsync() called with: " + "cityId = [" + idBook + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (presentationsByIdBookList.get(idBook)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postPresentationByIdBookDataLoadedEvent(presentationsByIdBookList.get(idBook),idBook);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new PresentationByIdBookService.DaoPresentationByIdBookLoadRunnable(idBook));
    }
  }

  private void loadPresentationByIdBookSync(int idBook){
    // Log.e(TAG, "loadPresentationByIdBookSync() called with: " + "idBook = [" + idBook + "]");
    // Load data from database
    /*

   */
    PresentationsDaoInterface presentationsDaoInterface = Injector.getDaoManager().getPresentationsDao();
    Presentation presentationByIdBook = presentationsDaoInterface.findPresentationByIdBook(idBook);
    presentationsDaoInterface = null;
    //update your own cache
    presentationsByIdBookList.put(idBook,presentationByIdBook);
    //send send back the answer using eventBus
    postPresentationByIdBookDataLoadedEvent(presentationByIdBook,idBook);
  }

  /**
   * @presentation Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoPresentationByIdBookLoadRunnable implements Runnable {
    final int idBook;

    public DaoPresentationByIdBookLoadRunnable(int idBook) {
      this.idBook = idBook;
    }

    @Override
    public void run() {
      loadPresentationByIdBookSync(idBook);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postPresentationByIdBookDataLoadedEvent(Presentation presentationByIdBook,int idBook) {
    //  Log.e(TAG, "postPresentationByIdBookDataLoadedEvent() called as found Presentation with " +
    //    presentationByIdBookLoadedEvent.getPresentationByIdBook().getQuotes().size() + " elements and idBook="+idBook);
    PresentationEventBus presentation = new PresentationEventBus(presentationByIdBook);

    if(presentationByIdBookLoadedEvent==null){
      presentationByIdBookLoadedEvent= new PresentationByIdBookLoadedEvent(presentation, idBook);
    }else{
      presentationByIdBookLoadedEvent.setPresentationByIdBook(presentation);
      presentationByIdBookLoadedEvent.setIdBook(idBook);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + presentationByIdBookLoadedEvent.getPresentationByIdBook() + " elements and idBook ="+presentationByIdBookLoadedEvent.getIdBook());
    EventBus.getDefault().post(presentationByIdBookLoadedEvent);
  }

}
