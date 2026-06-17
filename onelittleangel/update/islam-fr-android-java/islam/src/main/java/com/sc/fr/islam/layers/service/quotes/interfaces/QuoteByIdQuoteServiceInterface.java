package com.sc.fr.islam.layers.service.quotes.interfaces;

import com.sc.fr.islam.transverse.orms.realm.models.Quote;
import com.sc.fr.islam.layers.service.MotherBusinessServiceInterface;

import io.reactivex.Observable;

public interface QuoteByIdQuoteServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idQuote
   */
  Observable<Quote> loadQuoteByIdQuoteAsync(int idQuote);

}
