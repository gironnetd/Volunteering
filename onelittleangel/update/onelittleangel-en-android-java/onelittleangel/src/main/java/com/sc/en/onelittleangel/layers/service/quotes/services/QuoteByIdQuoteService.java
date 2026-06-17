package com.sc.en.onelittleangel.layers.service.quotes.services;

import android.util.SparseArray;

import com.sc.en.onelittleangel.injector.Injector;
import com.sc.en.onelittleangel.layers.dao.quotes.QuotesDaoInterface;
import com.sc.en.onelittleangel.layers.service.MotherBusinessService;
import com.sc.en.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.en.onelittleangel.layers.service.quotes.interfaces.QuoteByIdQuoteServiceInterface;
import com.sc.en.onelittleangel.transverse.eventbus.events.quotes.QuoteByIdQuoteLoadedEvent;
import com.sc.en.onelittleangel.transverse.eventbus.models.QuoteEventBus;
import com.sc.en.onelittleangel.transverse.orms.realm.models.Quote;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;

public class QuoteByIdQuoteService extends MotherBusinessService implements QuoteByIdQuoteServiceInterface {

  private static final String TAG = "QuoteByIdQuoteService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Quote> quotesByIdQuoteList = null;

  /**
   *
   */
  private QuoteByIdQuoteLoadedEvent quoteByIdQuoteLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public QuoteByIdQuoteService(ServiceManagerInterface srvManager) {
    super(srvManager);
    quotesByIdQuoteList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * @param idQuote
   */
  @Override
  public Observable<Quote> loadQuoteByIdQuoteAsync(int idQuote) {
    //  Log.e(TAG, "loadQuoteByIdQuoteAsync() called with: " + "cityId = [" + idQuote + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (quotesByIdQuoteList.get(idQuote)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      return Observable.just(quotesByIdQuoteList.get(idQuote));
    //  postQuoteByIdQuoteDataLoadedEvent(quotesByIdQuoteList.get(idQuote),idQuote);
    } else {
      // then launch it
    //  OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new QuoteByIdQuoteService.DaoQuoteByIdQuoteLoadRunnable(idQuote));
      return Observable.just(loadQuoteByIdQuoteSync(idQuote));
    }
  }

  private Quote loadQuoteByIdQuoteSync(int idQuote){
    // Log.e(TAG, "loadQuoteByIdQuoteSync() called with: " + "idQuote = [" + idQuote + "]");
    // Load data from database
    /*

   */
    QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
    Quote quoteByIdQuote = quotesDaoInterface.findQuoteByIdQuote(idQuote);
    quotesDaoInterface = null;
    //update your own cache
    quotesByIdQuoteList.put(idQuote,quoteByIdQuote);
    //send send back the answer using eventBus
    //postQuoteByIdQuoteDataLoadedEvent(quoteByIdQuote,idQuote);
    return quotesByIdQuoteList.get(idQuote);
  }

  /**
   * @quote Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoQuoteByIdQuoteLoadRunnable implements Runnable {
    final int idQuote;

    public DaoQuoteByIdQuoteLoadRunnable(int idQuote) {
      this.idQuote = idQuote;
    }

    @Override
    public void run() {
      loadQuoteByIdQuoteSync(idQuote);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postQuoteByIdQuoteDataLoadedEvent(Quote quoteByIdQuote,int idQuote) {
    //  Log.e(TAG, "postQuoteByIdQuoteDataLoadedEvent() called as found Quote with " +
    //    quoteByIdQuoteLoadedEvent.getQuoteByIdQuote().getQuotes().size() + " elements and idQuote="+idQuote);
    QuoteEventBus quote = new QuoteEventBus(quoteByIdQuote);

    if(quoteByIdQuoteLoadedEvent==null){
      quoteByIdQuoteLoadedEvent= new QuoteByIdQuoteLoadedEvent(quote, idQuote);
    }else{
      quoteByIdQuoteLoadedEvent.setQuoteByIdQuote(quote);
      quoteByIdQuoteLoadedEvent.setIdQuote(idQuote);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + quoteByIdQuoteLoadedEvent.getQuoteByIdQuote() + " elements and idQuote ="+quoteByIdQuoteLoadedEvent.getIdQuote());
    EventBus.getDefault().post(quoteByIdQuoteLoadedEvent);
  }

}
