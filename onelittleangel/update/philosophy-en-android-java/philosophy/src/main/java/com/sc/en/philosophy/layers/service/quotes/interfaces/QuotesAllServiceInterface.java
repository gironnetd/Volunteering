package com.sc.en.philosophy.layers.service.quotes.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;

public interface QuotesAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
  void loadAllQuotesAsync();
}
