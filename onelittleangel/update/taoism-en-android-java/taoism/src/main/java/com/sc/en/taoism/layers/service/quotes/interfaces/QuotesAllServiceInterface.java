package com.sc.en.taoism.layers.service.quotes.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

public interface QuotesAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
  void loadAllQuotesAsync();
}
