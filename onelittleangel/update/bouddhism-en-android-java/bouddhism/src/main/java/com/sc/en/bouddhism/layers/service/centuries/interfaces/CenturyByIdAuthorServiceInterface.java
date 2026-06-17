package com.sc.en.bouddhism.layers.service.centuries.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadCenturyByIdAuthorAsync(int idAuthor);
}
