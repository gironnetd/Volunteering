package com.sc.en.onelittleangel.layers.service.themes.interfaces;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface ThemeByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadThemeByIdThemeAsync(int idTheme);
}
