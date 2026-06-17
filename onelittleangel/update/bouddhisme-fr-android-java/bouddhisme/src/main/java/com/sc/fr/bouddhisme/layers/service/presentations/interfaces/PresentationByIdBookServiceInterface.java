package com.sc.fr.bouddhisme.layers.service.presentations.interfaces;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadPresentationByIdBookAsync(int idBook);
}
