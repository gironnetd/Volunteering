package com.sc.en.confucianism.layers.service.pictures.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

public interface PicturesByNameSmallServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param nameSmall
   */
  void loadPicturesByNameSmallAsync(String nameSmall);
}
