package com.sc.en.taoism.layers.dao.centuries;

import com.sc.en.taoism.transverse.orms.realm.models.Century;

import java.util.List;

public interface CenturiesDaoInterface {

  /**
   *
   * @param idCentury
   * @return
   */
  Century findCenturyByIdCentury(int idCentury);

  /**
   *
   * @param name
   * @return
   */
  Century findCenturyByName(String name);

  /**
   *
   * @param idAuthor
   * @return
   */
  Century findCenturyByIdAuthor(int idAuthor);

  /**
   *
   * @param idBook
   * @return
   */
  Century findCenturyByIdBook(int idBook);

  /**
   *
   * @return
   */
  List<Century> findAllCenturies();
}
