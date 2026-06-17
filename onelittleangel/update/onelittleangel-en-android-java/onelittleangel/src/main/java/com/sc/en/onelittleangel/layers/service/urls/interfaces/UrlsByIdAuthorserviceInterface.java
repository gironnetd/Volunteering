package com.sc.en.onelittleangel.layers.service.urls.interfaces;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdAuthorserviceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idAuthor
   */
  void loadUrlsByIdAuthorAsync(int idAuthor);
}
