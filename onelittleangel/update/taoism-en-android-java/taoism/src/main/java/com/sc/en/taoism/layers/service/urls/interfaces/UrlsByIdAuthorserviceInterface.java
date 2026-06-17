package com.sc.en.taoism.layers.service.urls.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdAuthorserviceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadUrlsByIdAuthorAsync(int idAuthor);
}
