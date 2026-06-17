package com.sc.fr.hindouisme.layers.service.urls.interfaces;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

public interface UrlsBySourceTypeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param sourceType
   */
  void loadUrlsBySourceTypeAsync(String sourceType);
}
