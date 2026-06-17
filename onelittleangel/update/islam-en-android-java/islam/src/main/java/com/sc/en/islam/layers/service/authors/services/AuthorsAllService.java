package com.sc.en.islam.layers.service.authors.services;

import android.util.Log;
import android.util.SparseArray;

import com.sc.en.islam.OnelittleAngelApplication;
import com.sc.en.islam.layers.dao.authors.AuthorsDaoInterface;
import com.sc.en.islam.layers.service.MotherBusinessService;
import com.sc.en.islam.layers.service.ServiceManagerInterface;
import com.sc.en.islam.layers.service.authors.interfaces.AuthorsAllServiceInterface;
import com.sc.en.islam.transverse.eventbus.events.authors.AuthorLoadedEvent;
import com.sc.en.islam.transverse.eventbus.events.authors.AuthorsLoadedEvent;
import com.sc.en.islam.transverse.eventbus.models.AuthorEventBus;
import com.sc.en.islam.transverse.orms.realm.models.Author;
import com.sc.en.islam.injector.Injector;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class AuthorsAllService extends MotherBusinessService implements AuthorsAllServiceInterface {

  private static final String TAG = "AuthorsAllService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Author> authorsList = null;

  /**
   *
   */
  private AuthorLoadedEvent authorLoadedEvent;

  /**
   *
   */
  private AuthorsLoadedEvent authorsLoadedEvent;
  /**
   * To know if tha data has to be reloaded
   */
  private boolean reload = true;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public AuthorsAllService(ServiceManagerInterface srvManager) {
    super(srvManager);
    authorsList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    authorsList = null;
  }

//  /**
//   * Load all the cities from the database asynchronously
//   */
//  @Override
//  public void loadCitiesAsync() {
//    Log.d(TAG, "loadCities() called with: " + "");
//    MyApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindAllRunnable);
//  }
//
//  /**
//   *
//   * Load all the cities from the database synchronously
//   */
//  private void loadCitiesSync() {
//    // Load data from DB
//    cityDaoIntf = Injector.getDaoManager().getCityDao();
//    //send back the answer using eventBus
//    postCitiesLoadedEvent(cityDaoIntf.findAll());
//    cityDaoIntf =null;
//  }
//
//  /**
//   * The runnable to execute
//   */
//  private DaoFindAllRunnable daoFindAllRunnable = new DaoFindAllRunnable();
//  /**
//   * @author Mathias Seguy (Android2EE)
//   * @goals
//   *        This class aims to implements a Runnable
//   */
//  private class DaoFindAllRunnable implements Runnable {
//    @Override
//    public void run() {
//      loadCitiesSync();
//    }
//  }
//  /**
//   * Broadcast the Cities Loaded event
//   */
//  private void postCitiesLoadedEvent(List<City> city) {
//    if(citiesLoadedEvent ==null){
//      citiesLoadedEvent = new CitiesLoadedEvent(city);
//    }else{
//      citiesLoadedEvent.setCities(city);
//    }
//    Log.e(TAG, "postCitiesLoadedEvent posted" );
//    EventBus.getDefault().post(citiesLoadedEvent);
//  }

  /**
   * Load all the authors from the database asynchronously
   */
  @Override
  public void loadAllAuthorsAsync() {
    //Log.d(TAG, "loadAuthors() called with: " + "");
    OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindAllAuthorsRunnable);
  }

  private void loadAllAuthorsSync(){
    // Load data from DB
    /*

   */
    AuthorsDaoInterface authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
    //send back the answer using eventBus
    postAllAuthorsLoadedEvent(authorsDaoInterface.findAllAuthors());
    authorsDaoInterface =null;
  }

  /**
   * The runnable to execute
   */
  private final DaoFindAllAuthorsRunnable daoFindAllAuthorsRunnable = new DaoFindAllAuthorsRunnable();
  /**
   * @author Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable
   */
  private class DaoFindAllAuthorsRunnable implements Runnable {
    @Override
    public void run() {
      loadAllAuthorsSync();
    }
  }

  /**
   * Broadcast all the authors Loaded event
   */
  private void postAllAuthorsLoadedEvent(List<Author> authorsAll) {

    List<AuthorEventBus> authors = new ArrayList<>();

    for(Author a : authorsAll){
      authors.add(new AuthorEventBus(a));
    }

    if(authorsLoadedEvent ==null){
      authorsLoadedEvent = new AuthorsLoadedEvent(authors);
    }else{
      authorsLoadedEvent.setAuthors(authors);
    }
    //Log.e(TAG, "postCitiesLoadedEvent posted" );
    EventBus.getDefault().post(authorsLoadedEvent);
  }
}
