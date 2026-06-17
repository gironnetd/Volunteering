package com.sc.en.bouddhism.layers.service.authors.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;

public interface AuthorsAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Load all the authors from the database asynchronously
   */
  void loadAllAuthorsAsync();
}
