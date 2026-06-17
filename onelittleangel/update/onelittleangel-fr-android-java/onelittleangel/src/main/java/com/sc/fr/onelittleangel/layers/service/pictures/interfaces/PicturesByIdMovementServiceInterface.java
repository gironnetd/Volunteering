package com.sc.fr.onelittleangel.layers.service.pictures.interfaces;

import com.sc.fr.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadPicturesByIdMovementAsync(int idMovement);
}
