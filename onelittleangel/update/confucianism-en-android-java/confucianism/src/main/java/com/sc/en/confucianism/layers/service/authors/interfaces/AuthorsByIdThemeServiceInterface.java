package com.sc.en.confucianism.layers.service.authors.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

public interface AuthorsByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Find authors that match the id theme in an asynchronous way
   * @param idTheme The id theme of authors searched
   */
  void loadAuthorsByIdThemeAsync(int idTheme);
}
