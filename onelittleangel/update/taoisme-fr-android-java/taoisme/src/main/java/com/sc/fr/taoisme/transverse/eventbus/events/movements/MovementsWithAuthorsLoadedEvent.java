package com.sc.fr.taoisme.transverse.eventbus.events.movements;

import com.sc.fr.taoisme.layers.mvp.tablecontents.models.AuthorBook;

import java.util.List;

public class MovementsWithAuthorsLoadedEvent {

  private List<AuthorBook> authorBooks;

  public MovementsWithAuthorsLoadedEvent() {
  }

  public MovementsWithAuthorsLoadedEvent(List<AuthorBook> authorBooks) {
    this.authorBooks = authorBooks;
  }

  public List<AuthorBook> getAuthorBooks() {
    return authorBooks;
  }

  public void setAuthorBooks(List<AuthorBook> authorBooks) {
    this.authorBooks = authorBooks;
  }
}
