package com.sc.en.christianism.layers.mvp.contents;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
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
import android.text.InputFilter;
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
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
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
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.sc.en.christianism.OnelittleAngelApplication;
import com.sc.en.christianism.layers.mvp.common.customs.edittexts.TextWatcher;
import com.sc.en.christianism.layers.mvp.common.customs.listviews.adapters.TypefacesRecyclerAdapter;
import com.sc.en.christianism.layers.mvp.common.customs.scrollviews.ObservableScrollView;
import com.sc.en.christianism.layers.mvp.common.customs.spinkits.SpinKitView;
import com.sc.en.christianism.layers.mvp.common.customs.spinkits.sprite.Sprite;
import com.sc.en.christianism.layers.mvp.common.customs.viewpagers.fragmentpageradapter.ContentsNativePagerAdapter;
import com.sc.en.christianism.layers.mvp.biography.BiographyActivity;
import com.sc.en.christianism.layers.mvp.biography.carousel.CarouselView;
import com.sc.en.christianism.layers.mvp.biography.fragments.Utils;
import com.sc.en.christianism.layers.mvp.common.customs.circle.Circle;
import com.sc.en.christianism.layers.mvp.common.customs.spinkits.SpriteFactory;
import com.sc.en.christianism.layers.mvp.common.customs.viewpagers.ViewPagerNative;
import com.sc.en.christianism.layers.mvp.common.listeners.imageviews.ImageViewListener;
import com.sc.en.christianism.layers.mvp.common.players.SoundPoolPlayer;
import com.sc.en.christianism.layers.mvp.contents.listgridviews.CardGridArrayAdapter;
import com.sc.en.christianism.layers.mvp.contents.listgridviews.CardGridView;
import com.sc.en.christianism.layers.mvp.settings.SettingsActivity;
import com.sc.en.christianism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.christianism.layers.service.mails.SendMailTask;
import com.sc.en.christianism.layers.service.notifications.pictureofday.services.PictureNotificationIntentService;
import com.sc.en.christianism.R;
import com.sc.en.christianism.injector.PresenterInjector;
import com.sc.en.christianism.layers.mvp.MotherActivity;
import com.sc.en.christianism.layers.mvp.MotherPresenter;
import com.sc.en.christianism.layers.mvp.common.animations.ActivityAnimator;
import com.sc.en.christianism.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.christianism.layers.mvp.common.customs.edittexts.InputFilterMinMax;
import com.sc.en.christianism.layers.mvp.common.customs.imagezoom.ImageViewTouch;
import com.sc.en.christianism.layers.mvp.common.customs.listviews.adapters.MailsAddedRecyclerAdapter;
import com.sc.en.christianism.layers.mvp.common.customs.listviews.adapters.MailsRecyclerAdapter;
import com.sc.en.christianism.layers.mvp.common.customs.spinkits.Style;
import com.sc.en.christianism.layers.mvp.common.customs.textviews.ContentTextViewNativeBiography;
import com.sc.en.christianism.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.en.christianism.layers.mvp.common.graphics.Arrow;
import com.sc.en.christianism.layers.mvp.common.layouts.linearlayouts.PersonalLinearLayout;
import com.sc.en.christianism.layers.mvp.common.models.PageModel;
import com.sc.en.christianism.layers.mvp.common.utils.Constants;
//import FavoritesActivity;
import com.sc.en.christianism.layers.service.notifications.quoteofday.services.QuoteNotificationIntentService;
import com.sc.en.christianism.transverse.test.EspressoIdlingResource;

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

public class ContentsActivity extends MotherActivity implements ContentsViewInterface, ObservableScrollView.Callbacks, ContentsNativePagerAdapter.Callbacks, TypefacesRecyclerAdapter.Callbacks, ImageViewListener.Callbacks, ImageViewTouch.CallBacks {

  private static final String TAG = "ContentsActivity";

  /***********************************************************
   *  Presenter
   **********************************************************/
  /**
   * The Presenter associated with that view
   */
  private ContentsPresenterInterface presenter = null;

  private SharedPreferences settings;
  private SharedPreferences.Editor editor;
  private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

  private static final int STATE_ONSCREEN = 0;
  private static final int STATE_OFFSCREEN = 1;
  private static final int STATE_RETURNING = 2;
  private View mPlaceholderView;
  private final ScrollSettleHandler mScrollSettleHandler = new ScrollSettleHandler();
  private int mMinRawY;
  private int mState = STATE_ONSCREEN;
  private int mQuickReturnHeight;
  private int mQuickReturnWidthForTitlePage;

  private int mMaxScrollY;
  private ObservableScrollView mObservableScrollView;
  private ViewPagerNative viewPager;
  // we name the left, middle and right page
  private static final int PAGE_LEFT = 0;
  private static final int PAGE_MIDDLE = 1;
  private static final int PAGE_RIGHT = 2;
  // we save each page in a model
  private int mSelectedPageIndex = -1;

  private final int ANIMATION_DURATION = 400;

  private TextViewNative txt, contentsTitlePage, txtDetails, txtnPositionQuotes, txtnSizeQuotes, txtAuthorBookName, txtGoToAccount, sendMails, txtGoToTypefaceSettings, aNative, txtBiographyFaiths;
  private int mViewPagerTop;
  private CardView containerfab;
  private CardView cvGoToTypefaceSettings;
  private LinearLayout rlNumber, llFab;

  private PageModel leftPage;
  private PageModel middlePage;
  private PageModel rightPage;
  private ImageView ivQuoteLeft;
  private ImageView ivQuoteRight;
  private ImageView ivAbHome;
  private ImageView ivAbSearch;
  private ImageView ivMicrophonePrevious;
  private ImageView ivMicrophoneStop;
  private ImageView ivMicrophonePause;
  private ImageView ivMicrophonePlay;
  private ImageView ivMicrophoneNext;
  private ImageView ivHomeScreen;
  private ImageView ivLockScreen;
  private ImageView ivHomeLockScreen;
  private ImageView ivPictures;
  private ImageViewTouch ivExpandedPictures;
  private ImageView fabAddToFavorites,fabSocialNetworks, fabTypefaces, fabMicrophone, fabTxtIncrease, fabTxtDecrease;
  private Arrow arrowPrevious, arrowNext;
  private Boolean isFabOpen = false;
  private Boolean isRlNumberIsOpen = false;
  private Animation fab_open;
  private Animation fab_close;
  private Animation alpha_in;
  private Animation alpha_out;
  private Animation rlnumber_alpha_in;
  private Animation rlnumber_alpha_out;
  private Animation rlnumber_alpha_out_no_duration;
  private Animation viewpager_alpha_in;
  private Animation viewpager_alpha_out;
  private Animation toggle_iv_pictures_background;
  private Animation viewpager_alpha_out_no_duration;
  private AppCompatEditText etChooseQuotes, etMailComment, inputEmail;
  private Dialog addRemoveFromDialog, socialNetworksDialog, typeFacesDialog, confirmTypefaceChoiceDialog, microphonesDialog, goToTypefaceSettingsDialog, accountsDialog, mailsDialog, wallPapersDialog, picturesLoadingDialog, carrouselDialog;
  private RecyclerView typeFacesDialogListView, mailsDialogListview, mailsAddedDialogListview;
  private TextView confirmAddToFavorites;
  private TextView cancelAddToFavorites;
  private TextView confirmTypefaceChoice;
  private TextView cancelTypefaceChoice;
  private TextView confirmGoToAccount;
  private TextView cancelGoToAccount;
  private TextView goToTypefaceSettings;
  private int oldLeftIndex;
  private int oldMiddleIndex;
  private int oldRightIndex;
  private MailsAddedRecyclerAdapter mailsAddedRecyclerAdapter;
  private int biggerCarouselViewHeight;
  private String initTitlePage;
  private int middlePagePosition;
  private String type;

  private Typeface typeface;
  private String tpString;
  private ContentsNativePagerAdapter adapter;
  private static TextToSpeech textToSpeech;
  private boolean isOnPause = false;
  private String destFileName;
  private HashMap<String, String> myHashRender;
  private String utteranceID;
  private boolean isForMicrophone = false;
  private CarouselView mCarouselView;
  private LinearLayout llSearch, llLockScreen, llHomeLockScreen;

  private static ArrayMap<String, SoundPoolPlayer> soundPoolPlayers ;
  public int soundID;
  private boolean plays = false;
  boolean isInit = false;

  private boolean isAuthor = false;
  private int chunkPosition = 0 ;
  private static String utterancePosition;
  private TypefacesRecyclerAdapter typefacesRecyclerAdapter;

  private Handler handler;
  //private final Runnable runnable;
  private List<String> chunks;
  private TextInputLayout inputLayoutEmail;
  private String s;
  private CardGridView gvCarrousel;
  private CardGridArrayAdapter gridArrayAdapter;
  private int idQuote = -1;
  private boolean isGvCarrouselIsOpened = false;
  private DisplayMetrics displaymetrics;
  private int mObservableHeight;
  private LinearLayout llHeader;
  private CardView cvImageComment;
  public boolean isExpandedImageDipslaying;
  public boolean isImageCommentDisplaying;
  private boolean isTypefaceChanged = false;
  private boolean isTypefaceDialogShowing = false;
  private boolean isMailDialogShowing = false;
  private boolean isMicrophoneDialogShowing = false;
  private boolean isMicrophoneNextOrPrevious = false;

  private int height ;
  private int width ;
  private List<String> ttsFileStrings;
  private int gvCarrouselMarginTop;
  private MailsRecyclerAdapter mailsRecyclerAdapter;
  private LinearLayout llNumber;
  private SoundPool soundPool;
  private static boolean isOrientationChanged = false;
  public static Dialog dQuotesLoading;
  public CardView cvQuotesDialog;
  public boolean isFromMailOrTypefaceSetings = false;
  float textSize = 0;

