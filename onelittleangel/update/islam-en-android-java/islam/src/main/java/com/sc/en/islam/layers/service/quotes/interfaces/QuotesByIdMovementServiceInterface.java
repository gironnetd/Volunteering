package com.sc.en.islam.layers.service.quotes.interfaces;

import com.sc.en.islam.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadQuotesByIdMovementAsync(int idMovement);
}
