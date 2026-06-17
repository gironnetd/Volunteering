package com.sc.fr.christianisme.transverse.eventbus.events.quotes;

import com.sc.fr.christianisme.transverse.eventbus.models.QuoteEventBus;

import java.util.List;

public class QuotesByIdThemeLoadedEvent {
  private List<QuoteEventBus> quotesByIdTheme;
  private int idTheme;

  public QuotesByIdThemeLoadedEvent(List<QuoteEventBus> quotesByIdTheme, int idTheme) {
    this.quotesByIdTheme = quotesByIdTheme;
    this.idTheme = idTheme;
  }

  public QuotesByIdThemeLoadedEvent() {
  }

  public List<QuoteEventBus> getQuotesByIdTheme() {
    return quotesByIdTheme;
  }

  public void setQuotesByIdTheme(List<QuoteEventBus> quotesByIdTheme) {
    this.quotesByIdTheme = quotesByIdTheme;
  }

  public int getIdTheme() {
    return idTheme;
  }

  public void setIdTheme(int idTheme) {
    this.idTheme = idTheme;
  }
}
