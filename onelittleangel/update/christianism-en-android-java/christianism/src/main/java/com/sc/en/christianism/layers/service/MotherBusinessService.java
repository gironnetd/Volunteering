package com.sc.en.christianism.layers.service;


import com.sc.en.christianism.transverse.eventbus.events.FakeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public abstract class MotherBusinessService implements MotherBusinessServiceInterface {

  /**
   * Constructor
   */
  protected MotherBusinessService(ServiceManagerInterface srvManager) {
    if(srvManager==null && srvManager instanceof ServiceManager){
      throw new IllegalArgumentException("The dev try to fuck the code, but the code doesn't want that, so fuck the dev");
    }
    // Nothing to initialize
    // the parameter is to ensure only srvManager cant create it
    EventBus.getDefault().register(this);
  }

  /**
   * Clean your resource when your service die
   */
  public void onDestroy(ServiceManagerInterface srvManager){
    EventBus.getDefault().unregister(this);
    onDestroy();
  }

  @Subscribe
  public void onEvent(FakeEvent event){
    //just to avoid
    //EventBusException: Subscriber class WeatherDataUpdater and its super classes
    // have no public methods with the @Subscribe annotation
  }
}
