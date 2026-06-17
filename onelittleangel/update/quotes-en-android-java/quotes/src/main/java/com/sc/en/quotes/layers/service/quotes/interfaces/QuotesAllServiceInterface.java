package com.sc.en.quotes.layers.service.quotes.interfaces;

import com.sc.en.quotes.layers.service.MotherBusinessServiceInterface;

public interface QuotesAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
  void loadAllQuotesAsync();
}
