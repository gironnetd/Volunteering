package com.sc.fr.taoisme.layers.service.presentations.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdPresentationServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idPresentation
   */
  void loadPresentationByIdPresentationAsync(int idPresentation);
}
