package com.sc.en.confucianism.layers.service.authors.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

public interface AuthorByIdAuthorServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Find the author that match the author id in an asynchronous way
   * @param idAuthor The name of the author searched
   */
  void loadAuthorByIdAuthorAsync(int idAuthor);
}
