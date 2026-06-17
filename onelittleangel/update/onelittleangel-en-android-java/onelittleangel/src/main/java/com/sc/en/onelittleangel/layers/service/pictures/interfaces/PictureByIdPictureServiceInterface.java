package com.sc.en.onelittleangel.layers.service.pictures.interfaces;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface PictureByIdPictureServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idPicture
   */
  void loadPictureByIdPictureAsync(int idPicture);
}
