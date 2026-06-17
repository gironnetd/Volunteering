package com.sc.en.philosophy.transverse.eventbus.events.movements;

import com.sc.en.philosophy.layers.mvp.tablecontents.models.Faith;

import java.util.List;

public class MovementsWithMovementsLoadedEvent {

  private List<Faith> movementsWithMovements;

  public MovementsWithMovementsLoadedEvent() {
  }

  public MovementsWithMovementsLoadedEvent(List<Faith> movementsWithMovements) {
    this.movementsWithMovements = movementsWithMovements;
  }

  public List<Faith> getMovementsWithMovements() {
    return movementsWithMovements;
  }

  public void setMovementsWithMovements(List<Faith> movementsWithMovements) {
    this.movementsWithMovements = movementsWithMovements;
  }
}
