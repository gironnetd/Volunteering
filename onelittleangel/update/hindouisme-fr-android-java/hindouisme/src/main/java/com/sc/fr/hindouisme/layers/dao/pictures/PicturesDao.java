package com.sc.fr.hindouisme.layers.dao.pictures;

import com.sc.fr.hindouisme.transverse.orms.realm.models.Author;
import com.sc.fr.hindouisme.transverse.orms.realm.models.Movement;
import com.sc.fr.hindouisme.transverse.orms.realm.models.Picture;
import com.sc.fr.hindouisme.transverse.orms.realm.models.Theme;
import com.sc.fr.hindouisme.layers.dao.DaoManager;
import com.sc.fr.hindouisme.transverse.orms.realm.models.Book;

import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

public class PicturesDao implements PicturesDaoInterface {

  private static final String TAG = "PicturesDao";
  private Realm realm;
  /***********************************************************
   * Constructor
   **********************************************************/
  public PicturesDao(DaoManager daoManager){
    //to ensure only DaoManager can instanciate this element
  }

  /***********************************************************
   *  Business Methods
   **********************************************************/

  @Override
  public Picture findPictureByIdPicture(int idPicture) {
    realm = Realm.getDefaultInstance();
    return realm.where(Picture.class).equalTo("idPicture", idPicture).findFirst();
  }

  @Override
  public List<Picture> findPicturesByNameSmall(String nameSmall) {
    realm = Realm.getDefaultInstance();
    return realm.where(Picture.class).equalTo("nameSmall", nameSmall).findAll().sort("idPicture");
  }

  @Override
  public List<Picture> findPicturesByIdAuthor(int idAuthor) {
    realm = Realm.getDefaultInstance();
    Author author = realm.where(Author.class).equalTo("idAuthor", idAuthor).findFirst();
    return author.getPictures();
  }

  @Override
  public List<Picture> findPicturesByIdBook(int idBook) {
    realm = Realm.getDefaultInstance();
    Book book = realm.where(Book.class).equalTo("idBook", idBook).findFirst();
    return book.getPictures();
  }

  @Override
  public List<Picture> findPicturesByIdMovement(int idMovement) {
    realm = Realm.getDefaultInstance();
    Movement movement = realm.where(Movement.class).equalTo("idMovement", idMovement).findFirst();
    return movement.getPictures();
  }

  @Override
  public List<Picture> findPicturesByIdTheme(int idTheme) {
    realm = Realm.getDefaultInstance();
    Theme theme = realm.where(Theme.class).equalTo("idTheme", idTheme).findFirst();
    return theme.getPictures();
  }

  @Override
  public List<Picture> findAllPictures() {
    realm = Realm.getDefaultInstance();
    return realm.where(Picture.class).findAll();
  }

  @Override
  public Picture findPictureByRandom() {
    Realm realm = Realm.getDefaultInstance();

// get all objects
    RealmResults<Picture> results = realm.where(Picture.class).findAll();

// random generator
    Random r = new Random(System.nanoTime());

// generate first random number to be from the range [0..number of objects)
    int firstRandomNumber = r.nextInt(results.size());

// get first object from results at position randomly generated above
    return results.get(firstRandomNumber);
  }
}
