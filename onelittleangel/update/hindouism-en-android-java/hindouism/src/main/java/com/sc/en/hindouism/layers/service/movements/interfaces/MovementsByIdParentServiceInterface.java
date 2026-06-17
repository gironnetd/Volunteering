package com.sc.en.hindouism.layers.service.movements.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

public interface MovementsByIdParentServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idParent
   */
  void loadMovementsByIdParentAsync(int idParent);
}
