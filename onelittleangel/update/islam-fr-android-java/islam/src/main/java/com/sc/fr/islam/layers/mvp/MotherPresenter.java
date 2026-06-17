package com.sc.fr.islam.layers.mvp;

import com.sc.fr.islam.transverse.eventbus.events.FakeEvent;

import org.greenrobot.eventbus.Subscribe;

public class MotherPresenter {
  @Subscribe
  public void onEvent(FakeEvent event){
    //just to avoid
    //EventBusException: Subscriber class WeatherDataUpdater and its super classes
    // have no public methods with the @Subscribe annotation
  }
}
