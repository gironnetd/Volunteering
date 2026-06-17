package com.sc.fr.philosophie.transverse.eventbus.events.authors;


import com.sc.fr.philosophie.transverse.eventbus.models.AuthorEventBus;

public class AuthorByIdAuthorLoadedEvent {
  private AuthorEventBus authorByIdAuthor;
  private int idAuthor;

  public AuthorByIdAuthorLoadedEvent(AuthorEventBus authorByIdAuthor, int idAuthor) {
    this.authorByIdAuthor = authorByIdAuthor;
    this.idAuthor = idAuthor;
  }

  public AuthorByIdAuthorLoadedEvent() {
  }

  public AuthorEventBus getAuthorByIdAuthor() {
    return authorByIdAuthor;
  }

  public void setAuthorByIdAuthor(AuthorEventBus authorByIdAuthor) {
    this.authorByIdAuthor = authorByIdAuthor;
  }

  public int getIdAuthor() {
    return idAuthor;
  }

  public void setIdAuthor(int idAuthor) {
    this.idAuthor = idAuthor;
  }
}
