package com.sc.en.christianism.layers.service.quotes.interfaces;

import com.sc.en.christianism.transverse.orms.realm.models.Quote;
import com.sc.en.christianism.layers.service.MotherBusinessServiceInterface;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesByMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param movement
   */
  Observable<List<Quote>> loadQuotesByMovementAsync(String movement);
}
