package com.sc.fr.taoisme.layers.service.urls.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface UrlByIdUrlServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idUrl
   */
  void loadUrlByIdUrlAsync(int idUrl);
}
