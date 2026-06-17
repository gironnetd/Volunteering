package com.sc.en.philosophy.layers.service.movements.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;

public interface MovementsWithAuthorsServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
    void loadMovementsWithAuthorsAsync();
}
