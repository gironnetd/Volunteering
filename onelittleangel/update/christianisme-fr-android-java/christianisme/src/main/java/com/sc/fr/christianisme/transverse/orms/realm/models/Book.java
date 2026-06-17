package com.sc.fr.christianisme.transverse.orms.realm.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Book extends RealmObject {

  @PrimaryKey
  private long idBook;
  private Century century;
  private String name;
  private String details;
  private String period;
  private Movement movement;
  private Presentation presentation;
  private RealmList<Url> urls;
  private RealmList<Picture> pictures;
  private String mcc1;
 // private long nbQuotes;
  private RealmList<Quote> quotes;

  public Book() {
  }

  public long getIdBook() {
    return idBook;
  }

  public void setIdBook(long idBook) {
    this.idBook = idBook;
  }

  public Century getCentury() {
    return century;
  }

  public void setCentury(Century century) {
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

  public Movement getMovement() {
    return movement;
  }

  public void setMovement(Movement movement) {
    this.movement = movement;
  }

  public Presentation getPresentation() {
    return presentation;
  }

  public void setPresentation(Presentation presentation) {
    this.presentation = presentation;
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

  public String getMcc1() {
    return mcc1;
  }

  public void setMcc1(String mcc1) {
    this.mcc1 = mcc1;
  }

//  public long getNbQuotes() {
//    return nbQuotes;
//  }
//
//  public void setNbQuotes(long nbQuotes) {
//    this.nbQuotes = nbQuotes;
//  }

  public RealmList<Quote> getQuotes() {
    return quotes;
  }

  public void setQuotes(RealmList<Quote> quotes) {
    this.quotes = quotes;
  }
}
