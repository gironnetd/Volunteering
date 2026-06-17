package com.sc.en.bouddhism.layers.mvp.tablecontents.fragments.homepage.fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sc.en.bouddhism.OnelittleAngelApplication;
import com.sc.en.bouddhism.layers.mvp.biography.fragments.Utils;
import com.sc.en.bouddhism.layers.mvp.common.animations.listviews.appearance.AnimationAdapter;
import com.sc.en.bouddhism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.bouddhism.R;
import com.sc.en.bouddhism.injector.PresenterInjector;
import com.sc.en.bouddhism.layers.mvp.common.animations.listviews.appearance.simple.AlphaInAnimationAdapter;
import com.sc.en.bouddhism.layers.mvp.common.animations.listviews.appearance.simple.SwingBottomInAnimationAdapter;
import com.sc.en.bouddhism.layers.mvp.common.utils.Constants;
import com.sc.en.bouddhism.layers.mvp.tablecontents.fragments.BaseFragment;
import com.sc.en.bouddhism.layers.mvp.tablecontents.fragments.homepage.adapters.listadapters.HomePageListAdapter;
import com.sc.en.bouddhism.layers.mvp.tablecontents.fragments.homepage.views.listviews.HomePageListView;

import java.util.Calendar;

public class HomePageFragment extends BaseFragment implements HomePageViewInterface, HomePageListAdapter.Callbacks {

  private static final String TITLE = "title";
  private HomePageListView homePageListView;
  private HomePageListAdapter homePageListAdapter;
  private boolean mHasInflated = false;

  private SharedPreferences settings;
  private SharedPreferences.Editor editor;
  private int widthMeasureSpec ;
  private int heightMeasureSpec ;
  private Callbacks mCallbacks;

  public HomePagePresenterInterface presenter = null;
  Dialog dHomePageLoading;
  private boolean isLoading = false;
  public boolean isResizedView = false;

  public static HomePageFragment newInstance() {

    HomePageFragment homePageFragment = new HomePageFragment();
    Bundle args = new Bundle();
    args.putString(TITLE, "");
    homePageFragment.setArguments(args);

    return homePageFragment;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if(homePageListAdapter != null) {
      for (int i = 0; i < homePageListAdapter.holders.size(); i++) {
        if(i != 1) {
          homePageListAdapter.holders.get(i).mTitle = null;
          homePageListAdapter.holders.get(i).biographyOfDay.setText(null);
          homePageListAdapter.holders.get(i).biographyOfDay.setCallbacks(null);
          homePageListAdapter.holders.get(i).biographyOfDay = null;
          homePageListAdapter.holders.get(i).quoteOfDay.setText(null);
          homePageListAdapter.holders.get(i).quoteOfDay.setCallbacks(null);
          homePageListAdapter.holders.get(i).quoteOfDay = null;
          homePageListAdapter.holders.get(i).name = null;
          homePageListAdapter.holders.get(i).link = null;
        } else {
          if(!settings.getBoolean(Constants.PICTURE_IS_NULL, false)) {
            if (homePageListAdapter.holders.get(i) != null) {
              homePageListAdapter.holders.get(i).mTitle = null;
              homePageListAdapter.holders.get(i).biographyOfDay.setText(null);
              homePageListAdapter.holders.get(i).biographyOfDay.setCallbacks(null);
              homePageListAdapter.holders.get(i).biographyOfDay = null;
              homePageListAdapter.holders.get(i).quoteOfDay.setText(null);
              homePageListAdapter.holders.get(i).quoteOfDay.setCallbacks(null);
              homePageListAdapter.holders.get(i).quoteOfDay = null;
              homePageListAdapter.holders.get(i).name = null;
              homePageListAdapter.holders.get(i).link = null;

              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                homePageListAdapter.holders.get(i).pictureOfDay.setBackground(null);
              } else {
                homePageListAdapter.holders.get(i).pictureOfDay.setBackgroundDrawable(null);
              }
              homePageListAdapter.holders.get(i).pictureOfDay = null;
            }
          }
        }


      }
      Utils.clearTextLineCache();
      homePageListAdapter.views = null;
      homePageListAdapter.setCallbacks(null);

      if(homePageListAdapter.soundPool != null) {

        homePageListAdapter.soundPool.release();
        homePageListAdapter.soundPool.setOnLoadCompleteListener(null);
      }

      homePageListAdapter = null;
    }
    homePageListView = null;
    settings = null;
    editor = null;
    //presenter.removeResources();
    //presenter = null;
    //mCallbacks = null;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if(presenter == null) presenter = PresenterInjector.getHomePagePresenter(this);

    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    //editor = settings.edit();

