package com.sc.en.hindouism.layers.service.notifications.biographyofday.interfaces;

import android.content.Intent;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;

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
