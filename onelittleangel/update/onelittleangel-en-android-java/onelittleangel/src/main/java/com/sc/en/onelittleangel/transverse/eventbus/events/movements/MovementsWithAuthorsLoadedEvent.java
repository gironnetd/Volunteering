package com.sc.en.onelittleangel.transverse.eventbus.events.movements;

import com.sc.en.onelittleangel.layers.mvp.tablecontents.models.AuthorBook;

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
