package com.sc.en.hindouism.layers.service.presentations.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadPresentationByIdMovementAsync(int idMovement);
}
