package com.sc.fr.confucianisme.layers.service.urls.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdAuthorserviceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadUrlsByIdAuthorAsync(int idAuthor);
}
