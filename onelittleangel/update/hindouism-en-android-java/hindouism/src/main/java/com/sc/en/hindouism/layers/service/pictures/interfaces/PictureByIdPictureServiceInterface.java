package com.sc.en.hindouism.layers.service.pictures.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

public interface PictureByIdPictureServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idPicture
   */
  void loadPictureByIdPictureAsync(int idPicture);
}
