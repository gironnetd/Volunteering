package com.sc.fr.bouddhisme.layers.service.presentations.interfaces;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdPresentationServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idPresentation
   */
  void loadPresentationByIdPresentationAsync(int idPresentation);
}
