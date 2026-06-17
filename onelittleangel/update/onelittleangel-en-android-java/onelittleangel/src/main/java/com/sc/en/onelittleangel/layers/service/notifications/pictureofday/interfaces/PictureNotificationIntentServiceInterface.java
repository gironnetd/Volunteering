package com.sc.en.onelittleangel.layers.service.notifications.pictureofday.interfaces;

import android.content.Intent;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;

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
