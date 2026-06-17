package com.sc.en.onelittleangel.layers.service.notifications.biographyofday.interfaces;

import android.content.Intent;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;

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
