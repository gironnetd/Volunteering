package com.sc.fr.islam.transverse.eventbus.events.authors;

import com.sc.fr.islam.transverse.eventbus.models.AuthorEventBus;

public class AuthorLoadedEvent {
  private AuthorEventBus author;

  public AuthorLoadedEvent() {
  }

  public AuthorLoadedEvent(AuthorEventBus author) {
    this.author = author;
  }

  public AuthorEventBus getAuthor() {
    return author;
  }

  public void setAuthor(AuthorEventBus author) {
    this.author = author;
  }
}
