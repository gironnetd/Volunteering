package com.sc.fr.taoisme.layers.service.books.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

public interface BooksAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Load all the authors from the database asynchronously
   */
  void loadAllBooksAsync();
}
