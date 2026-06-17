package com.sc.en.onelittleangel.transverse.orms.realm.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;


@RealmClass
public class Quote extends RealmObject {

  @PrimaryKey
  private long idQuote;
  private Author author;
  private Book book;
  private String quote;
  private String source;
  private String reference;
  private boolean isFavorites;
  private String remarque;
  private String comment;
  private String commentName;

  public Quote() {
  }

  public long getIdQuote() {
    return idQuote;
  }

  public void setIdQuote(long idQuote) {
    this.idQuote = idQuote;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public String getQuote() {
    return quote;
  }

  public void setQuote(String quote) {
    this.quote = quote;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public boolean isFavorites() {
    return isFavorites;
  }

  public void setFavorites(boolean favorites) {
    isFavorites = favorites;
  }

  public String getRemarque() {
    return remarque;
  }

  public void setRemarque(String remarque) {
    this.remarque = remarque;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getCommentName() {
    return commentName;
  }

  public void setCommentName(String commentName) {
    this.commentName = commentName;
  }
}
