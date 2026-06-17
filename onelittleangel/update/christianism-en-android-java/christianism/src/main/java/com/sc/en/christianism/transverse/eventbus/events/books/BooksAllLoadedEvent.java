package com.sc.en.christianism.transverse.eventbus.events.books;

import com.sc.en.christianism.transverse.eventbus.models.BookEventBus;

import java.util.List;

public class BooksAllLoadedEvent {

  private List<BookEventBus> booksAll;

  public BooksAllLoadedEvent() {
  }

  public BooksAllLoadedEvent(List<BookEventBus> booksAll) {
    this.booksAll = booksAll;
  }

  public List<BookEventBus> getBooks() {
    return booksAll;
  }

  public void setBooks(List<BookEventBus> booksAll) {
    this.booksAll = booksAll;
  }
}
