package com.sc.fr.islam.layers.service.books.services;

import android.util.Log;
import android.util.SparseArray;

import com.sc.fr.islam.OnelittleAngelApplication;
import com.sc.fr.islam.layers.service.MotherBusinessService;
import com.sc.fr.islam.layers.service.books.interfaces.BooksAllServiceInterface;
import com.sc.fr.islam.transverse.eventbus.events.books.BooksAllLoadedEvent;
import com.sc.fr.islam.transverse.eventbus.models.BookEventBus;
import com.sc.fr.islam.transverse.orms.realm.models.Book;
import com.sc.fr.islam.injector.Injector;
import com.sc.fr.islam.layers.dao.books.BooksDaoInterface;
import com.sc.fr.islam.layers.service.ServiceManagerInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class BooksAllService extends MotherBusinessService implements BooksAllServiceInterface {

  private static final String TAG = "BooksAllService";

  /**
   * The books to display (the cache)
   */
  private SparseArray<Book> booksList = null;
  /**
   *
   */
  private BooksAllServiceInterface booksAllServiceInterface = null;

  /**
   *
   */
  private BooksAllLoadedEvent booksAllLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public BooksAllService(ServiceManagerInterface srvManager) {
    super(srvManager);
    booksList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    booksList = null;
  }

  @Override
  public void loadAllBooksAsync() {
    //Log.d(TAG, "loadBooks() called with: " + "");
    OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindAllBooksRunnable);
  }

  private void loadAllBooksSync(){
    // Load data from DB
    BooksDaoInterface booksDaoInterface = Injector.getDaoManager().getBooksDao();
    //send back the answer using eventBus
    postAllBooksLoadedEvent(booksDaoInterface.findAllBooks());
    booksDaoInterface =null;
  }

  /**
   * The runnable to execute
   */
  private final BooksAllService.DaoFindAllBooksRunnable daoFindAllBooksRunnable = new BooksAllService.DaoFindAllBooksRunnable();
  /**
   * @book Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable
   */
  private class DaoFindAllBooksRunnable implements Runnable {
    @Override
    public void run() {
      loadAllBooksSync();
    }
  }

  /**
   * Broadcast all the books Loaded event
   */
  private void postAllBooksLoadedEvent(List<Book> booksAll) {

    List<BookEventBus> books = new ArrayList<>();

    for(Book a : booksAll){
      books.add(new BookEventBus(a));
    }

    if(booksAllLoadedEvent ==null){
      booksAllLoadedEvent = new BooksAllLoadedEvent(books);
    }else{
      booksAllLoadedEvent.setBooks(books);
    }
    //Log.e(TAG, "postCitiesLoadedEvent posted" );
    EventBus.getDefault().post(booksAllLoadedEvent);
  }
}
