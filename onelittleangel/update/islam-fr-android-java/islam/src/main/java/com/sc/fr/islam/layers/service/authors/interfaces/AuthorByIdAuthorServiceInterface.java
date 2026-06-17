package com.sc.fr.islam.layers.service.authors.interfaces;

import com.sc.fr.islam.layers.service.MotherBusinessServiceInterface;

public interface AuthorByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Find the author that match the author id in an asynchronous way
   * @param idAuthor The name of the author searched
   */
  void loadAuthorByIdAuthorAsync(int idAuthor);
}
