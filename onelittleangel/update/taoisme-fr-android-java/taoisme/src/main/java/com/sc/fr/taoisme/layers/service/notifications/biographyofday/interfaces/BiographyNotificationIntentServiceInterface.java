package com.sc.fr.taoisme.layers.service.notifications.biographyofday.interfaces;

import android.content.Intent;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

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
