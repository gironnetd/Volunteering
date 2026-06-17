package com.sc.fr.islam.transverse.eventbus.models;

import com.sc.fr.islam.transverse.orms.realm.models.Presentation;

public class PresentationEventBus {

  private long idPresentation;
  private String presentation;
  private String presentationTitle1;
  private String presentation1;
  private String presentationTitle2;
  private String presentation2;
  private String presentationTitle3;
  private String presentation3;
  private String presentationTitle4;
  private String presentation4;
  private String sourcePresentation;

  public PresentationEventBus() {
  }
  
  public PresentationEventBus(Presentation presentationRealm){
      idPresentation = presentationRealm.getIdPresentation();
      presentation = presentationRealm.getPresentation();
      presentationTitle1 = presentationRealm.getPresentationTitle1();
      presentation1 = presentationRealm.getPresentation1();
      presentationTitle2 = presentationRealm.getPresentationTitle2();
      presentation2 = presentationRealm.getPresentation2();
      presentationTitle3 = presentationRealm.getPresentationTitle3();
      presentation3 = presentationRealm.getPresentation3();
      presentationTitle4 = presentationRealm.getPresentationTitle4();
      presentation4 = presentationRealm.getPresentation4();
      sourcePresentation = presentationRealm.getSourcePresentation();
  }

  public long getIdPresentation() {
    return idPresentation;
  }

  public void setIdPresentation(long idPresentation) {
    this.idPresentation = idPresentation;
  }

  public String getPresentation() {
    return presentation;
  }

  public void setPresentation(String presentation) {
    this.presentation = presentation;
  }

  public String getPresentationTitle1() {
    return presentationTitle1;
  }

  public void setPresentationTitle1(String presentationTitle1) {
    this.presentationTitle1 = presentationTitle1;
  }

  public String getPresentation1() {
    return presentation1;
  }

  public void setPresentation1(String presentation1) {
    this.presentation1 = presentation1;
  }

  public String getPresentationTitle2() {
    return presentationTitle2;
  }

  public void setPresentationTitle2(String presentationTitle2) {
    this.presentationTitle2 = presentationTitle2;
  }

  public String getPresentation2() {
    return presentation2;
  }

  public void setPresentation2(String presentation2) {
    this.presentation2 = presentation2;
  }

  public String getPresentationTitle3() {
    return presentationTitle3;
  }

  public void setPresentationTitle3(String presentationTitle3) {
    this.presentationTitle3 = presentationTitle3;
  }

  public String getPresentation3() {
    return presentation3;
  }

  public void setPresentation3(String presentation3) {
    this.presentation3 = presentation3;
  }

  public String getPresentationTitle4() {
    return presentationTitle4;
  }

  public void setPresentationTitle4(String presentationTitle4) {
    this.presentationTitle4 = presentationTitle4;
  }

  public String getPresentation4() {
    return presentation4;
  }

  public void setPresentation4(String presentation4) {
    this.presentation4 = presentation4;
  }

  public String getSourcePresentation() {
    return sourcePresentation;
  }

  public void setSourcePresentation(String sourcePresentation) {
    this.sourcePresentation = sourcePresentation;
  }
}
