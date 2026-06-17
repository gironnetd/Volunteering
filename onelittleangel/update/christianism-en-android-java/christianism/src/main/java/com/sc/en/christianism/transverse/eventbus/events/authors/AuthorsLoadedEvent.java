package com.sc.en.christianism.transverse.eventbus.events.authors;

import com.sc.en.christianism.transverse.eventbus.models.AuthorEventBus;

import java.util.List;

public class AuthorsLoadedEvent {

  private List<AuthorEventBus> authors;

  public AuthorsLoadedEvent() {
  }

  public AuthorsLoadedEvent(List<AuthorEventBus> authors) {
    this.authors = authors;
  }

  public List<AuthorEventBus> getAuthors() {
    return authors;
  }

  public void setAuthors(List<AuthorEventBus> authors) {
    this.authors = authors;
  }
}
