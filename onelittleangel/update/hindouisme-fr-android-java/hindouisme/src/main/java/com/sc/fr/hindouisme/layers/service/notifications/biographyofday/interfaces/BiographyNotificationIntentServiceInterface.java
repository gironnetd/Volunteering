package com.sc.fr.hindouisme.layers.service.notifications.biographyofday.interfaces;

import android.content.Intent;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;

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
