package com.sc.en.onelittleangel.layers.service.quotes.interfaces;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadQuotesByIdBookAsync(int idBook);
}
