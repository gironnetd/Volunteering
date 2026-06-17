package com.sc.fr.philosophie.layers.service.quotes.services;

import com.sc.fr.philosophie.OnelittleAngelApplication;
import com.sc.fr.philosophie.injector.Injector;
import com.sc.fr.philosophie.layers.dao.quotes.QuotesDaoInterface;
import com.sc.fr.philosophie.layers.service.MotherBusinessService;
import com.sc.fr.philosophie.layers.service.ServiceManagerInterface;
import com.sc.fr.philosophie.layers.service.quotes.interfaces.UpdateQuoteServiceInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public class UpdateQuoteService extends MotherBusinessService implements UpdateQuoteServiceInterface {

  private static final String TAG = "UpdateQuoteService";

  /**
   *
   */
  private QuotesDaoInterface quotesDaoInterface = null;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public UpdateQuoteService(ServiceManagerInterface srvManager) {
    super(srvManager);
  }

  @Override
  public void onDestroy() {

  }

  @Override
  public void toggleQuoteIsFavoritesAsync(int idQuote) {
     OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoUpdateQuoteLoadRunnable(idQuote));

   // return null;
  }

  @Override
  public Observable<List<Quote>> toggleQuoteIsFavoritesAsync(int idQuote, boolean update) {
  //  OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoUpdateQuoteLoadRunnable(idQuote));

    toggleQuoteIsFavoritesSync(idQuote);

    quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
    //  List<Quote> quotesByIdAuthor = quotesDaoInterface.findQuotesByIdAuthor(idAuthor);
    Observable<List<Quote>> favoritesQuotesList = quotesDaoInterface.findAllFavoritesQuotes();
    quotesDaoInterface = null;
    //update your own cache
    return favoritesQuotesList;
  }

  private void toggleQuoteIsFavoritesSync(int idQuote) {
    quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
    quotesDaoInterface.toggleQuotesIsFavorites(idQuote);
    quotesDaoInterface = null;
  }

  /**
   * @quotes Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoUpdateQuoteLoadRunnable implements Runnable {
    final int idQuote;

    public DaoUpdateQuoteLoadRunnable(int idQuote) {
      this.idQuote =  idQuote;
    }

    @Override
    public void run() {
      toggleQuoteIsFavoritesSync(idQuote);
    }
  }
}
