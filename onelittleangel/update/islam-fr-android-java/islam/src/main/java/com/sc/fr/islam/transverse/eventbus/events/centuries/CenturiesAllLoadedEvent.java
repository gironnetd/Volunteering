package com.sc.fr.islam.transverse.eventbus.events.centuries;

import com.sc.fr.islam.transverse.eventbus.models.CenturyEventBus;

import java.util.List;

public class CenturiesAllLoadedEvent {

  private List<CenturyEventBus> centuriesAll;


  public CenturiesAllLoadedEvent(List<CenturyEventBus> centuriesAll) {
    this.centuriesAll = centuriesAll;
  }

  public CenturiesAllLoadedEvent() {
  }

  public List<CenturyEventBus> getCenturies() {
    return centuriesAll;
  }

  public void setCenturies(List<CenturyEventBus> centuriesAll) {
    this.centuriesAll = centuriesAll;
  }
}
