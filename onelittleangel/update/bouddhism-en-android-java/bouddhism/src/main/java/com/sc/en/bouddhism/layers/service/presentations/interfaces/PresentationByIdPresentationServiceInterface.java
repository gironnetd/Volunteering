package com.sc.en.bouddhism.layers.service.presentations.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdPresentationServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idPresentation
   */
  void loadPresentationByIdPresentationAsync(int idPresentation);
}
