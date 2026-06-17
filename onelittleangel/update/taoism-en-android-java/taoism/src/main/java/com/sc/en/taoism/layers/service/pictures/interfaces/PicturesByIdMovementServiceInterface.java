package com.sc.en.taoism.layers.service.pictures.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadPicturesByIdMovementAsync(int idMovement);
}
