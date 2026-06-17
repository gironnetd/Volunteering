package com.sc.en.philosophy.layers.service.quotes.services;

import android.util.SparseArray;

import com.sc.en.philosophy.OnelittleAngelApplication;
import com.sc.en.philosophy.layers.dao.quotes.QuotesDaoInterface;
import com.sc.en.philosophy.layers.service.MotherBusinessService;
import com.sc.en.philosophy.layers.service.quotes.interfaces.QuotesByIdAuthorServiceInterface;
import com.sc.en.philosophy.transverse.eventbus.events.quotes.QuotesByIdAuthorLoadedEvent;
import com.sc.en.philosophy.transverse.orms.realm.models.Quote;
import com.sc.en.philosophy.injector.Injector;
import com.sc.en.philosophy.layers.service.ServiceManagerInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import io.reactivex.Observable;
import io.realm.Realm;

public class QuotesByIdAuthorService extends MotherBusinessService implements QuotesByIdAuthorServiceInterface {

  private static final String TAG = "QuotesByIdAuthorService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Observable<List<Quote>>> quotesByIdAuthorList = null;

  /**
   *
   */
  private QuotesByIdAuthorLoadedEvent quotesByIdAuthorLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public QuotesByIdAuthorService(ServiceManagerInterface srvManager) {
    super(srvManager);
    quotesByIdAuthorList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    quotesByIdAuthorList = null;
  }

  /**
   * @param idAuthor
   */
  @Override
  public Observable<List<Quote>> loadQuotesByIdAuthorAsync(int idAuthor) {
    //  Log.e(TAG, "loadQuotesByIdAuthorAsync() called with: " + "cityId = [" + idAuthor + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (quotesByIdAuthorList.get(idAuthor)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
    //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthorList.get(idAuthor),idAuthor);
      return quotesByIdAuthorList.get(idAuthor);
    } else {
      // then launch it
    Future<Observable<List<Quote>>> quotesByAuthor =  OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoQuotesByIdAuthorLoadRunnable(idAuthor));
      try {
        return quotesByAuthor.get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
      //  return loadQuotesByIdAuthorSync(idAuthor);
    }
    return null;
  }

  private Observable<List<Quote>> loadQuotesByIdAuthorSync(int idAuthor){
    // Log.e(TAG, "loadQuotesByIdAuthorSync() called with: " + "idAuthor = [" + idAuthor + "]");
    // Load data from database
    /*

   */
    QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
  //  List<Quote> quotesByIdAuthor = quotesDaoInterface.findQuotesByIdAuthor(idAuthor);
    Observable<List<Quote>> quotesByIdAuthor = quotesDaoInterface.findQuotesByIdAuthor(idAuthor);
    quotesDaoInterface = null;
    //update your own cache
    quotesByIdAuthorList.put(idAuthor,quotesByIdAuthor);
    return quotesByIdAuthorList.get(idAuthor);
    //send send back the answer using eventBus
  //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthor,idAuthor);
  }

  /**
   * @quotes Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoQuotesByIdAuthorLoadRunnable implements Callable<Observable<List<Quote>>> {
    final int idAuthor;

    public DaoQuotesByIdAuthorLoadRunnable(int idAuthor) {
      this.idAuthor = idAuthor;
    }

    @Override
    public Observable<List<Quote>> call() throws Exception {
      return loadQuotesByIdAuthorSync(idAuthor);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postQuotesByIdAuthorDataLoadedEvent(List<Quote> quotesByIdAuthor,int idAuthor) {
    //  Log.e(TAG, "postAuthorByIdAuthorDataLoadedEvent() called as found Author with " +
    //    quotesByIdAuthorLoadedEvent.getAuthorByIdAuthor().getQuotes().size() + " elements and idAuthor="+idAuthor);
  //  List<QuoteEventBus> quotes = new ArrayList<>();

//    for(Quote q : quotesByIdAuthor){
//      quotes.add(new QuoteEventBus(q));
//    }

    if(quotesByIdAuthorLoadedEvent==null){
      quotesByIdAuthorLoadedEvent= new QuotesByIdAuthorLoadedEvent(quotesByIdAuthor, idAuthor);
    }else{
      quotesByIdAuthorLoadedEvent.setQuotesByIdAuthor(quotesByIdAuthor);
      quotesByIdAuthorLoadedEvent.setIdAuthor(idAuthor);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + quotesByIdAuthorLoadedEvent.getAuthorByIdAuthor() + " elements and idAuthor ="+quotesByIdAuthorLoadedEvent.getIdAuthor());
//    Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        EventBus.getDefault().post(quotesByIdAuthorLoadedEvent);
//      }
//    });
    Realm.getDefaultInstance();
    EventBus.getDefault().post(quotesByIdAuthorLoadedEvent);
    Realm.getDefaultInstance().close();
  }
}
