package com.sc.fr.hindouisme.layers.service.quotes.interfaces;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadQuotesByIdBookAsync(int idBook);
}
