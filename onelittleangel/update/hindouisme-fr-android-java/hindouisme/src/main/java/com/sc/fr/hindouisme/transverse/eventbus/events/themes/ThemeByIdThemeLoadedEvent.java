package com.sc.fr.hindouisme.transverse.eventbus.events.themes;

import com.sc.fr.hindouisme.transverse.eventbus.models.ThemeEventBus;

public class ThemeByIdThemeLoadedEvent {
  private ThemeEventBus themeByIdTheme;
  private int idTheme;

  public ThemeByIdThemeLoadedEvent(ThemeEventBus themeByIdTheme, int idTheme) {
    this.themeByIdTheme = themeByIdTheme;
    this.idTheme = idTheme;
  }

  public ThemeByIdThemeLoadedEvent() {
  }

  public ThemeEventBus getThemeByIdTheme() {
    return themeByIdTheme;
  }

  public void setThemeByIdTheme(ThemeEventBus themeByIdTheme) {
    this.themeByIdTheme = themeByIdTheme;
  }

  public int getIdTheme() {
    return idTheme;
  }

  public void setIdTheme(int idTheme) {
    this.idTheme = idTheme;
  }
}
