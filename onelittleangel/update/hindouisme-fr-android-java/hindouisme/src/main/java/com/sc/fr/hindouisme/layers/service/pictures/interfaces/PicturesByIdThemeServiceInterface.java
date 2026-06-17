package com.sc.fr.hindouisme.layers.service.pictures.interfaces;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

public interface PicturesByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @param idTheme
   */
  void loadPicturesByIdThemeAsync(int idTheme);
}
