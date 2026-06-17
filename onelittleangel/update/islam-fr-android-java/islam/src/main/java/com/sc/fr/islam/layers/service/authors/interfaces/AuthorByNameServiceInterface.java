package com.sc.fr.islam.layers.service.authors.interfaces;

import com.sc.fr.islam.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.islam.transverse.orms.realm.models.Author;

import io.reactivex.Observable;

public interface AuthorByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Find the author that match the author name in an asynchronous way
   * @param name The name of the author searched
   */
  Observable<Author> loadAuthorByNameAsync(String name);
}
