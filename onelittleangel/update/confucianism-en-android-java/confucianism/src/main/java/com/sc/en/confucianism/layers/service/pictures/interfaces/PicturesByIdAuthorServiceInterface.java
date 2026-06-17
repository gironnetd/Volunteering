package com.sc.en.confucianism.layers.service.pictures.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadPicturesByIdAuthorAsync(int idAuthor);
}
