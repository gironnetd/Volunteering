package com.sc.fr.philosophie.layers.service.authors.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;

public interface AuthorsAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Load all the authors from the database asynchronously
   */
  void loadAllAuthorsAsync();
}
