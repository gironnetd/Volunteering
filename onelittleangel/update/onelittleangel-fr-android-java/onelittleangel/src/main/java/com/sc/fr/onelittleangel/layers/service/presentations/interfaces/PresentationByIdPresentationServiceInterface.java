package com.sc.fr.onelittleangel.layers.service.presentations.interfaces;

import com.sc.fr.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdPresentationServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idPresentation
   */
  void loadPresentationByIdPresentationAsync(int idPresentation);
}
