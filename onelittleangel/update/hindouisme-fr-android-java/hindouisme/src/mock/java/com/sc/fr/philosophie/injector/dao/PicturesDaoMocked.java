package com.sc.fr.philosophie.injector.dao;

import com.sc.fr.philosophie.layers.dao.pictures.PicturesDaoInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Picture;

import java.util.List;

/**
 * Created by damien on 28/12/2016 for OnelittleAngel Android project.
 */

public class PicturesDaoMocked implements PicturesDaoInterface {
  @Override
  public Picture findPictureByIdPicture(int idPicture) {
    return null;
  }

  @Override
  public List<Picture> findPicturesByNameSmall(String name) {
    return null;
  }

  @Override
  public List<Picture> findPicturesByIdAuthor(int idAuthor) {
    return null;
  }

  @Override
  public List<Picture> findPicturesByIdBook(int idBook) {
    return null;
  }

  @Override
  public List<Picture> findPicturesByIdMovement(int idMovement) {
    return null;
  }

  @Override
  public List<Picture> findPicturesByIdTheme(int idTheme) {
    return null;
  }

  @Override
  public List<Picture> findAllPictures() {
    return null;
  }

  @Override
  public Picture findPictureByRandom() {
    return null;
  }
}
