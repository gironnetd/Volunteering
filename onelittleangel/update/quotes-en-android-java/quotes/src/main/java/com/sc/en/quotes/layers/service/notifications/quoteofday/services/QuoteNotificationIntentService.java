package com.sc.en.quotes.layers.service.notifications.quoteofday.services;

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
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.text.Html;
import android.widget.RemoteViews;

import com.sc.en.quotes.layers.broadcast_receiver.quoteofday.QuoteNotificationEventReceiver;
import com.sc.en.quotes.layers.dao.quotes.QuotesDaoInterface;
import com.sc.en.quotes.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.quotes.layers.mvp.common.utils.Constants;
import com.sc.en.quotes.layers.mvp.contents.ContentsActivity;
import com.sc.en.quotes.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.quotes.injector.Injector;
import com.sc.en.quotes.OnelittleAngelApplication;
import com.sc.en.quotes.R;
import com.sc.en.quotes.transverse.orms.realm.models.Quote;

import java.util.Calendar;

/**
 * Created by klogi
 *
 *
 */
public class QuoteNotificationIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 3;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";
    private static int i;
    private SharedPreferences settings;
    SharedPreferences.Editor editor;

  public QuoteNotificationIntentService() {
        super(QuoteNotificationIntentService.class.getSimpleName());
        settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    }

    public static Intent createIntentStartNotificationService(Context context) {
        //Log.i("55", "createIntentStartNotificationService");

        Intent intent = new Intent(context, QuoteNotificationIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        //Log.i("55", "createIntentDeleteNotification");

        Intent intent = new Intent(context, QuoteNotificationIntentService.class);
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

                if (settings.getInt(Constants.DAY_OF_MONTH, -1) != i) {
                    //Log.i("55", "settings.getInt(Constants.DAY_OF_MONTH, -1) != i");
//                    settings.edit().putInt(Constants.DAY_OF_MONTH, i).apply();

                    OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadQuoteOfDayAsync(true).subscribe(this::launchNotification);
                }
                else {
                    //Log.i("55", "settings.getInt(Constants.DAY_OF_MONTH, -1) == i");

                    QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
                    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
                    processStartNotification(quotesDaoInterface.findQuoteByIdQuote(settings.getInt(Constants.QUOTE_OF_DAY_ID, -1)));
                }
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void launchNotification(Quote quoteOfDay) {
        //Log.i("55", "launchNotification");
        QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
        settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
        processStartNotification(quotesDaoInterface.findQuoteByIdQuote(settings.getInt(Constants.QUOTE_OF_DAY_ID, -1)));
    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
        //Log.i("55", "processDeleteNotification");
    }

    private void processStartNotification(Quote quote) {
        //Log.i("55", "processStartNotification");

        settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
        settings.edit().putBoolean(Constants.WAS_NOTIFICATION_SENT_ON_DAY, true).apply();
        // Do something. For example, fetch fresh data from backend to create a rich notification?
        i++;

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification_onelittleangel);
      String name = null;
      String type = "";
      if(quote.getAuthor() != null) {
            name = quote.getAuthor().getName();
            type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
        } else {
            name = quote.getBook().getName();
            type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
        }
        String s = "<html><body style=\"text-align:justify;color:gray;background-color:black;\">"
          + quote.getQuote() + "</body></html>";

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.quote_notification);
    //    contentView.setImageViewResource(R.id.image, R.drawable.ic_notification_onelittleangel);
        contentView.setTextViewText(R.id.title, OnelittleAngelApplication.instance.getResources().getString(R.string.quotes_of_the_day));

        contentView.setTextColor(R.id.title, settings.getInt(CardViewNative.DARKERRGB, 0));
        contentView.setTextViewText(R.id.name, name);

        contentView.setTextViewText(R.id.other_quotes,"[ " + getResources().getString(R.string.more_quotes) + " " + name + " ]");
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
        builder.setContentTitle(OnelittleAngelApplication.instance.getResources().getString(R.string.quotes_of_the_day))
                .setAutoCancel(true)
                .setColor(Color.WHITE)
                .setContentText(name)
                .setSmallIcon(R.drawable.ic_notification_onelittleangel)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(quote.getQuote()))
                /*.setLargeIcon(largeIcon)*/;

        builder.setCustomBigContentView(contentView);

        Intent mainIntent = new Intent(this, ContentsActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // mainIntent.setAction(Intent.ACTION_MAIN);
        mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.fromFragment), type);
        mainIntent.putExtra("fromNotification", true);
        mainIntent.putExtra("fromQuoteNotification", true);

        mainIntent.putExtra(getResources().getString(R.string.from), name);

        mainIntent.putExtra(type, name);
       // mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
          NOTIFICATION_ID,
          mainIntent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntent);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        builder.setDeleteIntent(QuoteNotificationEventReceiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

}
