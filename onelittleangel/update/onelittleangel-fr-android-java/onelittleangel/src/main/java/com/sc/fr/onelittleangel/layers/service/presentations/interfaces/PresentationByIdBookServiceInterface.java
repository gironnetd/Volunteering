package com.sc.fr.onelittleangel.layers.service.presentations.interfaces;

import com.sc.fr.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface PresentationByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadPresentationByIdBookAsync(int idBook);
}
