package com.sc.en.bouddhism.transverse.eventbus.events.presentations;

import com.sc.en.bouddhism.transverse.eventbus.models.PresentationEventBus;

public class PresentationByIdBookLoadedEvent {
  private PresentationEventBus presentationByIdBook;
  private int idBook;

  public PresentationByIdBookLoadedEvent(PresentationEventBus presentationByIdBook, int idBook) {
    this.presentationByIdBook = presentationByIdBook;
    this.idBook = idBook;
  }

  public PresentationByIdBookLoadedEvent() {
  }

  public PresentationEventBus getPresentationByIdBook() {
    return presentationByIdBook;
  }

  public void setPresentationByIdBook(PresentationEventBus presentationByIdBook) {
    this.presentationByIdBook = presentationByIdBook;
  }

  public int getIdBook() {
    return idBook;
  }

  public void setIdBook(int idBook) {
    this.idBook = idBook;
  }
}
