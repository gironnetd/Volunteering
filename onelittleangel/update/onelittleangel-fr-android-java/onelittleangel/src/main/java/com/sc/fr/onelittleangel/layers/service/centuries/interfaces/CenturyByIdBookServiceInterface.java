package com.sc.fr.onelittleangel.layers.service.centuries.interfaces;

import com.sc.fr.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadCenturyByIdBookAsync(int idBook);
}
