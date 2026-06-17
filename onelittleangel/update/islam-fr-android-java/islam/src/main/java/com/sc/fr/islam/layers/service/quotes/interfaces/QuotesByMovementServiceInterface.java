package com.sc.fr.islam.layers.service.quotes.interfaces;

import com.sc.fr.islam.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.islam.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesByMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param movement
   */
  Observable<List<Quote>> loadQuotesByMovementAsync(String movement);
}
