package com.sc.en.christianism.layers.broadcast_receiver.quoteofday;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.sc.en.christianism.OnelittleAngelApplication;
import com.sc.en.christianism.layers.mvp.common.utils.Constants;
import com.sc.en.christianism.layers.mvp.tablecontents.TableContentsActivity;

import java.util.Calendar;
import java.util.Date;

import me.leolin.shortcutbadger.ShortcutBadger;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by klogi
 *
 * WakefulBroadcastReceiver used to receive intents fired from the AlarmManager for showing notifications
 * and from the notification itself if it is deleted.
 */
public class QuoteNotificationEventReceiver extends WakefulBroadcastReceiver {

    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";
    private static Date dateTime;

    private static final int NOTIFICATIONS_INTERVAL_IN_HOURS = 15;

    private static final SharedPreferences settings =  OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    private static final SharedPreferences.Editor editor = settings.edit();

    public static void setupAlarm(Context context,boolean setUpForNextDay, int hour, int minute) {
        //Log.i("55", "QuoteNotificationEventReceiver setupAlarm");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);
        //Log.i("55", " : " + (date == null ? new Date().getDate() : date.getDate()));
        //Log.i("55", "hour : " + hour);
        //Log.i("55", "minute : " + minute);
        //dateTime = date == null ?new Date() : date;

        if (SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, getTriggerAt(setUpForNextDay, new Date(), hour, minute), alarmIntent);
        }
        else if (Build.VERSION_CODES.KITKAT <= SDK_INT  && SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTriggerAt(setUpForNextDay, new Date(), hour, minute), alarmIntent);
        }
        else if (SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, getTriggerAt(setUpForNextDay, new Date(), hour, minute), alarmIntent);
        }
    }

    public static void cancelAlarm(Context context) {
        //Log.i("55", "QuoteNotificationEventReceiver cancelAlarm");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);
        alarmManager.cancel(alarmIntent);
    }

    private static long getTriggerAt(boolean setUpForNext, Date now, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        calendar.setTimeInMillis(System.currentTimeMillis());
        if (setUpForNext) calendar.add(Calendar.DATE, 1);

        int m = calendar.get(Calendar.MINUTE);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        if((!setUpForNext && hour < h) || (!setUpForNext && (hour == h && minute < m))) calendar.add(Calendar.DATE, 1);
        //Log.i("55", "getTriggerAt date : " + (now == null ? new Date().getDate() : now.getDate()));
        //Log.i("55", "calendar.get(Calendar.DATE) : " + calendar.get(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    private static PendingIntent getStartPendingIntent(Context context) {
        //Log.i("55", "QuoteNotificationEventReceiver getStartPendingIntent");

        Intent intent = new Intent(context, QuoteNotificationEventReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent getDeleteIntent(Context context) {
        //Log.i("55", "QuoteNotificationEventReceiver getDeleteIntent");

        Intent intent = new Intent(context, QuoteNotificationEventReceiver.class);
        intent.setAction(ACTION_DELETE_NOTIFICATION);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Intent serviceIntent = null;
        //Log.i("55", "QuoteNotificationEventReceiver onReceive");

        if (ACTION_START_NOTIFICATION_SERVICE.equals(action)) {
            //Log.i("55", "ACTION_START_NOTIFICATION_SERVICE.equals(action)");

            serviceIntent = OnelittleAngelApplication.instance.getServiceManager().getQuoteNotificationService().createQuoteStartNotificationServiceAsync();
            if(!settings.getBoolean(Constants.QUOTE_IS_NOTIFICATION_SENDED, false)) {
                int badgecount = settings.getInt(Constants.BADGE_COUNT, 0);
                ShortcutBadger.applyCount(OnelittleAngelApplication.instance.getApplicationContext(), ++badgecount);
                editor.putInt(Constants.BADGE_COUNT, badgecount);
                editor.putBoolean(Constants.QUOTE_IS_NOTIFICATION_SENDED, true);
                editor.commit();
            }

            setupAlarm(OnelittleAngelApplication.instance.getApplicationContext(), true,
                    Integer.parseInt(settings.getString(Constants.QUOTE_NOTIFICATION_HOUR, "")), Integer.parseInt(settings.getString(Constants.QUOTE_NOTIFICATION_MINUTE, "")));
        } else if (ACTION_DELETE_NOTIFICATION.equals(action)) {
            //Log.i("55", "ACTION_DELETE_NOTIFICATION.equals(action)");

            serviceIntent = OnelittleAngelApplication.instance.getServiceManager().getQuoteNotificationService().createQuoteDeleteNotificationAsync();
            int badgecount = settings.getInt(Constants.BADGE_COUNT, 0);
            ShortcutBadger.applyCount(OnelittleAngelApplication.instance.getApplicationContext(), --badgecount);
            editor.putInt(Constants.BADGE_COUNT, badgecount);
            editor.putBoolean(Constants.QUOTE_IS_NOTIFICATION_SENDED, false);
            editor.commit();
        }

        if (serviceIntent != null) {
            //    Log.i("55", "serviceIntent != null");
            startWakefulService(context, serviceIntent);
        }

    }
}
