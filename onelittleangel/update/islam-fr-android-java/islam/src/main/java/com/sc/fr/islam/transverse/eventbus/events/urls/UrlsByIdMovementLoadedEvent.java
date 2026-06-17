package com.sc.fr.islam.transverse.eventbus.events.urls;

import com.sc.fr.islam.transverse.eventbus.models.UrlEventBus;

import java.util.List;

public class UrlsByIdMovementLoadedEvent {
  private List<UrlEventBus> urlsByIdMovement;
  private int idMovement;

  public UrlsByIdMovementLoadedEvent(List<UrlEventBus> urlsByIdMovement, int idMovement) {
    this.urlsByIdMovement = urlsByIdMovement;
    this.idMovement = idMovement;
  }

  public UrlsByIdMovementLoadedEvent() {
  }

  public List<UrlEventBus> getUrlsByIdMovement() {
    return urlsByIdMovement;
  }

  public void setUrlsByIdMovement(List<UrlEventBus> urlsByIdMovement) {
    this.urlsByIdMovement = urlsByIdMovement;
  }

  public int getIdMovement() {
    return idMovement;
  }

  public void setIdMovement(int idMovement) {
    this.idMovement = idMovement;
  }
}
