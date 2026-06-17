package com.sc.en.onelittleangel.layers.service.movements.interfaces;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface MovementByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadMovementByIdMovementAsync(int idMovement);
}
