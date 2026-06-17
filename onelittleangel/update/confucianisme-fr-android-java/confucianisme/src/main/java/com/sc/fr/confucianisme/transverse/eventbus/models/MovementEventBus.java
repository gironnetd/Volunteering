package com.sc.fr.confucianisme.transverse.eventbus.models;

import android.util.Log;

import com.sc.fr.confucianisme.transverse.orms.realm.models.Author;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Book;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Movement;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Picture;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Url;

import java.util.ArrayList;
import java.util.List;

public class MovementEventBus {

  private static final String TAG = "MovementEventBus";

  private long idMovement;
  private long idParentMovement;
  private String movement;
  private String mcc1;
  private String mcc2;
  private PresentationEventBus presentation;
  private String mcc3;
  private long nbQuotes;
  private long nbAuthors;
  private long nbAuthorsQuotes;
  private long nbBooks;
  private long nbBooksQuotes;
  private boolean checked;
  private long nbTotalQuotes;
  private long nbTotalAuthors;
  private long nbTotalBooks;
  private long nbSubcourants;
  private long nbAuthorsSubcourants;
  private long nbBooksSubcourants;

  private List<UrlEventBus> urls;
  private List<PictureEventBus> pictures;
  private List<MovementEventBus> movements;
  private List<BookEventBus> books;
  private List<AuthorEventBus> authors;

  private MovementEventBus() {
  }

  private static MovementEventBus authorsMovementEventBus(Movement movement) {
    //Log.d(TAG, " :  authorsMovement.toString()");

    MovementEventBus authorsMovement = new MovementEventBus();

    authorsMovement.setIdMovement(movement.getIdMovement());
  //  authorsMovement.setIdParentMovement(movement.getParentMovement().getIdMovement());
    authorsMovement.setMovement(movement.getMovement());

//    mcc1 = movement.getMcc1();
//    mcc2 = movement.getMcc2();
//    presentation = new PresentationEventBus(movement.getPresentation());
//    mcc3 = movement.getMcc3();
//    nbQuotes = movement.getNbQuotes();
    authorsMovement.setNbAuthors(movement.getNbAuthors());
    authorsMovement.setNbAuthorsQuotes(movement.getNbAuthorsQuotes());
//    nbBooks = movement.getNbBooks();
//    nbBooksQuotes = movement.getNbBooksQuotes();
//    checked = movement.getChecked();
//    nbTotalQuotes = movement.getNbTotalQuotes();
    authorsMovement.setNbTotalAuthors(movement.getNbTotalAuthors());
//    nbTotalBooks = movement.getNbTotalBooks();
//    nbSubcourants = movement.getNbSubcourants();
    authorsMovement.setNbAuthorsSubcourants(movement.getNbAuthorsSubcourants());
//    nbBooksSubcourants = movement.getNbBooksSubcourants();

    List<MovementEventBus> movements = new ArrayList<>();
    for (Movement m : movement.getMovements()) {
      movements.add(authorsMovementEventBus(m));
    }
    authorsMovement.setMovements(movements);

    List<AuthorEventBus> authors = new ArrayList<>();
    for (Author a : movement.getAuthors()) {
      authors.add(new AuthorEventBus(a));
    }
    authorsMovement.setAuthors(authors);

    //Log.d(TAG, " : " + authorsMovement.toString());

    return authorsMovement;
  }

