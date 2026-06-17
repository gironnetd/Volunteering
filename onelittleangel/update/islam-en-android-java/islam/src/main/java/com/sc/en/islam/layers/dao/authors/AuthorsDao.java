package com.sc.en.islam.layers.dao.authors;

import com.sc.en.islam.layers.mvp.common.utils.Constants;
import com.sc.en.islam.transverse.orms.realm.models.Author;
import com.sc.en.islam.transverse.orms.realm.models.Theme;
import com.sc.en.islam.layers.dao.DaoManager;
import com.sc.en.islam.transverse.orms.realm.models.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

public class AuthorsDao implements AuthorsDaoInterface {

  private static final String TAG = "AuthorsDao";
  private Realm realm;
  private Author author;
  /***********************************************************
   * Constructor
   **********************************************************/
  public AuthorsDao(DaoManager daoManager){
    //to ensure only DaoManager can instanciate this element
  }

  @Override
  public Author findAuthorByIdAuthor(int idAuthor) {
    realm = Realm.getDefaultInstance();
    return realm.where(Author.class).equalTo("idAuthor", idAuthor).findFirst();
  }

  @Override
  public Author findAuthorByName(String name) {
    realm = Realm.getDefaultInstance();

    //realm.executeTransaction(realm1 -> author = realm1.where(Author.class).equalTo("name", name).findFirst());
    return realm.where(Author.class).equalTo("name", name).findFirst();
  }

  @Override
  public List<Author> findAuthorsByIdMovement(int idMovement) {
    realm = Realm.getDefaultInstance();
    Movement movement = realm.where(Movement.class).equalTo("idMovement", idMovement).findFirst();
    return movement.getAuthors();
  }

  @Override
  public List<Author> findAuthorsByIdTheme(int idTheme) {
    realm = Realm.getDefaultInstance();
    Theme theme = realm.where(Theme.class).equalTo("idTheme", idTheme).findFirst();
    return theme.getAuthors();
  }

  @Override
  public List<Author> findAllAuthors() {
    realm = Realm.getDefaultInstance();
    return realm.where(Author.class).findAll();
  }

  @Override
  public Author findAuthorByRandom() {
// get realm
    realm = Realm.getDefaultInstance();

    realm.executeTransaction(realm1 -> {
      RealmResults<Author> results = realm1.where(Author.class).findAll();

// random generator
      Random r = new Random(System.nanoTime());

// generate first random number to be from the range [0..number of objects)
      int firstRandomNumber = r.nextInt(results.size());

// get first object from results at position randomly generated above
      author = results.get(firstRandomNumber);
    });

// get all objects

    return author;
  }

  /**
   * @return
   */
  @Override
  public Author findAuthorWithPresentationByRandom() {
    Realm realm = Realm.getDefaultInstance();

// get all objects
    RealmResults<Author> results = realm.where(Author.class).isNotNull("presentation").findAll();

// random generator
    Random r = new Random(System.nanoTime());

// generate first random number to be from the range [0..number of objects)
    int firstRandomNumber = r.nextInt(results.size());

// get first object from results at position randomly generated above
    return results.get(firstRandomNumber);
  }

  @Override
  public Author findAuthorByIdPresentation(int idPresentation) {
    Realm realm = Realm.getDefaultInstance();

    return realm.where(Author.class).equalTo("presentation.idPresentation", idPresentation).findFirst();
  }

  @Override
  public List<Author> findAllAuthorsFromMainMovement() {
    Realm realm = Realm.getDefaultInstance();

    Movement appMovement = realm.where(Movement.class).equalTo("movement", Constants.APPLICATION_MOVEMENT).findFirst();

    List<Author> authors = new ArrayList<>();

    authors.addAll(appMovement.getAuthors());

    if (appMovement.getMovements().size() != 0) {

      for (Movement subMovement : appMovement.getMovements()) {

        authors.addAll(subMovement.getAuthors());

        if (subMovement.getMovements().size() != 0) {

          for (Movement subSubMovement : subMovement.getMovements()) {

            authors.addAll(subSubMovement.getAuthors());
          }
        }
      }
    }

    return authors;
  }
  /***********************************************************
   *  Business Methods
   **********************************************************/
}
