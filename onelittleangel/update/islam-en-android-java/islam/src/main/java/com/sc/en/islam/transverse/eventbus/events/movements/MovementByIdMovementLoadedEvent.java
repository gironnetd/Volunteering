package com.sc.en.islam.transverse.eventbus.events.movements;

import com.sc.en.islam.transverse.eventbus.models.MovementEventBus;

public class MovementByIdMovementLoadedEvent {
  private MovementEventBus movementByIdMovement;
  private int idMovement;

  public MovementByIdMovementLoadedEvent(MovementEventBus movementByIdMovement, int idMovement) {
    this.movementByIdMovement = movementByIdMovement;
    this.idMovement = idMovement;
  }

  public MovementByIdMovementLoadedEvent() {
  }

  public MovementEventBus getMovementByIdMovement() {
    return movementByIdMovement;
  }

  public void setMovementByIdMovement(MovementEventBus movementByIdMovement) {
    this.movementByIdMovement = movementByIdMovement;
  }

  public int getIdMovement() {
    return idMovement;
  }

  public void setIdMovement(int idMovement) {
    this.idMovement = idMovement;
  }
}
