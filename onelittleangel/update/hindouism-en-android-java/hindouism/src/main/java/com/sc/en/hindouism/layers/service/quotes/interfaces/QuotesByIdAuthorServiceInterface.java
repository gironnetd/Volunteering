package com.sc.en.hindouism.layers.service.quotes.interfaces;

import com.sc.en.hindouism.transverse.orms.realm.models.Quote;
import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  Observable<List<Quote>> loadQuotesByIdAuthorAsync(int idAuthor);
}
