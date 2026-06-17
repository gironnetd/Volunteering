package com.sc.en.islam.transverse.eventbus.events.urls;

import com.sc.en.islam.transverse.eventbus.models.UrlEventBus;

import java.util.List;

public class UrlsByIdBookLoadedEvent {
  private List<UrlEventBus> urlsByIdBook;
  private int idBook;

  public UrlsByIdBookLoadedEvent(List<UrlEventBus> urlsByIdBook, int idBook) {
    this.urlsByIdBook = urlsByIdBook;
    this.idBook = idBook;
  }

  public UrlsByIdBookLoadedEvent() {
  }

  public List<UrlEventBus> getUrlsByIdBook() {
    return urlsByIdBook;
  }

  public void setUrlsByIdBook(List<UrlEventBus> urlsByIdBook) {
    this.urlsByIdBook = urlsByIdBook;
  }

  public int getIdBook() {
    return idBook;
  }

  public void setIdBook(int idBook) {
    this.idBook = idBook;
  }
}