  private static MovementEventBus booksMovementEventBus(Movement movement) {
    MovementEventBus booksMovement = new MovementEventBus();

    booksMovement.setIdMovement(movement.getIdMovement());
  //  idParentMovement = movement.getParentMovement().getIdMovement();
  //  this.movement = movement.getMovement();
    booksMovement.setMovement(movement.getMovement());
//    mcc1 = movement.getMcc1();
//    mcc2 = movement.getMcc2();
//  //  presentation = new PresentationEventBus(movement.getPresentation());
//    mcc3 = movement.getMcc3();
//    nbQuotes = movement.getNbQuotes();
//    nbAuthors = movement.getNbAuthors();
//    nbAuthorsQuotes = movement.getNbAuthorsQuotes();
    booksMovement.setNbBooks(movement.getNbBooks());
    booksMovement.setNbBooksQuotes(movement.getNbBooksQuotes());
//    checked = movement.getChecked();
//    nbTotalQuotes = movement.getNbTotalQuotes();
//    nbTotalAuthors = movement.getNbTotalAuthors();
    booksMovement.setNbTotalBooks(movement.getNbTotalBooks());
  //  nbTotalBooks = movement.getNbTotalBooks();
   // nbSubcourants = movement.getNbSubcourants();
  //  nbAuthorsSubcourants = movement.getNbAuthorsSubcourants();
    booksMovement.setNbBooksSubcourants(movement.getNbBooksSubcourants());
  //  nbBooksSubcourants = movement.getNbBooksSubcourants();

    List<MovementEventBus> movements = new ArrayList<>();
    for (Movement m : movement.getMovements()) {
      movements.add(booksMovementEventBus(m));
    }
    booksMovement.setMovements(movements);

    List<BookEventBus> books = new ArrayList<>();
    for (Book b : movement.getBooks()) {
      books.add(new BookEventBus(b));
    }
    booksMovement.setBooks(books);

    return booksMovement;
  }

  public MovementEventBus(Movement movement) {
    idMovement = movement.getIdMovement();
    if(movement.getParentMovement() != null){
    idParentMovement = movement.getParentMovement().getIdMovement();
    }
    this.movement = movement.getMovement();
    mcc1 = movement.getMcc1();
    mcc2 = movement.getMcc2();
  //  presentation = new PresentationEventBus(movement.getPresentation());
    mcc3 = movement.getMcc3();
    nbQuotes = movement.getNbQuotes();
    nbAuthors = movement.getNbAuthors();
    nbAuthorsQuotes = movement.getNbAuthorsQuotes();
    nbBooks = movement.getNbBooks();
    nbBooksQuotes = movement.getNbBooksQuotes();
    checked = movement.isChecked();
    nbTotalQuotes = movement.getNbTotalQuotes();
    nbTotalAuthors = movement.getNbTotalAuthors();
    nbTotalBooks = movement.getNbTotalBooks();
    nbSubcourants = movement.getNbSubcourants();
    nbAuthorsSubcourants = movement.getNbAuthorsSubcourants();
    nbBooksSubcourants = movement.getNbBooksSubcourants();

    if(movement.getMovements() != null) {
      movements = new ArrayList<>();
      for (Movement m : movement.getMovements()) {
        movements.add(new MovementEventBus(m));
      }
    }

    if(movement.getAuthors() != null) {
      authors = new ArrayList<>();
      for (Author a : movement.getAuthors()) {
        authors.add(new AuthorEventBus(a));
      }
    }

    if(movement.getBooks() != null) {
      books = new ArrayList<>();
      for (Book b : movement.getBooks()) {
        books.add(new BookEventBus(b));
      }
    }

    if(movement.getUrls() != null) {
      urls = new ArrayList<>();
      for (Url ur : movement.getUrls()) {
        urls.add(new UrlEventBus(ur));
      }
    }

    if(movement.getPictures() != null) {
      pictures = new ArrayList<>();
      for (Picture pr : movement.getPictures()) {
        pictures.add(new PictureEventBus(pr));
      }
    }
  }

  public long getIdMovement() {
    return idMovement;
  }

  private void setIdMovement(long idMovement) {
    this.idMovement = idMovement;
  }

  public long getIdParentMovement() {
    return idParentMovement;
  }

  public void setIdParentMovement(long idParentMovement) {
    this.idParentMovement = idParentMovement;
  }

  public String getMovement() {
    return movement;
  }

  private void setMovement(String movement) {
    this.movement = movement;
  }

  public String getMcc1() {
    return mcc1;
  }

  public void setMcc1(String mcc1) {
    this.mcc1 = mcc1;
  }

  public String getMcc2() {
    return mcc2;
  }

  public void setMcc2(String mcc2) {
    this.mcc2 = mcc2;
  }

  public PresentationEventBus getPresentation() {
    return presentation;
  }

  public void setPresentation(PresentationEventBus presentation) {
    this.presentation = presentation;
  }

  public String getMcc3() {
    return mcc3;
  }

  public void setMcc3(String mcc3) {
    this.mcc3 = mcc3;
  }

  public long getNbQuotes() {
    return nbQuotes;
  }

  public void setNbQuotes(long nbQuotes) {
    this.nbQuotes = nbQuotes;
  }

