package com.sc.fr.bouddhisme.transverse.eventbus.models;

import com.sc.fr.bouddhisme.transverse.orms.realm.models.Picture;

public class PictureEventBus {

  private long idPicture;
  private String nameSmall;
  private String extension;
  private String comment;
  private long width;
  private long height;
  private long portrait;

  public PictureEventBus() {
  }

  public PictureEventBus(Picture pictureRealm){
    idPicture = pictureRealm.getIdPicture();
    nameSmall = pictureRealm.getNameSmall();
    extension = pictureRealm.getExtension();
    comment = pictureRealm.getComment();
    width = pictureRealm.getWidth();
    height = pictureRealm.getHeight();
    portrait = pictureRealm.getPortrait();
  }

  public long getIdPicture() {
    return idPicture;
  }

  public void setIdPicture(long idPicture) {
    this.idPicture = idPicture;
  }

  public String getNameSmall() {
    return nameSmall;
  }

  public void setNameSmall(String nameSmall) {
    this.nameSmall = nameSmall;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public long getWidth() {
    return width;
  }

  public void setWidth(long width) {
    this.width = width;
  }

  public long getHeight() {
    return height;
  }

  public void setHeight(long height) {
    this.height = height;
  }

  public long getPortrait() {
    return portrait;
  }

  public void setPortrait(long portrait) {
    this.portrait = portrait;
  }
}
