package com.sc.en.onelittleangel.layers.service.notifications.pictureofday.services;

import android.content.Intent;

import com.sc.en.onelittleangel.OnelittleAngelApplication;
import com.sc.en.onelittleangel.layers.service.MotherBusinessService;
import com.sc.en.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.en.onelittleangel.layers.service.notifications.pictureofday.interfaces.PictureNotificationIntentServiceInterface;

public class PictureNotificationService extends MotherBusinessService implements PictureNotificationIntentServiceInterface {

  /**
   * Constructor
   *
   * @param srvManager
   */
  public PictureNotificationService(ServiceManagerInterface srvManager) {
    super(srvManager);
  }

  @Override
  public void onDestroy() {

  }

  @Override
  public Intent createPictureStartNotificationServiceAsync() {
    return PictureNotificationIntentService.createIntentStartNotificationService(OnelittleAngelApplication.instance.getApplicationContext());
  }

  @Override
  public Intent createPictureDeleteNotificationAsync() {
    return PictureNotificationIntentService.createIntentDeleteNotification(OnelittleAngelApplication.instance.getApplicationContext());
  }
}
