package com.sc.fr.confucianisme.layers.service.notifications.pictureofday.services;

import android.content.Intent;

import com.sc.fr.confucianisme.OnelittleAngelApplication;
import com.sc.fr.confucianisme.layers.service.MotherBusinessService;
import com.sc.fr.confucianisme.layers.service.ServiceManagerInterface;
import com.sc.fr.confucianisme.layers.service.notifications.pictureofday.interfaces.PictureNotificationIntentServiceInterface;

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
