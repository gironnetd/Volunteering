package com.sc.en.philosophy.layers.service.pictures.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;

public interface PicturesByNameSmallServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param nameSmall
   */
  void loadPicturesByNameSmallAsync(String nameSmall);
}
