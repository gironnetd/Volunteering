package com.sc.en.onelittleangel.layers.service.pictures.interfaces;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadPicturesByIdThemeAsync(int idTheme);
}
