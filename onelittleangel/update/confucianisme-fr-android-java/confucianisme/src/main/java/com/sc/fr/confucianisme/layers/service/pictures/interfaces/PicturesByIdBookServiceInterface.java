package com.sc.fr.confucianisme.layers.service.pictures.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadPicturesByIdBookAsync(int idBook);
}
