package com.sc.en.bouddhism.layers.service.movements.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;

public interface MovementsByIdParentServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idParent
   */
  void loadMovementsByIdParentAsync(int idParent);
}
