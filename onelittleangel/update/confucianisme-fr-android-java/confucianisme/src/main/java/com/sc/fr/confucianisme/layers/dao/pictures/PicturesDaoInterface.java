package com.sc.fr.confucianisme.layers.dao.pictures;

import com.sc.fr.confucianisme.transverse.orms.realm.models.Picture;

import java.util.List;

public interface PicturesDaoInterface {

  /**
   *
   * @param idPicture
   * @return
   */
  Picture findPictureByIdPicture(int idPicture);

  /**
   *
   * @param nameSmall
   * @return
   */
  List<Picture> findPicturesByNameSmall(String nameSmall);

  /**
   *
   * @param idAuthor
   * @return
   */
  List<Picture> findPicturesByIdAuthor(int idAuthor);

  /**
   *
   * @param idBook
   * @return
   */
  List<Picture> findPicturesByIdBook(int idBook);

  /**
   *
   * @param idMovement
   * @return
   */
  List<Picture> findPicturesByIdMovement(int idMovement);

  /**
   *
   * @param idTheme
   * @return
   */
  List<Picture> findPicturesByIdTheme(int idTheme);

  /**
   *
   * @return
   */
  List<Picture> findAllPictures();

  /**
   *
   * @return
   */
  Picture findPictureByRandom();
}
