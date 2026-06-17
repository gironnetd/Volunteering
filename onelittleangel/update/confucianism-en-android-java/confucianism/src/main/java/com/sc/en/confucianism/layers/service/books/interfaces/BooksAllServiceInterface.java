package com.sc.en.confucianism.layers.service.books.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

public interface BooksAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Load all the authors from the database asynchronously
   */
  void loadAllBooksAsync();
}
