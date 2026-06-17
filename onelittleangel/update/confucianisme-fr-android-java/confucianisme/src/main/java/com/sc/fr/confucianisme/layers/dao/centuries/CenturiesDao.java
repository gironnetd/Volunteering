package com.sc.fr.confucianisme.layers.dao.centuries;

import com.sc.fr.confucianisme.transverse.orms.realm.models.Author;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Century;
import com.sc.fr.confucianisme.layers.dao.DaoManager;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Book;

import java.util.List;

import io.realm.Realm;

public class CenturiesDao implements CenturiesDaoInterface {

  private static final String TAG = "CenturiesDao";
  private Realm realm;
  /***********************************************************
   * Constructor
   **********************************************************/
  public CenturiesDao(DaoManager daoManager){
    //to ensure only DaoManager can instanciate this element
  }

  @Override
  public Century findCenturyByIdCentury(int idCentury) {
    realm = Realm.getDefaultInstance();
    return realm.where(Century.class).equalTo("idCentury", idCentury).findFirst();
  }

  @Override
  public Century findCenturyByName(String name) {
    realm = Realm.getDefaultInstance();
    return realm.where(Century.class).equalTo("century",name).findFirst();
  }

  @Override
  public Century findCenturyByIdAuthor(int idAuthor) {
    realm = Realm.getDefaultInstance();
    Author author = realm.where(Author.class).equalTo("idAuthor", idAuthor).findFirst();
    return author.getCentury();
  }

  @Override
  public Century findCenturyByIdBook(int idBook) {
    realm = Realm.getDefaultInstance();
    Book book = realm.where(Book.class).equalTo("idBook", idBook).findFirst();
    return book.getCentury();
  }

  @Override
  public List<Century> findAllCenturies() {
    realm = Realm.getDefaultInstance();
    return realm.where(Century.class).findAll();
  }
  /***********************************************************
   *  Business Methods
   **********************************************************/
}
