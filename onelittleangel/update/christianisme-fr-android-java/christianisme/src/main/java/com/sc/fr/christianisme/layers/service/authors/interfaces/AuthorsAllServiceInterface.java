package com.sc.fr.christianisme.layers.service.authors.interfaces;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

public interface AuthorsAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Load all the authors from the database asynchronously
   */
  void loadAllAuthorsAsync();
}
