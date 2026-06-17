package com.sc.fr.hindouisme.layers.service.themes.interfaces;

import com.sc.fr.hindouisme.transverse.orms.realm.models.Theme;
import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

import io.reactivex.Observable;

public interface ThemeByNameServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Observable<Theme> loadThemeByNameAsync(String name);
}
