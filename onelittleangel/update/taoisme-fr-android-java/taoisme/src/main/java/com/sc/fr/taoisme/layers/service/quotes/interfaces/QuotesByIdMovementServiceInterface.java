package com.sc.fr.taoisme.layers.service.quotes.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadQuotesByIdMovementAsync(int idMovement);
}
