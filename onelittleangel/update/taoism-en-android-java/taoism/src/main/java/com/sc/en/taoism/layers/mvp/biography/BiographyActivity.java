package com.sc.en.taoism.layers.mvp.biography;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.sc.en.taoism.OnelittleAngelApplication;
import com.sc.en.taoism.layers.mvp.biography.viewpagers.adapter.BiographyFragmentPagerAdapter;
import com.sc.en.taoism.layers.mvp.common.customs.circle.Circle;
import com.sc.en.taoism.layers.mvp.common.customs.edittexts.TextWatcher;
import com.sc.en.taoism.layers.mvp.common.customs.spinkits.SpriteFactory;
import com.sc.en.taoism.layers.mvp.favorites.FavoritesActivity;
import com.sc.en.taoism.layers.service.mails.SendMailTask;
import com.sc.en.taoism.layers.service.notifications.biographyofday.services.BiographyNotificationIntentService;
import com.sc.en.taoism.R;
import com.sc.en.taoism.injector.PresenterInjector;
import com.sc.en.taoism.layers.mvp.MotherActivity;
import com.sc.en.taoism.layers.mvp.MotherPresenter;
import com.sc.en.taoism.layers.mvp.biography.fragments.PresentationFragment;
import com.sc.en.taoism.layers.mvp.biography.listgridviews.CardGridArrayAdapter;
import com.sc.en.taoism.layers.mvp.biography.smarttablayout.SmartTabLayout;
import com.sc.en.taoism.layers.mvp.biography.viewpagers.ViewPagerNative;
import com.sc.en.taoism.layers.mvp.common.animations.ActivityAnimator;
import com.sc.en.taoism.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.taoism.layers.mvp.common.customs.imagezoom.ImageViewTouch;
import com.sc.en.taoism.layers.mvp.common.customs.listviews.adapters.MailsAddedRecyclerAdapter;
import com.sc.en.taoism.layers.mvp.common.customs.listviews.adapters.MailsRecyclerAdapter;
import com.sc.en.taoism.layers.mvp.common.customs.listviews.adapters.TypefacesRecyclerAdapter;
import com.sc.en.taoism.layers.mvp.common.customs.scrollviews.ObservableScrollView;
import com.sc.en.taoism.layers.mvp.common.customs.spinkits.SpinKitView;
import com.sc.en.taoism.layers.mvp.common.customs.spinkits.Style;
import com.sc.en.taoism.layers.mvp.common.customs.spinkits.sprite.Sprite;
import com.sc.en.taoism.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.en.taoism.layers.mvp.common.listeners.imageviews.ImageViewListener;
import com.sc.en.taoism.layers.mvp.common.players.SoundPoolPlayer;
import com.sc.en.taoism.layers.mvp.common.utils.Constants;
import com.sc.en.taoism.layers.mvp.contents.ContentsActivity;
import com.sc.en.taoism.layers.mvp.settings.SettingsActivity;
import com.sc.en.taoism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.taoism.transverse.test.EspressoIdlingResource;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import me.leolin.shortcutbadger.ShortcutBadger;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class BiographyActivity extends MotherActivity implements BiographyViewInterface, ObservableScrollView.Callbacks, ImageViewListener.Callbacks, PresentationFragment.Callbacks, TypefacesRecyclerAdapter.Callbacks, ImageViewTouch.CallBacks {
  private static final String TAG = "BiographyActivity";

  private String split = "";

  public BiographyPresenterInterface presenter = null;

  /***********************************************************
   * Attributes
   **********************************************************/

  private SharedPreferences settings;
  private SharedPreferences.Editor editor;

  private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

  public static final String PREFS_NAME = "SharedPreferences";
  private static final int STATE_ONSCREEN = 0;
  private static final int STATE_OFFSCREEN = 1;
  private static final int STATE_RETURNING = 2;
  private View mPlaceholderView;
  private final ScrollSettleHandler mScrollSettleHandler = new ScrollSettleHandler();
  private int mMinRawY;
  private int mState = STATE_ONSCREEN;
  private int mQuickReturnHeight;
  private int mMaxScrollY;
  private int height;
  private int width;
  public ObservableScrollView mObservableScrollView;
  private ImageView ivAbTitle, ivBackToHomePage;
  private TextViewNative quickReturnTitle;
  private int mQuickReturnWidthForTitlePage;
  private ViewPagerNative viewPager;
  private SmartTabLayout viewPagerTab;
  private boolean isInit = false;
  public static Dialog dBiographyLoading;
  public int position = -1;
  private ImageView fabSocialNetworks, fabTypefaces, fabMicrophone, fabTxtIncrease, fabTxtDecrease;
  private Animation fab_open, fab_close, alpha_in, alpha_out, rlnumber_alpha_in, rlnumber_alpha_out;
  private CardView containerfab, cvGoToTypefaceSettings, cvBiographyDialog, cvExpandedImage;
  public Boolean isFabOpen = false;
  public Dialog typeFacesDialog;
  private Dialog confirmTypefaceChoiceDialog;
  private Dialog goToTypefaceSettingsDialog;
  private Dialog accountsDialog;
  public Dialog mailsDialog;
  private Dialog picturesLoadingDialog;
  public static Dialog wallPapersDialog, microphonesDialog;
  private RecyclerView typeFacesDialogListView, mailsDialogListview, mailsAddedDialogListview;
  private ImageView ivMicrophonePrevious;
  private ImageView ivMicrophoneStop;
  private ImageView ivMicrophonePause;
  private ImageView ivMicrophonePlay;
  private ImageView ivMicrophoneNext;
  private ImageView ivHomeScreen;
  private ImageView ivLockScreen;
  private ImageView ivHomeLockScreen;
  private ImageView ivAddEmail;
  private ImageView ivExpandedPictures;
  private ImageView ivQuoteLeft;
  private ImageView ivQuoteRight;
  private TextView confirmTypefaceChoice;
  private TextView cancelTypefaceChoice;
  private TextView confirmGoToAccount;
  private TextView cancelGoToAccount;
  private TextView goToTypefaceSettings;
  private TextViewNative txtAuthorBookName, txtGoToAccount, sendMails, txtGoToTypefaceSettings, aNative;
  private LinearLayout llLockScreen, llHomeLockScreen;
  private MailsAddedRecyclerAdapter mailsAddedRecyclerAdapter;
  private AppCompatEditText etMailComment, inputEmail;
  private TextInputLayout inputLayoutEmail;
  private Typeface typeface;
  private String tpString;
  private static TextToSpeech textToSpeech;
  private boolean isOnPause = false;
  private String destFileName;
  private HashMap<String, String> myHashRender;
  private String utteranceID;
  public static boolean isForMicrophone = false;

  private LinearLayout headerContainer;
  public static int headerHeight = 0;
  private static ArrayMap<String, SoundPoolPlayer> soundPoolPlayers;
  public int soundID;
  public boolean plays = false;

  public boolean isAuthor = false;
  private int chunkPosition = 0;
  private static String utterancePosition;

  private Handler handler;
  private List<String> chunks;

  private int idPosition = 0;
  private final boolean isGvCarrouselIsOpened = false;
  public static boolean isOrientationChanged = false;
  private DisplayMetrics displaymetrics;
  private LinearLayout llHeader;
  private LinearLayout rlNumber;
  private int mObservableHeight;
  private List<String> ttsFileStrings;
  private TypefacesRecyclerAdapter typefacesRecyclerAdapter;
  private MailsRecyclerAdapter mailsRecyclerAdapter;
  private SoundPool soundPool;
  public boolean isExpandedImageDipslaying;
  public boolean isImageCommentDisplaying;
  public boolean isTypefaceChanged = false;
  public boolean isTypefaceDialogShowing = false;
  public boolean isMailDialogShowing = false;
  public boolean isMicrophoneDialogShowing = false;
  public boolean isMicrophoneNextOrPrevious = false;
  public boolean isFromMailOrTypefaceSetings = false;

  private CardView cvImageComment;
  private String type = "";
  private String name = "";

  @Override
  protected void onDestroy() {
    if (isTypefaceDialogShowing) typeFacesDialog.cancel();
    if (isMailDialogShowing) mailsDialog.cancel();
    if (isMicrophoneDialogShowing) microphonesDialog.cancel();

    if (!isOrientationChanged) {

      if (textToSpeech != null) {
        textToSpeech.stop();
        textToSpeech.shutdown();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
          textToSpeech.setOnUtteranceProgressListener(null);
          textToSpeech.setOnUtteranceCompletedListener(null);

        } else {
          textToSpeech.setOnUtteranceCompletedListener(null);
        }
        textToSpeech = null;
      }

      if (soundPoolPlayers != null) {


        for (SoundPoolPlayer player : soundPoolPlayers.values()) {

          if (player != null) {
            player.release();
            player.context = null;
            player.setOnCompletionListener(null);
          }
        }
        soundPoolPlayers.clear();

        for (int i = 0; i < soundPoolPlayers.size(); i++) {
        }

        soundPoolPlayers = null;
      }

      if (soundPool != null) {
        soundPool.release();
        soundPool.setOnLoadCompleteListener(null);

        soundPool = null;
      }

      if (viewPager.getAdapter().fragments.size() != 0)
        viewPager.getAdapter().fragments.removeAll(viewPager.getAdapter().fragments);

      for (int i = 0; i < viewPager.getAdapter().fragments.size(); i++) {

      }
      //microphonesDialog = wallPapersDialog = null;

      if (viewPagerTab != null) {
        if (viewPagerTab.soundPool != null && viewPagerTab.soundPool.get() != null) {
          viewPagerTab.soundPool.get().release();
          viewPagerTab.soundPool.get().setOnLoadCompleteListener(null);
          viewPagerTab.soundPool = null;
        }
        viewPagerTab = null;
      }
      viewPagerTab = null;

      typeface = null;
      tpString = null;
      textToSpeech = null;
      destFileName = null;
      myHashRender = null;
      utteranceID = null;
      headerContainer = null;
      txtAuthorBookName = txtGoToAccount = sendMails = txtGoToTypefaceSettings = aNative = null;
      confirmTypefaceChoice = cancelTypefaceChoice = confirmGoToAccount = cancelGoToAccount = goToTypefaceSettings = null;
      ivMicrophonePrevious = ivMicrophoneStop = ivMicrophonePause = ivMicrophonePlay = ivMicrophoneNext = ivHomeScreen = ivLockScreen = ivHomeLockScreen = ivAddEmail = null;
      typeFacesDialogListView = mailsDialogListview = mailsAddedDialogListview = null;
      Dialog socialNetworksDialog = typeFacesDialog = confirmTypefaceChoiceDialog = goToTypefaceSettingsDialog = accountsDialog = mailsDialog = picturesLoadingDialog = null;
      containerfab = cvGoToTypefaceSettings = cvBiographyDialog = null;
      fab_open = fab_close = alpha_in = alpha_out = null;
      fabSocialNetworks = fabTypefaces = fabMicrophone = fabTxtIncrease = fabTxtDecrease = null;
      llLockScreen = llHomeLockScreen = null;
      etMailComment = inputEmail = null;

      if (mailsAddedRecyclerAdapter != null) {
        if (mailsAddedRecyclerAdapter.soundPool != null) {
          mailsAddedRecyclerAdapter.soundPool.get().release();
          mailsAddedRecyclerAdapter.soundPool.get().setOnLoadCompleteListener(null);
          mailsAddedRecyclerAdapter.soundPool = null;
        }
        mailsAddedRecyclerAdapter = null;
      }
      if (typefacesRecyclerAdapter != null) {
        if (typefacesRecyclerAdapter.soundPool != null) {
          typefacesRecyclerAdapter.soundPool.get().release();
          typefacesRecyclerAdapter.soundPool.get().setOnLoadCompleteListener(null);
          typefacesRecyclerAdapter.soundPool = null;
        }
        typefacesRecyclerAdapter = null;
      }
      if (mailsRecyclerAdapter != null) {
        if (mailsRecyclerAdapter.soundPool != null) {
          mailsRecyclerAdapter.soundPool.get().release();
          mailsRecyclerAdapter.soundPool.get().setOnLoadCompleteListener(null);
          mailsRecyclerAdapter.soundPool = null;
        }
        mailsRecyclerAdapter = null;
      }

      ViewPager.OnPageChangeListener listener = null;
      quickReturnTitle = null;
      ImageView ivHeaderBackground = ivAbTitle = null;
      //mObservableScrollView = null;
      mPlaceholderView = null;
      inputLayoutEmail = null;
      if (handler != null) {
        handler = null;
      }
      headerHeight = 0;

      if (textToSpeech != null) {
        textToSpeech.setOnUtteranceCompletedListener(null);
        textToSpeech.shutdown();
        textToSpeech = null;
      }
    }
    super.onDestroy();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {

    if (viewPager != null) outState.putInt("idPosition", viewPager.getCurrentItem());
    if (isFabOpen) outState.putBoolean("isFabOpen", true);
    else outState.putBoolean("isFabOpen", false);

    outState.putString("type", type);
    outState.putString("name", name);

    if (cvImageComment != null && cvImageComment.getVisibility() == View.VISIBLE)
      outState.putBoolean("isImageCommentDisplaying", true);
    else outState.putBoolean("isImageCommentDisplaying", false);

    if (ivExpandedPictures != null && ivExpandedPictures.getVisibility() == View.VISIBLE)
      outState.putBoolean("isExpandedImageDipslaying", true);
    else outState.putBoolean("isExpandedImageDipslaying", false);

    outState.putBoolean("isTypefaceDialogShowing", isTypefaceDialogShowing);
    outState.putBoolean("isMailDialogShowing", isMailDialogShowing);
    outState.putBoolean("isMicrophoneDialogShowing", isMicrophoneDialogShowing);
    outState.putBoolean("plays", plays);
    outState.putBoolean("isOnPause", isOnPause);
    outState.putBoolean("isFromMailOrTypefaceSetings", isFromMailOrTypefaceSetings);

    if (ttsFileStrings != null) outState.putStringArrayList("ttsFileStrings", (ArrayList<String>) ttsFileStrings);
    if (chunks != null) outState.putStringArrayList("chunks", (ArrayList<String>) chunks);

    isOrientationChanged = true;
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState != null) {
      idPosition = savedInstanceState.getInt("idPosition");
      isFabOpen = savedInstanceState.getBoolean("isFabOpen");
      isExpandedImageDipslaying = savedInstanceState.getBoolean("isExpandedImageDipslaying");
      isImageCommentDisplaying = savedInstanceState.getBoolean("isImageCommentDisplaying");
      type = savedInstanceState.getString("type");
      name = savedInstanceState.getString("name");
      isTypefaceDialogShowing = savedInstanceState.getBoolean("isTypefaceDialogShowing");
      isMailDialogShowing = savedInstanceState.getBoolean("isMailDialogShowing");
      isMicrophoneDialogShowing = savedInstanceState.getBoolean("isMicrophoneDialogShowing");
      plays = savedInstanceState.getBoolean("plays");
      isOnPause = savedInstanceState.getBoolean("isOnPause");
      ttsFileStrings = savedInstanceState.getStringArrayList("ttsFileStrings");
      chunks = savedInstanceState.getStringArrayList("chunks");
      isOrientationChanged = true;
    } else {
      isOrientationChanged = false;
    }

    if (getIntent().hasExtra("isFromMailOrTypefaceSetings"))
      isFromMailOrTypefaceSetings = getIntent().getBooleanExtra("isFromMailOrTypefaceSetings", false);

    setContentView(R.layout.activity_biography);
    if (presenter == null) {
      presenter = PresenterInjector.getBiographyPresenter(this);
    }

    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    editor = settings.edit();

    if (getIntent().getBooleanExtra("fromNotification", false)) {
      int badgecount = settings.getInt(Constants.BADGE_COUNT, 0);
      ShortcutBadger.applyCount(OnelittleAngelApplication.instance.getApplicationContext(), --badgecount);
      getIntent().removeExtra("fromNotification");
      editor.putInt(Constants.BADGE_COUNT, badgecount);
      editor.putBoolean(Constants.BIOGRAPHY_IS_NOTIFICATION_SENDED, false);
      editor.apply();
    }

    cvImageComment = (CardView) findViewById(R.id.cv_image_comment);

    mObservableScrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
    mObservableScrollView.setCallbacks(this);
    mObservableScrollView.setVerticalScrollBarEnabled(true);
    mObservableScrollView.setHorizontalScrollBarEnabled(false);
    mPlaceholderView = findViewById(R.id.place_holder);
    llHeader = (LinearLayout) findViewById(R.id.ll_header);
    rlNumber = (LinearLayout) findViewById(R.id.rl_number);

    ivAbTitle = (ImageView) findViewById(R.id.iv_back);

    ImageViewListener ivAbHomeListener = new ImageViewListener(ivAbTitle, 1.3f);
    ivAbHomeListener.setCallbacks(this);
    ivAbTitle.setOnTouchListener(ivAbHomeListener);

    ivBackToHomePage = (ImageView) findViewById(R.id.iv_home);

    ImageViewListener ivBackToHomePageListener = new ImageViewListener(ivBackToHomePage, 1.3f);
    ivBackToHomePageListener.setCallbacks(this);
    ivBackToHomePage.setOnTouchListener(ivBackToHomePageListener);

    displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    height = displaymetrics.heightPixels;
    width = displaymetrics.widthPixels;

    int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

    quickReturnTitle = (TextViewNative) findViewById(R.id.title_biography);
    quickReturnTitle.setText(String.format("%s%s", getIntent().getStringExtra(getString(R.string.from)), getResources().getString(R.string.space)));

    String[] split = quickReturnTitle.getText().toString().trim().split(" ");
    String titleString = "";
    quickReturnTitle.setText(null);

    double rate = 2;

    if (ivQuoteLeft == null) {
      ivQuoteLeft = (ImageView) this.findViewById(R.id.quote_left);
    }

    int widthMeasureSpec1 = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
    int heightMeasureSpec1 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    ivQuoteLeft.measure(widthMeasureSpec1, heightMeasureSpec1);
    mQuickReturnWidthForTitlePage = ivQuoteLeft.getMeasuredWidth();

    for (String aSplit : split) {
      titleString += aSplit;
      quickReturnTitle.setText(titleString);
      quickReturnTitle.measure(widthMeasureSpec1, heightMeasureSpec1);

      if (quickReturnTitle.getMeasuredWidth() == width - mQuickReturnWidthForTitlePage * rate) {
        titleString += "\n";
        if (rate == 2) rate = 1.5;
        if (rate == 1.5) rate = 0.5;
      } else if (quickReturnTitle.getMeasuredWidth() > width - mQuickReturnWidthForTitlePage * rate) {
        titleString = titleString.replace(aSplit, "\n" + aSplit);
        if (rate == 2) rate = 1.5;
        if (rate == 1.5) rate = 0.5;
      }
      titleString += " ";
    }

    quickReturnTitle.setText(titleString);
    quickReturnTitle.measure(widthMeasureSpec1, heightMeasureSpec1);
    rlNumber.measure(widthMeasureSpec1, heightMeasureSpec1);

    LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) quickReturnTitle.getLayoutParams();
    layoutParams1.width = (int) (width - mQuickReturnWidthForTitlePage * rate);

    quickReturnTitle.setLayoutParams(layoutParams1);
    quickReturnTitle.setGravity(Gravity.CENTER_HORIZONTAL);
    quickReturnTitle.measure(widthMeasureSpec1, heightMeasureSpec1);

    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
    params.topMargin = (int) (quickReturnTitle.getMeasuredHeight() + layoutParams1.topMargin + getResources().getDimension(R.dimen.activity_horizontal_margin));
    rlNumber.setLayoutParams(params);
    FrameLayout flImage = (FrameLayout) findViewById(R.id.fl_image);
    viewPager = (ViewPagerNative) findViewById(R.id.viewpager);

    ViewGroup tab = (ViewGroup) findViewById(R.id.tab);
    tab.addView(LayoutInflater.from(this).inflate(R.layout.smarttab_biography_layout, tab, false));

    viewPagerTab = (SmartTabLayout) findViewById(R.id.view_pager_tab);

    dBiographyLoading = new Dialog(BiographyActivity.this, R.style.myDialogSlideUpAndDown);
    dBiographyLoading.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    dBiographyLoading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    dBiographyLoading = new Dialog(BiographyActivity.this, R.style.myDialogSlideUpAndDown);
    dBiographyLoading.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    dBiographyLoading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    dBiographyLoading.setContentView(R.layout.biography_dialog);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      SpinKitView spinKitView = (SpinKitView) dBiographyLoading.findViewById(R.id.spin_kit);
      Style style = Style.FADING_CIRCLE;
      Sprite drawable = SpriteFactory.create(style);
      spinKitView.setIndeterminateDrawable(drawable);
      spinKitView.setVisibility(View.VISIBLE);
      cvBiographyDialog = (CardView) dBiographyLoading.findViewById(R.id.cv_biography_dialog);
      cvBiographyDialog.setVisibility(View.GONE);
    }

    headerContainer = (LinearLayout) findViewById(R.id.container);

    mObservableScrollView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
      onScrollChanged(mObservableScrollView.getScrollY());
      mMaxScrollY = mObservableScrollView.computeVerticalScrollRange()
              - mObservableScrollView.getHeight();
      mQuickReturnHeight = rlNumber.getHeight() + 90;
      mObservableHeight = rlNumber.getBottom();
      mQuickReturnWidthForTitlePage = ivQuoteLeft.getMeasuredWidth();

      DisplayMetrics displaymetrics1 = new DisplayMetrics();

      if (Build.VERSION.SDK_INT >= 17) {
        //new pleasant way to get real metrics
        getWindowManager().getDefaultDisplay().getRealMetrics(displaymetrics1);

        width = displaymetrics1.widthPixels;
        height = displaymetrics1.heightPixels;

      } else if (Build.VERSION.SDK_INT >= 14) {
        //reflection for this weird in-between time
        try {
          Method mGetRawH = Display.class.getMethod("getRawHeight");
          Method mGetRawW = Display.class.getMethod("getRawWidth");
          width = (Integer) mGetRawW.invoke(displaymetrics1);
          height = (Integer) mGetRawH.invoke(displaymetrics1);
        } catch (Exception e) {
          //this may not be 100% accurate, but it's all we've got
          //  realWidth = display.getWidth();
          width = getWindowManager().getDefaultDisplay().getWidth();
          height = getWindowManager().getDefaultDisplay().getHeight();
        }

      } else {
        //This should be close, as lower API devices should not have window navigation bars
        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();
      }

      headerHeight = rlNumber.getBottom();

      if (viewPager != null) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
        lp.topMargin = 0;
        viewPager.setLayoutParams(lp);
      }
    });

    ivQuoteLeft = (ImageView) findViewById(R.id.quote_left);
    ivQuoteRight = (ImageView) findViewById(R.id.quote_right);

    mObservableScrollView.smoothScrollTo(0, 0);

    containerfab = (CardView) findViewById(R.id.fab_layout);

    fabTxtIncrease = (ImageView) findViewById(R.id.fab_txt_increase);
    ImageViewListener fabTxtIncreaseListener = new ImageViewListener(fabTxtIncrease, 1.3f);
    fabTxtIncreaseListener.setCallbacks(this);
    fabTxtIncrease.setOnTouchListener(fabTxtIncreaseListener);
    fabTxtDecrease = (ImageView) findViewById(R.id.fab_txt_decrease);
    ImageViewListener fabTxtDecreaseListener = new ImageViewListener(fabTxtDecrease, 1.3f);
    fabTxtDecreaseListener.setCallbacks(this);
    fabTxtDecrease.setOnTouchListener(fabTxtDecreaseListener);

    fabSocialNetworks = (ImageView) findViewById(R.id.fab_social_networks);

    ImageViewListener fabSocialNetworksListener = new ImageViewListener(fabSocialNetworks, 1.3f);
    fabSocialNetworksListener.setCallbacks(this);
    fabSocialNetworks.setOnTouchListener(fabSocialNetworksListener);
    fabTypefaces = (ImageView) findViewById(R.id.fab_type_faces);
    ImageViewListener fabTypefacesListener = new ImageViewListener(fabTypefaces, 1.3f);
    fabTypefacesListener.setCallbacks(this);
    fabTypefaces.setOnTouchListener(fabTypefacesListener);
    fabMicrophone = (ImageView) findViewById(R.id.fab_microphone);
    ImageViewListener fabMicrophonesListener = new ImageViewListener(fabMicrophone, 1.3f);
    fabMicrophonesListener.setCallbacks(this);
    fabMicrophone.setOnTouchListener(fabMicrophonesListener);

    fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
    fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
    alpha_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_in);
    alpha_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_out);
    rlnumber_alpha_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rlnumber_alpha_in);
    rlnumber_alpha_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rlnumber_alpha_out);

    fab_close.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {
      }

      @Override
      public void onAnimationEnd(Animation animation) {
        fabTxtIncrease.clearAnimation();

        fabTxtIncrease.setVisibility(View.GONE);
        fabTxtDecrease.clearAnimation();
        fabTxtDecrease.setVisibility(View.GONE);
        fabSocialNetworks.clearAnimation();
        fabSocialNetworks.setVisibility(View.GONE);
        fabTypefaces.clearAnimation();
        fabTypefaces.setVisibility(View.GONE);
        fabMicrophone.clearAnimation();
        fabMicrophone.setVisibility(View.GONE);
        containerfab.clearAnimation();

        containerfab.setVisibility(View.GONE);
      }

      @Override
      public void onAnimationRepeat(Animation animation) {
      }
    });

    if (isFabOpen) {
      containerfab.setVisibility(View.VISIBLE);
      containerfab.startAnimation(alpha_in);
      fabTxtIncrease.setVisibility(View.VISIBLE);
      fabTxtIncrease.startAnimation(fab_open);
      fabTxtDecrease.setVisibility(View.VISIBLE);
      fabTxtDecrease.startAnimation(fab_open);
      fabSocialNetworks.setVisibility(View.VISIBLE);
      fabSocialNetworks.startAnimation(fab_open);
      fabTypefaces.setVisibility(View.VISIBLE);
      fabTypefaces.startAnimation(fab_open);
      fabMicrophone.setVisibility(View.VISIBLE);
      fabMicrophone.startAnimation(fab_open);
    }

    accountsDialog = new Dialog(BiographyActivity.this, R.style.myDialogSlideUpAndDown);
    accountsDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    accountsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    accountsDialog.setContentView(R.layout.accounts_dialog);

    accountsDialog.setOnDismissListener(dialog -> {
      animate(viewPager).setDuration(800).alpha(1).start();
      animate(viewPagerTab).alpha(1).setDuration(800).start();
    });

    txtGoToAccount = (TextViewNative) accountsDialog.findViewById(R.id.txt_accounts_ask);

    confirmGoToAccount = (TextView) accountsDialog.findViewById(R.id.confirm_go_to_account);
    confirmGoToAccount.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    cancelGoToAccount = (TextView) accountsDialog.findViewById(R.id.cancel_go_to_account);
    cancelGoToAccount.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    ImageViewListener cancelGoToAccountListener = new ImageViewListener(cancelGoToAccount, 1.3f);
    cancelGoToAccountListener.setCallbacks(this);
    cancelGoToAccount.setOnTouchListener(cancelGoToAccountListener);

    goToTypefaceSettingsDialog = new Dialog(BiographyActivity.this, R.style.myDialogSlideUpAndDown);
    goToTypefaceSettingsDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    goToTypefaceSettingsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    goToTypefaceSettingsDialog.setContentView(R.layout.typefaces_choice_dialog);

    goToTypefaceSettingsDialog.setOnDismissListener(dialog -> {
      animate(viewPager).setDuration(800).alpha(1).start();
      animate(viewPagerTab).alpha(1).setDuration(800).start();
    });

    goToTypefaceSettings = (TextView) goToTypefaceSettingsDialog.findViewById(R.id.go_to_typeface_settings);
    goToTypefaceSettings.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    ImageViewListener goToTypefaceSettingsListener = new ImageViewListener(goToTypefaceSettings, 1.05f);
    goToTypefaceSettingsListener.setCallbacks(this);
    goToTypefaceSettings.setOnTouchListener(goToTypefaceSettingsListener);

    typeFacesDialog = new Dialog(BiographyActivity.this, R.style.myDialogSlideUpAndDown);
    typeFacesDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    typeFacesDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    typeFacesDialog.setContentView(R.layout.typefaces_dialog);

    typeFacesDialog.setOnDismissListener(dialog -> {
      animate(viewPager).setDuration(800).alpha(1).start();
      rlNumber.startAnimation(rlnumber_alpha_in);
    });

    typeFacesDialogListView = (RecyclerView) typeFacesDialog.findViewById(R.id.typefaces_dialog_list_view);

    typeFacesDialogListView.setLayoutManager(new LinearLayoutManager(this));

    confirmTypefaceChoiceDialog = new Dialog(BiographyActivity.this, R.style.myDialogSlideUpAndDown);
    confirmTypefaceChoiceDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    confirmTypefaceChoiceDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    confirmTypefaceChoiceDialog.setContentView(R.layout.typefaces_dialog_confirm);

    confirmTypefaceChoice = (TextView) confirmTypefaceChoiceDialog.findViewById(R.id.confirm_typeface_choice);
    confirmTypefaceChoice.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    ImageViewListener confirmTypeChoiceListener = new ImageViewListener(confirmTypefaceChoice, 1.05f);
    confirmTypeChoiceListener.setCallbacks(this);
    confirmTypefaceChoice.setOnTouchListener(confirmTypeChoiceListener);

    cancelTypefaceChoice = (TextView) confirmTypefaceChoiceDialog.findViewById(R.id.cancel_typeface_choice);
    cancelTypefaceChoice.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    ImageViewListener cancelTypeChoiceListener = new ImageViewListener(cancelTypefaceChoice, 1.05f);
    cancelTypeChoiceListener.setCallbacks(this);
    cancelTypefaceChoice.setOnTouchListener(cancelTypeChoiceListener);

    typeFacesDialogListView.measure(widthMeasureSpec, heightMeasureSpec);

    ScrollView scrollView = (ScrollView) typeFacesDialog.findViewById(R.id.fonts_dialog_scroll_view);
    scrollView.smoothScrollTo(0, 0);

    cvGoToTypefaceSettings = (CardView) typeFacesDialog.findViewById(R.id.cv_go_to_typefaces_settings);

    txtGoToTypefaceSettings = (TextViewNative) typeFacesDialog.findViewById(R.id.txtn_go_to_type_face_settings);

    txtGoToTypefaceSettings.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    txtGoToTypefaceSettings.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            animate(cvGoToTypefaceSettings).setDuration(100).scaleX(1.12f).scaleY(1.12f);
            animate(txtGoToTypefaceSettings).setDuration(100).scaleX(1.05f).scaleY(1.05f);
            return true;
          case MotionEvent.ACTION_MOVE:
            return true;
          case MotionEvent.ACTION_UP:
            animate(txtGoToTypefaceSettings).setDuration(100).scaleX(1).scaleY(1)
                    .setListener(new AnimatorListenerAdapter() {
                      /**
                       * {@inheritDoc}
                       *
                       * @param animation
                       */
                      @Override
                      public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        gotToTypefaceSettings();
                      }
                    });
            animate(cvGoToTypefaceSettings).setDuration(100).scaleX(1).scaleY(1)
                    .setListener(new AnimatorListenerAdapter() {
                      /**
                       * {@inheritDoc}
                       *
                       * @param animation
                       */
                      @Override
                      public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        gotToTypefaceSettings();
                      }
                    });
            return false;
          default:
            animate(txtGoToTypefaceSettings).setDuration(100).scaleX(1).scaleY(1);
            animate(cvGoToTypefaceSettings).setDuration(100).scaleX(1).scaleY(1);
            return false;
        }
      }
    });

    microphonesDialog = new Dialog(BiographyActivity.this, R.style.myDialogSlideUpAndDown);

    microphonesDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    microphonesDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    microphonesDialog.setContentView(R.layout.microphones_dialog);

    ivMicrophonePrevious = (ImageView) microphonesDialog.findViewById(R.id.ic_microphone_previous);
    ImageViewListener ivMicrophonePreviousListener = new ImageViewListener(ivMicrophonePrevious, 1.3f);
    ivMicrophonePreviousListener.setCallbacks(this);
    ivMicrophonePrevious.setOnTouchListener(ivMicrophonePreviousListener);
    ivMicrophonePrevious.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    ivMicrophoneStop = (ImageView) microphonesDialog.findViewById(R.id.ic_microphone_stop);
    ImageViewListener ivMicrophoneStopListener = new ImageViewListener(ivMicrophoneStop, 1.3f);
    ivMicrophoneStopListener.setCallbacks(this);
    ivMicrophoneStop.setOnTouchListener(ivMicrophoneStopListener);
    ivMicrophoneStop.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    ivMicrophonePause = (ImageView) microphonesDialog.findViewById(R.id.ic_microphone_pause);
    ImageViewListener ivMicrophonePauseListener = new ImageViewListener(ivMicrophonePause, 1.3f);
    ivMicrophonePauseListener.setCallbacks(this);
    ivMicrophonePause.setOnTouchListener(ivMicrophonePauseListener);
    ivMicrophonePause.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    ivMicrophonePlay = (ImageView) microphonesDialog.findViewById(R.id.ic_microphone_play);
    ImageViewListener ivMicrophonePlayListener = new ImageViewListener(ivMicrophonePlay, 1.3f);
    ivMicrophonePlayListener.setCallbacks(this);
    ivMicrophonePlay.setOnTouchListener(ivMicrophonePlayListener);
    ivMicrophonePlay.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    ivMicrophoneNext = (ImageView) microphonesDialog.findViewById(R.id.ic_microphone_next);
    ImageViewListener ivMicrophoneNextListener = new ImageViewListener(ivMicrophoneNext, 1.3f);
    ivMicrophoneNextListener.setCallbacks(this);
    ivMicrophoneNext.setOnTouchListener(ivMicrophoneNextListener);
    ivMicrophoneNext.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    microphonesDialog.setOnDismissListener(dialog -> isMicrophoneDialogShowing = false);

    ivExpandedPictures = (ImageView) findViewById(R.id.expanded_image);

    mailsDialog = new Dialog(BiographyActivity.this, R.style.myDialogSlideUpAndDown);
    mailsDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    mailsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    mailsDialog.setContentView(R.layout.mails_dialog);

    mailsDialog.setOnDismissListener(dialog -> {
      animate(viewPager).setDuration(800).alpha(1).start();
      rlNumber.startAnimation(rlnumber_alpha_in);
    });

    sendMails = (TextViewNative) mailsDialog.findViewById(R.id.send_mails);
    ImageViewListener sendMailsListener = new ImageViewListener(sendMails, 1.3f);
    sendMailsListener.setCallbacks(this);
    sendMails.setOnTouchListener(sendMailsListener);

    sendMails.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    mailsDialogListview = (RecyclerView) mailsDialog.findViewById(R.id.mails_dialog_list_view);
    mailsDialogListview.setLayoutManager(new LinearLayoutManager(this));

    mailsAddedDialogListview = (RecyclerView) mailsDialog.findViewById(R.id.mails_added_dialog_list_view);

    mailsAddedDialogListview.setLayoutManager(new LinearLayoutManager(this));
    mailsAddedDialogListview.setNestedScrollingEnabled(false);

    String[] strings = settings.getString(Constants.MAILS_ADDED_LIST, "").split(";");

    ArrayMap<String, String> arrayMap = new ArrayMap<>();

    if (strings.length != 0) {
      for (String string : strings) {
        if (!string.isEmpty())
          arrayMap.put(string, string);
      }
      mailsAddedRecyclerAdapter = new MailsAddedRecyclerAdapter(getApplicationContext(), arrayMap);
    } else {
      mailsAddedRecyclerAdapter = new MailsAddedRecyclerAdapter(getApplicationContext(), new ArrayMap<>());
    }

    mailsAddedDialogListview.setAdapter(mailsAddedRecyclerAdapter);

    etMailComment = (AppCompatEditText) mailsDialog.findViewById(R.id.et_mail_comment);

    int colors[] = {0xfffff, 0xffffff, 0xfffff};

    GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
    gradientDrawable.setStroke(getResources().getInteger(R.integer.mail_comment_border_width), settings.getInt(CardViewNative.DARKERRGB, 0));
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      etMailComment.setBackground(gradientDrawable);
    } else {
      etMailComment.setBackgroundDrawable(gradientDrawable);
    }

    ColorStateList colorStateList1 = ColorStateList.valueOf(settings.getInt(CardViewNative.DARKERRGB, 0));

    inputLayoutEmail = (TextInputLayout) mailsDialog.findViewById(R.id.input_layout_email);
    inputEmail = (AppCompatEditText) mailsDialog.findViewById(R.id.input_email);
    inputEmail.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    inputEmail.setHighlightColor(Color.TRANSPARENT);
    inputEmail.setHintTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    setCursorColor(inputEmail, settings.getInt(CardViewNative.DARKERRGB, 0));

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      inputEmail.setBackgroundTintList(colorStateList1);
    } else {
      //inputEmail.setSupportBackgroundTintList(colorStateList1);
    }
    inputEmail.addTextChangedListener(new TextWatcher(this, inputLayoutEmail, inputEmail));
    setTypefaceToInputLayout(inputLayoutEmail);
    setErrorTextColor(inputLayoutEmail);

    etMailComment.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    etMailComment.setHighlightColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    etMailComment.setHintTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    setCursorColor(etMailComment, settings.getInt(CardViewNative.DARKERRGB, 0));

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      etMailComment.setBackgroundTintList(colorStateList1);
    }

    mailsDialog.setOnDismissListener(dialog -> {
      editor.putString(Constants.MAILS_LIST_TO_SEND, "");
      editor.commit();
      isMailDialogShowing = false;

      if (mailsAddedRecyclerAdapter != null) {
        for (int i = 0; i < mailsAddedRecyclerAdapter.holders.size(); i++) {
          mailsAddedRecyclerAdapter.holders.get(i).fontCb.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
          mailsAddedRecyclerAdapter.holders.get(i).fontCb.setBackgroundColor(Color.TRANSPARENT);
        }
      }
    });

    ivAddEmail = (ImageView) mailsDialog.findViewById(R.id.iv_add_email);
    ImageViewListener ivAddEmailListener = new ImageViewListener(ivAddEmail, 1.3f);
    ivAddEmailListener.setCallbacks(this);
    ivAddEmail.setOnTouchListener(ivAddEmailListener);

    wallPapersDialog = new Dialog(BiographyActivity.this, R.style.myDialogSlideUpAndDown);
    wallPapersDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    wallPapersDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    wallPapersDialog.setContentView(R.layout.dialog_wallpaper_biography);

    ivHomeScreen = (ImageView) wallPapersDialog.findViewById(R.id.iv_home_screen);
    ImageViewListener ivHomeScreenListener = new ImageViewListener(ivHomeScreen, 1.3f);
    ivHomeScreenListener.setCallbacks(this);
    ivHomeScreen.setOnTouchListener(ivHomeScreenListener);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      ivLockScreen = (ImageView) wallPapersDialog.findViewById(R.id.iv_lock_screen);
      ImageViewListener ivLockScreenListener = new ImageViewListener(ivLockScreen, 1.3f);
      ivLockScreenListener.setCallbacks(this);
      ivLockScreen.setOnTouchListener(ivLockScreenListener);

      ivHomeLockScreen = (ImageView) wallPapersDialog.findViewById(R.id.iv_home_lock_screen);
      ImageViewListener ivHomeLockScreenListener = new ImageViewListener(ivHomeLockScreen, 1.3f);
      ivHomeLockScreenListener.setCallbacks(this);
      ivHomeLockScreen.setOnTouchListener(ivHomeLockScreenListener);
    } else {
      llLockScreen = (LinearLayout) wallPapersDialog.findViewById(R.id.ll_lock_screen);
      llLockScreen.setVisibility(View.GONE);
      llHomeLockScreen = (LinearLayout) wallPapersDialog.findViewById(R.id.ll_home_lock_screen);
      llHomeLockScreen.setVisibility(View.GONE);
    }

    Circle wallpapersAddCircle = (Circle) findViewById(R.id.wallpapers_add_circle);

    wallpapersAddCircle.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:

            if (!wallPapersDialog.isShowing()) {
              animate(wallpapersAddCircle).scaleX(1.65f).scaleY(1.65f).setDuration(200).start();
            } else {
              animate(wallpapersAddCircle).scaleX(1).scaleY(1).setDuration(200).start();
            }

            return true;
          case MotionEvent.ACTION_MOVE:
            return true;
          case MotionEvent.ACTION_UP:
            showWallpaperDialog();
            return false;
          default:
            return false;
        }
      }
    });

    wallPapersDialog.setOnDismissListener(dialog -> {
      animate(wallpapersAddCircle).scaleX(1).scaleY(1).setDuration(200).start();
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  protected void onRestart() {
    super.onRestart();
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  protected void onStart() {
    super.onStart();

    if (getIntent().getBooleanExtra("isMovement", false) || type.equals(getResources().getString(R.string.movements))) {
      type = getResources().getString(R.string.movements);
      if (getIntent().getStringExtra(getString(R.string.from)).endsWith(" ")) {
        presenter.loadMovement(getIntent().getStringExtra(getString(R.string.from)).substring(0, getIntent().getStringExtra(getString(R.string.from)).length() - 1));
        name = getIntent().getStringExtra(getString(R.string.from)).substring(0, getIntent().getStringExtra(getString(R.string.from)).length() - 1);
      } else {
        presenter.loadMovement(getIntent().getStringExtra(getString(R.string.from)));
        name = getIntent().getStringExtra(getString(R.string.from));
      }
    } else if (getIntent().getBooleanExtra("isAuthor", false) || type.equals(getResources().getString(R.string.authors))) {

      type = getResources().getString(R.string.authors);

      if (presenter.getAuthor() == null)
        if (getIntent().getStringExtra(getString(R.string.from)).endsWith(" ")) {
          presenter.loadAuthor(getIntent().getStringExtra(getString(R.string.from)).substring(0, getIntent().getStringExtra(getString(R.string.from)).length() - 1));
          name = getIntent().getStringExtra(getString(R.string.from)).substring(0, getIntent().getStringExtra(getString(R.string.from)).length() - 1);
        } else {
          presenter.loadAuthor(getIntent().getStringExtra(getString(R.string.from)));
          name = getIntent().getStringExtra(getString(R.string.from));
        }
    } else {
      type = getResources().getString(R.string.books);

      if (presenter.getBook() == null) {
        if (getIntent().getStringExtra(getString(R.string.from)).endsWith(" ")) {

          presenter.loadBook(getIntent().getStringExtra(getString(R.string.from)).substring(0, getIntent().getStringExtra(getString(R.string.from)).length() - 1));
          name = getIntent().getStringExtra(getString(R.string.from)).substring(0, getIntent().getStringExtra(getString(R.string.from)).length() - 1);
        } else {
          presenter.loadBook(getIntent().getStringExtra(getString(R.string.from)));
          name = getIntent().getStringExtra(getString(R.string.from));
        }
      }
    }

    if(name.equals(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, ""))) {
      if (settings.getBoolean(Constants.BIOGRAPHY_IS_NOTIFICATION_SENDED, false)) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(BiographyNotificationIntentService.NOTIFICATION_ID);
        int badgecount = settings.getInt(Constants.BADGE_COUNT, 0);
        ShortcutBadger.applyCount(OnelittleAngelApplication.instance.getApplicationContext(), --badgecount);
        editor.putInt(Constants.BADGE_COUNT, badgecount).apply();
        editor.putBoolean(Constants.BIOGRAPHY_IS_NOTIFICATION_SENDED, false).apply();
      }
    }

    if (getIntent().getStringExtra("fromSettings") != null) {
      if (getIntent().getStringExtra("fromSettings").equals("typeface")) {

        showTypeFaceDialog();
      }

      if (getIntent().getStringExtra("fromSettings").equals("mail")) {
        goToMailActivity();
      }
      getIntent().removeExtra("fromSettings");
    }
  }

  @Override
  public void onScroll(float deltaY) {

  }

  @Override
  public void onScrollChanged(int scrollYY) {

    int scrollY = Math.min(mMaxScrollY, scrollYY);
    mScrollSettleHandler.onScroll(scrollY);

    int rawY = mPlaceholderView.getTop() - scrollY;
    int translationY = 0;

    switch (mState) {
      case STATE_OFFSCREEN:
        if (rawY <= mMinRawY) {
          mMinRawY = rawY;
        } else {
          mState = STATE_RETURNING;
        }
        translationY = -mPlaceholderView.getTop() - mQuickReturnHeight;
        animate(rlNumber).cancel();
        ViewHelper.setTranslationY(rlNumber, (translationY * 3) /*+ scrollY*/);

        if (llHeader.getVisibility() == View.VISIBLE) {
          animate(llHeader).cancel();
          ViewHelper.setTranslationY(llHeader, -scrollY);
        }
        break;

      case STATE_ONSCREEN:
        if (rawY < -mQuickReturnHeight) {
          mState = STATE_OFFSCREEN;
          mMinRawY = rawY;
        }
        translationY = rawY;
        animate(rlNumber).cancel();
        ViewHelper.setTranslationY(rlNumber, (translationY * 2) + scrollY);
        if (llHeader.getVisibility() == View.VISIBLE) {
          animate(llHeader).cancel();
          ViewHelper.setTranslationY(llHeader, (translationY * 2) + scrollY);
        }

        break;

      case STATE_RETURNING:
        translationY = rawY - mMinRawY - mQuickReturnHeight;
        if (translationY > 0) {
          translationY = 0;
          mMinRawY = rawY - mQuickReturnHeight;
        }

        if (rawY > 0) {
          mState = STATE_ONSCREEN;
          translationY = rawY;
        }

        if (translationY < -mQuickReturnHeight) {
          mState = STATE_OFFSCREEN;
          mMinRawY = rawY;
        }
        animate(rlNumber).cancel();
        ViewHelper.setTranslationY(rlNumber, (translationY * 2) /*+ scrollY*/);
        if (llHeader.getVisibility() == View.VISIBLE) {
          animate(llHeader).cancel();
          ViewHelper.setTranslationY(llHeader, -scrollY);
        }
        break;
      default:
        break;
    }
  }

  @Override
  public void onDownMotionEvent() {
    mScrollSettleHandler.setSettleEnabled(false);
  }

  @Override
  public void onUpOrCancelMotionEvent() {
    mScrollSettleHandler.setSettleEnabled(true);
    mScrollSettleHandler.onScroll(mObservableScrollView.getScrollY());
  }

  @Override
  public void updateCarousel() {
  }

  @Override
  public void updatePresentation() {

    BiographyFragmentPagerAdapter adapter = new BiographyFragmentPagerAdapter(OnelittleAngelApplication.instance.getApplicationContext(), getSupportFragmentManager(), presenter, getIntent().getStringExtra(getString(R.string.from)));

    adapter.notifyDataSetChanged();



    for (int i = 0; i < adapter.getCount(); i++) adapter.getFragments(i).setActivity(this);

    viewPager.setAdapter(adapter);
    viewPager.adapter = adapter;
    viewPager.setScrollDurationFactor(1);
    viewPager.setOffscreenPageLimit(adapter.getCount());

    final LayoutInflater inflater = LayoutInflater.from(viewPagerTab.getContext());
    final Resources res = viewPagerTab.getContext().getResources();

    int[] colors = new int[adapter.getCount()];

    viewPagerTab.setCustomTabView((container, position1, adapter1) -> {
      ImageView icon = (ImageView) inflater.inflate(R.layout.biography_icons_layout, container, false);
      TextView tv = (TextView) inflater.inflate(R.layout.biography_textview_layout, container, false);
      if (viewPager.adapter.getFragments(position1).presenter == null) {
        if (!viewPager.adapter.getFragments(position1).getArguments().get(PresentationFragment.PRESENTATION_TITLE).toString().contains(res.getString(R.string.bibliography))) {
          colors[position1] = settings.getInt(CardViewNative.DARKERRGB, 0);
          if (position1 != 0) {
            tv.setText(" { " + (position1 + 1) + " } ");
            tv.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
            return tv;
          } else {
            tv.setText(" { " + (position1 + 1) + " } ");
            tv.setTextColor(Color.WHITE);
            return tv;
          }
        } else {
          colors[position1] = Color.WHITE;
          icon.setBackgroundResource(R.drawable.ic_st_bibliography);
          return icon;
        }
      } else {
        colors[position1] = Color.WHITE;
        icon.setBackgroundResource(R.drawable.ic_ab_pictures);
        icon.setDrawingCacheBackgroundColor(Color.WHITE);
        return icon;
      }
    });
    viewPagerTab.setActivity(this);
    viewPagerTab.setSelectedIndicatorColors(colors);
    viewPagerTab.setViewPager(viewPager);

    viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        if (!isInit) {
          isInit = true;
        } else {
          if (getIntent().getIntExtra("currentItem", 0) != 0) {
            viewPager.setCurrentItem(getIntent().getIntExtra("currentItem", 0));
            getIntent().removeExtra("currentItem");
            return;
          }

          if (idPosition != 0 && isOrientationChanged) {
            viewPager.setCurrentItem(idPosition);

            if (microphonesDialog != null && microphonesDialog.isShowing()) {
              ivMicrophonePrevious.setVisibility(View.VISIBLE);
              if (idPosition != viewPager.adapter.getCount() - 1) {
                if ((idPosition + 1 != viewPager.adapter.getCount() - 1 && viewPager.adapter.fragments.get(idPosition + 1) != null && viewPager.adapter.fragments.get(idPosition + 1).gridArrayAdapter == null))
                  ivMicrophoneNext.setVisibility(View.VISIBLE);
                else ivMicrophoneNext.setVisibility(View.GONE);
              } else ivMicrophoneNext.setVisibility(View.GONE);
            }
            isOrientationChanged = false;
            return;
          }

          if (isGvCarrouselIsOpened) {
            togglePictures();
          }

          if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
          } else {
            viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
          }
        }
      }
    });
  }

  @Override
  public void homeBackPressed() {

    soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
    soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> {
      soundPool1.play(soundID, 1f, 1f, 1, 0, 1f);
      onBackPressed();
    });
  }

  @Override
  public void backToHomePage() {
    Intent intent = new Intent(BiographyActivity.this, TableContentsActivity.class);
    startActivity(new Intent(intent));
    try {
      ActivityAnimator anim = new ActivityAnimator();
      anim.fadeAnimationToTableContents(BiographyActivity.this);
    } catch (Exception ignored) {
    }
    this.finish();
  }

  @Override
  public void showAddRemoveFavoritesDialog() {
  }

  @Override
  public void confirmAddRemoveDialog() {
  }

  @Override
  public void cancelAddRemoveDialog() {
  }

  @Override
  public void showSocialNetworksDialog() {
  }

  @Override
  public void showTypeFaceDialog() {

    animate(viewPager).setDuration(800).alpha(0).start();
    rlNumber.startAnimation(rlnumber_alpha_out);

    containerfab.startAnimation(alpha_out);
    fabTxtIncrease.startAnimation(fab_close);
    fabTxtDecrease.startAnimation(fab_close);
    fabSocialNetworks.startAnimation(fab_close);
    fabTypefaces.startAnimation(fab_close);
    fabMicrophone.startAnimation(fab_close);
    isFabOpen = false;
    String[] strings = settings.getString(Constants.TYPEFACESLIST, "").split(";");

    if (settings.getString(Constants.TYPEFACESLIST, "") == null || settings.getString(Constants.TYPEFACESLIST, "").equals("")) {
      if(!isFinishing()) goToTypefaceSettingsDialog.show();
    } else {
      typefacesRecyclerAdapter = new TypefacesRecyclerAdapter(getApplicationContext(), strings);
      typefacesRecyclerAdapter.setCallbacks(this);
      typeFacesDialogListView.setAdapter(typefacesRecyclerAdapter);
      if(!isFinishing())  typeFacesDialog.show();
      typeFacesDialog.setCancelable(false);
      isTypefaceDialogShowing = true;
      WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
      Window window = typeFacesDialog.getWindow();
      lp.copyFrom(window.getAttributes());
      lp.width = (WindowManager.LayoutParams.MATCH_PARENT);
      lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
      window.setAttributes(lp);
    }
  }

  @Override
  public void showMicrophoneDialog() {
    containerfab.startAnimation(alpha_out);
    fabTxtIncrease.startAnimation(fab_close);
    fabTxtDecrease.startAnimation(fab_close);
    fabSocialNetworks.startAnimation(fab_close);
    fabTypefaces.startAnimation(fab_close);
    fabMicrophone.startAnimation(fab_close);
    isFabOpen = false;
    isMicrophoneDialogShowing = true;

    if (microphonesDialog == null) {

      microphonesDialog = new Dialog(BiographyActivity.this, R.style.myDialogSlideUpAndDown);

      microphonesDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
      microphonesDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

      microphonesDialog.setContentView(R.layout.microphones_dialog);

      ivMicrophonePrevious = (ImageView) microphonesDialog.findViewById(R.id.ic_microphone_previous);
      ImageViewListener ivMicrophonePreviousListener = new ImageViewListener(ivMicrophonePrevious, 1.3f);
      ivMicrophonePreviousListener.setCallbacks(this);
      ivMicrophonePrevious.setOnTouchListener(ivMicrophonePreviousListener);
      ivMicrophonePrevious.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));

      ivMicrophoneStop = (ImageView) microphonesDialog.findViewById(R.id.ic_microphone_stop);
      ImageViewListener ivMicrophoneStopListener = new ImageViewListener(ivMicrophoneStop, 1.3f);
      ivMicrophoneStopListener.setCallbacks(this);
      ivMicrophoneStop.setOnTouchListener(ivMicrophoneStopListener);
      ivMicrophoneStop.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));

      ivMicrophonePause = (ImageView) microphonesDialog.findViewById(R.id.ic_microphone_pause);
      ImageViewListener ivMicrophonePauseListener = new ImageViewListener(ivMicrophonePause, 1.3f);
      ivMicrophonePauseListener.setCallbacks(this);
      ivMicrophonePause.setOnTouchListener(ivMicrophonePauseListener);
      ivMicrophonePause.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));

      ivMicrophonePlay = (ImageView) microphonesDialog.findViewById(R.id.ic_microphone_play);
      ImageViewListener ivMicrophonePlayListener = new ImageViewListener(ivMicrophonePlay, 1.3f);
      ivMicrophonePlayListener.setCallbacks(this);
      ivMicrophonePlay.setOnTouchListener(ivMicrophonePlayListener);
      ivMicrophonePlay.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));

      ivMicrophoneNext = (ImageView) microphonesDialog.findViewById(R.id.ic_microphone_next);
      ImageViewListener ivMicrophoneNextListener = new ImageViewListener(ivMicrophoneNext, 1.3f);
      ivMicrophoneNextListener.setCallbacks(this);
      ivMicrophoneNext.setOnTouchListener(ivMicrophoneNextListener);
      ivMicrophoneNext.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));

      microphonesDialog.setOnDismissListener(dialog -> isMicrophoneDialogShowing = false);
    }

    if (viewPager.getCurrentItem() == 0) ivMicrophonePrevious.setVisibility(View.GONE);
    else ivMicrophonePrevious.setVisibility(View.VISIBLE);


    if (viewPager.adapter != null) {

      if (viewPager.getCurrentItem() != viewPager.adapter.getCount() - 1) {

        if (viewPagerTab.getTabAt(viewPager.getCurrentItem() + 1) instanceof ImageView) {
          ImageView iv = (ImageView) viewPagerTab.getTabAt(viewPager.getCurrentItem() + 1);

          if (iv.getDrawingCacheBackgroundColor() == Color.WHITE) ivMicrophoneNext.setVisibility(View.GONE);
          else ivMicrophoneNext.setVisibility(View.VISIBLE);
        }
      } else ivMicrophoneNext.setVisibility(View.GONE);
    }
    if(!isFinishing()) microphonesDialog.show();
  }

  @Override
  public void openFullScreenMode() {
  }

  @Override
  public void startBiographyActivity() {
  }

  @Override
  public void startFaithsBiography() {

  }

  @Override
  public void arrowPrevious() {
  }

  @Override
  public void arrowNext() {
  }

  @Override
  public void searchQuote() {
  }

  @Override
  public void goToCommentsPlaystore() {
  }

  @Override
  public void cancelGoToCommentsPlaystore() {
  }

  @Override
  public void confirmTypefaceChoice() {
    confirmTypefaceChoiceDialog.cancel();
  }

  @Override
  public void cancelTypefaceChoice() {
  }

  @Override
  public void gotToTypefaceSettings() {
    Intent iTypefaces = new Intent(new Intent(BiographyActivity.this, SettingsActivity.class));
    iTypefaces.putExtra(getResources().getString(R.string.from), getResources().getString(R.string.typefaces_nav_title) + ";" + getIntent().getStringExtra("from"));

    if (getIntent().getStringExtra(getResources().getString(R.string.from)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.from)).equals(getResources().getString(R.string.movements))) {
      iTypefaces.putExtra(getResources().getString(R.string.returnTo), txtAuthorBookName.getText().toString().trim());
      iTypefaces.putExtra("value", quickReturnTitle.getText().toString().trim() + ";" + getIntent().getStringExtra("value"));
    } else {
      iTypefaces.putExtra(getResources().getString(R.string.returnTo), quickReturnTitle.getText().toString().trim() + ";" + getIntent().getStringExtra("value"));
    }
    iTypefaces.putExtra("currentItem", viewPager.getCurrentItem());
    iTypefaces.putExtra("type", getIntent().getStringExtra(getResources().getString(R.string.from)));

    if (getIntent().getStringExtra("fromActivity").contains("BiographyActivity")) {
      iTypefaces.putExtra("fromActivity", getIntent().getStringExtra("fromActivity"));
    } else {
      iTypefaces.putExtra("fromActivity", "BiographyActivity;" + getIntent().getStringExtra("fromActivity"));
    }

    iTypefaces.putExtra("isAuthor", getIntent().getBooleanExtra("isAuthor", false));
    iTypefaces.putExtra("idFavoriteQuote", getIntent().getStringExtra("idFavoriteQuote"));
    iTypefaces.putExtra("idContentQuote", getIntent().getStringExtra("idContentQuote"));
    iTypefaces.putExtra("fromContent", getIntent().getStringExtra("fromContent"));

    iTypefaces.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));
    iTypefaces.putExtra("isFromTypefaceSettings", true);

    startActivity(iTypefaces);
    try {
      ActivityAnimator anim = new ActivityAnimator();
      anim.fadeAnimation(BiographyActivity.this);
    } catch (Exception ignored) {
    }
    this.finish();
  }

  @Override
  public void goToTwitterActivity() {
  }

  @Override
  public void goToMailActivity() {

    if (isFabOpen) {
      containerfab.startAnimation(alpha_out);
      fabTxtIncrease.startAnimation(fab_close);
      fabTxtDecrease.startAnimation(fab_close);
      fabSocialNetworks.startAnimation(fab_close);
      fabTypefaces.startAnimation(fab_close);
      fabMicrophone.startAnimation(fab_close);
      isFabOpen = false;
    }

    if (settings.getString(Constants.MAIL_ACCOUNT, "") == null || settings.getString(Constants.MAIL_ACCOUNT, "").equals("")) {

      txtGoToAccount.setText(OnelittleAngelApplication.instance.getResources().getString(R.string.register_account_ask));

      if(!isFinishing()) accountsDialog.show();

      ImageViewListener confirmGoToAccountListener = new ImageViewListener(confirmGoToAccount, OnelittleAngelApplication.instance.getResources().getString(R.string.mail));
      confirmGoToAccountListener.setCallbacks(this);
      confirmGoToAccount.setOnTouchListener(confirmGoToAccountListener);
    } else {

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
        mailsRecyclerAdapter = new MailsRecyclerAdapter(getApplicationContext(), getNameEmailDetails());
        mailsDialogListview.setAdapter(mailsRecyclerAdapter);
      } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        mailsRecyclerAdapter = new MailsRecyclerAdapter(getApplicationContext(), getNameEmailDetails());
        mailsDialogListview.setAdapter(mailsRecyclerAdapter);
      }

      if(!isFinishing()) mailsDialog.show();
      isMailDialogShowing = true;
      WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
      Window window = mailsDialog.getWindow();
      lp.copyFrom(window.getAttributes());
      lp.width = (WindowManager.LayoutParams.MATCH_PARENT);
      lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
      window.setAttributes(lp);
    }
  }

  @Override
  public void goToFacebookActivity() {
  }

  @Override
  public void goToGooglePlusActivity() {
  }

  @Override
  public void cancelGoToSettingsAccount() {
    accountsDialog.cancel();
  }

  @Override
  public void goToAccountSettings(String settings) {

    int position = -1;

    if (settings.equals(OnelittleAngelApplication.instance.getString(R.string.twitter))) {
      position = 0;
    } else if (settings.equals(OnelittleAngelApplication.instance.getString(R.string.mail))) {
      position = 1;
    } else if (settings.equals(OnelittleAngelApplication.instance.getString(R.string.facebook))) {
      position = 2;
    } else if (settings.equals(OnelittleAngelApplication.instance.getString(R.string.google_plus))) {
      position = 3;
    }

    editor.putInt(Constants.SOCIAL_NETWORKS_POSITION, position);
    editor.commit();

    Intent iSocialNetworks = new Intent(new Intent(BiographyActivity.this, SettingsActivity.class));
    iSocialNetworks.putExtra(getResources().getString(R.string.from), getResources().getString(R.string.mail) + ";" + getIntent().getStringExtra("from"));

    if (getIntent().getStringExtra(getResources().getString(R.string.from)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.from)).equals(getResources().getString(R.string.movements))) {
      iSocialNetworks.putExtra(getResources().getString(R.string.returnTo), txtAuthorBookName.getText().toString().trim());
      iSocialNetworks.putExtra("value", quickReturnTitle.getText().toString().trim() + ";" + getIntent().getStringExtra("value"));
    } else {
      iSocialNetworks.putExtra(getResources().getString(R.string.returnTo), quickReturnTitle.getText().toString().trim() + ";" + getIntent().getStringExtra("value"));
    }
    iSocialNetworks.putExtra("currentItem", viewPager.getCurrentItem());
    iSocialNetworks.putExtra("type", getIntent().getStringExtra(getResources().getString(R.string.from)));

    if (getIntent().getStringExtra("fromActivity").contains("BiographyActivity")) {
      iSocialNetworks.putExtra("fromActivity", getIntent().getStringExtra("fromActivity"));
    } else {
      iSocialNetworks.putExtra("fromActivity", "BiographyActivity;" + getIntent().getStringExtra("fromActivity"));
    }
    iSocialNetworks.putExtra("isAuthor", getIntent().getBooleanExtra("isAuthor", false));
    iSocialNetworks.putExtra("idFavoriteQuote", getIntent().getStringExtra("idFavoriteQuote"));
    iSocialNetworks.putExtra("idContentQuote", getIntent().getStringExtra("idContentQuote"));
    iSocialNetworks.putExtra("fromContent", getIntent().getStringExtra("fromContent"));

    iSocialNetworks.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));
    iSocialNetworks.putExtra("isFromMailSettings", true);

    startActivity(iSocialNetworks);
    try {
      ActivityAnimator anim = new ActivityAnimator();
      anim.fadeAnimation((BiographyActivity.this));
    } catch (Exception ignored) {
    }
    finish();
  }

  @Override
  public void playQuoteMicrophone() {

    destFileName = getFilesDir() + "/" + "tts_file";

    if (soundPoolPlayers != null && soundPoolPlayers.size() != 0 && plays) {
      soundPoolPlayers.get(utterancePosition).pause();
      plays = false;
      isOnPause = true;
      return;
    }

    if (soundPoolPlayers != null && soundPoolPlayers.size() != 0 && !plays && !isForMicrophone && isOnPause) {
      soundPoolPlayers.get(utterancePosition).play();
      plays = true;
      isOnPause = false;
      return;
    }

    soundPoolPlayers = new ArrayMap<>();

    if (textToSpeech == null)
      textToSpeech = new TextToSpeech(OnelittleAngelApplication.instance.getApplicationContext(), status -> {
        if (status == TextToSpeech.SUCCESS) {
          textToSpeech.setLanguage(Locale.UK);

          if (chunks == null) {
            chunks = new ArrayList<>();

            if (!presenter.getPresentations().valueAt(viewPager.getCurrentItem()).keyAt(0).isEmpty())
              chunks.add(presenter.getPresentations().valueAt(viewPager.getCurrentItem()).keyAt(0));

            if (presenter.getPresentations().valueAt(viewPager.getCurrentItem()).keyAt(0).equals(getResources().getString(R.string.bibliography)))
              split = "-";
            else split = "\\.";

            if (presenter.getPresentations().valueAt(viewPager.getCurrentItem()).valueAt(0).contains(",")) {

              String[] dotChunks = presenter.getPresentations().valueAt(viewPager.getCurrentItem()).valueAt(0).split(split);
              for (int i = 0; i < dotChunks.length; i++) {
                String s = dotChunks[i].trim();

                String[] comaChunks = s.split(",");

                String s1 = " ";

                for (int j = 0; j < comaChunks.length; j += 4) {

                  if (j + 4 < comaChunks.length) {
                    for (int d = j; d < j + 4; d++) {
                      s1 += comaChunks[d] + ", ";
                    }
                  } else {
                    for (int d = j; d < comaChunks.length; d++) {
                      s1 += comaChunks[d] + ", ";
                    }
                  }
                  chunks.add(s1);
                  s1 = ", ";
                }
              }
            } else {
              String[] dotChunks = presenter.getPresentations().valueAt(viewPager.getCurrentItem()).valueAt(0).split(split);
              for (int i = 0; i < dotChunks.length; i++) {
                String s = dotChunks[i].trim();
                chunks.add(s);
              }
            }
          }

          for (int i = 0; i < chunks.size(); i++) {
            if (ttsFileStrings == null) {
              ttsFileStrings = new ArrayList<>();
            } else {
              ttsFileStrings.removeAll(ttsFileStrings);
            }
            chunkPosition = i;
            myHashRender = new HashMap();
            utteranceID = "wpta_" + i;
            myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceID);
            File fileTTS = new File(destFileName + "" + utteranceID + ".mp3");
            int sr = 0;
            if (!chunks.get(i).equals("")) {
              sr = textToSpeech.synthesizeToFile(chunks.get(i), myHashRender, destFileName + "" + utteranceID + ".mp3");
            } else {
              sr = textToSpeech.synthesizeToFile(" ", myHashRender, destFileName + "" + utteranceID + ".mp3");
            }

            textToSpeech.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
              @Override
              public void onUtteranceCompleted(String utteranceId) {
                String[] strings = utteranceId.split("_");
                soundPoolPlayers.put(utteranceId, SoundPoolPlayer.create(getApplicationContext(), destFileName + "" + utteranceId + ".mp3"));
                ttsFileStrings.add(utteranceId);
                soundPoolPlayers.get(utteranceId).setOnCompletionListener(mp -> {
                  plays = true;
                  isOnPause = false;
                  isForMicrophone = false;

                  int i1 = Integer.parseInt(strings[1]);
                  if (i1 < chunks.size() - 1) {
                    if (soundPoolPlayers != null && soundPoolPlayers.get("wpta_" + (i1 + 1)) != null)
                      soundPoolPlayers.get("wpta_" + ++i1).play();
                    plays = true;
                    isOnPause = false;
                    isForMicrophone = false;
                    utterancePosition = "wpta_" + i1;
                    if (soundPoolPlayers != null && soundPoolPlayers.get(utteranceId) != null)
                      soundPoolPlayers.get(utteranceId).release();

                    if (soundPoolPlayers != null && soundPoolPlayers.get(utteranceId) != null)
                      soundPoolPlayers.get(utteranceId).setOnCompletionListener(null);
                    if (soundPoolPlayers != null && soundPoolPlayers.get(utteranceId) != null)
                      soundPoolPlayers.remove(utteranceId);
                    File file = new File(destFileName + "wpta_" + i1 + ".mp3");
                    if (file.exists()) {
                      file.delete();
                    }
                  } else {
                    if (soundPoolPlayers != null) {
                      for (SoundPoolPlayer player : soundPoolPlayers.values()) {
                        player.release();
                        player.context = null;
                        player.setOnCompletionListener(null);
                        player = null;
                      }

                      for (File file : getFilesDir().listFiles()) {
                        if (file.getName().contains(".mp3")) file.delete();
                      }
                    }
                    chunks = null;
                    plays = false;
                    isOnPause = false;
                    isForMicrophone = false;

                    if (textToSpeech != null) {
                      textToSpeech.stop();
                      textToSpeech.shutdown();

                      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                        textToSpeech.setOnUtteranceProgressListener(null);
                        textToSpeech.setOnUtteranceCompletedListener(null);

                      } else {
                        textToSpeech.setOnUtteranceCompletedListener(null);
                      }
                      textToSpeech = null;
                    }
                  }
                });

                if (soundPoolPlayers.size() == 1) {
                  soundPoolPlayers.valueAt(0).play();
                  utterancePosition = utteranceId;
                  plays = true;
                  isOnPause = false;
                  isForMicrophone = false;
                }
              }
            });
          }
        }
      });
  }

  @Override
  public void stopQuoteMicrophone() {

    if (soundPoolPlayers != null) {
      if (soundPoolPlayers.get(utterancePosition) != null && soundPoolPlayers.get(utterancePosition).isPlaying()) {
        soundPoolPlayers.get(utterancePosition).stop();
      }

      for (SoundPoolPlayer player : soundPoolPlayers.values()) {

        if (player != null) {
          player.release();
          player.context = null;
          player.setOnCompletionListener(null);
        }
      }
      soundPoolPlayers.clear();

      if (ttsFileStrings == null) {
        ttsFileStrings = new ArrayList<>();
      } else {
        ttsFileStrings.removeAll(ttsFileStrings);
      }
    } else {
      if (soundPoolPlayers != null && soundPoolPlayers.get(utterancePosition) != null && soundPoolPlayers.get(utterancePosition).isPlaying()) {
        soundPoolPlayers.get(utterancePosition).stop();
      }

      if (soundPoolPlayers != null) {
        for (SoundPoolPlayer player : soundPoolPlayers.values()) {

          if (player != null) {
            player.release();
            player.context = null;
            player.setOnCompletionListener(null);
          }
        }
        soundPoolPlayers.clear();
      }
    }

    for (File file : getFilesDir().listFiles()) {
      if (file.getName().contains(".mp3")) file.delete();
    }
    soundPoolPlayers = null;
    chunks = null;
    if (!isMicrophoneNextOrPrevious) plays = false;
    isOnPause = false;
    isForMicrophone = true;

    if (textToSpeech != null) {
      textToSpeech.stop();
      textToSpeech.shutdown();

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
        textToSpeech.setOnUtteranceProgressListener(null);
        textToSpeech.setOnUtteranceCompletedListener(null);

      } else {
        textToSpeech.setOnUtteranceCompletedListener(null);
      }
      textToSpeech = null;
    }
  }

  @Override
  public void pauseQuoteMicrophone() {

    if (soundPoolPlayers != null) {

      if (soundPoolPlayers.get(utterancePosition) != null && soundPoolPlayers.get(utterancePosition).isPlaying()) {
        soundPoolPlayers.get(utterancePosition).pause();
        isOnPause = true;
        plays = false;
      } else if (soundPoolPlayers.get(utterancePosition) != null && !soundPoolPlayers.get(utterancePosition).isPlaying()) {
        soundPoolPlayers.get(utterancePosition).play();
        isOnPause = false;
        plays = true;
      }
    }
  }

  @Override
  public void previousQuoteMicrophone() {
    isForMicrophone = true;
    isMicrophoneNextOrPrevious = true;

    stopQuoteMicrophone();

    if (viewPager.adapter.fragments.get(viewPager.getCurrentItem()).gridArrayAdapter == null)
      ivMicrophoneNext.setVisibility(View.VISIBLE);

    if (viewPager.getCurrentItem() - 1 == 0) ivMicrophonePrevious.setVisibility(View.GONE);
    else ivMicrophonePrevious.setVisibility(View.VISIBLE);

    viewPager.setScrollDurationFactor(2);
    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1/*, true*/);


  }

  @Override
  public void nextQuoteMicrophone() {
    isForMicrophone = true;
    isMicrophoneNextOrPrevious = true;

    stopQuoteMicrophone();
    if ((viewPager.adapter.fragments.get(viewPager.getCurrentItem() + 1) != null && viewPager.adapter.fragments.get(viewPager.getCurrentItem() + 1).gridArrayAdapter == null && viewPager.getCurrentItem() + 1 != viewPager.adapter.getCount() - 1))
      ivMicrophoneNext.setVisibility(View.VISIBLE);
    else ivMicrophoneNext.setVisibility(View.GONE);

    if (ivMicrophonePrevious.getVisibility() == View.GONE)
      ivMicrophonePrevious.setVisibility(View.VISIBLE);

    viewPager.setScrollDurationFactor(2);
    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1/*, true*/);
  }

  private void setErrorTextColor(TextInputLayout textInputLayout) {

    final Typeface tf = Typeface.createFromAsset(OnelittleAngelApplication.instance.getAssets(), "fonts/" + "mtcorsva.ttf");

    try {
      Field fErrorView = TextInputLayout.class.getDeclaredField("mErrorView");
      fErrorView.setAccessible(true);
      TextView mErrorView = (TextView) fErrorView.get(textInputLayout);
      Field fCurTextColor = TextView.class.getDeclaredField("mCurTextColor");
      fCurTextColor.setAccessible(true);
      fCurTextColor.set(mErrorView, settings.getInt(CardViewNative.DARKERRGB, 0));
      mErrorView.setTypeface(tf);
      mErrorView.setTextSize(20);
      mErrorView.requestLayout();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setTypefaceToInputLayout(TextInputLayout inputLayout) {

    final Typeface tf = Typeface.createFromAsset(OnelittleAngelApplication.instance.getAssets(), "fonts/" + "mtcorsva.ttf");

    inputEmail.setTypeface(tf);
    etMailComment.setTypeface(tf);
    inputLayout.setTypeface(tf);
    try {
      // Retrieve the CollapsingTextHelper Field
      final Field collapsingTextHelperField = inputLayout.getClass().getDeclaredField("mCollapsingTextHelper");
      collapsingTextHelperField.setAccessible(true);

      // Retrieve an instance of CollapsingTextHelper and its TextPaint
      final Object collapsingTextHelper = collapsingTextHelperField.get(inputLayout);
      final Field tpf = collapsingTextHelper.getClass().getDeclaredField("mTextPaint");
      tpf.setAccessible(true);

      // Apply your Typeface to the CollapsingTextHelper TextPaint
      ((TextPaint) tpf.get(collapsingTextHelper)).setTypeface(tf);
      PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(settings.getInt(CardViewNative.DARKERRGB, 0), PorterDuff.Mode.SRC_ATOP);

      ((TextPaint) tpf.get(collapsingTextHelper)).setColorFilter(colorFilter);
    } catch (Exception ignored) {
      // Nothing to do
    }
  }

  private static void setCursorColor(AppCompatEditText view, @ColorInt int color) {
    try {
      // Get the cursor resource id
      Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
      field.setAccessible(true);
      int drawableResId = field.getInt(view);

      // Get the editor
      field = TextView.class.getDeclaredField("mEditor");
      field.setAccessible(true);
      Object editor = field.get(view);

      // Get the drawable and set a color filter
      Drawable drawable = ContextCompat.getDrawable(view.getContext(), drawableResId);
      drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
      Drawable[] drawables = {drawable, drawable};

      // Set the drawables
      field = editor.getClass().getDeclaredField("mCursorDrawable");
      field.setAccessible(true);
      field.set(editor, drawables);
    } catch (Exception ignored) {
    }
  }

  private boolean validateEmail() {
    String email = inputEmail.getText().toString().trim();
    if (email.isEmpty() || isValidEmail(email)) {

      if (isValidEmail(email)) {
        inputLayoutEmail.setError(OnelittleAngelApplication.instance.getString(R.string.err_msg_email_not_valid));
      }

      if (email.isEmpty()) {
        inputLayoutEmail.setError(OnelittleAngelApplication.instance.getString(R.string.err_msg_email));
      }

      inputLayoutEmail.setErrorEnabled(true);
      setErrorTextColor(inputLayoutEmail);

      requestFocus(inputEmail);
      setCursorColor(inputEmail, settings.getInt(CardViewNative.DARKERRGB, 0));

      return false;
    } else {
      inputLayoutEmail.setErrorEnabled(false);
    }
    return true;
  }

  /**
   * Validating form
   */
  private boolean submitForm() {
    if (!validateEmail()) {
      return false;
    }
    editor.putString(Constants.MAIL_ACCOUNT, inputEmail.getText().toString().trim());
    String movementsList = settings.getString(Constants.MAILS_ADDED_LIST, "");
    movementsList += inputEmail.getText().toString() + ";";
    editor.putString(Constants.MAILS_ADDED_LIST, movementsList);
    editor.commit();
    return true;
  }

  private static boolean isValidEmail(String email) {
    return TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }

  private void requestFocus(View view) {
    if (view.requestFocus()) {
      getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
  }


  @Override
  public void sendMails() {
    String mailToSettings = settings.getString(Constants.MAILS_LIST_TO_SEND, "");

    if (mailToSettings.equals("") || mailToSettings == null) {

      LayoutInflater inflater = getLayoutInflater();
      View layout = View.inflate(getBaseContext(),R.layout.toast_layout, null);

      Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

      aNative = (TextViewNative) layout.findViewById(R.id.toast_txtn);

      aNative.setText(getResources().getString(R.string.mail_choose_at_leat_one));
      aNative.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

      toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
      toast.setDuration(Toast.LENGTH_SHORT);
      toast.setView(layout);
      if(!isFinishing()) toast.show();

    } else {
      String[] split = mailToSettings.split(";");
      List<String> mailTo = new ArrayList<>();

      Collections.addAll(mailTo, split);

      if (mailTo.size() != 0) {

        String name;
        if (presenter.getAuthor() != null) {
          name = presenter.getAuthor().getMcc1() + " " + presenter.getAuthor().getName();
        } else if (presenter.getBook() != null) {
          name = presenter.getBook().getMcc1() + " " + presenter.getBook().getName();
        } else {
          name = "du " + " " + presenter.getMovement().getMovement();

        }

        String content;

        if (etMailComment.getText().toString().isEmpty()) {
          content = presenter.getPresentations().valueAt(viewPager.getCurrentItem()).valueAt(0);
        } else {
          content = etMailComment.getText().toString().trim() + "\n\n" + "------------------------------------" + "\n\n" + presenter.getPresentations().valueAt(viewPager.getCurrentItem()).valueAt(0);
        }

        new SendMailTask(mailTo, "Présentation " + name + " extraite de OnelittleAngel", content, result -> {
          LayoutInflater inflater = getLayoutInflater();
          View layout = View.inflate(getBaseContext(),R.layout.toast_layout, null);

          Toast toast = Toast.makeText(BiographyActivity.this, "", Toast.LENGTH_SHORT);
          aNative = (TextViewNative) layout.findViewById(R.id.toast_txtn);
          aNative.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

          if (result) {
            aNative.setText(getResources().getString(R.string.quote_sended));
          } else {
            aNative.setText(getResources().getString(R.string.quote_not_sended));
          }
          OnelittleAngelApplication.instance.manageConnectivityState();

          if (!OnelittleAngelApplication.instance.isConnected())
            aNative.setText(getResources().getString(R.string.quote_not_sended));

          toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
          toast.setDuration(Toast.LENGTH_SHORT);
          toast.setView(layout);
          if(!isFinishing()) toast.show();
        }).execute(
        );
        editor.remove(Constants.MAILS_LIST_TO_SEND);
        mailsDialog.cancel();
      } else {
        LayoutInflater inflater = getLayoutInflater();
        View layout = View.inflate(getBaseContext(),R.layout.toast_layout, null);

        Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        aNative = (TextViewNative) layout.findViewById(R.id.toast_txtn);
        aNative.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

        aNative.setText(getResources().getString(R.string.confirm_lock_screen_changing));
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        if(!isFinishing()) toast.show();
      }
    }
  }

  @Override
  public void goToFavoritesActivity() {
  }

  @Override
  public void showWallpaperDialog() {
    if(!isFinishing()) wallPapersDialog.show();
  }

  @Override
  public void returnToCardGridView() {

  }

  private class LoadPictures extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      runOnUiThread(() -> {
        // runs on UI thread
        updateCarousel();
        picturesLoadingDialog.dismiss();

      });
    }

    @Override
    protected Void doInBackground(Void... params) {
      // runs on UI thread
      if (getIntent().getBooleanExtra("isMovement", false)) {
        if (getIntent().getStringExtra(getString(R.string.from)).endsWith(" ")) {
          presenter.loadMovementPictures(getIntent().getStringExtra(getString(R.string.from)).substring(0, getIntent().getStringExtra(getString(R.string.from)).length() - 1));
        } else {
          presenter.loadMovementPictures(getIntent().getStringExtra(getString(R.string.from)));
        }
      } else if (getIntent().getBooleanExtra("isAuthor", false)) {
        presenter.loadAuthorPictures(getIntent().getStringExtra(getString(R.string.from)));
      } else {
        presenter.loadBookPictures(getIntent().getStringExtra(getString(R.string.from)));
      }
      return null;
    }
  }

  @Override
  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
  }

  @Override
  protected void onPostResume() {
    super.onPostResume();
  }


  @Override
  public void togglePictures() {

    if (presenter.getCarouselView().isEmpty() && !presenter.picturesAlreadyLoaded()) {
      if (!isOrientationChanged) {
        if(!isFinishing()) picturesLoadingDialog.show();
        picturesLoadingDialog.setCancelable(false);
      }
      if (isFabOpen) {
        containerfab.startAnimation(alpha_out);
        fabTxtIncrease.startAnimation(fab_close);
        fabTxtDecrease.startAnimation(fab_close);
        fabSocialNetworks.startAnimation(fab_close);
        fabTypefaces.startAnimation(fab_close);
        fabMicrophone.startAnimation(fab_close);
        isFabOpen = false;
      }
      LoadPictures loadPictures = new LoadPictures();
      loadPictures.execute();
      return;
    }

    if (isFabOpen) {
      containerfab.startAnimation(alpha_out);
      fabTxtIncrease.startAnimation(fab_close);
      fabTxtDecrease.startAnimation(fab_close);
      fabSocialNetworks.startAnimation(fab_close);
      fabTypefaces.startAnimation(fab_close);
      fabMicrophone.startAnimation(fab_close);
      isFabOpen = false;
    }

    if (viewPager.getVisibility() == View.GONE) {
      viewPager.setVisibility(View.VISIBLE);
      viewPager.startAnimation(rlnumber_alpha_in);

      if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {
      }
    } else {
      viewPager.startAnimation(rlnumber_alpha_out);
      viewPager.setVisibility(View.GONE);

      mObservableScrollView.smoothScrollTo(0, 0);
    }
  }

  private Bitmap scaleBitmap(Bitmap bm) {
    int width = bm.getWidth();
    int height = bm.getHeight();

    int maxWidth = this.width;
    int maxHeight = this.height;

    if (width > height) {
      // landscape
      float ratio = (float) width / maxWidth;
      width = maxWidth;
      height = (int) (height / ratio);
    } else if (height > width) {
      // portrait
      float ratio = (float) height / maxHeight;
      height = maxHeight;
      width = (int) (width / ratio);
    } else {
      // square
      float ratio = (float) width / maxWidth;
      width = maxWidth;
      height = (int) (height / ratio);
    }

    bm = Bitmap.createScaledBitmap(bm, width, height, true);
    return bm;
  }


  private Bitmap setWallpaper() {

    Bitmap wallpaper = null;
    //WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
    //import non-scaled bitmap wallpaper
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inScaled = false;
    wallpaper = scaleBitmap(viewPager.getAdapter().getItem(viewPager.getAdapter().getCount() - 1).presenterInterface.getBitmapsView().get(CardGridArrayAdapter.pos));

    if (width > wallpaper.getWidth() ||
            height > wallpaper.getHeight()) {
      //add padding to wallpaper so background image scales correctly
      int xPadding;
      int yPadding;
      if (wallpaper.getWidth() > wallpaper.getHeight()) {
        xPadding = Math.max(0, width - wallpaper.getWidth()) / wallpaper.getWidth();
        yPadding = Math.max(0, height - wallpaper.getHeight()) / 2;
      } else {
        xPadding = Math.max(0, width - wallpaper.getWidth()) / 2;
        yPadding = Math.max(0, height - wallpaper.getHeight()) / wallpaper.getHeight();
      }
      Bitmap paddedWallpaper = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(paddedWallpaper);
      Palette palette = Palette.from(wallpaper).generate();
      canvas.drawColor(palette.getLightVibrantColor(palette.getDominantSwatch().getRgb()));
      int[] pixels = new int[wallpaper.getWidth() * wallpaper.getHeight()];
      wallpaper.getPixels(pixels, 0, wallpaper.getWidth(), 0, 0, wallpaper.getWidth(), wallpaper.getHeight());
      paddedWallpaper.setPixels(pixels, 0, wallpaper.getWidth(), xPadding, yPadding, wallpaper.getWidth(), wallpaper.getHeight());
      return paddedWallpaper;
    } else {
      return wallpaper;
    }
  }

  @Override
  public void confirmWallpaperChanging(int resId) {

    WallpaperManager m = WallpaperManager.getInstance(OnelittleAngelApplication.instance.getApplicationContext());

    try {

      DisplayMetrics metrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(metrics);

      LayoutInflater inflater = getLayoutInflater();
      View layout = View.inflate(getBaseContext(),R.layout.toast_layout, null);
      Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
      TextViewNative aNative = (TextViewNative) layout.findViewById(R.id.toast_txtn);
      aNative.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

      if (resId == R.id.iv_home_screen) {
        m.clear();
        m.setBitmap(setWallpaper());
        aNative.setText(getResources().getString(R.string.confirm_home_screen_changing));
      }

      if (resId == R.id.iv_lock_screen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          m.clear(WallpaperManager.FLAG_LOCK);
          m.setBitmap(setWallpaper(), null, false, WallpaperManager.FLAG_LOCK);
          setWallpaper();
        }
        aNative.setText(getResources().getString(R.string.confirm_lock_screen_changing));
      }

      if (resId == R.id.iv_home_lock_screen) {
        m.clear();
        m.setBitmap(setWallpaper());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          m.setBitmap(setWallpaper(), null, false, WallpaperManager.FLAG_LOCK);
        }
        aNative.setText(getResources().getString(R.string.confirm_home_lock_screen_changing));
      }

      toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
      toast.setDuration(Toast.LENGTH_SHORT);
      toast.setView(layout);
      toast.show();
      wallPapersDialog.dismiss();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void cancelWallpaperChanging() {
    wallPapersDialog.cancel();
  }

  @Override
  public void addEmail() {
    if (submitForm()) {
      mailsAddedRecyclerAdapter.contacts.put(inputEmail.getText().toString(), inputEmail.getText().toString());
      mailsAddedRecyclerAdapter.notifyDataSetChanged();
      inputEmail.setText("");
    } else {
    }
  }

  @Override
  public void increaseTextSize() {
    displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    float size = settings.getFloat(Constants.TEXTSIZE, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 22, displaymetrics));
    size += 7f;
    editor.putFloat(Constants.TEXTSIZE, size);
    editor.commit();
    if(!isFinishing()) dBiographyLoading.show();

    viewPager.adapter.getItem(viewPager.getCurrentItem()).onScaleEnd(0, 0);
  }

  @Override
  public void decreaseTextSize() {
    displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    float size = settings.getFloat(Constants.TEXTSIZE, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 22, displaymetrics));
    size -= 7f;

    editor.putFloat(Constants.TEXTSIZE, size);
    editor.commit();

    viewPager.adapter.getItem(viewPager.getCurrentItem()).onScaleEnd(0, 0);
  }

  @Override
  public void toggleFloatingActionButton() {

    if ((plays || isOnPause) && soundPoolPlayers != null && soundPoolPlayers.size() != 0) {
      if(!isFinishing()) microphonesDialog.show();
      isMicrophoneDialogShowing = true;
      return;
    }

    if (!isFabOpen) {
      containerfab.setVisibility(View.VISIBLE);
      containerfab.startAnimation(alpha_in);
      fabTxtIncrease.setVisibility(View.VISIBLE);
      fabTxtIncrease.startAnimation(fab_open);
      fabTxtDecrease.setVisibility(View.VISIBLE);
      fabTxtDecrease.startAnimation(fab_open);
      fabSocialNetworks.setVisibility(View.VISIBLE);
      fabSocialNetworks.startAnimation(fab_open);
      fabTypefaces.setVisibility(View.VISIBLE);
      fabTypefaces.startAnimation(fab_open);
      fabMicrophone.setVisibility(View.VISIBLE);
      fabMicrophone.startAnimation(fab_open);
      isFabOpen = true;
    } else {
      containerfab.startAnimation(alpha_out);
      fabTxtIncrease.startAnimation(fab_close);
      fabTxtDecrease.startAnimation(fab_close);
      fabSocialNetworks.startAnimation(fab_close);
      fabTypefaces.startAnimation(fab_close);
      fabMicrophone.startAnimation(fab_close);
      isFabOpen = false;
    }
  }

  //@Override
  public void onLongPressed() {
    toggleFloatingActionButton();
  }

  //@Override
  public void onSingleTapUped() {

  }

  //@Override
  public void onFlingGesture() {

  }

  //@Override
  public void onSingleTapConfirm() {
    toggleFloatingActionButton();
  }

  //@Override
  public void onDoubleTaped() {
    toggleFloatingActionButton();
  }

  @Override
  public void onScaling(float size, int resId) {
    if (viewPager.isPagingEnabled()) viewPager.setPagingEnabled();
  }

  //@Override
  public void onScaleEnd(float size, int resId) {
    viewPager.adapter.getItem(viewPager.getCurrentItem()).onScaleEnd(0, 0);
  }

  @Override
  public void changeTypeFace(String tp) {
    if (!tp.trim().equals("")) {
      typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/" + tp.trim() + ".ttf");
    } else {
      tp = "mtcorsva";
      this.tpString = "mtcorsva";
      typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/" + tp + ".ttf");
    }
    this.tpString = settings.getString(Constants.TYPEFACE, "");

    editor.putString(Constants.TYPEFACE, tp.trim());
    editor.commit();

    isForMicrophone = false;
    typeFacesDialog.cancel();

    if (typefacesRecyclerAdapter != null) {
      if (typefacesRecyclerAdapter.soundPool != null) {
        typefacesRecyclerAdapter.soundPool.get().release();
        typefacesRecyclerAdapter.soundPool.get().setOnLoadCompleteListener(null);
        typefacesRecyclerAdapter.soundPool = null;
      }
      typefacesRecyclerAdapter = null;
    }

    isTypefaceChanged = true;
    if(!isFinishing() && dBiographyLoading != null && !dBiographyLoading.isShowing()) dBiographyLoading.show();
    viewPager.adapter.getItem(viewPager.getCurrentItem()).onScaleEnd(0, 0);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        // Permission is granted
        goToMailActivity();
      }
    }
  }

  private ArrayMap<String, String> getNameEmailDetails() {
    ArrayMap<String, String> names = new ArrayMap<>();

    ContentResolver cr = getContentResolver();
    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
    if (cur.getCount() > 0) {
      while (cur.moveToNext()) {
        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
        Cursor cur1 = cr.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{id}, null);
        while (cur1.moveToNext()) {
          //to get the contact names
          String name = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
          String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
          if (email != null) {
            names.put(email, name);
          }
        }
        cur1.close();
      }
    }
    cur.close();
    return names;
  }

  private class ScrollSettleHandler extends Handler {
    private static final int SETTLE_DELAY_MILLIS = 100;

    private int mSettledScrollY = Integer.MIN_VALUE;
    private boolean mSettleEnabled;

    public void onScroll(int scrollY) {
      if (mSettledScrollY != scrollY) {
        mSettledScrollY = scrollY;
      }
    }

    public void setSettleEnabled(boolean settleEnabled) {
      mSettleEnabled = settleEnabled;
    }

    @Override
    public void handleMessage(Message msg) {

      if (STATE_RETURNING == mState && mSettleEnabled) {
        int mDestTranslationY;
        if (mSettledScrollY - ViewHelper.getTranslationY(rlNumber) > mQuickReturnHeight / 2) {
          mState = STATE_OFFSCREEN;
          mDestTranslationY = Math.max(
                  mSettledScrollY - mQuickReturnHeight,
                  mPlaceholderView.getTop());
        } else {
          mDestTranslationY = mSettledScrollY;
        }

        mMinRawY = mPlaceholderView.getTop() - mQuickReturnHeight - mDestTranslationY;
        animate(rlNumber).translationY(mDestTranslationY);
      }
      mSettledScrollY = Integer.MIN_VALUE;
    }
  }

  @Override
  public void onBackPressed() {

    if (ivExpandedPictures.getVisibility() == View.VISIBLE || cvImageComment.getVisibility() == View.VISIBLE) {

      if (isFabOpen) {
        containerfab.startAnimation(alpha_out);
        fabTxtIncrease.startAnimation(fab_close);
        fabTxtDecrease.startAnimation(fab_close);
        fabSocialNetworks.startAnimation(fab_close);
        fabTypefaces.startAnimation(fab_close);
        fabMicrophone.startAnimation(fab_close);
        isFabOpen = false;
      }

      ivQuoteRight.setImageResource(R.drawable.quote_right_pictures);
      ivQuoteLeft.setImageResource(R.drawable.quote_left_pictures);
      if (viewPager.getAdapter().getFragments(viewPager.getAdapter().getCount() - 1).gridArrayAdapter == null)

        viewPager.getAdapter().getFragments(viewPager.getAdapter().getCount() - 1).gridArrayAdapter = new CardGridArrayAdapter(this, viewPager.getAdapter().getFragments(viewPager.getAdapter().getCount() - 1).presenterInterface, PresenterInjector.getBiographyPresenter(this));
      viewPager.getAdapter().getFragments(viewPager.getAdapter().getCount() - 1).gridArrayAdapter.zoomImageFromThumb2(null, false, false);
      return;
    }

    if (isFabOpen) {
      containerfab.startAnimation(alpha_out);
      fabTxtIncrease.startAnimation(fab_close);
      fabTxtDecrease.startAnimation(fab_close);
      fabSocialNetworks.startAnimation(fab_close);
      fabTypefaces.startAnimation(fab_close);
      fabMicrophone.startAnimation(fab_close);
      isFabOpen = false;
      return;
    }

    try {
      stopQuoteMicrophone();
    } finally {
      Intent mainIntent = null;
      if (getIntent().getStringExtra("fromActivity").equals("ContentsActivity")) {
        mainIntent = new Intent(this, ContentsActivity.class);
        mainIntent.putExtra("fromActivity", getIntent().getStringExtra("fromActivity"));
      }

      if (getIntent().getStringExtra("fromActivity").contains("FavoritesActivity")) {

        String[] strings = getIntent().getStringExtra("fromActivity").split(";");

        mainIntent = new Intent(this, FavoritesActivity.class);

        if (strings.length == 3) {
          mainIntent.putExtra("fromActivity", "FavoritesActivity;" + strings[2]);
        }
        if (strings.length == 2) {
          mainIntent.putExtra("fromActivity", "FavoritesActivity;" + strings[1]);
        }
      } else if (getIntent().getStringExtra("fromActivity").contains("BiographyActivity")) {
        String[] strings = getIntent().getStringExtra("fromActivity").split(";");
        if (strings[1].equals("ContentsActivity")) {
          mainIntent = new Intent(this, ContentsActivity.class);
          mainIntent.putExtra("fromActivity", strings[1]);
        } else if (strings[1].equals("FavoritesActivity")) {
          mainIntent = new Intent(this, FavoritesActivity.class);
          mainIntent.putExtra("fromActivity", strings[1]);
        }
      }

      if (getIntent().getStringExtra("fromActivity").contains("TableContentsActivity")) {
        String[] strings = getIntent().getStringExtra("fromActivity").split(";");
        if (strings.length == 1 || getIntent().getStringExtra("fromActivity").equals("BiographyActivity;TableContentsActivity")) {
          mainIntent = new Intent(this, TableContentsActivity.class);
          mainIntent.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));
          startActivity(new Intent(mainIntent));
          try {
            ActivityAnimator anim = new ActivityAnimator();
            anim.fadeAnimationToTableContents(BiographyActivity.this);
          } catch (Exception ignored) {
          }
          finish();
          // return;
        }
      }
      String name = "";

      if (getIntent().getStringExtra("type") != null) {
        if (getIntent().getStringExtra("type").contains(";")) {
          String[] strings = getIntent().getStringExtra("type").split(";");
          if (strings[1].equals(getResources().getString(R.string.themes))) {
            name = getIntent().getStringExtra("value");
          } else if (strings[1].equals(getResources().getString(R.string.movements))) {
            name = getIntent().getStringExtra(getString(R.string.from));
          } else {
            name = getIntent().getStringExtra(getString(R.string.from));
          }
        } else {
          if (getIntent().getStringExtra("type").equals(getResources().getString(R.string.themes))) {
            name = getIntent().getStringExtra("value");
          } else if (getIntent().getStringExtra("type").equals(getResources().getString(R.string.movements))) {
            name = getIntent().getStringExtra(getString(R.string.from));
          } else {
            name = getIntent().getStringExtra(getString(R.string.from));
          }
        }

        if (getIntent().getStringExtra("initTitlePage") != null) {
          name = getIntent().getStringExtra("initTitlePage");
        }

        //  String type = getIntent().getStringExtra("type");
        if (getIntent().getStringExtra("type").contains(";")) {
          //  String[] strings = getIntent().getStringExtra("type").split(";");
          mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), getIntent().getStringExtra("fromFragment"));
          mainIntent.putExtra(getIntent().getStringExtra("fromFragment"), name);
        } else {
          mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), getIntent().getStringExtra("type"));
          mainIntent.putExtra(getIntent().getStringExtra("type"), name);
        }
      }

      mainIntent.putExtra("idFavoriteQuote", getIntent().getStringExtra("idFavoriteQuote"));
      mainIntent.putExtra("idContentQuote", getIntent().getStringExtra("idContentQuote"));
      mainIntent.putExtra("fromContent", getIntent().getStringExtra("fromContent"));
      mainIntent.putExtra("isAuthor", getIntent().getBooleanExtra("isAuthor", false));
      mainIntent.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));

      startActivity(new Intent(mainIntent));
      try {
        ActivityAnimator anim = new ActivityAnimator();
        anim.fadeAnimationToTableContents(BiographyActivity.this);
      } catch (Exception ignored) {
      }
      finish();
    }
  }

  /***********************************************************
   * Managing Presenters
   **********************************************************/
  @Override
  public MotherPresenter getPresenter() {
    return (MotherPresenter) presenter;
  }

  /***********************************************************
   *  Testing Only
   **********************************************************/

  @VisibleForTesting
  public IdlingResource getCountingIdlingResource() {
    return EspressoIdlingResource.getIdlingResource();
  }
}
