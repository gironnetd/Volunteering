package com.sc.fr.bouddhisme.layers.service.notifications.quoteofday.interfaces;

import android.content.Intent;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;

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
