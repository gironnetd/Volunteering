package com.sc.en.philosophy.transverse.eventbus.events.books;

import com.sc.en.philosophy.transverse.eventbus.models.BookEventBus;

public class BookByNameLoadedEvent {
  private BookEventBus bookByName;
  private String name;

  public BookByNameLoadedEvent(BookEventBus bookByName, String name) {
    this.bookByName = bookByName;
    this.name = name;
  }

  public BookByNameLoadedEvent() {
  }

  public BookEventBus getBookByName() {
    return bookByName;
  }

  public void setBookByName(BookEventBus bookByName) {
    this.bookByName = bookByName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
