package com.sc.en.christianism.layers.service.themes.interfaces;

import com.sc.en.christianism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.christianism.transverse.orms.realm.models.Theme;

import io.reactivex.Observable;

public interface ThemeByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Observable<Theme> loadThemeByNameAsync(String name);
}
