package com.sc.en.taoism.layers.service.movements.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

public interface MovementsWithAuthorsServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
    void loadMovementsWithAuthorsAsync();
}
