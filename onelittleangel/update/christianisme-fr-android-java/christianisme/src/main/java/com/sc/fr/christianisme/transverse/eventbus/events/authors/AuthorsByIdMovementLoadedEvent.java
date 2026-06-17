package com.sc.fr.christianisme.transverse.eventbus.events.authors;


import com.sc.fr.christianisme.transverse.eventbus.models.AuthorEventBus;

import java.util.List;

public class AuthorsByIdMovementLoadedEvent {
  private List<AuthorEventBus> authorsByIdMovement;
  private int idMovement;

  public AuthorsByIdMovementLoadedEvent(List<AuthorEventBus> authorsByIdMovement, int idMovement) {
    this.authorsByIdMovement = authorsByIdMovement;
    this.idMovement = idMovement;
  }

  public AuthorsByIdMovementLoadedEvent() {
  }

  public List<AuthorEventBus> getAuthorsByIdMovement() {
    return authorsByIdMovement;
  }

  public void setAuthorsByIdMovement(List<AuthorEventBus> authorsByIdMovement) {
    this.authorsByIdMovement = authorsByIdMovement;
  }

  public int getIdMovement() {
    return idMovement;
  }

  public void setIdMovement(int idMovement) {
    this.idMovement = idMovement;
  }
}
