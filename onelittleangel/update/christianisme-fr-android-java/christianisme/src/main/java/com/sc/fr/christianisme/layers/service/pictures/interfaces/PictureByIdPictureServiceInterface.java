package com.sc.fr.christianisme.layers.service.pictures.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

public interface PictureByIdPictureServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idPicture
   */
  void loadPictureByIdPictureAsync(int idPicture);
}
