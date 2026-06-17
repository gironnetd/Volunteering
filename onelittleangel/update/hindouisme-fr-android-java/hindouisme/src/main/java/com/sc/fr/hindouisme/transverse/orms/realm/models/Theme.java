package com.sc.fr.hindouisme.transverse.orms.realm.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

//@Parcel(implementations = { Theme.class },
//  value = Parcel.Serialization.BEAN,
//  analyze = { Theme.class })
@RealmClass
public class Theme extends RealmObject {

  @PrimaryKey
  private long idTheme;
  private Theme parentTheme;
  private String theme;
  private String presentation;
  private String sourcePresentation;
  private long nbQuotes;

  private RealmList<Theme> themes;
  private RealmList<Quote> quotes;
  private RealmList<Picture> pictures;
  private RealmList<Author> authors;
  private RealmList<Book> books;

  public Theme() {
  }

  public long getIdTheme() {
    return idTheme;
  }

  public void setIdTheme(long idTheme) {
    this.idTheme = idTheme;
  }

  public Theme getParentTheme() {
    return parentTheme;
  }

  public void setParentTheme(Theme parentTheme) {
    this.parentTheme = parentTheme;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public String getPresentation() {
    return presentation;
  }

  public void setPresentation(String presentation) {
    this.presentation = presentation;
  }

  public String getSourcePresentation() {
    return sourcePresentation;
  }

  public void setSourcePresentation(String sourcePresentation) {
    this.sourcePresentation = sourcePresentation;
  }

  public long getNbQuotes() {
    return nbQuotes;
  }

  public void setNbQuotes(long nbQuotes) {
    this.nbQuotes = nbQuotes;
  }

  public RealmList<Theme> getThemes() {
    return themes;
  }

  public void setThemes(RealmList<Theme> themes) {
    this.themes = themes;
  }

  public RealmList<Quote> getQuotes() {
    return quotes;
  }

  public void setQuotes(RealmList<Quote> quotes) {
    this.quotes = quotes;
  }

  public RealmList<Picture> getPictures() {
    return pictures;
  }

  public void setPictures(RealmList<Picture> pictures) {
    this.pictures = pictures;
  }

  public RealmList<Author> getAuthors() {
    return authors;
  }

  public void setAuthors(RealmList<Author> authors) {
    this.authors = authors;
  }

  public RealmList<Book> getBooks() {
    return books;
  }

  public void setBooks(RealmList<Book> books) {
    this.books = books;
  }
}
