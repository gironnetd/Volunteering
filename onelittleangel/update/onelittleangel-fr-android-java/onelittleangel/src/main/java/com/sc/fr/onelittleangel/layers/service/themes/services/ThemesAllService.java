package com.sc.fr.onelittleangel.layers.service.themes.services;

import android.util.Log;
import android.util.SparseArray;

import com.sc.fr.onelittleangel.injector.Injector;
import com.sc.fr.onelittleangel.OnelittleAngelApplication;
import com.sc.fr.onelittleangel.layers.dao.themes.ThemesDaoInterface;
import com.sc.fr.onelittleangel.layers.service.MotherBusinessService;
import com.sc.fr.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.fr.onelittleangel.layers.service.themes.interfaces.ThemesAllServiceInterface;
import com.sc.fr.onelittleangel.transverse.eventbus.events.themes.ThemesAllLoadedEvent;
import com.sc.fr.onelittleangel.transverse.eventbus.models.ThemeEventBus;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Theme;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ThemesAllService extends MotherBusinessService implements ThemesAllServiceInterface {

  private static final String TAG = "ThemesAllService";

  /**
   * The themes to display (the cache)
   */
  private SparseArray<Theme> themesList = null;
  /**
   *
   */
  private ThemesAllServiceInterface themesAllServiceInterface = null;

  /**
   *
   */
  private ThemesAllLoadedEvent themesAllLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public ThemesAllService(ServiceManagerInterface srvManager) {
    super(srvManager);
    themesList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    themesList = null;
  }

  /**
   * Load all the themes from the database asynchronously
   */
  @Override
  public void loadAllThemesAsync() {
    Log.d(TAG, "loadThemes() called with: " + "");
    OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindAllThemesRunnable);
  }

  private void loadAllThemesSync(){
    // Load data from DB
    ThemesDaoInterface themesDaoInterface = Injector.getDaoManager().getThemesDao();
    //send back the answer using eventBus
    postAllThemesLoadedEvent(themesDaoInterface.findAllThemes());
    themesDaoInterface =null;
  }

  /**
   * The runnable to execute
   */
  private final DaoFindAllThemesRunnable daoFindAllThemesRunnable = new DaoFindAllThemesRunnable();
  /**
   * @theme Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable
   */
  private class DaoFindAllThemesRunnable implements Runnable {
    @Override
    public void run() {
      loadAllThemesSync();
    }
  }

  /**
   * Broadcast all the themes Loaded event
   */
  private void postAllThemesLoadedEvent(List<Theme> themesAll) {

    List<ThemeEventBus> themes = new ArrayList<>();

    for(Theme a : themesAll){
      themes.add(new ThemeEventBus(a));
    }

    if(themesAllLoadedEvent ==null){
      themesAllLoadedEvent = new ThemesAllLoadedEvent(themes);
    }else{
      themesAllLoadedEvent.setThemes(themes);
    }
    Log.e(TAG, "postCitiesLoadedEvent posted" );
    EventBus.getDefault().post(themesAllLoadedEvent);
  }
}
