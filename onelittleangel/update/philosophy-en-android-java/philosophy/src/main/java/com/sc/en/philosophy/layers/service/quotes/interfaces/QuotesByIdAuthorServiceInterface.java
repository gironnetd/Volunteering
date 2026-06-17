package com.sc.en.philosophy.layers.service.quotes.interfaces;

import com.sc.en.philosophy.transverse.orms.realm.models.Quote;
import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  Observable<List<Quote>> loadQuotesByIdAuthorAsync(int idAuthor);
}
