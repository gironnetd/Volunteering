package com.sc.fr.bouddhisme.layers.service.themes.interfaces;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Theme;

import io.reactivex.Observable;

public interface ThemeByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Observable<Theme> loadThemeByNameAsync(String name);
}
