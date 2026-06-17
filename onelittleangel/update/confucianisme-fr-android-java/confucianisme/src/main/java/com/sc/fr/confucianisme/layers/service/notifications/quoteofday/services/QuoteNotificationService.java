package com.sc.fr.confucianisme.layers.service.notifications.quoteofday.services;

import android.content.Intent;

import com.sc.fr.confucianisme.OnelittleAngelApplication;
import com.sc.fr.confucianisme.layers.service.MotherBusinessService;
import com.sc.fr.confucianisme.layers.service.ServiceManagerInterface;
import com.sc.fr.confucianisme.layers.service.notifications.quoteofday.interfaces.QuoteNotificationIntentServiceInterface;

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
