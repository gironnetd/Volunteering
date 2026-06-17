package com.sc.fr.philosophie.layers.service.books.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Book;

import io.reactivex.Observable;

public interface BookByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   * Find the author that match the author name in an asynchronous way
   * @param name The name of the author searched
   */
  Observable<Book> loadBookByNameAsync(String name);
}
