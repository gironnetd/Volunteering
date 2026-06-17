package com.sc.en.bouddhism.layers.service.presentations.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadPresentationByIdBookAsync(int idBook);
}
