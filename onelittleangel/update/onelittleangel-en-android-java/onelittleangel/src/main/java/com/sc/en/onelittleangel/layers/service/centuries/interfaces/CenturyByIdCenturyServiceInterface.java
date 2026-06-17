package com.sc.en.onelittleangel.layers.service.centuries.interfaces;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdCenturyServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idCentury
   */
  void loadCenturyByIdCenturyAsync(int idCentury);
}
