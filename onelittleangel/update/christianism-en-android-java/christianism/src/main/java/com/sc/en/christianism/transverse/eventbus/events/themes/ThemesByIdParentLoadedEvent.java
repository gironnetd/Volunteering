package com.sc.en.christianism.transverse.eventbus.events.themes;

import com.sc.en.christianism.transverse.eventbus.models.ThemeEventBus;

import java.util.List;

public class ThemesByIdParentLoadedEvent {
  private List<ThemeEventBus> themesByIdParent;
  private int idParent;

  public ThemesByIdParentLoadedEvent(List<ThemeEventBus> themesByIdParent, int idParent) {
    this.themesByIdParent = themesByIdParent;
    this.idParent = idParent;
  }

  public ThemesByIdParentLoadedEvent() {
  }

  public List<ThemeEventBus> getThemesByIdParent() {
    return themesByIdParent;
  }

  public void setThemesByIdParent(List<ThemeEventBus> themesByIdParent) {
    this.themesByIdParent = themesByIdParent;
  }

  public int getIdParent() {
    return idParent;
  }

  public void setIdParent(int idParent) {
    this.idParent = idParent;
  }
}
