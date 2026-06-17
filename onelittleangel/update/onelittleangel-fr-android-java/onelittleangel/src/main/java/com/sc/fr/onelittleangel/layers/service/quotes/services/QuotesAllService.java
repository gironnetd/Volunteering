package com.sc.fr.onelittleangel.layers.service.quotes.services;

import android.util.Log;
import android.util.SparseArray;

import com.sc.fr.onelittleangel.injector.Injector;
import com.sc.fr.onelittleangel.OnelittleAngelApplication;
import com.sc.fr.onelittleangel.layers.dao.quotes.QuotesDaoInterface;
import com.sc.fr.onelittleangel.layers.service.MotherBusinessService;
import com.sc.fr.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.fr.onelittleangel.layers.service.quotes.interfaces.QuotesAllServiceInterface;
import com.sc.fr.onelittleangel.transverse.eventbus.events.quotes.QuotesAllLoadedEvent;
import com.sc.fr.onelittleangel.transverse.eventbus.models.QuoteEventBus;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Quote;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class QuotesAllService extends MotherBusinessService implements QuotesAllServiceInterface {


  private static final String TAG = "QuotesAllService";

  /**
   *
   */
  private QuotesAllLoadedEvent quotesAllLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public QuotesAllService(ServiceManagerInterface srvManager) {
    super(srvManager);
    /*
    The quotes to display (the cache)
   */
    SparseArray<Quote> quotesList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * Load all the quotes from the database asynchronously
   */
  @Override
  public void loadAllQuotesAsync() {
    Log.d(TAG, "loadQuotes() called with: " + "");
    OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindAllQuotesRunnable);
  }

  private void loadAllQuotesSync() {
    // Load data from DB
    /*

   */
    QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
    //send back the answer using eventBus
    postAllQuotesLoadedEvent(quotesDaoInterface.findAllQuotes());
    quotesDaoInterface = null;
  }

  /**
   * The runnable to execute
   */
  private final DaoFindAllQuotesRunnable daoFindAllQuotesRunnable = new DaoFindAllQuotesRunnable();

  /**
   * @quote Mathias Seguy (Android2EE)
   * @goals This class aims to implements a Runnable
   */
  private class DaoFindAllQuotesRunnable implements Runnable {
    @Override
    public void run() {
      loadAllQuotesSync();
    }
  }

  /**
   * Broadcast all the quotes Loaded event
   */
  private void postAllQuotesLoadedEvent(List<Quote> quotesAll) {

    List<QuoteEventBus> quotes = new ArrayList<>();

    for (Quote a : quotesAll) {
      quotes.add(new QuoteEventBus(a));
    }

    if (quotesAllLoadedEvent == null) {
      quotesAllLoadedEvent = new QuotesAllLoadedEvent(quotes);
    } else {
      quotesAllLoadedEvent.setQuotes(quotes);
    }
    Log.e(TAG, "postCitiesLoadedEvent posted");
    EventBus.getDefault().post(quotesAllLoadedEvent);
  }
}