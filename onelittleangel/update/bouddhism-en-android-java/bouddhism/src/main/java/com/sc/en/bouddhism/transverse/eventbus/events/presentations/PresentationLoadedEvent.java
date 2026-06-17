package com.sc.en.bouddhism.transverse.eventbus.events.presentations;

import com.sc.en.bouddhism.transverse.eventbus.models.PresentationEventBus;

class PresentationLoadedEvent {
  private PresentationEventBus presentation;

  public PresentationLoadedEvent() {
  }

  public PresentationLoadedEvent(PresentationEventBus presentation) {
    this.presentation = presentation;
  }

  public PresentationEventBus getPresentation() {
    return presentation;
  }

  public void setPresentation(PresentationEventBus presentation) {
    this.presentation = presentation;
  }
}
