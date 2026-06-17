package com.sc.fr.philosophie.injector.dao;

import com.sc.fr.philosophie.layers.dao.urls.UrlsDaoInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Url;

import java.util.List;

/**
 * Created by damien on 28/12/2016 for OnelittleAngel Android project.
 */

public class UrlsDaoMocked implements UrlsDaoInterface {

  @Override
  public Url findUrlByIdUrl(int idUrl) {
    return null;
  }

  @Override
  public List<Url> findUrlsBySourceType(String sourceType) {
    return null;
  }

  @Override
  public List<Url> findUrlsByIdSource(String sourceType, int idSource) {
    return null;
  }

  @Override
  public List<Url> findUrlsByIdAuthor(int idAuthor) {
    return null;
  }

  @Override
  public List<Url> findUrlsByIdBook(int idBook) {
    return null;
  }

  @Override
  public List<Url> findUrlsByIdMovement(int idMovement) {
    return null;
  }

  @Override
  public List<Url> findAllUrls() {
    return null;
  }
}
