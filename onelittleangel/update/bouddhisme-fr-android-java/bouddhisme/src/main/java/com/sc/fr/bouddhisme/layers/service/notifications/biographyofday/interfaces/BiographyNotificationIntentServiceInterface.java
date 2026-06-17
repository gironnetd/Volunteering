package com.sc.fr.bouddhisme.layers.service.notifications.biographyofday.interfaces;

import android.content.Intent;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;

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
