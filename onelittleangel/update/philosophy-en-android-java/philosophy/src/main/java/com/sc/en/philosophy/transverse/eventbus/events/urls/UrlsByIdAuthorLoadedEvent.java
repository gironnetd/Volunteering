package com.sc.en.philosophy.transverse.eventbus.events.urls;

import com.sc.en.philosophy.transverse.eventbus.models.UrlEventBus;

import java.util.List;

public class UrlsByIdAuthorLoadedEvent {
  private List<UrlEventBus> urlsByIdAuthor;
  private int idAuthor;

  public UrlsByIdAuthorLoadedEvent(List<UrlEventBus> urlsByIdAuthor, int idAuthor) {
    this.urlsByIdAuthor = urlsByIdAuthor;
    this.idAuthor = idAuthor;
  }

  public UrlsByIdAuthorLoadedEvent() {
  }

  public List<UrlEventBus> getUrlsByIdAuthor() {
    return urlsByIdAuthor;
  }

  public void setUrlsByIdAuthor(List<UrlEventBus> urlsByIdAuthor) {
    this.urlsByIdAuthor = urlsByIdAuthor;
  }

  public int getIdAuthor() {
    return idAuthor;
  }

  public void setIdAuthor(int idAuthor) {
    this.idAuthor = idAuthor;
  }
}
