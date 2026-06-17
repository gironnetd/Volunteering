package com.sc.en.christianism.layers.service.pictures.interfaces;

import com.sc.en.christianism.layers.service.MotherBusinessServiceInterface;

public interface PictureByIdPictureServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idPicture
   */
  void loadPictureByIdPictureAsync(int idPicture);
}
