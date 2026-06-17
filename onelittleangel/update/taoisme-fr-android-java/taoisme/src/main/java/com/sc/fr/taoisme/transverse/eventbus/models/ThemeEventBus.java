package com.sc.fr.taoisme.transverse.eventbus.models;

import com.sc.fr.taoisme.transverse.orms.realm.models.Author;
import com.sc.fr.taoisme.transverse.orms.realm.models.Book;
import com.sc.fr.taoisme.transverse.orms.realm.models.Picture;
import com.sc.fr.taoisme.transverse.orms.realm.models.Quote;
import com.sc.fr.taoisme.transverse.orms.realm.models.Theme;

import java.util.ArrayList;
import java.util.List;

public class ThemeEventBus {

  private long idTheme;
  private long idParentTheme;
  private String theme;
  private String presentation;
  private String sourcePresentation;
  private long nbQuotes;

  private List<ThemeEventBus> themes;
  private List<QuoteEventBus> quotes;
  private List<PictureEventBus> pictures;
  private List<AuthorEventBus> authors;
  private List<BookEventBus> books;

  public ThemeEventBus() {
  }

  public ThemeEventBus(Theme theme){
    this.idTheme = theme.getIdTheme();
    if(theme.getParentTheme() != null) {
      this.idParentTheme = theme.getParentTheme().getIdTheme();
    }
    this.theme = theme.getTheme();
    this.presentation = theme.getPresentation();
    this.sourcePresentation = theme.getSourcePresentation();
    this.nbQuotes = theme.getNbQuotes();

    if(theme.getThemes() != null) {
      themes = new ArrayList<>();
      for (Theme tr : theme.getThemes()) {
        themes.add(new ThemeEventBus(tr));
      }
    }

    if(theme.getQuotes() != null) {
      quotes = new ArrayList<>();
      for (Quote qr : theme.getQuotes()) {
        quotes.add(new QuoteEventBus(qr));
      }
    }

    if(theme.getPictures() != null) {
      pictures = new ArrayList<>();
      for (Picture pr : theme.getPictures()) {
        pictures.add(new PictureEventBus(pr));
      }
    }

    if(theme.getAuthors() != null) {
      authors = new ArrayList<>();
      for (Author ar : theme.getAuthors()) {
        authors.add(new AuthorEventBus(ar));
      }
    }

    if(theme.getBooks() != null) {
      books = new ArrayList<>();
      for (Book br : theme.getBooks()) {
        books.add(new BookEventBus(br));
      }
    }
  }

  public long getIdTheme() {
    return idTheme;
  }

  public void setIdTheme(long idTheme) {
    this.idTheme = idTheme;
  }

  public long getIdParentTheme() {
    return idParentTheme;
  }

  public void setIdParentTheme(long idParentTheme) {
    this.idParentTheme = idParentTheme;
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

  public List<ThemeEventBus> getThemes() {
    return themes;
  }

  public void setThemes(List<ThemeEventBus> themes) {
    this.themes = themes;
  }

  public List<QuoteEventBus> getQuotes() {
    return quotes;
  }

  public void setQuotes(List<QuoteEventBus> quotes) {
    this.quotes = quotes;
  }

  public List<PictureEventBus> getPictures() {
    return pictures;
  }

  public void setPictures(List<PictureEventBus> pictures) {
    this.pictures = pictures;
  }

  public List<AuthorEventBus> getAuthors() {
    return authors;
  }

  public void setAuthors(List<AuthorEventBus> authors) {
    this.authors = authors;
  }

  public List<BookEventBus> getBooks() {
    return books;
  }

  public void setBooks(List<BookEventBus> books) {
    this.books = books;
  }
}
