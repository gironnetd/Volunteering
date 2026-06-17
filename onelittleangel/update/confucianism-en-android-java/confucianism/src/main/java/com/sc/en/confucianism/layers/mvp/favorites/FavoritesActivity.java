package com.sc.en.confucianism.layers.mvp.favorites;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
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
import com.sc.en.confucianism.OnelittleAngelApplication;
import com.sc.en.confucianism.layers.mvp.MotherActivity;
import com.sc.en.confucianism.layers.mvp.MotherPresenter;
import com.sc.en.confucianism.layers.mvp.biography.BiographyActivity;
import com.sc.en.confucianism.layers.mvp.common.animations.ActivityAnimator;
import com.sc.en.confucianism.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.confucianism.layers.mvp.common.customs.edittexts.TextWatcher;
import com.sc.en.confucianism.layers.mvp.common.customs.listviews.adapters.MailsAddedRecyclerAdapter;
import com.sc.en.confucianism.layers.mvp.common.customs.listviews.adapters.MailsRecyclerAdapter;
import com.sc.en.confucianism.layers.mvp.common.customs.listviews.adapters.TypefacesRecyclerAdapter;
import com.sc.en.confucianism.layers.mvp.common.customs.scrollviews.ObservableScrollView;
import com.sc.en.confucianism.layers.mvp.common.customs.spinkits.SpinKitView;
import com.sc.en.confucianism.layers.mvp.common.customs.spinkits.SpriteFactory;
import com.sc.en.confucianism.layers.mvp.common.customs.spinkits.sprite.Sprite;
import com.sc.en.confucianism.layers.mvp.common.customs.textviews.ContentTextViewNativeBiography;
import com.sc.en.confucianism.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.en.confucianism.layers.mvp.common.customs.viewpagers.fragmentpageradapter.FavoritesNativePagerAdapter;
import com.sc.en.confucianism.layers.mvp.common.listeners.imageviews.ImageViewListener;
import com.sc.en.confucianism.layers.mvp.common.models.PageModel;
import com.sc.en.confucianism.layers.mvp.common.players.SoundPoolPlayer;
import com.sc.en.confucianism.layers.mvp.common.utils.Constants;
import com.sc.en.confucianism.layers.mvp.contents.ContentsActivity;
import com.sc.en.confucianism.layers.mvp.settings.SettingsActivity;
import com.sc.en.confucianism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.confucianism.layers.service.mails.SendMailTask;
import com.sc.en.confucianism.transverse.test.EspressoIdlingResource;
import com.sc.en.confucianism.R;
import com.sc.en.confucianism.injector.PresenterInjector;
import com.sc.en.confucianism.layers.mvp.common.customs.edittexts.InputFilterMinMax;
import com.sc.en.confucianism.layers.mvp.common.customs.spinkits.Style;
import com.sc.en.confucianism.layers.mvp.common.customs.viewpagers.ViewPagerNative;
import com.sc.en.confucianism.layers.mvp.common.graphics.Arrow;
import com.sc.en.confucianism.layers.mvp.common.layouts.linearlayouts.PersonalLinearLayout;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class FavoritesActivity extends MotherActivity implements FavoritesViewInterface, ObservableScrollView.Callbacks, FavoritesNativePagerAdapter.Callbacks, TypefacesRecyclerAdapter.Callbacks, ImageViewListener.Callbacks {

  private static final String TAG = "FavoritesActivity";
  /***********************************************************
   *  Presenter
   **********************************************************/
  /**
   * The Presenter associated with that view
   */
  private FavoritesPresenterInterface presenter = null;

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
  private int mMaxScrollY;
  private ObservableScrollView mObservableScrollView;
  private ViewPagerNative viewPager;
  // we name the left, middle and right page
  private static final int PAGE_LEFT = 0;
  private static final int PAGE_MIDDLE = 1;
  private static final int PAGE_RIGHT = 2;
  // we save each page in a model
  private int mSelectedPageIndex = -1;

  private TextViewNative txt, contentsTitlePage, txtDetails, txtnPositionQuotes, txtnSizeQuotes, txtAuthorBookName, txtGoToAccount, sendMails, txtGoToTypefaceSettings, aNative;
  private int mViewPagerTop;
  private CardView  containerfab, cvGoToTypefaceSettings;

  private PageModel leftPage;
  private PageModel middlePage;
  private PageModel rightPage;
  private ImageView ivAbHome, ivAbSearch, ivAddEmail;
  private ImageView ivMicrophonePrevious, ivMicrophoneStop, ivMicrophonePause, ivMicrophonePlay, ivMicrophoneNext;
  private ImageView fabRemoveFromFavorites,fabSocialNetworks, fabTypefaces, fabMicrophone, fabTxtIncrease, fabTxtDecrease;
  private Arrow arrowPrevious, arrowNext;
  private Boolean isFabOpen = false;
  private Boolean isRlNumberIsOpen = false;

  private Animation fab_open;
  private Animation fab_close;
  private Animation alpha_in;
  private Animation alpha_out;
  private Animation rlnumber_alpha_in;
  private Animation rlnumber_alpha_out;
  private Animation rlnumber_alpha_in_no_duration;
  private Animation viewpager_alpha_in;
  private AppCompatEditText etChooseQuotes, etMailComment, inputEmail;
  private TextInputLayout  inputLayoutEmail;
  private Dialog removeFromFavoritesDialog;
  private Dialog socialNetworksDialog;
  private Dialog typeFacesDialog;
  private Dialog microphonesDialog;
  private Dialog goToTypefaceSettingsDialog;
  private Dialog accountsDialog;
  private Dialog confirmTypefaceChoiceDialog;
  private Dialog mailsDialog ;
  private RecyclerView typeFacesDialogListView, mailsDialogListview, mailsAddedDialogListview;
  private TextView confirmAddToFavorites;
  private TextView cancelAddToFavorites;
  private TextView confirmTypefaceChoice;
  private TextView cancelTypefaceChoice;
  private TextView confirmGoToAccount;
  private TextView cancelGoToAccount;
  private TextView goToTypefaceSettings ;
  private int oldLeftIndex;
  private int oldMiddleIndex;
  private int oldRightIndex;
  private MailsAddedRecyclerAdapter mailsAddedRecyclerAdapter;
  FavoritesNativePagerAdapter adapter;
  private LinearLayout llSearch;
  private LinearLayout llNumber;

  private int middlePagePosition = -1;

  private Typeface typeface;
  private String tpString;

  private static TextToSpeech textToSpeech;
  private boolean isOnPause = false;
  private String destFileName;
  private HashMap<String, String> myHashRender;
  private String utteranceID;
  private boolean isForMicrophone = false;
  private static ArrayMap<String, SoundPoolPlayer> soundPoolPlayers;
  private boolean plays = false;

  private boolean isAuthor = false;
  private int chunkPosition = 0 ;
  private static String utterancePosition;

  private Handler handler;
  private List<String> chunks;
  private int idQuote = -1;
  private DisplayMetrics displaymetrics;
  private LinearLayout rlNumber;
  private int mObservableHeight;
  private LinearLayout llHeader;

  private int width;
  private int height ;
  private List<String> ttsFileStrings;
  private MailsRecyclerAdapter mailsRecyclerAdapter;
  private TypefacesRecyclerAdapter typefacesRecyclerAdapter;
  private SoundPool soundPool;
  private boolean isTypefaceChanged = false;
  private boolean isTypefaceDialogShowing = false;
  private boolean isMailDialogShowing = false;
  private boolean isMicrophoneDialogShowing = false;
  private boolean isMicrophoneNextOrPrevious = false;
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

    if (presenter.getFavoritesPageModels().length == 0) {
      presenter.loadFavoritesPageModels();
      presenter.loadMailAccount();
    }
  }

  @Override
  public void onStop() {
    super.onStop();
  }

  @Override
  public void onDestroy() {

    if (isTypefaceDialogShowing) typeFacesDialog.cancel();
    if (isMailDialogShowing) mailsDialog.cancel();
    if (isMicrophoneDialogShowing) microphonesDialog.cancel();

    if (soundPool != null) {
      soundPool.release();
      soundPool.setOnLoadCompleteListener(null);
      soundPool = null;
    }

    if (middlePage != null) {
      editor.putInt(Constants.FAVORITE_CONTENT_QUOTE_ID, middlePage.getIndex());
      editor.commit();
    }

    if(mailsAddedRecyclerAdapter != null) {
      if(mailsAddedRecyclerAdapter.soundPool != null) {
        mailsAddedRecyclerAdapter.soundPool.get().release();
        mailsAddedRecyclerAdapter.soundPool.get().setOnLoadCompleteListener(null);
        mailsAddedRecyclerAdapter.soundPool = null;
      }
      mailsAddedRecyclerAdapter = null;
    }
    if(typefacesRecyclerAdapter != null) {
      if(typefacesRecyclerAdapter.soundPool != null) {
        typefacesRecyclerAdapter.soundPool.get().release();
        typefacesRecyclerAdapter.soundPool.get().setOnLoadCompleteListener(null);
        typefacesRecyclerAdapter.soundPool = null;
      }
      typefacesRecyclerAdapter = null;
    }
    if(mailsRecyclerAdapter != null) {
      if(mailsRecyclerAdapter.soundPool != null) {
        mailsRecyclerAdapter.soundPool.get().release();
        mailsRecyclerAdapter.soundPool.get().setOnLoadCompleteListener(null);
        mailsRecyclerAdapter.soundPool = null;
      }
      mailsRecyclerAdapter = null;
    }

    if(!isOrientationChanged) {

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

          if (player != null) {
            player.release();
            player.context = null;
            player.setOnCompletionListener(null);
          }
        }
        soundPoolPlayers.clear();
      }

      //viewPager.adapter = null;
      //viewPager = null;
