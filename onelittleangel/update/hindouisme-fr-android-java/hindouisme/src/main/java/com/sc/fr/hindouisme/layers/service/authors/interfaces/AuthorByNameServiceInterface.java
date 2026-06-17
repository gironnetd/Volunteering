package com.sc.fr.hindouisme.layers.service.authors.interfaces;

import com.sc.fr.hindouisme.transverse.orms.realm.models.Author;
import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

import io.reactivex.Observable;

public interface AuthorByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Find the author that match the author name in an asynchronous way
   * @param name The name of the author searched
   */
  Observable<Author> loadAuthorByNameAsync(String name);
}
