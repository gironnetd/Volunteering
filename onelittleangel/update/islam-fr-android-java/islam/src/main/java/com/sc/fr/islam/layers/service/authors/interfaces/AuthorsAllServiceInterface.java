package com.sc.fr.islam.layers.service.authors.interfaces;

import com.sc.fr.islam.layers.service.MotherBusinessServiceInterface;

public interface AuthorsAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Load all the authors from the database asynchronously
   */
  void loadAllAuthorsAsync();
}
