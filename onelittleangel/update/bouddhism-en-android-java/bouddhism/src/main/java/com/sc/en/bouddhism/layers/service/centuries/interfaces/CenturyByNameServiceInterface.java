package com.sc.en.bouddhism.layers.service.centuries.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;

public interface CenturyByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  void loadCenturyByNameAsync(String name);
}
