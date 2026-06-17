package com.sc.fr.christianisme.layers.service.presentations.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadPresentationByIdBookAsync(int idBook);
}
