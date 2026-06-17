package com.sc.fr.onelittleangel.layers.service.quotes.interfaces;

import com.sc.fr.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadQuotesByIdMovementAsync(int idMovement);
}
