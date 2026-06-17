package com.sc.en.christianism.layers.service;

public interface MotherBusinessServiceInterface {
  /**
   * Clean your resource when your service die
   */
  void onDestroy();
  /**
   * Clean your resource when your service die
   */
  void onDestroy(ServiceManagerInterface srvManager);
}
