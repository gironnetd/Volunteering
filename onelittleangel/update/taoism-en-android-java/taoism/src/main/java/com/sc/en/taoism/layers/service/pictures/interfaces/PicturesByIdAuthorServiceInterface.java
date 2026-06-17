package com.sc.en.taoism.layers.service.pictures.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadPicturesByIdAuthorAsync(int idAuthor);
}
