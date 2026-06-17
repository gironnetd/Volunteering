package com.sc.fr.bouddhisme.transverse.eventbus.events.quotes;

import com.sc.fr.bouddhisme.transverse.eventbus.models.QuoteEventBus;

class QuoteLoadedEvent {
  private QuoteEventBus quote;

  public QuoteLoadedEvent() {
  }

  public QuoteLoadedEvent(QuoteEventBus quote) {
    this.quote = quote;
  }

  public QuoteEventBus getQuote() {
    return quote;
  }

  public void setQuote(QuoteEventBus quote) {
    this.quote = quote;
  }
}
