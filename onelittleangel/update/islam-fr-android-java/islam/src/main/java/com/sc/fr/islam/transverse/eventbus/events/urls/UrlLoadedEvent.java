package com.sc.fr.islam.transverse.eventbus.events.urls;

import com.sc.fr.islam.transverse.eventbus.models.UrlEventBus;

class UrlLoadedEvent {
  private UrlEventBus url;

  public UrlLoadedEvent() {
  }

  public UrlLoadedEvent(UrlEventBus url) {
    this.url = url;
  }

  public UrlEventBus getUrl() {
    return url;
  }

  public void setUrl(UrlEventBus url) {
    this.url = url;
  }
}
