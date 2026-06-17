package com.sc.en.onelittleangel.transverse.eventbus.events.centuries;

import com.sc.en.onelittleangel.transverse.eventbus.models.CenturyEventBus;

public class CenturyByNameLoadedEvent {
  private CenturyEventBus centuryByName;
  private String name;

  public CenturyByNameLoadedEvent(CenturyEventBus centuryByName, String name) {
    this.centuryByName = centuryByName;
    this.name = name;
  }

  public CenturyByNameLoadedEvent() {
  }

  public CenturyEventBus getCenturyByName() {
    return centuryByName;
  }

  public void setCenturyByName(CenturyEventBus centuryByName) {
    this.centuryByName = centuryByName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
