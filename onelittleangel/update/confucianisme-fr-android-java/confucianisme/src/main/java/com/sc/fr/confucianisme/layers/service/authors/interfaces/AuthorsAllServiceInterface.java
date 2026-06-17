package com.sc.fr.confucianisme.layers.service.authors.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;

public interface AuthorsAllServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Load all the authors from the database asynchronously
   */
  void loadAllAuthorsAsync();
}
