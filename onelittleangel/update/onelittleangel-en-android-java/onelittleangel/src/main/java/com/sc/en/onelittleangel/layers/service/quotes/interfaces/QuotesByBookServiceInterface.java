package com.sc.en.onelittleangel.layers.service.quotes.interfaces;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;
import com.sc.en.onelittleangel.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesByBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  Observable<List<Quote>> loadQuotesByBookAsync(String name);
}
