package com.sc.fr.islam.layers.service.movements.interfaces;

import com.sc.fr.islam.layers.service.MotherBusinessServiceInterface;

public interface MovementByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadMovementByIdMovementAsync(int idMovement);
}
