package com.sc.en.islam.layers.service.quotes.services;

import android.util.SparseArray;

import com.sc.en.islam.OnelittleAngelApplication;
import com.sc.en.islam.layers.dao.quotes.QuotesDaoInterface;
import com.sc.en.islam.layers.service.MotherBusinessService;
import com.sc.en.islam.layers.service.quotes.interfaces.QuotesByIdMovementServiceInterface;
import com.sc.en.islam.transverse.orms.realm.models.Quote;
import com.sc.en.islam.injector.Injector;
import com.sc.en.islam.layers.service.ServiceManagerInterface;
import com.sc.en.islam.transverse.eventbus.events.quotes.QuotesByIdMovementLoadedEvent;
import com.sc.en.islam.transverse.eventbus.models.QuoteEventBus;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class QuotesByIdMovementService extends MotherBusinessService implements QuotesByIdMovementServiceInterface {

  private static final String TAG = "QuotesByIdMovementService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Quote>> quotesByIdMovementList = null;

  /**
   *
   */
  private QuotesByIdMovementLoadedEvent quotesByIdMovementLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public QuotesByIdMovementService(ServiceManagerInterface srvManager) {
    super(srvManager);
    quotesByIdMovementList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    quotesByIdMovementList = null;
  }

  /**
   * @param idMovement
   */
  @Override
  public void loadQuotesByIdMovementAsync(int idMovement) {
    //  Log.e(TAG, "loadQuoteByIdMovementAsync() called with: " + "cityId = [" + idMovement + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (quotesByIdMovementList.get(idMovement)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postQuoteByIdMovementDataLoadedEvent(quotesByIdMovementList.get(idMovement),idMovement);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new QuotesByIdMovementService.DaoQuoteByIdMovementLoadRunnable(idMovement));
    }
  }

  private void loadQuoteByIdMovementSync(int idMovement){
    // Log.e(TAG, "loadQuoteByIdMovementSync() called with: " + "idMovement = [" + idMovement + "]");
    // Load data from database
    /*

   */
    QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
    List<Quote> quotesByIdMovement = quotesDaoInterface.findQuotesByIdMovement(idMovement);
    quotesDaoInterface = null;
    //update your own cache
    quotesByIdMovementList.put(idMovement,quotesByIdMovement);
    //send send back the answer using eventBus
    postQuoteByIdMovementDataLoadedEvent(quotesByIdMovement,idMovement);
  }

  /**
   * @quote Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoQuoteByIdMovementLoadRunnable implements Runnable {
    final int idMovement;

    public DaoQuoteByIdMovementLoadRunnable(int idMovement) {
      this.idMovement = idMovement;
    }

    @Override
    public void run() {
      loadQuoteByIdMovementSync(idMovement);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postQuoteByIdMovementDataLoadedEvent(List<Quote> quotesByIdMovement,int idMovement) {
    //  Log.e(TAG, "postQuoteByIdMovementDataLoadedEvent() called as found Quote with " +
    //    quoteByIdMovementLoadedEvent.getQuoteByIdMovement().getQuotes().size() + " elements and idMovement="+idMovement);
    List<QuoteEventBus> quotes = new ArrayList<>();

    for(Quote q : quotesByIdMovement){
      quotes.add(new QuoteEventBus(q));
    }

    if(quotesByIdMovementLoadedEvent==null){
      quotesByIdMovementLoadedEvent= new QuotesByIdMovementLoadedEvent(quotes, idMovement);
    }else{
      quotesByIdMovementLoadedEvent.setQuotesByIdMovement(quotes);
      quotesByIdMovementLoadedEvent.setIdMovement(idMovement);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + quoteByIdMovementLoadedEvent.getQuoteByIdMovement() + " elements and idMovement ="+quoteByIdMovementLoadedEvent.getIdMovement());
    EventBus.getDefault().post(quotesByIdMovementLoadedEvent);
  }

}
