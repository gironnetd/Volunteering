package com.sc.en.quotes.layers.broadcast_receiver.quoteofday;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by klogi
 *
 * Broadcast receiver for: BOOT_COMPLETED, TIMEZONE_CHANGED, and TIME_SET events. Sets Alarm Manager for notification;
 */
public final class QuoteNotificationServiceStarterReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.i("55", "onReceive QuoteNotificationServiceStarterReceiver");

        QuoteNotificationEventReceiver.setupAlarm(context,false, 0, 0);
    }
}