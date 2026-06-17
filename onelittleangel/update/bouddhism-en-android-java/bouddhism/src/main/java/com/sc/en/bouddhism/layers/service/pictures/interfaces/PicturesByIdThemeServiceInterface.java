package com.sc.en.bouddhism.layers.service.pictures.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadPicturesByIdThemeAsync(int idTheme);
}
