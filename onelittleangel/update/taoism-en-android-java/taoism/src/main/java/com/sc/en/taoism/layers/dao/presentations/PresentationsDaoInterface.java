package com.sc.en.taoism.layers.dao.presentations;

import com.sc.en.taoism.transverse.orms.realm.models.Presentation;

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
