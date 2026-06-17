package com.sc.fr.bouddhisme.layers.service.quotes.interfaces;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;

public interface QuotesAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
  void loadAllQuotesAsync();
}
