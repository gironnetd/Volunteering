package com.sc.en.hindouism.layers.service.pictures.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

public interface PicturesByNameSmallServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param nameSmall
   */
  void loadPicturesByNameSmallAsync(String nameSmall);
}
