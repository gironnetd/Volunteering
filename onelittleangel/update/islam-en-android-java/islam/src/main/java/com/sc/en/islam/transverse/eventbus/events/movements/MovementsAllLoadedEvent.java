package com.sc.en.islam.transverse.eventbus.events.movements;

import com.sc.en.islam.transverse.eventbus.models.MovementEventBus;

import java.util.List;

public class MovementsAllLoadedEvent {

  private List<MovementEventBus> movementsAll;

  public MovementsAllLoadedEvent() {
  }

  public MovementsAllLoadedEvent(List<MovementEventBus> movementsAll) {
    this.movementsAll = movementsAll;
  }

  public List<MovementEventBus> getMovements() {
    return movementsAll;
  }

  public void setMovements(List<MovementEventBus> movementsAll) {
    this.movementsAll = movementsAll;
  }
}
