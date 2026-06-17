package com.sc.en.onelittleangel.transverse.eventbus.events.centuries;

import com.sc.en.onelittleangel.transverse.eventbus.models.CenturyEventBus;

public class CenturyByIdAuthorLoadedEvent {
  private CenturyEventBus centuryByIdAuthor;
  private int idAuthor;

  public CenturyByIdAuthorLoadedEvent(CenturyEventBus centuryByIdAuthor, int idAuthor) {
    this.centuryByIdAuthor = centuryByIdAuthor;
    this.idAuthor = idAuthor;
  }

  public CenturyByIdAuthorLoadedEvent() {
  }

  public CenturyEventBus getCenturyByIdAuthor() {
    return centuryByIdAuthor;
  }

  public void setCenturyByIdAuthor(CenturyEventBus centuryByIdAuthor) {
    this.centuryByIdAuthor = centuryByIdAuthor;
  }

  public int getIdAuthor() {
    return idAuthor;
  }

  public void setIdAuthor(int idAuthor) {
    this.idAuthor = idAuthor;
  }
}
