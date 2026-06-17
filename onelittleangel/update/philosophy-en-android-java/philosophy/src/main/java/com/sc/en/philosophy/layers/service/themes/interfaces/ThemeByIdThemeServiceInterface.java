package com.sc.en.philosophy.layers.service.themes.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;

public interface ThemeByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadThemeByIdThemeAsync(int idTheme);
}
