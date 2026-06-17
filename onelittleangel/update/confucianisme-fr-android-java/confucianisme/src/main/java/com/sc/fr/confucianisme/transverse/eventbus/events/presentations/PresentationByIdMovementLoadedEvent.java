package com.sc.fr.confucianisme.transverse.eventbus.events.presentations;

import com.sc.fr.confucianisme.transverse.eventbus.models.PresentationEventBus;

public class PresentationByIdMovementLoadedEvent {
  private PresentationEventBus presentationByIdMovement;
  private int idMovement;

  public PresentationByIdMovementLoadedEvent(PresentationEventBus presentationByIdMovement, int idMovement) {
    this.presentationByIdMovement = presentationByIdMovement;
    this.idMovement = idMovement;
  }

  public PresentationByIdMovementLoadedEvent() {
  }

  public PresentationEventBus getPresentationByIdMovement() {
    return presentationByIdMovement;
  }

  public void setPresentationByIdMovement(PresentationEventBus presentationByIdMovement) {
    this.presentationByIdMovement = presentationByIdMovement;
  }

  public int getIdMovement() {
    return idMovement;
  }

  public void setIdMovement(int idMovement) {
    this.idMovement = idMovement;
  }
}
