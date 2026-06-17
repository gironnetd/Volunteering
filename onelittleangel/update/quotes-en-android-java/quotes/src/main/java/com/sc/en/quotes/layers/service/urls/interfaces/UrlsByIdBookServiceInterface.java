package com.sc.en.quotes.layers.service.urls.interfaces;

import com.sc.en.quotes.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadUrlsByIdBookAsync(int idBook);
}
