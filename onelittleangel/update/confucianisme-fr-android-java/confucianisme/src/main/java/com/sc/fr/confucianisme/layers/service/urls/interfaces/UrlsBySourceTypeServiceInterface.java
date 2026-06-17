package com.sc.fr.confucianisme.layers.service.urls.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;

public interface UrlsBySourceTypeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param sourceType
   */
  void loadUrlsBySourceTypeAsync(String sourceType);
}
