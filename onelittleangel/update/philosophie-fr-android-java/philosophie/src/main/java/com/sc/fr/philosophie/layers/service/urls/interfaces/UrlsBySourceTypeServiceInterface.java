package com.sc.fr.philosophie.layers.service.urls.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;

public interface UrlsBySourceTypeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param sourceType
   */
  void loadUrlsBySourceTypeAsync(String sourceType);
}
