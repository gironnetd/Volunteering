package com.sc.fr.onelittleangel.transverse.eventbus.models;

import com.sc.fr.onelittleangel.transverse.orms.realm.models.Century;

public class CenturyEventBus {

  private long idCentury;
  private String century;
  private String presentation;

  public CenturyEventBus() {
  }

  public CenturyEventBus(Century centuryRealm){
    idCentury = centuryRealm.getIdCentury();
    century = centuryRealm.getCentury();
    presentation = centuryRealm.getPresentation();
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

  public String getPresentation() {
    return presentation;
  }

  public void setPresentation(String presentation) {
    this.presentation = presentation;
  }
}
