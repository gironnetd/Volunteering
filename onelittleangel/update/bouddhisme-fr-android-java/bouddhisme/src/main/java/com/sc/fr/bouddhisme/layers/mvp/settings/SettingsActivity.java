package com.sc.fr.bouddhisme.layers.mvp.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.sc.fr.bouddhisme.OnelittleAngelApplication;
import com.sc.fr.bouddhisme.layers.mvp.MotherActivity;
import com.sc.fr.bouddhisme.layers.mvp.MotherPresenter;
import com.sc.fr.bouddhisme.layers.mvp.common.animations.ActivityAnimator;
import com.sc.fr.bouddhisme.layers.mvp.common.customs.scrollviews.ObservableScrollView;
import com.sc.fr.bouddhisme.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.fr.bouddhisme.layers.mvp.common.listeners.imageviews.ImageViewListener;
import com.sc.fr.bouddhisme.layers.mvp.contents.ContentsActivity;
import com.sc.fr.bouddhisme.layers.mvp.favorites.FavoritesActivity;
import com.sc.fr.bouddhisme.layers.mvp.settings.fragments.SettingsFragmentAdapter;
import com.sc.fr.bouddhisme.layers.mvp.settings.viewpager.ViewPagerNative;
import com.sc.fr.bouddhisme.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.fr.bouddhisme.transverse.test.EspressoIdlingResource;
import com.sc.fr.bouddhisme.R;
import com.sc.fr.bouddhisme.injector.PresenterInjector;
import com.sc.fr.bouddhisme.layers.mvp.biography.BiographyActivity;
import com.sc.fr.bouddhisme.layers.mvp.settings.smarttablayout.SmartTabLayout;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class SettingsActivity extends MotherActivity implements SettingsViewInterface, ObservableScrollView.Callbacks, SmartTabLayout.Callbacks,ImageViewListener.Callbacks {

  private static final String TAG = "SettingsActivity";

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
  private TextViewNative quickReturnTitle;
  private ViewPagerNative viewPager;
  public static String from;
  private SettingsFragmentAdapter adapter;
  public static int headerHeight;
  public static int rlNumberHeight;
  public static boolean isOrientationChanged = false;
  private boolean isTpdOpened = false;
  public boolean isFromMailOrTypefaceSetings = false;

  private int height ;
  public LinearLayout rlNumber, llHeader;
  private int mObservableHeight;
  private FrameLayout flHeader;


  /***********************************************************
   *  Presenter
   **********************************************************/
  /**
   * The Presenter associated with that view
   */
  private SettingsPresenterInterface presenter=null;

  @Override
  protected void onDestroy() {
    super.onDestroy();
    //if(!isFinishing()) finish();

//    presenter = null;
//    if(!isOrientationChanged) {
//      mObservableScrollView = null;
//    }
//
//    adapter = null;
//    viewPagerTab = null;
//    imgHeaderBackground = imgAbTitle = null;
//    quickReturnTitle = null;
//    mPlaceholderView = null;
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    outState.putString("title", quickReturnTitle.getText().toString());
    outState.putBoolean("isTpdOpened", isTpdOpened);
    outState.putBoolean("isFromMailOrTypefaceSetings", isFromMailOrTypefaceSetings);
    isOrientationChanged = true;

    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    if(savedInstanceState != null) {

      isOrientationChanged = true;
      isTpdOpened = savedInstanceState.getBoolean("isTpdOpened");
      isFromMailOrTypefaceSetings = savedInstanceState.getBoolean("isFromMailOrTypefaceSetings");
    } else {
      isOrientationChanged = false;
    }

    //if (getIntent())

    presenter = PresenterInjector.getSettingsPresenter(this);

    DisplayMetrics displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    height = displaymetrics.heightPixels;
    int width = displaymetrics.widthPixels;

    int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

    SharedPreferences settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);


    LinearLayout headerContainer = (LinearLayout) findViewById(R.id.container);

    if(mObservableScrollView == null) {
      mObservableScrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
      mObservableScrollView.setCallbacks(this);
      mObservableScrollView.setVerticalScrollBarEnabled(false);
      mObservableScrollView.setHorizontalScrollBarEnabled(false);
    }
    rlNumber = (LinearLayout) findViewById(R.id.rl_number);
    mPlaceholderView = findViewById(R.id.place_holder);
    llHeader = (LinearLayout) findViewById(R.id.ll_header);
    flHeader = (FrameLayout) findViewById(R.id.fl_header);
    ImageView imgAbTitle = (ImageView) findViewById(R.id.ic_ab_title);

    ImageViewListener imgAbTitleListener = new ImageViewListener(imgAbTitle, 1.3f);
    imgAbTitleListener.setCallbacks(this);
    imgAbTitle.setOnTouchListener(imgAbTitleListener);

    quickReturnTitle = (TextViewNative) findViewById(R.id.title_navigation_activities);

    mObservableScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {

        if(mObservableScrollView == null) {
          mObservableScrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
          mObservableScrollView.setCallbacks(SettingsActivity.this);
          mObservableScrollView.setVerticalScrollBarEnabled(false);
          mObservableScrollView.setHorizontalScrollBarEnabled(false);
        }
        onScrollChanged(mObservableScrollView.getScrollY());
        mMaxScrollY = mObservableScrollView.computeVerticalScrollRange()
                - mObservableScrollView.getHeight();

        mObservableHeight = rlNumber.getBottom();

        mQuickReturnHeight = flHeader.getHeight();

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlNumber.getLayoutParams();
        params.topMargin = llHeader.getBottom() ;
        rlNumber.setLayoutParams(params);

        //rlNumber.measure(widthMeasureSpec, heightMeasureSpec);
        headerHeight = (int) (params.topMargin + params.height + getResources().getDimension(R.dimen.activity_horizontal_margin));
        rlNumberHeight = (int) (params.topMargin + params.height + getResources().getDimension(R.dimen.activity_horizontal_margin));
        //params.topMargin + params.height / 2;
        //(int) (llHeader.getMeasuredHeight() + getResources().getDimension(R.dimen.activity_horizontal_double_margin));

        if(viewPager == null) {
          adapter = new SettingsFragmentAdapter(getApplicationContext(), getSupportFragmentManager());
          viewPager = (ViewPagerNative) findViewById(R.id.viewpager);
          viewPager.setAdapter(adapter);
          viewPager.adapter = adapter;
          viewPager.setScrollDurationFactor();
          viewPager.setOffscreenPageLimit(5);
          viewPager.bringToFront();
        }

        //  LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) viewPager.getLayoutParams();

        //  params1.topMargin = (int) (rlNumber.getBottom() + getResources().getDimension(R.dimen.activity_horizontal_margin));

//        if((viewPager.getCurrentItem() == 0 && getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) ||viewPager.getCurrentItem() == 3 || viewPager.getCurrentItem() == 4) {
//          params1.height = (int) (height - SettingsActivity.headerHeight - getResources().getDimension(R.dimen.activity_horizontal_margin));
//        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
          mObservableScrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
          mObservableScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
      }
    });

    if(viewPager == null) {

      adapter = new SettingsFragmentAdapter(getApplicationContext(), getSupportFragmentManager());
      viewPager = (ViewPagerNative) findViewById(R.id.viewpager);
      viewPager.setAdapter(adapter);
      viewPager.adapter = adapter;
      viewPager.setScrollDurationFactor();
      viewPager.setOffscreenPageLimit(5);
      viewPager.bringToFront();
    }

    ViewGroup tab = (ViewGroup) findViewById(R.id.tab);
    tab.addView(LayoutInflater.from(this).inflate(R.layout.smarttab_settings_layout, tab, false));

    SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.view_pager_tab);
    viewPagerTab.setCallbacks(this);

    final LayoutInflater inflater = LayoutInflater.from(viewPagerTab.getContext());
    final Resources res = viewPagerTab.getContext().getResources();

    viewPagerTab.setCustomTabView((container, position, adapter1) -> {
      ImageView icon = (ImageView) inflater.inflate(R.layout.settings_icons_layout, container, false);
      switch (position) {
        case 0:
          icon.setImageDrawable(res.getDrawable(R.drawable.ic_st_movements));
          return icon;
        case 1:
          icon.setImageDrawable(res.getDrawable(R.drawable.ic_st_fonts));
          return icon;
        case 2:
          icon.setImageDrawable(res.getDrawable(R.drawable.ic_st_tips));
          return icon;
        case 3:
          icon.setImageDrawable(res.getDrawable(R.drawable.ic_st_mail));
          return icon;
        case 4:
          icon.setImageDrawable(res.getDrawable(R.drawable.ic_st_notifications));
          return icon;
        default:
          throw new IllegalStateException("Invalid position: " + position);
      }
    });

    if (getIntent().getBooleanExtra("isFromMailSettings", false) || getIntent().getBooleanExtra("isFromTypefaceSettings", false) || isFromMailOrTypefaceSetings) {
      isFromMailOrTypefaceSetings = true;
      //rlNumber.setVisibility(View.GONE);
      viewPagerTab.setVisibility(View.GONE);
      viewPager.setPagingEnabled(false);
      //headerHeight -= rlNumberHeight
    }

    viewPagerTab.setViewPager(viewPager);

    String from = getIntent().getStringExtra(getResources().getString(R.string.from));

    if(savedInstanceState != null) {
      from = savedInstanceState.getString("title");
    }

    if(from.contains(getResources().getString(R.string.movements_nav_title))) {
      ImageView iv = (ImageView) viewPagerTab.getTabAt(0);
      animate(iv).scaleX(1.25f).scaleY(1.25f).setDuration(300).start();
      quickReturnTitle.setText(getResources().getString(R.string.movements_nav_title));
    } else if(from.contains(getResources().getString(R.string.typefaces_nav_title))) {
      ImageView iv = (ImageView) viewPagerTab.getTabAt(1);
      animate(iv).scaleX(1.25f).scaleY(1.25f).setDuration(300).start();
      viewPager.setCurrentItem(1);
      quickReturnTitle.setText(getResources().getString(R.string.typefaces_nav_title));
    } else if(from.contains(getResources().getString(R.string.tips_nav_title))) {
      ImageView iv = (ImageView) viewPagerTab.getTabAt(2);
      animate(iv).scaleX(1.25f).scaleY(1.25f).setDuration(300).start();
      viewPager.setCurrentItem(2);
      quickReturnTitle.setText(getResources().getString(R.string.tips_nav_title));
    }else if(from.contains(getResources().getString(R.string.mail))) {
      ImageView iv = (ImageView) viewPagerTab.getTabAt(3);
      animate(iv).scaleX(1.25f).scaleY(1.25f).setDuration(300).start();
      viewPager.setCurrentItem(3);
      quickReturnTitle.setText(getResources().getString(R.string.mail));
    }
    else if(from.contains(getResources().getString(R.string.notifications_nav_title))) {
      ImageView iv = (ImageView) viewPagerTab.getTabAt(4);
      animate(iv).scaleX(1.25f).scaleY(1.25f).setDuration(300).start();
      viewPager.setCurrentItem(4);
      quickReturnTitle.setText(getResources().getString(R.string.notifications_nav_title));
    }
    mObservableScrollView.smoothScrollTo(0,0);
  }

  @Override
  protected void onStart() {
    super.onStart();
  }

  @Override
  protected void onStop() {
    super.onStop();
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
  public void changeTitle() {
    ViewHelper.setAlpha(quickReturnTitle, 0);
    quickReturnTitle.setText(adapter.getPageTitle(viewPager.getCurrentItem()));
    animate(quickReturnTitle).alpha(1).setDuration(600).start();
  }

  @Override
  public void avoidVisibility(int position) {
    animate(quickReturnTitle).alpha(0).setDuration(300).start();
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
  }

  @Override
  public void cancelAddRemoveDialog() {
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

  private class ScrollSettleHandler extends Handler {
    private static final int SETTLE_DELAY_MILLIS = 100;

    private int mSettledScrollY = Integer.MIN_VALUE;
    private boolean mSettleEnabled;

    public void onScroll(int scrollY) {

    }

    public void setSettleEnabled(boolean settleEnabled) {
      mSettleEnabled = settleEnabled;
    }

    @Override
    public void handleMessage(Message msg) {
    }
  }

  @Override
  public void onBackPressed() {
    Intent mainIntent = null;
    if(getIntent().getStringExtra("fromActivity") != null) {

      if (getIntent().getStringExtra("fromActivity").equals("ContentsActivity")) {
        mainIntent = new Intent(this, ContentsActivity.class);
      }

      if (getIntent().getStringExtra("fromActivity").contains("FavoritesActivity")) {
        String[] strings = getIntent().getStringExtra("fromActivity").split(";");
        if(strings[0].equals("FavoritesActivity")) {
          mainIntent = new Intent(this, FavoritesActivity.class);
          if(strings.length > 1) {
            mainIntent.putExtra("fromActivity", strings[1]);
          }
        } else {
          mainIntent = new Intent(this, BiographyActivity.class);
          mainIntent.putExtra("fromActivity", "FavoritesActivity;ContentsActivity");
        }
      }

      if (getIntent().getStringExtra("fromActivity").contains("BiographyActivity")) {
        mainIntent = new Intent(this, BiographyActivity.class);
        mainIntent.putExtra("currentItem", getIntent().getIntExtra("currentItem", 0));
      }

      String name;
      if (getIntent().getStringExtra("type").equals(getResources().getString(R.string.themes)) || getIntent().getStringExtra("type").equals(getResources().getString(R.string.movements))) {
        if(getIntent().getStringExtra("value").contains(";")) {
          String[] strings = getIntent().getStringExtra("value").split(";");
          mainIntent.putExtra("value", strings[1]);
          name = strings[0];
        } else {

          name = getIntent().getStringExtra("value");
        }
      } else {

        if(getIntent().getStringExtra(getString(R.string.returnTo)).contains(";")) {
          String[] strings = getIntent().getStringExtra(getString(R.string.returnTo)).split(";");
          mainIntent.putExtra("value", strings[1]);
          name = strings[0];
        } else {
          name = getIntent().getStringExtra(getString(R.string.returnTo));
        }
      }

      String type = getIntent().getStringExtra("type");
      mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), type);
      mainIntent.putExtra("type", getIntent().getStringExtra(getResources().getString(R.string.from)));

      mainIntent.putExtra(type, name);
      if (getIntent().getStringExtra(getString(R.string.from)).contains(getResources().getString(R.string.typefaces_nav_title))) {
        mainIntent.putExtra("fromSettings", "typeface");
      }

      if (getIntent().getStringExtra(getString(R.string.from)).contains(getResources().getString(R.string.mail))) {
        mainIntent.putExtra("fromSettings", "mail");
      }
      mainIntent.putExtra("idFavoriteQuote", getIntent().getStringExtra("idFavoriteQuote"));
      mainIntent.putExtra("idContentQuote", getIntent().getStringExtra("idContentQuote"));
      mainIntent.putExtra("fromContent", getIntent().getStringExtra("fromContent"));

      mainIntent.putExtra("isAuthor", getIntent().getBooleanExtra("isAuthor", false));
      if(mainIntent.getStringExtra("fromActivity") == null) {
        mainIntent.putExtra("fromActivity", getIntent().getStringExtra("fromActivity"));
      }
      mainIntent.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));
      mainIntent.putExtra("isFromMailOrTypefaceSetings", isFromMailOrTypefaceSetings);
      // ActivityCompat.finishAffinity(this);
      startActivity(new Intent(mainIntent));
      try {
        ActivityAnimator anim = new ActivityAnimator();
        anim.fadeAnimationToTableContents(SettingsActivity.this);
      } catch (Exception ignored) {
      }
      this.finish();
    } else {
      Intent intent = new Intent(this, TableContentsActivity.class);
      intent.putExtra("navigation_opened", true);
      intent.putExtra("fromFragment", getIntent().getStringExtra("fromFragment"));
      ActivityCompat.finishAffinity(this);
      startActivity(intent);
      try {
        ActivityAnimator anim = new ActivityAnimator();
        anim.fadeAnimationToTableContents(SettingsActivity.this);
      } catch (Exception ignored) {
      }
      this.finish();

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
