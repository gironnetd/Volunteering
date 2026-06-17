package com.sc.en.onelittleangel.layers.service.authors.services;

import android.util.SparseArray;

import com.sc.en.onelittleangel.layers.service.MotherBusinessService;
import com.sc.en.onelittleangel.transverse.orms.realm.models.Author;
import com.sc.en.onelittleangel.injector.Injector;
import com.sc.en.onelittleangel.OnelittleAngelApplication;
import com.sc.en.onelittleangel.layers.dao.authors.AuthorsDaoInterface;
import com.sc.en.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.en.onelittleangel.layers.service.authors.interfaces.AuthorByIdAuthorServiceInterface;
import com.sc.en.onelittleangel.transverse.eventbus.events.authors.AuthorByIdAuthorLoadedEvent;
import com.sc.en.onelittleangel.transverse.eventbus.models.AuthorEventBus;

import org.greenrobot.eventbus.EventBus;

public class AuthorByIdAuthorService extends MotherBusinessService implements AuthorByIdAuthorServiceInterface {

  private static final String TAG = "AuthorsByIdAuthorService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Author> authorsByIdAuthorList = null;

  /**
   *
   */
  private AuthorByIdAuthorLoadedEvent authorByIdAuthorLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public AuthorByIdAuthorService(ServiceManagerInterface srvManager) {
    super(srvManager);
    authorsByIdAuthorList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    authorsByIdAuthorList = null;
  }

  /**
   * Find the author that match the author id in an asynchronous way
   *
   * @param idAuthor The name of the author searched
   */
  @Override
  public void loadAuthorByIdAuthorAsync(int idAuthor) {
  //  Log.e(TAG, "loadAuthorByIdAuthorAsync() called with: " + "cityId = [" + idAuthor + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (authorsByIdAuthorList.get(idAuthor)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postAuthorByIdAuthorDataLoadedEvent(authorsByIdAuthorList.get(idAuthor),idAuthor);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoAuthorByIdAuthorLoadRunnable(idAuthor));
    }
  }

  private void loadAuthorByIdAuthorSync(int idAuthor){
   // Log.e(TAG, "loadAuthorByIdAuthorSync() called with: " + "idAuthor = [" + idAuthor + "]");
    // Load data from database
    /*

   */
    AuthorsDaoInterface authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
    Author authorByIdAuthor = authorsDaoInterface.findAuthorByIdAuthor(idAuthor);
    authorsDaoInterface = null;
    //update your own cache
    authorsByIdAuthorList.put(idAuthor,authorByIdAuthor);
    //send send back the answer using eventBus
    postAuthorByIdAuthorDataLoadedEvent(authorByIdAuthor,idAuthor);
  }

  /**
   * @author Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoAuthorByIdAuthorLoadRunnable implements Runnable {
    final int idAuthor;

    public DaoAuthorByIdAuthorLoadRunnable(int idAuthor) {
      this.idAuthor = idAuthor;
    }

    @Override
    public void run() {
      loadAuthorByIdAuthorSync(idAuthor);
    }
  }

  /**********************************************************
   Updating strategy and update listening
   */
  /**
   * Brodcast the Weather loaded event
   */
  private void postAuthorByIdAuthorDataLoadedEvent(Author authorByIdAuthor,int idAuthor) {
  //  Log.e(TAG, "postAuthorByIdAuthorDataLoadedEvent() called as found Author with " +
  //    authorByIdAuthorLoadedEvent.getAuthorByIdAuthor().getQuotes().size() + " elements and idAuthor="+idAuthor);
    AuthorEventBus author = new AuthorEventBus(authorByIdAuthor);

    if(authorByIdAuthorLoadedEvent==null){
      authorByIdAuthorLoadedEvent= new AuthorByIdAuthorLoadedEvent(author,idAuthor);
    }else{
      authorByIdAuthorLoadedEvent.setAuthorByIdAuthor(author);
      authorByIdAuthorLoadedEvent.setIdAuthor(idAuthor);
    }
  //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + authorByIdAuthorLoadedEvent.getAuthorByIdAuthor() + " elements and idAuthor ="+authorByIdAuthorLoadedEvent.getIdAuthor());
    EventBus.getDefault().post(authorByIdAuthorLoadedEvent);
  }

}
