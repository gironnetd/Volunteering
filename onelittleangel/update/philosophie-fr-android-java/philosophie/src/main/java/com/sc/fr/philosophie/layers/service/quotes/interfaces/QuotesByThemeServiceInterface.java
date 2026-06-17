package com.sc.fr.philosophie.layers.service.quotes.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesByThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param theme
   */
  Observable<List<Quote>> loadQuotesByThemeAsync(String theme);
}
