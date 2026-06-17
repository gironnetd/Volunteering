package com.sc.fr.confucianisme.layers.service.themes.services;

import android.util.SparseArray;

import com.sc.fr.confucianisme.OnelittleAngelApplication;
import com.sc.fr.confucianisme.layers.dao.themes.ThemesDaoInterface;
import com.sc.fr.confucianisme.layers.service.MotherBusinessService;
import com.sc.fr.confucianisme.layers.service.ServiceManagerInterface;
import com.sc.fr.confucianisme.layers.service.themes.interfaces.ThemeByIdThemeServiceInterface;
import com.sc.fr.confucianisme.transverse.eventbus.events.themes.ThemeByIdThemeLoadedEvent;
import com.sc.fr.confucianisme.injector.Injector;
import com.sc.fr.confucianisme.transverse.eventbus.models.ThemeEventBus;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Theme;

import org.greenrobot.eventbus.EventBus;

public class ThemeByIdThemeService extends MotherBusinessService implements ThemeByIdThemeServiceInterface {

  private static final String TAG = "ThemeByIdThemeService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<Theme> themesByIdThemeList = null;

  /**
   *
   */
  private ThemeByIdThemeLoadedEvent themeByIdThemeLoadedEvent;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public ThemeByIdThemeService(ServiceManagerInterface srvManager) {
    super(srvManager);
    themesByIdThemeList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    themesByIdThemeList = null;
  }

  /**
   * @param idTheme
   */
  @Override
  public void loadThemeByIdThemeAsync(int idTheme) {
    //  Log.e(TAG, "loadThemeByIdThemeAsync() called with: " + "cityId = [" + idTheme + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (themesByIdThemeList.get(idTheme)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postThemeByIdThemeDataLoadedEvent(themesByIdThemeList.get(idTheme),idTheme);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new ThemeByIdThemeService.DaoThemeByIdThemeLoadRunnable(idTheme));
    }
  }

  private void loadThemeByIdThemeSync(int idTheme){
    // Log.e(TAG, "loadThemeByIdThemeSync() called with: " + "idTheme = [" + idTheme + "]");
    // Load data from database
    /*

   */
    ThemesDaoInterface themesDaoInterface = Injector.getDaoManager().getThemesDao();
    Theme themeByIdTheme = themesDaoInterface.findThemeByIdTheme(idTheme);
    themesDaoInterface = null;
    //update your own cache
    themesByIdThemeList.put(idTheme,themeByIdTheme);
    //send send back the answer using eventBus
    postThemeByIdThemeDataLoadedEvent(themeByIdTheme,idTheme);
  }

  /**
   * @theme Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoThemeByIdThemeLoadRunnable implements Runnable {
    final int idTheme;

    public DaoThemeByIdThemeLoadRunnable(int idTheme) {
      this.idTheme = idTheme;
    }

    @Override
    public void run() {
      loadThemeByIdThemeSync(idTheme);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postThemeByIdThemeDataLoadedEvent(Theme themeByIdTheme,int idTheme) {
    //  Log.e(TAG, "postThemeByIdThemeDataLoadedEvent() called as found Theme with " +
    //    themeByIdThemeLoadedEvent.getThemeByIdTheme().getQuotes().size() + " elements and idTheme="+idTheme);
    ThemeEventBus theme = new ThemeEventBus(themeByIdTheme);

    if(themeByIdThemeLoadedEvent==null){
      themeByIdThemeLoadedEvent= new ThemeByIdThemeLoadedEvent(theme, idTheme);
    }else{
      themeByIdThemeLoadedEvent.setThemeByIdTheme(theme);
      themeByIdThemeLoadedEvent.setIdTheme(idTheme);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + themeByIdThemeLoadedEvent.getThemeByIdTheme() + " elements and idTheme ="+themeByIdThemeLoadedEvent.getIdTheme());
    EventBus.getDefault().post(themeByIdThemeLoadedEvent);
  }

}
