package com.sc.fr.taoisme.layers.service.books.services;

import android.util.SparseArray;

import com.sc.fr.taoisme.OnelittleAngelApplication;
import com.sc.fr.taoisme.injector.Injector;
import com.sc.fr.taoisme.layers.dao.books.BooksDaoInterface;
import com.sc.fr.taoisme.layers.service.ServiceManagerInterface;
import com.sc.fr.taoisme.layers.service.books.interfaces.BooksByIdMovementServiceInterface;
import com.sc.fr.taoisme.layers.service.MotherBusinessService;
import com.sc.fr.taoisme.transverse.eventbus.events.books.BooksByIdMovementLoadedEvent;
import com.sc.fr.taoisme.transverse.eventbus.models.BookEventBus;
import com.sc.fr.taoisme.transverse.orms.realm.models.Book;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class BooksByIdMovementService extends MotherBusinessService implements BooksByIdMovementServiceInterface {

  private static final String TAG = "BookByIdBookService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Book>> booksByIdMovementList = null;

  /**
   *
   */
  private BooksByIdMovementLoadedEvent booksByIdMovementLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public BooksByIdMovementService(ServiceManagerInterface srvManager) {
    super(srvManager);
    booksByIdMovementList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    booksByIdMovementList = null;
  }

  /**
   * Find authors that match the id movement in an asynchronous way
   *
   * @param idMovement The id movement of authors searched
   */
  @Override
  public void loadBooksByIdMovementAsync(int idMovement) {
    //  Log.e(TAG, "loadBookByIdMovementAsync() called with: " + "cityId = [" + idMovement + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (booksByIdMovementList.get(idMovement)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postBookByIdMovementDataLoadedEvent(booksByIdMovementList.get(idMovement),idMovement);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new BooksByIdMovementService.DaoBookByIdMovementLoadRunnable(idMovement));
    }
  }

  private void loadBookByIdMovementSync(int idMovement){
    // Log.e(TAG, "loadBookByIdMovementSync() called with: " + "idMovement = [" + idMovement + "]");
    // Load data from database
    /*

   */
    BooksDaoInterface booksDaoInterface = Injector.getDaoManager().getBooksDao();
    List<Book> booksByIdMovement = booksDaoInterface.findBooksByIdMovement(idMovement);
    booksDaoInterface = null;
    //update your own cache
    booksByIdMovementList.put(idMovement,booksByIdMovement);
    //send send back the answer using eventBus
    postBookByIdMovementDataLoadedEvent(booksByIdMovement,idMovement);
  }

  /**
   * @book Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoBookByIdMovementLoadRunnable implements Runnable {
    final int idMovement;

    public DaoBookByIdMovementLoadRunnable(int idMovement) {
      this.idMovement = idMovement;
    }

    @Override
    public void run() {
      loadBookByIdMovementSync(idMovement);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postBookByIdMovementDataLoadedEvent(List<Book> booksByIdMovement,int idMovement) {
    //  Log.e(TAG, "postBookByIdMovementDataLoadedEvent() called as found Book with " +
    //    bookByIdMovementLoadedEvent.getBookByIdMovement().getQuotes().size() + " elements and idMovement="+idMovement);
    List<BookEventBus> books = new ArrayList<>();

    for(Book b : booksByIdMovement){
      books.add(new BookEventBus(b));
    }

    if(booksByIdMovementLoadedEvent==null){
      booksByIdMovementLoadedEvent= new BooksByIdMovementLoadedEvent(books, idMovement);
    }else{
      booksByIdMovementLoadedEvent.setBooksByIdMovement(books);
      booksByIdMovementLoadedEvent.setIdMovement(idMovement);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + bookByIdMovementLoadedEvent.getBookByIdMovement() + " elements and idMovement ="+bookByIdMovementLoadedEvent.getIdMovement());
    EventBus.getDefault().post(booksByIdMovementLoadedEvent);
  }

}
