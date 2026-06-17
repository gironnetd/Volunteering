package com.sc.en.quotes.layers.service.notifications.quoteofday.services;

import android.content.Intent;

import com.sc.en.quotes.OnelittleAngelApplication;
import com.sc.en.quotes.layers.service.MotherBusinessService;
import com.sc.en.quotes.layers.service.ServiceManagerInterface;
import com.sc.en.quotes.layers.service.notifications.quoteofday.interfaces.QuoteNotificationIntentServiceInterface;

public class QuoteNotificationService extends MotherBusinessService implements QuoteNotificationIntentServiceInterface {

  /**
   * Constructor
   *
   * @param srvManager
   */
  public QuoteNotificationService(ServiceManagerInterface srvManager) {
    super(srvManager);
  }

  @Override
  public void onDestroy() {

  }

  @Override
  public Intent createQuoteStartNotificationServiceAsync() {
    return QuoteNotificationIntentService.createIntentStartNotificationService(OnelittleAngelApplication.instance.getApplicationContext());
  }

  @Override
  public Intent createQuoteDeleteNotificationAsync() {
    return QuoteNotificationIntentService.createIntentDeleteNotification(OnelittleAngelApplication.instance.getApplicationContext());
  }
}
