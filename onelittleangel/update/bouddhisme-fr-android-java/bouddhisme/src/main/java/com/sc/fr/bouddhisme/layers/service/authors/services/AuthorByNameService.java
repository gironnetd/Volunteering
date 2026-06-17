package com.sc.fr.bouddhisme.layers.service.authors.services;

import android.support.v4.util.ArrayMap;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessService;
import com.sc.fr.bouddhisme.layers.service.authors.interfaces.AuthorByNameServiceInterface;
import com.sc.fr.bouddhisme.transverse.eventbus.events.authors.AuthorByNameLoadedEvent;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Author;
import com.sc.fr.bouddhisme.injector.Injector;
import com.sc.fr.bouddhisme.layers.dao.authors.AuthorsDaoInterface;
import com.sc.fr.bouddhisme.layers.service.ServiceManagerInterface;

import io.reactivex.Observable;

public class AuthorByNameService extends MotherBusinessService implements AuthorByNameServiceInterface {

  private static final String TAG = "AuthorsByNameService";

  /**
   * The authors to display (the cache)
   */
  private ArrayMap<String,Author> authorsByNameList = null;

  /**
   *
   */
  private AuthorByNameLoadedEvent authorByNameLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public AuthorByNameService(ServiceManagerInterface srvManager) {
    super(srvManager);
    authorsByNameList = new ArrayMap<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    authorsByNameList = null;
  }

  /**
   * Find the author that match the author name in an asynchronous way
   *
   * @param name The name of the author searched
   */
  @Override
  public Observable<Author> loadAuthorByNameAsync(String name) {
    //  Log.e(TAG, "loadAuthorByIdAuthorAsync() called with: " + "cityId = [" + idAuthor + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (authorsByNameList.get(name)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
    //  postAuthorByNameDataLoadedEvent(authorsByNameList.get(name),name);
      return Observable.just(authorsByNameList.get(name));
    //  return authorsByNameList.get(name).asObservable();
    } else {
      // then launch it
    //  OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoAuthorByNameLoadRunnable(name));
      return Observable.just(loadAuthorByNameSync(name));
    }
  }

  private Author loadAuthorByNameSync(String name){
    // Log.e(TAG, "loadAuthorByIdAuthorSync() called with: " + "idAuthor = [" + idAuthor + "]");
    // Load data from database
    /*

   */
    AuthorsDaoInterface authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
    Author authorByName = authorsDaoInterface.findAuthorByName(name);
    authorsDaoInterface = null;
    //update your own cache
    authorsByNameList.put(name,authorByName);
    return authorByName;
    //send send back the answer using eventBus
  //  postAuthorByNameDataLoadedEvent(authorByName,name);
  }

  /**
   * @author Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoAuthorByNameLoadRunnable implements Runnable {
    final String name;

    public DaoAuthorByNameLoadRunnable(String name) {
      this.name = name;
    }

    @Override
    public void run() {
      loadAuthorByNameSync(name);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
//  private void postAuthorByNameDataLoadedEvent(Author authorByName,String name) {
//    //  Log.e(TAG, "postAuthorByIdAuthorDataLoadedEvent() called as found Author with " +
//    //    authorByIdAuthorLoadedEvent.getAuthorByIdAuthor().getQuotes().size() + " elements and idAuthor="+idAuthor);
//    AuthorEventBus author = new AuthorEventBus(authorByName);
//
//
//    if(authorByNameLoadedEvent==null){
//      authorByNameLoadedEvent= new AuthorByNameLoadedEvent(author,name);
//    }else{
//      authorByNameLoadedEvent.setAuthorByName(author);
//      authorByNameLoadedEvent.setName(name);
//    }
//    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + authorByIdAuthorLoadedEvent.getAuthorByIdAuthor() + " elements and idAuthor ="+authorByIdAuthorLoadedEvent.getIdAuthor());
//    EventBus.getDefault().post(authorByNameLoadedEvent);
//  }

}
