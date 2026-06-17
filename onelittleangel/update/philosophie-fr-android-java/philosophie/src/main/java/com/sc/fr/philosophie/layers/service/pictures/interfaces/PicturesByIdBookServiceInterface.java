package com.sc.fr.philosophie.layers.service.pictures.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadPicturesByIdBookAsync(int idBook);
}
