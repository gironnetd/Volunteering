package com.sc.fr.hindouisme.transverse.eventbus.events.pictures;

import com.sc.fr.hindouisme.transverse.eventbus.models.PictureEventBus;

import java.util.List;

public class PicturesByIdMovementLoadedEvent {
  private List<PictureEventBus> picturesByIdMovement;
  private int idMovement;

  public PicturesByIdMovementLoadedEvent(List<PictureEventBus> picturesByIdMovement, int idMovement) {
    this.picturesByIdMovement = picturesByIdMovement;
    this.idMovement = idMovement;
  }

  public PicturesByIdMovementLoadedEvent() {
  }

  public List<PictureEventBus> getPicturesByIdMovement() {
    return picturesByIdMovement;
  }

  public void setPicturesByIdMovement(List<PictureEventBus> picturesByIdMovement) {
    this.picturesByIdMovement = picturesByIdMovement;
  }

  public int getIdMovement() {
    return idMovement;
  }

  public void setIdMovement(int idMovement) {
    this.idMovement = idMovement;
  }
}
