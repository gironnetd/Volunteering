package com.sc.fr.christianisme.transverse.eventbus.models;

import com.sc.fr.christianisme.transverse.orms.realm.models.Quote;

public class QuoteEventBus {

  private long idQuote;
  private long idAuthor;
  private long idBook;
  private String quote;
  private String remarque;
  private String comment;
  private String commentName;

  public QuoteEventBus() {
  }

  public QuoteEventBus(Quote quote){
    idQuote = quote.getIdQuote();
    if(quote.getAuthor() != null) {
      idAuthor = quote.getAuthor().getIdAuthor();
    }
    if(quote.getBook() != null) {
      idBook = quote.getBook().getIdBook();
    }
    this.quote = quote.getQuote();
    remarque = quote.getRemarque();
    comment = quote.getComment();
    commentName = quote.getCommentName();
  }

  public long getIdQuote() {
    return idQuote;
  }

  public void setIdQuote(long idQuote) {
    this.idQuote = idQuote;
  }

  public long getIdAuthor() {
    return idAuthor;
  }

  public void setIdAuthor(long idAuthor) {
    this.idAuthor = idAuthor;
  }

  public long getIdBook() {
    return idBook;
  }

  public void setIdBook(long idBook) {
    this.idBook = idBook;
  }

  public String getQuote() {
    return quote;
  }

  public void setQuote(String quote) {
    this.quote = quote;
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
