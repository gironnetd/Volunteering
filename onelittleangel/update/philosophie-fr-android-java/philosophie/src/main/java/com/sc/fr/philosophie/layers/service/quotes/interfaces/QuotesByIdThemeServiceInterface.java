package com.sc.fr.philosophie.layers.service.quotes.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadQuotesByIdThemeAsync(int idTheme);
}
