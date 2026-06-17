package com.sc.fr.bouddhisme.layers.service.notifications.pictureofday.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.RemoteViews;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.sc.fr.bouddhisme.OnelittleAngelApplication;
import com.sc.fr.bouddhisme.layers.broadcast_receiver.pictureofday.PictureNotificationEventReceiver;
import com.sc.fr.bouddhisme.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.fr.bouddhisme.layers.mvp.common.utils.Constants;
import com.sc.fr.bouddhisme.layers.mvp.contents.ContentsActivity;
import com.sc.fr.bouddhisme.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.fr.bouddhisme.R;

import java.util.Calendar;

/**
 * Created by klogi
 *
 *
 */
public class PictureNotificationIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 2;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";
    private static int i;
    private SharedPreferences settings;
    SharedPreferences.Editor editor;

    public PictureNotificationIntentService() {
        super(PictureNotificationIntentService.class.getSimpleName());
        settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    }

    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, PictureNotificationIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Intent intent = new Intent(context, PictureNotificationIntentService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {

                Calendar calendar = Calendar.getInstance();
                int i = calendar.get(Calendar.DAY_OF_MONTH);
                OnelittleAngelApplication.instance.manageConnectivityState();

                if (settings == null) settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

                if(settings.getInt(Constants.DAY_OF_MONTH, -1) != i) {
                    //    settings.edit().putInt(Constants.DAY_OF_MONTH, i).apply();

                    OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadPictureOfDayAsync(true).subscribe(this::launchNotification);
                }
                else {

                    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
                    Bitmap bitmap = null;

                    if (!settings.getBoolean(Constants.PICTURE_IS_NULL, false)) {

//       OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadPictureOfDayAsync().subscribe(this::initPictureOfDay);
//       OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadNameOfPictureOfDayAsync().subscribe(this::initNameOfPictureOfDay);
//       OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadAuthorPictureOfDayAsync().subscribe(this::initAuthorPictureOfDay);


                        ANRequest request = AndroidNetworking.get("http://www.onelittleangel.com/common/images/auteur/" + settings.getString(Constants.PICTURE_OF_DAY_JPEGNAME, ""))
                                .setTag("imageRequestTag")
                                .setPriority(Priority.IMMEDIATE)
                                .build();

                        ANResponse response = request.executeForBitmap();
                        //  try {

                        bitmap = (Bitmap) response.getResult();
                        processStartNotification(bitmap);
                    }
                }
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void launchNotification(Bitmap pictureOfDay) {

        if (!settings.getBoolean(Constants.PICTURE_IS_NULL, false)) {

//       OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadPictureOfDayAsync().subscribe(this::initPictureOfDay);
//       OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadNameOfPictureOfDayAsync().subscribe(this::initNameOfPictureOfDay);
//       OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadAuthorPictureOfDayAsync().subscribe(this::initAuthorPictureOfDay);


//            ANRequest request = AndroidNetworking.get("http://www.onelittleangel.com/common/images/auteur/" + settings.getString(Constants.PICTURE_OF_DAY_JPEGNAME, ""))
//                    .setTag("imageRequestTag")
//                    .setPriority(Priority.IMMEDIATE)
//                    .build();
//
//            ANResponse response = request.executeForBitmap();
//            //  try {
//
//            bitmap = (Bitmap) response.getResult();
            processStartNotification(pictureOfDay);
        }

    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
    }

    private void processStartNotification(Bitmap bitmap) {
        // Do something. For example, fetch fresh data from backend to create a rich notification?
        i++;

        settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
        settings.edit().putBoolean(Constants.WAS_NOTIFICATION_SENT_ON_DAY, true).apply();

        // Do something. For example, fetch fresh data from backend to create a rich notification?
        i++;

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification_onelittleangel);

        String namePictureOfDay = settings.getString(Constants.PICTURE_OF_DAY_NAME, "");

        String type;
        if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
            type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
        } else {
            type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
        }

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.picture_notification);
        contentView.setImageViewBitmap(R.id.image, bitmap);

        contentView.setTextViewText(R.id.title, OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day));
        contentView.setTextColor(R.id.title, settings.getInt(CardViewNative.DARKERRGB, 0));

        contentView.setTextViewText(R.id.name, namePictureOfDay);

        contentView.setTextColor(R.id.text, settings.getInt(CardViewNative.DARKERRGB, 0));
        contentView.setTextViewText(R.id.text, "[ " + getResources().getString(R.string.quotes_of) + " " + namePictureOfDay + " ]");

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day))
                .setAutoCancel(true)
                .setColor(Color.WHITE)
                .setContentText(namePictureOfDay)
                .setSmallIcon(R.drawable.ic_notification_onelittleangel)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));

        builder.setCustomBigContentView(contentView);

        Intent mainIntent = new Intent(this, ContentsActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.fromFragment), type);
        mainIntent.putExtra(getResources().getString(R.string.from), namePictureOfDay);

        mainIntent.putExtra(type, namePictureOfDay);
        mainIntent.putExtra("fromNotification", true);
        mainIntent.putExtra("fromPictureNotification", true);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                mainIntent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntent);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        builder.setDeleteIntent(PictureNotificationEventReceiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }
}
