package com.sc.fr.hindouisme.layers.service.movements.interfaces;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

public interface MovementByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadMovementByIdMovementAsync(int idMovement);
}