  public long getNbAuthors() {
    return nbAuthors;
  }

  private void setNbAuthors(long nbAuthors) {
    this.nbAuthors = nbAuthors;
  }

  public long getNbAuthorsQuotes() {
    return nbAuthorsQuotes;
  }

  private void setNbAuthorsQuotes(long nbAuthorsQuotes) {
    this.nbAuthorsQuotes = nbAuthorsQuotes;
  }

  public long getNbBooks() {
    return nbBooks;
  }

  private void setNbBooks(long nbBooks) {
    this.nbBooks = nbBooks;
  }

  public long getNbBooksQuotes() {
    return nbBooksQuotes;
  }

  private void setNbBooksQuotes(long nbBooksQuotes) {
    this.nbBooksQuotes = nbBooksQuotes;
  }

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  public long getNbTotalQuotes() {
    return nbTotalQuotes;
  }

  public void setNbTotalQuotes(long nbTotalQuotes) {
    this.nbTotalQuotes = nbTotalQuotes;
  }

  public long getNbTotalAuthors() {
    return nbTotalAuthors;
  }

  private void setNbTotalAuthors(long nbTotalAuthors) {
    this.nbTotalAuthors = nbTotalAuthors;
  }

  public long getNbTotalBooks() {
    return nbTotalBooks;
  }

  private void setNbTotalBooks(long nbTotalBooks) {
    this.nbTotalBooks = nbTotalBooks;
  }

  public long getNbSubcourants() {
    return nbSubcourants;
  }

  public void setNbSubcourants(long nbSubcourants) {
    this.nbSubcourants = nbSubcourants;
  }

  public long getNbAuthorsSubcourants() {
    return nbAuthorsSubcourants;
  }

  private void setNbAuthorsSubcourants(long nbAuthorsSubcourants) {
    this.nbAuthorsSubcourants = nbAuthorsSubcourants;
  }

  public long getNbBooksSubcourants() {
    return nbBooksSubcourants;
  }

  private void setNbBooksSubcourants(long nbBooksSubcourants) {
    this.nbBooksSubcourants = nbBooksSubcourants;
  }

  public List<UrlEventBus> getUrls() {
    return urls;
  }

  public void setUrls(List<UrlEventBus> urls) {
    this.urls = urls;
  }

  public List<PictureEventBus> getPictures() {
    return pictures;
  }

  public void setPictures(List<PictureEventBus> pictures) {
    this.pictures = pictures;
  }

  public List<MovementEventBus> getMovements() {
    return movements;
  }

  private void setMovements(List<MovementEventBus> movements) {
    this.movements = movements;
  }

  public List<BookEventBus> getBooks() {
    return books;
  }

  private void setBooks(List<BookEventBus> books) {
    this.books = books;
  }

  public List<AuthorEventBus> getAuthors() {
    return authors;
  }

  private void setAuthors(List<AuthorEventBus> authors) {
    this.authors = authors;
  }

  @Override
  public String toString() {
    return "MovementEventBus{" +
      "idMovement=" + idMovement +
      ", idParentMovement=" + idParentMovement +
      ", movement='" + movement + '\'' +
      ", mcc1='" + mcc1 + '\'' +
      ", mcc2='" + mcc2 + '\'' +
      ", presentation=" + presentation +
      ", mcc3='" + mcc3 + '\'' +
      ", nbQuotes=" + nbQuotes +
      ", nbAuthors=" + nbAuthors +
      ", nbAuthorsQuotes=" + nbAuthorsQuotes +
      ", nbBooks=" + nbBooks +
      ", nbBooksQuotes=" + nbBooksQuotes +
      ", checked=" + checked +
      ", nbTotalQuotes=" + nbTotalQuotes +
      ", nbTotalAuthors=" + nbTotalAuthors +
      ", nbTotalBooks=" + nbTotalBooks +
      ", nbSubcourants=" + nbSubcourants +
      ", nbAuthorsSubcourants=" + nbAuthorsSubcourants +
      ", nbBooksSubcourants=" + nbBooksSubcourants +
      ", urls=" + urls +
      ", pictures=" + pictures +
      ", movements=" + movements +
      ", books=" + books +
      ", authors=" + authors +
      '}';
  }
}
