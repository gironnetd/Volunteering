package com.sc.fr.philosophie.injector.dao;

import com.sc.fr.philosophie.layers.dao.authors.AuthorsDaoInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Author;

import java.util.List;

/**
 * Created by damien on 28/12/2016 for OnelittleAngel Android project.
 */

public class AuthorsDaoMocked implements AuthorsDaoInterface {

  @Override
  public Author findAuthorByIdAuthor(int idAuthor) {
    return null;
  }

  @Override
  public Author findAuthorByName(String name) {
    return null;
  }

  @Override
  public List<Author> findAuthorsByIdMovement(int idMovement) {
    return null;
  }

  @Override
  public List<Author> findAuthorsByIdTheme(int idTheme) {
    return null;
  }

  @Override
  public List<Author> findAllAuthors() {
    return null;
  }

  @Override
  public Author findAuthorByRandom() {
    return null;
  }

  @Override
  public Author findAuthorWithPresentationByRandom() {
    return null;
  }
}
