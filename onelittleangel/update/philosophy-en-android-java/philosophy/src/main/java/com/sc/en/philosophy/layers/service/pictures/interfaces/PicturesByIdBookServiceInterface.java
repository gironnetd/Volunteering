package com.sc.en.philosophy.layers.service.pictures.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadPicturesByIdBookAsync(int idBook);
}
