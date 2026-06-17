package com.sc.fr.philosophie.transverse.eventbus.events.themes;

import com.sc.fr.philosophie.layers.mvp.tablecontents.models.Theme;

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
