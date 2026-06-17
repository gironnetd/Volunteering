package com.sc.fr.onelittleangel.injector.dao;

import com.sc.fr.onelittleangel.layers.dao.centuries.CenturiesDaoInterface;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Century;

import java.util.List;

/**
 * Created by damien on 28/12/2016 for OnelittleAngel Android project.
 */

public class CenturiesDaoMocked implements CenturiesDaoInterface {
  @Override
  public Century findCenturyByIdCentury(int idCentury) {
    return null;
  }

  @Override
  public Century findCenturyByName(String name) {
    return null;
  }

  @Override
  public Century findCenturyByIdAuthor(int idAuthor) {
    return null;
  }

  @Override
  public Century findCenturyByIdBook(int idBook) {
    return null;
  }

  @Override
  public List<Century> findAllCenturies() {
    return null;
  }
}
