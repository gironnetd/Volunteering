package com.sc.en.hindouism.layers.service.centuries.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadCenturyByIdBookAsync(int idBook);
}
