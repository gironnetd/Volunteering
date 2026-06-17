package com.sc.fr.confucianisme.layers.service.urls.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdSourceServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param sourceType
   * @param idSource
   */
  void loadUrlsByIdSourceAsync(String sourceType, int idSource);

}
