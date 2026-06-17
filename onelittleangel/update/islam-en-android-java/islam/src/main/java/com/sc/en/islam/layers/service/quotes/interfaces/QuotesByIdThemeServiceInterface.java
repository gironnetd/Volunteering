package com.sc.en.islam.layers.service.quotes.interfaces;

import com.sc.en.islam.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadQuotesByIdThemeAsync(int idTheme);
}
