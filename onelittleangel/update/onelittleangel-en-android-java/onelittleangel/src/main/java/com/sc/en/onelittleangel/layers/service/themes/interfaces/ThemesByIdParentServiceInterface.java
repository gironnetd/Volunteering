package com.sc.en.onelittleangel.layers.service.themes.interfaces;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface ThemesByIdParentServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idParent
   */
  void loadThemesByIdParentAsync(int idParent);
}
