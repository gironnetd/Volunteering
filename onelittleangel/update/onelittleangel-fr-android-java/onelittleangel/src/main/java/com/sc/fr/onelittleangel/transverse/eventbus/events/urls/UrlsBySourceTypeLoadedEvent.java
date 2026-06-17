package com.sc.fr.onelittleangel.transverse.eventbus.events.urls;

import com.sc.fr.onelittleangel.transverse.eventbus.models.UrlEventBus;

import java.util.List;

public class UrlsBySourceTypeLoadedEvent {
  private List<UrlEventBus> urlsBySourceType;
  private String sourceType;

  public UrlsBySourceTypeLoadedEvent(List<UrlEventBus> urlsBySourceType, String sourceType) {
    this.urlsBySourceType = urlsBySourceType;
    this.sourceType = sourceType;
  }

  public UrlsBySourceTypeLoadedEvent() {
  }

  public List<UrlEventBus> getUrlsBySourceType() {
    return urlsBySourceType;
  }

  public void setUrlsBySourceType(List<UrlEventBus> urlsBySourceType) {
    this.urlsBySourceType = urlsBySourceType;
  }

  public String getSourceType() {
    return sourceType;
  }

  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
  }
}
