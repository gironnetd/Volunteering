package com.sc.en.taoism.layers.service.themes.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.taoism.transverse.orms.realm.models.Theme;

import io.reactivex.Observable;

public interface ThemeByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Observable<Theme> loadThemeByNameAsync(String name);
}