  @Override
  public void onStart() {
    super.onStart();

    if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    if(getIntent().getStringExtra("fromContent") != null) {
      s = getIntent().getStringExtra("fromContent").trim();
    } else {
      s = getIntent().getStringExtra(getIntent().getStringExtra("from"));
    }
    if (presenter.getPageModels().length == 0) {
      if(getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals("homepage")) {
        if(settings.getString(Constants.CALL_FROM_HOMEPAGE, "").equals(OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day))) {
          if(settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
            presenter.loadPageModels("authors", s);
          } else {
            presenter.loadPageModels("books", s);
          }
        } else {
          if(settings.getBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, false)) {
            presenter.loadPageModels("authors", s);
          } else {
            presenter.loadPageModels("books", s);
          }
        }
      } else {

        if(getIntent().getStringExtra("fromContent") != null) {
          presenter.loadPageModels(getIntent().getStringExtra(getResources().getString(R.string.fromFragment)), s);
        } else {

          if (getIntent().getStringExtra(getIntent().getStringExtra(getResources().getString(R.string.fromFragment))) != null)
            s = getIntent().getStringExtra(getIntent().getStringExtra(getResources().getString(R.string.fromFragment)));
          else s = getIntent().getStringExtra(getResources().getString(R.string.from));

          presenter.loadPageModels(getIntent().getStringExtra(getResources().getString(R.string.fromFragment)), s);
        }
      }
      presenter.loadMailAccount();
    }
  }

  @Override
  public void onStop() {
    super.onStop();

    if (plays) {
    }
  }

  @Override
  public void onDestroy() {

    if (isTypefaceDialogShowing) typeFacesDialog.cancel();
    if (isMailDialogShowing) mailsDialog.cancel();
    if (isMicrophoneDialogShowing) microphonesDialog.cancel();

    if (middlePage != null) {
      editor.putInt(Constants.CONTENT_QUOTE_ID, middlePage.getIndex());
      editor.commit();
    }

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

      if(soundPoolPlayers != null) {


        for (SoundPoolPlayer player : soundPoolPlayers.values()) {

          //  if (soundPoolPlayers.size() >= i && soundPoolPlayers.valueAt(i) != null) {
          //  Log.i("100",  : soundPoolPlayers.size() >= i && soundPoolPlayers.valueAt(i) != null" + i);
          if (player != null) {
            player.release();
            player.context = null;
            //player.getOnCompletionListener().onCompletion(null);
            player.setOnCompletionListener(null);
          }
          //soundPoolPlayers.valueAt(i).player.release();
          //soundPoolPlayers.valueAt(i).player.setOnCompletionListener(null);
          //player = null;
          //soundPoolPlayers.valueAt(i).this.

          //  soundPoolPlayers.remove();
        }

        //soundPoolPlayers.removeAll(soundPoolPlayers.values());
        soundPoolPlayers.clear();

      }


      CardGridArrayAdapter.thumbViewList = null;
      CardGridArrayAdapter.thumbBaseView = null;
      //  presenter.setContentsViewInterface();
      //  presenter.setPageModels();
//      for (int i = 0; i < presenter.getCarouselView().size(); i++) {
//        presenter.getCarouselView().remove(presenter.getCarouselView().get(i));
//      }
//      presenter.setCarouselView(null);
//      for (int i = 0; i < presenter.getBitmapsView().size(); i++) {
//        presenter.getBitmapsView().remove(presenter.getBitmapsView().get(i));
//      }
//      presenter.setBitmapsView(null);
      //  presenter = null;
      //viewPager.adapter = null;
      //viewPager = null;
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
//        if (textToSpeech != null) {
//          textToSpeech.setOnUtteranceProgressListener(null);
//          textToSpeech.setOnUtteranceCompletedListener(null);
//          textToSpeech.shutdown();
//          textToSpeech = null;
//        }
//      }

    //  txt = contentsTitlePage = txtDetails = txtnPositionQuotes = txtnSizeQuotes = txtAuthorBookName = txtGoToAccount = sendMails = txtGoToTypefaceSettings = aNative = null;
    //  confirmAddToFavorites = cancelAddToFavorites = confirmTypefaceChoice = cancelTypefaceChoice = confirmGoToAccount = cancelGoToAccount = goToTypefaceSettings = null;
    //  typeFacesDialogListView = mailsDialogListview = mailsAddedDialogListview = null;
    //  addRemoveFromDialog = socialNetworksDialog = typeFacesDialog = confirmTypefaceChoiceDialog = microphonesDialog = wallPapersDialog = carrouselDialog = goToTypefaceSettingsDialog = accountsDialog = mailsDialog = picturesLoadingDialog = null;
    //  etChooseQuotes = etMailComment = inputEmail = null;
    //  inputLayoutEmail = null;
    //  fab_open = fab_close = alpha_in = alpha_out = rlnumber_alpha_in = rlnumber_alpha_out = rlnumber_alpha_out_no_duration = viewpager_alpha_in = viewpager_alpha_out = null;
    //  arrowPrevious = arrowNext = null;
    //  fabAddToFavorites = fabSocialNetworks = fabTypefaces = fabMicrophone = null;

    //  ivMicrophonePrevious = ivMicrophoneStop = ivMicrophonePause = ivMicrophonePlay = ivMicrophoneNext = ivHomeScreen = ivLockScreen = ivHomeLockScreen  = ivPictures = null;
    //  llLockScreen = llHomeLockScreen = llSearch = null;
    //  typeface = null;
    //  tpString = null;
      //adapter = null;
      textToSpeech = null;
     // mPlaceholderView = null;
    //  mObservableScrollView = null;
    //  rlNumber = null;
    //  containerfab = cvGoToTypefaceSettings = null;
//      if (handler != null) {
//        //handler.removeCallbacks(runnable);
//        handler = null;
//      }
//      mCarouselView = null;
//      mailsAddedRecyclerAdapter = null;
//      gvCarrousel = null;
//      gridArrayAdapter = null;
//      Utils.clearTextLineCache();
//      if (ttsFileStrings != null) stopQuoteMicrophone();
//
//      if(mailsAddedRecyclerAdapter != null) {
//        if(mailsAddedRecyclerAdapter.soundPool != null) {
//          mailsAddedRecyclerAdapter.soundPool.get().release();
//          mailsAddedRecyclerAdapter.soundPool.get().setOnLoadCompleteListener(null);
//          mailsAddedRecyclerAdapter.soundPool = null;
//        }
//        mailsAddedRecyclerAdapter = null;
//      }
//      if(typefacesRecyclerAdapter != null) {
//        if(typefacesRecyclerAdapter.soundPool != null) {
//          typefacesRecyclerAdapter.soundPool.get().release();
//          typefacesRecyclerAdapter.soundPool.get().setOnLoadCompleteListener(null);
//          typefacesRecyclerAdapter.soundPool = null;
//        }
//        typefacesRecyclerAdapter = null;
//      }
//      if(mailsRecyclerAdapter != null) {
//        if(mailsRecyclerAdapter.soundPool != null) {
//          mailsRecyclerAdapter.soundPool.get().release();
//          mailsRecyclerAdapter.soundPool.get().setOnLoadCompleteListener(null);
//          mailsRecyclerAdapter.soundPool = null;
//        }
//        mailsRecyclerAdapter = null;
//      }
//
//      if (soundPool != null) {
//        soundPool.release();
//        soundPool.setOnLoadCompleteListener(null);
//        soundPool = null;
//      }

      if(soundPoolPlayers != null) {
        for (SoundPoolPlayer player : soundPoolPlayers.values()) {
          player.release();
          player.setOnCompletionListener(null);
          soundPoolPlayers.remove(player);
        }
      }
    }
    super.onDestroy();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    if (middlePage != null )outState.putInt("idQuote", middlePage.getIndex());
    if(gvCarrousel != null && gvCarrousel.getVisibility() == View.VISIBLE) {
      outState.putBoolean("isGvCarrouselIsOpened", true);
    }

    if(isFabOpen){
      outState.putBoolean("isFabOpen", true);
    } else {
      outState.putBoolean("isFabOpen", false);
    }

    if(cvImageComment != null && cvImageComment.getVisibility() == View.VISIBLE) {
      outState.putBoolean("isGvCarrouselIsOpened", true);
      outState.putBoolean("isImageCommentDisplaying", true);
    } else {
      outState.putBoolean("isImageCommentDisplaying", false);
    }

    if(ivExpandedPictures != null && ivExpandedPictures.getVisibility() == View.VISIBLE) {
      outState.putBoolean("isGvCarrouselIsOpened", true);
      outState.putBoolean("isExpandedImageDipslaying", true);
    } else {
      outState.putBoolean("isExpandedImageDipslaying", false);
    }

    outState.putBoolean("isTypefaceDialogShowing" , isTypefaceDialogShowing);
    outState.putBoolean("isMailDialogShowing", isMailDialogShowing);
    outState.putBoolean("isMicrophoneDialogShowing", isMicrophoneDialogShowing);
    outState.putBoolean("plays", plays);
    outState.putBoolean("isOnPause", isOnPause);

    if(ttsFileStrings != null) outState.putStringArrayList("ttsFileStrings",(ArrayList<String>) ttsFileStrings);
    outState.putString("type", type);
    outState.putInt("middlePagePosition", middlePagePosition);
    if (chunks != null ) outState.putStringArrayList("chunks", (ArrayList<String>) chunks);
    isOrientationChanged = true;
    outState.putBoolean("isFromMailOrTypefaceSetings", isFromMailOrTypefaceSetings);
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if(savedInstanceState != null) {
      idQuote = savedInstanceState.getInt("idQuote");
      isGvCarrouselIsOpened = savedInstanceState.getBoolean("isGvCarrouselIsOpened");
      isFabOpen = savedInstanceState.getBoolean("isFabOpen");
      isExpandedImageDipslaying = savedInstanceState.getBoolean("isExpandedImageDipslaying");
      isImageCommentDisplaying = savedInstanceState.getBoolean("isImageCommentDisplaying");
      type = savedInstanceState.getString("type");
      isTypefaceDialogShowing = savedInstanceState.getBoolean("isTypefaceDialogShowing");
      isMailDialogShowing = savedInstanceState.getBoolean("isMailDialogShowing");
      isMicrophoneDialogShowing = savedInstanceState.getBoolean("isMicrophoneDialogShowing");
      plays = savedInstanceState.getBoolean("plays");
      isOnPause = savedInstanceState.getBoolean("isOnPause");
      ttsFileStrings = savedInstanceState.getStringArrayList("ttsFileStrings");
      chunks = savedInstanceState.getStringArrayList("chunks");
      isFromMailOrTypefaceSetings = savedInstanceState.getBoolean("isFromMailOrTypefaceSetings");

      middlePagePosition = idQuote;
      isOrientationChanged = true;
    } else {
      idQuote = -1;
      type = getIntent().getStringExtra(getResources().getString(R.string.fromFragment));
      isOrientationChanged = false;
    }

    if (getIntent().hasExtra("isFromMailOrTypefaceSetings"))
      isFromMailOrTypefaceSetings = getIntent().getBooleanExtra("isFromMailOrTypefaceSetings", false);

    setContentView(R.layout.activity_contents);

    if(presenter == null) presenter = PresenterInjector.getContentsPresenter(this);
    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    editor = settings.edit();

    if(savedInstanceState != null) idQuote = settings.getInt(Constants.CONTENT_QUOTE_ID, -1);

    if (getIntent().getBooleanExtra("fromNotification", false)) {
      int badgecount = settings.getInt(Constants.BADGE_COUNT, 0);
      ShortcutBadger.applyCount(OnelittleAngelApplication.instance.getApplicationContext(), --badgecount);
      //getIntent().removeExtra("fromNotification");
      editor.putInt(Constants.BADGE_COUNT, badgecount).apply();
      if (getIntent().getBooleanExtra("fromQuoteNotification", false))
        editor.putBoolean(Constants.QUOTE_IS_NOTIFICATION_SENDED, false).apply();

      if (getIntent().getBooleanExtra("fromPictureNotification", false)) {

        editor.putBoolean(Constants.PICTURE_IS_NOTIFICATION_SENDED, false).apply();
      }

//editor.commit();
      editor.apply();
    }

    typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/" + settings.getString(Constants.TYPEFACE, "") + ".ttf");

    llSearch  = (LinearLayout) findViewById(R.id.ll_search);
    cvImageComment = (CardView) findViewById(R.id.cv_image_comment);

    txtnPositionQuotes = (TextViewNative) findViewById(R.id.txtn_position_quotes);
    etChooseQuotes = (AppCompatEditText) findViewById(R.id.et_choose_quotes);
    etChooseQuotes.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    etChooseQuotes.setHighlightColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    ColorStateList colorStateList = ColorStateList.valueOf(settings.getInt(CardViewNative.DARKERRGB, 0));
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      etChooseQuotes.setBackgroundTintList(colorStateList);
    } else {
      //noinspection RestrictedApi
      etChooseQuotes.setSupportBackgroundTintList(colorStateList);
    }
    Typeface tf = Typeface.createFromAsset(OnelittleAngelApplication.instance.getAssets(), "fonts/Monotype-Corsiva-Regular.ttf");
    etChooseQuotes.setTypeface(tf);

    txtnSizeQuotes = (TextViewNative) findViewById(R.id.txtn_quotes_number);

    etChooseQuotes.setOnClickListener(v -> {
      if (!etChooseQuotes.getText().toString().equals("")) {
        etChooseQuotes.setVisibility(View.GONE);
        txtnPositionQuotes.setVisibility(View.VISIBLE);
        goToQuote(Integer.parseInt(etChooseQuotes.getText().toString()));
        viewPager.setScrollDurationFactor(1);
        txtnPositionQuotes.setText(String.format("%s ", etChooseQuotes.getText().toString()));
        etChooseQuotes.setText("");
      }
    });

    txtDetails = (TextViewNative) findViewById(R.id.txt_details);
    Circle detailsCircle = (Circle) findViewById(R.id.details_circle);
    //FrameLayout flDetailsCircle = (FrameLayout) findViewById(R.id.fl_details_circle);
    Circle wallpapersAddCircle = (Circle) findViewById(R.id.wallpapers_add_circle);
    //FrameLayout flWallpapersAddCircle = (FrameLayout) findViewById(R.id.fl_wallpapers_add_circle);

    wallpapersAddCircle.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:

            if (!wallPapersDialog.isShowing()) {
              animate(wallpapersAddCircle).scaleX(1.65f).scaleY(1.65f).setDuration(200).start();
              //  animate(flWallpapersAddCircle).scaleX(1.65f).scaleY(1.65f).setDuration(200).start();
            } else {
              animate(wallpapersAddCircle).scaleX(1).scaleY(1).setDuration(200).start();
              //  animate(flWallpapersAddCircle).scaleX(1).scaleY(1).setDuration(200).start();
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

    ImageViewListener txtDetailsListener = new ImageViewListener(txtDetails, 1.05f);
    txtDetailsListener.setCallbacks(this);
    txtDetails.setOnTouchListener(txtDetailsListener);
    txtDetails.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    txtDetails.bringToFront();

    txtAuthorBookName = (TextViewNative) findViewById(R.id.txt_author_book_name);
    txtAuthorBookName.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    txtAuthorBookName.bringToFront();

    ImageViewListener txtAuthorBookListener = new ImageViewListener(txtAuthorBookName, 1.05f);
    txtAuthorBookListener.setCallbacks(this);
    txtAuthorBookName.setOnTouchListener(txtAuthorBookListener);

    if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {
      txtAuthorBookName.setVisibility(View.VISIBLE);
    } else {
      txtAuthorBookName.setVisibility(View.GONE);
    }

    txtBiographyFaiths = (TextViewNative) findViewById(R.id.txt_biography_faiths);
    ImageViewListener txtBiographyFaithsListener = new ImageViewListener(txtBiographyFaiths, 1.05f);
    txtBiographyFaithsListener.setCallbacks(this);
    txtBiographyFaiths.setOnTouchListener(txtBiographyFaithsListener);
    txtBiographyFaiths.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    mObservableScrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
    mObservableScrollView.setCallbacks(this);
    mObservableScrollView.setVerticalScrollBarEnabled(false);
    mObservableScrollView.setHorizontalScrollBarEnabled(false);
    llHeader = (LinearLayout) findViewById(R.id.ll_header);
    llNumber = (LinearLayout) findViewById(R.id.ll_number);
    FrameLayout flDrawerLayout = (FrameLayout) findViewById(R.id.drawer_layout);
    mPlaceholderView = findViewById(R.id.place_holder);
    FrameLayout flHeader = (FrameLayout) findViewById(R.id.fl_header);
    viewPager = (ViewPagerNative) findViewById(R.id.viewpager);

    containerfab = (CardView) findViewById(R.id.fab_layout);
    llFab = (LinearLayout) findViewById(R.id.ll_fab);

    fabAddToFavorites = (ImageView) findViewById(R.id.add_to_favorites);
    ImageViewListener fabAddToFavoritesListener = new ImageViewListener(fabAddToFavorites, 1.3f);
    fabAddToFavoritesListener.setCallbacks(this);
    fabAddToFavorites.setOnTouchListener(fabAddToFavoritesListener);

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
    fab_open.setFillAfter(true);
    fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
    fab_close.setFillAfter(true);

    alpha_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_in);
    alpha_in.setFillAfter(true);

    alpha_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_out);
    alpha_out.setFillAfter(true);

    rlnumber_alpha_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rlnumber_alpha_in);
    rlnumber_alpha_in.setFillAfter(true);

    rlnumber_alpha_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rlnumber_alpha_out);
    rlnumber_alpha_out.setFillAfter(true);

    rlnumber_alpha_out_no_duration = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rlnumber_alpha_out_no_duration);
    Animation rlnumber_alpha_in_no_duration = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rlnumber_alpha_in_no_duration);

    viewpager_alpha_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rlnumber_alpha_in);
    viewpager_alpha_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rlnumber_alpha_out);
    viewpager_alpha_out_no_duration = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rlnumber_alpha_out_no_duration);

    rlNumber = (LinearLayout) findViewById(R.id.rl_number);
    rlNumber.startAnimation(rlnumber_alpha_out_no_duration);


    ivAbHome = (ImageView) findViewById(R.id.iv_back);
    ImageViewListener ivAbHomeListener = new ImageViewListener(ivAbHome, 1.3f);
    ivAbHomeListener.setCallbacks(this);
    ivAbHome.setOnTouchListener(ivAbHomeListener);

    ivAbSearch = (ImageView) findViewById(R.id.ic_ab_search);
    ImageViewListener ivAbSearchListener = new ImageViewListener(ivAbSearch, 1.3f);
    ivAbSearchListener.setCallbacks(this);
    ivAbSearch.setOnTouchListener(ivAbSearchListener);

    arrowPrevious = (Arrow) findViewById(R.id.arrow_previous);
    ImageViewListener arrowPreviousListener = new ImageViewListener(arrowPrevious, 1.3f);
    arrowPreviousListener.setCallbacks(this);
    arrowPrevious.setOnTouchListener(arrowPreviousListener);

    arrowNext = (Arrow) findViewById(R.id.arrow_next);
    ImageViewListener arrowNextListener = new ImageViewListener(arrowNext, 1.3f);
    arrowNextListener.setCallbacks(this);
    arrowNext.setOnTouchListener(arrowNextListener);

    alpha_out.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {
      }

      @Override
      public void onAnimationEnd(Animation animation) {

        fabAddToFavorites.clearAnimation();
        fabAddToFavorites.setVisibility(View.GONE);
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

    viewpager_alpha_out.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {
      }

      @Override
      public void onAnimationEnd(Animation animation) {
        viewPager.setVisibility(View.GONE);
      }

      @Override
      public void onAnimationRepeat(Animation animation) {
      }
    });

    rlnumber_alpha_out.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {
      }

      @Override
      public void onAnimationEnd(Animation animation) {

        if (presenter.getPageModels().length > 1) {

          arrowPrevious.clearAnimation();
          arrowPrevious.setVisibility(View.GONE);

          ivAbSearch.clearAnimation();
          ivAbSearch.setVisibility(View.GONE);

          arrowNext.clearAnimation();
          arrowNext.setVisibility(View.GONE);
        }

        llSearch.clearAnimation();
        llSearch.setVisibility(View.GONE);

        llNumber.clearAnimation();
        llNumber.setVisibility(View.GONE);

        ivAbHome.clearAnimation();
        ivAbHome.setVisibility(View.GONE);

        rlNumber.clearAnimation();
        rlNumber.setVisibility(View.GONE);
        isRlNumberIsOpen = false;
      }

      @Override
      public void onAnimationRepeat(Animation animation) {
      }
    });


    fab_close.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {
      }

      @Override
      public void onAnimationEnd(Animation animation) {

        fabAddToFavorites.clearAnimation();
        fabAddToFavorites.setVisibility(View.GONE);

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

    ivQuoteLeft = (ImageView) findViewById(R.id.quote_left);
    ivQuoteRight = (ImageView) findViewById(R.id.quote_right);

    fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
    ivExpandedPictures = (ImageViewTouch) findViewById(R.id.expanded_image);

    mObservableScrollView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
      onScrollChanged(mObservableScrollView.getScrollY());
      mMaxScrollY = mObservableScrollView.computeVerticalScrollRange()
              - mObservableScrollView.getHeight();
      mQuickReturnHeight = rlNumber.getHeight() + 90;

      DisplayMetrics displaymetrics1 = new DisplayMetrics();

      if (Build.VERSION.SDK_INT >= 17){
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
          width = getWindowManager().getDefaultDisplay().getWidth();
          height = getWindowManager().getDefaultDisplay().getHeight();
        }

      } else {
        //This should be close, as lower API devices should not have window navigation bars
        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();
      }

      mQuickReturnWidthForTitlePage = ivQuoteLeft.getWidth();
      if (mViewPagerTop == 0) {
        mViewPagerTop = height;
      }
      if (gvCarrouselMarginTop == 0) gvCarrouselMarginTop = (int) (ivPictures.getBottom() + getResources().getDimension(R.dimen.activity_horizontal_double_margin));
    });

    DisplayMetrics displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    height = displaymetrics.heightPixels;
    width = displaymetrics.widthPixels;

    int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    txtDetails.measure(widthMeasureSpec, heightMeasureSpec);
    txtnPositionQuotes.measure(widthMeasureSpec, heightMeasureSpec);

    animate(viewPager).alpha(0).setDuration(0).start();
    mObservableScrollView.smoothScrollTo(0, 0);

    addRemoveFromDialog = new Dialog(ContentsActivity.this, R.style.myDialogSlideUpAndDown);
    addRemoveFromDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    addRemoveFromDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    addRemoveFromDialog.setContentView(R.layout.add_to_favorites_dialog);

    accountsDialog = new Dialog(ContentsActivity.this, R.style.myDialogSlideUpAndDown);
    accountsDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    accountsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    accountsDialog.setContentView(R.layout.accounts_dialog);

    accountsDialog.setOnDismissListener(dialog -> {
      animate(viewPager).alpha(1).setDuration(800).start();
      if(!txtAuthorBookName.getText().toString().equals("")) {
        txtAuthorBookName.setVisibility(View.VISIBLE);
        animate(txtAuthorBookName).alpha(1).setDuration(800).start();
      }

      if (!getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) && !getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {
        txtDetails.setVisibility(View.VISIBLE);
        animate(txtDetails).alpha(1).setDuration(800).start();
      }
    });



    txtGoToAccount = (TextViewNative) accountsDialog.findViewById(R.id.txt_accounts_ask);

    confirmGoToAccount = (TextView) accountsDialog.findViewById(R.id.confirm_go_to_account);
    confirmGoToAccount.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    cancelGoToAccount = (TextView) accountsDialog.findViewById(R.id.cancel_go_to_account);
    cancelGoToAccount.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    ImageViewListener cancelGoToAccountListener = new ImageViewListener(cancelGoToAccount, 1.3f);
    cancelGoToAccountListener.setCallbacks(this);
    cancelGoToAccount.setOnTouchListener(cancelGoToAccountListener);

    goToTypefaceSettingsDialog = new Dialog(ContentsActivity.this, R.style.myDialogSlideUpAndDown);
    goToTypefaceSettingsDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    goToTypefaceSettingsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    goToTypefaceSettingsDialog.setContentView(R.layout.typefaces_choice_dialog);

    goToTypefaceSettingsDialog.setOnDismissListener(dialog -> {
      animate(viewPager).alpha(1).setDuration(800).start();
      if(!txtAuthorBookName.getText().toString().equals("")) {
        txtAuthorBookName.setVisibility(View.VISIBLE);
        animate(txtAuthorBookName).alpha(1).setDuration(800).start();
      }

      if (!getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) && !getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {
        txtDetails.setVisibility(View.VISIBLE);
        animate(txtDetails).alpha(1).setDuration(800).start();
      }
    });

    goToTypefaceSettings = (TextView) goToTypefaceSettingsDialog.findViewById(R.id.go_to_typeface_settings);
    goToTypefaceSettings.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    ImageViewListener goToTypefaceSettingsListener = new ImageViewListener(goToTypefaceSettings, 1.05f);
    goToTypefaceSettingsListener.setCallbacks(this);
    goToTypefaceSettings.setOnTouchListener(goToTypefaceSettingsListener);

    typeFacesDialog = new Dialog(ContentsActivity.this, R.style.myDialogSlideUpAndDown);
    typeFacesDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    typeFacesDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    typeFacesDialog.setContentView(R.layout.typefaces_dialog);

    typeFacesDialog.setOnDismissListener(dialog -> {
      if (viewPager != null) animate(viewPager).alpha(1).setDuration(800).start();
      if(txtAuthorBookName != null && !txtAuthorBookName.getText().toString().equals("")) {
        txtAuthorBookName.setVisibility(View.VISIBLE);
        animate(txtAuthorBookName).alpha(1).setDuration(800).start();
      }

      if (!getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) && !getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {
        if (txtDetails != null) {
          txtDetails.setVisibility(View.VISIBLE);
          animate(txtDetails).alpha(1).setDuration(800).start();
        }
      }

      if(typefacesRecyclerAdapter != null) {
        if(typefacesRecyclerAdapter.soundPool != null) {
          typefacesRecyclerAdapter.soundPool.get().release();
          typefacesRecyclerAdapter.soundPool.get().setOnLoadCompleteListener(null);
          typefacesRecyclerAdapter.soundPool = null;
        }
        typefacesRecyclerAdapter = null;
      }

      isTypefaceDialogShowing = false;

    });

    typeFacesDialogListView = (RecyclerView) typeFacesDialog.findViewById(R.id.typefaces_dialog_list_view);

    typeFacesDialogListView.setLayoutManager(new LinearLayoutManager(this));
    typeFacesDialogListView.setNestedScrollingEnabled(false);

    confirmTypefaceChoiceDialog = new Dialog(ContentsActivity.this, R.style.myDialogSlideUpAndDown);
    confirmTypefaceChoiceDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    confirmTypefaceChoiceDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    confirmTypefaceChoiceDialog.setContentView(R.layout.typefaces_dialog_confirm);

    confirmTypefaceChoiceDialog.setOnDismissListener(dialog -> {
      animate(viewPager).alpha(1).setDuration(800).start();
      if(!txtAuthorBookName.getText().toString().equals("")) {
        txtAuthorBookName.setVisibility(View.VISIBLE);
        animate(txtAuthorBookName).alpha(1).setDuration(800).start();
      }

      if (!getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) && !getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {
        txtDetails.setVisibility(View.VISIBLE);
        animate(txtDetails).alpha(1).setDuration(800).start();
      }
    });

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

    microphonesDialog = new Dialog(ContentsActivity.this, R.style.myDialogSlideUpAndDown);

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

    confirmAddToFavorites = (TextView) addRemoveFromDialog.findViewById(R.id.confirm_add_to_favorites);
    ImageViewListener confirmAddToFavoritesListener = new ImageViewListener(confirmAddToFavorites, 1.3f);
    confirmAddToFavoritesListener.setCallbacks(this);
    confirmAddToFavorites.setOnTouchListener(confirmAddToFavoritesListener);

    cancelAddToFavorites = (TextView) addRemoveFromDialog.findViewById(R.id.cancel_add_to_favorites);
    ImageViewListener cancelAddToFavoritesListener = new ImageViewListener(cancelAddToFavorites, 1.3f);
    cancelAddToFavoritesListener.setCallbacks(this);
    cancelAddToFavorites.setOnTouchListener(cancelAddToFavoritesListener);

    confirmAddToFavorites.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    cancelAddToFavorites.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    mailsDialog = new Dialog(ContentsActivity.this, R.style.myDialogSlideUpAndDown);

    mailsDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    mailsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    mailsDialog.setContentView(R.layout.mails_dialog);

    sendMails = (TextViewNative) mailsDialog.findViewById(R.id.send_mails);
    ImageViewListener sendMailsListener = new ImageViewListener(sendMails, 1.3f);
    sendMailsListener.setCallbacks(this);
    sendMails.setOnTouchListener(sendMailsListener);

    sendMails.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    mailsDialogListview = (RecyclerView) mailsDialog.findViewById(R.id.mails_dialog_list_view);

    mailsDialogListview.setLayoutManager(new LinearLayoutManager(this));
    mailsDialogListview.setNestedScrollingEnabled(false);

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
      if(mailsAddedRecyclerAdapter != null) {
        for (int i = 0; i < mailsAddedRecyclerAdapter.holders.size(); i++) {
          mailsAddedRecyclerAdapter.holders.get(i).fontCb.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
          mailsAddedRecyclerAdapter.holders.get(i).fontCb.setBackgroundColor(Color.TRANSPARENT);
        }
      }

      if (!getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) && !getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {
        txtDetails.setVisibility(View.VISIBLE);
        animate(txtDetails).alpha(1).setDuration(800).start();
      }

      if(mailsAddedRecyclerAdapter != null) {
        if(mailsAddedRecyclerAdapter.soundPool != null) {
          mailsAddedRecyclerAdapter.soundPool.get().release();
          mailsAddedRecyclerAdapter.soundPool.get().setOnLoadCompleteListener(null);
          mailsAddedRecyclerAdapter.soundPool = null;
        }
        mailsAddedRecyclerAdapter = null;
      }

      if(mailsRecyclerAdapter != null) {
        if(mailsRecyclerAdapter.soundPool != null) {
          mailsRecyclerAdapter.soundPool.get().release();
          mailsRecyclerAdapter.soundPool.get().setOnLoadCompleteListener(null);
          mailsRecyclerAdapter.soundPool = null;
        }
        mailsRecyclerAdapter = null;
      }
    });

    ImageView ivAddEmail = (ImageView) mailsDialog.findViewById(R.id.iv_add_email);
    ImageViewListener ivAddEmailListener = new ImageViewListener(ivAddEmail, 1.3f);
    ivAddEmailListener.setCallbacks(this);
    ivAddEmail.setOnTouchListener(ivAddEmailListener);

    ivPictures = (ImageView) findViewById(R.id.iv_pictures);
    ImageViewListener ivPicturesListener = new ImageViewListener(ivPictures, 1.3f);
    ivPicturesListener.setCallbacks(this);
    ivPictures.setOnTouchListener(ivPicturesListener);
    OnelittleAngelApplication.instance.manageConnectivityState();

    if(!OnelittleAngelApplication.instance.isConnected()) ivPictures.setVisibility(View.GONE);

    carrouselDialog = new Dialog(ContentsActivity.this, R.style.myDialogSlideUpAndDown);
    carrouselDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    carrouselDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    carrouselDialog.setContentView(R.layout.dialog_carrousel);

    mCarouselView = (CarouselView) carrouselDialog.findViewById(R.id.carouselView);

    wallPapersDialog = new Dialog(ContentsActivity.this, R.style.myDialogSlideUpAndDown);
    wallPapersDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    wallPapersDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    wallPapersDialog.setContentView(R.layout.dialog_wallpaper_biography);

    wallPapersDialog.setOnDismissListener(dialog -> {
      animate(wallpapersAddCircle).scaleX(1).scaleY(1).setDuration(200).start();
    });

    animate(mCarouselView).alpha(0).start();

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

    picturesLoadingDialog = new Dialog(ContentsActivity.this, R.style.myDialogSlideUpAndDown);
    picturesLoadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    picturesLoadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    picturesLoadingDialog.setContentView(R.layout.biography_dialog);

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      SpinKitView spinKitView = (SpinKitView) picturesLoadingDialog.findViewById(R.id.spin_kit);
      Style style = Style.FADING_CIRCLE;
      Sprite drawable = SpriteFactory.create(style);
      spinKitView.setIndeterminateDrawable(drawable);

      spinKitView.setVisibility(View.VISIBLE);
      CardView cvBiographyDialog = (CardView) picturesLoadingDialog.findViewById(R.id.cv_biography_dialog);
      cvBiographyDialog.setVisibility(View.GONE);

      TextViewNative aNative = (TextViewNative) picturesLoadingDialog.findViewById(R.id.txtn_waiting);
      aNative.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
      aNative.setText(getResources().getString(R.string.loading_pictures));
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

    gvCarrousel = (CardGridView) findViewById(R.id.gv_carrousel);
    gridArrayAdapter = new CardGridArrayAdapter(this,presenter);
    gvCarrousel.setAdapter(gridArrayAdapter);
    gvCarrousel.bringToFront();

    dQuotesLoading = new Dialog(ContentsActivity.this, R.style.myDialogSlideUpAndDown);
    dQuotesLoading.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    dQuotesLoading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    dQuotesLoading.setContentView(R.layout.biography_dialog);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      SpinKitView spinKitView = (SpinKitView) dQuotesLoading.findViewById(R.id.spin_kit);
      Style style = Style.FADING_CIRCLE;
      Sprite drawable = SpriteFactory.create(style);
      spinKitView.setIndeterminateDrawable(drawable);
      spinKitView.setVisibility(View.VISIBLE);
      cvQuotesDialog = (CardView) dQuotesLoading.findViewById(R.id.cv_biography_dialog);
      cvQuotesDialog.setVisibility(View.GONE);
    }
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

  private void setErrorTextColor(TextInputLayout textInputLayout) {

    final Typeface tf = Typeface.createFromAsset(OnelittleAngelApplication.instance.getAssets(),"fonts/" + "Monotype-Corsiva-Regular.ttf");

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

  private void setTypefaceToInputLayout(TextInputLayout inputLayout){

    final Typeface tf = Typeface.createFromAsset(OnelittleAngelApplication.instance.getAssets(),"fonts/" + "Monotype-Corsiva-Regular.ttf");

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

      if(isValidEmail(email)) {
        inputLayoutEmail.setError(OnelittleAngelApplication.instance.getString(R.string.err_msg_email_not_valid));
      }

      if(email.isEmpty()) {
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
    editor.putString(Constants.MAIL_ACCOUNT,inputEmail.getText().toString().trim());
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

  private void goToQuote(int idQuote) {

    this.idQuote = idQuote;

    leftPage = presenter.getPageModels()[PAGE_LEFT];
    middlePage = presenter.getPageModels()[PAGE_MIDDLE];
    rightPage = presenter.getPageModels()[PAGE_RIGHT];

    oldLeftIndex = leftPage.getIndex();
    oldMiddleIndex = middlePage.getIndex();
    oldRightIndex = rightPage.getIndex();

    if (idQuote == presenter.getPageModels().length) {

      leftPage.setIndex(idQuote - 1, presenter.getPageModels()[idQuote - 1].getQuote());
      middlePage.setIndex(idQuote, presenter.getPageModels()[0].getQuote());
      rightPage.setIndex(1, presenter.getPageModels()[1].getQuote());
      mSelectedPageIndex = -1;
      setContent(PAGE_LEFT);
      setContent(PAGE_MIDDLE);
      setContent(PAGE_RIGHT);

      if (presenter.getPageModels()[0].getQuote().isFavorites()) {
        fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));

        fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
      } else {
        fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
        fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
      }
    } else if (idQuote == 1) {

      leftPage.setIndex(presenter.getPageModels().length, presenter.getPageModels()[presenter.getPageModels().length - 1].getQuote());
      middlePage.setIndex(idQuote, presenter.getPageModels()[1].getQuote());
      rightPage.setIndex(idQuote + 1, presenter.getPageModels()[idQuote + 1].getQuote());

      mSelectedPageIndex = -1;
      setContent(PAGE_LEFT);
      setContent(PAGE_MIDDLE);
      setContent(PAGE_RIGHT);

      if (presenter.getPageModels()[1].getQuote().isFavorites()) {
        fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
        fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
      } else {
        fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
        fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
      }

    } else if (idQuote == presenter.getPageModels().length - 1) {
      leftPage.setIndex(idQuote - 1, presenter.getPageModels()[idQuote - 1].getQuote());
      middlePage.setIndex(idQuote, presenter.getPageModels()[idQuote].getQuote());
      rightPage.setIndex(idQuote + 1, presenter.getPageModels()[0].getQuote());

      mSelectedPageIndex = -1;
      setContent(PAGE_LEFT);
      setContent(PAGE_MIDDLE);
      setContent(PAGE_RIGHT);

      if (presenter.getPageModels()[idQuote].getQuote().isFavorites()) {
        fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
        fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
      } else {
        fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
        fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
      }

    }  else {
      leftPage.setIndex(idQuote - 1, presenter.getPageModels()[idQuote - 1].getQuote());
      middlePage.setIndex(idQuote, presenter.getPageModels()[idQuote].getQuote());
      rightPage.setIndex(idQuote + 1, presenter.getPageModels()[idQuote + 1].getQuote());

      mSelectedPageIndex = -1;
      setContent(PAGE_LEFT);
      setContent(PAGE_MIDDLE);
      setContent(PAGE_RIGHT);

      if (presenter.getPageModels()[idQuote].getQuote().isFavorites()) {
        fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
        fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
      } else {
        fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
        fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
      }

    }
  }

  private void swipeOnLeft() {
    // moving each page content one page to the right
    if (!presenter.isPageModelsIsSizeOfTwo()) {
      if (oldMiddleIndex == 1) {
        leftPage.setIndex(presenter.getPageModels().length - 1, presenter.getPageModels()[presenter.getPageModels().length - 1].getQuote());
        middlePage.setIndex(presenter.getPageModels().length, presenter.getPageModels()[0].getQuote());
        rightPage.setIndex(oldMiddleIndex, presenter.getPageModels()[oldMiddleIndex].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        animate(arrowPrevious).setListener(null);

        if (presenter.getPageModels()[0].getQuote().isFavorites()) {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }

      } else if (oldMiddleIndex == presenter.getPageModels().length) {
        leftPage.setIndex(oldLeftIndex - 1, presenter.getPageModels()[oldLeftIndex - 1].getQuote());
        middlePage.setIndex(oldLeftIndex, presenter.getPageModels()[oldLeftIndex].getQuote());
        rightPage.setIndex(oldMiddleIndex, presenter.getPageModels()[oldMiddleIndex - 1].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        animate(arrowPrevious).setListener(null);

        if (presenter.getPageModels()[oldLeftIndex].getQuote().isFavorites()) {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }

      } else if (oldMiddleIndex == 2) {

        leftPage.setIndex(presenter.getPageModels().length, presenter.getPageModels()[presenter.getPageModels().length - 1].getQuote());
        middlePage.setIndex(1, presenter.getPageModels()[1].getQuote());
        rightPage.setIndex(oldMiddleIndex, presenter.getPageModels()[oldMiddleIndex].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        animate(arrowPrevious).setListener(null);

        if (presenter.getPageModels()[oldLeftIndex].getQuote().isFavorites()) {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }

      } else {
        leftPage.setIndex(oldLeftIndex - 1, adapter.getmPageModels()[oldLeftIndex - 1].getQuote());
        middlePage.setIndex(oldLeftIndex, adapter.getmPageModels()[oldLeftIndex].getQuote());
        rightPage.setIndex(oldMiddleIndex, adapter.getmPageModels()[oldMiddleIndex].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        animate(arrowPrevious).setListener(null);

        if (presenter.getPageModels()[oldLeftIndex].getQuote().isFavorites()) {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }

      }
    } else {
      if (oldMiddleIndex == 1) {
        leftPage.setIndex(1, presenter.getPageModels()[1].getQuote());
        middlePage.setIndex(2, presenter.getPageModels()[0].getQuote());
        rightPage.setIndex(1, presenter.getPageModels()[1].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        animate(arrowPrevious).setListener(null);

        if (presenter.getPageModels()[2].getQuote().isFavorites()) {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }
      } else if (oldMiddleIndex == 2) {
        leftPage.setIndex(2, presenter.getPageModels()[0].getQuote());
        middlePage.setIndex(1, presenter.getPageModels()[1].getQuote());
        rightPage.setIndex(2, presenter.getPageModels()[0].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        animate(arrowPrevious).setListener(null);

        if (presenter.getPageModels()[1].getQuote().isFavorites()) {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }
      }
    }
  }

  private void swipeOnRight() {

    if (!presenter.isPageModelsIsSizeOfTwo()) {
      if (oldMiddleIndex == 1) {
        leftPage.setIndex(oldMiddleIndex, presenter.getPageModels()[oldMiddleIndex].getQuote());
        middlePage.setIndex(oldMiddleIndex + 1, presenter.getPageModels()[oldMiddleIndex + 1].getQuote());
        if (oldMiddleIndex + 2 != presenter.getPageModels().length) {
          rightPage.setIndex(oldMiddleIndex + 2, presenter.getPageModels()[oldMiddleIndex + 2].getQuote());
        } else {
          rightPage.setIndex(oldMiddleIndex + 2, presenter.getPageModels()[0].getQuote());
        }

        setContent(PAGE_LEFT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_RIGHT);
        animate(arrowNext).setListener(null);

        if (presenter.getPageModels()[oldMiddleIndex + 1].getQuote().isFavorites()) {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }
      } else if (oldMiddleIndex == (presenter.getPageModels().length - 1)) {
        leftPage.setIndex(oldMiddleIndex, presenter.getPageModels()[oldMiddleIndex].getQuote());
        middlePage.setIndex(presenter.getPageModels().length, presenter.getPageModels()[0].getQuote());
        rightPage.setIndex(1, presenter.getPageModels()[1].getQuote());

        setContent(PAGE_LEFT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_RIGHT);
        animate(arrowNext).setListener(null);

        if (presenter.getPageModels()[0].getQuote().isFavorites()) {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }

      } else if (oldMiddleIndex == (presenter.getPageModels().length - 2)) {
        leftPage.setIndex(oldMiddleIndex, presenter.getPageModels()[oldMiddleIndex].getQuote());
        middlePage.setIndex(oldRightIndex, presenter.getPageModels()[oldRightIndex].getQuote());
        rightPage.setIndex(presenter.getPageModels().length - 1, presenter.getPageModels()[presenter.getPageModels().length - 1].getQuote());

        setContent(PAGE_LEFT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_RIGHT);
        animate(arrowNext).setListener(null);

        if (presenter.getPageModels()[oldRightIndex].getQuote().isFavorites()) {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }

      } else if (oldMiddleIndex == presenter.getPageModels().length) {
        leftPage.setIndex(oldMiddleIndex, presenter.getPageModels()[0].getQuote());
        middlePage.setIndex(1, presenter.getPageModels()[1].getQuote());
        rightPage.setIndex(oldRightIndex + 1, presenter.getPageModels()[oldRightIndex + 1].getQuote());

        setContent(PAGE_LEFT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_RIGHT);
        animate(arrowNext).setListener(null);

        if (presenter.getPageModels()[1].getQuote().isFavorites()) {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }

      } else {
        leftPage.setIndex(oldMiddleIndex, presenter.getPageModels()[oldMiddleIndex].getQuote());
        middlePage.setIndex(oldRightIndex, presenter.getPageModels()[oldRightIndex].getQuote());
        rightPage.setIndex(oldRightIndex + 1, presenter.getPageModels()[oldRightIndex + 1].getQuote());

        setContent(PAGE_LEFT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_RIGHT);

        if (presenter.getPageModels()[oldRightIndex].getQuote().isFavorites()) {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }
        animate(arrowNext).setListener(null);
      }
    } else {
      if (oldMiddleIndex == 1) {
        leftPage.setIndex(0, presenter.getPageModels()[1].getQuote());
        middlePage.setIndex(2, presenter.getPageModels()[2].getQuote());
        rightPage.setIndex(1, presenter.getPageModels()[1].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        animate(arrowNext).setListener(null);

        if (presenter.getPageModels()[2].getQuote().isFavorites()) {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }
      } else if (oldMiddleIndex == 2) {
        leftPage.setIndex(0, presenter.getPageModels()[2].getQuote());
        middlePage.setIndex(1, presenter.getPageModels()[1].getQuote());
        rightPage.setIndex(2, presenter.getPageModels()[2].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        animate(arrowNext).setListener(null);

        if (presenter.getPageModels()[1].getQuote().isFavorites()) {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
          fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }
      }
    }
  }

  private void setContent(int position) {

    viewPager.setPagingEnabled(false);

    PageModel model;

    if (position == PAGE_MIDDLE) {

      if (presenter.getPageModels().length > 1) {
        model = presenter.getPageModels()[position];

        model.setTypeface(typeface);
        if (model.textView == null) {

          model.llTxtView = presenter.getPageModels()[position].llTxtView;
          model.sourceTxtView = presenter.getPageModels()[position].sourceTxtView;
          model.cardView = presenter.getPageModels()[position].cardView;
        }
      } else {
        model = presenter.getPageModels()[0];

        model.setTypeface(typeface);

        if (model.textView == null) {

          model.llTxtView = presenter.getPageModels()[0].llTxtView;
          model.sourceTxtView = presenter.getPageModels()[0].sourceTxtView;
          model.cardView = presenter.getPageModels()[0].cardView;
        }
      }

      ViewHelper.setAlpha(model.cardView, 0);
      ViewHelper.setAlpha(model.llTxtView, 0);
      ViewHelper.setAlpha(model.sourceTxtView, 0);


      if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

      //ViewHelper.setAlpha(model.cardView, 0);

      //animate(model.textView).alpha(0).start();

      Handler handler = new Handler();

      handler.postDelayed(new Runnable() {
        @Override
        public void run() {

          String s = "  ";
          int j = -1;
          int k;
          boolean isWhiteSpaced = false;
          int widthMeasureSpec, heightMeasureSpec;
          int uWidth = 0;

          DisplayMetrics displaymetrics = new DisplayMetrics();
          getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
          int width = displaymetrics.widthPixels;

          widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
          heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

          if (model.llTxtView.getChildCount() <= 1 || textSize != settings.getFloat(Constants.TEXTSIZE, 0)) {

            model.llTxtView.removeAllViews();

            if(model.contentTextViewNatives != null && model.contentTextViewNatives.size() != 0){
              for(int i = 0; i < model.contentTextViewNatives.size(); i++) {
                model.llTxtView.removeView(model.contentTextViewNatives.get(i));
              }
            }

            model.contentTextViewNatives = new ArrayList<>();
            ContentTextViewNativeBiography aNative1 = new ContentTextViewNativeBiography(ContentsActivity.this);
            aNative1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            aNative1.setIncludeFontPadding(false);
            aNative1.setText("a");
            aNative1.measure(0, 0);
            model.llTxtView.measure(widthMeasureSpec, heightMeasureSpec);
            uWidth = (int) ((model.llTxtView.getMeasuredWidth() - getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) / aNative1.getMeasuredWidth());

            ContentTextViewNativeBiography aNative = new ContentTextViewNativeBiography(ContentsActivity.this);
            k = uWidth * 3;

            for (int i = 0; i < model.getText().length(); i += k) {
              boolean isLastLine = false;
              if (model.getText().length() > i + uWidth * 3) {
                s += model.getText().substring(i, i + uWidth * 3);

              } else {
                s += model.getText().substring(i, model.getText().length());
                isLastLine = true;
              }
              isWhiteSpaced = false;

              do {

                if (s.length() != 0 && (!Character.isWhitespace(s.charAt(s.length() - 1)))) {
                  s = s.substring(0, s.length() - 1);
                }

                if (s.length() != 0 && (Character.isWhitespace(s.charAt(s.length() - 1)))) {
                  model.llTxtView.removeView(aNative);
                  aNative = new ContentTextViewNativeBiography(ContentsActivity.this);
                  aNative.setIncludeFontPadding(false);

                  aNative.setText(s);
                  model.llTxtView.addView(aNative);
                  model.layout.measure(widthMeasureSpec, heightMeasureSpec);
                  if (!isLastLine && aNative.lastLinePosition != 0) {
                    s = s.substring(0, aNative.lastLinePosition);
                  }

                  if (s.length() != 0 && s.charAt(s.length() - 1) == '\n') {
                    s = s.substring(0, s.length() - 1);

                    k = s.length() + 1;
                  } else {
                    k = s.length();

                  }

                  if (j == -1) k = s.length() - 2;

                  model.llTxtView.removeView(aNative);

                  aNative = new ContentTextViewNativeBiography(ContentsActivity.this);

                  aNative.setIncludeFontPadding(false);
                  aNative.setText(s);
                  aNative.setCallbacks(adapter);
                  model.contentTextViewNatives.add(aNative);
                  isWhiteSpaced = true;
                } else if (isLastLine) {
                  s = model.getText().substring(i, model.getText().length());
                  k = s.length();
                  aNative = new ContentTextViewNativeBiography(ContentsActivity.this);
                  aNative.setIncludeFontPadding(false);
                  aNative.setText(s);
                  aNative.setCallbacks(adapter);
                  model.llTxtView.addView(aNative);
                  model.layout.measure(widthMeasureSpec, heightMeasureSpec);
                  model.llTxtView.removeView(aNative);
                  model.contentTextViewNatives.add(aNative);
                  isWhiteSpaced = true;
                }
              }
              while (!isWhiteSpaced);
              if (i == 0) {
                j = 0;
              } else {
                j++;
              }
              s = "";
            }
          }

          if (model.llTxtView.getChildCount() <= 1) {
            for (int i = 0; i < model.contentTextViewNatives.size(); i++) {
              model.contentTextViewNatives.get(i).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
              model.llTxtView.addView(model.contentTextViewNatives.get(i));
            }
          }

          model.sourceTxtView.setText(model.getSourceText());

          if(isTypefaceDialogShowing && isTypefaceChanged) {
            isTypefaceDialogShowing = false;
            isTypefaceChanged = false;

            viewPager.setVisibility(View.VISIBLE);
            viewPager.startAnimation(viewpager_alpha_in);
          }

          isAuthor = model.isAuthor;

          if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {

            txtDetails.setVisibility(View.GONE);
            if (model.getQuote().getAuthor() != null) {
              txtAuthorBookName.setText(String.format("%s%s", model.getAuthorBookNameText(), getResources().getString(R.string.space)));

              if (model.getDetailText() != null && !model.getDetailText().equals("")) {

              } else {

              }
            } else if (model.getQuote().getBook() != null) {
              txtAuthorBookName.setText(String.format("%s%s", model.getAuthorBookNameText(), getResources().getString(R.string.space)));

              if (model.getDetailText() != null && !model.getDetailText().equals("")) {

              } else {

              }
            } else {
              txtAuthorBookName.setVisibility(View.GONE);
            }
          }

          if (!model.isPresentationOrBibliography) {
            txtAuthorBookName.setOnTouchListener(null);
            if (txtAuthorBookName.getText().length() != 0 && txtAuthorBookName.getVisibility() != View.GONE) {
              txtAuthorBookName.setTextColor(Color.BLACK);
            }
            txtDetails.setOnTouchListener(null);
            if (txtDetails.getText().length() != 0 && txtDetails.getVisibility() != View.GONE) {
              txtDetails.setTextColor(Color.BLACK);

              if (txtDetails.getText().toString().equals(getResources().getString(R.string.biography)) || txtDetails.getText().toString().equals(getResources().getString(R.string.presentation)) || txtDetails.getText().toString().equals(getResources().getString(R.string.biography_and_calligraphy)) || txtDetails.getText().toString().equals(getResources().getString(R.string.presentation_and_calligraphy))) {
                txtDetails.setText("");
              } else {
                txtDetails.setVisibility(View.VISIBLE);
              }
            }
          } else {
            if (txtDetails.getText().length() != 0 && txtDetails.getVisibility() != View.GONE) {
              txtDetails.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
            }
            ImageViewListener txtDetailsListener = new ImageViewListener(txtDetails, 1.05f);
            txtDetailsListener.setCallbacks(ContentsActivity.this);
            txtDetails.setOnTouchListener(txtDetailsListener);

            txtDetails.bringToFront();

            if (txtAuthorBookName.getText().length() != 0 && txtAuthorBookName.getVisibility() != View.GONE) {
              txtAuthorBookName.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
            }
            ImageViewListener txtAuthorBookListener = new ImageViewListener(txtAuthorBookName, 1.05f);
            txtAuthorBookListener.setCallbacks(ContentsActivity.this);
            txtAuthorBookName.setOnTouchListener(txtAuthorBookListener);
            txtAuthorBookName.bringToFront();
          }

          viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
              int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(middlePage.cardView.getWidth(), View.MeasureSpec.AT_MOST);
              int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
              middlePage.cardView.measure(widthMeasureSpec, heightMeasureSpec);
              txtnPositionQuotes.measure(widthMeasureSpec, heightMeasureSpec);

              int i;
              if(txtDetails.getVisibility() == View.VISIBLE) {
                i = middlePage.cardView.getMeasuredHeight() + ((int) getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) + mObservableHeight;
              } else {
                i = middlePage.cardView.getMeasuredHeight() + ((int) getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) + mObservableHeight;
              }

              if (mViewPagerTop > i) {
                viewPager.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
              } else {
                viewPager.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, i));
              }

              PersonalLinearLayout.LayoutParams params1 = (PersonalLinearLayout.LayoutParams) middlePage.cardView.getLayoutParams();
              params1.topMargin = mObservableHeight;
              middlePage.cardView.setLayoutParams(params1);

              if (presenter.isPageModelsIsSizeOfTwo()) {
                txtnPositionQuotes.setText(String.format("%d ", ((model.getIndex() + 1) /*- presenter.getPageModels().length*/ % 2) + 1));
              } else {
                txtnPositionQuotes.setText(String.format("%d ", model.getIndex()));
              }

              if(!isRlNumberIsOpen) rlNumber.setVisibility(View.GONE);

              else if (isRlNumberIsOpen && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                params.topMargin = (int) (llHeader.getBottom() + getResources().getDimension(R.dimen.action_bar_margin)  /** 4*/);
                rlNumber.setLayoutParams(params);
              }

              animate(model.cardView).alpha(1).setDuration(800).start();
              animate(model.llTxtView).alpha(1).setDuration(800).start();
              animate(model.sourceTxtView).alpha(1).setDuration(800).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                  super.onAnimationEnd(animation);
                  if (viewPager != null && !viewPager.isPagingEnabled()) viewPager.setPagingEnabled(true);

                }
              }).start();


              if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
              } else {
                viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
              }
            }
          });

          if (isForMicrophone && isMicrophoneNextOrPrevious) {
            playQuoteMicrophone();
            isMicrophoneNextOrPrevious = false;
          }
          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }
      }, 100);

    }
  }

  @Override
  public void onScroll(float deltaY) {
    animate(ivQuoteLeft).translationY(-deltaY);
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

        if(llHeader.getVisibility() == View.VISIBLE) {
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
        if(llHeader.getVisibility() == View.VISIBLE) {
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
        if(llHeader.getVisibility() == View.VISIBLE) {
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
  public void toggleFloatingActionButton() {

    if((plays || isOnPause) && soundPoolPlayers!= null && soundPoolPlayers.size() != 0){
      microphonesDialog.show();
      isMicrophoneDialogShowing = true;
      return;
    }

    if (!isFabOpen) {
      if(!isRlNumberIsOpen) {

        rlNumber.setVisibility(View.VISIBLE);
        rlNumber.startAnimation(rlnumber_alpha_in);

        if (presenter.getPageModels().length > 1) {

          arrowPrevious.setVisibility(View.VISIBLE);
          arrowPrevious.startAnimation(rlnumber_alpha_in);

          ivAbSearch.setVisibility(View.VISIBLE);
          ivAbSearch.startAnimation(rlnumber_alpha_in);

          arrowNext.setVisibility(View.VISIBLE);
          arrowNext.startAnimation(rlnumber_alpha_in);
        }

        llNumber.setVisibility(View.VISIBLE);
        llNumber.startAnimation(rlnumber_alpha_in);

        llSearch.setVisibility(View.VISIBLE);
        llSearch.startAnimation(rlnumber_alpha_in);

        ivAbHome.setVisibility(View.VISIBLE);
        ivAbHome.startAnimation(rlnumber_alpha_in);



        isRlNumberIsOpen = true;
      }

      containerfab.setVisibility(View.VISIBLE);
      containerfab.startAnimation(alpha_in);
      llFab.setVisibility(View.VISIBLE);
      llFab.startAnimation(alpha_in);
      fabAddToFavorites.setVisibility(View.VISIBLE);
      fabAddToFavorites.startAnimation(fab_open);
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

      if(isRlNumberIsOpen) {
        rlNumber.startAnimation(rlnumber_alpha_out);
        isRlNumberIsOpen = false;
      }

      containerfab.startAnimation(alpha_out);
      llFab.startAnimation(alpha_out);
      fabAddToFavorites.startAnimation(fab_close);
      fabTxtIncrease.startAnimation(fab_close);
      fabTxtDecrease.startAnimation(fab_close);
      fabSocialNetworks.startAnimation(fab_close);
      fabTypefaces.startAnimation(fab_close);
      fabMicrophone.startAnimation(fab_close);
      isFabOpen = false;
    }
  }

  @Override
  public void onScaling(float size, int resId) {

    if (viewPager.isPagingEnabled()) viewPager.setPagingEnabled(false);

    if (resId == R.id.source) {
      middlePage.sourceTxtView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

      for(int i = 0; i < middlePage.contentTextViewNatives.size(); i++) {
        ContentTextViewNativeBiography nativeBiography = (ContentTextViewNativeBiography) middlePage.contentTextViewNatives.get(i);
        nativeBiography.setTextSize(TypedValue.COMPLEX_UNIT_PX, size * 1.5f);
      }
    } else {
      middlePage.sourceTxtView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size / 1.5f);

      for(int i = 0; i < middlePage.contentTextViewNatives.size(); i++) {
        ContentTextViewNativeBiography nativeBiography = (ContentTextViewNativeBiography) middlePage.contentTextViewNatives.get(i);
        nativeBiography.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
      }
    }

    viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(middlePage.cardView.getWidth(), View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        middlePage.cardView.measure(widthMeasureSpec, heightMeasureSpec);

        if (txtDetails.getVisibility() == View.VISIBLE) {
        }
        txtnPositionQuotes.measure(widthMeasureSpec, heightMeasureSpec);

        int i;
        if (txtDetails.getVisibility() == View.VISIBLE) {
          i = middlePage.cardView.getMeasuredHeight() + ((int) getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) + mObservableHeight;
        } else {
          i = middlePage.cardView.getMeasuredHeight() + ((int) getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) + mObservableHeight;
        }
        if (mViewPagerTop > i) {
          viewPager.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        } else {
          viewPager.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, i));
        }

        PersonalLinearLayout.LayoutParams params = (PersonalLinearLayout.LayoutParams) middlePage.cardView.getLayoutParams();
        params.topMargin = mObservableHeight;
        middlePage.cardView.setLayoutParams(params);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
          viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
          viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
      }
    });
  }

  @Override
  public void onScaleEnd() {
    setContent(PAGE_MIDDLE);
  }

  @Override
  public void changeTypeFace(String tpString) {
    if (!tpString.trim().equals("")) {
      typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/" + tpString.trim() + ".ttf");
    } else {
      tpString = "Monotype-Corsiva-Regular";
      this.tpString = "Monotype-Corsiva-Regular";
      typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/" + tpString + ".ttf");
    }
    this.tpString = settings.getString(Constants.TYPEFACE, "");

    editor.putString(Constants.TYPEFACE, tpString.trim());
    editor.commit();
    isTypefaceChanged = true;
    if (idQuote != -1) middlePagePosition = idQuote;
    else middlePagePosition = settings.getInt(Constants.CONTENT_QUOTE_ID, 0);

    presenter.init(true);
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

    setContent(PAGE_LEFT);
    setContent(PAGE_MIDDLE);
    setContent(PAGE_RIGHT);
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

  }

  @Override
  public void showAddRemoveFavoritesDialog() {

    if(isFabOpen) {
      if(isRlNumberIsOpen) {
        rlNumber.startAnimation(rlnumber_alpha_out);
        isRlNumberIsOpen = false;
      }
      containerfab.startAnimation(alpha_out);
      fabAddToFavorites.startAnimation(fab_close);
      fabTxtIncrease.startAnimation(fab_close);
      fabTxtDecrease.startAnimation(fab_close);
      fabSocialNetworks.startAnimation(fab_close);
      fabTypefaces.startAnimation(fab_close);
      fabMicrophone.startAnimation(fab_close);

      isFabOpen = false;
    }

    txt = (TextViewNative) addRemoveFromDialog.findViewById(R.id.txt_add_to_favorites_ask);
    if (fabAddToFavorites.getTag().equals(R.drawable.ic_menu_remove_favorites)) {
      txt.setText(getResources().getString(R.string.removing_to_favorites_ask));
    } else {
      txt.setText(getResources().getString(R.string.adding_to_favorites_ask));
    }
    if(!isFinishing()) addRemoveFromDialog.show();
  }

  @Override
  public void confirmAddRemoveDialog() {
    if ((int) fabAddToFavorites.getTag() == R.drawable.ic_menu_add_favorites) {
      fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
      fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
      if (middlePage.getIndex() == presenter.getPageModels().length) {
        presenter.toggleQuoteIsFavorites((int) presenter.getPageModels()[0].getQuote().getIdQuote());
      } else {
        presenter.toggleQuoteIsFavorites((int) presenter.getPageModels()[middlePage.getIndex()].getQuote().getIdQuote());
      }
    } else {
      fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
      fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
      if (middlePage.getIndex() == presenter.getPageModels().length)
        presenter.toggleQuoteIsFavorites((int) presenter.getPageModels()[0].getQuote().getIdQuote());
      else
        presenter.toggleQuoteIsFavorites((int) presenter.getPageModels()[middlePage.getIndex()].getQuote().getIdQuote());
    }
    addRemoveFromDialog.cancel();
  }

  @Override
  public void cancelAddRemoveDialog() {
    addRemoveFromDialog.cancel();
  }

  @Override
  public void showSocialNetworksDialog() {
    if(!isFinishing()) socialNetworksDialog.show();
  }

  @Override
  public void showTypeFaceDialog() {

    containerfab.startAnimation(alpha_out);
    fabAddToFavorites.startAnimation(fab_close);
    fabTxtIncrease.startAnimation(fab_close);
    fabTxtDecrease.startAnimation(fab_close);
    fabSocialNetworks.startAnimation(fab_close);
    fabTypefaces.startAnimation(fab_close);
    fabMicrophone.startAnimation(fab_close);
    animate(viewPager).alpha(0).setDuration(800).start();
    animate(txtAuthorBookName).alpha(0).setDuration(800).start();
    animate(txtDetails).alpha(0).setDuration(800).start();
    if(isRlNumberIsOpen) {
      rlNumber.startAnimation(rlnumber_alpha_out);
      isRlNumberIsOpen = false;
    }
    isFabOpen = false;

    String[] strings = settings.getString(Constants.TYPEFACESLIST, "").split(";");

    if (settings.getString(Constants.TYPEFACESLIST, "") == null || settings.getString(Constants.TYPEFACESLIST, "").equals("")) {
      if(!isFinishing()) goToTypefaceSettingsDialog.show();
    } else {
      typefacesRecyclerAdapter = new TypefacesRecyclerAdapter(getApplicationContext(), strings);
      typefacesRecyclerAdapter.setCallbacks(this);

      typeFacesDialogListView.setAdapter(typefacesRecyclerAdapter);
      if(!isFinishing()) typeFacesDialog.show();
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
    if(isRlNumberIsOpen) {
      rlNumber.startAnimation(rlnumber_alpha_out);
      isRlNumberIsOpen = false;
    }
    containerfab.startAnimation(alpha_out);
    fabAddToFavorites.startAnimation(fab_close);
    fabSocialNetworks.startAnimation(fab_close);
    fabTxtIncrease.startAnimation(fab_close);
    fabTxtDecrease.startAnimation(fab_close);
    fabTypefaces.startAnimation(fab_close);
    fabMicrophone.startAnimation(fab_close);
    isFabOpen = false;
    isMicrophoneDialogShowing = true;

    if(!isFinishing()) microphonesDialog.show();
  }

  @Override
  public void openFullScreenMode() {
  }

  @Override
  public void startBiographyActivity() {

    Intent iBiography = new Intent(ContentsActivity.this, BiographyActivity.class);
    if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {

      iBiography.putExtra(getResources().getString(R.string.from), middlePage.getAuthorBookNameText());
      if (getIntent().getStringExtra("fromContent") != null) {
        iBiography.putExtra("value", getIntent().getStringExtra("fromContent"));
      } else {
        iBiography.putExtra("value", getIntent().getStringExtra(getIntent().getStringExtra(getResources().getString(R.string.from))));
      }
    } else {
      if (getIntent().getStringExtra("fromContent") != null) {
        iBiography.putExtra(getResources().getString(R.string.from), getIntent().getStringExtra("fromContent"));
      } else {
        if (getIntent().getStringExtra(getIntent().getStringExtra(getResources().getString(R.string.from))) != null)
          iBiography.putExtra(getResources().getString(R.string.from), getIntent().getStringExtra(getIntent().getStringExtra(getResources().getString(R.string.from))));
        else iBiography.putExtra(getResources().getString(R.string.from), getIntent().getStringExtra(getResources().getString(R.string.from)));
      }
    }

    iBiography.putExtra("initTitlePage",initTitlePage.trim());
    iBiography.putExtra("idContentQuote", txtnPositionQuotes.getText().toString());
    iBiography.putExtra("type", getIntent().getStringExtra(getResources().getString(R.string.from)));
    iBiography.putExtra("fromActivity", "ContentsActivity");
    iBiography.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));
    iBiography.putExtra("isAuthor", isAuthor);
    startActivity(iBiography);
    try {
      ActivityAnimator anim = new ActivityAnimator();
      anim.fadeAnimation((ContentsActivity.this));
    } catch (Exception ignored) {
    }
    finish();
  }

  @Override
  public void startFaithsBiography() {

    Intent iBiography = new Intent(ContentsActivity.this, BiographyActivity.class);
    if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {

      iBiography.putExtra(getResources().getString(R.string.from),initTitlePage.trim());
    }
    iBiography.putExtra("idContentQuote", txtnPositionQuotes.getText().toString());
    iBiography.putExtra("type", getIntent().getStringExtra(getResources().getString(R.string.from)));
    iBiography.putExtra("fromActivity", "ContentsActivity");
    iBiography.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));
    iBiography.putExtra("isMovement", true);
    startActivity(iBiography);
    try {
      ActivityAnimator anim = new ActivityAnimator();
      anim.fadeAnimation((ContentsActivity.this));
    } catch (Exception ignored) {
    }
    finish();
  }

  @Override
  public void arrowPrevious() {
    if (isFabOpen) {
      containerfab.startAnimation(alpha_out);
      fabAddToFavorites.startAnimation(fab_close);
      fabTxtIncrease.startAnimation(fab_close);
      fabTxtDecrease.startAnimation(fab_close);
      fabSocialNetworks.startAnimation(fab_close);
      fabTypefaces.startAnimation(fab_close);
      fabMicrophone.startAnimation(fab_close);
      isRlNumberIsOpen = true;
      isFabOpen = false;
    }
    viewPager
            .setScrollDurationFactor(2);
    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1/*, true*/);
  }

  @Override
  public void arrowNext() {
    if (isFabOpen) {
      containerfab.startAnimation(alpha_out);
      fabAddToFavorites.startAnimation(fab_close);
      fabTxtIncrease.startAnimation(fab_close);
      fabTxtDecrease.startAnimation(fab_close);
      fabSocialNetworks.startAnimation(fab_close);
      fabTypefaces.startAnimation(fab_close);
      fabMicrophone.startAnimation(fab_close);
      isRlNumberIsOpen = true;
      isFabOpen = false;
    }
    viewPager.setScrollDurationFactor(2);
    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1/*, true*/);
  }

  @Override
  public void searchQuote() {
    viewPager.setScrollDurationFactor(2);

    if (etChooseQuotes.getVisibility() == View.GONE) {
      etChooseQuotes.setVisibility(View.VISIBLE);
      txtnPositionQuotes.setVisibility(View.GONE);
      animate(ivAbSearch).setListener(null);

    } else {
      etChooseQuotes.setVisibility(View.GONE);
      txtnPositionQuotes.setVisibility(View.VISIBLE);

      if (!etChooseQuotes.getText().toString().equals("")) {

        goToQuote(Integer.parseInt(etChooseQuotes.getText().toString()));
        viewPager.setScrollDurationFactor(1);
        txtnPositionQuotes.setText(String.format("%s ", etChooseQuotes.getText().toString()));
        etChooseQuotes.setText("");
      }
      animate(ivAbSearch).setListener(null);
    }
  }

  @Override
  public void goToCommentsPlaystore() {

  }

  @Override
  public void cancelGoToCommentsPlaystore() {

  }

  @Override
  public void confirmTypefaceChoice() {
  }

  @Override
  public void cancelTypefaceChoice() {
  }

  @Override
  public void gotToTypefaceSettings() {
    Intent iTypefaces = new Intent(new Intent(ContentsActivity.this, SettingsActivity.class));
    iTypefaces.putExtra(getResources().getString(R.string.from), getResources().getString(R.string.typefaces_nav_title));

    if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {

      iTypefaces.putExtra(getResources().getString(R.string.returnTo), middlePage.getAuthorBookNameText());
      if (getIntent().getStringExtra("fromContent") != null) {
        iTypefaces.putExtra("value", getIntent().getStringExtra("fromContent"));
      } else {
        iTypefaces.putExtra("value", getIntent().getStringExtra(getIntent().getStringExtra(getResources().getString(R.string.from))));
      }
    } else {
      if (getIntent().getStringExtra("fromContent") != null) {
        iTypefaces.putExtra(getResources().getString(R.string.returnTo), getIntent().getStringExtra("fromContent"));
      } else {
        iTypefaces.putExtra(getResources().getString(R.string.returnTo), getIntent().getStringExtra(getIntent().getStringExtra(getResources().getString(R.string.from))));
      }
    }
    iTypefaces.putExtra("idContentQuote", txtnPositionQuotes.getText().toString());
    iTypefaces.putExtra("type", getIntent().getStringExtra(getResources().getString(R.string.from)));
    iTypefaces.putExtra("fromActivity", "ContentsActivity");
    iTypefaces.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));
    iTypefaces.putExtra("isAuthor", isAuthor);
    iTypefaces.putExtra("isFromTypefaceSettings", true);

    startActivity(iTypefaces);
    try {
      ActivityAnimator anim = new ActivityAnimator();
      anim.fadeAnimation(ContentsActivity.this);
    } catch (Exception ignored) {
    }
    this.finish();
  }

  @Override
  public void goToTwitterActivity() {

  }

  @Override
  public void goToMailActivity() {

    if(isRlNumberIsOpen) {
      rlNumber.startAnimation(rlnumber_alpha_out);
      isRlNumberIsOpen = false;
    }
    containerfab.startAnimation(alpha_out);
    fabAddToFavorites.startAnimation(fab_close);
    fabTxtIncrease.startAnimation(fab_close);
    fabTxtDecrease.startAnimation(fab_close);
    fabSocialNetworks.startAnimation(fab_close);
    fabTypefaces.startAnimation(fab_close);
    fabMicrophone.startAnimation(fab_close);

    isFabOpen = false;

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

    Intent iSocialNetworks = new Intent(new Intent(ContentsActivity.this, SettingsActivity.class));
    iSocialNetworks.putExtra(getResources().getString(R.string.from), getResources().getString(R.string.mail));

    if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {

      iSocialNetworks.putExtra(getResources().getString(R.string.returnTo), middlePage.getAuthorBookNameText());
      if (getIntent().getStringExtra("fromContent") != null) {
        iSocialNetworks.putExtra("value", getIntent().getStringExtra("fromContent"));
      } else {
        iSocialNetworks.putExtra("value", getIntent().getStringExtra(getIntent().getStringExtra(getResources().getString(R.string.from))));
      }
    } else {
      if (getIntent().getStringExtra("fromContent") != null) {
        iSocialNetworks.putExtra(getResources().getString(R.string.returnTo), getIntent().getStringExtra("fromContent"));
      } else {
        iSocialNetworks.putExtra(getResources().getString(R.string.returnTo), getIntent().getStringExtra(getIntent().getStringExtra(getResources().getString(R.string.from))));
      }
    }
    iSocialNetworks.putExtra("idContentQuote", txtnPositionQuotes.getText().toString());
    iSocialNetworks.putExtra("type", getIntent().getStringExtra(getResources().getString(R.string.from)));
    iSocialNetworks.putExtra("fromActivity", "ContentsActivity");
    iSocialNetworks.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));
    iSocialNetworks.putExtra("isAuthor", isAuthor);
    iSocialNetworks.putExtra("isFromMailSettings", true);

    startActivity(iSocialNetworks);
    try {
      ActivityAnimator anim = new ActivityAnimator();
      anim.fadeAnimation((ContentsActivity.this));
    } catch (Exception ignored) {
    }
    finish();
  }

  @Override
  public void playQuoteMicrophone() {

    destFileName = getFilesDir() + "/" + "tts_file";

    if (soundPoolPlayers != null && plays) {
      soundPoolPlayers.get(utterancePosition).pause();
      plays = false;
      isOnPause = true;
      return;
    }

    if (soundPoolPlayers != null && !plays && !isForMicrophone && isOnPause) {
      soundPoolPlayers.get(utterancePosition).play();
      plays = true;
      isOnPause = false;
      return;
    }
    soundPoolPlayers = new ArrayMap<>();

    if (textToSpeech == null) textToSpeech = new TextToSpeech(OnelittleAngelApplication.instance.getApplicationContext(), status -> {
      if (status == TextToSpeech.SUCCESS) {
        textToSpeech.setLanguage(Locale.UK);
        CharSequence cs = middlePage.getQuoteText();

        if(chunks == null) {

          chunks = new ArrayList<>();

          if (middlePage.getQuoteText().contains(",")) {

            String[] dotChunks = middlePage.getQuoteText().split("\\.");
            for (int i = 0; i < dotChunks.length; i++) {
              String s12 = dotChunks[i].trim();

              String[] comaChunks = s12.split(",");

              String s1 = " ";

              for (int j = 0; j < comaChunks.length; j += 6) {

                if (j + 6 < comaChunks.length) {
                  for (int d = j; d < j + 6; d++) {
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
            String[] dotChunks = middlePage.getQuoteText().split("\\.");
            Collections.addAll(chunks, dotChunks);
          }
        }

        for(int i = 0; i < chunks.size(); i++) {
          if(ttsFileStrings == null) {
            ttsFileStrings = new ArrayList<>();
          } else {
            ttsFileStrings.removeAll(ttsFileStrings);
          }
          myHashRender = new HashMap();
          utteranceID = "wpta_" + i;
          myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceID);
          int sr = 0;
          if(!chunks.get(i).equals("")) {
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
                  if (soundPoolPlayers != null && soundPoolPlayers.get(utteranceId) != null) soundPoolPlayers.get(utteranceId).release();
                  if (soundPoolPlayers != null && soundPoolPlayers.get(utteranceId) != null)
                    soundPoolPlayers.get(utteranceId).setOnCompletionListener(null);
                  if (soundPoolPlayers != null && soundPoolPlayers.get(utteranceId) != null) soundPoolPlayers.remove(utteranceId);
                  File file = new File(destFileName + "wpta_" + i1 + ".mp3");
                  if (file.exists()) {
                    file.delete();
                  }
                } else {
                  if (soundPoolPlayers != null) {
                    for(SoundPoolPlayer player : soundPoolPlayers.values()) {

                      player.release();
                      player.context = null;
                      player.setOnCompletionListener(null);
                      player = null;

                    }

                    for(File file : getFilesDir().listFiles()) {
                      if(file.getName().contains(".mp3")) file.delete();
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
    if (soundPoolPlayers != null ) {
      if(soundPoolPlayers.get(utterancePosition) != null && soundPoolPlayers.get(utterancePosition).isPlaying())
      {
        soundPoolPlayers.get(utterancePosition).stop();
      }


      for(SoundPoolPlayer player : soundPoolPlayers.values()) {

        if(player != null) {
          player.release();
          player.context = null;
          player.setOnCompletionListener(null);
        }
      }
      soundPoolPlayers.clear();

      if(ttsFileStrings == null) {
        ttsFileStrings = new ArrayList<>();
      } else {
        ttsFileStrings.removeAll(ttsFileStrings);
      }

    } else {
      if(soundPoolPlayers != null && soundPoolPlayers.get(utterancePosition) != null && soundPoolPlayers.get(utterancePosition).isPlaying())
      {
        soundPoolPlayers.get(utterancePosition).stop();
      }

      if(soundPoolPlayers != null) {

        for(SoundPoolPlayer player : soundPoolPlayers.values()) {

          if(player != null) {
            player.release();
            player.context = null;
            player.setOnCompletionListener(null);
          }
        }
        soundPoolPlayers.clear();
      }
    }
    chunks = null;
    isOnPause = false;
    plays = false;
    isForMicrophone = true;

    for(File file : getFilesDir().listFiles()) {
      if(file.getName().contains(".mp3")) file.delete();
    }

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
    if(ttsFileStrings != null) stopQuoteMicrophone();
    viewPager.setScrollDurationFactor(2);
    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1/*, true*/);
  }

  @Override
  public void nextQuoteMicrophone() {
    isForMicrophone = true;
    isMicrophoneNextOrPrevious = true;

    if(ttsFileStrings != null) stopQuoteMicrophone();
    viewPager.setScrollDurationFactor(2);
    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1/*, true*/);
  }

  @Override
  public void sendMails() {

    String mailToSettings = settings.getString(Constants.MAILS_LIST_TO_SEND, "");

    if (mailToSettings.equals("") || mailToSettings == null) {

      LayoutInflater inflater = getLayoutInflater();
      View layout = inflater.inflate(R.layout.toast_layout, null);

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

      if(mailTo.size() != 0) {

        String name;

        if(middlePage.getIndex() == presenter.getPageModels().length) {
          if(presenter.getPageModels()[0].getQuote().getAuthor() != null) {
            name = presenter.getPageModels()[0].getQuote().getAuthor().getMcc1() + " " + presenter.getPageModels()[0].getQuote().getAuthor().getName();
          } else {
            name = presenter.getPageModels()[0].getQuote().getBook().getMcc1() + " " + presenter.getPageModels()[0].getQuote().getBook().getName();
          }
        }  else {
          if(presenter.getPageModels()[middlePage.getIndex()].getQuote().getAuthor() != null) {
            name = presenter.getPageModels()[middlePage.getIndex()].getQuote().getAuthor().getMcc1() + " " + presenter.getPageModels()[middlePage.getIndex()].getQuote().getAuthor().getName();
          } else {
            name = presenter.getPageModels()[middlePage.getIndex()].getQuote().getBook().getMcc1() + " " + presenter.getPageModels()[middlePage.getIndex()].getQuote().getBook().getName();
          }
        }

        String content;

        if(etMailComment.getText().toString().isEmpty()) {
          content = middlePage.getQuoteText() + "\n\n" + "------------------------------------" +"\n\n" + middlePage.getSourceText();
        } else {
          content = etMailComment.getText().toString().trim() + "\n\n" + "------------------------------------" +"\n\n" + middlePage.getQuoteText() + "\n\n" + "------------------------------------" +"\n\n" + middlePage.getSourceText();
        }

        new SendMailTask(mailTo, "Citation " + name + " extraite de OnelittleAngel",content, result -> {
          LayoutInflater inflater = getLayoutInflater();
          View layout = inflater.inflate(R.layout.toast_layout, null);

          Toast toast = Toast.makeText(ContentsActivity.this, "", Toast.LENGTH_SHORT);
          aNative = (TextViewNative) layout.findViewById(R.id.toast_txtn);
          aNative.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

          if (result) {
            aNative.setText(getResources().getString(R.string.quote_sended));
          } else {
            aNative.setText(getResources().getString(R.string.quote_not_sended));
          }
          OnelittleAngelApplication.instance.manageConnectivityState();

          if(!OnelittleAngelApplication.instance.isConnected())
            aNative.setText(getResources().getString(R.string.quote_not_sended));

          toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
          toast.setDuration(Toast.LENGTH_SHORT);
          toast.setView(layout);
          if(!isFinishing()) toast.show();
        }).execute(
        );
        editor.remove(Constants.MAILS_LIST_TO_SEND);
        mailsDialog.cancel();
        isMailDialogShowing = false;

        viewPager.startAnimation(viewpager_alpha_in);

        if (!getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) && !getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {
          txtDetails.setVisibility(View.VISIBLE);
          animate(txtDetails).alpha(1).setDuration(800).start();
        }
      } else {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, null);

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
  public void togglePictures() {

    if(presenter.getCarouselView().isEmpty() && !presenter.picturesAlreadyLoaded()) {
      if(!isFinishing()) picturesLoadingDialog.show();
      picturesLoadingDialog.setCancelable(false);
      LoadPictures loadPictures = new LoadPictures();
      loadPictures.execute();
      return;
    }

    if (isFabOpen) {
      if(isRlNumberIsOpen) {
        rlNumber.startAnimation(rlnumber_alpha_out);
        isRlNumberIsOpen = false;
      }

      containerfab.startAnimation(alpha_out);
      fabAddToFavorites.startAnimation(fab_close);
      fabTxtIncrease.startAnimation(fab_close);
      fabTxtDecrease.startAnimation(fab_close);
      fabSocialNetworks.startAnimation(fab_close);
      fabTypefaces.startAnimation(fab_close);
      fabMicrophone.startAnimation(fab_close);
      isFabOpen = false;
    }

    if(gvCarrousel.getVisibility() == View.VISIBLE) {

      if (isFabOpen) {
        if(isRlNumberIsOpen) {

          isRlNumberIsOpen = false;
        }
        containerfab.startAnimation(alpha_out);
        fabAddToFavorites.startAnimation(fab_close);
        fabTxtIncrease.startAnimation(fab_close);
        fabTxtDecrease.startAnimation(fab_close);
        fabSocialNetworks.startAnimation(fab_close);
        fabTypefaces.startAnimation(fab_close);
        fabMicrophone.startAnimation(fab_close);
        isFabOpen = false;

      }

      animate(gvCarrousel).alpha(0).setDuration(800).setListener(new AnimatorListenerAdapter() {

        @Override
        public void onAnimationStart(Animator animation) {
          super.onAnimationStart(animation);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
          super.onAnimationEnd(animation);
          gvCarrousel.setVisibility(View.GONE);
          animate(gvCarrousel).setListener(null);

          ivPictures.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_ab_pictures));

          if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {
            if(txtAuthorBookName != null ) {
              animate(txtAuthorBookName).alpha(1).setDuration(800).start();
            }
          }

          if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.authors)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.books))) {
            if(txtDetails != null ) {
              animate(txtDetails).alpha(1).setDuration(800).start();
            }
          }

          viewPager.setVisibility(View.VISIBLE);

          viewPager.startAnimation(viewpager_alpha_in);

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ivQuoteRight.setBackground(getResources().getDrawable(R.drawable.quote_right_text));
            ivQuoteLeft.setBackground(getResources().getDrawable(R.drawable.quote_left_text));
          } else {
            ivQuoteRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.quote_right_text));
            ivQuoteLeft.setBackgroundDrawable(getResources().getDrawable(R.drawable.quote_left_text));
          }
        }
      }).start();

    } else {

      if (isFabOpen) {
        if(isRlNumberIsOpen) {
          rlNumber.startAnimation(rlnumber_alpha_out);
          isRlNumberIsOpen = false;
        }
        containerfab.startAnimation(alpha_out);
        fabAddToFavorites.startAnimation(fab_close);
        fabTxtIncrease.startAnimation(fab_close);
        fabTxtDecrease.startAnimation(fab_close);
        fabSocialNetworks.startAnimation(fab_close);
        fabTypefaces.startAnimation(fab_close);
        fabMicrophone.startAnimation(fab_close);

        isFabOpen = false;
      }

      mObservableScrollView.smoothScrollTo(0, 0);
      if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {
        if(txtAuthorBookName != null ) {
          animate(txtAuthorBookName).alpha(0).setDuration(800).start();
        }
      }

      if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.authors)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.books))) {
        if(txtDetails != null ) {
          animate(txtDetails).alpha(0).setDuration(800).start();
        }
      }

      if(viewPager.getVisibility() == View.VISIBLE) viewPager.startAnimation(viewpager_alpha_out);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        ivQuoteRight.setBackground(getResources().getDrawable(R.drawable.quote_right_pictures));
        ivQuoteLeft.setBackground(getResources().getDrawable(R.drawable.quote_left_pictures));
      } else {
        ivQuoteRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.quote_right_pictures));
        ivQuoteLeft.setBackgroundDrawable(getResources().getDrawable(R.drawable.quote_left_pictures));
      }

      gvCarrousel.setVisibility(View.VISIBLE);

      animate(gvCarrousel).alpha(1).setDuration(800).setListener(new AnimatorListenerAdapter() {

        @Override
        public void onAnimationStart(Animator animation) {
          super.onAnimationStart(animation);

          if (settings == null) settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
          if (editor == null) editor = settings.edit();

          ivPictures.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_ab_arrow_previous));

          editor.putBoolean(Constants.IS_ANIMATING_LISTVIEW_ENABLED, true).commit();

          gvCarrousel.setBottomAdapter();
        }
      }).start();
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
      height = (int)(height / ratio);
    } else if (height > width) {
      // portrait
      float ratio = (float) height / maxHeight;
      height = maxHeight;
      width = (int)(width / ratio);
    } else {
      // square
      height = maxHeight;
      width = maxWidth;
    }

    bm = Bitmap.createScaledBitmap(bm, width, height, true);
    return bm;
  }

  private Bitmap setWallpaper() {

    Bitmap wallpaper = null;
    WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
    //import non-scaled bitmap wallpaper
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inScaled = false;
    wallpaper = scaleBitmap(presenter.getBitmapsView().get(CardGridArrayAdapter.pos));

    if (width > wallpaper.getWidth() ||
            height > wallpaper.getHeight()) {
      //add padding to wallpaper so background image scales correctly
      int xPadding;
      int yPadding;
      if(wallpaper.getWidth() > wallpaper.getHeight()) {
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

    WallpaperManager m=WallpaperManager.getInstance(OnelittleAngelApplication.instance.getApplicationContext());

    try {

      DisplayMetrics metrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(metrics);

      LayoutInflater inflater = getLayoutInflater();
      View layout = inflater.inflate(R.layout.toast_layout, null);
      Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
      TextViewNative aNative = (TextViewNative) layout.findViewById(R.id.toast_txtn);
      aNative.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

      if( resId == R.id.iv_home_screen ) {
        m.clear();
        m.setBitmap(setWallpaper());
        aNative.setText(getResources().getString(R.string.confirm_home_screen_changing));
      }

      if( resId == R.id.iv_lock_screen ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          m.clear(WallpaperManager.FLAG_LOCK);
          m.setBitmap(setWallpaper(),null, false, WallpaperManager.FLAG_LOCK);
          setWallpaper();
        }
        aNative.setText(getResources().getString(R.string.confirm_lock_screen_changing));
      }

      if( resId == R.id.iv_home_lock_screen) {
        m.clear();
        m.setBitmap(setWallpaper());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          m.setBitmap(setWallpaper(),null, false, WallpaperManager.FLAG_LOCK);
        }
        aNative.setText(getResources().getString(R.string.confirm_home_lock_screen_changing));
      }

      toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
      toast.setDuration(Toast.LENGTH_SHORT);
      toast.setView(layout);
      if(!isFinishing()) toast.show();
      wallPapersDialog.dismiss();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void cancelWallpaperChanging() {
  }

  @Override
  public void addEmail() {

    if(submitForm()) {
      mailsAddedRecyclerAdapter.contacts.put(inputEmail.getText().toString(), inputEmail.getText().toString()) ;
      mailsAddedRecyclerAdapter.notifyDataSetChanged();
      inputEmail.setText("");
    }
  }

  @Override
  public void increaseTextSize() {
    displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    float size = settings.getFloat(Constants.TEXTSIZE, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 22, displaymetrics));
    size += 7f;
    middlePage.sourceTxtView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size / 1.5f);

    for(int i = 0; i < middlePage.contentTextViewNatives.size(); i++) {
      ContentTextViewNativeBiography nativeBiography = (ContentTextViewNativeBiography) middlePage.llTxtView.getChildAt(i);
      nativeBiography.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    editor.putFloat(Constants.TEXTSIZE, size);
    editor.commit();
    setContent(PAGE_MIDDLE);
  }

  @Override
  public void decreaseTextSize() {
    displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    float size = settings.getFloat(Constants.TEXTSIZE, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 22, displaymetrics));
    size -= 7f;

    middlePage.sourceTxtView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size / 1.5f);

    for(int i = 0; i < middlePage.contentTextViewNatives.size(); i++) {
      ContentTextViewNativeBiography nativeBiography = (ContentTextViewNativeBiography) middlePage.llTxtView.getChildAt(i);
      nativeBiography.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    editor.putFloat(Constants.TEXTSIZE, size);
    editor.commit();

    setContent(PAGE_MIDDLE);
  }

  @Override
  public void showWallpaperDialog() {
    if(!isFinishing()) wallPapersDialog.show();
  }

  @Override
  public void returnToCardGridView() {
    gridArrayAdapter.zoomImageFromThumb2(null, false, false);
  }

  private class ScrollSettleHandler extends Handler {
    private static final int SETTLE_DELAY_MILLIS = 100;

    private int mSettledScrollY = Integer.MIN_VALUE;
    private boolean mSettleEnabled;

    public void onScroll(int scrollY) {
      if (mSettledScrollY != scrollY) {

        removeMessages(0);
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
        if (mSettledScrollY - ViewHelper.getTranslationY(rlNumber) > mQuickReturnHeight /*/ 2*/) {
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

    if(ivExpandedPictures.getVisibility() == View.VISIBLE || cvImageComment.getVisibility() == View.VISIBLE) {

      if (isFabOpen) {
        if(isRlNumberIsOpen) {
          rlNumber.startAnimation(rlnumber_alpha_out);
          isRlNumberIsOpen = false;
        }
        containerfab.startAnimation(alpha_out);
        fabAddToFavorites.startAnimation(fab_close);
        fabTxtIncrease.startAnimation(fab_close);
        fabTxtDecrease.startAnimation(fab_close);
        fabSocialNetworks.startAnimation(fab_close);
        fabTypefaces.startAnimation(fab_close);
        fabMicrophone.startAnimation(fab_close);

        isFabOpen = false;
      }
      gridArrayAdapter.zoomImageFromThumb2(null, false, false);
      return;
    }

    if(gvCarrousel != null && gvCarrousel.getVisibility() == View.VISIBLE) {

      if (isFabOpen) {
        if(isRlNumberIsOpen) {
          rlNumber.startAnimation(rlnumber_alpha_out);

          isRlNumberIsOpen = false;
        }
        containerfab.startAnimation(alpha_out);
        fabAddToFavorites.startAnimation(fab_close);
        fabTxtIncrease.startAnimation(fab_close);
        fabTxtDecrease.startAnimation(fab_close);
        fabSocialNetworks.startAnimation(fab_close);
        fabTypefaces.startAnimation(fab_close);
        fabMicrophone.startAnimation(fab_close);

        isFabOpen = false;
      }

      ivPictures.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_ab_pictures));

      animate(gvCarrousel).alpha(0).setDuration(800).setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          super.onAnimationEnd(animation);
          gvCarrousel.setVisibility(View.GONE);
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ivQuoteRight.setBackground(getResources().getDrawable(R.drawable.quote_right_text));
            ivQuoteLeft.setBackground(getResources().getDrawable(R.drawable.quote_left_text));
          } else {
            ivQuoteRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.quote_right_text));
            ivQuoteLeft.setBackgroundDrawable(getResources().getDrawable(R.drawable.quote_left_text));
          }

          if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {
            if(txtAuthorBookName != null ) {
              animate(txtAuthorBookName).alpha(1).setDuration(800).start();
            }
          }

          if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.authors)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.books))) {
            if(txtDetails != null ) {
              animate(txtDetails).alpha(1).setDuration(800).start();
            }
          }
          viewPager.setVisibility(View.VISIBLE);

          viewPager.startAnimation(rlnumber_alpha_in);
        }
      }).start();
      return;
    }

    try {
      stopQuoteMicrophone();
    } finally {

      Intent intent = new Intent(ContentsActivity.this, TableContentsActivity.class);
      if (!getIntent().hasExtra("fromNotification")) intent.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));
      startActivity(new Intent(intent));
      try {
        ActivityAnimator anim = new ActivityAnimator();
        anim.fadeAnimationToTableContents(ContentsActivity.this);
      } catch (Exception ignored) {
      }
      this.finish();
    }
  }

  /***********************************************************
   * Managing Presenters
   **********************************************************/

  @Override
  public void updatePageModels() {

    if (type.equals(getResources().getString(R.string.themes)) && presenter.getTheme().getPictures().size() == 0) {
      ivPictures.setVisibility(View.GONE);
    }

    if (type.equals(getResources().getString(R.string.movements)) && presenter.getMovement().getPictures().size() == 0) {
      ivPictures.setVisibility(View.GONE);
    }

    if (type.equals(getResources().getString(R.string.authors)) && presenter.getAuthor().getPictures().size() == 0) {
      ivPictures.setVisibility(View.GONE);
    }

    if (type.equals(getResources().getString(R.string.books)) && presenter.getBook().getPictures().size() == 0) {
      ivPictures.setVisibility(View.GONE);
    }

    contentsTitlePage = (TextViewNative) findViewById(R.id.title_contents);
    if (getIntent().getStringExtra("fromContent") != null) {
      initTitlePage = getIntent().getStringExtra("fromContent");
      contentsTitlePage.setText(String.format("%s%s", getIntent().getStringExtra("fromContent"), getResources().getString(R.string.space)));
    } else {
      if (getIntent().getStringExtra(getIntent().getStringExtra(getResources().getString(R.string.from))) != null) {
        initTitlePage = getIntent().getStringExtra(getIntent().getStringExtra(getResources().getString(R.string.from)));
        contentsTitlePage.setText(String.format("%s%s", getIntent().getStringExtra(getIntent().getStringExtra(getResources().getString(R.string.from))), getResources().getString(R.string.space)));
      } else {
        initTitlePage = s;
        contentsTitlePage.setText(String.format("%s%s", s, getResources().getString(R.string.space)));
      }
    }



    if(initTitlePage.equals(settings.getString(Constants.PICTURE_OF_DAY_NAME, ""))) {
      if (settings.getBoolean(Constants.PICTURE_IS_NOTIFICATION_SENDED, false)) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(PictureNotificationIntentService.NOTIFICATION_ID);
        int badgecount = settings.getInt(Constants.BADGE_COUNT, 0);
        ShortcutBadger.applyCount(OnelittleAngelApplication.instance.getApplicationContext(), --badgecount);
        editor.putInt(Constants.BADGE_COUNT, badgecount).apply();
        editor.putBoolean(Constants.PICTURE_IS_NOTIFICATION_SENDED, false).apply();
      }
    }

    if(initTitlePage.equals(settings.getString(Constants.QUOTE_OF_DAY_NAME, ""))) {
      if (settings.getBoolean(Constants.QUOTE_IS_NOTIFICATION_SENDED, false)) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(QuoteNotificationIntentService.NOTIFICATION_ID);
        int badgecount = settings.getInt(Constants.BADGE_COUNT, 0);
        ShortcutBadger.applyCount(OnelittleAngelApplication.instance.getApplicationContext(), --badgecount);
        editor.putInt(Constants.BADGE_COUNT, badgecount).apply();
        editor.putBoolean(Constants.QUOTE_IS_NOTIFICATION_SENDED, false).apply();
      }
    }

    String[] split = contentsTitlePage.getText().toString().trim().split(" ");
    String titleString = "";
    String titleString1 = "";
    contentsTitlePage.setText(null);

    double rate = 2;

    DisplayMetrics displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    height = displaymetrics.heightPixels;
    int width1 = displaymetrics.widthPixels;

    int widthMeasureSpec1 = View.MeasureSpec.makeMeasureSpec(width1, View.MeasureSpec.AT_MOST);
    int heightMeasureSpec1 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    ivQuoteLeft.measure(widthMeasureSpec1, heightMeasureSpec1);
    mQuickReturnWidthForTitlePage = ivQuoteLeft.getMeasuredWidth();

    for (String aSplit : split) {
      titleString1 = titleString;
      titleString += aSplit;
      contentsTitlePage.setText(titleString);
      contentsTitlePage.measure(widthMeasureSpec1, heightMeasureSpec1);

      if (contentsTitlePage.getMeasuredWidth() == width1 - mQuickReturnWidthForTitlePage * rate) {
        titleString += "\n";
        if (rate == 2) rate = 1.5;
        if (rate == 1.5) rate = 0.5;
      } else if (contentsTitlePage.getMeasuredWidth() > width1 - mQuickReturnWidthForTitlePage * rate) {
        titleString = titleString1 + "\n" + aSplit;
        if (rate == 2) rate = 1.5;
        if (rate == 1.5) rate = 0.5;
      }
      titleString += " ";
    }

    contentsTitlePage.setText(titleString);
    contentsTitlePage.measure(widthMeasureSpec1, heightMeasureSpec1);

    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) contentsTitlePage.getLayoutParams();
    layoutParams.width = (int) (width1 - ivQuoteLeft.getMeasuredWidth() * rate);
    contentsTitlePage.setLayoutParams(layoutParams);
    contentsTitlePage.setGravity(Gravity.CENTER_HORIZONTAL);

    if (dQuotesLoading != null && !dQuotesLoading.isShowing() && !isFromMailOrTypefaceSetings) {
      dQuotesLoading.show();
      dQuotesLoading.setCancelable(false);
    }

    Handler handler = new Handler();

    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        adapter = new ContentsNativePagerAdapter(ContentsActivity.this, presenter.getPageModels(), presenter);
        adapter.setCallbacks(ContentsActivity.this);

        viewPager.setAdapter(adapter);

        viewPager.setScrollDurationFactor(1);
        viewPager.setPagingEnabled(true);

        if (presenter.isPageModelsIsSizeOfTwo()) {

          etChooseQuotes.setFilters(new InputFilter[]{new InputFilterMinMax("2")});
          txtnSizeQuotes.setText("2 ");
          middlePage = presenter.getPageModels()[1];
          middlePage.layout = presenter.getPageModels()[1].layout;
          if (!presenter.isInit())
            middlePage.setIndex(1, middlePage.getQuote());
          else
            middlePage.setIndex(middlePage.getIndex(), middlePage.getQuote());
        } else {

          etChooseQuotes.setFilters(new InputFilter[]{new InputFilterMinMax(String.valueOf(presenter.getPageModels().length))});
          txtnSizeQuotes.setText(" " + presenter.getPageModels().length + " ");

          if (presenter.getPageModels().length == 0) {
            arrowPrevious.setVisibility(View.GONE);
            arrowNext.setVisibility(View.GONE);
            return;
          }

          if (presenter.getPageModels().length > 1) {
            viewPager.setPagingEnabled(true);

            if (!presenter.isInit()) {
              middlePage = presenter.getPageModels()[1];
              middlePage.layout = presenter.getPageModels()[1].layout;
              middlePage.setIndex(1, middlePage.getQuote());
            } else {
              if(idQuote != -1) middlePagePosition = idQuote;
              if (middlePagePosition == presenter.getPageModels().length) {
                middlePage = presenter.getPageModels()[0];
                middlePage.layout = presenter.getPageModels()[0].layout;

                middlePage.setIndex(middlePagePosition, presenter.getPageModels()[0].getQuote());
              } else {
                middlePage = presenter.getPageModels()[middlePagePosition];
                middlePage.layout = presenter.getPageModels()[middlePagePosition].layout;
                middlePage.setIndex(middlePagePosition, presenter.getPageModels()[middlePagePosition - 1].getQuote());
              }
            }
            if (middlePage.getIndex() == presenter.getPageModels().length) {
              if (presenter.getPageModels()[middlePage.getIndex() - 1].getQuote().isFavorites()) {
                fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
                fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
              } else {
                fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
                fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
              }
            } else {
              if (presenter.getPageModels()[middlePage.getIndex()].getQuote().isFavorites()) {
                fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
                fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
              } else {
                fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
                fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
              }
            }

          } else if (presenter.getPageModels().length == 1) {
            viewPager.setPagingEnabled(false);
            middlePage = presenter.getPageModels()[0];
            middlePage.layout = presenter.getPageModels()[0].layout;

            if (!presenter.isInit())
              middlePage.setIndex(1, middlePage.getQuote());
            else
              middlePage.setIndex(middlePage.getIndex(), middlePage.getQuote());

            arrowPrevious.setVisibility(View.GONE);
            arrowNext.setVisibility(View.GONE);
            ivMicrophonePrevious.setVisibility(View.GONE);
            ivMicrophoneNext.setVisibility(View.GONE);

            ivAbSearch.setVisibility(View.GONE);

            if (presenter.getPageModels()[0].getQuote().isFavorites()) {
              fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_remove_favorites));
              fabAddToFavorites.setTag(R.drawable.ic_menu_remove_favorites);
            } else {
              fabAddToFavorites.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_menu_add_favorites));
              fabAddToFavorites.setTag(R.drawable.ic_menu_add_favorites);
            }
          }
        }

        if (isFabOpen) {
          rlNumber.setVisibility(View.VISIBLE);
          rlNumber.startAnimation(rlnumber_alpha_in);
          isRlNumberIsOpen = true;

          if (presenter.getPageModels().length > 1) {

            arrowPrevious.setVisibility(View.VISIBLE);
            arrowPrevious.startAnimation(rlnumber_alpha_in);

            ivAbSearch.setVisibility(View.VISIBLE);
            ivAbSearch.startAnimation(rlnumber_alpha_in);

            arrowNext.setVisibility(View.VISIBLE);
            arrowNext.startAnimation(rlnumber_alpha_in);
          }

          ivAbHome.setVisibility(View.VISIBLE);
          ivAbHome.startAnimation(rlnumber_alpha_in);

          llNumber.setVisibility(View.VISIBLE);
          llNumber.startAnimation(rlnumber_alpha_in);

          llSearch.setVisibility(View.VISIBLE);
          llSearch.startAnimation(rlnumber_alpha_in);

          containerfab.setVisibility(View.VISIBLE);
          containerfab.startAnimation(alpha_in);
          fabAddToFavorites.setVisibility(View.VISIBLE);
          fabAddToFavorites.startAnimation(fab_open);
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

        if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {
          txtAuthorBookName.setVisibility(View.VISIBLE);
          txtDetails.setVisibility(View.GONE);
          if (middlePage.getQuote().getAuthor() != null) {
            txtAuthorBookName.setText(String.format("%s%s", middlePage.getAuthorBookNameText(), getResources().getString(R.string.space)));

            if (middlePage.getDetailText() != null && !middlePage.getDetailText().equals("")) {
            } else {
            }
          } else if (middlePage.getQuote().getBook() != null) {
            txtAuthorBookName.setText(String.format("%s%s", middlePage.getAuthorBookNameText(), getResources().getString(R.string.space)));

            if (middlePage.getDetailText() != null && !middlePage.getDetailText().equals("")) {
            } else {
            }
          } else {
            txtAuthorBookName.setVisibility(View.GONE);
          }

          if (presenter.getMovement() != null && presenter.getMovement().getPresentation() != null) {
            txtBiographyFaiths.setVisibility(View.VISIBLE);
            OnelittleAngelApplication.instance.manageConnectivityState();

            if (presenter.getMovement().getPictures().size() == 0 || !OnelittleAngelApplication.instance.isConnected()) {
              txtBiographyFaiths.setText(getResources().getString(R.string.faiths));
            } else {
              txtBiographyFaiths.setText(getResources().getString(R.string.faiths_and_pictures));
            }
            ivPictures.setVisibility(View.GONE);
          } else {
            if (presenter.getMovement() != null && presenter.getMovement().getPictures().size() == 0) {
              ivPictures.setVisibility(View.GONE);
            }
          }
        } else if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.books))) {
          txtAuthorBookName.setVisibility(View.GONE);
          txtDetails.setVisibility(View.GONE);
        } else {
          txtAuthorBookName.setVisibility(View.GONE);
          txtDetails.setVisibility(View.VISIBLE);
        }

        if (presenter.getPageModels()[0].getQuote().getAuthor() != null) {

          OnelittleAngelApplication.instance.manageConnectivityState();

          if (presenter.getPageModels()[0].getQuote().getAuthor().getPictures().size() == 0 || !OnelittleAngelApplication.instance.isConnected()) {
            txtDetails.setText(getResources().getString(R.string.biography));
          } else {
            ivPictures.setVisibility(View.GONE);
            txtDetails.setText(getResources().getString(R.string.biography_and_calligraphy));
          }

          if (presenter.getTheme() == null && presenter.getMovement() == null && presenter.getPageModels()[0].getQuote().getAuthor().getPresentation() == null && presenter.getPageModels()[0].getQuote().getAuthor().getPictures().size() != 0) {
            ivPictures.setVisibility(View.VISIBLE);
          }
        } else if (presenter.getPageModels()[0].getQuote().getBook() != null) {

          if (!presenter.getPageModels()[0].getQuote().getBook().getDetails().equals("")) {
            txtDetails.setVisibility(View.GONE);
          } else {
            OnelittleAngelApplication.instance.manageConnectivityState();

            if (presenter.getPageModels()[0].getQuote().getBook().getPictures().size() == 0 || !OnelittleAngelApplication.instance.isConnected()) {
              txtDetails.setText(getResources().getString(R.string.presentation));
            } else {
              ivPictures.setVisibility(View.GONE);
              txtDetails.setText(getResources().getString(R.string.presentation_and_calligraphy));
            }
            txtDetails.setVisibility(View.VISIBLE);
          }

          if (presenter.getTheme() == null && presenter.getMovement() == null && presenter.getPageModels()[0].getQuote().getBook().getPresentation() == null && presenter.getPageModels()[0].getQuote().getBook().getPictures().size() != 0) {
            ivPictures.setVisibility(View.VISIBLE);
          }
        }

        viewPager.setCurrentItem(PAGE_MIDDLE, false);

        isAuthor = middlePage.isAuthor;

        if (!middlePage.isPresentationOrBibliography) {
          txtAuthorBookName.setOnTouchListener(null);
          if (txtAuthorBookName.getText().length() != 0 && txtAuthorBookName.getVisibility() != View.GONE) {
            txtAuthorBookName.setTextColor(Color.BLACK);
          }
          txtDetails.setOnTouchListener(null);
          if (txtDetails.getText().length() != 0 && txtDetails.getVisibility() != View.GONE) {
            txtDetails.setTextColor(Color.BLACK);

            if (txtDetails.getText().toString().equals(getResources().getString(R.string.biography)) || txtDetails.getText().toString().equals(getResources().getString(R.string.biography_and_calligraphy)) || txtDetails.getText().toString().equals(getResources().getString(R.string.presentation)) || txtDetails.getText().toString().equals(getResources().getString(R.string.presentation_and_calligraphy))) {
              txtDetails.setText("");
              txtDetails.setVisibility(View.GONE);
            } else {
              txtDetails.setVisibility(View.VISIBLE);
            }
          }
        } else {
          if (txtDetails.getText().length() != 0 && txtDetails.getVisibility() != View.GONE) {
            txtDetails.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
          }
          ImageViewListener txtDetailsListener = new ImageViewListener(txtDetails, 1.05f);
          txtDetailsListener.setCallbacks(ContentsActivity.this);
          txtDetails.setOnTouchListener(txtDetailsListener);

          txtDetails.bringToFront();

          if (txtAuthorBookName.getText().length() != 0 && txtAuthorBookName.getVisibility() != View.GONE) {
            txtAuthorBookName.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
          }
          ImageViewListener txtAuthorBookListener = new ImageViewListener(txtAuthorBookName, 1.05f);
          txtAuthorBookListener.setCallbacks(ContentsActivity.this);
          txtAuthorBookName.setOnTouchListener(txtAuthorBookListener);
          txtAuthorBookName.bringToFront();

          ivPictures.setVisibility(View.GONE);
        }

        if (presenter.getTheme() != null && presenter.getTheme().getPictures().size() != 0) {
          ivPictures.setVisibility(View.VISIBLE);
        }

        txtnPositionQuotes.setText(String.format("%d ", middlePage.getIndex()));

        if (presenter.getPageModels().length > 1) {
          viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
              mSelectedPageIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

              if (state == ViewPager.SCROLL_STATE_IDLE) {

                leftPage = adapter.getmPageModels()[PAGE_LEFT];
                middlePage = adapter.getmPageModels()[PAGE_MIDDLE];
                rightPage = adapter.getmPageModels()[PAGE_RIGHT];

                oldLeftIndex = leftPage.getIndex();
                oldMiddleIndex = middlePage.getIndex();
                oldRightIndex = rightPage.getIndex();

                // user swiped to right direction --> left page
                if (mSelectedPageIndex == PAGE_LEFT) {
                  swipeOnLeft();
                } else if (mSelectedPageIndex == PAGE_RIGHT) {
                  swipeOnRight();
                }
                viewPager.setCurrentItem(PAGE_MIDDLE, false);

                viewPager.setScrollDurationFactor(1);
              }
            }
          });
        }

        if (!OnelittleAngelApplication.instance.isConnected()) ivPictures.setVisibility(View.GONE);
        if (type.equals(getResources().getString(R.string.themes))) txtDetails.setVisibility(View.GONE);

        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

          @Override
          public void onGlobalLayout() {

            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(middlePage.cardView.getWidth(), View.MeasureSpec.AT_MOST);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            middlePage.cardView.measure(widthMeasureSpec, heightMeasureSpec);

            txtnPositionQuotes.measure(widthMeasureSpec, heightMeasureSpec);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

              FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
              params.topMargin = (int) (llHeader.getBottom() + getResources().getDimension(R.dimen.action_bar_margin));
              rlNumber.setLayoutParams(params);
              if (mObservableHeight == 0)
                mObservableHeight = rlNumber.getBottom() + params.topMargin;
            } else {

              if (type.equals(getResources().getString(R.string.themes))) {
                if (ivPictures.getVisibility() == View.VISIBLE) {
                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (ivPictures.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin) * 4);
                  rlNumber.setLayoutParams(params);

                  llHeader.measure(widthMeasureSpec, heightMeasureSpec);
                  rlNumber.measure(widthMeasureSpec, heightMeasureSpec);

                  if (mObservableHeight == 0)
                    mObservableHeight = (int) (llHeader.getBottom() + getResources().getDimension(R.dimen.action_bar_margin));
                } else {
                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (txtAuthorBookName.getTop()  + getResources().getDimension(R.dimen.activity_horizontal_margin) * 1);
                  rlNumber.setLayoutParams(params);

                  if (mObservableHeight == 0) mObservableHeight = llHeader.getBottom() + 20;
                }
              } else if (type.equals(getResources().getString(R.string.movements))) {

                if (txtBiographyFaiths.getVisibility() == View.VISIBLE) {

                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (txtBiographyFaiths.getTop() + getResources().getDimension(R.dimen.action_bar_margin) * 3);
                  rlNumber.setLayoutParams(params);
                  if (mObservableHeight == 0)
                    mObservableHeight = (int) (txtAuthorBookName.getBottom() + getResources().getDimension(R.dimen.action_bar_margin));
                } else {

                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (txtAuthorBookName.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin) );
                  rlNumber.setLayoutParams(params);
                  if (mObservableHeight == 0) mObservableHeight = llHeader.getBottom() + 20;
                }
              } else if (type.equals(getResources().getString(R.string.authors))) {
                if (txtDetails.getCurrentTextColor() == settings.getInt(CardViewNative.DARKERRGB, 0)) {

                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (txtDetails.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin));
                  rlNumber.setLayoutParams(params);
                  rlNumber.measure(widthMeasureSpec, heightMeasureSpec);
                  if (mObservableHeight == 0)
                    mObservableHeight = (int) (txtDetails.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin) + rlNumber.getMeasuredHeight() + params.bottomMargin);
                } else if (ivPictures.getVisibility() == View.VISIBLE) {

                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (ivPictures.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin) );
                  rlNumber.setLayoutParams(params);
                  if (mObservableHeight == 0)
                    mObservableHeight = (int) (llHeader.getBottom() + getResources().getDimension(R.dimen.action_bar_margin) );
                } else if (txtBiographyFaiths.getVisibility() == View.VISIBLE) {

                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (txtBiographyFaiths.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin));
                  rlNumber.setLayoutParams(params);
                  if (mObservableHeight == 0)
                    mObservableHeight = llHeader.getBottom();
                } else if (txtBiographyFaiths.getVisibility() == View.GONE && txtDetails.getVisibility() == View.GONE) {

                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (contentsTitlePage.getBottom() + getResources().getDimension(R.dimen.action_bar_margin));
                  rlNumber.setLayoutParams(params);
                  rlNumber.measure(widthMeasureSpec, heightMeasureSpec);
                  if (mObservableHeight == 0)
                    mObservableHeight = params.topMargin + rlNumber.getMeasuredHeight() + params.bottomMargin;
                } else if (txtBiographyFaiths.getVisibility() == View.GONE && txtDetails.getVisibility() == View.VISIBLE) {
                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (txtDetails.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin));

                  rlNumber.setLayoutParams(params);
                  if (mObservableHeight == 0)
                    mObservableHeight = (int) (txtDetails.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin) + rlNumber.getMeasuredHeight() + params.bottomMargin);
                }
              } else if (type.equals(getResources().getString(R.string.books))) {

                if (txtDetails.getVisibility() == View.VISIBLE) {

                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (txtDetails.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin));
                  rlNumber.setLayoutParams(params);
                  rlNumber.measure(widthMeasureSpec, heightMeasureSpec);
                  if (mObservableHeight == 0)
                    mObservableHeight = (int) (txtDetails.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin) + rlNumber.getMeasuredHeight() + params.bottomMargin);
                } else if (ivPictures.getVisibility() == View.VISIBLE) {

                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (ivPictures.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin) * 1);
                  rlNumber.setLayoutParams(params);
                  if (mObservableHeight == 0)
                    mObservableHeight = (int) (ivPictures.getTop() + getResources().getDimension(R.dimen.action_bar_margin) + rlNumber.getMeasuredHeight() + params.bottomMargin );
                } else if (txtBiographyFaiths.getVisibility() == View.VISIBLE) {

                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (txtBiographyFaiths.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin));
                  rlNumber.setLayoutParams(params);
                  if (mObservableHeight == 0)
                    mObservableHeight = llHeader.getBottom();
                } else {

                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (contentsTitlePage.getBottom() + getResources().getDimension(R.dimen.activity_horizontal_margin));
                  rlNumber.setLayoutParams(params);
                  rlNumber.measure(widthMeasureSpec, heightMeasureSpec);
                  if (mObservableHeight == 0) mObservableHeight = (int) ((llHeader.getBottom() + rlNumber.getMeasuredHeight() - getResources().getDimension(R.dimen.activity_horizontal_margin)));
                }
              } else if (type.equals(getResources().getString(R.string.homepage))) {

                if (txtDetails.getVisibility() == View.VISIBLE) {

                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (txtDetails.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin) );
                  rlNumber.setLayoutParams(params);
                  rlNumber.measure(widthMeasureSpec, heightMeasureSpec);
                  if (mObservableHeight == 0)
                    mObservableHeight = (int) (txtDetails.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin) + rlNumber.getMeasuredHeight() + params.bottomMargin );
                } else if (ivPictures.getVisibility() == View.VISIBLE) {

                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (ivPictures.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin));
                  rlNumber.setLayoutParams(params);
                  if (mObservableHeight == 0)
                    mObservableHeight = (int) (ivPictures.getTop() + getResources().getDimension(R.dimen.action_bar_margin) + rlNumber.getMeasuredHeight() + params.bottomMargin );
                } else if (txtBiographyFaiths.getVisibility() == View.VISIBLE) {

                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (txtBiographyFaiths.getTop() + getResources().getDimension(R.dimen.activity_horizontal_margin));
                  rlNumber.setLayoutParams(params);
                  if (mObservableHeight == 0)
                    mObservableHeight = llHeader.getBottom();
                } else {

                  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                  params.topMargin = (int) (llHeader.getBottom() + getResources().getDimension(R.dimen.action_bar_margin));
                  rlNumber.setLayoutParams(params);
                  rlNumber.measure(widthMeasureSpec, heightMeasureSpec);
                  if (mObservableHeight == 0) mObservableHeight = (int) ((llHeader.getBottom() + rlNumber.getMeasuredHeight() - getResources().getDimension(R.dimen.activity_horizontal_margin)));
                }
              }
            }

            int i;
            if (txtDetails.getVisibility() == View.VISIBLE) {
              i = middlePage.cardView.getMeasuredHeight() + ((int) getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) + mObservableHeight;
            } else {
              i = middlePage.cardView.getMeasuredHeight() + ((int) getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) + mObservableHeight;
            }
            if (mViewPagerTop > i) {
              viewPager.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
            } else {
              viewPager.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, i));
            }
            PersonalLinearLayout.LayoutParams params = (PersonalLinearLayout.LayoutParams) middlePage.cardView.getLayoutParams();
            params.topMargin = mObservableHeight;
            middlePage.cardView.setLayoutParams(params);

            if (!isTypefaceDialogShowing && !isMailDialogShowing) viewPager.startAnimation(viewpager_alpha_in);
            else if (isTypefaceDialogShowing) showTypeFaceDialog();
            else if (isMailDialogShowing) goToMailActivity();

            if (isMicrophoneDialogShowing) {
              showMicrophoneDialog();
              isMicrophoneDialogShowing = false;
            }

            if (presenter.isPageModelsIsSizeOfTwo()) {
              txtnPositionQuotes.setText(String.format("%d ", ((middlePage.getIndex() + 1) % 2) + 1));
            } else {
              txtnPositionQuotes.setText(String.format("%d ", middlePage.getIndex()));
            }

            if (gvCarrouselMarginTop == 0) {
              ivPictures.measure(widthMeasureSpec, heightMeasureSpec);
              gvCarrouselMarginTop = (int) (ivPictures.getBottom() + getResources().getDimension(R.dimen.activity_horizontal_double_margin));
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
              viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } else {
              viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }

            if (getIntent().getStringExtra("idContentQuote") != null && presenter.getPageModels().length > 3) {
              goToQuote(Integer.parseInt(getIntent().getStringExtra("idContentQuote").substring(0, getIntent().getStringExtra("idContentQuote").length() - 1)));
              getIntent().removeExtra("idContentQuote");
            }

            if (idQuote != -1 && idQuote != 0 && presenter.getPageModels().length > 1) {
              goToQuote(idQuote);
            }

            if (isGvCarrouselIsOpened) {
              if (viewPager.getVisibility() == View.VISIBLE)
                viewPager.startAnimation(viewpager_alpha_out);
              togglePictures();
            }

            if(dQuotesLoading != null && dQuotesLoading.isShowing()) dQuotesLoading.dismiss();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
          }
        });

        if (presenter.isInit() && !isTypefaceDialogShowing) {
          ObjectAnimator objectAnimator = new ObjectAnimator();
          objectAnimator.setDuration(800);
          objectAnimator.ofFloat(viewPager, "alpha", 1).start();
          presenter.init(false);

        } else {
          animate(viewPager).alpha(1).setDuration(400).start();
        }

      }
    }, 2000);
  }

  private class LoadPictures extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      if (isFabOpen) {
        if(isRlNumberIsOpen) {
          rlNumber.startAnimation(rlnumber_alpha_out);

          isRlNumberIsOpen = false;
        }
        containerfab.startAnimation(alpha_out);
        fabAddToFavorites.startAnimation(fab_close);
        fabTxtIncrease.startAnimation(fab_close);
        fabTxtDecrease.startAnimation(fab_close);
        fabSocialNetworks.startAnimation(fab_close);
        fabTypefaces.startAnimation(fab_close);
        fabMicrophone.startAnimation(fab_close);

        isFabOpen = false;

      }
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
      if(getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.homepage))) {
        if(getIntent().getBooleanExtra("isAuthor", false)){
          presenter.loadPictures(getResources().getString(R.string.authors), s);
        } else {
          presenter.loadPictures(getResources().getString(R.string.books), s);
        }
      } else {
        presenter.loadPictures(getIntent().getStringExtra(getResources().getString(R.string.fromFragment)), s);
      }
      return null;
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


  @Override
  public void isEmailWasSended() {
  }

  @Override
  public void updateCarousel() {
    if(presenter.getCarouselView().size() != 0) {

      biggerCarouselViewHeight = 0;
      for (int i = 0; i < presenter.getCarouselView().size(); i++) {
        final int positionCarrouselView = i;
        presenter.getCarouselView().get(i).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override
          public void onGlobalLayout() {
            if(presenter.getCarouselView().get(positionCarrouselView).getMeasuredHeight() > presenter.getCarouselView().get(positionCarrouselView).getMeasuredWidth()) {
              if (biggerCarouselViewHeight < presenter.getCarouselView().get(positionCarrouselView).getMeasuredHeight()) {
                biggerCarouselViewHeight = presenter.getCarouselView().get(positionCarrouselView).getMeasuredHeight();
              }

              if (positionCarrouselView == presenter.getCarouselView().size() - 1) {
                mCarouselView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, biggerCarouselViewHeight));
              }
            } else {

            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
              presenter.getCarouselView().get(positionCarrouselView).getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } else {
              presenter.getCarouselView().get(positionCarrouselView).getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
          }
        });

        ivExpandedPictures.setCallbacks(this);
        mCarouselView.addView(presenter.getCarouselView().get(i));
      }
      mCarouselView.notifyDataSetChanged();

      ivExpandedPictures.setCallbacks(this);
      gvCarrousel.setVisibility(View.VISIBLE);
      if(viewPager.getVisibility() == View.VISIBLE) viewPager.startAnimation(viewpager_alpha_out);

      if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {
        if(txtAuthorBookName != null ) {
          animate(txtAuthorBookName).alpha(0).setDuration(800).start();
        }
      }

      if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.authors)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.books))) {
        if(txtDetails != null ) {
          animate(txtDetails).alpha(0).setDuration(800).start();
        }
      }

      ivPictures.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_ab_arrow_previous));

      animate(gvCarrousel).alpha(1).setDuration(0).start();
      gvCarrousel.getmAdapter().notifyDataSetChanged();

      gvCarrousel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

          int itemCount = gvCarrousel.getmAdapter().getCount();
          int columns = 0;
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            columns = gvCarrousel.getNumColumns();
          } else {
            columns = gvCarrousel.getNumColumnsCompat();
          }

          int lines = itemCount / columns;
          if (itemCount % columns != 0) {
            lines++;
          }

          if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            gvCarrousel.setPadding(((int) getResources().getDimension(R.dimen.activity_horizontal_margin)), (int) (gvCarrouselMarginTop + getResources().getDimension(R.dimen.activity_horizontal_double_margin)), ((int) getResources().getDimension(R.dimen.activity_horizontal_margin)), ((int) getResources().getDimension(R.dimen.activity_horizontal_margin)));
          else gvCarrousel.setPadding(((int) getResources().getDimension(R.dimen.action_bar_padding_viewstub)), (int) (gvCarrouselMarginTop + getResources().getDimension(R.dimen.activity_horizontal_double_margin)), ((int) getResources().getDimension(R.dimen.action_bar_padding_viewstub)), ((int) getResources().getDimension(R.dimen.action_bar_padding_viewstub)));

          FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) gvCarrousel.getLayoutParams();
          lp.height = lines * gvCarrousel.getmAdapter().itemHeight + ((int) getResources().getDimension(R.dimen.activity_horizontal_margin)) + (int) (gvCarrouselMarginTop + getResources().getDimension(R.dimen.activity_horizontal_double_margin));

          displaymetrics = new DisplayMetrics();
          getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
          height = displaymetrics.heightPixels;
          width = displaymetrics.widthPixels;

          if(height - txtDetails.getTop() > lp.height) {
            lp.height = height - txtDetails.getTop();
          }

          gvCarrousel.setLayoutParams(lp);
          gvCarrousel.requestLayout();
          mObservableScrollView.smoothScrollTo(0, 0);

          if (settings == null) settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
          if (editor == null) editor = settings.edit();


          editor.putBoolean(Constants.IS_ANIMATING_LISTVIEW_ENABLED, true).commit();

          gvCarrousel.setBottomAdapter();

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ivQuoteRight.setBackground(getResources().getDrawable(R.drawable.quote_right_pictures));
            ivQuoteLeft.setBackground(getResources().getDrawable(R.drawable.quote_left_pictures));
          } else {
            ivQuoteRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.quote_right_pictures));
            ivQuoteLeft.setBackgroundDrawable(getResources().getDrawable(R.drawable.quote_left_pictures));
          }

          if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            gvCarrousel.getViewTreeObserver().removeGlobalOnLayoutListener(this);
          } else {
            gvCarrousel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
          }
        }
      });
    }
  }

  @Override
  public MotherPresenter getPresenter() {
    return (MotherPresenter) presenter;
  }

  /***********************************************************
   * Testing Only
   **********************************************************/

  @VisibleForTesting
  public IdlingResource getCountingIdlingResource() {
    return EspressoIdlingResource.getIdlingResource();
  }
}
