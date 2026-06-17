package com.sc.en.hindouism.layers.service.books.services;

import android.util.Log;
import android.util.SparseArray;

import com.sc.en.hindouism.OnelittleAngelApplication;
import com.sc.en.hindouism.layers.dao.books.BooksDaoInterface;
import com.sc.en.hindouism.layers.service.MotherBusinessService;
import com.sc.en.hindouism.layers.service.ServiceManagerInterface;
import com.sc.en.hindouism.layers.service.books.interfaces.BooksAllServiceInterface;
import com.sc.en.hindouism.transverse.eventbus.events.books.BooksAllLoadedEvent;
import com.sc.en.hindouism.transverse.eventbus.models.BookEventBus;
import com.sc.en.hindouism.transverse.orms.realm.models.Book;
import com.sc.en.hindouism.injector.Injector;

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
