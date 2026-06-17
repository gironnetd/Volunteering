package com.sc.fr.bouddhisme.layers.dao.presentations;

import com.sc.fr.bouddhisme.layers.dao.DaoManager;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Author;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Book;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Movement;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Presentation;

import java.util.List;

import io.realm.Realm;

public class PresentationsDao implements PresentationsDaoInterface {

  private static final String TAG = "PresentationsDao";
  private Realm realm;
  /***********************************************************
   * Constructor
   **********************************************************/
  public PresentationsDao(DaoManager daoManager){
    //to ensure only DaoManager can instanciate this element
  }

  @Override
  public Presentation findPresentationByIdPresentation(int idPresentation) {
    realm = Realm.getDefaultInstance();
    return realm.where(Presentation.class).equalTo("idPresentation", idPresentation).findFirst();
  }

  @Override
  public Presentation findPresentationByIdAuthor(int idAuthor) {
    realm = Realm.getDefaultInstance();
    Author author = realm.where(Author.class).equalTo("idAuthor", idAuthor).findFirst();
    return author.getPresentation();
  }

  @Override
  public Presentation findPresentationByIdBook(int idBook) {
    realm = Realm.getDefaultInstance();
    Book book = realm.where(Book.class).equalTo("idBook", idBook).findFirst();
    return book.getPresentation();
  }

  @Override
  public Presentation findPresentationByIdMovement(int idMovement) {
    realm = Realm.getDefaultInstance();
    Movement movement = realm.where(Movement.class).equalTo("idMovement", idMovement).findFirst();
    return movement.getPresentation();
  }

  @Override
  public List<Presentation> findAllPresentations() {
    realm = Realm.getDefaultInstance();
    return realm.where(Presentation.class).findAll().sort("idPresentation");
  }
  /***********************************************************
   *  Business Methods
   **********************************************************/
}
