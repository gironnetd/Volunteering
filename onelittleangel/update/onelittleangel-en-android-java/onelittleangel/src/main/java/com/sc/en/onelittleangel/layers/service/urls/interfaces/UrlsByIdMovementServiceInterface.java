package com.sc.en.onelittleangel.layers.service.urls.interfaces;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadUrlsByIdMovementAsync(int idMovement);
}
