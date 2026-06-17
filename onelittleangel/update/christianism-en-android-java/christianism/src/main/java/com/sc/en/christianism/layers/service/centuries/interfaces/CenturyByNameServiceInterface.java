package com.sc.en.christianism.layers.service.centuries.interfaces;

import com.sc.en.christianism.layers.service.MotherBusinessServiceInterface;

public interface CenturyByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  void loadCenturyByNameAsync(String name);
}
