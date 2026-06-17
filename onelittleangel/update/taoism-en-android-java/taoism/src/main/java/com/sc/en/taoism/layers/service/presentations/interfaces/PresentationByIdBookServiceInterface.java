package com.sc.en.taoism.layers.service.presentations.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadPresentationByIdBookAsync(int idBook);
}
