package com.sc.en.quotes.transverse.eventbus.events.pictures;

import com.sc.en.quotes.transverse.eventbus.models.PictureEventBus;

import java.util.List;

public class PicturesByIdThemeLoadedEvent {
  private List<PictureEventBus> picturesByIdTheme;
  private int idTheme;

  public PicturesByIdThemeLoadedEvent(List<PictureEventBus> picturesByIdTheme, int idTheme) {
    this.picturesByIdTheme = picturesByIdTheme;
    this.idTheme = idTheme;
  }

  public PicturesByIdThemeLoadedEvent() {
  }

  public List<PictureEventBus> getPicturesByIdTheme() {
    return picturesByIdTheme;
  }

  public void setPicturesByIdTheme(List<PictureEventBus> picturesByIdTheme) {
    this.picturesByIdTheme = picturesByIdTheme;
  }

  public int getIdTheme() {
    return idTheme;
  }

  public void setIdTheme(int idTheme) {
    this.idTheme = idTheme;
  }
}
