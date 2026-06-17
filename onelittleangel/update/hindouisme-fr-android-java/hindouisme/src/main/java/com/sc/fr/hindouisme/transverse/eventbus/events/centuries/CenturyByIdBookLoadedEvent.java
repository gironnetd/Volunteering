package com.sc.fr.hindouisme.transverse.eventbus.events.centuries;

import com.sc.fr.hindouisme.transverse.eventbus.models.CenturyEventBus;

public class CenturyByIdBookLoadedEvent {
  private CenturyEventBus centuryByIdBook;
  private int idBook;

  public CenturyByIdBookLoadedEvent(CenturyEventBus centuryByIdBook, int idBook) {
    this.centuryByIdBook = centuryByIdBook;
    this.idBook = idBook;
  }

  public CenturyByIdBookLoadedEvent() {
  }

  public CenturyEventBus getCenturyByIdBook() {
    return centuryByIdBook;
  }

  public void setCenturyByIdBook(CenturyEventBus centuryByIdBook) {
    this.centuryByIdBook = centuryByIdBook;
  }

  public int getIdBook() {
    return idBook;
  }

  public void setIdBook(int idBook) {
    this.idBook = idBook;
  }
}
