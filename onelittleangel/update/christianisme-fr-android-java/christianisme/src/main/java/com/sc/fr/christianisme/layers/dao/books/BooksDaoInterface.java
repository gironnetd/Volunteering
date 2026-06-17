package com.sc.fr.christianisme.layers.dao.books;

import com.sc.fr.christianisme.transverse.orms.realm.models.Book;

import java.util.List;

public interface BooksDaoInterface {

  /**
   *
   * @param idBook
   * @return
   */
  Book findBookByIdBook(int idBook);

  /**
   *
   * @param name
   * @return
   */
  Book findBookByName(String name);

  /**
   *
   * @param idMovement
   * @return
   */
  List<Book> findBooksByIdMovement(int idMovement);

  /**
   *
   * @param idTheme
   * @return
   */
  List<Book> findBooksByIdTheme(int idTheme);

  /**
   *
   * @return
   */
  List<Book> findAllBooks();

  /**
   *
   * @return
   */
  Book findBookByRandom();

  Book findBookByIdPresentation(int idPresentation);

  List<Book> findAllBooksFromMainMovement();

}
