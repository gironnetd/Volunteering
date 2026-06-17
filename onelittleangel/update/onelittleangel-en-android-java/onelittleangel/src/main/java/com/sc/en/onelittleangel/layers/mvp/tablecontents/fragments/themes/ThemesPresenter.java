package com.sc.en.onelittleangel.layers.mvp.tablecontents.fragments.themes;

import com.sc.en.onelittleangel.OnelittleAngelApplication;
import com.sc.en.onelittleangel.layers.mvp.MotherPresenter;
import com.sc.en.onelittleangel.layers.mvp.tablecontents.fragments.PresenterInterface;
import com.sc.en.onelittleangel.layers.mvp.tablecontents.fragments.ViewInterface;
import com.sc.en.onelittleangel.layers.mvp.tablecontents.models.BaseEntity;
import com.sc.en.onelittleangel.layers.mvp.tablecontents.models.Theme;
import com.sc.en.onelittleangel.transverse.eventbus.events.themes.ThemesWithThemesLoadedEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ThemesPresenter extends MotherPresenter implements PresenterInterface {

  private static final String TAG = "ThemesPresenter";

  private ViewInterface themesViewInterface = null;

  private List<Theme> themes;

  public ThemesPresenter(ViewInterface themesViewInterface) {
    this.themesViewInterface = themesViewInterface;
    if(themes == null) {
      themes = new ArrayList<>();
    }
  }

  /**
   *
   */
  @Override
  public void loadMovements() {
    OnelittleAngelApplication.instance.getServiceManager()
      .getThemesWithThemesService().loadThemesWithThemesAsync();
  }

  /**
   * @return
   */
  @Override
  public List<? extends BaseEntity> getMovements() {
    return themes;
  }

  @Override
  public void removeMovementsOfFragment() {
    themes = null;
    themesViewInterface = null;
  }

  /***********************************************************
   *  Listening for services response
   **********************************************************/

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(ThemesWithThemesLoadedEvent event){
    themes.addAll(event.getThemesWithThemes());
    themesViewInterface.updateMovements();
  }

}
