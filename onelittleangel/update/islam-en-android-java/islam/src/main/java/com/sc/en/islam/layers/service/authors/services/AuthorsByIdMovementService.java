package com.sc.en.islam.layers.service.authors.services;

import android.util.SparseArray;

import com.sc.en.islam.OnelittleAngelApplication;
import com.sc.en.islam.layers.service.MotherBusinessService;
import com.sc.en.islam.layers.service.authors.interfaces.AuthorsByIdMovementServiceInterface;
import com.sc.en.islam.transverse.eventbus.events.authors.AuthorsByIdMovementLoadedEvent;
import com.sc.en.islam.transverse.orms.realm.models.Author;
import com.sc.en.islam.injector.Injector;
import com.sc.en.islam.layers.dao.authors.AuthorsDaoInterface;
import com.sc.en.islam.layers.service.ServiceManagerInterface;
import com.sc.en.islam.transverse.eventbus.models.AuthorEventBus;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class AuthorsByIdMovementService extends MotherBusinessService implements AuthorsByIdMovementServiceInterface {

  private static final String TAG = "AuthorsByIdMovementService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Author>> authorsByIdMovementList = null;

  /**
   *
   */
  private AuthorsByIdMovementLoadedEvent authorByIdMovementLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public AuthorsByIdMovementService(ServiceManagerInterface srvManager) {
    super(srvManager);
    authorsByIdMovementList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    authorsByIdMovementList = null;
  }

  /**
   * Find authors that match the id movement in an asynchronous way
   *
   * @param idMovement The id movement of authors searched
   */
  @Override
  public void loadAuthorsByIdMovementAsync(int idMovement) {
    //  Log.e(TAG, "loadAuthorByIdMovementAsync() called with: " + "cityId = [" + idMovement + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (authorsByIdMovementList.get(idMovement)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postAuthorByIdMovementDataLoadedEvent(authorsByIdMovementList.get(idMovement),idMovement);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoAuthorByIdMovementLoadRunnable(idMovement));
    }
  }

  private void loadAuthorByIdMovementSync(int idMovement){
    // Log.e(TAG, "loadAuthorByIdMovementSync() called with: " + "idMovement = [" + idMovement + "]");
    // Load data from database
    /*

   */
    AuthorsDaoInterface authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
    List<Author> authorsByIdMovement = authorsDaoInterface.findAuthorsByIdMovement(idMovement);
    authorsDaoInterface = null;
    //update your own cache
    authorsByIdMovementList.put(idMovement,authorsByIdMovement);
    //send send back the answer using eventBus
    postAuthorByIdMovementDataLoadedEvent(authorsByIdMovement,idMovement);
  }

  /**
   * @author Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoAuthorByIdMovementLoadRunnable implements Runnable {
    final int idMovement;

    public DaoAuthorByIdMovementLoadRunnable(int idMovement) {
      this.idMovement = idMovement;
    }

    @Override
    public void run() {
      loadAuthorByIdMovementSync(idMovement);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postAuthorByIdMovementDataLoadedEvent(List<Author> authorsByIdMovement,int idMovement) {
    //  Log.e(TAG, "postAuthorByIdMovementDataLoadedEvent() called as found Author with " +
    //    authorByIdMovementLoadedEvent.getAuthorByIdMovement().getQuotes().size() + " elements and idMovement="+idMovement);
    List<AuthorEventBus> authors = new ArrayList<>();

    for(Author a : authorsByIdMovement){
      authors.add(new AuthorEventBus(a));
    }

    if(authorByIdMovementLoadedEvent==null){
      authorByIdMovementLoadedEvent= new AuthorsByIdMovementLoadedEvent(authors, idMovement);
    }else{
      authorByIdMovementLoadedEvent.setAuthorsByIdMovement(authors);
      authorByIdMovementLoadedEvent.setIdMovement(idMovement);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + authorByIdMovementLoadedEvent.getAuthorByIdMovement() + " elements and idMovement ="+authorByIdMovementLoadedEvent.getIdMovement());
    EventBus.getDefault().post(authorByIdMovementLoadedEvent);
  }

}
