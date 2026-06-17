package com.sc.fr.confucianisme.layers.service.quotes.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;

public interface QuotesAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
  void loadAllQuotesAsync();
}
