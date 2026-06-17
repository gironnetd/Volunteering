package com.sc.en.christianism.transverse.eventbus.events.books;


import com.sc.en.christianism.transverse.eventbus.models.BookEventBus;

public class BookByIdBookLoadedEvent {
  private BookEventBus bookByIdBook;
  private int idBook;

  public BookByIdBookLoadedEvent(BookEventBus bookByIdBook, int idBook) {
    this.bookByIdBook = bookByIdBook;
    this.idBook = idBook;
  }

  public BookByIdBookLoadedEvent() {
  }

  public BookEventBus getBookByIdBook() {
    return bookByIdBook;
  }

  public void setBookByIdBook(BookEventBus bookByIdBook) {
    this.bookByIdBook = bookByIdBook;
  }

  public int getIdBook() {
    return idBook;
  }

  public void setIdBook(int idBook) {
    this.idBook = idBook;
  }
}
