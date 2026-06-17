package com.sc.en.taoism.layers.service.quotes.interfaces;

import com.sc.en.taoism.transverse.orms.realm.models.Quote;
import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesByMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param movement
   */
  Observable<List<Quote>> loadQuotesByMovementAsync(String movement);
}
