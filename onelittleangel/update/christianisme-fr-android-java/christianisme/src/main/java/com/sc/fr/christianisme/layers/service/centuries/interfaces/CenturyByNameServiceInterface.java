package com.sc.fr.christianisme.layers.service.centuries.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

public interface CenturyByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param name
   */
  void loadCenturyByNameAsync(String name);
}
