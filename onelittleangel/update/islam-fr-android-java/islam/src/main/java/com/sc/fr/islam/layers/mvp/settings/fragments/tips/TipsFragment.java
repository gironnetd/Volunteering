package com.sc.fr.islam.layers.mvp.settings.fragments.tips;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.sc.fr.islam.OnelittleAngelApplication;
import com.sc.fr.islam.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.fr.islam.layers.mvp.settings.SettingsActivity;
import com.sc.fr.islam.layers.mvp.settings.fragments.BaseFragment;
import com.sc.fr.islam.layers.mvp.settings.viewpager.ViewPagerNative;
import com.sc.fr.islam.R;
import com.sc.fr.islam.layers.mvp.tablecontents.TableContentsActivity;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class TipsFragment extends BaseFragment implements TipsViewInterface {

  /***********************************************************
   *  Attributes
   **********************************************************/

  private boolean mHasInflated = false;
  public LinearLayout lltips;
  private CardView tipsCarView;
  private int widthMeasureSpec ;
  private int heightMeasureSpec ;
  //SharedPreferences.Editor editor;


  /***********************************************************
   *  Presenter
   **********************************************************/

  /**
   * The Presenter associated with that view
   */
  TipsPresenterInterface presenter=null;

  /**
   *
   * @return the fragment instance
   */
  public static TipsFragment newInstance() {
    TipsFragment tipsFragment = new TipsFragment();
    Bundle args = new Bundle();
    args.putString(TYPE, "tips");
    tipsFragment.setArguments(args);
    return tipsFragment;
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
  public void onDestroy() {
    super.onDestroy();
    tipsCarView = null;
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

      SharedPreferences settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      //editor = settings.edit();
      //lltips = (LinearLayout) inflatedView.findViewById(R.id.ll_tips_fragment);

      tipsCarView = (CardView) inflatedView.findViewById(R.id.tips_card_view);
      ViewHelper.setAlpha(tipsCarView, 0);

      TextView presentation1 = (TextView) inflatedView.findViewById(R.id.ic_presentation_1);
      presentation1.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
      TextView presentation2 = (TextView) inflatedView.findViewById(R.id.ic_presentation_2);
      presentation2.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

      TextView presentation3 = (TextView) inflatedView.findViewById(R.id.ic_presentation_3);
      presentation3.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

      TextView presentation4 = (TextView) inflatedView.findViewById(R.id.ic_presentation_4);
      presentation4.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

      TextView presentation5 = (TextView) inflatedView.findViewById(R.id.ic_presentation_5);
      presentation5.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

      DisplayMetrics displaymetrics = new DisplayMetrics();
      getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
      int height = displaymetrics.heightPixels;
      int width = displaymetrics.widthPixels;

      widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
      heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

      ViewPagerNative viewPager = (ViewPagerNative) getActivity().findViewById(R.id.viewpager);

      viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
          tipsCarView.measure(widthMeasureSpec, heightMeasureSpec);

          LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
          lp.height = tipsCarView.getMeasuredHeight() + SettingsActivity.headerHeight + ((int) getResources().getDimension(R.dimen.activity_horizontal_margin));
          //lp.topMargin = SettingsActivity.headerHeight;

          viewPager.setLayoutParams(lp);

          LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tipsCarView.getLayoutParams();
          layoutParams.topMargin = SettingsActivity.headerHeight;
          tipsCarView.setLayoutParams(layoutParams);

          tipsCarView.setVisibility(View.VISIBLE);
          animate(tipsCarView).alpha(1).setDuration(600).start();

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
  public String getFragmentType() {
    return type;
  }

  @Override
  public boolean getHasInflated() {
    return mHasInflated;
  }

  @Override
  protected int getViewStubLayoutResource() {
    return R.layout.tips_viewstub;
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

  /***********************************************************
   *  Implement the ViewInterface
   **********************************************************/
}
