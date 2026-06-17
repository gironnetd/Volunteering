package com.sc.fr.islam.layers.service.quotes.interfaces;

import com.sc.fr.islam.transverse.orms.realm.models.Quote;
import com.sc.fr.islam.layers.service.MotherBusinessServiceInterface;

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
