package com.sc.en.confucianism.transverse.eventbus.events.themes;

import com.sc.en.confucianism.transverse.eventbus.models.ThemeEventBus;

import java.util.List;

public class ThemesAllLoadedEvent {

  private List<ThemeEventBus> themesAll;

  public ThemesAllLoadedEvent() {
  }

  public ThemesAllLoadedEvent(List<ThemeEventBus> themesAll) {
    this.themesAll = themesAll;
  }

  public List<ThemeEventBus> getThemes() {
    return themesAll;
  }

  public void setThemes(List<ThemeEventBus> themesAll) {
    this.themesAll = themesAll;
  }
}
