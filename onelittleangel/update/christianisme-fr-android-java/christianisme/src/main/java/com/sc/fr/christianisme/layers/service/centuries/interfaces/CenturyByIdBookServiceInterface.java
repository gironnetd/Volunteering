package com.sc.fr.christianisme.layers.service.centuries.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadCenturyByIdBookAsync(int idBook);
}
