package com.sc.fr.christianisme.layers.service.urls.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

public interface UrlsBySourceTypeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param sourceType
   */
  void loadUrlsBySourceTypeAsync(String sourceType);
}
