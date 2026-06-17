package com.sc.fr.taoisme.layers.service.pictures.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadPicturesByIdMovementAsync(int idMovement);
}
