package com.sc.en.confucianism.layers.broadcast_receiver.datechanging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.sc.en.confucianism.OnelittleAngelApplication;
import com.sc.en.confucianism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.confucianism.layers.broadcast_receiver.biographyofday.BiographyNotificationEventReceiver;
import com.sc.en.confucianism.layers.broadcast_receiver.pictureofday.PictureNotificationEventReceiver;
import com.sc.en.confucianism.layers.broadcast_receiver.quoteofday.QuoteNotificationEventReceiver;
import com.sc.en.confucianism.layers.mvp.common.utils.Constants;

public class DateChangedReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    SharedPreferences settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    //Log.i("55", "DateChangedReceiver onReceive");

    if(settings.getBoolean(Constants.QUOTE_RECEIVE_NOTIFICATION, false)) {
      //Log.i("55", "DateChangedReceiver onReceive settings.getBoolean(Constants.QUOTE_RECEIVE_NOTIFICATION, false)");

      QuoteNotificationEventReceiver.setupAlarm(OnelittleAngelApplication.instance.getApplicationContext(),
              false, Integer.parseInt(settings.getString(Constants.QUOTE_NOTIFICATION_HOUR, "")), Integer.parseInt(settings.getString(Constants.QUOTE_NOTIFICATION_MINUTE, "")));
    }

    if(settings.getBoolean(Constants.BIOGRAPHY_RECEIVE_NOTIFICATION, false)) {
      BiographyNotificationEventReceiver.setupAlarm(OnelittleAngelApplication.instance.getApplicationContext(), false, Integer.parseInt(settings.getString(Constants.BIOGRAPHY_NOTIFICATION_HOUR, "")), Integer.parseInt(settings.getString(Constants.BIOGRAPHY_NOTIFICATION_MINUTE, "")));
    }

    if(settings.getBoolean(Constants.PICTURE_RECEIVE_NOTIFICATION, false)) {
      PictureNotificationEventReceiver.setupAlarm(OnelittleAngelApplication.instance.getApplicationContext(), false, Integer.parseInt(settings.getString(Constants.PICTURE_NOTIFICATION_HOUR, "")), Integer.parseInt(settings.getString(Constants.PICTURE_NOTIFICATION_MINUTE, "")));
    }

    editor.putBoolean(Constants.RELOAD_HOME_PAGE, true);
//editor.commit();
    editor.apply();  }
}
