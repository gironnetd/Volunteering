package com.sc.en.hindouism.layers.service.pictures.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadPicturesByIdAuthorAsync(int idAuthor);
}
