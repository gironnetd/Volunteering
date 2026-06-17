package com.sc.fr.bouddhisme.layers.service.presentations.interfaces;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadPresentationByIdAuthorAsync(int idAuthor);
}
