package com.sc.fr.christianisme.layers.service.quotes.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadQuotesByIdBookAsync(int idBook);
}
