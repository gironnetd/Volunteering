package com.sc.fr.onelittleangel.layers.service.movements.interfaces;

import com.sc.fr.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface MovementsByIdParentServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idParent
   */
  void loadMovementsByIdParentAsync(int idParent);
}
