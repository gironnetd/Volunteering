package com.sc.fr.onelittleangel.injector.service;

import com.sc.fr.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.fr.onelittleangel.layers.service.authors.interfaces.AuthorsAllServiceInterface;

/**
 * Created by damien on 28/12/2016 for OnelittleAngel Android project.
 */

public class AuthorsServiceMocked implements AuthorsAllServiceInterface {
  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {

  }

  /**
   * Clean your resource when your service die
   *
   * @param srvManager
   */
  @Override
  public void onDestroy(ServiceManagerInterface srvManager) {

  }

  public AuthorsServiceMocked() {
    super();
  }

  /**
   * Load all the authors from the database asynchronously
   */
  @Override
  public void loadAllAuthorsAsync() {

  }
}
