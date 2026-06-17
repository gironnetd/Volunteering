package com.sc.fr.philosophie.injector.service;

import com.sc.fr.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.fr.onelittleangel.layers.service.urls.interfaces.UrlsAllServiceInterface;

/**
 * Created by damien on 28/12/2016 for OnelittleAngel Android project.
 */

public class UrlsAllServiceMocked implements UrlsAllServiceInterface {

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

  public UrlsAllServiceMocked() {
    super();
  }

  /**
   *
   */
  @Override
  public void loadAllUrlsAsync() {

  }
}
