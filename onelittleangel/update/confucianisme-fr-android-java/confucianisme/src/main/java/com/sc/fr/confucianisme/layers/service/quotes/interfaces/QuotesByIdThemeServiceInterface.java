package com.sc.fr.confucianisme.layers.service.quotes.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadQuotesByIdThemeAsync(int idTheme);
}
