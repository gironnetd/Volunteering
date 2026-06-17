package com.sc.en.onelittleangel.layers.service.themes.interfaces;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;
import com.sc.en.onelittleangel.transverse.orms.realm.models.Theme;

import io.reactivex.Observable;

public interface ThemeByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Observable<Theme> loadThemeByNameAsync(String name);
}
