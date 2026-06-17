package com.sc.en.philosophy.transverse.eventbus.events.centuries;

import com.sc.en.philosophy.transverse.eventbus.models.CenturyEventBus;

public class CenturyByIdCenturyLoadedEvent {
  private CenturyEventBus centuryByIdCentury;
  private int idCentury;

  public CenturyByIdCenturyLoadedEvent(CenturyEventBus centuryByIdCentury, int idCentury) {
    this.centuryByIdCentury = centuryByIdCentury;
    this.idCentury = idCentury;
  }

  public CenturyByIdCenturyLoadedEvent() {
  }

  public CenturyEventBus getCenturyByIdCentury() {
    return centuryByIdCentury;
  }

  public void setCenturyByIdCentury(CenturyEventBus centuryByIdCentury) {
    this.centuryByIdCentury = centuryByIdCentury;
  }

  public int getIdCentury() {
    return idCentury;
  }

  public void setIdCentury(int idCentury) {
    this.idCentury = idCentury;
  }
}
