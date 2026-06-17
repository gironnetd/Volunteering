package com.sc.en.bouddhism.layers.service.themes.services;

import android.util.Log;

import com.sc.en.bouddhism.OnelittleAngelApplication;
import com.sc.en.bouddhism.layers.dao.themes.ThemesDaoInterface;
import com.sc.en.bouddhism.layers.mvp.tablecontents.models.Theme;
import com.sc.en.bouddhism.layers.service.MotherBusinessService;
import com.sc.en.bouddhism.layers.service.ServiceManagerInterface;
import com.sc.en.bouddhism.layers.service.themes.interfaces.ThemesWithThemesServiceInterface;
import com.sc.en.bouddhism.transverse.eventbus.events.themes.ThemesWithThemesLoadedEvent;
import com.sc.en.bouddhism.injector.Injector;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ThemesWithThemesService extends MotherBusinessService implements ThemesWithThemesServiceInterface {

  private static final String TAG = "ThemesWithThemesService";

  /**
   *
   */
  private ThemesWithThemesLoadedEvent themesWithThemesLoadedEvent;

  /**
   *
   */
  private List<Theme> themes = null;


  public ThemesWithThemesService(ServiceManagerInterface srvManager) {
    super(srvManager);
    themes = new ArrayList<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    themes = null;
  }
  
  
  /**
   *
   */
  @Override
  public void loadThemesWithThemesAsync() {
    //Log.d(TAG, "loadThemesWithThemesAsync() called with: " + "");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
//    if (!themes.isEmpty()) {
//      reload = true;
//    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postThemesWithThemesLoadedEvent(themes);
    } else {
      themes.clear();
      OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindThemesWithThemesRunnable);
    }
  }
  
  private void loadThemesWithThemesSync(){
    //Log.d(TAG, "loadThemesWithThemesSync() called with: " + "");
    /*

   */
    ThemesDaoInterface themesDaoInterface = Injector.getDaoManager().getThemesDao();

  //  List<Theme> themes = themesDaoInterface.findThemesWithThemes();

    postThemesWithThemesLoadedEvent(convertToThemes(themesDaoInterface.findThemesWithThemes()));
    themesDaoInterface = null;
  }

  private List<Theme> convertToThemes(List<com.sc.en.bouddhism.transverse.orms.realm.models.Theme> themesWithThemes){

    if (themesWithThemes != null) {
      for (int i = 0; i < themesWithThemes.size(); i++) {

        if (themesWithThemes.get(i).getNbQuotes() == 0) {
          continue;
        }

        Theme mMainTheme = new Theme();
        mMainTheme.baseEntities = new ArrayList<>();
        mMainTheme.idCategory = (int) themesWithThemes.get(i).getIdTheme();
        mMainTheme.faith = themesWithThemes.get(i).getTheme();

        if (themesWithThemes.get(i).getThemes() != null) {
          for (int j = 0; j < themesWithThemes.get(i).getThemes().size(); j++) {

            if (themesWithThemes.get(i).getThemes().get(j).getNbQuotes() == 0) {

            } else {

              Theme mSecondTheme = new Theme();
              mSecondTheme.idCategory = (int) themesWithThemes.get(i).getThemes().get(j).getIdTheme();
              mSecondTheme.faith = themesWithThemes.get(i).getThemes().get(j).getTheme();

              mMainTheme.baseEntities.add(mSecondTheme);
            }
          }
        }
        mMainTheme.number = mMainTheme.baseEntities.size();
        themes.add(mMainTheme);
      }
    }
    return themes;
  }

  /**
   * The runnable to execute
   */
  private final DaoFindMovementsWithMovementsRunnable daoFindThemesWithThemesRunnable
    = new DaoFindMovementsWithMovementsRunnable();

  private class DaoFindMovementsWithMovementsRunnable implements Runnable {
    @Override
    public void run() {
      loadThemesWithThemesSync();
    }
  }

  /**
   * Broadcast all the movements Loaded event
   */
  private void postThemesWithThemesLoadedEvent(List<Theme> themesWithThemes) {

    if (themesWithThemesLoadedEvent == null) {
      themesWithThemesLoadedEvent = new ThemesWithThemesLoadedEvent(themesWithThemes);
    } else {
      themesWithThemesLoadedEvent.setThemesWithThemes(themesWithThemes);
    }
    // Log.e(TAG, "postThemesWithThemesLoadedEvent posted" );
    EventBus.getDefault().post(themesWithThemesLoadedEvent);
  }
}
