package com.sc.en.philosophy.layers.service.centuries.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadCenturyByIdAuthorAsync(int idAuthor);
}
