package com.sc.fr.taoisme.layers.service.centuries.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadCenturyByIdBookAsync(int idBook);
}
