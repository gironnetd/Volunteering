package com.sc.fr.confucianisme.transverse.eventbus.events.quotes;

import com.sc.fr.confucianisme.transverse.eventbus.models.QuoteEventBus;

import java.util.List;

public class QuotesByIdBookLoadedEvent {
  private List<QuoteEventBus> quotesByIdBook;
  private int idBook;

  public QuotesByIdBookLoadedEvent(List<QuoteEventBus> quotesByIdBook, int idBook) {
    this.quotesByIdBook = quotesByIdBook;
    this.idBook = idBook;
  }

  public QuotesByIdBookLoadedEvent() {
  }

  public List<QuoteEventBus> getQuotesByIdBook() {
    return quotesByIdBook;
  }

  public void setQuotesByIdBook(List<QuoteEventBus> quotesByIdBook) {
    this.quotesByIdBook = quotesByIdBook;
  }

  public int getIdBook() {
    return idBook;
  }

  public void setIdBook(int idBook) {
    this.idBook = idBook;
  }
}
