package com.sc.en.hindouism.layers.service.centuries.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadCenturyByIdAuthorAsync(int idAuthor);
}