//      presenter.setFavoritesPageModels();
//      presenter.setFavoritesViewInterface();
//      presenter = null;
//      llSearch = null;
//      txt = contentsTitlePage = txtDetails = txtnPositionQuotes = txtnSizeQuotes = txtAuthorBookName = txtGoToAccount = sendMails = aNative = null;
//      confirmAddToFavorites = cancelAddToFavorites = confirmTypefaceChoice = cancelTypefaceChoice = confirmGoToAccount = cancelGoToAccount = goToTypefaceSettings = null;
//      typeFacesDialogListView = mailsDialogListview = mailsAddedDialogListview = null;
//      socialNetworksDialog = typeFacesDialog = confirmTypefaceChoiceDialog = microphonesDialog = goToTypefaceSettingsDialog = accountsDialog = mailsDialog = null;
//      etChooseQuotes = etMailComment = inputEmail = null;
//      inputLayoutEmail = null;
//      mailsAddedRecyclerAdapter = null;
//      fab_open = fab_close = alpha_in = alpha_out = rlnumber_alpha_in = rlnumber_alpha_out = null;
//      arrowPrevious = arrowNext = null;
//      fabRemoveFromFavorites = fabSocialNetworks = fabTypefaces = fabMicrophone = fabTxtIncrease = fabTxtDecrease = null;
//      ivMicrophonePrevious = ivMicrophoneStop = ivMicrophonePause = ivMicrophonePlay = ivMicrophoneNext = null;
//      ivAbHome = ivAbSearch = ivAddEmail = null;
//      leftPage = middlePage = rightPage = null;
//      typeface = null;
//      tpString = null;
//      textToSpeech = null;
//      mPlaceholderView = null;
//      mObservableScrollView = null;
//      rlNumber = null;
//      containerfab = null;
//      if (handler != null) {
//        handler = null;
//      }
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
    }
    super.onDestroy();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    if(presenter.getFavoritesPageModels().length != 0) {
      if (middlePage != null ) outState.putInt("idQuote", middlePage.getIndex());
    }

    if(isFabOpen){
      outState.putBoolean("isFabOpen", true);
    } else {
      outState.putBoolean("isFabOpen", false);
    }

    outState.putBoolean("isTypefaceDialogShowing" , isTypefaceDialogShowing);
    outState.putBoolean("isMailDialogShowing", isMailDialogShowing);
    outState.putBoolean("isMicrophoneDialogShowing", isMicrophoneDialogShowing);
    outState.putBoolean("plays", plays);
    outState.putBoolean("isOnPause", isOnPause);

    if(ttsFileStrings != null) outState.putStringArrayList("ttsFileStrings",(ArrayList<String>) ttsFileStrings);
    if (chunks != null) outState.putStringArrayList("chunks", (ArrayList<String>) chunks);
    isOrientationChanged = true;
    outState.putBoolean("isFromMailOrTypefaceSetings", isFromMailOrTypefaceSetings);
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if(savedInstanceState != null) {
      idQuote = savedInstanceState.getInt("idQuote");
      isFabOpen = savedInstanceState.getBoolean("isFabOpen");
      isTypefaceDialogShowing = savedInstanceState.getBoolean("isTypefaceDialogShowing");
      isMailDialogShowing = savedInstanceState.getBoolean("isMailDialogShowing");
      isMicrophoneDialogShowing = savedInstanceState.getBoolean("isMicrophoneDialogShowing");
      plays = savedInstanceState.getBoolean("plays");
      isOnPause = savedInstanceState.getBoolean("isOnPause");

      ttsFileStrings = savedInstanceState.getStringArrayList("ttsFileStrings");
      chunks = savedInstanceState.getStringArrayList("chunks");
      isFromMailOrTypefaceSetings = savedInstanceState.getBoolean("isFromMailOrTypefaceSetings");

      isOrientationChanged = true;
    } else {
      isOrientationChanged = false;
    }

    if (getIntent().hasExtra("isFromMailOrTypefaceSetings"))
      isFromMailOrTypefaceSetings = getIntent().getBooleanExtra("isFromMailOrTypefaceSetings", false);

    setContentView(R.layout.activity_favorites);

    presenter = PresenterInjector.getFavoritesPresenter(this);
    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    editor = settings.edit();

    if(savedInstanceState != null) idQuote = settings.getInt(Constants.FAVORITE_CONTENT_QUOTE_ID, -1);

    String tpString = settings.getString(Constants.TYPEFACE, "");

    typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/" + settings.getString(Constants.TYPEFACE, "") + ".ttf");

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

    ImageViewListener txtDetailsListener  = new ImageViewListener(txtDetails,1.05f);
    txtDetailsListener.setCallbacks(this);
    txtDetails.setOnTouchListener(txtDetailsListener);
    txtDetails.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    txtDetails.bringToFront();

    txtAuthorBookName = (TextViewNative) findViewById(R.id.txt_author_book_name);
    txtAuthorBookName.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    txtAuthorBookName.bringToFront();

    ImageViewListener txtAuthorBookListener  = new ImageViewListener(txtAuthorBookName,1.05f);
    txtAuthorBookListener.setCallbacks(this);
    txtAuthorBookName.setOnTouchListener(txtAuthorBookListener);

    contentsTitlePage = (TextViewNative) findViewById(R.id.title_favorites);
    mObservableScrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
    mObservableScrollView.setCallbacks(this);
    mObservableScrollView.setVerticalScrollBarEnabled(false);
    mObservableScrollView.setHorizontalScrollBarEnabled(false);
    mPlaceholderView = findViewById(R.id.place_holder);

    viewPager = (ViewPagerNative) findViewById(R.id.viewpager);

    containerfab = (CardView) findViewById(R.id.fab_layout);

    fabRemoveFromFavorites = (ImageView) findViewById(R.id.remove_from_favorites);
    fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
    ImageViewListener fabRemoveFromFavoritesListener = new ImageViewListener(fabRemoveFromFavorites, 1.3f);
    fabRemoveFromFavoritesListener.setCallbacks(this);
    fabRemoveFromFavorites.setOnTouchListener(fabRemoveFromFavoritesListener);

    fabTxtIncrease = (ImageView) findViewById(R.id.fab_txt_increase);
    ImageViewListener fabTxtIncreaseListener = new ImageViewListener(fabTxtIncrease, 1.3f);
    fabTxtIncreaseListener.setCallbacks(this);
    fabTxtIncrease.setOnTouchListener(fabTxtIncreaseListener);
    fabTxtDecrease = (ImageView) findViewById(R.id.fab_txt_decrease);
    ImageViewListener fabTxtDecreaseListener = new ImageViewListener(fabTxtDecrease, 1.3f);
    fabTxtDecreaseListener.setCallbacks(this);
    fabTxtDecrease.setOnTouchListener(fabTxtDecreaseListener);

    fabSocialNetworks = (ImageView) findViewById(R.id.fab_social_networks);

    ImageViewListener fabSocialNetworksListener  = new ImageViewListener(fabSocialNetworks,1.3f);
    fabSocialNetworksListener.setCallbacks(this);
    fabSocialNetworks.setOnTouchListener(fabSocialNetworksListener);
    fabTypefaces = (ImageView) findViewById(R.id.fab_type_faces);
    ImageViewListener fabTypefacesListener  = new ImageViewListener(fabTypefaces,1.3f);
    fabTypefacesListener.setCallbacks(this);
    fabTypefaces.setOnTouchListener(fabTypefacesListener);
    fabMicrophone = (ImageView) findViewById(R.id.fab_microphone);
    ImageViewListener fabMicrophonesListener  = new ImageViewListener(fabMicrophone,1.3f);
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

    Animation rlnumber_alpha_out_no_duration = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rlnumber_alpha_out_no_duration);
    rlnumber_alpha_in_no_duration = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rlnumber_alpha_in_no_duration);

    viewpager_alpha_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rlnumber_alpha_in);
    Animation viewpager_alpha_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rlnumber_alpha_out);
    Animation viewpager_alpha_out_no_duration = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rlnumber_alpha_out_no_duration);
    ivAbHome = (ImageView) findViewById(R.id.iv_back);
    ImageViewListener ivAbHomeListener = new ImageViewListener(ivAbHome, 1.3f);
    ivAbHomeListener.setCallbacks(this);
    ivAbHome.setOnTouchListener(ivAbHomeListener);

    rlNumber = (LinearLayout) findViewById(R.id.rl_number);
    rlNumber.startAnimation(rlnumber_alpha_out_no_duration);

    rlNumber.bringToFront();

    llHeader = (LinearLayout) findViewById(R.id.ll_header);
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

    llSearch = (LinearLayout) findViewById(R.id.ll_search);
    llNumber = (LinearLayout) findViewById(R.id.ll_number);

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

        if(presenter.getFavoritesPageModels().length > 1) {

          arrowPrevious.clearAnimation();
          arrowPrevious.setVisibility(View.GONE);

          ivAbSearch.clearAnimation();
          ivAbSearch.setVisibility(View.GONE);

          arrowNext.clearAnimation();
          arrowNext.setVisibility(View.GONE);
        }

        llSearch.clearAnimation();

        llSearch.setVisibility(View.GONE);
        rlNumber.clearAnimation();

        llNumber.setVisibility(View.GONE);

        ivAbHome.clearAnimation();
        ivAbHome.setVisibility(View.GONE);

        rlNumber.clearAnimation();

        rlNumber.setVisibility(View.GONE);
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

        fabRemoveFromFavorites.clearAnimation();

        fabRemoveFromFavorites.setVisibility(View.GONE);
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

      //  mQuickReturnWidthForTitlePage = ivQuoteLeft.getWidth();
      if (mViewPagerTop == 0) {
        mViewPagerTop = height;
      }

      int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
      int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);


      if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

        if(txtAuthorBookName.getVisibility() == View.VISIBLE) {

          FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
          params.topMargin = (int) (llHeader.getBottom() + getResources().getDimension(R.dimen.activity_horizontal_margin)  /** 4*/);
          rlNumber.setLayoutParams(params);

          if (mObservableHeight == 0) {
            rlNumber.measure(widthMeasureSpec, heightMeasureSpec);
            mObservableHeight = (int) (llHeader.getBottom() + rlNumber.getMeasuredHeight() - getResources().getDimension(R.dimen.activity_horizontal_margin));
          }
        } else {
          FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
          params.topMargin = (int) (contentsTitlePage.getBottom() + getResources().getDimension(R.dimen.activity_horizontal_margin)  /** 4*/);
          rlNumber.setLayoutParams(params);
        }
      } else {
        if (mObservableHeight == 0) mObservableHeight = (int) (llHeader.getBottom() + getResources().getDimension(R.dimen.activity_horizontal_margin));

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
        params.topMargin = (int) (txtAuthorBookName.getTop() + getResources().getDimension(R.dimen.action_bar_margin)  /** 4*/);
        rlNumber.setLayoutParams(params);
      }
    });

    //ImageView ivQuoteLeft = (ImageView) findViewById(R.id.quote_left);

    displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    height = displaymetrics.heightPixels;
    int width = displaymetrics.widthPixels;

    int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    txtDetails.measure(widthMeasureSpec, heightMeasureSpec);
    txtnPositionQuotes.measure(widthMeasureSpec, heightMeasureSpec);

    viewPager.startAnimation(viewpager_alpha_out_no_duration);
    viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            height));

    animate(viewPager).alpha(0);

    mObservableScrollView.smoothScrollTo(0, 0);

    removeFromFavoritesDialog = new Dialog(FavoritesActivity.this, R.style.myDialogSlideUpAndDown);
    removeFromFavoritesDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    removeFromFavoritesDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    removeFromFavoritesDialog.setContentView(R.layout.add_to_favorites_dialog);

    accountsDialog = new Dialog(FavoritesActivity.this, R.style.myDialogSlideUpAndDown);
    accountsDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    accountsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    accountsDialog.setContentView(R.layout.accounts_dialog);

    accountsDialog.setOnDismissListener(dialog -> {
      if (viewPager == null) viewPager = (ViewPagerNative) findViewById(R.id.viewpager);
      animate(viewPager).alpha(1).setDuration(800).start();
      if (txtAuthorBookName == null) txtAuthorBookName = (TextViewNative) findViewById(R.id.txt_author_book_name);
      animate(txtAuthorBookName).alpha(1).setDuration(800).start();
      if (txtDetails == null) txtDetails = (TextViewNative) findViewById(R.id.txt_details);
      animate(txtDetails).alpha(1).setDuration(800).start();
    });

    txtGoToAccount = (TextViewNative) accountsDialog.findViewById(R.id.txt_accounts_ask);

    confirmGoToAccount = (TextView) accountsDialog.findViewById(R.id.confirm_go_to_account);
    confirmGoToAccount.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    cancelGoToAccount = (TextView) accountsDialog.findViewById(R.id.cancel_go_to_account);
    cancelGoToAccount.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    ImageViewListener cancelGoToAccountListener = new ImageViewListener(cancelGoToAccount, 1.3f);
    cancelGoToAccountListener.setCallbacks(this);
    cancelGoToAccount.setOnTouchListener(cancelGoToAccountListener);

    goToTypefaceSettingsDialog = new Dialog(FavoritesActivity.this, R.style.myDialogSlideUpAndDown);
    goToTypefaceSettingsDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    goToTypefaceSettingsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    goToTypefaceSettingsDialog.setContentView(R.layout.typefaces_choice_dialog);

    goToTypefaceSettingsDialog.setOnDismissListener(dialog -> {
      if (viewPager == null) viewPager = (ViewPagerNative) findViewById(R.id.viewpager);
      animate(viewPager).alpha(1).setDuration(800).start();
      if (txtAuthorBookName == null) txtAuthorBookName = (TextViewNative) findViewById(R.id.txt_author_book_name);
      animate(txtAuthorBookName).alpha(1).setDuration(800).start();
      if (txtDetails == null) txtDetails = (TextViewNative) findViewById(R.id.txt_details);
      animate(txtDetails).alpha(1).setDuration(800).start();
    });

    goToTypefaceSettings = (TextView) goToTypefaceSettingsDialog.findViewById(R.id.go_to_typeface_settings);
    goToTypefaceSettings.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    ImageViewListener goToTypefaceSettingsListener = new ImageViewListener(goToTypefaceSettings, 1.05f);
    goToTypefaceSettingsListener.setCallbacks(this);
    goToTypefaceSettings.setOnTouchListener(goToTypefaceSettingsListener);


    typeFacesDialog = new Dialog(FavoritesActivity.this, R.style.myDialogSlideUpAndDown);
    typeFacesDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    typeFacesDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    typeFacesDialog.setContentView(R.layout.typefaces_dialog);

    typeFacesDialog.setOnDismissListener(dialog -> {
      if (viewPager != null) {
        animate(viewPager).alpha(1).setDuration(800).start();
        if (txtAuthorBookName != null) animate(txtAuthorBookName).alpha(1).setDuration(800).start();
        if (txtDetails != null) animate(txtDetails).alpha(1).setDuration(800).start();
      }
      isTypefaceDialogShowing = false;
    });

    typeFacesDialogListView = (RecyclerView) typeFacesDialog.findViewById(R.id.typefaces_dialog_list_view);
    typeFacesDialogListView.setLayoutManager(new LinearLayoutManager(this));
    typeFacesDialogListView.setNestedScrollingEnabled(false);

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

    confirmTypefaceChoiceDialog = new Dialog(FavoritesActivity.this, R.style.myDialogSlideUpAndDown);
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

    microphonesDialog = new Dialog(FavoritesActivity.this, R.style.myDialogSlideUpAndDown);
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

    confirmAddToFavorites = (TextView) removeFromFavoritesDialog.findViewById(R.id.confirm_add_to_favorites);
    cancelAddToFavorites = (TextView) removeFromFavoritesDialog.findViewById(R.id.cancel_add_to_favorites);

    confirmAddToFavorites = (TextView) removeFromFavoritesDialog.findViewById(R.id.confirm_add_to_favorites);
    ImageViewListener confirmAddToFavoritesListener = new ImageViewListener(confirmAddToFavorites, 1.3f);
    confirmAddToFavoritesListener.setCallbacks(this);
    confirmAddToFavorites.setOnTouchListener(confirmAddToFavoritesListener);

    cancelAddToFavorites = (TextView) removeFromFavoritesDialog.findViewById(R.id.cancel_add_to_favorites);
    ImageViewListener cancelAddToFavoritesListener = new ImageViewListener(cancelAddToFavorites, 1.3f);
    cancelAddToFavoritesListener.setCallbacks(this);
    cancelAddToFavorites.setOnTouchListener(cancelAddToFavoritesListener);

    confirmAddToFavorites.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    cancelAddToFavorites.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    mailsDialog = new Dialog(FavoritesActivity.this, R.style.myDialogSlideUpAndDown);

    mailsDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    mailsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    mailsDialog.setContentView(R.layout.mails_dialog);

    mailsDialog.setOnDismissListener(dialog -> {
      viewPager.setVisibility(View.VISIBLE);
      viewPager.setBackgroundColor(Color.GREEN);
      animate(viewPager).alpha(1).setDuration(800).start();
      animate(txtAuthorBookName).alpha(1).setDuration(800).start();
      animate(txtDetails).alpha(1).setDuration(800).start();
      isMailDialogShowing = false;
    });

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
      editor.apply();      isMailDialogShowing = false;

      if(mailsAddedRecyclerAdapter != null) {
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

    if (getIntent().getStringExtra("fromSettings") != null) {
      if (getIntent().getStringExtra("fromSettings").equals("typeface")) {
        showTypeFaceDialog();
      }

      if (getIntent().getStringExtra("fromSettings").equals("mail")) {
        goToMailActivity();
      }
    }

    dQuotesLoading = new Dialog(FavoritesActivity.this, R.style.myDialogSlideUpAndDown);
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

  private void goToQuote(int idQuote) {

    leftPage = presenter.getFavoritesPageModels()[PAGE_LEFT];
    middlePage = presenter.getFavoritesPageModels()[PAGE_MIDDLE];
    rightPage = presenter.getFavoritesPageModels()[PAGE_RIGHT];

    oldLeftIndex = leftPage.getIndex();
    oldMiddleIndex = middlePage.getIndex();
    oldRightIndex = rightPage.getIndex();

    if (idQuote == presenter.getFavoritesPageModels().length) {

      leftPage.setIndex(idQuote - 1, presenter.getFavoritesPageModels()[idQuote - 1].getQuote());
      middlePage.setIndex(idQuote, presenter.getFavoritesPageModels()[0].getQuote());
      rightPage.setIndex(1, presenter.getFavoritesPageModels()[1].getQuote());

      mSelectedPageIndex = -1;
      setContent(PAGE_LEFT);
      setContent(PAGE_MIDDLE);
      setContent(PAGE_RIGHT);

      if (presenter.getFavoritesPageModels()[0].getQuote().isFavorites()) {
        fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
        fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
      } else {
        fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
        fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
      }
    } else if (idQuote == 1) {

      leftPage.setIndex(presenter.getFavoritesPageModels().length, presenter.getFavoritesPageModels()[presenter.getFavoritesPageModels().length - 1].getQuote());
      middlePage.setIndex(idQuote, presenter.getFavoritesPageModels()[1].getQuote());
      rightPage.setIndex(idQuote + 1, presenter.getFavoritesPageModels()[idQuote + 1].getQuote());

      mSelectedPageIndex = -1;
      setContent(PAGE_LEFT);
      setContent(PAGE_MIDDLE);
      setContent(PAGE_RIGHT);

      if (presenter.getFavoritesPageModels()[1].getQuote().isFavorites()) {
        fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
        fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
      } else {
        fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
        fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
      }

    } else if (idQuote == presenter.getFavoritesPageModels().length - 1) {
      leftPage.setIndex(idQuote - 1, presenter.getFavoritesPageModels()[idQuote - 1].getQuote());
      middlePage.setIndex(idQuote, presenter.getFavoritesPageModels()[idQuote].getQuote());
      rightPage.setIndex(idQuote + 1, presenter.getFavoritesPageModels()[0].getQuote());

      mSelectedPageIndex = -1;
      setContent(PAGE_LEFT);
      setContent(PAGE_MIDDLE);
      setContent(PAGE_RIGHT);

      if (presenter.getFavoritesPageModels()[idQuote].getQuote().isFavorites()) {
        fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
        fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
      } else {
        fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
        fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
      }

    } else {
      leftPage.setIndex(idQuote - 1, presenter.getFavoritesPageModels()[idQuote - 1].getQuote());
      middlePage.setIndex(idQuote, presenter.getFavoritesPageModels()[idQuote].getQuote());
      rightPage.setIndex(idQuote + 1, presenter.getFavoritesPageModels()[idQuote + 1].getQuote());

      mSelectedPageIndex = -1;
      setContent(PAGE_LEFT);
      setContent(PAGE_MIDDLE);
      setContent(PAGE_RIGHT);

      if (presenter.getFavoritesPageModels()[idQuote].getQuote().isFavorites()) {
        fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
        fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
      } else {
        fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
        fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
      }
    }
  }

  private void swipeOnLeft() {
    if(!presenter.isPageModelsIsSizeOfTwo()) {

      if (oldMiddleIndex == 0) {
        leftPage.setIndex(presenter.getFavoritesPageModels().length - 1, presenter.getFavoritesPageModels()[presenter.getFavoritesPageModels().length - 1].getQuote());
        middlePage.setIndex(presenter.getFavoritesPageModels().length, presenter.getFavoritesPageModels()[0].getQuote());
        rightPage.setIndex(1, presenter.getFavoritesPageModels()[1].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        if (arrowPrevious.getVisibility() == View.VISIBLE) animate(arrowPrevious).setListener(null);

        if (presenter.getFavoritesPageModels()[0].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }
      } else if (oldMiddleIndex == 1) {
        leftPage.setIndex(presenter.getFavoritesPageModels().length - 1, presenter.getFavoritesPageModels()[presenter.getFavoritesPageModels().length - 2].getQuote());
        middlePage.setIndex(presenter.getFavoritesPageModels().length, presenter.getFavoritesPageModels()[0].getQuote());
        rightPage.setIndex(oldMiddleIndex, presenter.getFavoritesPageModels()[oldMiddleIndex].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        if (arrowPrevious.getVisibility() == View.VISIBLE) animate(arrowPrevious).setListener(null);

        if (presenter.getFavoritesPageModels()[0].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }

      } else if (oldMiddleIndex == presenter.getFavoritesPageModels().length) {
        leftPage.setIndex(oldLeftIndex - 1, presenter.getFavoritesPageModels()[oldLeftIndex - 1].getQuote());
        middlePage.setIndex(oldLeftIndex, presenter.getFavoritesPageModels()[oldLeftIndex].getQuote());
        rightPage.setIndex(oldMiddleIndex, presenter.getFavoritesPageModels()[oldMiddleIndex - 1].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        if (arrowPrevious.getVisibility() == View.VISIBLE) animate(arrowPrevious).setListener(null);

        if (presenter.getFavoritesPageModels()[oldLeftIndex].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }

      } else if (oldMiddleIndex == 2) {

        leftPage.setIndex(presenter.getFavoritesPageModels().length, presenter.getFavoritesPageModels()[presenter.getFavoritesPageModels().length - 1].getQuote());
        middlePage.setIndex(1, presenter.getFavoritesPageModels()[1].getQuote());
        rightPage.setIndex(oldMiddleIndex, presenter.getFavoritesPageModels()[oldMiddleIndex].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        if (arrowPrevious.getVisibility() == View.VISIBLE) animate(arrowPrevious).setListener(null);

        if (presenter.getFavoritesPageModels()[oldLeftIndex].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }

      } else {
        leftPage.setIndex(oldLeftIndex - 1, presenter.getFavoritesPageModels()[oldLeftIndex - 1].getQuote());
        middlePage.setIndex(oldLeftIndex, presenter.getFavoritesPageModels()[oldLeftIndex].getQuote());
        rightPage.setIndex(oldMiddleIndex, presenter.getFavoritesPageModels()[oldMiddleIndex].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        if (arrowPrevious.getVisibility() == View.VISIBLE) animate(arrowPrevious).setListener(null);

        if (presenter.getFavoritesPageModels()[oldLeftIndex].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }

      }
    } else {
      if(oldMiddleIndex == 1) {
        leftPage.setIndex(1, presenter.getFavoritesPageModels()[1].getQuote());
        middlePage.setIndex(2, presenter.getFavoritesPageModels()[0].getQuote());
        rightPage.setIndex(1, presenter.getFavoritesPageModels()[1].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        if (arrowPrevious.getVisibility() == View.VISIBLE) animate(arrowPrevious).setListener(null);

        if (presenter.getFavoritesPageModels()[2].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }
      } else if(oldMiddleIndex == 2) {
        leftPage.setIndex(2, presenter.getFavoritesPageModels()[0].getQuote());
        middlePage.setIndex(1, presenter.getFavoritesPageModels()[1].getQuote());
        rightPage.setIndex(2, presenter.getFavoritesPageModels()[0].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        if (arrowPrevious.getVisibility() == View.VISIBLE) animate(arrowPrevious).setListener(null);

        if (presenter.getFavoritesPageModels()[1].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }
      }
    }
  }

  private void swipeOnRight() {

    if(!presenter.isPageModelsIsSizeOfTwo()) {

      if (oldMiddleIndex == 0) {
        leftPage.setIndex(0, presenter.getFavoritesPageModels()[1].getQuote());
        middlePage.setIndex(oldRightIndex + 1, presenter.getFavoritesPageModels()[oldRightIndex + 1].getQuote());
        rightPage.setIndex(oldRightIndex + 2, presenter.getFavoritesPageModels()[oldRightIndex + 2].getQuote());
        setContent(PAGE_LEFT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_RIGHT);

        if (arrowNext.getVisibility() == View.VISIBLE) animate(arrowNext).setListener(null);

        if (presenter.getFavoritesPageModels()[oldRightIndex + 1].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }


        return;
      }
      if (oldMiddleIndex == 1) {
        leftPage.setIndex(oldMiddleIndex, presenter.getFavoritesPageModels()[oldMiddleIndex].getQuote());
        middlePage.setIndex(oldMiddleIndex + 1, presenter.getFavoritesPageModels()[oldMiddleIndex + 1].getQuote());
        if(oldMiddleIndex + 2 != presenter.getFavoritesPageModels().length) {
          rightPage.setIndex(oldMiddleIndex + 2, presenter.getFavoritesPageModels()[oldMiddleIndex + 2].getQuote());
        } else {
          rightPage.setIndex(oldMiddleIndex + 2, presenter.getFavoritesPageModels()[0].getQuote());
        }

        setContent(PAGE_LEFT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_RIGHT);
        if (arrowNext.getVisibility() == View.VISIBLE) animate(arrowNext).setListener(null);

        if (presenter.getFavoritesPageModels()[oldMiddleIndex + 1].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }
      } else if (oldMiddleIndex == (presenter.getFavoritesPageModels().length - 1)) {
        leftPage.setIndex(oldMiddleIndex, presenter.getFavoritesPageModels()[oldMiddleIndex].getQuote());
        middlePage.setIndex(presenter.getFavoritesPageModels().length, presenter.getFavoritesPageModels()[0].getQuote());
        rightPage.setIndex(1, presenter.getFavoritesPageModels()[1].getQuote());

        setContent(PAGE_LEFT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_RIGHT);
        if (arrowNext.getVisibility() == View.VISIBLE) animate(arrowNext).setListener(null);

        if (presenter.getFavoritesPageModels()[0].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }

      } else if (oldMiddleIndex == (presenter.getFavoritesPageModels().length - 2)) {
        leftPage.setIndex(oldMiddleIndex, presenter.getFavoritesPageModels()[oldMiddleIndex].getQuote());
        middlePage.setIndex(oldRightIndex, presenter.getFavoritesPageModels()[oldRightIndex].getQuote());
        rightPage.setIndex(presenter.getFavoritesPageModels().length - 1, presenter.getFavoritesPageModels()[presenter.getFavoritesPageModels().length - 1].getQuote());

        setContent(PAGE_LEFT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_RIGHT);
        if (arrowNext.getVisibility() == View.VISIBLE) animate(arrowNext).setListener(null);

        if (presenter.getFavoritesPageModels()[oldRightIndex].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }

      } else if (oldMiddleIndex == presenter.getFavoritesPageModels().length) {
        leftPage.setIndex(oldMiddleIndex, presenter.getFavoritesPageModels()[0].getQuote());
        middlePage.setIndex(1, presenter.getFavoritesPageModels()[1].getQuote());
        rightPage.setIndex(oldRightIndex + 1, presenter.getFavoritesPageModels()[oldRightIndex + 1].getQuote());

        setContent(PAGE_LEFT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_RIGHT);
        if (arrowNext.getVisibility() == View.VISIBLE) animate(arrowNext).setListener(null);

        if (presenter.getFavoritesPageModels()[1].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }

      } else {
        leftPage.setIndex(oldMiddleIndex, presenter.getFavoritesPageModels()[oldMiddleIndex].getQuote());
        middlePage.setIndex(oldRightIndex, presenter.getFavoritesPageModels()[oldRightIndex].getQuote());
        rightPage.setIndex(oldRightIndex + 1, presenter.getFavoritesPageModels()[oldRightIndex + 1].getQuote());

        setContent(PAGE_LEFT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_RIGHT);

        if (presenter.getFavoritesPageModels()[oldRightIndex].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }
        if (arrowNext.getVisibility() == View.VISIBLE) animate(arrowNext).setListener(null);
      }
    } else {
      if(oldMiddleIndex == 1) {
        leftPage.setIndex(0, presenter.getFavoritesPageModels()[1].getQuote());
        middlePage.setIndex(2, presenter.getFavoritesPageModels()[2].getQuote());
        rightPage.setIndex(1, presenter.getFavoritesPageModels()[1].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        if (arrowNext.getVisibility() == View.VISIBLE) animate(arrowNext).setListener(null);

        if (presenter.getFavoritesPageModels()[2].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }
      } else if(oldMiddleIndex == 2) {
        leftPage.setIndex(0, presenter.getFavoritesPageModels()[2].getQuote());
        middlePage.setIndex(1, presenter.getFavoritesPageModels()[1].getQuote());
        rightPage.setIndex(2, presenter.getFavoritesPageModels()[2].getQuote());

        setContent(PAGE_RIGHT);
        setContent(PAGE_MIDDLE);
        setContent(PAGE_LEFT);
        if (arrowNext.getVisibility() == View.VISIBLE) animate(arrowNext).setListener(null);

        if (presenter.getFavoritesPageModels()[1].getQuote().isFavorites()) {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
        } else {
          fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
          fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
        }
      }
    }
  }

  private void setContent(int position) {

    viewPager.setPagingEnabled(false);

    PageModel model;

    if (position == PAGE_MIDDLE) {

      if (presenter.getFavoritesPageModels().length > 1) {
        model = presenter.getFavoritesPageModels()[position];

        model.setTypeface(typeface);
        if (model.textView == null) {

          model.llTxtView = presenter.getFavoritesPageModels()[position].llTxtView;
          model.sourceTxtView = presenter.getFavoritesPageModels()[position].sourceTxtView;
          model.cardView = presenter.getFavoritesPageModels()[position].cardView;
        }
      } else {
        model = presenter.getFavoritesPageModels()[0];

        model.setTypeface(typeface);

        if (model.textView == null) {

          model.llTxtView = presenter.getFavoritesPageModels()[0].llTxtView;
          model.sourceTxtView = presenter.getFavoritesPageModels()[0].sourceTxtView;
          model.cardView = presenter.getFavoritesPageModels()[0].cardView;
        }
      }

      ViewHelper.setAlpha(model.cardView, 0);
      ViewHelper.setAlpha(model.llTxtView, 0);
      ViewHelper.setAlpha(model.sourceTxtView, 0);


      if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

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
            ContentTextViewNativeBiography aNative1 = new ContentTextViewNativeBiography(FavoritesActivity.this);
            aNative1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            aNative1.setIncludeFontPadding(false);
            aNative1.setText("a");
            aNative1.measure(0, 0);
            model.llTxtView.measure(widthMeasureSpec, heightMeasureSpec);
            uWidth = (int) ((model.llTxtView.getMeasuredWidth() - getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) / aNative1.getMeasuredWidth());

            ContentTextViewNativeBiography aNative = new ContentTextViewNativeBiography(FavoritesActivity.this);
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
                  aNative = new ContentTextViewNativeBiography(FavoritesActivity.this);
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

                  aNative = new ContentTextViewNativeBiography(FavoritesActivity.this);
                  aNative.setIncludeFontPadding(false);
                  aNative.setText(s);
                  aNative.setCallbacks(adapter);
                  model.contentTextViewNatives.add(aNative);
                  isWhiteSpaced = true;
                } else if (isLastLine) {
                  s = model.getText().substring(i, model.getText().length());
                  k = s.length();
                  aNative = new ContentTextViewNativeBiography(FavoritesActivity.this);
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
            viewPager.startAnimation(viewpager_alpha_in);
          }

          isAuthor = model.isAuthor;

          txtDetails.setVisibility(View.GONE);
          if(middlePage.getQuote().getAuthor() != null) {
            txtAuthorBookName.setText(String.format("%s%s", middlePage.getAuthorBookNameText(), getResources().getString(R.string.space)));
          } else if (middlePage.getQuote().getBook() != null) {
            txtAuthorBookName.setText(String.format("%s%s", middlePage.getAuthorBookNameText(), getResources().getString(R.string.space)));
          } else {
            txtAuthorBookName.setVisibility(View.GONE);
          }

          if (!model.isPresentationOrBibliography) {
            txtAuthorBookName.setOnTouchListener(null);
            if (txtAuthorBookName.getText().length() != 0 && txtAuthorBookName.getVisibility() != View.GONE) {
              txtAuthorBookName.setTextColor(Color.BLACK);
            }
            txtDetails.setOnTouchListener(null);
          } else {
            if (txtAuthorBookName.getText().length() != 0 && txtAuthorBookName.getVisibility() != View.GONE) {
              txtAuthorBookName.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
            }
            ImageViewListener txtAuthorBookListener = new ImageViewListener(txtAuthorBookName, 1.05f);
            txtAuthorBookListener.setCallbacks(FavoritesActivity.this);
            txtAuthorBookName.setOnTouchListener(txtAuthorBookListener);
            txtAuthorBookName.bringToFront();
          }

          viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
              int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(model.cardView.getWidth(), View.MeasureSpec.AT_MOST);
              int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
              model.cardView.measure(widthMeasureSpec, heightMeasureSpec);
              txtDetails.measure(widthMeasureSpec, heightMeasureSpec);
              txtnPositionQuotes.measure(widthMeasureSpec, heightMeasureSpec);

              int i = model.cardView.getMeasuredHeight() + ((int) getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) + mObservableHeight;
              if (mViewPagerTop > i) {
                viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
              } else {
                viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, i));
              }

              PersonalLinearLayout.LayoutParams params1 = (PersonalLinearLayout.LayoutParams) middlePage.cardView.getLayoutParams();
              params1.topMargin = mObservableHeight;
              middlePage.cardView.setLayoutParams(params1);

              if(presenter.isPageModelsIsSizeOfTwo()) {
                txtnPositionQuotes.setText(String.format("%d ", ((model.getIndex() + 1) /*- presenter.getFavoritesPageModels().length*/ % 2) + 1));
              } else {
                txtnPositionQuotes.setText(String.format("%d ", model.getIndex()));
              }

              animate(model.cardView).alpha(1).setDuration(800).start();
              animate(model.llTxtView).alpha(1).setDuration(800).start();
              animate(model.sourceTxtView).alpha(1).setDuration(800).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                  super.onAnimationEnd(animation);
                  if (!viewPager.isPagingEnabled()) viewPager.setPagingEnabled(true);

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
      if(!isFinishing()) microphonesDialog.show();
      isMicrophoneDialogShowing = true;
      return;
    }

    if (!isFabOpen) {
      if(!isRlNumberIsOpen) {
        rlNumber.setVisibility(View.VISIBLE);
        rlNumber.startAnimation(rlnumber_alpha_in);

        if(presenter.getFavoritesPageModels().length > 1) {

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
      fabRemoveFromFavorites.setVisibility(View.VISIBLE);
      fabRemoveFromFavorites.startAnimation(fab_open);
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
      fabRemoveFromFavorites.startAnimation(fab_close);
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

    viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(middlePage.cardView.getWidth(), View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        middlePage.cardView.measure(widthMeasureSpec, heightMeasureSpec);

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

        txtnPositionQuotes.measure(widthMeasureSpec, heightMeasureSpec);
        int i = middlePage.cardView.getMeasuredHeight() + ((int) getResources().getDimension(R.dimen.activity_horizontal_margin) * 2)  + mObservableHeight;
        if (mViewPagerTop > i) {
          viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        } else {
          viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, i));
        }
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
    if(!tpString.trim().equals("")) {
      typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/" + tpString.trim() + ".ttf");
    } else {
      tpString = "Monotype-Corsiva-Regular";
      this.tpString = "Monotype-Corsiva-Regular";
      typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/" + tpString + ".ttf");
    }

    editor.putString(Constants.TYPEFACE, tpString.trim());
    editor.commit();
    isTypefaceChanged = true;
    if (idQuote != -1) middlePagePosition = idQuote;
    else middlePagePosition = settings.getInt(Constants.FAVORITE_CONTENT_QUOTE_ID, 0);

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

    stopQuoteMicrophone();

    Intent mainIntent = null;
    if (getIntent().getStringExtra("fromActivity").contains("TableContentsActivity")) {
      mainIntent = new Intent(this, TableContentsActivity.class);
      mainIntent.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));
      startActivity(new Intent(mainIntent));
      try {
        ActivityAnimator anim = new ActivityAnimator();
        anim.fadeAnimationToTableContents(FavoritesActivity.this);
      } catch (Exception ignored) {
      }
      this.finish();
      return;
    }
    try {
      stopQuoteMicrophone();
    } finally {

      if (getIntent().getStringExtra("fromActivity").contains("ContentsActivity")) {
        mainIntent = new Intent(this, ContentsActivity.class);
        mainIntent.putExtra("fromActivity", getIntent().getStringExtra("fromActivity"));
      }

      if (getIntent().getStringExtra("fromActivity").equals("FavoritesActivity")) {
        mainIntent = new Intent(this, FavoritesActivity.class);
        mainIntent.putExtra("fromActivity", getIntent().getStringExtra("fromActivity"));
      }

      if (getIntent().getStringExtra("fromActivity").contains("BiographyActivity")) {
        String[] strings = getIntent().getStringExtra("fromActivity").split(";");
        if (strings[1].equals("ContentsActivity")) {
          mainIntent = new Intent(this, ContentsActivity.class);
          mainIntent.putExtra("fromActivity", strings[1]);
        } else if (strings[1].equals("FavoritesActivity")) {
          mainIntent = new Intent(this, FavoritesActivity.class);
          mainIntent.putExtra("fromActivity", strings[1]);
        }
      }
      String name;

      if (getIntent().getStringExtra("type") != null) {
        if (getIntent().getStringExtra("type").contains(";")) {
          String[] strings = getIntent().getStringExtra("type").split(";");
          if (strings[1].equals(getResources().getString(R.string.themes)) || strings[1].equals(getResources().getString(R.string.movements))) {
            name = getIntent().getStringExtra("value");
          } else {
            name = getIntent().getStringExtra(getResources().getString(R.string.from));
          }
        } else {
          if (getIntent().getStringExtra("type").equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra("type").equals(getResources().getString(R.string.movements))) {
            name = getIntent().getStringExtra("value");
          } else {
            name = getIntent().getStringExtra(getString(R.string.from));
          }
        }
        if (getIntent().getStringExtra("type").contains(";")) {
          String[] strings = getIntent().getStringExtra("type").split(";");
          mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), getIntent().getStringExtra("fromFragment"));
          mainIntent.putExtra(getIntent().getStringExtra("fromFragment"), name);
        } else {
          mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), getIntent().getStringExtra("type"));
          mainIntent.putExtra(getIntent().getStringExtra("type"), name);
        }
      }

      mainIntent.putExtra("idContentQuote", getIntent().getStringExtra("idContentQuote"));
      mainIntent.putExtra("fromContent", getIntent().getStringExtra("fromContent"));

      mainIntent.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));

      startActivity(new Intent(mainIntent));
      try {
        ActivityAnimator anim = new ActivityAnimator();
        anim.fadeAnimationToTableContents(FavoritesActivity.this);
      } catch (Exception ignored) {
      }
      finish();
    }
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
    txt = (TextViewNative) removeFromFavoritesDialog.findViewById(R.id.txt_add_to_favorites_ask);
    if (fabRemoveFromFavorites.getTag().equals(R.drawable.ic_menu_remove_favorites)) {
      txt.setText(getResources().getString(R.string.removing_to_favorites_ask));
    } else {
      txt.setText(getResources().getString(R.string.adding_to_favorites_ask));
    }
    if(!isFinishing()) removeFromFavoritesDialog.show();
  }

  @Override
  public void confirmAddRemoveDialog() {
    presenter.init(true);
    if (middlePage.getIndex() == presenter.getFavoritesPageModels().length) {
      presenter.toggleQuoteIsFavorites((int) presenter.getFavoritesPageModels()[0].getQuote().getIdQuote());
    }else if( presenter.getFavoritesPageModels().length == 1) {
      presenter.toggleQuoteIsFavorites((int) presenter.getFavoritesPageModels()[0].getQuote().getIdQuote());

    } else if (presenter.getFavoritesPageModels().length != 0) {
      presenter.toggleQuoteIsFavorites((int) presenter.getFavoritesPageModels()[middlePage.getIndex()].getQuote().getIdQuote());
    }
    removeFromFavoritesDialog.cancel();
  }

  @Override
  public void cancelAddRemoveDialog() {
    removeFromFavoritesDialog.cancel();
  }

  @Override
  public void showSocialNetworksDialog() {
    if(!isFinishing()) socialNetworksDialog.show();
  }

  @Override
  public void showTypeFaceDialog() {
    containerfab.startAnimation(alpha_out);
    fabRemoveFromFavorites.startAnimation(fab_close);
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

    if(settings.getString(Constants.TYPEFACESLIST, "") == null || settings.getString(Constants.TYPEFACESLIST, "").equals("")) {
      if(!isFinishing()) goToTypefaceSettingsDialog.show();
    } else {
      typefacesRecyclerAdapter = new TypefacesRecyclerAdapter(getApplicationContext(),strings);
      typefacesRecyclerAdapter.setCallbacks(this);
      typeFacesDialogListView.setAdapter(typefacesRecyclerAdapter);
      if(!isFinishing()) typeFacesDialog.show();
      isTypefaceDialogShowing = true;
      WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
      Window window = typeFacesDialog.getWindow();
      lp.copyFrom(window.getAttributes());
      //This makes the dialog take up the full width
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
    fabRemoveFromFavorites.startAnimation(fab_close);
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
    if(!isFinishing()) Toast.makeText(getApplicationContext(), "full screen", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void startBiographyActivity() {

    Intent iBiography = new Intent(FavoritesActivity.this, BiographyActivity.class);

    iBiography.putExtra(getResources().getString(R.string.from), middlePage.getAuthorBookNameText());

    iBiography.putExtra("value", getResources().getString(R.string.favorites_title));
    iBiography.putExtra("idFavoriteQuote", txtnPositionQuotes.getText().toString());
    iBiography.putExtra("idContentQuote", getIntent().getStringExtra("idContentQuote"));
    iBiography.putExtra("fromContent", getIntent().getStringExtra("fromContent"));
    iBiography.putExtra("type", getIntent().getStringExtra(getResources().getString(R.string.fromFragment)));
    iBiography.putExtra("fromActivity", "FavoritesActivity;" + getIntent().getStringExtra("fromActivity"));
    iBiography.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));
    iBiography.putExtra("isAuthor", isAuthor);

    startActivity(iBiography);
    try {
      ActivityAnimator anim = new ActivityAnimator();
      anim.fadeAnimation((FavoritesActivity.this));
    } catch (Exception ignored) {
    }
    finish();
  }

  @Override
  public void startFaithsBiography() {

  }

  @Override
  public void arrowPrevious() {
    if (isFabOpen) {
      containerfab.startAnimation(alpha_out);
      fabRemoveFromFavorites.startAnimation(fab_close);
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
      fabRemoveFromFavorites.startAnimation(fab_close);
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
    Intent iTypefaces = new Intent(new Intent(FavoritesActivity.this, SettingsActivity.class));
    iTypefaces.putExtra(getResources().getString(R.string.from), getResources().getString(R.string.typefaces_nav_title) + ";" + txtAuthorBookName.getText().toString());

    if (getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra(getResources().getString(R.string.fromFragment)).equals(getResources().getString(R.string.movements))) {
      iTypefaces.putExtra(getResources().getString(R.string.returnTo), txtAuthorBookName.getText().toString().trim());
      iTypefaces.putExtra("value", txtAuthorBookName.getText().toString().trim());
    } else {
      iTypefaces.putExtra(getResources().getString(R.string.returnTo), txtAuthorBookName.getText().toString().trim());
    }
    iTypefaces.putExtra("idFavoriteQuote", txtnPositionQuotes.getText().toString());
    iTypefaces.putExtra("idContentQuote", getIntent().getStringExtra("idContentQuote"));
    iTypefaces.putExtra("fromContent", getIntent().getStringExtra("fromContent"));

    iTypefaces.putExtra("type", getIntent().getStringExtra(getResources().getString(R.string.from)));

    iTypefaces.putExtra("fromActivity", "FavoritesActivity;" + getIntent().getStringExtra("fromActivity"));
    iTypefaces.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));
    iTypefaces.putExtra("isAuthor", isAuthor);
    iTypefaces.putExtra("isFromTypefaceSettings", true);

    startActivity(iTypefaces);

    try {
      ActivityAnimator anim = new ActivityAnimator();
      anim.fadeAnimation(FavoritesActivity.this);
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
    fabRemoveFromFavorites.startAnimation(fab_close);
    fabTxtIncrease.startAnimation(fab_close);
    fabTxtDecrease.startAnimation(fab_close);
    fabSocialNetworks.startAnimation(fab_close);
    fabTypefaces.startAnimation(fab_close);
    fabMicrophone.startAnimation(fab_close);

    isFabOpen = false;

    if(settings.getString(Constants.MAIL_ACCOUNT, "") == null || settings.getString(Constants.MAIL_ACCOUNT, "").equals("")) {

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
      //This makes the dialog take up the full width
      lp.width = (WindowManager.LayoutParams.MATCH_PARENT);
      lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
      window.setAttributes(lp);
    }
  }

  private ArrayMap<String, String> getNameEmailDetails(){
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

    if(settings.equals(OnelittleAngelApplication.instance.getString(R.string.twitter))) {
      position = 0;
    } else if(settings.equals(OnelittleAngelApplication.instance.getString(R.string.mail))) {
      position = 1;
    } else if(settings.equals(OnelittleAngelApplication.instance.getString(R.string.facebook))) {
      position = 2;
    } else if(settings.equals(OnelittleAngelApplication.instance.getString(R.string.google_plus))) {
      position = 3;
    }

    editor.putInt(Constants.SOCIAL_NETWORKS_POSITION, position);
    editor.commit();

    Intent iSocialNetworks = new Intent(new Intent(FavoritesActivity.this, SettingsActivity.class));
    iSocialNetworks.putExtra(getResources().getString(R.string.from), getResources().getString(R.string.mail) + ";" + txtAuthorBookName.getText().toString());

    if(middlePage.getQuote().getAuthor() != null) {
      iSocialNetworks.putExtra(getResources().getString(R.string.returnTo), txtAuthorBookName.getText().toString());
    } else {
      iSocialNetworks.putExtra(getResources().getString(R.string.returnTo), txtAuthorBookName.getText().toString());
    }
    iSocialNetworks.putExtra("fromActivity", "FavoritesActivity;" + getIntent().getStringExtra("fromActivity"));
    iSocialNetworks.putExtra("type", "");
    iSocialNetworks.putExtra("idFavoriteQuote", txtnPositionQuotes.getText().toString());
    iSocialNetworks.putExtra("idContentQuote", getIntent().getStringExtra("idContentQuote"));
    iSocialNetworks.putExtra("fromContent", getIntent().getStringExtra("fromContent"));

    iSocialNetworks.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));
    iSocialNetworks.putExtra("isAuthor", isAuthor);
    iSocialNetworks.putExtra("isFromMailSettings", true);


    startActivity(iSocialNetworks);
    try {
      ActivityAnimator anim = new ActivityAnimator();
      anim.fadeAnimation((FavoritesActivity.this));
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
        int u = middlePage.getQuoteText().getBytes().length;
        CharSequence cs = middlePage.getQuoteText();

        if (chunks == null) {
          chunks = new ArrayList<>();

          if (middlePage.getQuoteText().contains(",")) {

            String[] dotChunks = middlePage.getQuoteText().split("\\.");
            for (int i = 0; i < dotChunks.length; i++) {
              String s = dotChunks[i].trim();

              String[] comaChunks = s.split(",");

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
            for (int i = 0; i < dotChunks.length; i++) {
              String s = dotChunks[i].trim();
              chunks.add(s);
            }
          }
        }

        for(int i = 0; i < chunks.size(); i++) {
          if(ttsFileStrings == null) {
            ttsFileStrings = new ArrayList<>();
          } else {
            ttsFileStrings.removeAll(ttsFileStrings);
          }
          chunkPosition = i;
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

        if(middlePage.getIndex() == presenter.getFavoritesPageModels().length) {
          if(presenter.getFavoritesPageModels()[0].getQuote().getAuthor() != null) {
            name = presenter.getFavoritesPageModels()[0].getQuote().getAuthor().getMcc1() + " " + presenter.getFavoritesPageModels()[0].getQuote().getAuthor().getName();
          } else {
            name = presenter.getFavoritesPageModels()[0].getQuote().getBook().getMcc1() + " " + presenter.getFavoritesPageModels()[0].getQuote().getBook().getName();
          }
        }  else {
          if(presenter.getFavoritesPageModels()[middlePage.getIndex()].getQuote().getAuthor() != null) {
            name = presenter.getFavoritesPageModels()[middlePage.getIndex()].getQuote().getAuthor().getMcc1() + " " + presenter.getFavoritesPageModels()[middlePage.getIndex()].getQuote().getAuthor().getName();
          } else {
            name = presenter.getFavoritesPageModels()[middlePage.getIndex()].getQuote().getBook().getMcc1() + " " + presenter.getFavoritesPageModels()[middlePage.getIndex()].getQuote().getBook().getName();
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

          Toast toast = Toast.makeText(FavoritesActivity.this, "", Toast.LENGTH_SHORT);
          aNative = (TextViewNative) layout.findViewById(R.id.toast_txtn);
          aNative.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

          if (result) {
            aNative.setText(getResources().getString(R.string.quote_sended));
          } else {
            aNative.setText(getResources().getString(R.string.quote_not_sended));
          }
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
        View layout = inflater.inflate(R.layout.toast_layout, null);

        Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        aNative = (TextViewNative) layout.findViewById(R.id.toast_txtn);
        aNative.setText(getResources().getString(R.string.confirm_lock_screen_changing));
        aNative.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

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

  }

  @Override
  public void confirmWallpaperChanging(int resId) {

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
    } else {
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


  /***********************************************************
   * Managing Presenters
   **********************************************************/

  @Override
  public void updatePageModels() {

    if (presenter.getFavoritesPageModels().length != 0) {

      if (dQuotesLoading != null && !dQuotesLoading.isShowing() && !isFromMailOrTypefaceSetings) {
        dQuotesLoading.show();
        dQuotesLoading.setCancelable(false);
      }
    }

    Handler handler = new Handler();

    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        adapter = new FavoritesNativePagerAdapter(FavoritesActivity.this,presenter.getFavoritesPageModels(), presenter);
        adapter.setCallbacks(FavoritesActivity.this);
        viewPager.setAdapter(adapter);
        viewPager.setScrollDurationFactor(1);

        if(presenter.isPageModelsIsSizeOfTwo()) {

          etChooseQuotes.setFilters(new InputFilter[]{new InputFilterMinMax("2")});
          txtnSizeQuotes.setText("2 ");
          middlePage = presenter.getFavoritesPageModels()[1];
          middlePage.setIndex(1, middlePage.getQuote());
        } else {
          etChooseQuotes.setFilters(new InputFilter[]{new InputFilterMinMax(String.valueOf(presenter.getFavoritesPageModels().length))});
          txtnSizeQuotes.setText(" " + presenter.getFavoritesPageModels().length + " ");

          if(presenter.getFavoritesPageModels().length == 0) {
            txtnPositionQuotes.setText(" 0 ");

            if(!presenter.isInit()) {
              txtAuthorBookName.setVisibility(View.GONE);
              txtDetails.setVisibility(View.GONE);

              rlNumber.startAnimation(rlnumber_alpha_in_no_duration);

              FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
              params.topMargin = (int) (contentsTitlePage.getBottom() + getResources().getDimension(R.dimen.action_bar_margin)  /** 4*/);
              rlNumber.setLayoutParams(params);
            }
            txtAuthorBookName.setVisibility(View.INVISIBLE);

            containerfab.startAnimation(alpha_out);
            fabRemoveFromFavorites.startAnimation(fab_close);
            fabTxtIncrease.startAnimation(fab_close);
            fabTxtDecrease.startAnimation(fab_close);
            fabSocialNetworks.startAnimation(fab_close);
            fabTypefaces.startAnimation(fab_close);
            fabMicrophone.startAnimation(fab_close);
            isFabOpen = false;

            arrowPrevious.setVisibility(View.GONE);
            ivAbSearch.setVisibility(View.GONE);
            arrowNext.setVisibility(View.GONE);
            return;
          }

          if (presenter.getFavoritesPageModels().length > 1) {
            viewPager.setPagingEnabled(true);

            if(presenter.isInit() && middlePagePosition != -1) {
              if (middlePagePosition == presenter.getFavoritesPageModels().length) {
                middlePage = presenter.getFavoritesPageModels()[0];
                middlePage.setIndex(middlePagePosition, presenter.getFavoritesPageModels()[0].getQuote());
              } else {
                middlePage = presenter.getFavoritesPageModels()[middlePagePosition];
                middlePage.setIndex(middlePagePosition, presenter.getFavoritesPageModels()[middlePagePosition - 1].getQuote());
              }
              middlePagePosition = -1;
            } else {
              middlePage = presenter.getFavoritesPageModels()[1];
              middlePage.setIndex(1, middlePage.getQuote());
            }
            if(middlePage.getIndex() == presenter.getFavoritesPageModels().length) {
              if (presenter.getFavoritesPageModels()[middlePage.getIndex() -1 ].getQuote().isFavorites()) {
                fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
                fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
              } else {
                fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
                fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
              }
            } else {
              if (presenter.getFavoritesPageModels()[middlePage.getIndex()].getQuote().isFavorites()) {
                fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
                fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
              } else {
                fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
                fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
              }
            }
          } else if(presenter.getFavoritesPageModels().length == 1) {

            ivMicrophonePrevious.setVisibility(View.GONE);
            ivMicrophoneNext.setVisibility(View.GONE);
            ivAbSearch.clearAnimation();
            ivAbSearch.setVisibility(View.GONE);
            arrowNext.clearAnimation();
            arrowNext.setVisibility(View.GONE);
            arrowPrevious.clearAnimation();
            arrowPrevious.setVisibility(View.GONE);
            viewPager.setPagingEnabled(false);
            middlePage = presenter.getFavoritesPageModels()[0];
            middlePage.setIndex(1, middlePage.getQuote());

            if (presenter.getFavoritesPageModels()[0].getQuote().isFavorites()) {
              fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_remove_favorites);
              fabRemoveFromFavorites.setTag(R.drawable.ic_menu_remove_favorites);
            } else {
              fabRemoveFromFavorites.setBackgroundResource(R.drawable.ic_menu_add_favorites);
              fabRemoveFromFavorites.setTag(R.drawable.ic_menu_add_favorites);
            }
          }
        }

        if (isFabOpen) {
          rlNumber.setVisibility(View.VISIBLE);
          rlNumber.startAnimation(rlnumber_alpha_in);
          isRlNumberIsOpen = true;

          if(presenter.getFavoritesPageModels().length > 1) {

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

          containerfab.setVisibility(View.VISIBLE);
          containerfab.startAnimation(alpha_in);
          fabRemoveFromFavorites.setVisibility(View.VISIBLE);
          fabRemoveFromFavorites.startAnimation(fab_open);
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

        txtDetails.setVisibility(View.GONE);
        if(middlePage.getQuote().getAuthor() != null) {
          txtAuthorBookName.setText(String.format("%s%s", middlePage.getAuthorBookNameText(), getResources().getString(R.string.space)));

        } else if (middlePage.getQuote().getBook() != null) {
          txtAuthorBookName.setText(String.format("%s%s", middlePage.getAuthorBookNameText(), getResources().getString(R.string.space)));
        } else {
          txtAuthorBookName.setVisibility(View.GONE);
        }

        if (presenter.getFavoritesPageModels()[0].getQuote().getAuthor() != null) {
          if (!presenter.getFavoritesPageModels()[0].getQuote().getAuthor().getDetails().equals("")) {
            txtDetails.setText(presenter.getFavoritesPageModels()[0].getQuote().getAuthor().getDetails());
          } else {
            if(presenter.getFavoritesPageModels()[0].getQuote().getAuthor().getPictures().size() == 0) {
              txtDetails.setText(getResources().getString(R.string.biography));
            } else {
              txtDetails.setText(getResources().getString(R.string.biography_and_calligraphy));
            }
          }
        }

        if (presenter.getFavoritesPageModels()[0].getQuote().getAuthor() != null) {

          String s = presenter.getFavoritesPageModels()[0].getQuote().getAuthor().getDetails();

          if (!presenter.getFavoritesPageModels()[0].getQuote().getAuthor().getDetails().equals("")) {
            txtDetails.setText(presenter.getFavoritesPageModels()[0].getQuote().getAuthor().getDetails());
          } else {
            if(presenter.getFavoritesPageModels()[0].getQuote().getAuthor().getPictures().size() == 0) {
              txtDetails.setText(getResources().getString(R.string.biography));
            } else {
              txtDetails.setText(getResources().getString(R.string.biography_and_calligraphy));
            }
          }
        } else if (presenter.getFavoritesPageModels()[0].getQuote().getBook() != null) {

          if (!presenter.getFavoritesPageModels()[0].getQuote().getBook().getDetails().equals("")) {
            txtDetails.setText(presenter.getFavoritesPageModels()[0].getQuote().getBook().getDetails());
          } else {
            if(presenter.getFavoritesPageModels()[0].getQuote().getBook().getPictures().size() == 0) {
              txtDetails.setText(getResources().getString(R.string.presentation));
            } else {
              txtDetails.setText(getResources().getString(R.string.presentation_and_calligraphy));
            }
          }
        }

        viewPager.setCurrentItem(PAGE_MIDDLE, false);

        isAuthor = middlePage.isAuthor;

        if (!middlePage.isPresentationOrBibliography) {
          txtAuthorBookName.setOnTouchListener(null);
          if (txtAuthorBookName.getText().length() != 0 && txtAuthorBookName.getVisibility() != View.GONE) {
            txtAuthorBookName.setTextColor(Color.BLACK);
          }
        } else {
          if (txtAuthorBookName.getText().length() != 0 && txtAuthorBookName.getVisibility() != View.GONE) {
            txtAuthorBookName.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
          }
          ImageViewListener txtAuthorBookListener = new ImageViewListener(txtAuthorBookName, 1.05f);
          txtAuthorBookListener.setCallbacks(FavoritesActivity.this);
          txtAuthorBookName.setOnTouchListener(txtAuthorBookListener);
          txtAuthorBookName.bringToFront();
        }

        txtnPositionQuotes.setText(String.format("%d ", middlePage.getIndex()));

        if (presenter.getFavoritesPageModels().length > 1) {
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

                leftPage = presenter.getFavoritesPageModels()[PAGE_LEFT];
                middlePage = presenter.getFavoritesPageModels()[PAGE_MIDDLE];
                rightPage = presenter.getFavoritesPageModels()[PAGE_RIGHT];

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

        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override
          public void onGlobalLayout() {
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(middlePage.cardView.getWidth(), View.MeasureSpec.AT_MOST);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            middlePage.cardView.measure(widthMeasureSpec, heightMeasureSpec);
            txtDetails.measure(widthMeasureSpec, heightMeasureSpec);
            txtnPositionQuotes.measure(widthMeasureSpec, heightMeasureSpec);

            int i = middlePage.cardView.getMeasuredHeight() + ((int) getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) + mObservableHeight;
            if (mViewPagerTop > i) {
              viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
            } else {
              viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, i));
            }

            llHeader.measure(widthMeasureSpec, heightMeasureSpec);
            rlNumber.measure(widthMeasureSpec, heightMeasureSpec);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

              if (txtAuthorBookName.getVisibility() == View.VISIBLE) {
                if (mObservableHeight == 0)
                  mObservableHeight = (int) (llHeader.getBottom() + rlNumber.getBottom() + getResources().getDimension(R.dimen.activity_horizontal_margin));

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                params.topMargin = (int) (llHeader.getBottom() + getResources().getDimension(R.dimen.activity_horizontal_margin)  /** 4*/);
                rlNumber.setLayoutParams(params);
              } else {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
                params.topMargin = (int) (contentsTitlePage.getBottom() + getResources().getDimension(R.dimen.activity_horizontal_margin)  /** 4*/);
                rlNumber.setLayoutParams(params);
              }
            } else {
              if (mObservableHeight == 0)
                mObservableHeight = (int) (llHeader.getBottom() + getResources().getDimension(R.dimen.activity_horizontal_margin));

              FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
              params.topMargin = (int) (txtAuthorBookName.getTop() + getResources().getDimension(R.dimen.action_bar_margin)  /** 4*/);
              rlNumber.setLayoutParams(params);
            }

            PersonalLinearLayout.LayoutParams params1 = (PersonalLinearLayout.LayoutParams) middlePage.cardView.getLayoutParams();
            params1.topMargin = mObservableHeight;
            middlePage.cardView.setLayoutParams(params1);

            if (!isTypefaceDialogShowing && !isMailDialogShowing)
              viewPager.startAnimation(viewpager_alpha_in);
            else if (isTypefaceDialogShowing) showTypeFaceDialog();
            else if (isMailDialogShowing) goToMailActivity();

            if (isMicrophoneDialogShowing) showMicrophoneDialog();

            if (presenter.isPageModelsIsSizeOfTwo()) {
              if (middlePage.getIndex() == 0 || middlePage.getIndex() == 2) {
                txtnPositionQuotes.setText(String.format("%d ", (middlePage.getIndex() + 1) % 2));
              } else {
                txtnPositionQuotes.setText(String.format("%d ", ((middlePage.getIndex() + 1) % 2) + 1));
              }
            } else {
              txtnPositionQuotes.setText(String.format("%d ", middlePage.getIndex()));
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
              viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } else {
              viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }

            if (getIntent().getStringExtra("idFavoriteQuote") != null) {
              if (presenter.getFavoritesPageModels().length != 1) {
                goToQuote(Integer.parseInt(getIntent().getStringExtra("idFavoriteQuote").substring(0, getIntent().getStringExtra("idFavoriteQuote").length() - 1)));
              }
              getIntent().removeExtra("idFavoriteQuote");
            }

            if (idQuote != -1 && presenter.getFavoritesPageModels().length > 1) {
              goToQuote(idQuote);
            }
          }
        });

        if (presenter.isInit() && !isTypefaceDialogShowing) {
          ObjectAnimator objectAnimator = new ObjectAnimator();
          objectAnimator.setDuration(400);
          objectAnimator.ofFloat(viewPager, "alpha", 1).start();
          presenter.init(false);
        } else {
          animate(viewPager).alpha(1).setDuration(400).start();
        }
        if(dQuotesLoading != null && dQuotesLoading.isShowing())  dQuotesLoading.dismiss();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
      }
    }, 2000);
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
