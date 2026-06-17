package com.sc.en.onelittleangel.layers.service.notifications.quoteofday.interfaces;

import android.content.Intent;

import com.sc.en.onelittleangel.layers.service.MotherBusinessServiceInterface;

public interface QuoteNotificationIntentServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Intent createQuoteStartNotificationServiceAsync();

  /**
   *
   * @return
   */
  Intent createQuoteDeleteNotificationAsync();
}
