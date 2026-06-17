package com.sc.fr.philosophie.layers.service.centuries.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;

public interface CenturyByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  void loadCenturyByNameAsync(String name);
}
