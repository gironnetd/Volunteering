package com.sc.fr.taoisme.layers.service.quotes.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadQuotesByIdBookAsync(int idBook);
}
