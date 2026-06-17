package com.sc.fr.philosophie.layers.service.movements.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;

public interface MovementsAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
  void loadAllMovementsAsync();
}
