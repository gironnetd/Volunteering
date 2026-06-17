package com.sc.fr.christianisme.layers.service.urls.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

public interface UrlByIdUrlServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idUrl
   */
  void loadUrlByIdUrlAsync(int idUrl);
}
