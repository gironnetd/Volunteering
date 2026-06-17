package com.sc.en.taoism.layers.dao.urls;

import com.sc.en.taoism.transverse.orms.realm.models.Url;

import java.util.List;

public interface UrlsDaoInterface {

  /**
   *
   * @param idUrl
   * @return
   */
  Url findUrlByIdUrl(int idUrl);

  /**
   *
   * @param sourceType
   * @return
   */
  List<Url> findUrlsBySourceType(String sourceType);

  /**
   *
   * @param sourceType
   * @param idSource
   * @return
   */
  List<Url> findUrlsByIdSource(String sourceType, int idSource);


  /**
   *
   * @param idAuthor
   * @return
   */
  List<Url> findUrlsByIdAuthor(int idAuthor);

  /**
   *
   * @param idBook
   * @return
   */
  List<Url> findUrlsByIdBook(int idBook);

  /**
   *
   * @param idMovement
   * @return
   */
  List<Url> findUrlsByIdMovement(int idMovement);

  /**
   *
   * @return
   */
  List<Url> findAllUrls();
}
