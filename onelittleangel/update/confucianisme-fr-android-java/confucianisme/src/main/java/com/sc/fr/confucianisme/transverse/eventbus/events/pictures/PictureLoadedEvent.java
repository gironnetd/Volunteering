package com.sc.fr.confucianisme.transverse.eventbus.events.pictures;

import com.sc.fr.confucianisme.transverse.eventbus.models.PictureEventBus;

class PictureLoadedEvent {
  private PictureEventBus picture;

  public PictureLoadedEvent() {
  }

  public PictureLoadedEvent(PictureEventBus picture) {
    this.picture = picture;
  }

  public PictureEventBus getPicture() {
    return picture;
  }

  public void setPicture(PictureEventBus picture) {
    this.picture = picture;
  }
}
