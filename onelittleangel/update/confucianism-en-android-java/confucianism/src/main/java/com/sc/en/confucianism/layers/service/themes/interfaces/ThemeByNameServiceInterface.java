package com.sc.en.confucianism.layers.service.themes.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.confucianism.transverse.orms.realm.models.Theme;

import io.reactivex.Observable;

public interface ThemeByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Observable<Theme> loadThemeByNameAsync(String name);
}
