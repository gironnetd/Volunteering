package com.sc.en.confucianism.layers.service.notifications.pictureofday.interfaces;

import android.content.Intent;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;

public interface PictureNotificationIntentServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Intent createPictureStartNotificationServiceAsync();

  /**
   *
   * @return
   */
  Intent createPictureDeleteNotificationAsync();
}
