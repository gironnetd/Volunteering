package com.sc.en.quotes.layers.mvp.settings.fragments.notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.sc.en.quotes.OnelittleAngelApplication;
import com.sc.en.quotes.layers.broadcast_receiver.pictureofday.PictureNotificationEventReceiver;
import com.sc.en.quotes.layers.mvp.MotherActivity;
import com.sc.en.quotes.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.en.quotes.layers.mvp.settings.fragments.BaseFragment;
import com.sc.en.quotes.R;
import com.sc.en.quotes.layers.broadcast_receiver.biographyofday.BiographyNotificationEventReceiver;
import com.sc.en.quotes.layers.broadcast_receiver.quoteofday.QuoteNotificationEventReceiver;
import com.sc.en.quotes.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.quotes.layers.mvp.common.utils.Constants;
import com.sc.en.quotes.layers.mvp.settings.SettingsActivity;
//import com.sc.fr.onelittleangel.layers.mvp.settings.fragments.notifications.pickers.time.TimePickerDialog;
import com.sc.en.quotes.layers.mvp.settings.fragments.notifications.picker.time.TimePickerDialog;
import com.sc.en.quotes.layers.mvp.settings.fragments.notifications.recycler.adapter.NotificationsRecyclerAdapter;
import com.sc.en.quotes.layers.mvp.settings.viewpager.ViewPagerNative;
import com.sc.en.quotes.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.quotes.layers.service.notifications.biographyofday.services.BiographyNotificationIntentService;
import com.sc.en.quotes.layers.service.notifications.pictureofday.services.PictureNotificationIntentService;
import com.sc.en.quotes.layers.service.notifications.quoteofday.services.QuoteNotificationIntentService;

