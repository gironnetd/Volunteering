package com.sc.fr.hindouisme.layers.service.movements.interfaces;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

public interface MovementsByIdParentServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idParent
   */
  void loadMovementsByIdParentAsync(int idParent);
}
