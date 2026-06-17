package com.sc.en.hindouism.layers.service.quotes.interfaces;

import com.sc.en.hindouism.transverse.orms.realm.models.Quote;
import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesByMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param movement
   */
  Observable<List<Quote>> loadQuotesByMovementAsync(String movement);
}
