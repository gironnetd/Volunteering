package com.sc.fr.confucianisme.layers.service.centuries.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;

public interface CenturyByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  void loadCenturyByNameAsync(String name);
}
