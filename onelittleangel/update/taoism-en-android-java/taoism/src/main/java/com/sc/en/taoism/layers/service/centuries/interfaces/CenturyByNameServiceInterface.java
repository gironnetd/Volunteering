package com.sc.en.taoism.layers.service.centuries.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

public interface CenturyByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  void loadCenturyByNameAsync(String name);
}
