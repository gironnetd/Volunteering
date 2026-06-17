package com.sc.en.quotes.layers.service.authors.interfaces;

import com.sc.en.quotes.layers.service.MotherBusinessServiceInterface;
import com.sc.en.quotes.transverse.orms.realm.models.Author;

import io.reactivex.Observable;

public interface AuthorByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Find the author that match the author name in an asynchronous way
   * @param name The name of the author searched
   */
  Observable<Author> loadAuthorByNameAsync(String name);
}
