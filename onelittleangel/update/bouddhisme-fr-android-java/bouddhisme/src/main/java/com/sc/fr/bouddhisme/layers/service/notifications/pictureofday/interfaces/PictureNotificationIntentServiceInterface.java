package com.sc.fr.bouddhisme.layers.service.notifications.pictureofday.interfaces;

import android.content.Intent;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;

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
