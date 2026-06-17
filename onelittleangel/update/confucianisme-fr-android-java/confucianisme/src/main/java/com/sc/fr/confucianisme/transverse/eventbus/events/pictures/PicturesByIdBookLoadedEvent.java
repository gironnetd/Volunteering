package com.sc.fr.confucianisme.transverse.eventbus.events.pictures;

import com.sc.fr.confucianisme.transverse.eventbus.models.PictureEventBus;

import java.util.List;

public class PicturesByIdBookLoadedEvent {
  private List<PictureEventBus> picturesByIdBook;
  private int idBook;

  public PicturesByIdBookLoadedEvent(List<PictureEventBus> picturesByIdBook, int idBook) {
    this.picturesByIdBook = picturesByIdBook;
    this.idBook = idBook;
  }

  public PicturesByIdBookLoadedEvent() {
  }

  public List<PictureEventBus> getPicturesByIdBook() {
    return picturesByIdBook;
  }

  public void setPicturesByIdBook(List<PictureEventBus> picturesByIdBook) {
    this.picturesByIdBook = picturesByIdBook;
  }

  public int getIdBook() {
    return idBook;
  }

  public void setIdBook(int idBook) {
    this.idBook = idBook;
  }
}
