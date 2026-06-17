package com.sc.en.hindouism.transverse.eventbus.events.themes;

import com.sc.en.hindouism.transverse.eventbus.models.ThemeEventBus;

class ThemeLoadedEvent {
  private ThemeEventBus theme;

  public ThemeLoadedEvent() {
  }

  public ThemeLoadedEvent(ThemeEventBus theme) {
    this.theme = theme;
  }

  public ThemeEventBus getTheme() {
    return theme;
  }

  public void setTheme(ThemeEventBus theme) {
    this.theme = theme;
  }
}
