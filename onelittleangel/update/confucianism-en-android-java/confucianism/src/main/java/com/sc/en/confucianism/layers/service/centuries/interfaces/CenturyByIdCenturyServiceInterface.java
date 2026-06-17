package com.sc.en.confucianism.layers.service.centuries.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdCenturyServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idCentury
   */
  void loadCenturyByIdCenturyAsync(int idCentury);
}
