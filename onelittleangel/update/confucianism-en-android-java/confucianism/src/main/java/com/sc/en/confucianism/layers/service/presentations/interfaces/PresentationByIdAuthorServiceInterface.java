package com.sc.en.confucianism.layers.service.presentations.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadPresentationByIdAuthorAsync(int idAuthor);
}
