package com.sc.en.philosophy.transverse.eventbus.events.pictures;

import com.sc.en.philosophy.transverse.eventbus.models.PictureEventBus;

public class PictureByIdPictureLoadedEvent {
  private PictureEventBus pictureByIdPicture;
  private int idPicture;

  public PictureByIdPictureLoadedEvent(PictureEventBus pictureByIdPicture, int idPicture) {
    this.pictureByIdPicture = pictureByIdPicture;
    this.idPicture = idPicture;
  }

  public PictureByIdPictureLoadedEvent() {
  }

  public PictureEventBus getPictureByIdPicture() {
    return pictureByIdPicture;
  }

  public void setPictureByIdPicture(PictureEventBus pictureByIdPicture) {
    this.pictureByIdPicture = pictureByIdPicture;
  }

  public int getIdPicture() {
    return idPicture;
  }

  public void setIdPicture(int idPicture) {
    this.idPicture = idPicture;
  }
}
