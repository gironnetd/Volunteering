package com.sc.en.bouddhism.layers.service.notifications.biographyofday.interfaces;

import android.content.Intent;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;

public interface BiographyNotificationIntentServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Intent createBiographyStartNotificationServiceAsync();

  /**
   *
   * @return
   */
  Intent createBiographyDeleteNotificationAsync();

}
