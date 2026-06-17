package com.sc.en.onelittleangel.transverse.eventbus.events.movements;

import com.sc.en.onelittleangel.transverse.eventbus.models.MovementEventBus;

public class MovementByNameLoadedEvent {
  private MovementEventBus movementByName;
  private String name;

  public MovementByNameLoadedEvent(MovementEventBus movementByName, String name) {
    this.movementByName = movementByName;
    this.name = name;
  }

  public MovementByNameLoadedEvent() {
  }

  public MovementEventBus getMovementByName() {
    return movementByName;
  }

  public void setMovementByName(MovementEventBus movementByName) {
    this.movementByName = movementByName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
