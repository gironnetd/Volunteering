package com.sc.en.christianism.transverse.eventbus.events.quotes;


import com.sc.en.christianism.transverse.eventbus.models.QuoteEventBus;

import java.util.List;

public class QuotesByIdMovementLoadedEvent {
  private List<QuoteEventBus> quotesByIdMovement;
  private int idMovement;

  public QuotesByIdMovementLoadedEvent(List<QuoteEventBus> quotesByIdMovement, int idMovement) {
    this.quotesByIdMovement = quotesByIdMovement;
    this.idMovement = idMovement;
  }

  public QuotesByIdMovementLoadedEvent() {
  }

  public List<QuoteEventBus> getQuotesByIdMovement() {
    return quotesByIdMovement;
  }

  public void setQuotesByIdMovement(List<QuoteEventBus> quotesByIdMovement) {
    this.quotesByIdMovement = quotesByIdMovement;
  }

  public int getIdMovement() {
    return idMovement;
  }

  public void setIdMovement(int idMovement) {
    this.idMovement = idMovement;
  }
}
