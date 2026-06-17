package com.sc.en.bouddhism.layers.service.quotes.services;

import android.util.SparseArray;

import com.sc.en.bouddhism.OnelittleAngelApplication;
import com.sc.en.bouddhism.layers.dao.quotes.QuotesDaoInterface;
import com.sc.en.bouddhism.layers.service.MotherBusinessService;
import com.sc.en.bouddhism.transverse.eventbus.events.quotes.QuotesByIdBookLoadedEvent;
import com.sc.en.bouddhism.transverse.orms.realm.models.Quote;
import com.sc.en.bouddhism.injector.Injector;
import com.sc.en.bouddhism.layers.service.ServiceManagerInterface;
import com.sc.en.bouddhism.layers.service.quotes.interfaces.QuotesByIdBookServiceInterface;
import com.sc.en.bouddhism.transverse.eventbus.models.QuoteEventBus;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class QuotesByIdBookService extends MotherBusinessService implements QuotesByIdBookServiceInterface {

  private static final String TAG = "QuotesByIdBookService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Quote>> quotesByIdBookList = null;

  /**
   *
   */
  private QuotesByIdBookLoadedEvent quotesByIdBookLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public QuotesByIdBookService(ServiceManagerInterface srvManager) {
    super(srvManager);
    quotesByIdBookList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    quotesByIdBookList = null;
  }

  /**
   * @param idBook
   */
  @Override
  public void loadQuotesByIdBookAsync(int idBook) {
    //  Log.e(TAG, "loadQuoteByIdBookAsync() called with: " + "cityId = [" + idBook + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (quotesByIdBookList.get(idBook)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postQuoteByIdBookDataLoadedEvent(quotesByIdBookList.get(idBook),idBook);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoQuoteByIdBookLoadRunnable(idBook));
    }
  }

  private void loadQuoteByIdBookSync(int idBook){
    // Log.e(TAG, "loadQuoteByIdBookSync() called with: " + "idBook = [" + idBook + "]");
    // Load data from database
    /*

   */
    QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
    List<Quote> quotesByIdBook = quotesDaoInterface.findQuotesByIdBook(idBook);
    quotesDaoInterface = null;
    //update your own cache
    quotesByIdBookList.put(idBook,quotesByIdBook);
    //send send back the answer using eventBus
    postQuoteByIdBookDataLoadedEvent(quotesByIdBook,idBook);
  }

  /**
   * @quote Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoQuoteByIdBookLoadRunnable implements Runnable {
    final int idBook;

    public DaoQuoteByIdBookLoadRunnable(int idBook) {
      this.idBook = idBook;
    }

    @Override
    public void run() {
      loadQuoteByIdBookSync(idBook);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postQuoteByIdBookDataLoadedEvent(List<Quote> quotesByIdBook,int idBook) {
    //  Log.e(TAG, "postQuoteByIdBookDataLoadedEvent() called as found Quote with " +
    //    quoteByIdBookLoadedEvent.getQuoteByIdBook().getQuotes().size() + " elements and idBook="+idBook);
    List<QuoteEventBus> quotes = new ArrayList<>();

    for(Quote q : quotesByIdBook){
      quotes.add(new QuoteEventBus(q));
    }

    if(quotesByIdBookLoadedEvent==null){
      quotesByIdBookLoadedEvent= new QuotesByIdBookLoadedEvent(quotes, idBook);
    }else{
      quotesByIdBookLoadedEvent.setQuotesByIdBook(quotes);
      quotesByIdBookLoadedEvent.setIdBook(idBook);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + quoteByIdBookLoadedEvent.getQuoteByIdBook() + " elements and idBook ="+quoteByIdBookLoadedEvent.getIdBook());
    EventBus.getDefault().post(quotesByIdBookLoadedEvent);
  }

}
