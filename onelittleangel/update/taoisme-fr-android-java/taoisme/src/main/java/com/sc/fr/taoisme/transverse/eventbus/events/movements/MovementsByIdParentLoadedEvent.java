package com.sc.fr.taoisme.transverse.eventbus.events.movements;

import com.sc.fr.taoisme.transverse.eventbus.models.MovementEventBus;

import java.util.List;

public class MovementsByIdParentLoadedEvent {
  private List<MovementEventBus> movementsByIdParent;
  private int idParent;

  public MovementsByIdParentLoadedEvent(List<MovementEventBus> movementsByIdParent, int idParent) {
    this.movementsByIdParent = movementsByIdParent;
    this.idParent = idParent;
  }

  public MovementsByIdParentLoadedEvent() {
  }

  public List<MovementEventBus> getMovementsByIdParent() {
    return movementsByIdParent;
  }

  public void setMovementsByIdParent(List<MovementEventBus> movementsByIdParent) {
    this.movementsByIdParent = movementsByIdParent;
  }

  public int getIdParent() {
    return idParent;
  }

  public void setIdParent(int idParent) {
    this.idParent = idParent;
  }
}
