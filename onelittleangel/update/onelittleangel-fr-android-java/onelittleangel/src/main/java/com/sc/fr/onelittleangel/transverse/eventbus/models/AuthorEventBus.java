package com.sc.fr.onelittleangel.transverse.eventbus.models;

import com.sc.fr.onelittleangel.transverse.orms.realm.models.Author;

import java.util.List;

public class AuthorEventBus {

  private long idAuthor;
  private CenturyEventBus century;
  private String name;
  private String surname;
  private String details;
  private String period;
  private long idMovement;
  private String bibliographie;
  private PresentationEventBus presentation;
  private List<UrlEventBus> urls;
  private List<PictureEventBus> pictures;
  private long mainPicture;
  private String mcc1;
  // private long nbQuotes;
  private List<BookEventBus> books;
  private List<QuoteEventBus> quotes;

  public AuthorEventBus() {
  }

  public AuthorEventBus(Author author){
    idAuthor = author.getIdAuthor();
  //  century = new CenturyEventBus(author.getCentury());
    name = author.getName();
    surname = author.getSurname();
    details = author.getDetails();
    period = author.getPeriod();
    idMovement = author.getMovement().getIdMovement();
    bibliographie = author.getBibliographie();
  //  presentation = new PresentationEventBus(author.getPresentation());

//    urls = new ArrayList<>();
//    for (Url ur : author.getUrls()) {
//      urls.add(new UrlEventBus(ur));
//    }
//
//    pictures = new ArrayList<>();
//    for (Picture pr : author.getPictures()) {
//      pictures.add(new PictureEventBus(pr));
//    }

    mainPicture = author.getMainPicture();
    mcc1 = author.getMcc1();

//    books = new ArrayList<>();
//    for (Book br : author.getBooks()) {
//      books.add(new BookEventBus(br));
//    }
//
//    quotes = new ArrayList<>();
//    for(Quote qr : author.getQuotes()){
//      quotes.add(new QuoteEventBus(qr));
//    }
  }

  public long getIdAuthor() {
    return idAuthor;
  }

  public void setIdAuthor(long idAuthor) {
    this.idAuthor = idAuthor;
  }

  public CenturyEventBus getCentury() {
    return century;
  }

  public void setCentury(CenturyEventBus century) {
    this.century = century;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public long getIdMovement() {
    return idMovement;
  }

  public void setIdMovement(long idMovement) {
    this.idMovement = idMovement;
  }

  public String getBibliographie() {
    return bibliographie;
  }

  public void setBibliographie(String bibliographie) {
    this.bibliographie = bibliographie;
  }

  public PresentationEventBus getPresentation() {
    return presentation;
  }

  public void setPresentation(PresentationEventBus presentation) {
    this.presentation = presentation;
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

  public long getMainPicture() {
    return mainPicture;
  }

  public void setMainPicture(long mainPicture) {
    this.mainPicture = mainPicture;
  }

  public String getMcc1() {
    return mcc1;
  }

  public void setMcc1(String mcc1) {
    this.mcc1 = mcc1;
  }

  public List<BookEventBus> getBooks() {
    return books;
  }

  public void setBooks(List<BookEventBus> books) {
    this.books = books;
  }

  public List<QuoteEventBus> getQuotes() {
    return quotes;
  }

  public void setQuotes(List<QuoteEventBus> quotes) {
    this.quotes = quotes;
  }
}
