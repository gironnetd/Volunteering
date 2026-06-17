package com.sc.fr.taoisme.layers.service.centuries.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadCenturyByIdAuthorAsync(int idAuthor);
}