import me.leolin.shortcutbadger.ShortcutBadger;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class NotificationsFragment extends BaseFragment implements NotificationsViewInterface, TimePickerDialog.OnTimeSetListener  {

  /***********************************************************
   *  Attributes
   **********************************************************/

  private boolean mHasInflated = false;
  private CardView notificationsContainer;
  public ImageView ivReceiveNotifications;
  private SharedPreferences settings;
  private SharedPreferences.Editor editor;
  private NotificationsRecyclerAdapter adapter;
  private int height ;
  private SoundPool soundPool;

  /***********************************************************
   *  Presenter
   **********************************************************/

  /**
   * The Presenter associated with that view
   */
  NotificationsPresenterInterface presenter=null;

  /**
   *
   * @return the fragment instance
   */
  public static NotificationsFragment newInstance() {
    NotificationsFragment notificationsFragment = new NotificationsFragment();
    Bundle args = new Bundle();
    args.putString(TYPE, "notifications");
    notificationsFragment.setArguments(args);
    return notificationsFragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    type = getArguments().getString(TYPE);
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  public void onStop() {
    super.onStop();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    if (adapter != null) {
      outState.putInt("adapter.idPosition", adapter.idPosition);
      editor.putInt(Constants.SETTINGS_NOTIFICATION_TPD_OPENED_POSITION, adapter.idPosition);
      editor.commit();
    }
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  //  presenter = null;
    //adapter = null;
  //  settings = null;
  //  editor = null;
  //  notificationsContainer = null;

   // flReceiveNotificationsIssue = null;
  //  ivReceiveNotifications = null;
  //  txtReceiveNotifications = null;

    if (soundPool != null) {
      soundPool.release();
      soundPool.setOnLoadCompleteListener(null);
      soundPool = null;
    }

    if (adapter != null) {
      if (adapter.soundPool != null) {
        adapter.soundPool.release();
        adapter.soundPool.setOnLoadCompleteListener(null);
        adapter.soundPool = null;
      }
      adapter.context = null;
    //  adapter.tpd.onDestroy();
    //  adapter.tpd.dismiss();
    //  adapter.tpd = null;
      adapter = null;
    }

//    if (rvNotifications.getAdapter() != null) {
//      if (rvNotifications.getAdapter().soundPool != null) {
//        rvNotifications.adapter.soundPool.release();
//        rvNotifications.adapter.soundPool.setOnLoadCompleteListener(null);
//        rvNotifications.adapter.soundPool = null;
//      }
//      rvNotifications.adapter.context = null;
//      rvNotifications.adapter = null;
//    }
  //  rvNotifications = null;
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  @Override
  protected void onCreateViewAfterViewStubInflated(View inflatedView, Bundle savedInstanceState) {
    mHasInflated = true;
    this.container.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

    if(inflatedView != null) {

      notificationsContainer = (CardView) inflatedView.findViewById(R.id.notifications_card_view);
      ViewHelper.setAlpha(notificationsContainer, 0);

      RecyclerView rvNotifications = (RecyclerView) inflatedView.findViewById(R.id.rv_notifications);
      TextViewNative txtReceiveNotifications = (TextViewNative) inflatedView.findViewById(R.id.txt_receive_notifications);
      ivReceiveNotifications = (ImageView) inflatedView.findViewById(R.id.iv_receive_notifications);
      FrameLayout flReceiveNotificationsIssue = (FrameLayout) inflatedView.findViewById(R.id.fl_receive_notifications_ask);

      rvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
      String[] notificationsName = {getResources().getString(R.string.quotes_of_the_day), getResources().getString(R.string.picture_of_the_day), getResources().getString(R.string.biography_of_the_day)};
      adapter = new NotificationsRecyclerAdapter((MotherActivity)getActivity(),this, notificationsName);
      rvNotifications.setAdapter(adapter);

      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      editor = settings.edit();

      if(settings.getBoolean(Constants.RECEIVE_NOTIFICATION, false)){
        ivReceiveNotifications.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
        ivReceiveNotifications.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));

      } else {
        ivReceiveNotifications.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
        ivReceiveNotifications.setBackgroundColor(Color.TRANSPARENT);
      }

      View.OnTouchListener onTouchListener = (v, event) -> {
        switch(event.getAction()) {
          case MotionEvent.ACTION_DOWN:

            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            int soundID = soundPool.load(getContext(), R.raw.unlock, 1);
            soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> soundPool1.play(soundID, 1f, 1f, 1, 0, 1f));
            if(ivReceiveNotifications.getDrawingCacheBackgroundColor() == settings.getInt(CardViewNative.DARKERRGB, 0)) {
              ivReceiveNotifications.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
              ivReceiveNotifications.setBackgroundColor(Color.TRANSPARENT);
              editor.putBoolean(Constants.RECEIVE_NOTIFICATION, false);

              for(int i = 0; i < adapter.holders.size(); i++) {
                adapter.holders.get(i).notificationsCb.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
                adapter.holders.get(i).notificationsCb.setBackgroundColor(Color.TRANSPARENT);
                adapter.holders.get(i).notificationTime.setVisibility(View.GONE);
              }

              editor.putBoolean(Constants.QUOTE_RECEIVE_NOTIFICATION, false);
              editor.putBoolean(Constants.BIOGRAPHY_RECEIVE_NOTIFICATION, false);
              editor.putBoolean(Constants.PICTURE_RECEIVE_NOTIFICATION, false);
                BiographyNotificationEventReceiver.cancelAlarm(OnelittleAngelApplication.instance.getApplicationContext());
                PictureNotificationEventReceiver.cancelAlarm(OnelittleAngelApplication.instance.getApplicationContext());
                QuoteNotificationEventReceiver.cancelAlarm(OnelittleAngelApplication.instance.getApplicationContext());
              ShortcutBadger.applyCount(OnelittleAngelApplication.instance.getApplicationContext(), 0);
              editor.putInt(Constants.BADGE_COUNT, 0);
              NotificationManager notificationManager = (NotificationManager) OnelittleAngelApplication.instance.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
              notificationManager.cancel(BiographyNotificationIntentService.NOTIFICATION_ID);
              notificationManager.cancel(PictureNotificationIntentService.NOTIFICATION_ID);
              notificationManager.cancel(QuoteNotificationIntentService.NOTIFICATION_ID);
              editor.putBoolean(Constants.QUOTE_IS_NOTIFICATION_SENDED, false);
              editor.putBoolean(Constants.BIOGRAPHY_IS_NOTIFICATION_SENDED, false);
              editor.putBoolean(Constants.PICTURE_IS_NOTIFICATION_SENDED, false);
            } else {
              ivReceiveNotifications.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
              ivReceiveNotifications.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
              editor.putBoolean(Constants.RECEIVE_NOTIFICATION, true);
            }
//editor.commit();
            editor.apply();
        }
        return false;
      };

      flReceiveNotificationsIssue.setOnTouchListener(onTouchListener);
      txtReceiveNotifications.setOnTouchListener(onTouchListener);

      DisplayMetrics displaymetrics = new DisplayMetrics();
      getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
      height = displaymetrics.heightPixels;
      int width = displaymetrics.widthPixels;

      int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
      int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

      ViewPagerNative viewPager = (ViewPagerNative) getActivity().findViewById(R.id.viewpager);

      viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
          LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
          lp.height = (int) (height);
          //lp.topMargin = SettingsActivity.headerHeight;

          viewPager.setLayoutParams(lp);

          LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) notificationsContainer.getLayoutParams();
          layoutParams.topMargin = SettingsActivity.headerHeight;
          notificationsContainer.setLayoutParams(layoutParams);
          notificationsContainer.setVisibility(View.VISIBLE);
          animate(notificationsContainer).alpha(1).setDuration(600).start();

          if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
          } else {
            viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
          }
        }
      });
    }
  }

  @Override
  protected void afterViewStubInflated(View originalViewContainerWithViewStub) {
    super.afterViewStubInflated(originalViewContainerWithViewStub);
    TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
    if(tpd != null) tpd.setOnTimeSetListener(this);
  }

  @Override
  public String getFragmentType() {
    return type;
  }

  @Override
  public boolean getHasInflated() {
    return mHasInflated;
  }

  @Override
  protected int getViewStubLayoutResource() {
    return R.layout.notifications_viewstub;
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.base_fragment;
  }

  @Override
  protected void populateViewForOrientation(LayoutInflater inflater) {
    ViewGroup viewGroup = (ViewGroup) getView();
    viewGroup.removeAllViewsInLayout();
    View view = inflater.inflate(R.layout.viewstub, viewGroup, false);
    viewGroup.addView(view);
    mHasInflated = false;
    getHasInflated();
  }

  @Override
  public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
  }

  /***********************************************************
   *  Implement the ViewInterface
   **********************************************************/
}
