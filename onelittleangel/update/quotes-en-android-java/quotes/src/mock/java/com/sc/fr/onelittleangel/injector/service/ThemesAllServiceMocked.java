package com.sc.fr.onelittleangel.injector.service;

import com.sc.fr.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.fr.onelittleangel.layers.service.themes.interfaces.ThemesAllServiceInterface;

/**
 * Created by damien on 28/12/2016 for OnelittleAngel Android project.
 */

public class ThemesAllServiceMocked implements ThemesAllServiceInterface {

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

  public ThemesAllServiceMocked() {
    super();
  }

  /**
   *
   */
  @Override
  public void loadAllThemesAsync() {

  }
}
