package com.sc.fr.christianisme.layers.dao.themes;

import com.sc.fr.christianisme.transverse.orms.realm.models.Theme;

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
