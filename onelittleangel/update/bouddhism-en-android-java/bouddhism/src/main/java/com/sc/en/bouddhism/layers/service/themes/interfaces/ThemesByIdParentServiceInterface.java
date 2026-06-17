package com.sc.en.bouddhism.layers.service.themes.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;

public interface ThemesByIdParentServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idParent
   */
  void loadThemesByIdParentAsync(int idParent);
}
