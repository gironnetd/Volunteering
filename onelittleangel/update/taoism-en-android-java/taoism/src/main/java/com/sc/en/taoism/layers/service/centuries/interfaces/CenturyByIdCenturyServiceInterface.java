package com.sc.en.taoism.layers.service.centuries.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdCenturyServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idCentury
   */
  void loadCenturyByIdCenturyAsync(int idCentury);
}
