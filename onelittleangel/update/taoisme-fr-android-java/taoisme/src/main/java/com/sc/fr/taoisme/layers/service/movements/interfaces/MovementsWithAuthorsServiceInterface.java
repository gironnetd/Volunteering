package com.sc.fr.taoisme.layers.service.movements.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface MovementsWithAuthorsServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
    void loadMovementsWithAuthorsAsync();
}
