package com.sc.en.hindouism.layers.service.books.services;

import android.util.SparseArray;

import com.sc.en.hindouism.OnelittleAngelApplication;
import com.sc.en.hindouism.layers.dao.books.BooksDaoInterface;
import com.sc.en.hindouism.layers.service.MotherBusinessService;
import com.sc.en.hindouism.layers.service.ServiceManagerInterface;
import com.sc.en.hindouism.transverse.eventbus.events.books.BooksByIdThemeLoadedEvent;
import com.sc.en.hindouism.transverse.eventbus.models.BookEventBus;
import com.sc.en.hindouism.transverse.orms.realm.models.Book;
import com.sc.en.hindouism.layers.service.books.interfaces.BooksByIdThemeServiceInterface;
import com.sc.en.hindouism.injector.Injector;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class BooksByIdThemeService extends MotherBusinessService implements BooksByIdThemeServiceInterface {

  private static final String TAG = "BookByIdThemeService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Book>> booksByIdThemeList = null;

  /**
   *
   */
  private BooksByIdThemeLoadedEvent booksByIdThemeLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public BooksByIdThemeService(ServiceManagerInterface srvManager) {
    super(srvManager);
    booksByIdThemeList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    booksByIdThemeList = null;
  }

  /**
   * Finf authors that match the id theme in an asynchronous way
   *
   * @param idTheme The id theme of authors searched
   */
  @Override
  public void loadBooksByIdThemeAsync(int idTheme) {
    //  Log.e(TAG, "loadBookByIdThemeAsync() called with: " + "cityId = [" + idTheme + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (booksByIdThemeList.get(idTheme)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postBookByIdThemeDataLoadedEvent(booksByIdThemeList.get(idTheme),idTheme);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new BooksByIdThemeService.DaoBookByIdThemeLoadRunnable(idTheme));
    }
  }

  private void loadBookByIdThemeSync(int idTheme){
    // Log.e(TAG, "loadBookByIdThemeSync() called with: " + "idTheme = [" + idTheme + "]");
    // Load data from database
    /*

   */
    BooksDaoInterface booksDaoInterface = Injector.getDaoManager().getBooksDao();
    List<Book> booksByIdTheme = booksDaoInterface.findBooksByIdTheme(idTheme);
    booksDaoInterface = null;
    //update your own cache
    booksByIdThemeList.put(idTheme,booksByIdTheme);
    //send send back the answer using eventBus
    postBookByIdThemeDataLoadedEvent(booksByIdTheme,idTheme);
  }

  /**
   * @book Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoBookByIdThemeLoadRunnable implements Runnable {
    final int idTheme;

    public DaoBookByIdThemeLoadRunnable(int idTheme) {
      this.idTheme = idTheme;
    }

    @Override
    public void run() {
      loadBookByIdThemeSync(idTheme);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postBookByIdThemeDataLoadedEvent(List<Book> booksByIdTheme,int idTheme) {
    //  Log.e(TAG, "postBookByIdThemeDataLoadedEvent() called as found Book with " +
    //    bookByIdThemeLoadedEvent.getBookByIdTheme().getQuotes().size() + " elements and idTheme="+idTheme);
    List<BookEventBus> books = new ArrayList<>();

    for(Book b : booksByIdTheme){
      books.add(new BookEventBus(b));
    }

    if(booksByIdThemeLoadedEvent==null){
      booksByIdThemeLoadedEvent= new BooksByIdThemeLoadedEvent(books, idTheme);
    }else{
      booksByIdThemeLoadedEvent.setBooksByIdTheme(books);
      booksByIdThemeLoadedEvent.setIdTheme(idTheme);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + bookByIdThemeLoadedEvent.getBookByIdTheme() + " elements and idTheme ="+bookByIdThemeLoadedEvent.getIdTheme());
    EventBus.getDefault().post(booksByIdThemeLoadedEvent);
  }

}
