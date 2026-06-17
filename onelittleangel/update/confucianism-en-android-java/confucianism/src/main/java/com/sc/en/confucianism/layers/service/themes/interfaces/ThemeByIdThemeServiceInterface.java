package com.sc.en.confucianism.layers.service.themes.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

public interface ThemeByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadThemeByIdThemeAsync(int idTheme);
}
