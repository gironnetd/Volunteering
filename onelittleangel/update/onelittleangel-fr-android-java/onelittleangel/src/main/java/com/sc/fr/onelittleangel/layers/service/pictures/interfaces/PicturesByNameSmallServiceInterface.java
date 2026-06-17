package com.sc.fr.onelittleangel.layers.service.pictures.interfaces;

import com.sc.fr.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface PicturesByNameSmallServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param nameSmall
   */
  void loadPicturesByNameSmallAsync(String nameSmall);
}
