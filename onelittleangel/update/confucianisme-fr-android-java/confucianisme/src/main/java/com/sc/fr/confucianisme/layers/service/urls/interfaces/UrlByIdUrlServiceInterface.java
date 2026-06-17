package com.sc.fr.confucianisme.layers.service.urls.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;

public interface UrlByIdUrlServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idUrl
   */
  void loadUrlByIdUrlAsync(int idUrl);
}
