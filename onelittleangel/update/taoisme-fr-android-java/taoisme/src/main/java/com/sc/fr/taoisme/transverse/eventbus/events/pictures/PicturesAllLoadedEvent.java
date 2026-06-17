package com.sc.fr.taoisme.transverse.eventbus.events.pictures;

import com.sc.fr.taoisme.transverse.eventbus.models.PictureEventBus;

import java.util.List;

public class PicturesAllLoadedEvent {

  private List<PictureEventBus> picturesAll;

  public PicturesAllLoadedEvent() {
  }

  public PicturesAllLoadedEvent(List<PictureEventBus> picturesAll) {
    this.picturesAll = picturesAll;
  }

  public List<PictureEventBus> getPictures() {
    return picturesAll;
  }

  public void setPictures(List<PictureEventBus> picturesAll) {
    this.picturesAll = picturesAll;
  }
}
