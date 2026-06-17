package com.sc.en.philosophy.layers.service.centuries.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdCenturyServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idCentury
   */
  void loadCenturyByIdCenturyAsync(int idCentury);
}
