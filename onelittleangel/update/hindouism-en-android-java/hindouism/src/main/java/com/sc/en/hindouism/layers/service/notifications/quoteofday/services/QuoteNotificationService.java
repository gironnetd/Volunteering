package com.sc.en.hindouism.layers.service.notifications.quoteofday.services;

import android.content.Intent;

import com.sc.en.hindouism.OnelittleAngelApplication;
import com.sc.en.hindouism.layers.service.MotherBusinessService;
import com.sc.en.hindouism.layers.service.notifications.quoteofday.interfaces.QuoteNotificationIntentServiceInterface;
import com.sc.en.hindouism.layers.service.ServiceManagerInterface;

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
