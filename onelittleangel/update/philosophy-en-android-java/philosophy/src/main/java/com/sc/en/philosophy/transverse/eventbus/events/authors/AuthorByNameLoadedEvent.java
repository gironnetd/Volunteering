package com.sc.en.philosophy.transverse.eventbus.events.authors;

import com.sc.en.philosophy.transverse.eventbus.models.AuthorEventBus;

public class AuthorByNameLoadedEvent {
  private AuthorEventBus authorByName;
  private String name;

  public AuthorByNameLoadedEvent(AuthorEventBus authorByName, String name) {
    this.authorByName = authorByName;
    this.name = name;
  }

  public AuthorByNameLoadedEvent() {
  }

  public AuthorEventBus getAuthorByName() {
    return authorByName;
  }

  public void setAuthorByName(AuthorEventBus authorByName) {
    this.authorByName = authorByName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
