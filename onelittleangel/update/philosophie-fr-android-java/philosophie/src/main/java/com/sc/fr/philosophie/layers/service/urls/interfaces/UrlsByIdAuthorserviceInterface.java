package com.sc.fr.philosophie.layers.service.urls.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdAuthorserviceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadUrlsByIdAuthorAsync(int idAuthor);
}
