package com.sc.en.quotes.layers.service.pictures.interfaces;

import com.sc.en.quotes.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadPicturesByIdMovementAsync(int idMovement);
}
