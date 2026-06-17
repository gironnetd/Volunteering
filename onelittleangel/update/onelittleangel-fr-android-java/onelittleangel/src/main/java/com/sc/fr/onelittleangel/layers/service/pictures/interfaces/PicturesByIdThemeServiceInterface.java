package com.sc.fr.onelittleangel.layers.service.pictures.interfaces;

import com.sc.fr.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadPicturesByIdThemeAsync(int idTheme);
}
