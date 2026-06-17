package com.sc.fr.christianisme.layers.service.centuries.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

public interface CenturyByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadCenturyByIdAuthorAsync(int idAuthor);
}
