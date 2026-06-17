package com.sc.en.bouddhism.layers.service.pictures.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadPicturesByIdBookAsync(int idBook);
}
