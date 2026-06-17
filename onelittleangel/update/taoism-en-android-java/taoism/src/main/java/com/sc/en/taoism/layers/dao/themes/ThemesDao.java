package com.sc.en.taoism.layers.dao.themes;

import com.sc.en.taoism.layers.dao.DaoManager;
import com.sc.en.taoism.transverse.orms.realm.models.Theme;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class ThemesDao implements ThemesDaoInterface {


  private static final String TAG = "ThemesDao";
  private Realm realm;

  /***********************************************************
   * Constructor
   **********************************************************/
  public ThemesDao(DaoManager daoManager){
    //to ensure only DaoManager can instanciate this element
  }

  /***********************************************************
   *  Business Methods
   **********************************************************/

  @Override
  public Theme findThemeByIdTheme(int idTheme) {
    realm = Realm.getDefaultInstance();
    return realm.where(Theme.class).equalTo("idTheme",idTheme).findFirst();
  }

  @Override
  public Theme findThemeByName(String name) {
    realm = Realm.getDefaultInstance();
    return realm.where(Theme.class).equalTo("theme",name).findFirst();
  }

  @Override
  public List<Theme> findThemesByIdParent(int idParent) {
    realm = Realm.getDefaultInstance();
    if(idParent == 0)
      return realm.where(Theme.class).isNull("parentTheme").findAllSorted("theme");
    else
      return realm.where(Theme.class).equalTo("parentTheme.idTheme",idParent).findAllSorted("theme");
  }

  @Override
  public List<Theme> findAllThemes() {
    realm = Realm.getDefaultInstance();
    return realm.where(Theme.class).findAllSorted("theme");
  }

  /**
   * @return
   */
  @Override
  public List<Theme> findThemesWithThemes() {
    realm = Realm.getDefaultInstance();
    List<Theme> themes =  realm.where(Theme.class).isNull("parentTheme").findAllSorted("theme");

    List<Theme> themesWithOnlyThemes = new ArrayList<>();

    for(Theme theme : themes){
      Theme t = new Theme();
      t.setIdTheme(theme.getIdTheme());
      t.setTheme(theme.getTheme());
      t.setNbQuotes(theme.getNbQuotes());

      if(theme.getThemes() != null){
        t.setThemes(new RealmList<>());

        for(Theme theme1 : theme.getThemes()){
          Theme t1 = new Theme();
          t1.setIdTheme(theme1.getIdTheme());
          t1.setTheme(theme1.getTheme());
          t1.setNbQuotes(theme1.getNbQuotes());

          t.getThemes().add(t1);
        }
      }
      themesWithOnlyThemes.add(t);
    }

    return themesWithOnlyThemes ;
  }
}
