package com.sc.fr.onelittleangel.bouddhisme.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Quote implements Serializable {
    private static final long serialVersionUID = 1;
    private String book;
    private Date dteCreated;
    private Author idAuthor;
    private Long idQuote;
    private List<QuoCategory> quoCategoryList;
    private String quote;

    public Quote() {
    }

    public Quote(Long idQuote) {
        this.idQuote = idQuote;
    }

    public Long getIdQuote() {
        return this.idQuote;
    }

    public void setIdQuote(Long idQuote) {
        this.idQuote = idQuote;
    }

    public String getQuote() {
        return this.quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public Date getDteCreated() {
        return this.dteCreated;
    }

    public void setDteCreated(Date dteCreated) {
        this.dteCreated = dteCreated;
    }

    public String getBook() {
        return this.book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public List<QuoCategory> getQuoCategoryList() {
        return this.quoCategoryList;
    }

    public void setQuoCategoryList(List<QuoCategory> quoCategoryList) {
        this.quoCategoryList = quoCategoryList;
    }

    public Author getIdAuthor() {
        return this.idAuthor;
    }

    public void setIdAuthor(Author idAuthor) {
        this.idAuthor = idAuthor;
    }

    public int hashCode() {
        int hash = 0 + (this.idQuote != null ? this.idQuote.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Quote)) {
            return false;
        }
        Quote other = (Quote) object;
        if (this.idQuote != null || other.idQuote == null) {
            return this.idQuote == null || this.idQuote.equals(other.idQuote);
        }
        return false;
    }

    public String toString() {
        return String.format("Quote [%s, %s, %s ]", this.idQuote, this.idAuthor, this.quote);
    }
}
