package com.sc.en.philosophy.layers.service.notifications.biographyofday.interfaces;

import android.content.Intent;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;

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
