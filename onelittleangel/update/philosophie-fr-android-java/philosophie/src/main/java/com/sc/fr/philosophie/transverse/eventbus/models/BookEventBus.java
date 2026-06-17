package com.sc.fr.philosophie.transverse.eventbus.models;

import com.sc.fr.philosophie.transverse.orms.realm.models.Book;

import java.util.List;

public class BookEventBus {

  private long idBook;
  private CenturyEventBus century;
  private String name;
  private String details;
  private String period;
  private long idMovement;
  private PresentationEventBus presentation;
  private List<UrlEventBus> urls;
  private List<PictureEventBus> pictures;
  private String mcc1;
  // private long nbQuotes;
  private List<QuoteEventBus> quotes;

  public BookEventBus() {
  }

  public BookEventBus(Book book){
    idBook = book.getIdBook();
  //  century = new CenturyEventBus(book.getCentury());
    name = book.getName();
    details = book.getDetails();
    period = book.getPeriod();
    idMovement = book.getMovement().getIdMovement();
  //  presentation = new PresentationEventBus(book.getPresentation());

//    urls = new ArrayList<>();
//    for (Url ur : book.getUrls()) {
//      urls.add(new UrlEventBus(ur));
//    }
//
//    pictures = new ArrayList<>();
//    for (Picture pr : book.getPictures()) {
//      pictures.add(new PictureEventBus(pr));
//    }

    mcc1 = book.getMcc1();

//    quotes = new ArrayList<>();
//    for(Quote qr : book.getQuotes()){
//      quotes.add(new QuoteEventBus(qr));
//    }
  }

  public long getIdBook() {
    return idBook;
  }

  public void setIdBook(long idBook) {
    this.idBook = idBook;
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

  public String getMcc1() {
    return mcc1;
  }

  public void setMcc1(String mcc1) {
    this.mcc1 = mcc1;
  }

  public List<QuoteEventBus> getQuotes() {
    return quotes;
  }

  public void setQuotes(List<QuoteEventBus> quotes) {
    this.quotes = quotes;
  }
}
