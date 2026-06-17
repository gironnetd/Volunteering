package com.sc.fr.taoisme.layers.service.movements.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface MovementsByIdParentServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idParent
   */
  void loadMovementsByIdParentAsync(int idParent);
}
