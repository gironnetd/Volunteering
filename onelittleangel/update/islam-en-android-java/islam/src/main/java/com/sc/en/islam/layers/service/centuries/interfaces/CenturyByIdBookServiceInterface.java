package com.sc.en.islam.layers.service.centuries.interfaces;

import com.sc.en.islam.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadCenturyByIdBookAsync(int idBook);
}
