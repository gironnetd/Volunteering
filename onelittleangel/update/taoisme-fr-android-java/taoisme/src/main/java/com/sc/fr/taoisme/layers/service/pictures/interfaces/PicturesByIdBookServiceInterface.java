package com.sc.fr.taoisme.layers.service.pictures.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadPicturesByIdBookAsync(int idBook);
}
