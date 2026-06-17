package com.sc.fr.hindouisme.layers.service.themes.interfaces;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

public interface ThemesByIdParentServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idParent
   */
  void loadThemesByIdParentAsync(int idParent);
}
