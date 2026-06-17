package com.sc.fr.philosophie.layers.service.quotes.services;

import android.support.v4.util.ArrayMap;

import com.sc.fr.philosophie.injector.Injector;
import com.sc.fr.philosophie.layers.dao.quotes.QuotesDaoInterface;
import com.sc.fr.philosophie.layers.service.MotherBusinessService;
import com.sc.fr.philosophie.layers.service.ServiceManagerInterface;
import com.sc.fr.philosophie.layers.service.quotes.interfaces.QuotesByBookServiceInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Quote;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

public class QuotesByBookService extends MotherBusinessService implements QuotesByBookServiceInterface {

  /**
   *
   */
  private ArrayMap<String, Observable<List<Quote>>> quotesByBookList = null;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public QuotesByBookService(ServiceManagerInterface srvManager) {
    super(srvManager);
    quotesByBookList = new ArrayMap<>();
  }

  @Override
  public void onDestroy() {
    quotesByBookList = null;
  }

  @Override
  public Observable<List<Quote>> loadQuotesByBookAsync(String name) {
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (quotesByBookList.get(name) != null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthorList.get(idAuthor),idAuthor);
      return quotesByBookList.get(name);
    } else {
      return loadQuotesByBookSync(name);
      // then launch it
//      Future<Observable<List<Quote>>> quotesByAuthor = OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoQuotesByBookLoadRunnable(name));
//      try {
//        return quotesByAuthor.get();
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      } catch (ExecutionException e) {
//        e.printStackTrace();
//      }
    }
  //  return null;
  }

  private Observable<List<Quote>> loadQuotesByBookSync(String name) {
    /*

   */
    QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
    //  List<Quote> quotesByIdAuthor = quotesDaoInterface.findQuotesByIdAuthor(idAuthor);
    Observable<List<Quote>> quotesByBook = quotesDaoInterface.findQuotesByBook(name);
    quotesDaoInterface = null;
    //update your own cache
    quotesByBookList.put(name,quotesByBook);
    return quotesByBookList.get(name);
  }

  /**
   * @quotes Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoQuotesByBookLoadRunnable implements Callable<Observable<List<Quote>>> {
    final String name;

    public DaoQuotesByBookLoadRunnable(String name) {
      this.name = name;
    }

    @Override
    public Observable<List<Quote>> call() throws Exception {
      return loadQuotesByBookSync(name);
    }
  }
}
