package com.sc.fr.taoisme.layers.service.themes.services;

import android.support.v4.util.ArrayMap;

import com.sc.fr.taoisme.injector.Injector;
import com.sc.fr.taoisme.layers.dao.themes.ThemesDaoInterface;
import com.sc.fr.taoisme.layers.service.MotherBusinessService;
import com.sc.fr.taoisme.layers.service.ServiceManagerInterface;
import com.sc.fr.taoisme.layers.service.themes.interfaces.ThemeByNameServiceInterface;
import com.sc.fr.taoisme.transverse.orms.realm.models.Theme;

import io.reactivex.Observable;

public class ThemeByNameService extends MotherBusinessService implements ThemeByNameServiceInterface {

  private static final String TAG = "ThemeByNameService";

  /**
   * The authors to display (the cache)
   */
  private ArrayMap<String, Theme> themesByNameList = null;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public ThemeByNameService(ServiceManagerInterface srvManager) {
    super(srvManager);
    themesByNameList = new ArrayMap<>();
  }

  @Override
  public void onDestroy() {

  }

  @Override
  public Observable<Theme> loadThemeByNameAsync(String name) {
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (themesByNameList.get(name)!=null) {
      reload = true;
    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
    //  postThemeByIdThemeDataLoadedEvent(themesByIdThemeList.get(idTheme),idTheme);
      return Observable.just(themesByNameList.get(name));
    } else {
      // then launch it
    //  OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new ThemeByIdThemeService.DaoThemeByIdThemeLoadRunnable(idTheme));
      /*

   */
      ThemesDaoInterface themesDaoInterface = Injector.getDaoManager().getThemesDao();
      Theme themeByName = themesDaoInterface.findThemeByName(name);
      themesDaoInterface = null;
      //update your own cache
      themesByNameList.put(name, themeByName);
      return Observable.just(themeByName);
    }
  }
}
