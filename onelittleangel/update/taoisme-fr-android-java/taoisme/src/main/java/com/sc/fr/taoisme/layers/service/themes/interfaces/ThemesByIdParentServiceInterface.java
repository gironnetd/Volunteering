package com.sc.fr.taoisme.layers.service.themes.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface ThemesByIdParentServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idParent
   */
  void loadThemesByIdParentAsync(int idParent);
}
