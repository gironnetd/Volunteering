package com.sc.en.hindouism.layers.service.pictures.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadPicturesByIdBookAsync(int idBook);
}
