package com.sc.fr.islam.layers.service.quotes.interfaces;

import com.sc.fr.islam.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.islam.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesByBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  Observable<List<Quote>> loadQuotesByBookAsync(String name);
}
