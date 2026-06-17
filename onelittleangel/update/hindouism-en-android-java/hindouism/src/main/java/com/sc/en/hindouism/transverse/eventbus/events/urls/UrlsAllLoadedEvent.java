package com.sc.en.hindouism.transverse.eventbus.events.urls;

import com.sc.en.hindouism.transverse.eventbus.models.UrlEventBus;

import java.util.List;

public class UrlsAllLoadedEvent {

  private List<UrlEventBus> urlsAll;

  public UrlsAllLoadedEvent(List<UrlEventBus> urlsAll) {
    this.urlsAll = urlsAll;
  }

  public UrlsAllLoadedEvent() {
  }

  public List<UrlEventBus> getUrls() {
    return urlsAll;
  }

  public void setUrls(List<UrlEventBus> urlsAll) {
    this.urlsAll = urlsAll;
  }
}
