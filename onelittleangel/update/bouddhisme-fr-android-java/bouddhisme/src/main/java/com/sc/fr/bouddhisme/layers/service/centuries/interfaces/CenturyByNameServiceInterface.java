package com.sc.fr.bouddhisme.layers.service.centuries.interfaces;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;

public interface CenturyByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  void loadCenturyByNameAsync(String name);
}
