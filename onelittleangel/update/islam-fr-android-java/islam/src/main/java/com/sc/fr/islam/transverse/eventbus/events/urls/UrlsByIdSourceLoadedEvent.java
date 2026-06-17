package com.sc.fr.islam.transverse.eventbus.events.urls;

import com.sc.fr.islam.transverse.eventbus.models.UrlEventBus;

import java.util.List;

public class UrlsByIdSourceLoadedEvent {
  private List<UrlEventBus> urlsByIdSource;
  private int idSource;
  private String sourceType;

  public UrlsByIdSourceLoadedEvent(List<UrlEventBus> urlsByIdSource, int idSource, String sourceType) {
    this.urlsByIdSource = urlsByIdSource;
    this.idSource = idSource;
    this.sourceType = sourceType;
  }

  public UrlsByIdSourceLoadedEvent() {
  }

  public List<UrlEventBus> getUrlsByIdSource() {
    return urlsByIdSource;
  }

  public void setUrlsByIdSource(List<UrlEventBus> urlsByIdSource) {
    this.urlsByIdSource = urlsByIdSource;
  }

  public int getIdSource() {
    return idSource;
  }

  public void setIdSource(int idSource) {
    this.idSource = idSource;
  }

  public String getSourceType() {
    return sourceType;
  }

  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
  }
}
