package com.sc.fr.christianisme.layers.service.notifications.quoteofday.interfaces;

import android.content.Intent;

import com.sc.fr.christianisme.layers.service.MotherBusinessServiceInterface;

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
