package com.sc.en.confucianism.layers.service.centuries.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

public interface CenturyByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  void loadCenturyByNameAsync(String name);
}
