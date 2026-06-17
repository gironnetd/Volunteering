package com.sc.en.islam.layers.broadcast_receiver.pictureofday;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by klogi
 *
 * Broadcast receiver for: BOOT_COMPLETED, TIMEZONE_CHANGED, and TIME_SET events. Sets Alarm Manager for notification;
 */
public final class PictureNotificationServiceStarterReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PictureNotificationEventReceiver.setupAlarm(context, false, 0, 0);
    }
}