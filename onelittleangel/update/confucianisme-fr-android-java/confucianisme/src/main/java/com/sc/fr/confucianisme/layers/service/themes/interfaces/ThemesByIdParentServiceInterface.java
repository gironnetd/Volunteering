package com.sc.fr.confucianisme.layers.service.themes.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;

public interface ThemesByIdParentServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idParent
   */
  void loadThemesByIdParentAsync(int idParent);
}
