package com.sc.en.hindouism.layers.service.books.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

public interface BooksByIdThemeServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Finf authors that match the id theme in an asynchronous way
   * @param idTheme The id theme of authors searched
   */
  void loadBooksByIdThemeAsync(int idTheme);
}
