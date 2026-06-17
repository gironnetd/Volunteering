package com.sc.fr.hindouisme.layers.service.quotes.interfaces;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadQuotesByIdThemeAsync(int idTheme);
}
