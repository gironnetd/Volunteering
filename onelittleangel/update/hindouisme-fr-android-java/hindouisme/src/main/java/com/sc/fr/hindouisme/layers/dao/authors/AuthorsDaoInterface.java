package com.sc.fr.hindouisme.layers.dao.authors;

import com.sc.fr.hindouisme.transverse.orms.realm.models.Author;

import java.util.List;

public interface AuthorsDaoInterface {

  /**
   *
   * @param idAuthor
   * @return
   */
  Author findAuthorByIdAuthor(int idAuthor);

  /**
   *
   * @param name
   * @return
   */
  Author findAuthorByName(String name);

  /**
   *
   * @param idMovement
   * @return
   */
  List<Author> findAuthorsByIdMovement(int idMovement);

  /**
   *
   * @param idTheme
   * @return
   */
  List<Author> findAuthorsByIdTheme(int idTheme);

  /**
   *
   * @return
   */
  List<Author> findAllAuthors();

  /**
   *
   * @return
   */
  Author findAuthorByRandom();

  /**
   *
   *
   * @return
   */
  Author findAuthorWithPresentationByRandom();

  Author findAuthorByIdPresentation(int idPresentation);

  List<Author> findAllAuthorsFromMainMovement();
}
