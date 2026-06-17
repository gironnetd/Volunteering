package com.sc.en.hindouism.layers.service.themes.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

public interface ThemesByIdParentServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idParent
   */
  void loadThemesByIdParentAsync(int idParent);
}
