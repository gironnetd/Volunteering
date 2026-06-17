package com.sc.fr.philosophie.layers.service.quotes.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadQuotesByIdBookAsync(int idBook);
}
