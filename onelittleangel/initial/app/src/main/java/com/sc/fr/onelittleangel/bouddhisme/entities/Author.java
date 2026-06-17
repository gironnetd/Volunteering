package com.sc.fr.onelittleangel.bouddhisme.entities;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Author implements Serializable {
    private static final long serialVersionUID = 1;
    private Boolean book;
    private String details;
    private Long idAuthor;
    private Courant idCourant;
    private String name;
    private int nbAuthors;
    private int nbQuotes;
    private List<Quote> quoteList;
    private String surname;

    public int getNbQuotes() {
        return this.nbQuotes;
    }

    public void setNbQuotes(int nbQuotes) {
        this.nbQuotes = nbQuotes;
    }

    public int getNbAuthors() {
        return this.nbAuthors;
    }

    public void setNbAuthors(int nbAuthors) {
        this.nbAuthors = nbAuthors;
    }

    public Author() {
    }

    public Author(Long idAuthor) {
        this.idAuthor = idAuthor;
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(String name, int nbQuotes) {
        this.name = name;
        this.nbQuotes = nbQuotes;
    }

    public Long getIdAuthor() {
        return this.idAuthor;
    }

    public void setIdAuthor(Long idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean getBook() {
        return this.book;
    }

    public void setBook(Boolean book) {
        this.book = book;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Courant getIdCourant() {
        return this.idCourant;
    }

    public void setIdCourant(Courant idCourant) {
        this.idCourant = idCourant;
    }

    public List<Quote> getQuoteList() {
        return this.quoteList;
    }

    public void setQuoteList(List<Quote> quoteList) {
        this.quoteList = quoteList;
    }

    public int hashCode() {
        int hash = 0 + (this.idAuthor != null ? this.idAuthor.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Author)) {
            return false;
        }
        Author other = (Author) object;
        if (this.idAuthor != null || other.idAuthor == null) {
            return this.idAuthor == null || this.idAuthor.equals(other.idAuthor);
        }
        return false;
    }

    public String toString() {
        return String.format("Author[ %s, %s ,%s]", this.idAuthor, this.name, this.idCourant);
    }
}
