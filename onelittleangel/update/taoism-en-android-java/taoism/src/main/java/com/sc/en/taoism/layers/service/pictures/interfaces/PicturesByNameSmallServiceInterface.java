package com.sc.en.taoism.layers.service.pictures.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

public interface PicturesByNameSmallServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param nameSmall
   */
  void loadPicturesByNameSmallAsync(String nameSmall);
}
