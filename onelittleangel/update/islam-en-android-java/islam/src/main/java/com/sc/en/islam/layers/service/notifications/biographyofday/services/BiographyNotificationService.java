package com.sc.en.islam.layers.service.notifications.biographyofday.services;

import android.content.Intent;

import com.sc.en.islam.OnelittleAngelApplication;
import com.sc.en.islam.layers.service.MotherBusinessService;
import com.sc.en.islam.layers.service.ServiceManagerInterface;
import com.sc.en.islam.layers.service.notifications.biographyofday.interfaces.BiographyNotificationIntentServiceInterface;

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
