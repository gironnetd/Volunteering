package com.sc.fr.christianisme.layers.service.quotes.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

public interface QuotesAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
  void loadAllQuotesAsync();
}
