package com.sc.fr.philosophie.layers.service.quotes.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Quote;

import io.reactivex.Observable;

public interface QuoteByIdQuoteServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idQuote
   */
  Observable<Quote> loadQuoteByIdQuoteAsync(int idQuote);

}
