package com.sc.en.christianism.layers.service.urls.interfaces;

import com.sc.en.christianism.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadUrlsByIdBookAsync(int idBook);
}
