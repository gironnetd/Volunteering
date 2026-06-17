package com.sc.en.hindouism.layers.service.themes.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.hindouism.transverse.orms.realm.models.Theme;

import io.reactivex.Observable;

public interface ThemeByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Observable<Theme> loadThemeByNameAsync(String name);
}
