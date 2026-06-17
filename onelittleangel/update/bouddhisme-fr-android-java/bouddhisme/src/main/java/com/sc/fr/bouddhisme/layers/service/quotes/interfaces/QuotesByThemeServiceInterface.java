package com.sc.fr.bouddhisme.layers.service.quotes.interfaces;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesByThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param theme
   */
  Observable<List<Quote>> loadQuotesByThemeAsync(String theme);
}
