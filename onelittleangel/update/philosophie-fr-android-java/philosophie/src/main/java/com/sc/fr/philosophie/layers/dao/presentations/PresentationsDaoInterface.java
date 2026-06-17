package com.sc.fr.philosophie.layers.dao.presentations;

import com.sc.fr.philosophie.transverse.orms.realm.models.Presentation;

import java.util.List;

public interface PresentationsDaoInterface {

  /**
   *
   * @param idPresentation
   * @return
   */
  Presentation findPresentationByIdPresentation(int idPresentation);

  /**
   *
   * @param idAuthor
   * @return
   */
  Presentation findPresentationByIdAuthor(int idAuthor);

  /**
   *
   * @param idBook
   * @return
   */
  Presentation findPresentationByIdBook(int idBook);

  /**
   *
   * @param idMovement
   * @return
   */
  Presentation findPresentationByIdMovement(int idMovement);

  /**
   *
   * @return
   */
  List<Presentation> findAllPresentations();
}
