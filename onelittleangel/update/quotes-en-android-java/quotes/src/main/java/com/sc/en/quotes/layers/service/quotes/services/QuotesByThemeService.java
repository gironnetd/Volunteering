package com.sc.en.quotes.layers.service.quotes.services;

import android.support.v4.util.ArrayMap;

import com.sc.en.quotes.layers.dao.quotes.QuotesDaoInterface;
import com.sc.en.quotes.layers.service.MotherBusinessService;
import com.sc.en.quotes.layers.service.quotes.interfaces.QuotesByThemeServiceInterface;
import com.sc.en.quotes.injector.Injector;
import com.sc.en.quotes.layers.service.ServiceManagerInterface;
import com.sc.en.quotes.transverse.orms.realm.models.Quote;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

public class QuotesByThemeService extends MotherBusinessService implements QuotesByThemeServiceInterface {

  /**
   *
   */
  private ArrayMap<String, Observable<List<Quote>>> quotesByThemeList = null;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public QuotesByThemeService(ServiceManagerInterface srvManager) {
    super(srvManager);
    quotesByThemeList = new ArrayMap<>();
  }

  @Override
  public void onDestroy() {
    quotesByThemeList = null;
  }

  @Override
  public Observable<List<Quote>> loadQuotesByThemeAsync(String theme) {
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (quotesByThemeList.get(theme) != null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthorList.get(idAuthor),idAuthor);
      return quotesByThemeList.get(theme);
    } else {
      return loadQuotesByThemeSync(theme);
      // then launch it
//      Future<Observable<List<Quote>>> quotesByAuthor = OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoQuotesByThemeLoadRunnable(theme));
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

  private Observable<List<Quote>> loadQuotesByThemeSync(String theme) {
    /*

   */
    QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
    //  List<Quote> quotesByIdAuthor = quotesDaoInterface.findQuotesByIdAuthor(idAuthor);
    Observable<List<Quote>> quotesByTheme = quotesDaoInterface.findQuotesByTheme(theme);
    quotesDaoInterface = null;
    //update your own cache
    quotesByThemeList.put(theme,quotesByTheme);
    return quotesByThemeList.get(theme);
  }

  /**
   * @quotes Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoQuotesByThemeLoadRunnable implements Callable<Observable<List<Quote>>> {
    final String name;

    public DaoQuotesByThemeLoadRunnable(String name) {
      this.name = name;
    }

    @Override
    public Observable<List<Quote>> call() throws Exception {
      return loadQuotesByThemeSync(name);
    }
  }
}
