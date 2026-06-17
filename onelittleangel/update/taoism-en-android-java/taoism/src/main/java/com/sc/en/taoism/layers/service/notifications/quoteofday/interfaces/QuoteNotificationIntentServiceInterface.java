package com.sc.en.taoism.layers.service.notifications.quoteofday.interfaces;

import android.content.Intent;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;

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
