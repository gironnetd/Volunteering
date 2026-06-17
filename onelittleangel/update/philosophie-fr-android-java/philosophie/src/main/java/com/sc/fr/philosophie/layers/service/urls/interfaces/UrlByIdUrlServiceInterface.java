package com.sc.fr.philosophie.layers.service.urls.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;

public interface UrlByIdUrlServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idUrl
   */
  void loadUrlByIdUrlAsync(int idUrl);
}
