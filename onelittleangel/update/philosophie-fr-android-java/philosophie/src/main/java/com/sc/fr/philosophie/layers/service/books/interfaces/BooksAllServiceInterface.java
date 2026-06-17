package com.sc.fr.philosophie.layers.service.books.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;

public interface BooksAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Load all the authors from the database asynchronously
   */
  void loadAllBooksAsync();
}
