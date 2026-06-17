package com.sc.fr.bouddhisme.layers.service.urls.interfaces;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdSourceServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param sourceType
   * @param idSource
   */
  void loadUrlsByIdSourceAsync(String sourceType, int idSource);

}
