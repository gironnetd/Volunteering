package com.sc.fr.bouddhisme.layers.service.notifications.biographyofday.services;

import android.content.Intent;

import com.sc.fr.bouddhisme.OnelittleAngelApplication;
import com.sc.fr.bouddhisme.layers.service.MotherBusinessService;
import com.sc.fr.bouddhisme.layers.service.ServiceManagerInterface;
import com.sc.fr.bouddhisme.layers.service.notifications.biographyofday.interfaces.BiographyNotificationIntentServiceInterface;

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
