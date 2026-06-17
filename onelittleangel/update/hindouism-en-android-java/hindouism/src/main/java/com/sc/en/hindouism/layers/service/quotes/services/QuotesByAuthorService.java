package com.sc.en.hindouism.layers.service.quotes.services;

import android.support.v4.util.ArrayMap;

import com.sc.en.hindouism.layers.service.MotherBusinessService;
import com.sc.en.hindouism.layers.service.ServiceManagerInterface;
import com.sc.en.hindouism.layers.service.quotes.interfaces.QuotesByAuthorServiceInterface;
import com.sc.en.hindouism.transverse.orms.realm.models.Quote;
import com.sc.en.hindouism.layers.dao.quotes.QuotesDaoInterface;
import com.sc.en.hindouism.injector.Injector;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

public class QuotesByAuthorService extends MotherBusinessService implements QuotesByAuthorServiceInterface {

  /**
   *
   */
  private ArrayMap<String, List<Quote>> quotesByAuthorList = null;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public QuotesByAuthorService(ServiceManagerInterface srvManager) {
    super(srvManager);
    quotesByAuthorList = new ArrayMap<>();
  }

  @Override
  public Observable<List<Quote>> loadQuotesByAuthorAsync(String name) {

    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (quotesByAuthorList.get(name) != null) {
      reload = true;
    }

    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthorList.get(idAuthor),idAuthor);
      return Observable.just(quotesByAuthorList.get(name));
    } else {
      return loadQuotesByAuthorSync(name);
      // then launch it
    //  Future<Observable<List<Quote>>> quotesByAuthor = OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoQuotesByAuthorLoadRunnable(name));
//      try {
//      //  return quotesByAuthor.get();
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      } catch (ExecutionException e) {
//        e.printStackTrace();
//      }
    }
  //  return null;
  }

  private Observable<List<Quote>> loadQuotesByAuthorSync(String name) {
    /*

   */
    QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
    //  List<Quote> quotesByIdAuthor = quotesDaoInterface.findQuotesByIdAuthor(idAuthor);
    List<Quote> quotesByIdAuthor = quotesDaoInterface.findQuotesByAuthor(name);
    quotesDaoInterface = null;
    //update your own cache
    quotesByAuthorList.put(name,quotesByIdAuthor);
    return Observable.just(quotesByIdAuthor);
  }

  /**
   * @quotes Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoQuotesByAuthorLoadRunnable implements Callable<Observable<List<Quote>>> {
    final String name;

    public DaoQuotesByAuthorLoadRunnable(String name) {
      this.name = name;
    }

    @Override
    public Observable<List<Quote>> call() throws Exception {
      return loadQuotesByAuthorSync(name);
    }
  }

  @Override
  public void onDestroy() {
    quotesByAuthorList = null;
  }
}
