package com.sc.en.hindouism.layers.service.notifications.pictureofday.services;

import android.content.Intent;

import com.sc.en.hindouism.OnelittleAngelApplication;
import com.sc.en.hindouism.layers.service.notifications.pictureofday.interfaces.PictureNotificationIntentServiceInterface;
import com.sc.en.hindouism.layers.service.MotherBusinessService;
import com.sc.en.hindouism.layers.service.ServiceManagerInterface;

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
