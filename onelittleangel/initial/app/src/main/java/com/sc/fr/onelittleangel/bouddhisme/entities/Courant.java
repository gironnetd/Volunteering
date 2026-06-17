package com.sc.fr.onelittleangel.bouddhisme.entities;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Courant implements Serializable {
    private static final long serialVersionUID = 1;
    private List<Author> authorList;
    private String courant;
    private List<Courant> courantList;
    private Long idCourant;
    private Courant idParent;

    public Courant() {
    }

    public Courant(Long idCourant) {
        this.idCourant = idCourant;
    }

    public Courant(String courant) {
        this.courant = courant;
    }

    public Long getIdCourant() {
        return this.idCourant;
    }

    public void setIdCourant(Long idCourant) {
        this.idCourant = idCourant;
    }

    public String getCourant() {
        return this.courant;
    }

    public void setCourant(String courant) {
        this.courant = courant;
    }

    public List<Author> getAuthorList() {
        return this.authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public List<Courant> getCourantList() {
        return this.courantList;
    }

    public void setCourantList(List<Courant> courantList) {
        this.courantList = courantList;
    }

    public Courant getIdParent() {
        return this.idParent;
    }

    public void setIdParent(Courant idParent) {
        this.idParent = idParent;
    }

    public int hashCode() {
        int hash = 0 + (this.idCourant != null ? this.idCourant.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Courant)) {
            return false;
        }
        Courant other = (Courant) object;
        if (this.idCourant != null || other.idCourant == null) {
            return this.idCourant == null || this.idCourant.equals(other.idCourant);
        }
        return false;
    }

    public String toString() {
        return String.format("Courant[ %s, %s ]", this.idCourant, this.courant);
    }
}
