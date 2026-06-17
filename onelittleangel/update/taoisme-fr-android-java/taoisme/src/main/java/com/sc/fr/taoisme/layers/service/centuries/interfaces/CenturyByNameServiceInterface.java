package com.sc.fr.taoisme.layers.service.centuries.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface CenturyByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  void loadCenturyByNameAsync(String name);
}
