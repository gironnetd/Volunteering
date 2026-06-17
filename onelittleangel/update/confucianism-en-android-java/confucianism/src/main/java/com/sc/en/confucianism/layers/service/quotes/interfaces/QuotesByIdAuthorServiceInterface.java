package com.sc.en.confucianism.layers.service.quotes.interfaces;

import com.sc.en.confucianism.transverse.orms.realm.models.Quote;
import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  Observable<List<Quote>> loadQuotesByIdAuthorAsync(int idAuthor);
}
