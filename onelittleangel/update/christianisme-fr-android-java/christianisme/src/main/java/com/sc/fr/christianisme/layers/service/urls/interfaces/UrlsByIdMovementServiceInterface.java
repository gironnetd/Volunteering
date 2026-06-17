package com.sc.fr.christianisme.layers.service.urls.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadUrlsByIdMovementAsync(int idMovement);
}
