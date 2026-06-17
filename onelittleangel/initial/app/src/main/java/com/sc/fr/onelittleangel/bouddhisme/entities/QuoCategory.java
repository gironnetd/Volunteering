package com.sc.fr.onelittleangel.bouddhisme.entities;

import java.io.Serializable;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class QuoCategory implements Serializable {
    private static final long serialVersionUID = 1;
    private Date dteCreated;
    private Long idAffiliation;
    private Category idCategory;
    private Quote idQuote;

    public QuoCategory() {
    }

    public QuoCategory(Long idAffiliation) {
        this.idAffiliation = idAffiliation;
    }

    public Long getIdAffiliation() {
        return this.idAffiliation;
    }

    public void setIdAffiliation(Long idAffiliation) {
        this.idAffiliation = idAffiliation;
    }

    public Date getDteCreated() {
        return this.dteCreated;
    }

    public void setDteCreated(Date dteCreated) {
        this.dteCreated = dteCreated;
    }

    public Category getIdCategory() {
        return this.idCategory;
    }

    public void setIdCategory(Category idCategory) {
        this.idCategory = idCategory;
    }

    public Quote getIdQuote() {
        return this.idQuote;
    }

    public void setIdQuote(Quote idQuote) {
        this.idQuote = idQuote;
    }

    public int hashCode() {
        int hash = 0 + (this.idAffiliation != null ? this.idAffiliation.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object object) {
        if (!(object instanceof QuoCategory)) {
            return false;
        }
        QuoCategory other = (QuoCategory) object;
        if (this.idAffiliation != null || other.idAffiliation == null) {
            return this.idAffiliation == null || this.idAffiliation.equals(other.idAffiliation);
        }
        return false;
    }

    public String toString() {
        return String.format("QuoCategory[ %s, %s, %s ]", this.idAffiliation, this.idCategory, this.idQuote);
    }
}
