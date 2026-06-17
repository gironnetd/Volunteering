package com.sc.fr.confucianisme.layers.service.presentations.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadPresentationByIdBookAsync(int idBook);
}
