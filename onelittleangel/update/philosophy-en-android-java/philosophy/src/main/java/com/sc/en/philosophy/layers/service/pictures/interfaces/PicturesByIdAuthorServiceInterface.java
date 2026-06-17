package com.sc.en.philosophy.layers.service.pictures.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadPicturesByIdAuthorAsync(int idAuthor);
}
