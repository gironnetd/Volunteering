package com.sc.en.philosophy.layers.service.presentations.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadPresentationByIdAuthorAsync(int idAuthor);
}
