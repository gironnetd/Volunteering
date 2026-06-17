package com.sc.en.christianism.layers.service.authors.interfaces;

import com.sc.en.christianism.layers.service.MotherBusinessServiceInterface;

public interface AuthorsAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Load all the authors from the database asynchronously
   */
  void loadAllAuthorsAsync();
}
