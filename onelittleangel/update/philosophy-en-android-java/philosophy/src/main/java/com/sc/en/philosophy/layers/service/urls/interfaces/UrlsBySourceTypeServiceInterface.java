package com.sc.en.philosophy.layers.service.urls.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;

public interface UrlsBySourceTypeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param sourceType
   */
  void loadUrlsBySourceTypeAsync(String sourceType);
}
