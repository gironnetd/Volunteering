package com.sc.en.quotes.transverse.orms.realm.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Century extends RealmObject {

  @PrimaryKey
  private long idCentury;
  private String century;
  private String presentation;


  public Century() {
  }

  public Century(int idCentury, String century, String presentation) {
    this.idCentury = idCentury;
    this.century = century;
    this.presentation = presentation;
  //  this.baseEntity = new BaseEntity();
  }

  public long getIdCentury() {
    return idCentury;
  }

  public void setIdCentury(long idCentury) {
    this.idCentury = idCentury;
  }

  public String getCentury() {
    return century;
  }

  public void setCentury(String century) {
    this.century = century;
  }

 // @Override
  public String getPresentation() {
    return presentation;
  }

//  public BaseEntity getBaseEntity() {
//    return baseEntity;
//  }
//
//  public void setBaseEntity(BaseEntity baseEntity) {
//    this.baseEntity = baseEntity;
//  }

  public void setPresentation(String presentation) {
    this.presentation = presentation;
  }
}
