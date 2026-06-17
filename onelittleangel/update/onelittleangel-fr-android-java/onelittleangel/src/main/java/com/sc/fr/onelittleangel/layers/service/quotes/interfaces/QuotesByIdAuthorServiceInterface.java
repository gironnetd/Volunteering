package com.sc.fr.onelittleangel.layers.service.quotes.interfaces;

import com.sc.fr.onelittleangel.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  Observable<List<Quote>> loadQuotesByIdAuthorAsync(int idAuthor);
}
