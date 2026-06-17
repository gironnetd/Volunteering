package com.sc.fr.philosophie.injector.dao;

import com.sc.fr.philosophie.layers.dao.themes.ThemesDaoInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Theme;

import java.util.List;

/**
 * Created by damien on 28/12/2016 for OnelittleAngel Android project.
 */

public class ThemesDaoMocked implements ThemesDaoInterface {
  @Override
  public Theme findThemeByIdTheme(int idTheme) {
    return null;
  }

  @Override
  public Theme findThemeByName(String name) {
    return null;
  }

  @Override
  public List<Theme> findThemesByIdParent(int idParent) {
    return null;
  }

  @Override
  public List<Theme> findAllThemes() {
    return null;
  }

  /**
   * @return
   */
  @Override
  public List<Theme> findThemesWithThemes() {
    return null;
  }
}
