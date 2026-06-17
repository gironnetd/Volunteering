package com.sc.en.taoism.layers.service.presentations.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadPresentationByIdAuthorAsync(int idAuthor);
}