    DisplayMetrics displaymetrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    final int height = displaymetrics.heightPixels;
    int width = displaymetrics.widthPixels;

    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

    boolean isOrientationChanged = savedInstanceState != null;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);
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
  protected void onCreateViewAfterViewStubInflated(View inflatedView, Bundle savedInstanceState) {

    mHasInflated = true;
    getHasInflated();
    if(inflatedView != null) {

      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      editor = settings.edit();

      homePageListView = (HomePageListView) inflatedView.findViewById(R.id.homepage_list_view);

      if (homePageListAdapter == null) homePageListAdapter = new HomePageListAdapter(getActivity(), this, presenter);
      homePageListAdapter.notifyDataSetChanged();
      homePageListAdapter.setCallbacks(this);
      setCallbacks((TableContentsActivity) getActivity());

      if (isLoading && !settings.getBoolean(Constants.PICTURE_IS_NULL, false)) {

        if (settings == null) settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
        if (editor == null) editor = settings.edit();

        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.DAY_OF_MONTH);
        OnelittleAngelApplication.instance.manageConnectivityState();

        if(settings.getInt(Constants.QUOTE_OF_DAY_ID, -1) == -1 || settings.getInt(Constants.DAY_OF_MONTH, -1) == i) {

          editor.putBoolean(Constants.IS_ANIMATING_LISTVIEW_ENABLED, true).commit();
          getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

          setBottomAdapter();
        }
        //  resizeView();
        //  if (!isResizedView)resizeView();
      } else {
        //  if (!isResizedView)resizeView();
      }

      editor.putInt(Constants.TABLE_CONTENTS_CURRENT_POSITION, ((TableContentsActivity) getActivity()).viewPager.getCurrentItem());
//editor.commit();
      editor.apply();
      if (TableContentsActivity.fromFragment == null || TableContentsActivity.fromFragment.equals(getResources().getString(R.string.homepage)) || !TableContentsActivity.isFromOtherActivity) {

        if (settings.getBoolean(Constants.IS_REOPENED, false)) {
          editor.putBoolean(Constants.IS_REOPENED, false);
          editor.commit();
        }
      }

      if (TableContentsActivity.fromFragment != null && TableContentsActivity.fromFragment.equals(getResources().getString(R.string.homepage))) {
        TableContentsActivity.isFromOtherActivity = false;
      }

      if (TableContentsActivity.isOrientationChanged) {
        TableContentsActivity.isOrientationChanged = false;
      }
    }
  }

  @Override
  protected void afterViewStubInflated(View originalViewContainerWithViewStub) {
    super.afterViewStubInflated(originalViewContainerWithViewStub);
    (((TableContentsActivity) getActivity())).mObservableScrollView.smoothScrollTo(0, 0);

    if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
      getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
      getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    Calendar calendar = Calendar.getInstance();
    int i = calendar.get(Calendar.DAY_OF_MONTH);
    OnelittleAngelApplication.instance.manageConnectivityState();

    if(presenter.getPictureOfDay() == null ) {
      isLoading = true;
      isResizedView = false;

      if(settings.getInt(Constants.QUOTE_OF_DAY_ID, -1) == -1 || settings.getInt(Constants.DAY_OF_MONTH, -1) != i && OnelittleAngelApplication.instance.isConnected() || (settings.getBoolean(Constants.PICTURE_IS_NULL, false) && OnelittleAngelApplication.instance.isConnected())) {

        if (TableContentsActivity.homePageDialog != null && !TableContentsActivity.homePageDialog.isShowing()) {

          TableContentsActivity.homePageDialog.show();
          TableContentsActivity.homePageDialog.setCancelable(false);
        }

        Handler handler = new Handler();
        handler.postDelayed(() -> {
          if (presenter == null) presenter = PresenterInjector.getHomePagePresenter(this);
          presenter.updateHomePage();
        }, 1000);
      } else {
        presenter.updateHomePage();
      }
    } else {

      if(settings.getInt(Constants.QUOTE_OF_DAY_ID, -1) == -1 || settings.getInt(Constants.DAY_OF_MONTH, -1) != i && OnelittleAngelApplication.instance.isConnected()) {

        if (TableContentsActivity.homePageDialog != null && !TableContentsActivity.homePageDialog.isShowing()) {

          TableContentsActivity.homePageDialog.show();
          TableContentsActivity.homePageDialog.setCancelable(false);
        }

        Handler handler = new Handler();
        handler.postDelayed(() -> {
          if (presenter == null) presenter = PresenterInjector.getHomePagePresenter(this);
          presenter.updateHomePage();
        }, 1000);
      } else {
        // presenter.updateHomePage();
        isLoading = false;
        isResizedView = false;
        resizeView();
      }
    }
  }

  @Override
  protected void populateViewForOrientation(LayoutInflater inflater) {
    ViewGroup viewGroup = (ViewGroup) getView();
    viewGroup.removeAllViewsInLayout();
    View view = inflater.inflate(R.layout.viewstub_homepage, viewGroup, false);
    viewGroup.addView(view);
    mHasInflated = false;
    Handler handler = null;
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.home_page_fragment;
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
    return R.layout.homepage_viewstub_fragment;
  }

  public void setAlphaAdapter() {
    AnimationAdapter animCardArrayAdapter = new AlphaInAnimationAdapter(homePageListAdapter);
    animCardArrayAdapter.setAbsListView(homePageListView);
    if (homePageListView != null) {
      homePageListView.setExternalAdapter(animCardArrayAdapter, homePageListAdapter);
    }
  }

  public void setBottomAdapter() {

    AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(homePageListAdapter);
    animCardArrayAdapter.setAbsListView(homePageListView);

    homePageListView.setExternalAdapter(animCardArrayAdapter, homePageListAdapter);
  }

  public void resizeView() {

    int heightToApply = 0;
    for(int i = 0;i < homePageListAdapter.holders.size();i++) {
      homePageListAdapter.holders.get(i).llHomePage.measure(widthMeasureSpec, heightMeasureSpec);
      heightToApply += homePageListAdapter.holders.get(i).llHomePage.getMeasuredHeight() + getResources().getDimension(R.dimen.activity_horizontal_margin);
    }

    if (heightToApply != 0) {
      LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ((TableContentsActivity) getActivity()).viewPager.getLayoutParams();
      layoutParams.height = heightToApply;
      ((TableContentsActivity) getActivity()).viewPager.setLayoutParams(layoutParams);
      //((TableContentsActivity) getActivity()).viewPager.setBackgroundColor(Color.CYAN);

      if (settings == null) settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      if (editor == null) editor = settings.edit();

      editor.putBoolean(Constants.IS_ANIMATING_LISTVIEW_ENABLED, true).commit();

      Handler handler = new Handler();
      handler.postDelayed(new Runnable() {
        @Override
        public void run() {
          getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
          setBottomAdapter();
        }
      }, 500);
    } else {
//      setBottomAdapter();
//      ((TableContentsActivity) getActivity()).viewPager.setPadding(0, 10, 0, 0);
//      LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ((TableContentsActivity) getActivity()).viewPager.getLayoutParams();
//      layoutParams.height = settings.getInt(Constants.HOMEPAGE_HEIGHT, 0);
//      ((TableContentsActivity) getActivity()).viewPager.setLayoutParams(layoutParams);
//      Handler handler = new Handler();
//      handler.postDelayed(this::setBottomAdapter, 500);
    }
    isResizedView = true;
  }

  @Override
  public void updateHomePage() {
    if (homePageListAdapter != null) {

//      ((TableContentsActivity) getActivity()).viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//        @Override
//        public void onGlobalLayout() {
//
//          if (((TableContentsActivity) getActivity()).viewPager.getMeasuredHeight() < 2800)
//            ((TableContentsActivity) getActivity()).viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    2800 + ((int) OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) /*+ TableContentsActivity.headerHeight*//*/*16308*/));
//
//
//          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            ((TableContentsActivity) getActivity()).viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//          } else {
//            ((TableContentsActivity) getActivity()).viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//          }
//        }
//      });

      if (TableContentsActivity.homePageDialog != null && TableContentsActivity.homePageDialog.isShowing()) {

        TableContentsActivity.homePageDialog.dismiss();
      }

      homePageListAdapter.notifyDataSetChanged();
      getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

      if (settings == null) settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      if (editor == null) editor = settings.edit();

      editor.putBoolean(Constants.IS_ANIMATING_LISTVIEW_ENABLED, true).commit();

      setBottomAdapter();
    }
  }

  public void setActivity(TableContentsActivity activity) {
  }

  public interface Callbacks {
    void onScaling(float size, int resId);
  }

  public HomePageListView getHomePageListView() {
    return homePageListView;
  }

  public void setCallbacks(Callbacks listener) {
    mCallbacks = listener;
  }

  @Override
  public void onScaling(float size, int resId) {
    mCallbacks.onScaling(size, resId);
  }
}
