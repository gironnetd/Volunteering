package com.sc.fr.confucianisme.transverse.eventbus.models;

import com.sc.fr.confucianisme.transverse.orms.realm.models.Url;

public class UrlEventBus {

  private long idUrl;
  private String sourceType;
  private long idSource;
  private String title;
  private String url;
  private String presentation;

  public UrlEventBus() {
  }

  public UrlEventBus(Url urlRealm){
    idUrl = urlRealm.getIdUrl();
    sourceType = urlRealm.getSourceType();
    title = urlRealm.getTitle();
    url = urlRealm.getUrl();
    presentation = urlRealm.getPresentation();
  }


  public long getIdUrl() {
    return idUrl;
  }

  public void setIdUrl(long idUrl) {
    this.idUrl = idUrl;
  }

  public String getSourceType() {
    return sourceType;
  }

  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
  }

  public long getIdSource() {
    return idSource;
  }

  public void setIdSource(long idSource) {
    this.idSource = idSource;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPresentation() {
    return presentation;
  }

  public void setPresentation(String presentation) {
    this.presentation = presentation;
  }
}
