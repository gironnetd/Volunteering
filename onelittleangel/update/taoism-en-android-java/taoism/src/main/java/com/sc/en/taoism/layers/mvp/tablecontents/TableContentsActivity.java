package com.sc.en.taoism.layers.mvp.tablecontents;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.VisibleForTesting;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.sc.en.taoism.OnelittleAngelApplication;
import com.sc.en.taoism.layers.mvp.common.customs.spinkits.SpriteFactory;
import com.sc.en.taoism.layers.mvp.common.customs.typefaces.PersonalTypefaceSpan;
import com.sc.en.taoism.layers.mvp.common.customs.viewpagers.ViewPagerNative;
import com.sc.en.taoism.layers.mvp.common.customs.viewpagers.fragmentpageradapter.FragmentNativePagerAdapter;
import com.sc.en.taoism.layers.mvp.common.layouts.smarttablayouts.SmartTabLayout;
import com.sc.en.taoism.layers.mvp.favorites.FavoritesActivity;
import com.sc.en.taoism.R;
import com.sc.en.taoism.injector.PresenterInjector;
import com.sc.en.taoism.layers.mvp.MotherActivity;
import com.sc.en.taoism.layers.mvp.MotherPresenter;
import com.sc.en.taoism.layers.mvp.common.animations.ActivityAnimator;
import com.sc.en.taoism.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.taoism.layers.mvp.common.customs.scrollviews.ObservableScrollView;
import com.sc.en.taoism.layers.mvp.common.customs.spinkits.SpinKitView;
import com.sc.en.taoism.layers.mvp.common.customs.spinkits.Style;
import com.sc.en.taoism.layers.mvp.common.customs.spinkits.sprite.Sprite;
import com.sc.en.taoism.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.en.taoism.layers.mvp.common.listeners.imageviews.ImageViewListener;
import com.sc.en.taoism.layers.mvp.common.utils.Constants;
import com.sc.en.taoism.layers.mvp.settings.SettingsActivity;
import com.sc.en.taoism.layers.mvp.tablecontents.fragments.homepage.adapters.listadapters.HomePageListAdapter;
import com.sc.en.taoism.layers.mvp.tablecontents.fragments.homepage.fragments.HomePageFragment;
import com.sc.en.taoism.transverse.test.EspressoIdlingResource;

