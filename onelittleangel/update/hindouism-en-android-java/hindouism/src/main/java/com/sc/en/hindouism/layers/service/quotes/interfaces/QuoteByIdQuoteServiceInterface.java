package com.sc.en.hindouism.layers.service.quotes.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.hindouism.transverse.orms.realm.models.Quote;

import io.reactivex.Observable;

public interface QuoteByIdQuoteServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idQuote
   */
  Observable<Quote> loadQuoteByIdQuoteAsync(int idQuote);

}
