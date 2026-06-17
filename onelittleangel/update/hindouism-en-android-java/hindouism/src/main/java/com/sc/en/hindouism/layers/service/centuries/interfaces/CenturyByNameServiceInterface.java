package com.sc.en.hindouism.layers.service.centuries.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

public interface CenturyByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  void loadCenturyByNameAsync(String name);
}
