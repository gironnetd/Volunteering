package com.sc.en.christianism.transverse.eventbus.events.presentations;

import com.sc.en.christianism.transverse.eventbus.models.PresentationEventBus;

import java.util.List;

public class PresentationsAllLoadedEvent {

  private List<PresentationEventBus> presentationsAll;

  public PresentationsAllLoadedEvent() {
  }

  public PresentationsAllLoadedEvent(List<PresentationEventBus> presentationsAll) {
    this.presentationsAll = presentationsAll;
  }

  public List<PresentationEventBus> getPresentations() {
    return presentationsAll;
  }

  public void setPresentations(List<PresentationEventBus> presentationsAll) {
    this.presentationsAll = presentationsAll;
  }
}
