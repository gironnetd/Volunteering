package com.sc.en.christianism.layers.service.movements.interfaces;

import com.sc.en.christianism.layers.service.MotherBusinessServiceInterface;

public interface MovementByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadMovementByIdMovementAsync(int idMovement);
}
