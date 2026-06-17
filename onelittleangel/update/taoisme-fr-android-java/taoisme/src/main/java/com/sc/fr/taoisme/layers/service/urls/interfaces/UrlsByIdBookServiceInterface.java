package com.sc.fr.taoisme.layers.service.urls.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadUrlsByIdBookAsync(int idBook);
}
