package com.sc.fr.onelittleangel.transverse.eventbus.events.pictures;

import com.sc.fr.onelittleangel.transverse.eventbus.models.PictureEventBus;

import java.util.List;

public class PicturesByIdAuthorLoadedEvent {
  private List<PictureEventBus> picturesByIdAuthor;
  private int idAuthor;

  public PicturesByIdAuthorLoadedEvent(List<PictureEventBus> picturesByIdAuthor, int idAuthor) {
    this.picturesByIdAuthor = picturesByIdAuthor;
    this.idAuthor = idAuthor;
  }

  public PicturesByIdAuthorLoadedEvent() {
  }

  public List<PictureEventBus> getPicturesByIdAuthor() {
    return picturesByIdAuthor;
  }

  public void setPicturesByIdAuthor(List<PictureEventBus> picturesByIdAuthor) {
    this.picturesByIdAuthor = picturesByIdAuthor;
  }

  public int getIdAuthor() {
    return idAuthor;
  }

  public void setIdAuthor(int idAuthor) {
    this.idAuthor = idAuthor;
  }
}
