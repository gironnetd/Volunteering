package com.sc.en.onelittleangel.layers.service.books.services;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.sc.en.onelittleangel.layers.service.MotherBusinessService;
import com.sc.en.onelittleangel.layers.service.books.interfaces.BookByNameServiceInterface;
import com.sc.en.onelittleangel.transverse.eventbus.events.books.BookByNameLoadedEvent;
import com.sc.en.onelittleangel.injector.Injector;
import com.sc.en.onelittleangel.layers.dao.books.BooksDaoInterface;
import com.sc.en.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.en.onelittleangel.transverse.eventbus.models.BookEventBus;
import com.sc.en.onelittleangel.transverse.orms.realm.models.Book;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Callable;

import io.reactivex.Observable;

public class BookByNameService extends MotherBusinessService implements BookByNameServiceInterface {

  private static final String TAG = "BookByNameService";

  /**
   * The authors to display (the cache)
   */
  private ArrayMap<String,Book> booksByNameList = null;

  /**
   *
   */
  private BookByNameLoadedEvent bookByNameLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public BookByNameService(ServiceManagerInterface srvManager) {
    super(srvManager);
    booksByNameList = new ArrayMap<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    booksByNameList = null;
  }

  /**
   * Find the author that match the author name in an asynchronous way
   *
   * @param name The name of the author searched
   */
  @Override
  public Observable<Book> loadBookByNameAsync(String name) {
    //  Log.e(TAG, "loadAuthorByNameAsync() called with: " + "cityId = [" + name + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (booksByNameList.get(name)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      //postAuthorByNameDataLoadedEvent(booksByNameList.get(name),name);
      return Observable.just(booksByNameList.get(name));
      //return booksByNameList.get(name).asObservable();
    } else {
      // then launch it

      Book book  = loadAuthorByNameSync(name);
      if (book == null) return Observable.empty();
      else return Observable.just(book);
//      return Observable.from(OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoBookByNameLoadRunnable(name)));
    }
  }

  private Book loadAuthorByNameSync(String name){
     //Log.e(TAG, "loadAuthorByNameSync() called with: " + "name = [" + name + "]");
    // Load data from database
    /*

   */
    BooksDaoInterface booksDaoInterface = Injector.getDaoManager().getBooksDao();
    Book bookByName = booksDaoInterface.findBookByName(name);
    booksDaoInterface = null;
    //update your own cache
     booksByNameList.put(name,bookByName);

    return bookByName;
    //send send back the answer using eventBus
    //postAuthorByNameDataLoadedEvent(bookByName,name);
  }

  /**
   * @book Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoBookByNameLoadRunnable implements Callable<Book> {
    final String name;

    public DaoBookByNameLoadRunnable(String name) {
      this.name = name;
    }

    @Override
    public Book call() throws Exception {
      return loadAuthorByNameSync(name);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postAuthorByNameDataLoadedEvent(Book bookByName,String name) {
    //  Log.e(TAG, "postAuthorByNameDataLoadedEvent() called as found Author with " +
    //    bookByNameLoadedEvent.getAuthorByName().getQuotes().size() + " elements and name="+name);
    BookEventBus book = new BookEventBus(bookByName);


    if(bookByNameLoadedEvent==null){
      bookByNameLoadedEvent= new BookByNameLoadedEvent(book, name);
    }else{
      bookByNameLoadedEvent.setBookByName(book);
      bookByNameLoadedEvent.setName(name);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + bookByNameLoadedEvent.getAuthorByName() + " elements and name ="+bookByNameLoadedEvent.getName());
    EventBus.getDefault().post(bookByNameLoadedEvent);
  }

}
