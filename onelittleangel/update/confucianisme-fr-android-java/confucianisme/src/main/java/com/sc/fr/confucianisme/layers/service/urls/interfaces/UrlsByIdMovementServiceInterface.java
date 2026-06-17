package com.sc.fr.confucianisme.layers.service.urls.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadUrlsByIdMovementAsync(int idMovement);
}
