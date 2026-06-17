package com.sc.en.quotes.layers.service.pictures.interfaces;

import com.sc.en.quotes.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadPicturesByIdThemeAsync(int idTheme);
}
