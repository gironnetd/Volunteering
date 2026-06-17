package com.sc.fr.confucianisme.layers.service.presentations.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdMovementServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idMovement
   */
  void loadPresentationByIdMovementAsync(int idMovement);
}
