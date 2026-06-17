package com.sc.fr.confucianisme.transverse.eventbus.events.books;

import com.sc.fr.confucianisme.transverse.eventbus.models.BookEventBus;

import java.util.List;

public class BooksByIdMovementLoadedEvent {
  private List<BookEventBus> booksByIdMovement;
  private int idMovement;

  public BooksByIdMovementLoadedEvent(List<BookEventBus> booksByIdMovement, int idMovement) {
    this.booksByIdMovement = booksByIdMovement;
    this.idMovement = idMovement;
  }

  public BooksByIdMovementLoadedEvent() {
  }

  public List<BookEventBus> getBooksByIdMovement() {
    return booksByIdMovement;
  }

  public void setBooksByIdMovement(List<BookEventBus> booksByIdMovement) {
    this.booksByIdMovement = booksByIdMovement;
  }

  public int getIdMovement() {
    return idMovement;
  }

  public void setIdMovement(int idMovement) {
    this.idMovement = idMovement;
  }
}
