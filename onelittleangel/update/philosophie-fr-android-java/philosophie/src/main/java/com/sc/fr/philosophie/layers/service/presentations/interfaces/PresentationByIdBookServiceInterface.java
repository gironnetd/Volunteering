package com.sc.fr.philosophie.layers.service.presentations.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadPresentationByIdBookAsync(int idBook);
}
