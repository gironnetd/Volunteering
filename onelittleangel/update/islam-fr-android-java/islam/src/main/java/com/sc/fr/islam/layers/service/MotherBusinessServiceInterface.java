package com.sc.fr.islam.layers.service;

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
