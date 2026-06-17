package com.sc.en.confucianism.layers.service.urls.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadUrlsByIdMovementAsync(int idMovement);
}
