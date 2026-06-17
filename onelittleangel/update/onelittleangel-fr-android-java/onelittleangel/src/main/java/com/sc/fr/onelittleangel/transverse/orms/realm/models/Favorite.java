package com.sc.fr.onelittleangel.transverse.orms.realm.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Favorite extends RealmObject {

  @PrimaryKey
  private long idFavorite;
  private long idQuote;

  public Favorite() {
  }

  public long getIdFavorite() {
    return idFavorite;
  }

  public void setIdFavorite(long idFavorite) {
    this.idFavorite = idFavorite;
  }

  public long getIdQuote() {
    return idQuote;
  }

  public void setIdQuote(long idQuote) {
    this.idQuote = idQuote;
  }
}
