package com.sc.fr.christianisme.layers.service.pictures.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadPicturesByIdBookAsync(int idBook);
}