import java.io.File;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class TableContentsActivity extends MotherActivity implements TableContentsViewInterface, ObservableScrollView.Callbacks, ImageViewListener.Callbacks, HomePageFragment.Callbacks {

  @Override
  public void playQuoteMicrophone() {
  }

  @Override
  public void stopQuoteMicrophone() {
  }

  @Override
  public void pauseQuoteMicrophone() {
  }

  @Override
  public void previousQuoteMicrophone() {

  }

  @Override
  public void nextQuoteMicrophone() {
  }

  @Override
  public void sendMails() {
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
  }

  @Override
  public void increaseTextSize() {

  }

  @Override
  public void decreaseTextSize() {

  }

  @Override
  public void toggleFloatingActionButton() {

  }

  private static final String TAG = "TableContentsActivity";
  /***********************************************************
   *  Presenter
   **********************************************************/
  /**
   * The Presenter associated with that view
   */
  public TableContentsPresenterInterface presenter=null;

  /***********************************************************
   * Attributes
   **********************************************************/

  public static final String PREFS_NAME = "SharedPreferences";
  private static final int STATE_ONSCREEN = 0;
  private static final int STATE_OFFSCREEN = 1;
  private static final int STATE_RETURNING = 2;

  private SharedPreferences settings;
  private SharedPreferences.Editor editor;

  private View mPlaceholderView;
  private final ScrollSettleHandler mScrollSettleHandler = new ScrollSettleHandler();
  private int mMinRawY;
  private int mState = STATE_ONSCREEN;
  private int mQuickReturnHeight;
  private int mMaxScrollY;
  public ObservableScrollView mObservableScrollView;
  public ViewPagerNative viewPager;
  private ImageView tools, favorites;
  private long backPressed;
  private NavigationView navigationView;
  private DrawerLayout drawer;
  private View navHeader;

  private TextViewNative txtName;
  private TextViewNative txtWebsite;
  private TextViewNative txtnPlaystoreCommentGiven;
  private Rect rect;
  public static boolean navigationIdOpened = false;
  public static boolean isOrientationChanged = false;

  public static String fromFragment;
  public static boolean isFromOtherActivity = false;
  private SmartTabLayout viewPagerTab;
  public static int headerHeight;
  private LinearLayout headerContainer;
  public LinearLayout rlHeader;
  public FrameLayout rlContainer;
  private static int rlContainerHeight;

  private Dialog commentsPlaystoreDialog;
  public static Dialog homePageDialog;

  private HomePageFragment homePageFragment;

  private MenuItem navContents ;
  private MenuItem navFonts ;
  private MenuItem navComments ;
  private MenuItem navTips ;
  private MenuItem navShare ;
  private MenuItem navQuoteofDay ;

  private SpannableString mNewContentsTitle ;
  private SpannableString mNewFontsTitle ;
  private SpannableString mNewTipsTitle ;
  private SpannableString mNewSharesTitle ;
  private SpannableString mNewQuoteofDaysTitle ;

  private Typeface font;
  boolean isInit = false;
  private LinearLayout llHeader;
  private FrameLayout flHeader;
  public Dialog addRemoveFromDialog;

  /***********************************************************
   * Managing LifeCycle
   **********************************************************/

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  public void onStop() {
    super.onStop();
  }

  @Override
  protected void onDestroy() {


    if(!isOrientationChanged) {
      if (viewPager != null) viewPager.adapter = null;
      viewPager = null;
      viewPagerTab = null;
      mObservableScrollView = null;
      headerHeight = 0;
      headerContainer = null;
      rlContainer = null;
      rlHeader = null;
      rlContainerHeight = 0;
      homePageDialog = null;
      fromFragment = null;

      settings = null;
      editor = null;
      homePageFragment.setCallbacks(null);
      homePageFragment = null;
      CardView mQuickReturnView = null;
      mPlaceholderView = null;

      tools = null;
      favorites = null;
      navigationView = null;
      drawer = null;
      navHeader = null;

      txtName.typeface = null;
      txtName = null;
      txtWebsite.typeface = null;
      txtWebsite = null;
      rect = null;
      settings = null;
      editor = null;

      navContents = null;
      navFonts = null;
      navComments = null;
      navTips = null;
      navShare = null;
      navQuoteofDay = null;

      mNewContentsTitle = null;
      mNewFontsTitle = null;
      SpannableString mNewCommentsTitle = null;
      mNewTipsTitle = null;
      mNewSharesTitle = null;
      mNewQuoteofDaysTitle = null;
      commentsPlaystoreDialog = null;
      font = null;
    }
    super.onDestroy();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    isOrientationChanged = true;

    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    CardViewNative view = new CardViewNative(this);
    view.setCardLayoutResourceID(R.layout.content_layout);
    view.setCardLayoutResourceID(R.layout.card_layout);

    isOrientationChanged = savedInstanceState != null;

    presenter= PresenterInjector.getTableContentsPresenter(this);
    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    editor = settings.edit();

    for(File file : getFilesDir().listFiles()) {
      if(file.getName().contains(".mp3")) file.delete();
    }

    if(settings.getFloat(Constants.TEXTSIZE, 0) == 0) {
      DisplayMetrics displaymetrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

      editor.putFloat(Constants.TEXTSIZE, getResources().getDimension(R.dimen.default_text_size));
//editor.commit();
      editor.apply();    }

    if(settings.getString(Constants.TYPEFACE, "").equals("")){
      editor.putString(Constants.TYPEFACE, "IM_Fell_English");
//editor.commit();
      editor.apply();    }

    headerContainer = (LinearLayout) findViewById(R.id.container);
    rlHeader = (LinearLayout) findViewById(R.id.header);
    rlContainer = (FrameLayout) findViewById(R.id.fl_container);
    mObservableScrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
    mObservableScrollView.setCallbacks(this);
    mObservableScrollView.setVerticalScrollBarEnabled(false);
    mObservableScrollView.setHorizontalScrollBarEnabled(false);
    mObservableScrollView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

    flHeader = (FrameLayout) findViewById(R.id.fl_header);

    mPlaceholderView = findViewById(R.id.place_holder);

    favorites = (ImageView) findViewById(R.id.favorites);
    tools = (ImageView) findViewById(R.id.tools) ;

    TextViewNative tvTitle = (TextViewNative) findViewById(R.id.title_main);
    ImageViewListener tvTitleListener = new ImageViewListener(tvTitle, 1.05f);
    tvTitleListener.setCallbacks(this);
    tvTitle.setOnTouchListener(tvTitleListener);

    DisplayMetrics displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    int width = displaymetrics.widthPixels;

    int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    headerContainer.measure(widthMeasureSpec, heightMeasureSpec);
    rlContainer.measure(widthMeasureSpec, heightMeasureSpec);
    rlContainerHeight = rlContainer.getMeasuredHeight();
    headerContainer.bringToFront();
    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

    navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setItemIconTintList(null);
    Menu menu = navigationView.getMenu();

    navContents = menu.findItem(R.id.nav_movements);
    navFonts = menu.findItem(R.id.nav_fonts);
    navComments = menu.findItem(R.id.nav_comments);
    navTips = menu.findItem(R.id.nav_tips);
    navShare = menu.findItem(R.id.nav_socialnetworks);
    navQuoteofDay = menu.findItem(R.id.nav_notifications);

    font = Typeface.createFromAsset(getAssets(), "fonts/mtcorsva.ttf");

    mNewContentsTitle = new SpannableString(navContents.getTitle());
    mNewContentsTitle.setSpan(new PersonalTypefaceSpan(font), 0 , mNewContentsTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    navContents.setTitle(mNewContentsTitle);

    mNewFontsTitle = new SpannableString(navFonts.getTitle());
    mNewFontsTitle.setSpan(new PersonalTypefaceSpan(font), 0 , mNewFontsTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    navFonts.setTitle(mNewFontsTitle);

    SpannableString mNewCommentsTitle = new SpannableString(navComments.getTitle());
    mNewCommentsTitle.setSpan(new PersonalTypefaceSpan(font), 0 , mNewCommentsTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    navComments.setTitle(mNewCommentsTitle);

    mNewTipsTitle = new SpannableString(navTips.getTitle());
    mNewTipsTitle.setSpan(new PersonalTypefaceSpan(font), 0 , mNewTipsTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    navTips.setTitle(mNewTipsTitle);

    mNewSharesTitle = new SpannableString(navShare.getTitle());
    mNewSharesTitle.setSpan(new PersonalTypefaceSpan(font), 0 , mNewSharesTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    navShare.setTitle(mNewSharesTitle);

    mNewQuoteofDaysTitle = new SpannableString(navQuoteofDay.getTitle());
    mNewQuoteofDaysTitle.setSpan(new PersonalTypefaceSpan(font), 0 , mNewQuoteofDaysTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    navQuoteofDay.setTitle(mNewQuoteofDaysTitle);

    setUpNavigationView();

    // Navigation view header
    navHeader = navigationView.getHeaderView(0);
    txtName = (TextViewNative) navHeader.findViewById(R.id.name);
    txtWebsite = (TextViewNative) navHeader.findViewById(R.id.website);
    llHeader = (LinearLayout) findViewById(R.id.ll_header);
    mObservableScrollView.bringChildToFront(llHeader);
    llHeader.bringToFront();

    txtName.setText(String.format("%sDamien Gironnet", getResources().getString(R.string.develop_by)));
    txtWebsite.setText(String.format("%swww.onelittleangel.com", getResources().getString(R.string.develop_for)));

    commentsPlaystoreDialog = new Dialog(this, R.style.myDialogSlideUpAndDown);
    commentsPlaystoreDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    commentsPlaystoreDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    commentsPlaystoreDialog.setContentView(R.layout.comments_dialog);

    txtnPlaystoreCommentGiven = (TextViewNative) commentsPlaystoreDialog.findViewById(R.id.txt_add_to_favorites_ask);

    TextView confirmPlaystoreComments = (TextView) commentsPlaystoreDialog.findViewById(R.id.confirm_go_to_comments_playstore);
    ImageViewListener confirmPlaystoreCommentsListener = new ImageViewListener(confirmPlaystoreComments, 1.3f);
    confirmPlaystoreCommentsListener.setCallbacks(this);
    confirmPlaystoreComments.setOnTouchListener(confirmPlaystoreCommentsListener);

    TextView cancelPlaystoreComments = (TextView) commentsPlaystoreDialog.findViewById(R.id.cancel_go_to_comments_playstore);
    ImageViewListener cancelPlaystoreCommentsListener = new ImageViewListener(cancelPlaystoreComments, 1.3f);
    cancelPlaystoreCommentsListener.setCallbacks(this);
    cancelPlaystoreComments.setOnTouchListener(cancelPlaystoreCommentsListener);

    confirmPlaystoreComments.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    cancelPlaystoreComments.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    tools.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
          case MotionEvent.ACTION_DOWN:
            rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());

            animate(tools).setDuration(100).scaleX(1.3f).scaleY(1.3f);
            return true;
          case MotionEvent.ACTION_MOVE:
            if(!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
              animate(tools)
                      .setDuration(100)
                      .scaleX(1)
                      .scaleY(1)
                      .setListener(new AnimatorListenerAdapter() {
                        /**
                         * {@inheritDoc}
                         *
                         * @param animation
                         */
                        @Override
                        public void onAnimationEnd(Animator animation) {
                          super.onAnimationEnd(animation);
                          drawer.openDrawer(Gravity.LEFT);
                        }
                      });
              return false;
            }
            return true;
          case MotionEvent.ACTION_UP:
            animate(tools)
                    .setDuration(100)
                    .scaleX(1)
                    .scaleY(1)
                    .setListener(new AnimatorListenerAdapter() {
                      /**
                       * {@inheritDoc}
                       *
                       * @param animation
                       */
                      @Override
                      public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        drawer.openDrawer(Gravity.LEFT);
                      }
                    });
            return false;
          default:
            animate(tools)
                    .setDuration(100)
                    .scaleX(1)
                    .scaleY(1)
                    .setListener(new AnimatorListenerAdapter() {
                      /**
                       * {@inheritDoc}
                       *
                       * @param animation
                       */
                      @Override
                      public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        drawer.openDrawer(Gravity.LEFT);
                      }
                    });
            return false;
        }
      }
    });

    favorites.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
          case MotionEvent.ACTION_DOWN:
            animate(favorites).setDuration(100).scaleX(1.3f).scaleY(1.3f);
            return true;
          case MotionEvent.ACTION_MOVE:
            return true;
          case MotionEvent.ACTION_UP:
            animate(favorites)
                    .setDuration(100)
                    .scaleX(1)
                    .scaleY(1)
                    .setListener(new AnimatorListenerAdapter() {
                      /**
                       * {@inheritDoc}
                       *
                       * @param animation
                       */
                      @Override
                      public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        Intent iFavorites = new Intent(TableContentsActivity.this, FavoritesActivity.class);
                        iFavorites.putExtra("fromActivity", "TableContentsActivity");

                        if(TableContentsActivity.fromFragment != null) {
                          iFavorites.putExtra(getResources().getString(R.string.from), TableContentsActivity.fromFragment);
                          iFavorites.putExtra(getResources().getString(R.string.fromFragment), TableContentsActivity.fromFragment);
                        } else {
                          iFavorites.putExtra(getResources().getString(R.string.from), getResources().getString(R.string.homepage));
                          iFavorites.putExtra(getResources().getString(R.string.fromFragment), getResources().getString(R.string.homepage));
                        }
                        startActivity(iFavorites);
                        try {
                          ActivityAnimator anim = new ActivityAnimator();
                          anim.fadeAnimation(TableContentsActivity.this);
                        } catch (Exception ignored) {
                        }
                        TableContentsActivity.this.finish();
                      }
                    });
            return false;
          default:
            animate(favorites)
                    .setDuration(100)
                    .scaleX(1)
                    .scaleY(1)
                    .setListener(new AnimatorListenerAdapter() {
                      /**
                       * {@inheritDoc}
                       *
                       * @param animation
                       */
                      @Override
                      public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        Intent iFavorites = new Intent(TableContentsActivity.this, FavoritesActivity.class);
                        iFavorites.putExtra("fromActivity", "TableContentsActivity");

                        if(TableContentsActivity.fromFragment != null) {
                          iFavorites.putExtra(getResources().getString(R.string.from), TableContentsActivity.fromFragment);
                          iFavorites.putExtra(getResources().getString(R.string.fromFragment), TableContentsActivity.fromFragment);
                        } else {
                          iFavorites.putExtra(getResources().getString(R.string.from), getResources().getString(R.string.homepage));
                          iFavorites.putExtra(getResources().getString(R.string.fromFragment), getResources().getString(R.string.homepage));
                        }
                        startActivity(iFavorites);
                        try {
                          ActivityAnimator anim = new ActivityAnimator();
                          anim.fadeAnimation(TableContentsActivity.this);
                        } catch (Exception ignored) {
                        }
                        TableContentsActivity.this.finish();
                      }
                    });
            return false;
        }
      }
    });

    homePageDialog = new Dialog(TableContentsActivity.this, R.style.myDialogSlideUpAndDown);
    homePageDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    homePageDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    homePageDialog.setContentView(R.layout.biography_dialog);
    CardView cvBiographyDialog = (CardView) homePageDialog.findViewById(R.id.cv_biography_dialog);
    TextViewNative aNative = (TextViewNative) homePageDialog.findViewById(R.id.txtn_waiting);
    aNative.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      SpinKitView spinKitView = (SpinKitView) homePageDialog.findViewById(R.id.spin_kit);
      Style style = Style.FADING_CIRCLE;
      Sprite drawable = SpriteFactory.create(style);
      spinKitView.setIndeterminateDrawable(drawable);
      spinKitView.setVisibility(View.VISIBLE);
      cvBiographyDialog.setVisibility(View.GONE);
    }

    FragmentNativePagerAdapter adapter =
            new FragmentNativePagerAdapter(getApplicationContext(), getSupportFragmentManager());
    viewPager = (ViewPagerNative) findViewById(R.id.viewpager);
    viewPager.setAdapter(adapter);

    viewPager.adapter = adapter;
    viewPager.setScrollDurationFactor(1);
    viewPager.setOffscreenPageLimit(5);
    //  viewPager.setPagingEnabled(false);

    ViewGroup tab = (ViewGroup) findViewById(R.id.tab);
    tab.addView(LayoutInflater.from(this).inflate(R.layout.smarttab_tablecontents_layout, tab, false));

    viewPagerTab = (SmartTabLayout) findViewById(R.id.view_pager_tab);
    viewPagerTab.setViewPager(viewPager);
    viewPagerTab.setActivity(this);

    mObservableScrollView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
      if(mObservableScrollView != null) {
        onScrollChanged(mObservableScrollView.getScrollY());
        mMaxScrollY = mObservableScrollView.computeVerticalScrollRange()
                - mObservableScrollView.getHeight();
      }
      mQuickReturnHeight = llHeader.getHeight();
      if(headerHeight == 0 && viewPagerTab != null) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) llHeader.getLayoutParams();
        headerHeight = llHeader.getMeasuredHeight()
                + viewPagerTab.getMeasuredHeight() + params.topMargin;
      }
    });

    homePageFragment = (HomePageFragment) viewPager.adapter.getItem(0);
    homePageFragment.setCallbacks(this);
    homePageFragment.setActivity(this);

    navigationIdOpened = getIntent().getBooleanExtra("navigation_opened", false);

    if(navigationIdOpened && !isOrientationChanged){
      drawer.openDrawer(Gravity.LEFT);
    }

    fromFragment = getIntent().getStringExtra(getResources().getString(R.string.fromFragment));

    if(fromFragment != null) {
      isFromOtherActivity = true;

      switch(TableContentsActivity.fromFragment){
        case "homepage":
//          if(!isFinishing()) homePageDialog.show();
//          homePageDialog.setCancelable(false);

          viewPager.setCurrentItem(0);
          viewPager.setPagingEnabled(false);
          break;
        case "authors":
          viewPager.setCurrentItem(1);
          viewPager.setPagingEnabled(true);
          break;
        case "movements":
          viewPager.setCurrentItem(2);
          viewPager.setPagingEnabled(true);
          break;
        case "themes":
          viewPager.setCurrentItem(3);
          viewPager.setPagingEnabled(true);
          break;
        case "books":
          viewPager.setCurrentItem(4);
          viewPager.setPagingEnabled(true);
          break;
      }
    } else if(!isOrientationChanged && !isFromOtherActivity) {
      int i = settings.getInt(Constants.OPENED_NUMBER, 0);
      editor.putInt(Constants.OPENED_NUMBER, ++i);
      editor.commit();

      if(i % 50 == 0 && !settings.getBoolean(Constants.PLAYSTORE_COMMENT_GIVEN, false)) {
        if(!isFinishing()) commentsPlaystoreDialog.show();
      }
    }

    mObservableScrollView.smoothScrollTo(0,0);

    addRemoveFromDialog = new Dialog(TableContentsActivity.this, R.style.myDialogSlideUpAndDown);
    addRemoveFromDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    addRemoveFromDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    addRemoveFromDialog.setContentView(R.layout.add_to_favorites_dialog);

    TextViewNative txt = (TextViewNative) addRemoveFromDialog.findViewById(R.id.txt_add_to_favorites_ask);
    txt.setText(getResources().getString(R.string.adding_to_favorites_ask));

    TextView confirmAddToFavorites = (TextView) addRemoveFromDialog.findViewById(R.id.confirm_add_to_favorites);
    ImageViewListener confirmAddToFavoritesListener = new ImageViewListener(confirmAddToFavorites, 1.3f);
    confirmAddToFavoritesListener.setCallbacks(this);
    confirmAddToFavorites.setOnTouchListener(confirmAddToFavoritesListener);

    TextView cancelAddToFavorites = (TextView) addRemoveFromDialog.findViewById(R.id.cancel_add_to_favorites);
    ImageViewListener cancelAddToFavoritesListener = new ImageViewListener(cancelAddToFavorites, 1.3f);
    cancelAddToFavoritesListener.setCallbacks(this);
    cancelAddToFavorites.setOnTouchListener(cancelAddToFavoritesListener);

    confirmAddToFavorites.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    cancelAddToFavorites.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

  }

  private void setUpNavigationView() {
    NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
    if (navigationMenuView != null) {
      navigationMenuView.setVerticalScrollBarEnabled(false);
      navigationMenuView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }
    navigationView.setNavigationItemSelectedListener(item -> {
      switch(item.getItemId()){
        case R.id.nav_movements:
          Intent iMovements = new Intent(new Intent(TableContentsActivity.this, SettingsActivity.class));
          iMovements.putExtra(getResources().getString(R.string.from), getResources().getString(R.string.movements_nav_title));
          iMovements.putExtra("fromFragment", fromFragment);
          startActivity(iMovements);
          try {
            ActivityAnimator anim = new ActivityAnimator();
            anim.fadeAnimation(TableContentsActivity.this);
          } catch (Exception ignored) {
          }
          finish();
          break;
        case R.id.nav_fonts:
          Intent iFonts = new Intent(new Intent(TableContentsActivity.this, SettingsActivity.class));
          iFonts.putExtra(getResources().getString(R.string.from), getResources().getString(R.string.typefaces_nav_title));
          iFonts.putExtra("fromFragment", fromFragment);

          startActivity(iFonts);
          try {
            ActivityAnimator anim = new ActivityAnimator();
            anim.fadeAnimation(TableContentsActivity.this);
          } catch (Exception ignored) {
          }
          finish();
          break;
        case R.id.nav_comments:
          if(settings.getBoolean(Constants.PLAYSTORE_COMMENT_GIVEN, false)) {

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout, null);
            Toast toast = Toast.makeText(TableContentsActivity.this, "", Toast.LENGTH_SHORT);
            TextViewNative aNative = (TextViewNative) layout.findViewById(R.id.toast_txtn);
            aNative.setText(getResources().getString(R.string.thanks_to_give_comment));
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            if(!isFinishing()) toast.show();
            txtnPlaystoreCommentGiven.setText(getResources().getString(R.string.thanks_to_give_comment));
          } else {
            if(!isFinishing()) commentsPlaystoreDialog.show();
          }
          break;
        case R.id.nav_tips:
          Intent iTips = new Intent(new Intent(TableContentsActivity.this, SettingsActivity.class));
          iTips.putExtra(getResources().getString(R.string.from), getResources().getString(R.string.tips_nav_title));
          iTips.putExtra("fromFragment", fromFragment);
          startActivity(iTips);
          try {
            ActivityAnimator anim = new ActivityAnimator();
            anim.fadeAnimation(TableContentsActivity.this);
          } catch (Exception ignored) {
          }
          finish();
          break;
        case R.id.nav_socialnetworks:
          Intent iSocialNetworks = new Intent(new Intent(TableContentsActivity.this, SettingsActivity.class));
          iSocialNetworks.putExtra(getResources().getString(R.string.from), getResources().getString(R.string.mail));
          iSocialNetworks.putExtra("fromFragment", fromFragment);
          startActivity(iSocialNetworks);
          try {
            ActivityAnimator anim = new ActivityAnimator();
            anim.fadeAnimation(TableContentsActivity.this);
          } catch (Exception ignored) {
          }
          finish();
          break;
        case R.id.nav_notifications:
          Intent iNotifications = new Intent(new Intent(TableContentsActivity.this, SettingsActivity.class));
          iNotifications.putExtra(getResources().getString(R.string.from), getResources().getString(R.string.notifications_nav_title));
          iNotifications.putExtra("fromFragment", fromFragment);
          startActivity(iNotifications);
          try {
            ActivityAnimator anim = new ActivityAnimator();
            anim.fadeAnimation(TableContentsActivity.this);
          } catch (Exception ignored) {
          }
          finish();
          break;
      }
      return false;
    });
  }

  @Override
  public void onScroll(float deltaY) {

  }

  @Override
  public void onScrollChanged(int scrollYY) {

    ViewHelper.setTranslationY(flHeader, scrollYY / 2);

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
        animate(llHeader).cancel();
        ViewHelper.setTranslationY(llHeader, (translationY * 3) /*+ scrollY*/);

        break;

      case STATE_ONSCREEN:
        if (rawY < -mQuickReturnHeight) {
          mState = STATE_OFFSCREEN;
          mMinRawY = rawY;
        }
        translationY = rawY;
        animate(llHeader).cancel();
        ViewHelper.setTranslationY(llHeader, (translationY * 2) + scrollY);
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
        animate(llHeader).cancel();
        ViewHelper.setTranslationY(llHeader, (translationY * 2) /*+ scrollY*/);

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
    if(mObservableScrollView != null) {
      mScrollSettleHandler.onScroll(mObservableScrollView.getScrollY());
    }
  }

  @Override
  public void homeBackPressed() {
    onBackPressed();
  }

  @Override
  public void backToHomePage() {

  }

  @Override
  public void showAddRemoveFavoritesDialog() {
  }

  @Override
  public void confirmAddRemoveDialog() {
    //HomePageFragment homePageFragment = (HomePageFragment) viewPager.adapter.getFragments().get(0);
    presenter.toggleQuoteOfDayIsFavorites(settings.getInt(Constants.QUOTE_OF_DAY_ID, -1));
    addRemoveFromDialog.cancel();
  }

  @Override
  public void cancelAddRemoveDialog() {
    addRemoveFromDialog.cancel();
  }

  @Override
  public void showSocialNetworksDialog() {
  }

  @Override
  public void showTypeFaceDialog() {
  }

  @Override
  public void showMicrophoneDialog() {
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

    Intent intent;
    //Intent launchIntent = new Intent();

    final String appPackageName = getPackageName();
    try {
      intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
    } catch (android.content.ActivityNotFoundException anfe) {
      intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
    }

    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);

    Uri uri = Uri.parse("market://details?id=" +  getApplicationContext().getPackageName());
    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
    // To count with Play market backstack, After pressing back button,
    // to taken back to our application, we need to add following flags to intent.
    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
            //Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
    try {
      startActivity(goToMarket);
    } catch (ActivityNotFoundException e) {
      startActivity(new Intent(Intent.ACTION_VIEW,
              Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
    }

    editor.putBoolean(Constants.PLAYSTORE_COMMENT_GIVEN, true);
    editor.commit();
    commentsPlaystoreDialog.cancel();
  }

  @Override
  public void cancelGoToCommentsPlaystore() {
    commentsPlaystoreDialog.cancel();
  }

  @Override
  public void confirmTypefaceChoice() {

  }

  @Override
  public void cancelTypefaceChoice() {

  }

  @Override
  public void gotToTypefaceSettings() {

  }

  @Override
  public void goToTwitterActivity() {

  }

  @Override
  public void goToMailActivity() {

  }

  @Override
  public void goToFacebookActivity() {

  }

  @Override
  public void goToGooglePlusActivity() {

  }

  @Override
  public void cancelGoToSettingsAccount() {

  }

  @Override
  public void goToAccountSettings(String settings) {

  }

  @Override
  public void onScaling(float size, int resId) {

    viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        viewPager.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, rlContainer.getMeasuredHeight() /*+ headerHeight*/   + (int) OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)));
        HomePageListAdapter.isInit = false;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
          viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
          viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
      }
    });
  }

  private class ScrollSettleHandler extends Handler {
    private static final int SETTLE_DELAY_MILLIS = 100;

    private int mSettledScrollY = Integer.MIN_VALUE;
    private boolean mSettleEnabled;

    public void onScroll(int scrollY) {
      if (mSettledScrollY != scrollY) {
        //removeMessages(0);
        //sendEmptyMessageDelayed(0, SETTLE_DELAY_MILLIS);
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
        if (mSettledScrollY  - ViewHelper.getTranslationY(llHeader) > mQuickReturnHeight / 2) {
          mState = STATE_OFFSCREEN;
          mDestTranslationY = Math.max(
                  mSettledScrollY - mQuickReturnHeight,
                  mPlaceholderView.getTop());
        } else {
          mDestTranslationY = mSettledScrollY;
        }

        mMinRawY = mPlaceholderView.getTop() - mQuickReturnHeight - mDestTranslationY;
        animate(llHeader).translationY(mDestTranslationY);
      }
      mSettledScrollY = Integer.MIN_VALUE;
    }
  }

  @Override
  public void onBackPressed() {

    if(drawer.isDrawerOpen(Gravity.LEFT)){
      drawer.closeDrawer(Gravity.LEFT);
    }
    else if ( viewPager != null && viewPager.getCurrentItem() != 0) {
      viewPager.setCurrentItem(0);
    }
    else {
      if (backPressed + 2000 > System.currentTimeMillis()) {
        super.onBackPressed();
        editor.putBoolean(Constants.IS_REOPENED, true);
        editor.commit();
      } else {
        backPressed = System.currentTimeMillis();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, null);
        TextViewNative aNative = (TextViewNative) layout.findViewById(R.id.toast_txtn);
        aNative.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
        Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        if(!isFinishing()) toast.show();
      }
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
