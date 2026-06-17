package com.sc.fr.christianisme.layers.service.urls.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadUrlsByIdBookAsync(int idBook);
}
