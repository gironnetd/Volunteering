package com.sc.fr.onelittleangel.transverse.eventbus.events.books;

import com.sc.fr.onelittleangel.transverse.eventbus.models.BookEventBus;

import java.util.List;

public class BooksByIdThemeLoadedEvent {
  private List<BookEventBus> booksByIdTheme;
  private int idTheme;

  public BooksByIdThemeLoadedEvent(List<BookEventBus> booksByIdTheme, int idTheme) {
    this.booksByIdTheme = booksByIdTheme;
    this.idTheme = idTheme;
  }

  public BooksByIdThemeLoadedEvent() {
  }

  public List<BookEventBus> getBooksByIdTheme() {
    return booksByIdTheme;
  }

  public void setBooksByIdTheme(List<BookEventBus> booksByIdTheme) {
    this.booksByIdTheme = booksByIdTheme;
  }

  public int getIdTheme() {
    return idTheme;
  }

  public void setIdTheme(int idTheme) {
    this.idTheme = idTheme;
  }
}
