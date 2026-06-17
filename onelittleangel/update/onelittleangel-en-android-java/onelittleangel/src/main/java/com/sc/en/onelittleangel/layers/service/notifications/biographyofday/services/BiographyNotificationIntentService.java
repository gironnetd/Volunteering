package com.sc.en.onelittleangel.layers.service.notifications.biographyofday.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.text.Html;
import android.widget.RemoteViews;

import com.sc.en.onelittleangel.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.onelittleangel.transverse.orms.realm.models.Author;
import com.sc.en.onelittleangel.injector.Injector;
import com.sc.en.onelittleangel.OnelittleAngelApplication;
import com.sc.en.onelittleangel.R;
import com.sc.en.onelittleangel.layers.broadcast_receiver.biographyofday.BiographyNotificationEventReceiver;
import com.sc.en.onelittleangel.layers.dao.authors.AuthorsDaoInterface;
import com.sc.en.onelittleangel.layers.dao.books.BooksDaoInterface;
import com.sc.en.onelittleangel.layers.mvp.biography.BiographyActivity;
import com.sc.en.onelittleangel.layers.mvp.common.utils.Constants;
import com.sc.en.onelittleangel.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.onelittleangel.transverse.orms.realm.models.Book;

import java.util.Calendar;

/**
 * Created by klogi
 *
 *
 */
public class BiographyNotificationIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";
    SharedPreferences settings;
  // SharedPreferences.Editor editor;

    public BiographyNotificationIntentService() {
        super(BiographyNotificationIntentService.class.getSimpleName());

    }

    public static Intent createIntentStartNotificationService(Context context) {

        Intent intent = new Intent(context, BiographyNotificationIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Intent intent = new Intent(context, BiographyNotificationIntentService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {

                Calendar calendar = Calendar.getInstance();
                int i = calendar.get(Calendar.DAY_OF_MONTH);
                OnelittleAngelApplication.instance.manageConnectivityState();

                if (settings == null) settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);


                if (settings.getInt(Constants.DAY_OF_MONTH, -1) != i) {

                    // settings.edit().putInt(Constants.DAY_OF_MONTH, i).apply();

                    OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadBiographyOfDayAsync(false, true).subscribe(this::launchAuthorPresentationOfDayNotification);
                } else {
                    processStartNotification();
                }
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void launchAuthorPresentationOfDayNotification(Author authorPresentationOfDay) {
        AuthorsDaoInterface authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
        authorPresentationOfDay = authorsDaoInterface.findAuthorByName(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, ""));
        authorsDaoInterface = null;

        if(authorPresentationOfDay == null) {
            OnelittleAngelApplication.instance.getServiceManager().getBookByNameService().loadBookByNameAsync(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, "")).subscribe(this::launchAuthorPresentationOfDayNotification);
        } else processStartNotification();
    }

    private void launchAuthorPresentationOfDayNotification(Book bookPresentationOfDay) {
      processStartNotification();
    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
    }

    private void processStartNotification() {
        // Do something. For example, fetch fresh data from backend to create a rich notification?
      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      settings.edit().putBoolean(Constants.WAS_NOTIFICATION_SENT_ON_DAY, true).apply();
        String s = null;

        if(settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false)) {
           AuthorsDaoInterface authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
            s = authorsDaoInterface.findAuthorByName(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, "")).getPresentation().getPresentation().substring(0, 600) + " ...";
            authorsDaoInterface = null;
        } else {
            BooksDaoInterface booksDaoInterface = Injector.getDaoManager().getBooksDao();
            s = booksDaoInterface.findBookByName(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, "")).getPresentation().getPresentation().substring(0, 600) + " ...";
        }

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.biography_notification);
        contentView.setTextViewText(R.id.title, OnelittleAngelApplication.instance.getResources().getString(R.string.biography_of_the_day));

        contentView.setTextColor(R.id.title, settings.getInt(CardViewNative.DARKERRGB, 0));
        contentView.setTextViewText(R.id.name, settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, ""));
        contentView.setTextViewText(R.id.other_quotes,"[ " + getResources().getString(R.string.biography_of) + " " + settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, "") + " ]");
        contentView.setTextColor(R.id.other_quotes, settings.getInt(CardViewNative.DARKERRGB, 0));

        if(s.length() >= 450) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                contentView.setTextViewText(R.id.text, Html.fromHtml(s.substring(0, 450) + " ...", Html.FROM_HTML_MODE_LEGACY));
            } else {
                contentView.setTextViewText(R.id.text, Html.fromHtml(s.substring(0, 450) + " ..."));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                contentView.setTextViewText(R.id.text, Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY));
            } else {
                contentView.setTextViewText(R.id.text, Html.fromHtml(s));
            }
        }

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(OnelittleAngelApplication.instance.getResources().getString(R.string.biography_of_the_day))
          .setAutoCancel(true)
          .setColor(Color.WHITE)
          .setContentText(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, ""))
          .setSmallIcon(R.drawable.ic_notification_onelittleangel)
          .setStyle(new NotificationCompat.BigTextStyle().bigText(s));

        builder.setCustomBigContentView(contentView);

        Intent mainIntent = new Intent(this, BiographyActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        mainIntent.putExtra(getResources().getString(R.string.from), settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, "") + " ");
        mainIntent.putExtra("fromNotification", true);
        mainIntent.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
        mainIntent.putExtra("fromActivity", "TableContentsActivity");
        mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
          NOTIFICATION_ID,
          mainIntent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntent);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        builder.setDeleteIntent(BiographyNotificationEventReceiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

}
