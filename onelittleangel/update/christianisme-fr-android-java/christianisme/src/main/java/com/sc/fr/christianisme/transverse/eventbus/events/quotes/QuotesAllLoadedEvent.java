package com.sc.fr.christianisme.transverse.eventbus.events.quotes;

import com.sc.fr.christianisme.transverse.eventbus.models.QuoteEventBus;

import java.util.List;

public class QuotesAllLoadedEvent {

  private List<QuoteEventBus> quotesAll;

  public QuotesAllLoadedEvent() {
  }

  public QuotesAllLoadedEvent(List<QuoteEventBus> quotesAll) {
    this.quotesAll = quotesAll;
  }

  public List<QuoteEventBus> getQuotes() {
    return quotesAll;
  }

  public void setQuotes(List<QuoteEventBus> quotesAll) {
    this.quotesAll = quotesAll;
  }
}
