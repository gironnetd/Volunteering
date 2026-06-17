package com.sc.en.taoism.transverse.eventbus.events.themes;

import com.sc.en.taoism.layers.mvp.tablecontents.models.Theme;

import java.util.List;

public class ThemesWithThemesLoadedEvent {
  private List<Theme> themesWithThemes;

  public ThemesWithThemesLoadedEvent() {
  }

  public ThemesWithThemesLoadedEvent(List<Theme> themesWithThemes) {
    this.themesWithThemes = themesWithThemes;
  }

  public List<Theme> getThemesWithThemes() {
    return themesWithThemes;
  }

  public void setThemesWithThemes(List<Theme> themesWithThemes) {
    this.themesWithThemes = themesWithThemes;
  }
}
