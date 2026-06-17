package com.sc.fr.philosophie.layers.service.movements.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;

public interface MovementsWithAuthorsServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
    void loadMovementsWithAuthorsAsync();
}
