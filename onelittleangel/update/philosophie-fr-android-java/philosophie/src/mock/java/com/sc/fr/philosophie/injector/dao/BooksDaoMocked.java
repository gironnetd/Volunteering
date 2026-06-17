package com.sc.fr.philosophie.injector.dao;

import com.sc.fr.philosophie.layers.dao.books.BooksDaoInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Book;

import java.util.List;

/**
 * Created by damien on 28/12/2016 for OnelittleAngel Android project.
 */

public class BooksDaoMocked implements BooksDaoInterface {

  @Override
  public Book findBookByIdBook(int idBook) {
    return null;
  }

  @Override
  public Book findBookByName(String name) {
    return null;
  }

  @Override
  public List<Book> findBooksByIdMovement(int idMovement) {
    return null;
  }

  @Override
  public List<Book> findBooksByIdTheme(int idTheme) {
    return null;
  }

  @Override
  public List<Book> findAllBooks() {
    return null;
  }

  @Override
  public Book findBookByRandom() {
    return null;
  }
}
