package com.sc.en.confucianism.layers.service.quotes.interfaces;

import com.sc.en.confucianism.transverse.orms.realm.models.Quote;
import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

import io.reactivex.Observable;

public interface QuoteByIdQuoteServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idQuote
   */
  Observable<Quote> loadQuoteByIdQuoteAsync(int idQuote);

}
