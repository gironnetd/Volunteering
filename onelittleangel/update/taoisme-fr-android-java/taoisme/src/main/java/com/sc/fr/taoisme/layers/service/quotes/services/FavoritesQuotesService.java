package com.sc.fr.taoisme.layers.service.quotes.services;

import com.sc.fr.taoisme.injector.Injector;
import com.sc.fr.taoisme.layers.dao.quotes.QuotesDaoInterface;
import com.sc.fr.taoisme.layers.service.MotherBusinessService;
import com.sc.fr.taoisme.layers.service.ServiceManagerInterface;
import com.sc.fr.taoisme.layers.service.quotes.interfaces.FavoritesQuotesServiceInterface;
import com.sc.fr.taoisme.transverse.orms.realm.models.Quote;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

public class FavoritesQuotesService extends MotherBusinessService implements FavoritesQuotesServiceInterface {

  private static final String TAG = "FavoritesQuotesService";

  /**
   * The quotes to display (the cache)
   */
  private Observable<List<Quote>> favoritesQuotesList = null;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public FavoritesQuotesService(ServiceManagerInterface srvManager) {
    super(srvManager);

  }

  @Override
  public Observable<List<Quote>> loadAllFavoritesQuotesAsync() {

    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
//    if (favoritesQuotesList != null) {
//      reload = true;
//    }

    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthorList.get(idAuthor),idAuthor);
      return favoritesQuotesList;
    } else {
      // then launch it
      return loadAllFavoritesQuotesSync();
//      Future<Observable<List<Quote>>> favoritesQuotes = OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoAllFavoritesQuotesLoadRunnable());
//      try {
//        return favoritesQuotes.get();
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      } catch (ExecutionException e) {
//        e.printStackTrace();
//      }
    }
//    return null;
  }

  private Observable<List<Quote>> loadAllFavoritesQuotesSync() {
    /*

   */
    QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
    //  List<Quote> quotesByIdAuthor = quotesDaoInterface.findQuotesByIdAuthor(idAuthor);
    favoritesQuotesList = quotesDaoInterface.findAllFavoritesQuotes();
    quotesDaoInterface = null;
    //update your own cache
    return favoritesQuotesList;
  }

  /**
   * @quotes Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoAllFavoritesQuotesLoadRunnable implements Callable<Observable<List<Quote>>> {


    public DaoAllFavoritesQuotesLoadRunnable() {
    }

    @Override
    public Observable<List<Quote>> call() throws Exception {
      return loadAllFavoritesQuotesSync();
    }
  }

  @Override
  public void onDestroy() {

  }
}
