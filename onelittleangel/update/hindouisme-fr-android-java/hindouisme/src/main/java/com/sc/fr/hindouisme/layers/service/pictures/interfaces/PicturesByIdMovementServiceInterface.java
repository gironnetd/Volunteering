package com.sc.fr.hindouisme.layers.service.pictures.interfaces;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadPicturesByIdMovementAsync(int idMovement);
}
