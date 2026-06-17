package com.sc.fr.onelittleangel.layers.service.urls.interfaces;

import com.sc.fr.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface UrlsByIdBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idBook
   */
  void loadUrlsByIdBookAsync(int idBook);
}
