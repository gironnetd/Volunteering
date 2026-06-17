package com.sc.en.hindouism.layers.service.movements.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

public interface MovementByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadMovementByIdMovementAsync(int idMovement);
}
