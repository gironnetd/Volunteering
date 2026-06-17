package com.sc.fr.taoisme.layers.dao.quotes;

import com.sc.fr.taoisme.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesDaoInterface {

  /**
   *
   * @param idQuote
   * @return
   */
  Quote findQuoteByIdQuote(int idQuote);

  /**
   *
   * @param name
   * @return
   */
  List<Quote> findQuotesByAuthor(String name);

  /**
   *
   * @param idAuthor
   * @return
   */
  Observable<List<Quote>> findQuotesByIdAuthor(int idAuthor);

  /**
   *
   * @param name
   * @return
   */
  Observable<List<Quote>> findQuotesByBook(String name);

  /**
   *
   * @param idBook
   * @return
   */
  List<Quote> findQuotesByIdBook(int idBook);

  /**
   *
   * @param theme
   * @return
   */
  Observable<List<Quote>> findQuotesByTheme(String theme);

  /**
   *
   * @param idTheme
   * @return
   */
  List<Quote> findQuotesByIdTheme(int idTheme);

  /**
   *
   * @param movement
   * @return
   */
  Observable<List<Quote>> findQuotesByMovement(String movement);

  /**
   *
   * @param idMovement
   * @return
   */
  List<Quote> findQuotesByIdMovement(int idMovement);


  /**
   *
   * @return
   */
  List<Quote> findAllQuotes();

  /**
   *
   * @param idQuote
   */
  void toggleQuotesIsFavorites(int idQuote);

  /**
   *
   * @return
   */
  Observable<List<Quote>> findAllFavoritesQuotes();

  /**
   *
   * @return
   */
  Quote findQuoteByRandom();
}
