package com.sc.en.quotes.layers.dao.urls;

import com.sc.en.quotes.layers.dao.DaoManager;
import com.sc.en.quotes.transverse.orms.realm.models.Author;
import com.sc.en.quotes.transverse.orms.realm.models.Book;
import com.sc.en.quotes.transverse.orms.realm.models.Movement;
import com.sc.en.quotes.transverse.orms.realm.models.Url;

import java.util.List;

import io.realm.Realm;

public class UrlsDao implements UrlsDaoInterface {

  private static final String TAG = "UrlsDao";
  private Realm realm;

  /***********************************************************
   * Constructor
   **********************************************************/
  public UrlsDao(DaoManager daoManager){
    //to ensure only DaoManager can instanciate this element
  }

  @Override
  public Url findUrlByIdUrl(int idUrl) {
    realm = Realm.getDefaultInstance();
    return realm.where(Url.class).equalTo("idUrl", idUrl).findFirst();
  }

  @Override
  public List<Url> findUrlsBySourceType(String sourceType) {
    realm = Realm.getDefaultInstance();
    return realm.where(Url.class).equalTo("sourceType", sourceType).findAll();
  }

  @Override
  public List<Url> findUrlsByIdSource(String sourceType, int idSource) {
    realm = Realm.getDefaultInstance();
    return realm.where(Url.class).equalTo("idSource", idSource).equalTo("sourceType", sourceType).findAll();
  }

  @Override
  public List<Url> findUrlsByIdAuthor(int idAuthor) {
    realm = Realm.getDefaultInstance();
    Author author = realm.where(Author.class).equalTo("idAuthor", idAuthor).findFirst();
    return author.getUrls();
  }

  @Override
  public List<Url> findUrlsByIdBook(int idBook) {
    realm = Realm.getDefaultInstance();
    Book book = realm.where(Book.class).equalTo("idBook", idBook).findFirst();
    return book.getUrls();
  }

  @Override
  public List<Url> findUrlsByIdMovement(int idMovement) {
    realm = Realm.getDefaultInstance();
    Movement movement = realm.where(Movement.class).equalTo("idMovement", idMovement).findFirst();
    return movement.getUrls();
  }

  @Override
  public List<Url> findAllUrls() {
    realm = Realm.getDefaultInstance();
    return realm.where(Url.class).findAll();
  }
  /***********************************************************
   *  Business Methods
   **********************************************************/
}
