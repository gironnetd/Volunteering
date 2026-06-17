package com.sc.en.christianism.layers.service.authors.interfaces;

import com.sc.en.christianism.transverse.orms.realm.models.Author;
import com.sc.en.christianism.layers.service.MotherBusinessServiceInterface;

import io.reactivex.Observable;

public interface AuthorByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Find the author that match the author name in an asynchronous way
   * @param name The name of the author searched
   */
  Observable<Author> loadAuthorByNameAsync(String name);
}
