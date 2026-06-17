package com.sc.en.bouddhism.layers.service.quotes.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadQuotesByIdBookAsync(int idBook);
}
