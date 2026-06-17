package com.sc.en.quotes.layers.service.movements.interfaces;

import com.sc.en.quotes.layers.service.MotherBusinessServiceInterface;

public interface MovementsWithAuthorsServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
    void loadMovementsWithAuthorsAsync();
}
