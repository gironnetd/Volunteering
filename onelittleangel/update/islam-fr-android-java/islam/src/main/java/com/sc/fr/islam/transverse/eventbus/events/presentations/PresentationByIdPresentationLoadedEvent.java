package com.sc.fr.islam.transverse.eventbus.events.presentations;

import com.sc.fr.islam.transverse.eventbus.models.PresentationEventBus;

public class PresentationByIdPresentationLoadedEvent {
  private PresentationEventBus presentationByIdPresentation;
  private int idPresentation;

  public PresentationByIdPresentationLoadedEvent(PresentationEventBus presentationByIdPresentation, int idPresentation) {
    this.presentationByIdPresentation = presentationByIdPresentation;
    this.idPresentation = idPresentation;
  }

  public PresentationByIdPresentationLoadedEvent() {
  }

  public PresentationEventBus getPresentationByIdPresentation() {
    return presentationByIdPresentation;
  }

  public void setPresentationByIdPresentation(PresentationEventBus presentationByIdPresentation) {
    this.presentationByIdPresentation = presentationByIdPresentation;
  }

  public int getIdPresentation() {
    return idPresentation;
  }

  public void setIdPresentation(int idPresentation) {
    this.idPresentation = idPresentation;
  }
}
