package com.sc.en.bouddhism.layers.service.notifications.quoteofday.interfaces;

import android.content.Intent;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;

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
