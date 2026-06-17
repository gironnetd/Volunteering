package com.sc.en.bouddhism.layers.service.authors.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.bouddhism.transverse.orms.realm.models.Author;

import io.reactivex.Observable;

public interface AuthorByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Find the author that match the author name in an asynchronous way
   * @param name The name of the author searched
   */
  Observable<Author> loadAuthorByNameAsync(String name);
}
