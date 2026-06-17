package com.sc.fr.christianisme.transverse.eventbus.events.quotes;

import com.sc.fr.christianisme.transverse.eventbus.models.QuoteEventBus;

public class QuoteByIdQuoteLoadedEvent {
  private QuoteEventBus quoteByIdQuote;
  private int idQuote;

  public QuoteByIdQuoteLoadedEvent(QuoteEventBus quoteByIdQuote, int idQuote) {
    this.quoteByIdQuote = quoteByIdQuote;
    this.idQuote = idQuote;
  }

  public QuoteByIdQuoteLoadedEvent() {
  }

  public QuoteEventBus getQuoteByIdQuote() {
    return quoteByIdQuote;
  }

  public void setQuoteByIdQuote(QuoteEventBus quoteByIdQuote) {
    this.quoteByIdQuote = quoteByIdQuote;
  }

  public int getIdQuote() {
    return idQuote;
  }

  public void setIdQuote(int idQuote) {
    this.idQuote = idQuote;
  }
}
