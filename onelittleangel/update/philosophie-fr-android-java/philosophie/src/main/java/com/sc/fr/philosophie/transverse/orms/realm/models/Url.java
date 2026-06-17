package com.sc.fr.philosophie.transverse.orms.realm.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Url extends RealmObject {

  @PrimaryKey
  private long idUrl;
  private String sourceType;
  private long idSource;
  private String title;
  private String url;
  private String presentation;

  public Url() {
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
