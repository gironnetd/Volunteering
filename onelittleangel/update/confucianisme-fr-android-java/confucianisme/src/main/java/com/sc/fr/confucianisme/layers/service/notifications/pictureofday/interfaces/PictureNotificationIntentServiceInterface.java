package com.sc.fr.confucianisme.layers.service.notifications.pictureofday.interfaces;

import android.content.Intent;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;

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
