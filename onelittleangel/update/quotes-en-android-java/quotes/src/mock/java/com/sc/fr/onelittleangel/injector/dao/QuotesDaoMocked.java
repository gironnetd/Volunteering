package com.sc.fr.onelittleangel.injector.dao;

import com.sc.fr.onelittleangel.layers.dao.quotes.QuotesDaoInterface;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by damien on 28/12/2016 for OnelittleAngel Android project.
 */

public class QuotesDaoMocked implements QuotesDaoInterface {
  @Override
  public Quote findQuoteByIdQuote(int idQuote) {
    return null;
  }

  @Override
  public List<Quote> findQuotesByAuthor(String name) {
    return null;
  }

  @Override
  public Observable<List<Quote>> findQuotesByIdAuthor(int idAuthor) {
    return null;
  }

  @Override
  public Observable<List<Quote>> findQuotesByBook(String name) {
    return null;
  }


  @Override
  public List<Quote> findQuotesByIdBook(int idBook) {
    return null;
  }

  @Override
  public Observable<List<Quote>> findQuotesByTheme(String theme) {
    return null;
  }

  @Override
  public List<Quote> findQuotesByIdTheme(int idTheme) {
    return null;
  }

  @Override
  public Observable<List<Quote>> findQuotesByMovement(String movement) {
    return null;
  }

  @Override
  public List<Quote> findQuotesByIdMovement(int idMovement) {
    return null;
  }

  @Override
  public List<Quote> findAllQuotes() {
    return null;
  }

  @Override
  public void toggleQuotesIsFavorites(int idQuote) {

  }

  @Override
  public Observable<List<Quote>> findAllFavoritesQuotes() {
    return null;
  }

  @Override
  public Quote findQuoteByRandom() {
    return null;
  }
}
