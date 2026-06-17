package com.sc.en.bouddhism.layers.service.presentations.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadPresentationByIdAuthorAsync(int idAuthor);
}
