package com.sc.en.christianism.layers.service.quotes.interfaces;

import com.sc.en.christianism.transverse.orms.realm.models.Quote;
import com.sc.en.christianism.layers.service.MotherBusinessServiceInterface;

import io.reactivex.Observable;

public interface QuoteByIdQuoteServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idQuote
   */
  Observable<Quote> loadQuoteByIdQuoteAsync(int idQuote);

}
