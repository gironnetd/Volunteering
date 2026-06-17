package com.sc.fr.taoisme.layers.service.movements.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface MovementsAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
  void loadAllMovementsAsync();
}
