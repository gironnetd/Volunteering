package com.sc.en.bouddhism.layers.service.quotes.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.bouddhism.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesByAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  Observable<List<Quote>> loadQuotesByAuthorAsync(String name);
}
