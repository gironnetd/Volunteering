package com.sc.fr.christianisme.transverse.eventbus.events.quotes;

import com.sc.fr.christianisme.transverse.orms.realm.models.Quote;

import java.util.List;

public class QuotesByIdAuthorLoadedEvent {
  private List<Quote> quotesByIdAuthor;
  private int idAuthor;

  public QuotesByIdAuthorLoadedEvent(List<Quote> quotesByIdAuthor, int idAuthor) {
    this.quotesByIdAuthor = quotesByIdAuthor;
    this.idAuthor = idAuthor;
  }

  public QuotesByIdAuthorLoadedEvent() {
  }

  public List<Quote> getQuotesByIdAuthor() {
    return quotesByIdAuthor;
  }

  public void setQuotesByIdAuthor(List<Quote> quotesByIdAuthor) {
    this.quotesByIdAuthor = quotesByIdAuthor;
  }

  public int getIdAuthor() {
    return idAuthor;
  }

  public void setIdAuthor(int idAuthor) {
    this.idAuthor = idAuthor;
  }
}
