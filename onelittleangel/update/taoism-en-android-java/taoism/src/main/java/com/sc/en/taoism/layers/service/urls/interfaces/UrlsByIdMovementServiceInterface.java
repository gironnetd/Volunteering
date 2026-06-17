package com.sc.en.taoism.layers.service.urls.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadUrlsByIdMovementAsync(int idMovement);
}
