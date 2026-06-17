package com.sc.fr.bouddhisme.layers.service.themes.services;

import android.util.SparseArray;

import com.sc.fr.bouddhisme.OnelittleAngelApplication;
import com.sc.fr.bouddhisme.layers.dao.themes.ThemesDaoInterface;
import com.sc.fr.bouddhisme.layers.service.MotherBusinessService;
import com.sc.fr.bouddhisme.transverse.eventbus.events.themes.ThemesByIdParentLoadedEvent;
import com.sc.fr.bouddhisme.transverse.eventbus.models.ThemeEventBus;
import com.sc.fr.bouddhisme.injector.Injector;
import com.sc.fr.bouddhisme.layers.service.ServiceManagerInterface;
import com.sc.fr.bouddhisme.layers.service.themes.interfaces.ThemesByIdParentServiceInterface;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Theme;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ThemesByIdParentService extends MotherBusinessService implements ThemesByIdParentServiceInterface {

  private static final String TAG = "ThemesByIdParentService";

  /**
   * The authors to display (the cache)
   */
  private SparseArray<List<Theme>> themesByIdParentList = null;

  /**
   *
   */
  private ThemesByIdParentLoadedEvent themeByIdParentLoadedEvent;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public ThemesByIdParentService(ServiceManagerInterface srvManager) {
    super(srvManager);
    themesByIdParentList = new SparseArray<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    themesByIdParentList = null;
  }

  /**
   * @param idParent
   */
  @Override
  public void loadThemesByIdParentAsync(int idParent) {
    //  Log.e(TAG, "loadThemeByIdThemeAsync() called with: " + "cityId = [" + idParent + "]");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (themesByIdParentList.get(idParent)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postThemeByIdThemeDataLoadedEvent(themesByIdParentList.get(idParent),idParent);
    } else {
      // then launch it
      OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoThemeByIdThemeLoadRunnable(idParent));
    }
  }

  private void loadThemeByIdThemeSync(int idParent){
    // Log.e(TAG, "loadThemeByIdThemeSync() called with: " + "idParent = [" + idParent + "]");
    // Load data from database
    /*

   */
    ThemesDaoInterface themesDaoInterface = Injector.getDaoManager().getThemesDao();
    List<Theme> themesByIdTheme = themesDaoInterface.findThemesByIdParent(idParent);
    themesDaoInterface = null;
    //update your own cache
    themesByIdParentList.put(idParent,themesByIdTheme);
    //send send back the answer using eventBus
    postThemeByIdThemeDataLoadedEvent(themesByIdTheme,idParent);
  }

  /**
   * @theme Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable with an Handler
   */
  private class DaoThemeByIdThemeLoadRunnable implements Runnable {
    final int idParent;

    public DaoThemeByIdThemeLoadRunnable(int idParent) {
      this.idParent = idParent;
    }

    @Override
    public void run() {
      loadThemeByIdThemeSync(idParent);
    }
  }

  /***********************************************************
   *  Updating strategy and update listening
   **********************************************************/
  /**
   * Brodcast the Weather loaded event
   */
  private void postThemeByIdThemeDataLoadedEvent(List<Theme> themesByIdTheme,int idParent) {
    //  Log.e(TAG, "postThemeByIdThemeDataLoadedEvent() called as found Theme with " +
    //    themeByIdThemeLoadedEvent.getThemeByIdTheme().getQuotes().size() + " elements and idParent="+idParent);
    List<ThemeEventBus> themes = new ArrayList<>();

    for(Theme t : themesByIdTheme){
      themes.add(new ThemeEventBus(t));
    }

    if(themeByIdParentLoadedEvent==null){
      themeByIdParentLoadedEvent= new ThemesByIdParentLoadedEvent(themes, idParent);
    }else{
      themeByIdParentLoadedEvent.setThemesByIdParent(themes);
      themeByIdParentLoadedEvent.setIdParent(idParent);
    }
    //  Log.e(TAG, "postForecastDataLoadedEvent() returns event:" + themeByIdThemeLoadedEvent.getThemeByIdTheme() + " elements and idParent ="+themeByIdThemeLoadedEvent.getIdTheme());
    EventBus.getDefault().post(themeByIdParentLoadedEvent);
  }

}
