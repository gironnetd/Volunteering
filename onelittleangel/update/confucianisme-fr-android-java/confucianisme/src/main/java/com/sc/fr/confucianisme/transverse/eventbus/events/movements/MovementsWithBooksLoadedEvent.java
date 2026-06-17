package com.sc.fr.confucianisme.transverse.eventbus.events.movements;

import com.sc.fr.confucianisme.layers.mvp.tablecontents.models.AuthorBook;

import java.util.List;

public class MovementsWithBooksLoadedEvent {

  private List<AuthorBook> movementsWithBooks;

  public MovementsWithBooksLoadedEvent() {
  }

  public MovementsWithBooksLoadedEvent(List<AuthorBook> movementsWithBooks) {
    this.movementsWithBooks = movementsWithBooks;
  }

  public List<AuthorBook> getMovementsWithBooks() {
    return movementsWithBooks;
  }

  public void setMovementsWithBooks(List<AuthorBook> movementsWithBooks) {
    this.movementsWithBooks = movementsWithBooks;
  }
}
