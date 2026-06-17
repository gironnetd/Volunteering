package com.sc.fr.islam.transverse.eventbus.events.movements;

import com.sc.fr.islam.transverse.eventbus.models.MovementEventBus;

class MovementLoadedEvent {
  private MovementEventBus movement;

  public MovementLoadedEvent() {
  }

  public MovementLoadedEvent(MovementEventBus movement) {
    this.movement = movement;
  }

  public MovementEventBus getMovement() {
    return movement;
  }

  public void setMovement(MovementEventBus movement) {
    this.movement = movement;
  }
}
