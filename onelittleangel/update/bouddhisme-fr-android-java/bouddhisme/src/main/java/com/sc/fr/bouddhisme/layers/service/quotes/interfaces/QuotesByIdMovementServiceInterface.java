package com.sc.fr.bouddhisme.layers.service.quotes.interfaces;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadQuotesByIdMovementAsync(int idMovement);
}
