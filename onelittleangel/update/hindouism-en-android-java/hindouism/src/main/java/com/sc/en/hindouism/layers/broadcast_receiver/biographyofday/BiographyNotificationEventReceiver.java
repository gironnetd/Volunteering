package com.sc.en.hindouism.layers.broadcast_receiver.biographyofday;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.sc.en.hindouism.OnelittleAngelApplication;
import com.sc.en.hindouism.layers.mvp.common.utils.Constants;
import com.sc.en.hindouism.layers.mvp.tablecontents.TableContentsActivity;

import java.util.Calendar;
import java.util.Date;

import me.leolin.shortcutbadger.ShortcutBadger;

import static android.os.Build.VERSION.SDK_INT;


public class BiographyNotificationEventReceiver extends WakefulBroadcastReceiver {

    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";

    private static final SharedPreferences settings =  OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    private static final SharedPreferences.Editor editor = settings.edit();

    public static void setupAlarm(Context context, boolean setUpForNext, int hour, int minute) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);

        if (SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, getTriggerAt(setUpForNext, new Date(), hour, minute), alarmIntent);
        }
        else if (Build.VERSION_CODES.KITKAT <= SDK_INT  && SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTriggerAt(setUpForNext, new Date(), hour, minute), alarmIntent);
        }
        else if (SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, getTriggerAt(setUpForNext, new Date(), hour, minute), alarmIntent);
        }
    }

    public static void cancelAlarm(Context context) {
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
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    private static PendingIntent getStartPendingIntent(Context context) {
        Intent intent = new Intent(context, BiographyNotificationEventReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent getDeleteIntent(Context context) {
        Intent intent = new Intent(context, BiographyNotificationEventReceiver.class);
        intent.setAction(ACTION_DELETE_NOTIFICATION);

        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Intent serviceIntent = null;
        if (ACTION_START_NOTIFICATION_SERVICE.equals(action)) {
            serviceIntent = OnelittleAngelApplication.instance.getServiceManager().getBiographyNotificationService().createBiographyStartNotificationServiceAsync();
            if(!settings.getBoolean(Constants.BIOGRAPHY_IS_NOTIFICATION_SENDED, false)) {
                int badgecount = settings.getInt(Constants.BADGE_COUNT, 0);
                ShortcutBadger.applyCount(OnelittleAngelApplication.instance.getApplicationContext(), ++badgecount);
                editor.putInt(Constants.BADGE_COUNT, badgecount);
                editor.putBoolean(Constants.BIOGRAPHY_IS_NOTIFICATION_SENDED, true);
                editor.commit();
            }

            setupAlarm(OnelittleAngelApplication.instance.getApplicationContext(), true,
                    Integer.parseInt(settings.getString(Constants.BIOGRAPHY_NOTIFICATION_HOUR, "")), Integer.parseInt(settings.getString(Constants.BIOGRAPHY_NOTIFICATION_MINUTE, "")));
        } else if (ACTION_DELETE_NOTIFICATION.equals(action)) {

            serviceIntent = OnelittleAngelApplication.instance.getServiceManager().getBiographyNotificationService().createBiographyDeleteNotificationAsync();
            int badgecount = settings.getInt(Constants.BADGE_COUNT, 0);
            ShortcutBadger.applyCount(OnelittleAngelApplication.instance.getApplicationContext(), --badgecount);
            editor.putInt(Constants.BADGE_COUNT, badgecount);
            editor.putBoolean(Constants.BIOGRAPHY_IS_NOTIFICATION_SENDED, false);
            editor.commit();
        }

        if (serviceIntent != null) {
            startWakefulService(context, serviceIntent);
        }
    }
}
