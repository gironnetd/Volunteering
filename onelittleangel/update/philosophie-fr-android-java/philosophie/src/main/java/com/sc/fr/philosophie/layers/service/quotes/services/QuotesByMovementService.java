package com.sc.fr.philosophie.layers.service.quotes.services;

import android.support.v4.util.ArrayMap;

import com.sc.fr.philosophie.injector.Injector;
import com.sc.fr.philosophie.layers.dao.quotes.QuotesDaoInterface;
import com.sc.fr.philosophie.layers.service.MotherBusinessService;
import com.sc.fr.philosophie.layers.service.ServiceManagerInterface;
import com.sc.fr.philosophie.layers.service.quotes.interfaces.QuotesByMovementServiceInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Quote;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

public class QuotesByMovementService extends MotherBusinessService implements QuotesByMovementServiceInterface {

  /**
   *
   */
  private ArrayMap<String, Observable<List<Quote>>> quotesByMovementList = null;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public QuotesByMovementService(ServiceManagerInterface srvManager) {
    super(srvManager);
    quotesByMovementList = new ArrayMap<>();
  }

  @Override
  public void onDestroy() {
    quotesByMovementList = null;
  }

  @Override
  public Observable<List<Quote>> loadQuotesByMovementAsync(String movement) {
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (quotesByMovementList.get(movement) != null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthorList.get(idAuthor),idAuthor);
      return quotesByMovementList.get(movement);
    } else {
      return loadQuotesByMovementSync(movement);
      // then launch it
//      Future<Observable<List<Quote>>> quotesByAuthor = OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoQuotesByMovementLoadRunnable(movement));
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

  private Observable<List<Quote>> loadQuotesByMovementSync(String movement) {
    /*

   */
    QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
    //  List<Quote> quotesByIdAuthor = quotesDaoInterface.findQuotesByIdAuthor(idAuthor);
    Observable<List<Quote>> quotesByMovement = quotesDaoInterface.findQuotesByMovement(movement);
    quotesDaoInterface = null;
    //update your own cache
    quotesByMovementList.put(movement,quotesByMovement);
    return quotesByMovementList.get(movement);
  }

  /**
   * @quotes Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoQuotesByMovementLoadRunnable implements Callable<Observable<List<Quote>>> {
    final String movement;

    public DaoQuotesByMovementLoadRunnable(String movement) {
      this.movement = movement;
    }

    @Override
    public Observable<List<Quote>> call() throws Exception {
      return loadQuotesByMovementSync(movement);
    }
  }
}
