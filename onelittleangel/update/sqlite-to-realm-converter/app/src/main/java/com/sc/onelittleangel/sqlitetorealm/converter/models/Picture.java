package com.sc.onelittleangel.sqlitetorealm.converter.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Picture extends RealmObject {

  @PrimaryKey
  private long idPicture;
  private String nameSmall;
  private String extension;
  private String comment;
  private long width;
  private long height;
  private long portrait;

  public Picture() {
  }

  public Picture(int idPicture, String nameSmall, String extension, String comment,
                 int width, int height, int portrait) {
    this.idPicture = idPicture;
    this.nameSmall = nameSmall;
    this.extension = extension;
    this.comment = comment;
    this.width = width;
    this.height = height;
    this.portrait = portrait;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Picture)) return false;

    Picture picture = (Picture) o;

    if (idPicture != picture.idPicture) return false;
    if (width != picture.width) return false;
    return height == picture.height && portrait == picture.portrait && (nameSmall != null ? nameSmall.equals(picture.nameSmall) : picture.nameSmall == null && (extension != null ? extension.equals(picture.extension) : picture.extension == null && (comment != null ? comment.equals(picture.comment) : picture.comment == null)));

  }

  @Override
  public int hashCode() {
    int result = (int) (idPicture ^ (idPicture >>> 32));
    result = 31 * result + (nameSmall != null ? nameSmall.hashCode() : 0);
    result = 31 * result + (extension != null ? extension.hashCode() : 0);
    result = 31 * result + (comment != null ? comment.hashCode() : 0);
    result = 31 * result + (int) (width ^ (width >>> 32));
    result = 31 * result + (int) (height ^ (height >>> 32));
    result = 31 * result + (int) (portrait ^ (portrait >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "Picture{" +
      "idPicture=" + idPicture +
      ", nameSmall='" + nameSmall + '\'' +
      ", extension='" + extension + '\'' +
      ", comment='" + comment + '\'' +
      ", width=" + width +
      ", height=" + height +
      ", portrait=" + portrait +
      '}';
  }
}
