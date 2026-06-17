package com.sc.en.philosophy.layers.service.urls.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;

public interface UrlByIdUrlServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idUrl
   */
  void loadUrlByIdUrlAsync(int idUrl);
}
