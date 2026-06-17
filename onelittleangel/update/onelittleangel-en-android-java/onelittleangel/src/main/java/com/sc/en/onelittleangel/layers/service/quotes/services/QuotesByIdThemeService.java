package com.sc.en.onelittleangel.layers.service.quotes.services;

import android.util.SparseArray;

import com.sc.en.onelittleangel.layers.dao.quotes.QuotesDaoInterface;
import com.sc.en.onelittleangel.layers.service.MotherBusinessService;
import com.sc.en.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.en.onelittleangel.layers.service.quotes.interfaces.QuotesByIdThemeServiceInterface;
import com.sc.en.onelittleangel.transverse.eventbus.events.quotes.QuotesByIdThemeLoadedEvent;
import com.sc.en.onelittleangel.transverse.eventbus.models.QuoteEventBus;
import com.sc.en.onelittleangel.transverse.orms.realm.models.Quote;
import com.sc.en.onelittleangel.injector.Injector;
import com.sc.en.onelittleangel.OnelittleAngelApplication;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class QuotesByIdThemeService extends MotherBusinessService implements QuotesByIdThemeServiceInterface {

  private static final String TAG = "QuotesByIdThemeService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Quote>> quotesByIdThemeList = null;

  /**
   *
   */
  private QuotesByIdThemeLoadedEvent quotesByIdThemeLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public QuotesByIdThemeService(ServiceManagerInterface srvManager) {
    super(srvManager);
    quotesByIdThemeList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    quotesByIdThemeList = null;
  }

  /**
   * @param idTheme
   */
  @Override
  public void loadQuotesByIdThemeAsync(int idTheme) {
    //  Log.e(TAG, "loadQuoteByIdThemeAsync() called with: " + "cityId = [" + idTheme + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (quotesByIdThemeList.get(idTheme)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postQuoteByIdThemeDataLoadedEvent(quotesByIdThemeList.get(idTheme),idTheme);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new QuotesByIdThemeService.DaoQuoteByIdThemeLoadRunnable(idTheme));
    }
  }

  private void loadQuoteByIdThemeSync(int idTheme){
    // Log.e(TAG, "loadQuoteByIdThemeSync() called with: " + "idTheme = [" + idTheme + "]");
    // Load data from database
    /*

   */
    QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
    List<Quote> quotesByIdTheme = quotesDaoInterface.findQuotesByIdTheme(idTheme);
    quotesDaoInterface = null;
    //update your own cache
    quotesByIdThemeList.put(idTheme,quotesByIdTheme);
    //send send back the answer using eventBus
    postQuoteByIdThemeDataLoadedEvent(quotesByIdTheme,idTheme);
  }

  /**
   * @quote Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoQuoteByIdThemeLoadRunnable implements Runnable {
    final int idTheme;

    public DaoQuoteByIdThemeLoadRunnable(int idTheme) {
      this.idTheme = idTheme;
    }

    @Override
    public void run() {
      loadQuoteByIdThemeSync(idTheme);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postQuoteByIdThemeDataLoadedEvent(List<Quote> quotesByIdTheme,int idTheme) {
    //  Log.e(TAG, "postQuoteByIdThemeDataLoadedEvent() called as found Quote with " +
    //    quoteByIdThemeLoadedEvent.getQuoteByIdTheme().getQuotes().size() + " elements and idTheme="+idTheme);
    List<QuoteEventBus> quotes = new ArrayList<>();

    for(Quote q : quotesByIdTheme){
      quotes.add(new QuoteEventBus(q));
    }

    if(quotesByIdThemeLoadedEvent==null){
      quotesByIdThemeLoadedEvent= new QuotesByIdThemeLoadedEvent(quotes, idTheme);
    }else{
      quotesByIdThemeLoadedEvent.setQuotesByIdTheme(quotes);
      quotesByIdThemeLoadedEvent.setIdTheme(idTheme);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + quoteByIdThemeLoadedEvent.getQuoteByIdTheme() + " elements and idTheme ="+quoteByIdThemeLoadedEvent.getIdTheme());
    EventBus.getDefault().post(quotesByIdThemeLoadedEvent);
  }

}
