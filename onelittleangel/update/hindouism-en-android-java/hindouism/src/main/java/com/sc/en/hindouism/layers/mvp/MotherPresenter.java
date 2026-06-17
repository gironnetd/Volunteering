package com.sc.en.hindouism.layers.mvp;

import com.sc.en.hindouism.transverse.eventbus.events.FakeEvent;

import org.greenrobot.eventbus.Subscribe;

public class MotherPresenter {
  @Subscribe
  public void onEvent(FakeEvent event){
    //just to avoid
    //EventBusException: Subscriber class WeatherDataUpdater and its super classes
    // have no public methods with the @Subscribe annotation
  }
}
