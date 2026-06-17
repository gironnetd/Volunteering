package com.sc.fr.christianisme.layers.service.authors.services;

import android.util.SparseArray;

import com.sc.fr.christianisme.OnelittleAngelApplication;
import com.sc.fr.christianisme.injector.Injector;
import com.sc.fr.christianisme.layers.dao.authors.AuthorsDaoInterface;
import com.sc.fr.christianisme.layers.service.authors.interfaces.AuthorsByIdThemeServiceInterface;
import com.sc.fr.christianisme.layers.service.MotherBusinessService;
import com.sc.fr.christianisme.layers.service.ServiceManagerInterface;
import com.sc.fr.christianisme.transverse.eventbus.events.authors.AuthorsByIdThemeLoadedEvent;
import com.sc.fr.christianisme.transverse.eventbus.models.AuthorEventBus;
import com.sc.fr.christianisme.transverse.orms.realm.models.Author;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class AuthorsByIdThemeService extends MotherBusinessService implements AuthorsByIdThemeServiceInterface {

  private static final String TAG = "AuthorsByIdThemeService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Author>> authorsByIdThemeList = null;

  /**
   *
   */
  private AuthorsByIdThemeLoadedEvent authorByIdThemeLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public AuthorsByIdThemeService(ServiceManagerInterface srvManager) {
    super(srvManager);
    authorsByIdThemeList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    authorsByIdThemeList = null;
  }

  /**
   * Find authors that match the id theme in an asynchronous way
   *
   * @param idTheme The id theme of authors searched
   */
  @Override
  public void loadAuthorsByIdThemeAsync(int idTheme) {
    //  Log.e(TAG, "loadAuthorByIdThemeAsync() called with: " + "cityId = [" + idTheme + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (authorsByIdThemeList.get(idTheme)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postAuthorByIdThemeDataLoadedEvent(authorsByIdThemeList.get(idTheme),idTheme);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new AuthorsByIdThemeService.DaoAuthorByIdThemeLoadRunnable(idTheme));
    }
  }

  private void loadAuthorByIdThemeSync(int idTheme){
    // Log.e(TAG, "loadAuthorByIdThemeSync() called with: " + "idTheme = [" + idTheme + "]");
    // Load data from database
    /*

   */
    AuthorsDaoInterface authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
    List<Author> authorsByIdTheme = authorsDaoInterface.findAuthorsByIdTheme(idTheme);
    authorsDaoInterface = null;
    //update your own cache
    authorsByIdThemeList.put(idTheme,authorsByIdTheme);
    //send send back the answer using eventBus
    postAuthorByIdThemeDataLoadedEvent(authorsByIdTheme,idTheme);
  }

  /**
   * @author Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoAuthorByIdThemeLoadRunnable implements Runnable {
    final int idTheme;

    public DaoAuthorByIdThemeLoadRunnable(int idTheme) {
      this.idTheme = idTheme;
    }

    @Override
    public void run() {
      loadAuthorByIdThemeSync(idTheme);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postAuthorByIdThemeDataLoadedEvent(List<Author> authorsByIdTheme,int idTheme) {
    //  Log.e(TAG, "postAuthorByIdThemeDataLoadedEvent() called as found Author with " +
    //    authorByIdThemeLoadedEvent.getAuthorByIdTheme().getQuotes().size() + " elements and idTheme="+idTheme);
    List<AuthorEventBus> authors = new ArrayList<>();

    for(Author a : authorsByIdTheme){
      authors.add(new AuthorEventBus(a));
    }

    if(authorByIdThemeLoadedEvent==null){
      authorByIdThemeLoadedEvent= new AuthorsByIdThemeLoadedEvent(authors, idTheme);
    }else{
      authorByIdThemeLoadedEvent.setAuthorsByIdTheme(authors);
      authorByIdThemeLoadedEvent.setIdTheme(idTheme);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + authorByIdThemeLoadedEvent.getAuthorByIdTheme() + " elements and idTheme ="+authorByIdThemeLoadedEvent.getIdTheme());
    EventBus.getDefault().post(authorByIdThemeLoadedEvent);
  }

}
