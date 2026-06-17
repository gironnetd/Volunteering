package com.sc.en.quotes.transverse.eventbus.events.centuries;

import com.sc.en.quotes.transverse.eventbus.models.CenturyEventBus;

class CenturyLoadedEvent {
  private CenturyEventBus century;

  public CenturyLoadedEvent() {
  }

  public CenturyLoadedEvent(CenturyEventBus century) {
    this.century = century;
  }

  public CenturyEventBus getCentury() {
    return century;
  }

  public void setCentury(CenturyEventBus century) {
    this.century = century;
  }
}
