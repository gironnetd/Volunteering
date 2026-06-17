package com.sc.en.quotes.transverse.eventbus.events.authors;

import com.sc.en.quotes.transverse.eventbus.models.AuthorEventBus;

import java.util.List;

public class AuthorsByIdThemeLoadedEvent {
  private List<AuthorEventBus> authorsByIdTheme;
  private int idTheme;

  public AuthorsByIdThemeLoadedEvent(List<AuthorEventBus> authorsByIdTheme, int idTheme) {
    this.authorsByIdTheme = authorsByIdTheme;
    this.idTheme = idTheme;
  }

  public AuthorsByIdThemeLoadedEvent() {
  }

  public List<AuthorEventBus> getAuthorsByIdTheme() {
    return authorsByIdTheme;
  }

  public void setAuthorsByIdTheme(List<AuthorEventBus> authorsByIdTheme) {
    this.authorsByIdTheme = authorsByIdTheme;
  }

  public int getIdTheme() {
    return idTheme;
  }

  public void setIdTheme(int idTheme) {
    this.idTheme = idTheme;
  }
}
