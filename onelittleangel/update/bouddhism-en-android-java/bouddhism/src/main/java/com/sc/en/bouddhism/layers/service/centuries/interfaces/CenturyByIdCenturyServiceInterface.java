package com.sc.en.bouddhism.layers.service.centuries.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdCenturyServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idCentury
   */
  void loadCenturyByIdCenturyAsync(int idCentury);
}
