package com.sc.en.islam.transverse.eventbus.events.pictures;

import com.sc.en.islam.transverse.eventbus.models.PictureEventBus;

import java.util.List;

public class PicturesByNameSmallLoadedEvent {
  private List<PictureEventBus> picturesByNameSmall;
  private String nameSmall;

  public PicturesByNameSmallLoadedEvent(List<PictureEventBus> picturesByNameSmall, String nameSmall) {
    this.picturesByNameSmall = picturesByNameSmall;
    this.nameSmall = nameSmall;
  }

  public List<PictureEventBus> getPicturesByNameSmall() {
    return picturesByNameSmall;
  }

  public void setPicturesByNameSmall(List<PictureEventBus> picturesByNameSmall) {
    this.picturesByNameSmall = picturesByNameSmall;
  }

  public String getNameSmall() {
    return nameSmall;
  }

  public void setNameSmall(String nameSmall) {
    this.nameSmall = nameSmall;
  }
}
