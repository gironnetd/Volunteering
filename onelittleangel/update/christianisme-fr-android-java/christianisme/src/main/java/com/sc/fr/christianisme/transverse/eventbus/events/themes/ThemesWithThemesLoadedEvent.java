package com.sc.fr.christianisme.transverse.eventbus.events.themes;

import com.sc.fr.christianisme.layers.mvp.tablecontents.models.Theme;

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
