package com.sc.fr.hindouisme.layers.service.presentations.interfaces;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadPresentationByIdMovementAsync(int idMovement);
}
