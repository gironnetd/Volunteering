package com.sc.fr.christianisme.layers.service.quotes.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.christianisme.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesByThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param theme
   */
  Observable<List<Quote>> loadQuotesByThemeAsync(String theme);
}
