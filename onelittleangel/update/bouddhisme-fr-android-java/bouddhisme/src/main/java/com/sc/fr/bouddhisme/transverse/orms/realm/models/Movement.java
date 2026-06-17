package com.sc.fr.bouddhisme.transverse.orms.realm.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Movement extends RealmObject {

  @PrimaryKey
  private long idMovement;
  private Movement parentMovement;
  private String movement;
  private String mcc1;
  private String mcc2;
  private Presentation presentation;
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

  private RealmList<Url> urls;
  private RealmList<Picture> pictures;
  private RealmList<Movement> movements;
  private RealmList<Book> books;
  private RealmList<Author> authors;

  public Movement() {
  }

  public long getIdMovement() {
    return idMovement;
  }

  public void setIdMovement(long idMovement) {
    this.idMovement = idMovement;
  }

  public Movement getParentMovement() {
    return parentMovement;
  }

  public void setParentMovement(Movement parentMovement) {
    this.parentMovement = parentMovement;
  }

  public String getMovement() {
    return movement;
  }

  public void setMovement(String movement) {
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

  public Presentation getPresentation() {
    return presentation;
  }

  public void setPresentation(Presentation presentation) {
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

  public void setNbAuthors(long nbAuthors) {
    this.nbAuthors = nbAuthors;
  }

  public long getNbAuthorsQuotes() {
    return nbAuthorsQuotes;
  }

  public void setNbAuthorsQuotes(long nbAuthorsQuotes) {
    this.nbAuthorsQuotes = nbAuthorsQuotes;
  }

  public long getNbBooks() {
    return nbBooks;
  }

  public void setNbBooks(long nbBooks) {
    this.nbBooks = nbBooks;
  }

  public long getNbBooksQuotes() {
    return nbBooksQuotes;
  }

  public void setNbBooksQuotes(long nbBooksQuotes) {
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

  public void setNbTotalAuthors(long nbTotalAuthors) {
    this.nbTotalAuthors = nbTotalAuthors;
  }

  public long getNbTotalBooks() {
    return nbTotalBooks;
  }

  public void setNbTotalBooks(long nbTotalBooks) {
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

  public void setNbAuthorsSubcourants(long nbAuthorsSubcourants) {
    this.nbAuthorsSubcourants = nbAuthorsSubcourants;
  }

  public long getNbBooksSubcourants() {
    return nbBooksSubcourants;
  }

  public void setNbBooksSubcourants(long nbBooksSubcourants) {
    this.nbBooksSubcourants = nbBooksSubcourants;
  }

  public RealmList<Url> getUrls() {
    return urls;
  }

  public void setUrls(RealmList<Url> urls) {
    this.urls = urls;
  }

  public RealmList<Picture> getPictures() {
    return pictures;
  }

  public void setPictures(RealmList<Picture> pictures) {
    this.pictures = pictures;
  }

  public RealmList<Movement> getMovements() {
    return movements;
  }

  public void setMovements(RealmList<Movement> movements) {
    this.movements = movements;
  }

  public RealmList<Book> getBooks() {
    return books;
  }

  public void setBooks(RealmList<Book> books) {
    this.books = books;
  }

  public RealmList<Author> getAuthors() {
    return authors;
  }

  public void setAuthors(RealmList<Author> authors) {
    this.authors = authors;
  }
}
