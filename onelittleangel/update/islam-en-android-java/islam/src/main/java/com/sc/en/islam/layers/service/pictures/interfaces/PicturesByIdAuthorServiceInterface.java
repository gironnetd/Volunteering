package com.sc.en.islam.layers.service.pictures.interfaces;

import com.sc.en.islam.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadPicturesByIdAuthorAsync(int idAuthor);
}
