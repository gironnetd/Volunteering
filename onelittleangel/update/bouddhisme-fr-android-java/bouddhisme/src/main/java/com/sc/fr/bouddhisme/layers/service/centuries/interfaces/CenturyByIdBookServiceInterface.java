package com.sc.fr.bouddhisme.layers.service.centuries.interfaces;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadCenturyByIdBookAsync(int idBook);
}
