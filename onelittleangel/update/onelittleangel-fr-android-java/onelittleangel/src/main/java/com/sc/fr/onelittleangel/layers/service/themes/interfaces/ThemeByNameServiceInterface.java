package com.sc.fr.onelittleangel.layers.service.themes.interfaces;

import com.sc.fr.onelittleangel.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Theme;

import io.reactivex.Observable;

public interface ThemeByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Observable<Theme> loadThemeByNameAsync(String name);
}
