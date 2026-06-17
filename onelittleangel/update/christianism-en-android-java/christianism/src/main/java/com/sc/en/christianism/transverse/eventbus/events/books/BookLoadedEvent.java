package com.sc.en.christianism.transverse.eventbus.events.books;

import com.sc.en.christianism.transverse.eventbus.models.BookEventBus;

class BookLoadedEvent {
  private BookEventBus book;

  public BookLoadedEvent() {
  }

  public BookLoadedEvent(BookEventBus book) {
    this.book = book;
  }

  public BookEventBus getBook() {
    return book;
  }

  public void setBook(BookEventBus book) {
    this.book = book;
  }
}
