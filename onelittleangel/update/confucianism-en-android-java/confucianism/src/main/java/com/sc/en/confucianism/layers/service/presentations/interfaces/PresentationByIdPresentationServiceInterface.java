package com.sc.en.confucianism.layers.service.presentations.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdPresentationServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idPresentation
   */
  void loadPresentationByIdPresentationAsync(int idPresentation);
}
