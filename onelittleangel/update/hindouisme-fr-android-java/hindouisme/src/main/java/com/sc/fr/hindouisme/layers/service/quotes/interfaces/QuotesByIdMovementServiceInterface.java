package com.sc.fr.hindouisme.layers.service.quotes.interfaces;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadQuotesByIdMovementAsync(int idMovement);
}
