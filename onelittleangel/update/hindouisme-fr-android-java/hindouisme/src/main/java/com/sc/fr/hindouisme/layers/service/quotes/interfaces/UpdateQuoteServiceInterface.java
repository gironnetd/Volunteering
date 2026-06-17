package com.sc.fr.hindouisme.layers.service.quotes.interfaces;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.hindouisme.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface UpdateQuoteServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idQuote
   */
  void toggleQuoteIsFavoritesAsync(int idQuote);

  Observable<List<Quote>> toggleQuoteIsFavoritesAsync(int idQuote, boolean update);

}
