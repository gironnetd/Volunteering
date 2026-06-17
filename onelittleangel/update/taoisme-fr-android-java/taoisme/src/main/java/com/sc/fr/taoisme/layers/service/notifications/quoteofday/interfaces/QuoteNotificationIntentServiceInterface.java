package com.sc.fr.taoisme.layers.service.notifications.quoteofday.interfaces;

import android.content.Intent;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;

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
