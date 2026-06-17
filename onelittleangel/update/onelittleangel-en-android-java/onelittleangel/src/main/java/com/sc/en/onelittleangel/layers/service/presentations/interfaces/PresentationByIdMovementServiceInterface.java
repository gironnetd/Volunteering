package com.sc.en.onelittleangel.layers.service.presentations.interfaces;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadPresentationByIdMovementAsync(int idMovement);
}
