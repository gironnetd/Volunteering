package com.sc.fr.hindouisme.transverse.eventbus.events.presentations;

import com.sc.fr.hindouisme.transverse.eventbus.models.PresentationEventBus;

public class PresentationByIdAuthorLoadedEvent {
  private PresentationEventBus presentationByIdAuthor;
  private int idAuthor;

  public PresentationByIdAuthorLoadedEvent(int idAuthor, PresentationEventBus presentationByIdAuthor) {
    this.idAuthor = idAuthor;
    this.presentationByIdAuthor = presentationByIdAuthor;
  }

  public PresentationByIdAuthorLoadedEvent() {
  }

  public PresentationEventBus getPresentationByIdAuthor() {
    return presentationByIdAuthor;
  }

  public void setPresentationByIdAuthor(PresentationEventBus presentationByIdAuthor) {
    this.presentationByIdAuthor = presentationByIdAuthor;
  }

  public int getIdAuthor() {
    return idAuthor;
  }

  public void setIdAuthor(int idAuthor) {
    this.idAuthor = idAuthor;
  }
}
