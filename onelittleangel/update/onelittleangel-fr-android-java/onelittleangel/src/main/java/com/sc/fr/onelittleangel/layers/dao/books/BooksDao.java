package com.sc.fr.onelittleangel.layers.dao.books;

import com.sc.fr.onelittleangel.layers.dao.DaoManager;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Book;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Movement;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Theme;

import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

public class BooksDao implements BooksDaoInterface {

  private static final String TAG = "BooksDao";
  private Realm realm;

  /***********************************************************
   * Constructor
   **********************************************************/
  public BooksDao(DaoManager daoManager){
    //to ensure only DaoManager can instanciate this element
  }

  @Override
  public Book findBookByIdBook(int idBook) {
    realm = Realm.getDefaultInstance();
    return realm.where(Book.class).equalTo("idBook", idBook).findFirst();
  }

  @Override
  public Book findBookByName(String name) {
    realm = Realm.getDefaultInstance();
    return realm.where(Book.class).equalTo("name", name).findFirst();
  }

  @Override
  public List<Book> findBooksByIdMovement(int idMovement) {
    realm = Realm.getDefaultInstance();
    Movement movement = realm.where(Movement.class).equalTo("idMovement", idMovement).findFirst();
    return movement.getBooks();
  }

  @Override
  public List<Book> findBooksByIdTheme(int idTheme) {
    realm = Realm.getDefaultInstance();

    Theme theme = realm.where(Theme.class).equalTo("idTheme", idTheme).findFirst();
    return theme.getBooks();
  }

  @Override
  public List<Book> findAllBooks() {
    realm = Realm.getDefaultInstance();
    return realm.where(Book.class).findAll();
  }

  /**
   * @return
   */
  @Override
  public Book findBookByRandom() {
// get realm
    realm = Realm.getDefaultInstance();

// get all objects
    RealmResults<Book> results = realm.where(Book.class).findAll();

// random generator
    Random r = new Random(System.nanoTime());

// generate first random number to be from the range [0..number of objects)
    int firstRandomNumber = r.nextInt(results.size());

// get first object from results at position randomly generated above
    return results.get(firstRandomNumber);
  }

  @Override
  public Book findBookByIdPresentation(int idPresentation) {
    Realm realm = Realm.getDefaultInstance();

    return realm.where(Book.class).equalTo("presentation.idPresentation", idPresentation).findFirst();  }

  @Override
  public List<Book> findAllBooksFromMainMovements() {
    return null;
  }
  /***********************************************************
   *  Business Methods
   **********************************************************/
}
