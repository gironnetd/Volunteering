package com.sc.en.confucianism.layers.service.urls.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdSourceServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param sourceType
   * @param idSource
   */
  void loadUrlsByIdSourceAsync(String sourceType, int idSource);

}
