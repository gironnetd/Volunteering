package com.sc.en.quotes.layers.service.pictures.interfaces;

import com.sc.en.quotes.layers.service.MotherBusinessServiceInterface;

public interface PictureByIdPictureServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idPicture
   */
  void loadPictureByIdPictureAsync(int idPicture);
}
