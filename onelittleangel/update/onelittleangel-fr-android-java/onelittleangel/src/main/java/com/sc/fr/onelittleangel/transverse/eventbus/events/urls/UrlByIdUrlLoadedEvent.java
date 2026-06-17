package com.sc.fr.onelittleangel.transverse.eventbus.events.urls;

import com.sc.fr.onelittleangel.transverse.eventbus.models.UrlEventBus;

public class UrlByIdUrlLoadedEvent {
  private UrlEventBus urlByIdUrl;
  private int idUrl;

  public UrlByIdUrlLoadedEvent(UrlEventBus urlByIdUrl, int idUrl) {
    this.urlByIdUrl = urlByIdUrl;
    this.idUrl = idUrl;
  }

  public UrlByIdUrlLoadedEvent() {
  }

  public UrlEventBus getUrlByIdUrl() {
    return urlByIdUrl;
  }

  public void setUrlByIdUrl(UrlEventBus urlByIdUrl) {
    this.urlByIdUrl = urlByIdUrl;
  }

  public int getIdUrl() {
    return idUrl;
  }

  public void setIdUrl(int idUrl) {
    this.idUrl = idUrl;
  }
}
