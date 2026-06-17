package com.sc.en.bouddhism.layers.service.books.services;

import android.util.SparseArray;

import com.sc.en.bouddhism.OnelittleAngelApplication;
import com.sc.en.bouddhism.transverse.eventbus.models.BookEventBus;
import com.sc.en.bouddhism.layers.dao.books.BooksDaoInterface;
import com.sc.en.bouddhism.layers.service.MotherBusinessService;
import com.sc.en.bouddhism.injector.Injector;
import com.sc.en.bouddhism.layers.service.ServiceManagerInterface;
import com.sc.en.bouddhism.layers.service.books.interfaces.BookByIdBookServiceInterface;
import com.sc.en.bouddhism.transverse.eventbus.events.books.BookByIdBookLoadedEvent;
import com.sc.en.bouddhism.transverse.orms.realm.models.Book;

import org.greenrobot.eventbus.EventBus;

public class BookByIdBookService extends MotherBusinessService implements BookByIdBookServiceInterface {

  private static final String TAG = "BookByIdBookService";
  
  /**
   * The authors to display (the cache)
   */
  private SparseArray<Book> booksByIdBookList = null;

  /**
   *
   */
  private BookByIdBookLoadedEvent bookByIdBookLoadedEvent;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public BookByIdBookService(ServiceManagerInterface srvManager) {
    super(srvManager);
    booksByIdBookList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    booksByIdBookList = null;
  }

  /**
   * Find the author that match the author id in an asynchronous way
   *
   * @param idBook The name of the author searched
   */
  @Override
  public void loadBookByIdBookAsync(int idBook) {
    //  Log.e(TAG, "loadBookByIdBookAsync() called with: " + "cityId = [" + idBook + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (booksByIdBookList.get(idBook)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postBookByIdBookDataLoadedEvent(booksByIdBookList.get(idBook),idBook);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new BookByIdBookService.DaoBookByIdBookLoadRunnable(idBook));
    }
  }

  private void loadBookByIdBookSync(int idBook){
    // Log.e(TAG, "loadBookByIdBookSync() called with: " + "idBook = [" + idBook + "]");
    // Load data from database
    /*

   */
    BooksDaoInterface booksDaoInterface = Injector.getDaoManager().getBooksDao();
    Book bookByIdBook = booksDaoInterface.findBookByIdBook(idBook);
    booksDaoInterface = null;
    //update your own cache
    booksByIdBookList.put(idBook,bookByIdBook);
    //send send back the answer using eventBus
    postBookByIdBookDataLoadedEvent(bookByIdBook,idBook);
  }

  /**
   * @book Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoBookByIdBookLoadRunnable implements Runnable {
    final int idBook;

    public DaoBookByIdBookLoadRunnable(int idBook) {
      this.idBook = idBook;
    }

    @Override
    public void run() {
      loadBookByIdBookSync(idBook);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postBookByIdBookDataLoadedEvent(Book bookByIdBook,int idBook) {
    //  Log.e(TAG, "postBookByIdBookDataLoadedEvent() called as found Book with " +
    //    bookByIdBookLoadedEvent.getBookByIdBook().getQuotes().size() + " elements and idBook="+idBook);
    BookEventBus book = new BookEventBus(bookByIdBook);

    if(bookByIdBookLoadedEvent==null){
      bookByIdBookLoadedEvent= new BookByIdBookLoadedEvent(book, idBook);
    }else{
      bookByIdBookLoadedEvent.setBookByIdBook(book);
      bookByIdBookLoadedEvent.setIdBook(idBook);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + bookByIdBookLoadedEvent.getBookByIdBook() + " elements and idBook ="+bookByIdBookLoadedEvent.getIdBook());
    EventBus.getDefault().post(bookByIdBookLoadedEvent);
  }

}
