package com.sc.fr.taoisme.layers.service.themes.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface ThemeByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadThemeByIdThemeAsync(int idTheme);
}
