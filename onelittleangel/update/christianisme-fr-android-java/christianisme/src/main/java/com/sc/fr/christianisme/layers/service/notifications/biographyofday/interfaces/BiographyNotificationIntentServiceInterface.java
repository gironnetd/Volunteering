package com.sc.fr.christianisme.layers.service.notifications.biographyofday.interfaces;

import android.content.Intent;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

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
