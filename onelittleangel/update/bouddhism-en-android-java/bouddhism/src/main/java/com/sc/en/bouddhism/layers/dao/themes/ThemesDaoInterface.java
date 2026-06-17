package com.sc.en.bouddhism.layers.dao.themes;

import com.sc.en.bouddhism.transverse.orms.realm.models.Theme;

import java.util.List;

public interface ThemesDaoInterface {

  /**
   *
   * @param idTheme
   * @return
   */
  Theme findThemeByIdTheme(int idTheme);

  /**
   *
   * @param name
   * @return
   */
  Theme findThemeByName(String name);

  /**
   *
   * @param idParent
   * @return
   */
  List<Theme> findThemesByIdParent(int idParent);

  /**
   *
   * @return
   */
  List<Theme> findAllThemes();

  /**
   *
   * @return
   */
  List<Theme> findThemesWithThemes();
}
