package com.sc.fr.hindouisme.layers.service.urls.interfaces;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdAuthorserviceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadUrlsByIdAuthorAsync(int idAuthor);
}
