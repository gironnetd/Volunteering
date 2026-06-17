package com.sc.en.confucianism.layers.service.presentations.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadPresentationByIdMovementAsync(int idMovement);
}
