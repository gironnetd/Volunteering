package com.sc.en.taoism.layers.service.themes.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

public interface ThemeByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadThemeByIdThemeAsync(int idTheme);
}
