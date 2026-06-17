package com.sc.en.taoism.layers.service.notifications.biographyofday.services;

import android.content.Intent;

import com.sc.en.taoism.OnelittleAngelApplication;
import com.sc.en.taoism.layers.service.MotherBusinessService;
import com.sc.en.taoism.layers.service.ServiceManagerInterface;
import com.sc.en.taoism.layers.service.notifications.biographyofday.interfaces.BiographyNotificationIntentServiceInterface;

public class BiographyNotificationService extends MotherBusinessService implements BiographyNotificationIntentServiceInterface {

  /**
   * Constructor
   *
   * @param srvManager
   */
  public BiographyNotificationService(ServiceManagerInterface srvManager) {
    super(srvManager);
  }

  @Override
  public void onDestroy() {

  }

  @Override
  public Intent createBiographyStartNotificationServiceAsync() {
    return BiographyNotificationIntentService.createIntentStartNotificationService(OnelittleAngelApplication.instance.getApplicationContext());
  }

  @Override
  public Intent createBiographyDeleteNotificationAsync() {
    return BiographyNotificationIntentService.createIntentDeleteNotification(OnelittleAngelApplication.instance.getApplicationContext());
  }
}
