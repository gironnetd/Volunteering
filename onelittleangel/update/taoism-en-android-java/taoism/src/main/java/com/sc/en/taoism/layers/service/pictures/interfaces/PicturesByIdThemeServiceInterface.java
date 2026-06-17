package com.sc.en.taoism.layers.service.pictures.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadPicturesByIdThemeAsync(int idTheme);
}
