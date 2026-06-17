package com.sc.en.taoism.layers.service.quotes.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

public interface QuotesByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadQuotesByIdThemeAsync(int idTheme);
}
