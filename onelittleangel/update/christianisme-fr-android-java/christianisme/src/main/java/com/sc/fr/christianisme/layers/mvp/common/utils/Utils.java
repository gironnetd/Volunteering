package com.sc.fr.christianisme.layers.mvp.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;

import com.sc.fr.christianisme.OnelittleAngelApplication;
import com.sc.fr.christianisme.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.fr.christianisme.layers.mvp.tablecontents.models.base.adapters.listadapters.BaseListAdapter;
import com.sc.fr.christianisme.layers.mvp.tablecontents.models.base.adapters.recycleradapters.BaseRecyclerAdapter;
import com.sc.fr.christianisme.R;
import com.sc.fr.christianisme.layers.mvp.common.animations.ResizedHeightAnimation;
import com.sc.fr.christianisme.layers.mvp.tablecontents.TableContentsActivity;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class Utils {

  public static final String SHAREDPREFERENCES_FORMAT = "%d - %d";

  private static int t = 0;
  private static int mMeasuredHeight;
  private static int objectSize;
  private static int measuredHeight;
  private static int darkerRgb;

  private static void rotate(View view, int rotate, int duration) {
    animate(view).rotation(rotate).setDuration(duration);
  }

  public static void toggle(Activity activity, final int position, final String modelsName,
                            final BaseListAdapter mBaseListAdapter,
                            final BaseListAdapter.BaseListViewHolder mBaseListViewHolder,
                            final BaseRecyclerAdapter.BaseRecyclerViewHolder mParentBaseRecyclerViewHolder,
                            final BaseRecyclerAdapter.BaseRecyclerViewHolder mBaseRecyclerViewHolder,
                            final int duration) {

    SharedPreferences settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    final SharedPreferences.Editor editor = settings.edit();

    mMeasuredHeight = settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0);
    int mContentMeasuredHeight = settings.getInt(CardViewNative.MCONTENTCARDVIEWMEASUREDHEIGHT, 0);
    darkerRgb = settings.getInt(CardViewNative.DARKERRGB, 0);

    t = 0;
    objectSize = 0;
    boolean isViewHolderIsExpanded = false;
    DisplayMetrics displaymetrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    int height = displaymetrics.heightPixels;
    int width = displaymetrics.widthPixels;

    int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

    if (position == 0) {

      if (mBaseListViewHolder.object.baseEntities != null) {

        if (mBaseListViewHolder.object.baseEntities != null)
          objectSize = mBaseListViewHolder.object.baseEntities.size();
        measuredHeight = mMeasuredHeight;
      } else {
        if (mBaseListViewHolder.object.authorBooks != null)
          objectSize = mBaseListViewHolder.object.authorBooks.size();
        measuredHeight = mMeasuredHeight; //mContentMeasuredHeight;
      }

      if (mBaseListViewHolder.isExpanded) {

        rotate(mBaseListViewHolder.arrow, 0, 200);

        final BaseRecyclerAdapter adapter = mBaseListViewHolder.baseRecyclerView.getAdapter();

        int t = 0;
        for (int i = 0; i < adapter.holders.size(); i++) {
          BaseRecyclerAdapter.BaseRecyclerViewHolder viewHolder = (BaseRecyclerAdapter.BaseRecyclerViewHolder) adapter.holders.get(i);
          if (viewHolder.isExpanded) {
            t = viewHolder.baseRecyclerView.getChildCount();
            viewHolder.headerLayout.setBackgroundColor(Color.WHITE);
            viewHolder.faith.setTextColor(darkerRgb);
            viewHolder.number.setTextColor(darkerRgb);
            viewHolder.arrow.setColor(darkerRgb);
          }
        }

        ResizedHeightAnimation animationView = new ResizedHeightAnimation(mBaseListViewHolder.frame, mMeasuredHeight);
        animationView.setDuration((objectSize + t) * duration);

        animationView.setAnimationListener(new Animation.AnimationListener() {

          @Override
          public void onAnimationStart(Animation animation) {
          }

          @Override
          public void onAnimationEnd(Animation animation) {
            mBaseListViewHolder.headerLayout.setBackgroundColor(Color.WHITE);
            mBaseListViewHolder.faith.setTextColor(darkerRgb);
            mBaseListViewHolder.number.setTextColor(darkerRgb);
            mBaseListViewHolder.arrow.setColor(darkerRgb);
            mBaseListViewHolder.baseRecyclerView = null;
            mBaseListViewHolder.viewStubBaseRecyclerView = null;
            mBaseListViewHolder.isAnimating = false;
            LayoutInflater inflater = (LayoutInflater) OnelittleAngelApplication.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View header = mBaseListViewHolder.headerLayout;
            View view = inflater.inflate(R.layout.viewstub_listview, mBaseListViewHolder.frame, false);

            mBaseListViewHolder.frame.removeAllViewsInLayout();
            mBaseListViewHolder.frame.addView(header, 0);
            mBaseListViewHolder.frame.addView(view, 1);

            ResizedHeightAnimation viewPagerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).viewPager, (int) ((mBaseListAdapter.getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)));
            viewPagerHeightAnimation.setDuration(0);
            ((TableContentsActivity) activity).viewPager.startAnimation(viewPagerHeightAnimation);

            ResizedHeightAnimation rlHeaderHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlHeader, (int) ((mBaseListAdapter.getCount() + 1) * (mMeasuredHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) > height ?
                    (int) ((mBaseListAdapter.getCount() + 1) * (mMeasuredHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : height);
            rlHeaderHeightAnimation.setDuration(0);
            ((TableContentsActivity) activity).rlHeader.startAnimation(rlHeaderHeightAnimation);

            ResizedHeightAnimation rlContainerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlContainer, (int) ((mBaseListAdapter.getCount() + 1) * (mMeasuredHeight + +OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)));
            rlContainerHeightAnimation.setDuration(0);
            ((TableContentsActivity) activity).rlContainer.startAnimation(rlContainerHeightAnimation);

            ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(mBaseListAdapter.getBaseListView(), (int) ((mBaseListAdapter.getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)));
            baseListviewHeightAnimation.setDuration(0);
            mBaseListAdapter.getBaseListView().startAnimation(baseListviewHeightAnimation);
          }

          @Override
          public void onAnimationRepeat(Animation animation) {

          }
        });

        mBaseListViewHolder.frame.startAnimation(animationView);

        mBaseListViewHolder.isExpanded = false;
        editor.putString(modelsName, String.valueOf(-1));
//editor.commit();
        editor.apply();
      } else if (!mBaseListViewHolder.isExpanded) {

        if (duration != 0) {

          if (!mBaseListViewHolder.isOtherViewHolderOpened) {

            ResizedHeightAnimation rlHeaderHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlHeader, ((int) (((mBaseListAdapter.getBaseListView().getCount() + 1) + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height ? (int) (height) : (int) (((mBaseListAdapter.getBaseListView().getCount() + 1) + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
            rlHeaderHeightAnimation.setDuration(0);
            ((TableContentsActivity) activity).rlHeader.startAnimation(rlHeaderHeightAnimation);

            ResizedHeightAnimation viewPagerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).viewPager, ((int) (((mBaseListAdapter.getBaseListView().getCount() + 1) + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) (((mBaseListAdapter.getBaseListView().getCount() + 1) + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
            viewPagerHeightAnimation.setDuration(0);
            ((TableContentsActivity) activity).viewPager.startAnimation(viewPagerHeightAnimation);
            ResizedHeightAnimation rlContainerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlContainer, ((int) (((mBaseListAdapter.getBaseListView().getCount() + 1) + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) (((mBaseListAdapter.getBaseListView().getCount() + 1) + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
            rlContainerHeightAnimation.setDuration(0);
            ((TableContentsActivity) activity).rlContainer.startAnimation(rlContainerHeightAnimation);

            ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(mBaseListAdapter.getBaseListView(), ((int) (((mBaseListAdapter.getBaseListView().getCount() + 1) + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) (((mBaseListAdapter.getBaseListView().getCount() + 1) + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))));

            baseListviewHeightAnimation.setDuration(0);
            baseListviewHeightAnimation.setAnimationListener(new Animation.AnimationListener() {

              @Override
              public void onAnimationStart(Animation animation) {
              }

              @Override
              public void onAnimationEnd(Animation animation) {

                Handler handler = new Handler();
                handler.postDelayed(() -> {

                  if (mBaseListAdapter.holders.size() != 0) {
                    for (int i = 0; i < mBaseListAdapter.holders.size(); i++) {
                      final BaseListAdapter.BaseListViewHolder viewHolder = (BaseListAdapter.BaseListViewHolder) mBaseListAdapter.holders.get(i);
                      if (viewHolder.isExpanded) {
                        rotate(viewHolder.arrow, 0, 200);

                        final BaseRecyclerAdapter adapter = viewHolder.baseRecyclerView.getAdapter();

                        int t12 = viewHolder.baseRecyclerView.getChildCount();
                        for (int j = 0; j < adapter.holders.size(); j++) {
                          BaseRecyclerAdapter.BaseRecyclerViewHolder recyclerViewHolder = (BaseRecyclerAdapter.BaseRecyclerViewHolder) adapter.holders.get(j);
                          if (recyclerViewHolder.isExpanded) {
                            t12 += recyclerViewHolder.baseRecyclerView.getChildCount();
                            recyclerViewHolder.headerLayout.setBackgroundColor(Color.WHITE);
                            recyclerViewHolder.faith.setTextColor(darkerRgb);
                            recyclerViewHolder.number.setTextColor(darkerRgb);
                            recyclerViewHolder.arrow.setColor(darkerRgb);
                          }
                        }

                        ResizedHeightAnimation animationViewHolder =
                                new ResizedHeightAnimation(viewHolder.frame, (mMeasuredHeight));
                        animationViewHolder.setDuration((objectSize + t12) * duration);
                        animationViewHolder.setAnimationListener(new Animation.AnimationListener() {
                          @Override
                          public void onAnimationStart(Animation animation12) {

                          }

                          @Override
                          public void onAnimationEnd(Animation animation12) {
                            viewHolder.headerLayout.setBackgroundColor(Color.WHITE);
                            viewHolder.faith.setTextColor(darkerRgb);
                            viewHolder.number.setTextColor(darkerRgb);
                            viewHolder.arrow.setColor(darkerRgb);

                            viewHolder.baseRecyclerView = null;
                            viewHolder.viewStubBaseRecyclerView = null;
                            LayoutInflater inflater = (LayoutInflater) OnelittleAngelApplication.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            View header = viewHolder.headerLayout;
                            View view = inflater.inflate(R.layout.viewstub_listview, viewHolder.frame, false);

                            viewHolder.frame.removeAllViewsInLayout();
                            viewHolder.frame.addView(header, 0);
                            viewHolder.frame.addView(view, 1);
                          }

                          @Override
                          public void onAnimationRepeat(Animation animation12) {

                          }
                        });
                        viewHolder.frame.startAnimation(animationViewHolder);
                        viewHolder.isExpanded = false;
                      }
                    }
                  }

                  mBaseListViewHolder.headerLayout.setBackgroundColor(darkerRgb);
                  mBaseListViewHolder.faith.setTextColor(Color.WHITE);
                  mBaseListViewHolder.number.setTextColor(Color.WHITE);
                  mBaseListViewHolder.arrow.setColor(Color.WHITE);
                  rotate(mBaseListViewHolder.arrow, -180, duration);

                  ResizedHeightAnimation animationFrame = new ResizedHeightAnimation(mBaseListViewHolder.frame,
                          (mMeasuredHeight + (measuredHeight * objectSize)));
                  animationFrame.setDuration(objectSize * duration);
                  animationFrame.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                      mBaseListViewHolder.isAnimating = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                  });
                  mBaseListViewHolder.frame.startAnimation(animationFrame);
                  mBaseListViewHolder.isExpanded = true;

                  if (mBaseListViewHolder.baseRecyclerView != null && mBaseListViewHolder.baseRecyclerView.getMeasuredHeight() == 0) {
                    ResizedHeightAnimation animationRecyclerView = new ResizedHeightAnimation(mBaseListViewHolder.baseRecyclerView,
                            (measuredHeight * (objectSize)));
                    animationRecyclerView.setDuration(objectSize * duration);
                    mBaseListViewHolder.baseRecyclerView.startAnimation(animationRecyclerView);
                  }

                  if (duration != 0) {
                    editor.putString(modelsName, String.valueOf(mBaseListViewHolder.position));
                    editor.commit();
                  }
                }, 200);
              }

              @Override
              public void onAnimationRepeat(Animation animation) {
              }
            });
            mBaseListAdapter.getBaseListView().startAnimation(baseListviewHeightAnimation);
          } else {

            if (mBaseListAdapter.holders.size() != 0) {
              for (int i = 0; i < mBaseListAdapter.holders.size(); i++) {
                final BaseListAdapter.BaseListViewHolder viewHolder = (BaseListAdapter.BaseListViewHolder) mBaseListAdapter.holders.get(i);
                if (viewHolder.isExpanded) {
                  rotate(viewHolder.arrow, 0, 200);
                  viewHolder.isAnimating = true;

                  if (viewHolder.baseRecyclerView != null) {

                    final BaseRecyclerAdapter adapter = viewHolder.baseRecyclerView.getAdapter();

                    int t = 0;
                    for (int j = 0; j < adapter.holders.size(); j++) {
                      BaseRecyclerAdapter.BaseRecyclerViewHolder recyclerViewHolder = (BaseRecyclerAdapter.BaseRecyclerViewHolder) adapter.holders.get(j);
                      if (recyclerViewHolder.isExpanded) {
                        t += recyclerViewHolder.baseRecyclerView.getChildCount();
                        recyclerViewHolder.headerLayout.setBackgroundColor(Color.WHITE);
                        recyclerViewHolder.faith.setTextColor(darkerRgb);
                        recyclerViewHolder.number.setTextColor(darkerRgb);
                        recyclerViewHolder.arrow.setColor(darkerRgb);
                      }
                    }
                  }
                  ResizedHeightAnimation animationViewHolder =
                          new ResizedHeightAnimation(viewHolder.frame, (mMeasuredHeight));
                  animationViewHolder.setDuration((t + objectSize) * duration);
                  animationViewHolder.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                      viewHolder.headerLayout.setBackgroundColor(Color.WHITE);
                      viewHolder.faith.setTextColor(darkerRgb);
                      viewHolder.number.setTextColor(darkerRgb);
                      viewHolder.arrow.setColor(darkerRgb);

                      //viewHolder.isAnimating = false;
                      viewHolder.baseRecyclerView = null;
                      viewHolder.viewStubBaseRecyclerView = null;
                      LayoutInflater inflater = (LayoutInflater) OnelittleAngelApplication.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                      //  View header  = inflater.inflate(R.layout.header_layout, viewHolder.frame, false);
                      View header = viewHolder.headerLayout;
                      View view = inflater.inflate(R.layout.viewstub_listview, viewHolder.frame, false);

                      viewHolder.frame.removeAllViewsInLayout();
                      viewHolder.frame.addView(header, 0);
                      viewHolder.frame.addView(view, 1);

                      ResizedHeightAnimation viewPagerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).viewPager, ((int) (mBaseListAdapter.getCount() * mMeasuredHeight + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) (mBaseListAdapter.getCount() * mMeasuredHeight + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))));
                      viewPagerHeightAnimation.setDuration(duration);

                      ((TableContentsActivity) activity).viewPager.startAnimation(viewPagerHeightAnimation);

                      ResizedHeightAnimation rlHeaderHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlHeader, ((int) (mBaseListAdapter.getCount() * mMeasuredHeight + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) > height ?
                              (int) (mBaseListAdapter.getCount() * mMeasuredHeight + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : height));
                      rlHeaderHeightAnimation.setDuration(duration);
                      ((TableContentsActivity) activity).rlHeader.startAnimation(rlHeaderHeightAnimation);

                      ResizedHeightAnimation rlContainerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlContainer, ((int) (mBaseListAdapter.getCount() * mMeasuredHeight + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))));
                      rlContainerHeightAnimation.setDuration(duration);
                      ((TableContentsActivity) activity).rlContainer.startAnimation(rlContainerHeightAnimation);

                      ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(mBaseListAdapter.getBaseListView(), ((int) (mBaseListAdapter.getCount() * mMeasuredHeight + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) (mBaseListAdapter.getCount() * mMeasuredHeight + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))));
                      baseListviewHeightAnimation.setDuration(duration);
                      baseListviewHeightAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                          ResizedHeightAnimation rlHeaderHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlHeader, ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height ? height : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
                          rlHeaderHeightAnimation.setDuration(0);
                          ((TableContentsActivity) activity).rlHeader.startAnimation(rlHeaderHeightAnimation);

                          ResizedHeightAnimation viewPagerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).viewPager, ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
                          viewPagerHeightAnimation.setDuration(0);
                          ((TableContentsActivity) activity).viewPager.startAnimation(viewPagerHeightAnimation);

                          ResizedHeightAnimation rlContainerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlContainer, ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
                          rlContainerHeightAnimation.setDuration(0);
                          ((TableContentsActivity) activity).rlContainer.startAnimation(rlContainerHeightAnimation);

                          ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(mBaseListAdapter.getBaseListView(), ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (measuredHeight * objectSize) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));

                          baseListviewHeightAnimation.setDuration(0);
                          baseListviewHeightAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                              Handler handler = new Handler();
                              handler.postDelayed(() -> {

                                if (mBaseListAdapter.holders.size() != 0) {
                                  for (int i1 = 0; i1 < mBaseListAdapter.holders.size(); i1++) {
                                    final BaseListAdapter.BaseListViewHolder viewHolder1 = (BaseListAdapter.BaseListViewHolder) mBaseListAdapter.holders.get(i1);
                                    if (viewHolder1.isExpanded) {
                                      rotate(viewHolder1.arrow, 0, 200);

                                      final BaseRecyclerAdapter adapter1 = viewHolder1.baseRecyclerView.getAdapter();

                                      int t1 = viewHolder1.baseRecyclerView.getChildCount();
                                      for (int j = 0; j < adapter1.holders.size(); j++) {
                                        BaseRecyclerAdapter.BaseRecyclerViewHolder recyclerViewHolder = (BaseRecyclerAdapter.BaseRecyclerViewHolder) adapter1.holders.get(j);
                                        if (recyclerViewHolder.isExpanded) {
                                          t1 += recyclerViewHolder.baseRecyclerView.getChildCount();
                                          recyclerViewHolder.headerLayout.setBackgroundColor(Color.WHITE);
                                          recyclerViewHolder.faith.setTextColor(darkerRgb);
                                          recyclerViewHolder.number.setTextColor(darkerRgb);
                                          recyclerViewHolder.arrow.setColor(darkerRgb);
                                        }
                                      }

                                      ResizedHeightAnimation animationViewHolder1 =
                                              new ResizedHeightAnimation(viewHolder1.frame, (mMeasuredHeight));
                                      animationViewHolder1.setDuration((objectSize + t1) * duration);
                                      animationViewHolder1.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation1) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation1) {
                                          viewHolder1.headerLayout.setBackgroundColor(Color.WHITE);
                                          viewHolder1.faith.setTextColor(darkerRgb);
                                          viewHolder1.number.setTextColor(darkerRgb);
                                          viewHolder1.arrow.setColor(darkerRgb);

                                          viewHolder1.baseRecyclerView = null;
                                          viewHolder1.viewStubBaseRecyclerView = null;
                                          LayoutInflater inflater1 = (LayoutInflater) OnelittleAngelApplication.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                          View header1 = viewHolder1.headerLayout;
                                          View view1 = inflater1.inflate(R.layout.viewstub_listview, viewHolder1.frame, false);

                                          viewHolder1.frame.removeAllViewsInLayout();
                                          viewHolder1.frame.addView(header1, 0);
                                          viewHolder1.frame.addView(view1, 1);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation1) {

                                        }
                                      });
                                      viewHolder1.frame.startAnimation(animationViewHolder1);
                                      viewHolder1.isExpanded = false;
                                    }
                                  }
                                }

                                mBaseListViewHolder.headerLayout.setBackgroundColor(darkerRgb);
                                mBaseListViewHolder.faith.setTextColor(Color.WHITE);
                                mBaseListViewHolder.number.setTextColor(Color.WHITE);
                                mBaseListViewHolder.arrow.setColor(Color.WHITE);
                                rotate(mBaseListViewHolder.arrow, -180, duration);

                                ResizedHeightAnimation animationFrame = new ResizedHeightAnimation(mBaseListViewHolder.frame,
                                        (mMeasuredHeight + (measuredHeight * objectSize)));
                                animationFrame.setAnimationListener(new Animation.AnimationListener() {
                                  @Override
                                  public void onAnimationStart(Animation animation) {

                                  }

                                  @Override
                                  public void onAnimationEnd(Animation animation) {
                                    mBaseListViewHolder.isAnimating = false;
                                    viewHolder.isAnimating = false;
                                  }

                                  @Override
                                  public void onAnimationRepeat(Animation animation) {

                                  }
                                });
                                animationFrame.setDuration(objectSize * duration);
                                mBaseListViewHolder.frame.startAnimation(animationFrame);
                                mBaseListViewHolder.isExpanded = true;

                                if (mBaseListViewHolder.baseRecyclerView != null && mBaseListViewHolder.baseRecyclerView.getMeasuredHeight() == 0) {
                                  ResizedHeightAnimation animationRecyclerView = new ResizedHeightAnimation(mBaseListViewHolder.baseRecyclerView,
                                          (measuredHeight * (objectSize)));
                                  animationRecyclerView.setDuration(objectSize * duration);
                                  mBaseListViewHolder.baseRecyclerView.startAnimation(animationRecyclerView);
                                }

                                if (duration != 0) {
                                  editor.putString(modelsName, String.valueOf(mBaseListViewHolder.position));
                                  editor.commit();
                                }
                              }, 200);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                          });
                          mBaseListAdapter.getBaseListView().startAnimation(baseListviewHeightAnimation);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                      });
                      mBaseListAdapter.getBaseListView().startAnimation(baseListviewHeightAnimation);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                  });
                  viewHolder.frame.startAnimation(animationViewHolder);
                  viewHolder.isExpanded = false;
                }
              }
            }
          }
        } else {

          if (mBaseListAdapter.holders.size() != 0) {
            for (int i = 0; i < mBaseListAdapter.holders.size(); i++) {
              final BaseListAdapter.BaseListViewHolder viewHolder = (BaseListAdapter.BaseListViewHolder) mBaseListAdapter.holders.get(i);
              if (viewHolder.isExpanded) {
                rotate(viewHolder.arrow, 0, 200);

                final BaseRecyclerAdapter adapter = viewHolder.baseRecyclerView.getAdapter();

                int t = viewHolder.baseRecyclerView.getChildCount();
                for (int j = 0; j < adapter.holders.size(); j++) {
                  BaseRecyclerAdapter.BaseRecyclerViewHolder recyclerViewHolder = (BaseRecyclerAdapter.BaseRecyclerViewHolder) adapter.holders.get(j);
                  if (recyclerViewHolder.isExpanded) {
                    t += recyclerViewHolder.baseRecyclerView.getChildCount();
                    recyclerViewHolder.headerLayout.setBackgroundColor(Color.WHITE);
                    recyclerViewHolder.faith.setTextColor(darkerRgb);
                    recyclerViewHolder.number.setTextColor(darkerRgb);
                    recyclerViewHolder.arrow.setColor(darkerRgb);
                  }
                }

                ResizedHeightAnimation animationViewHolder =
                        new ResizedHeightAnimation(viewHolder.frame, (mMeasuredHeight));
                animationViewHolder.setDuration((objectSize) * duration);
                animationViewHolder.setAnimationListener(new Animation.AnimationListener() {
                  @Override
                  public void onAnimationStart(Animation animation) {

                  }

                  @Override
                  public void onAnimationEnd(Animation animation) {
                    viewHolder.headerLayout.setBackgroundColor(Color.WHITE);
                    viewHolder.faith.setTextColor(darkerRgb);
                    viewHolder.number.setTextColor(darkerRgb);
                    viewHolder.arrow.setColor(darkerRgb);

                    viewHolder.baseRecyclerView = null;
                    viewHolder.viewStubBaseRecyclerView = null;
                    LayoutInflater inflater = (LayoutInflater) OnelittleAngelApplication.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    //  View header  = inflater.inflate(R.layout.header_layout, viewHolder.frame, false);
                    View header = viewHolder.headerLayout;
                    View view = inflater.inflate(R.layout.viewstub_listview, viewHolder.frame, false);

                    viewHolder.frame.removeAllViewsInLayout();
                    viewHolder.frame.addView(header, 0);
                    viewHolder.frame.addView(view, 1);
                  }

                  @Override
                  public void onAnimationRepeat(Animation animation) {

                  }
                });
                viewHolder.frame.startAnimation(animationViewHolder);
                viewHolder.isExpanded = false;
              }
            }
          }

          mBaseListViewHolder.headerLayout.setBackgroundColor(darkerRgb);
          mBaseListViewHolder.faith.setTextColor(Color.WHITE);
          mBaseListViewHolder.number.setTextColor(Color.WHITE);
          mBaseListViewHolder.arrow.setColor(Color.WHITE);
          rotate(mBaseListViewHolder.arrow, -180, duration);

          ResizedHeightAnimation animationFrame = new ResizedHeightAnimation(mBaseListViewHolder.frame,
                  (mMeasuredHeight + (measuredHeight * objectSize)));
          animationFrame.setDuration(objectSize * duration);
          mBaseListViewHolder.frame.startAnimation(animationFrame);
          mBaseListViewHolder.isExpanded = true;

          if (mBaseListViewHolder.baseRecyclerView != null && mBaseListViewHolder.baseRecyclerView.getMeasuredHeight() == 0) {
            ResizedHeightAnimation animationRecyclerView = new ResizedHeightAnimation(mBaseListViewHolder.baseRecyclerView,
                    (measuredHeight * (objectSize)));
            animationRecyclerView.setDuration(objectSize * duration);
            mBaseListViewHolder.baseRecyclerView.startAnimation(animationRecyclerView);
          }

          if (duration != 0) {
            editor.putString(modelsName, String.valueOf(mBaseListViewHolder.position));
            editor.commit();
          }
        }
      }
    }

    if (position == 1) {

      if (mBaseRecyclerViewHolder.object.baseEntities != null) {
        objectSize = mBaseRecyclerViewHolder.object.baseEntities.size();
        measuredHeight = mMeasuredHeight;
      } else {
        objectSize = mBaseRecyclerViewHolder.object.authorBooks.size();
        measuredHeight = mMeasuredHeight;//mContentMeasuredHeight;
      }

      //mBaseRecyclerViewHolder.isAnimating = true;

      if (duration != 0) {

        if (mBaseRecyclerViewHolder.isExpanded) {

          rotate(mBaseRecyclerViewHolder.arrow, 0, 200);

          final BaseRecyclerAdapter adapter = mBaseRecyclerViewHolder.baseRecyclerView.getAdapter();

          int t = 0;
          for (int i = 0; i < adapter.holders.size(); i++) {
            BaseRecyclerAdapter.BaseRecyclerViewHolder viewHolder = (BaseRecyclerAdapter.BaseRecyclerViewHolder) adapter.holders.get(i);
            if (viewHolder.isExpanded) {
              t = viewHolder.baseRecyclerView.getChildCount();
              viewHolder.headerLayout.setBackgroundColor(Color.WHITE);
              viewHolder.faith.setTextColor(darkerRgb);
              viewHolder.number.setTextColor(darkerRgb);
              viewHolder.arrow.setColor(darkerRgb);
            }
          }

          ResizedHeightAnimation animationView = new ResizedHeightAnimation(mBaseRecyclerViewHolder.frame, mMeasuredHeight);
          animationView.setDuration((objectSize /*+ t*/) * duration);

          animationView.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

              mBaseRecyclerViewHolder.headerLayout.setBackgroundColor(Color.WHITE);
              mBaseRecyclerViewHolder.faith.setTextColor(darkerRgb);
              mBaseRecyclerViewHolder.number.setTextColor(darkerRgb);
              mBaseRecyclerViewHolder.arrow.setColor(darkerRgb);
              mBaseRecyclerViewHolder.isAnimating = false;
              mBaseRecyclerViewHolder.baseRecyclerView = null;
              mBaseRecyclerViewHolder.viewStubBaseRecyclerView = null;
              LayoutInflater inflater = (LayoutInflater) OnelittleAngelApplication.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

              View header = mBaseRecyclerViewHolder.headerLayout;
              View view = inflater.inflate(R.layout.viewstub_listview, mBaseRecyclerViewHolder.frame, false);

              mBaseRecyclerViewHolder.frame.removeAllViewsInLayout();
              mBaseRecyclerViewHolder.frame.addView(header, 0);
              mBaseRecyclerViewHolder.frame.addView(view, 1);

              ResizedHeightAnimation rlHeaderHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlHeader, ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size()))) < height ? height : (mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size())))));
              rlHeaderHeightAnimation.setDuration(0);
              ((TableContentsActivity) activity).rlHeader.startAnimation(rlHeaderHeightAnimation);

              ResizedHeightAnimation viewPagerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).viewPager, (mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size()))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size()))));
              viewPagerHeightAnimation.setDuration(0);
              ((TableContentsActivity) activity).viewPager.startAnimation(viewPagerHeightAnimation);

              ResizedHeightAnimation rlContainerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlContainer, ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + ((mMeasuredHeight * 2) + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size()))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + ((mMeasuredHeight * 2) + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size())))));
              rlContainerHeightAnimation.setDuration(0);
              ((TableContentsActivity) activity).rlContainer.startAnimation(rlContainerHeightAnimation);

              ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(mBaseListAdapter.getBaseListView(), ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + (mMeasuredHeight * 2) + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size())) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + (mMeasuredHeight * 2) + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size()))));

              baseListviewHeightAnimation.setDuration(0);
              mBaseListAdapter.getBaseListView().startAnimation(baseListviewHeightAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
          });

          mBaseRecyclerViewHolder.frame.startAnimation(animationView);
          mBaseRecyclerViewHolder.isExpanded = false;
          editor.putString(modelsName, String.valueOf(mBaseListViewHolder.position));
          editor.commit();

          ResizedHeightAnimation animationBaseListViewHolder =
                  new ResizedHeightAnimation(mBaseListViewHolder.frame,
                          (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size() + 1)));
          animationBaseListViewHolder.setDuration(objectSize * duration);
          mBaseListViewHolder.frame.startAnimation(animationBaseListViewHolder);
        } else if (!mBaseRecyclerViewHolder.isExpanded) {

          if (!mBaseRecyclerViewHolder.isOtherViewHolderOpened) {

            ResizedHeightAnimation rlHeaderHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlHeader, ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height ? height : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
            rlHeaderHeightAnimation.setDuration(0);
            ((TableContentsActivity) activity).rlHeader.startAnimation(rlHeaderHeightAnimation);

            ResizedHeightAnimation viewPagerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).viewPager, (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))));
            viewPagerHeightAnimation.setDuration(0);
            viewPagerHeightAnimation.setAnimationListener(new Animation.AnimationListener() {

              @Override
              public void onAnimationStart(Animation animation) {

              }

              @Override
              public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(() -> {

                  mBaseRecyclerViewHolder.headerLayout.setBackgroundColor(darkerRgb);
                  mBaseRecyclerViewHolder.faith.setTextColor(Color.WHITE);
                  mBaseRecyclerViewHolder.number.setTextColor(Color.WHITE);
                  mBaseRecyclerViewHolder.arrow.setColor(Color.WHITE);
                  rotate(mBaseRecyclerViewHolder.arrow, -180, duration);

                  ResizedHeightAnimation animationRecyclerFrame = new ResizedHeightAnimation(mBaseRecyclerViewHolder.frame,
                          (mMeasuredHeight + (measuredHeight * (objectSize))));
                  animationRecyclerFrame.setDuration(objectSize * duration);
                  animationRecyclerFrame.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                      mBaseRecyclerViewHolder.isAnimating = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                  });
                  mBaseRecyclerViewHolder.frame.startAnimation(animationRecyclerFrame);
                  mBaseRecyclerViewHolder.isExpanded = true;

                  ResizedHeightAnimation animationRecyclerView = new ResizedHeightAnimation(mBaseRecyclerViewHolder.baseRecyclerView, (measuredHeight * (objectSize + 1)));
                  animationRecyclerView.setDuration(objectSize * duration);
                  mBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationRecyclerView);

                  if (mBaseRecyclerViewHolder.baseRecyclerView.getMeasuredHeight() == 0) {
                    ResizedHeightAnimation animationRecyclerView2 = new ResizedHeightAnimation(mBaseRecyclerViewHolder.baseRecyclerView, (measuredHeight * (objectSize + 1)));

                    if (t == 0) animationRecyclerView2.setDuration(objectSize * duration);
                    else animationRecyclerView2.setDuration(t * duration);

                    mBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationRecyclerView2);
                  }

                  ResizedHeightAnimation animationFrame = new ResizedHeightAnimation(mBaseListViewHolder.baseRecyclerView,
                          ((mBaseListViewHolder.baseRecyclerView.getAdapter().objects.size() * mMeasuredHeight) + (objectSize * measuredHeight) + measuredHeight));

                  animationFrame.setDuration((objectSize + t) * duration);
                  mBaseListViewHolder.baseRecyclerView.startAnimation(animationFrame);

                  ResizedHeightAnimation animationView = new ResizedHeightAnimation(mBaseListViewHolder.frame,
                          (((mBaseListViewHolder.baseRecyclerView.getAdapter().objects.size() + 1) * mMeasuredHeight)
                                  + (objectSize * measuredHeight)));
                  animationView.setDuration((objectSize + t) * duration);
                  mBaseListViewHolder.frame.startAnimation(animationView);

                  if (duration != 0) {
                    editor.putString(modelsName, mBaseListViewHolder.position + " - " + mBaseRecyclerViewHolder.position);
                    editor.commit();
                  }
                }, 200);
              }

              @Override
              public void onAnimationRepeat(Animation animation) {
              }
            });

            ((TableContentsActivity) activity).viewPager.startAnimation(viewPagerHeightAnimation);

            ResizedHeightAnimation rlContainerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlContainer, (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size() /*+ 1*/)) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size() /*+ 1*/)) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))));
            rlContainerHeightAnimation.setDuration(0);
            ((TableContentsActivity) activity).rlContainer.startAnimation(rlContainerHeightAnimation);

            ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(mBaseListAdapter.getBaseListView(), ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));

            baseListviewHeightAnimation.setDuration(500);

          } else {

            BaseRecyclerAdapter adapter = mBaseListViewHolder.baseRecyclerView.getAdapter();

            if (adapter.holders.size() != 0) {

              for (int i = 0; i < adapter.holders.size(); i++) {

                final BaseRecyclerAdapter.BaseRecyclerViewHolder viewHolder = (BaseRecyclerAdapter.BaseRecyclerViewHolder) adapter.holders.get(i);

                if (viewHolder.isExpanded) {

                  isViewHolderIsExpanded = true;
                  rotate(viewHolder.arrow, 0, 200);
                  viewHolder.isAnimating = true;

                  ResizedHeightAnimation animationRecyclerView = new ResizedHeightAnimation(viewHolder.baseRecyclerView, (mMeasuredHeight));
                  animationRecyclerView.setDuration((t) * duration);

                  ResizedHeightAnimation animationFrame = new ResizedHeightAnimation(viewHolder.frame,
                          (mMeasuredHeight));
                  if (viewHolder.object.authorBooks != null) {
                    t = viewHolder.object.authorBooks.size();
                  }

                  if (viewHolder.object.baseEntities != null) {
                    t = viewHolder.object.baseEntities.size();
                  }

                  animationFrame.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {

                      ResizedHeightAnimation animationBaseListViewHolder =
                              new ResizedHeightAnimation(mBaseListViewHolder.frame,
                                      (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size() + 1)));
                      animationBaseListViewHolder.setDuration(t * duration);
                      mBaseListViewHolder.frame.startAnimation(animationBaseListViewHolder);

                      ResizedHeightAnimation animationRecyclerView = new ResizedHeightAnimation(mBaseListViewHolder.baseRecyclerView,
                              (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size())));
                      animationRecyclerView.setDuration(t * duration);
                      mBaseListViewHolder.baseRecyclerView.startAnimation(animationRecyclerView);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                      //viewHolder.isAnimating = false;
                      ResizedHeightAnimation rlHeaderHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlHeader, ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size() /*+ 1*/)) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height ? height : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size() /*+ 1*/)) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
                      rlHeaderHeightAnimation.setDuration(0);
                      ((TableContentsActivity) activity).rlHeader.startAnimation(rlHeaderHeightAnimation);

                      ResizedHeightAnimation viewPagerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).viewPager, ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size() /*+ 1*/)) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size() /*+ 1*/)) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
                      viewPagerHeightAnimation.setDuration(0);
                      viewPagerHeightAnimation.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                          Handler handler = new Handler();
                          handler.postDelayed(() -> {

                            viewHolder.headerLayout.setBackgroundColor(Color.WHITE);
                            viewHolder.faith.setTextColor(darkerRgb);
                            viewHolder.number.setTextColor(darkerRgb);
                            viewHolder.arrow.setColor(darkerRgb);

                            viewHolder.baseRecyclerView = null;
                            viewHolder.viewStubBaseRecyclerView = null;
                            LayoutInflater inflater = (LayoutInflater) OnelittleAngelApplication.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            View header = viewHolder.headerLayout;
                            View view = inflater.inflate(R.layout.viewstub_listview, viewHolder.frame, false);

                            viewHolder.frame.removeAllViewsInLayout();
                            viewHolder.frame.addView(header, 0);
                            viewHolder.frame.addView(view, 1);

                            mBaseRecyclerViewHolder.headerLayout.setBackgroundColor(darkerRgb);
                            mBaseRecyclerViewHolder.faith.setTextColor(Color.WHITE);
                            mBaseRecyclerViewHolder.number.setTextColor(Color.WHITE);
                            mBaseRecyclerViewHolder.arrow.setColor(Color.WHITE);
                            rotate(mBaseRecyclerViewHolder.arrow, -180, 200);

                            ResizedHeightAnimation animationFrame1 = new ResizedHeightAnimation(mBaseRecyclerViewHolder.frame,
                                    (mMeasuredHeight + (measuredHeight * (objectSize))));
                            animationFrame1.setAnimationListener(new Animation.AnimationListener() {
                              @Override
                              public void onAnimationStart(Animation animation) {
                              }

                              @Override
                              public void onAnimationEnd(Animation animation) {
                                mBaseRecyclerViewHolder.isAnimating = false;
                                viewHolder.isAnimating = false;
                              }

                              @Override
                              public void onAnimationRepeat(Animation animation) {
                              }
                            });
                            animationFrame1.setDuration(objectSize * duration);
                            mBaseRecyclerViewHolder.frame.startAnimation(animationFrame1);
                            mBaseRecyclerViewHolder.isExpanded = true;
                            if (duration != 0) {
                              editor.putString(modelsName, mBaseListViewHolder.position + " - " + mBaseRecyclerViewHolder.position);
                              editor.commit();
                            }

                            ResizedHeightAnimation animationView = new ResizedHeightAnimation(mBaseRecyclerViewHolder.baseRecyclerView,
                                    (measuredHeight * (objectSize)));
                            animationView.setDuration(objectSize * duration);
                            mBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationView);

                            if (mBaseRecyclerViewHolder.baseRecyclerView.getMeasuredHeight() == 0) {
                              ResizedHeightAnimation animationRecyclerView1 = new ResizedHeightAnimation(mBaseRecyclerViewHolder.baseRecyclerView,
                                      (measuredHeight * (objectSize)));
                              animationRecyclerView1.setDuration(objectSize * duration);
                              mBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationRecyclerView1);
                            }

                            ResizedHeightAnimation animationBaseListViewHolder = new ResizedHeightAnimation(mBaseListViewHolder.frame,
                                    ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getChildCount() + 1)) + (measuredHeight * (objectSize))));
                            animationBaseListViewHolder.setDuration(objectSize * duration);
                            mBaseListViewHolder.frame.startAnimation(animationBaseListViewHolder);

                            ResizedHeightAnimation animationRecyclerView1 = new ResizedHeightAnimation(mBaseListViewHolder.baseRecyclerView,
                                    ((mMeasuredHeight * mBaseListViewHolder.baseRecyclerView.getChildCount()) + (measuredHeight * (objectSize))));
                            animationRecyclerView1.setDuration(objectSize * duration);
                            mBaseListViewHolder.baseRecyclerView.startAnimation(animationRecyclerView1);
                          }, 200);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                      });

                      ((TableContentsActivity) activity).viewPager.startAnimation(viewPagerHeightAnimation);

                      ResizedHeightAnimation rlContainerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlContainer, ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size() /*+ 1*/)) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size() /*+ 1*/)) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
                      rlContainerHeightAnimation.setDuration(0);
                      ((TableContentsActivity) activity).rlContainer.startAnimation(rlContainerHeightAnimation);

                      ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(mBaseListAdapter.getBaseListView(), ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));

                      baseListviewHeightAnimation.setDuration(500);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                  });

                  animationFrame.setDuration((t) * duration);
                  viewHolder.frame.startAnimation(animationFrame);
                  viewHolder.isExpanded = false;
                }
              }
            }
          }
        }
      } else {

        mBaseRecyclerViewHolder.headerLayout.setBackgroundColor(darkerRgb);
        mBaseRecyclerViewHolder.faith.setTextColor(Color.WHITE);
        mBaseRecyclerViewHolder.number.setTextColor(Color.WHITE);
        mBaseRecyclerViewHolder.arrow.setColor(Color.WHITE);
        rotate(mBaseRecyclerViewHolder.arrow, -180, duration);

        ResizedHeightAnimation animationRecyclerFrame = new ResizedHeightAnimation(mBaseRecyclerViewHolder.frame,
                (mMeasuredHeight + (measuredHeight * (objectSize))));
        animationRecyclerFrame.setDuration(objectSize * duration);
        mBaseRecyclerViewHolder.frame.startAnimation(animationRecyclerFrame);
        mBaseRecyclerViewHolder.isExpanded = true;

        ResizedHeightAnimation animationRecyclerView = new ResizedHeightAnimation(mBaseRecyclerViewHolder.baseRecyclerView, (measuredHeight * (objectSize /*+ 1*/)));
        animationRecyclerView.setDuration(objectSize * duration);
        mBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationRecyclerView);

        if (mBaseRecyclerViewHolder.baseRecyclerView.getMeasuredHeight() == 0) {
          ResizedHeightAnimation animationRecyclerView2 = new ResizedHeightAnimation(mBaseRecyclerViewHolder.baseRecyclerView, (measuredHeight * (objectSize)));

          if (t == 0) animationRecyclerView2.setDuration(objectSize * duration);
          else animationRecyclerView2.setDuration(t * duration);

          mBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationRecyclerView2);
        }

        ResizedHeightAnimation animationFrame = new ResizedHeightAnimation(mBaseListViewHolder.baseRecyclerView,
                ((mBaseListViewHolder.baseRecyclerView.getAdapter().objects.size() * mMeasuredHeight)
                        + (objectSize * measuredHeight)));
        animationFrame.setDuration((objectSize + t) * duration);
        mBaseListViewHolder.baseRecyclerView.startAnimation(animationFrame);

        ResizedHeightAnimation animationView = new ResizedHeightAnimation(mBaseListViewHolder.frame,
                (((mBaseListViewHolder.baseRecyclerView.getAdapter().objects.size() + 1) * mMeasuredHeight)
                        + (objectSize * measuredHeight)));
        animationView.setDuration((objectSize + t) * duration);
        mBaseListViewHolder.frame.startAnimation(animationView);
      }
    }

    if (position == 2) {

      if (mBaseRecyclerViewHolder.object.baseEntities != null) {
        objectSize = mBaseRecyclerViewHolder.object.baseEntities.size();
        measuredHeight = mMeasuredHeight;
      } else {
        objectSize = mBaseRecyclerViewHolder.object.authorBooks.size();
        measuredHeight = mMeasuredHeight;//mContentMeasuredHeight;
      }

      if (duration != 0) {

        if (mBaseRecyclerViewHolder.isExpanded) {

          rotate(mBaseRecyclerViewHolder.arrow, 0, 200);

          ResizedHeightAnimation animationView = new ResizedHeightAnimation(mBaseRecyclerViewHolder.frame,
                  mMeasuredHeight);
          animationView.setDuration((objectSize) * duration);
          animationView.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
              mBaseRecyclerViewHolder.headerLayout.setBackgroundColor(Color.WHITE);
              mBaseRecyclerViewHolder.faith.setTextColor(darkerRgb);
              mBaseRecyclerViewHolder.number.setTextColor(darkerRgb);
              mBaseRecyclerViewHolder.arrow.setColor(darkerRgb);
              mBaseRecyclerViewHolder.isAnimating = false;
              mBaseRecyclerViewHolder.baseRecyclerView = null;
              mBaseRecyclerViewHolder.viewStubBaseRecyclerView = null;
              LayoutInflater inflater = (LayoutInflater) OnelittleAngelApplication.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

              View header = mBaseRecyclerViewHolder.headerLayout;
              View view = inflater.inflate(R.layout.viewstub_listview, mBaseRecyclerViewHolder.frame, false);

              mBaseRecyclerViewHolder.frame.removeAllViewsInLayout();
              mBaseRecyclerViewHolder.frame.addView(header, 0);
              mBaseRecyclerViewHolder.frame.addView(view, 1);

              ResizedHeightAnimation rlHeaderHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlHeader, ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size()) + (mParentBaseRecyclerViewHolder.object.baseEntities.size() * mMeasuredHeight))) < height ? height : (mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size()) + (mParentBaseRecyclerViewHolder.object.baseEntities.size() * mMeasuredHeight)))));
              rlHeaderHeightAnimation.setDuration(0);
              ((TableContentsActivity) activity).rlHeader.startAnimation(rlHeaderHeightAnimation);

              ResizedHeightAnimation viewPagerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).viewPager, ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size()) + (mParentBaseRecyclerViewHolder.object.baseEntities.size() * mMeasuredHeight))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size()) + (mParentBaseRecyclerViewHolder.object.baseEntities.size() * mMeasuredHeight)))));
              viewPagerHeightAnimation.setDuration(0);
              ((TableContentsActivity) activity).viewPager.startAnimation(viewPagerHeightAnimation);

              ResizedHeightAnimation rlContainerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlContainer, ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + ((mMeasuredHeight * 2) + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size()) + (mParentBaseRecyclerViewHolder.object.baseEntities.size() * mMeasuredHeight))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + ((mMeasuredHeight * 2) + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size()) + (mParentBaseRecyclerViewHolder.object.baseEntities.size() * mMeasuredHeight)))));
              rlContainerHeightAnimation.setDuration(0);
              ((TableContentsActivity) activity).rlContainer.startAnimation(rlContainerHeightAnimation);

              ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(mBaseListAdapter.getBaseListView(), ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + (mMeasuredHeight * 2) + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size()) + (mParentBaseRecyclerViewHolder.object.baseEntities.size() * mMeasuredHeight)) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + (mMeasuredHeight * 2) + (mMeasuredHeight * (mBaseListViewHolder.object.baseEntities.size()) + (mParentBaseRecyclerViewHolder.object.baseEntities.size() * mMeasuredHeight))));

              baseListviewHeightAnimation.setDuration(0);
              mBaseListAdapter.getBaseListView().startAnimation(baseListviewHeightAnimation);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
          });

          mBaseRecyclerViewHolder.frame.startAnimation(animationView);
          mBaseRecyclerViewHolder.isExpanded = false;
          editor.putString(modelsName, mBaseListViewHolder.position + " - " + mParentBaseRecyclerViewHolder.position);
          editor.commit();

          ResizedHeightAnimation animationParentFrame = new ResizedHeightAnimation(mParentBaseRecyclerViewHolder.frame,
                  (mMeasuredHeight * (mParentBaseRecyclerViewHolder.baseRecyclerView.getChildCount() + 1)));
          animationParentFrame.setDuration(objectSize * duration);
          mParentBaseRecyclerViewHolder.frame.startAnimation(animationParentFrame);

          ResizedHeightAnimation animationParentRecyclerView = new ResizedHeightAnimation(mParentBaseRecyclerViewHolder.baseRecyclerView,
                  (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getChildCount()));
          animationParentRecyclerView.setDuration(objectSize * duration);
          mParentBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationParentRecyclerView);

          ResizedHeightAnimation animationBaseListViewHolder =
                  new ResizedHeightAnimation(mBaseListViewHolder.frame,
                          (mMeasuredHeight * (mParentBaseRecyclerViewHolder.baseRecyclerView.getChildCount() + mBaseListViewHolder.object.baseEntities.size() + 1)));
          animationBaseListViewHolder.setDuration(objectSize * duration);
          mBaseListViewHolder.frame.startAnimation(animationBaseListViewHolder);
        } else if (!mBaseRecyclerViewHolder.isExpanded) {

          if (!mBaseRecyclerViewHolder.isOtherViewHolderOpened) {

            ResizedHeightAnimation rlHeaderHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlHeader, ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height ? height : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
            rlHeaderHeightAnimation.setDuration(0);
            ((TableContentsActivity) activity).rlHeader.startAnimation(rlHeaderHeightAnimation);

            ResizedHeightAnimation viewPagerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).viewPager, ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
            viewPagerHeightAnimation.setDuration(0);
            viewPagerHeightAnimation.setAnimationListener(new Animation.AnimationListener() {

              @Override
              public void onAnimationStart(Animation animation) {

              }

              @Override
              public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(() -> {

                  mBaseRecyclerViewHolder.headerLayout.setBackgroundColor(darkerRgb);
                  mBaseRecyclerViewHolder.faith.setTextColor(Color.WHITE);
                  mBaseRecyclerViewHolder.number.setTextColor(Color.WHITE);
                  mBaseRecyclerViewHolder.arrow.setColor(Color.WHITE);
                  rotate(mBaseRecyclerViewHolder.arrow, -180, 200);

                  ResizedHeightAnimation animationFrame = new ResizedHeightAnimation(mBaseRecyclerViewHolder.frame,
                          (mMeasuredHeight + (measuredHeight * (objectSize))));
                  animationFrame.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                      mBaseRecyclerViewHolder.isAnimating = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                  });
                  animationFrame.setDuration(objectSize * duration);
                  mBaseRecyclerViewHolder.frame.startAnimation(animationFrame);
                  mBaseRecyclerViewHolder.isExpanded = true;

                  if (duration != 0) {
                    editor.putString(modelsName, mBaseListViewHolder.position + " - " +
                            mParentBaseRecyclerViewHolder.position + " - " +
                            mBaseRecyclerViewHolder.position);
                    editor.commit();
                  }

                  ResizedHeightAnimation animationView = new ResizedHeightAnimation(mBaseRecyclerViewHolder.baseRecyclerView,
                          (measuredHeight * (objectSize)));
                  animationView.setDuration(objectSize * duration);
                  mBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationView);

                  if (mBaseRecyclerViewHolder.baseRecyclerView.getMeasuredHeight() == 0) {
                    ResizedHeightAnimation animationRecyclerView = new ResizedHeightAnimation(mBaseRecyclerViewHolder.baseRecyclerView,
                            (measuredHeight * (objectSize)));
                    animationRecyclerView.setDuration(objectSize * duration);

                    mBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationRecyclerView);
                  }

                  ResizedHeightAnimation animationParentFrameView = new ResizedHeightAnimation(mParentBaseRecyclerViewHolder.frame,
                          ((mMeasuredHeight * (mParentBaseRecyclerViewHolder.baseRecyclerView.getChildCount() + 1)) + measuredHeight * (objectSize)));
                  animationParentFrameView.setDuration(objectSize * duration);
                  mParentBaseRecyclerViewHolder.frame.startAnimation(animationParentFrameView);

                  ResizedHeightAnimation animationParentRecyclerView = new ResizedHeightAnimation(mParentBaseRecyclerViewHolder.baseRecyclerView,
                          (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getChildCount() + measuredHeight * (objectSize)));
                  animationParentRecyclerView.setDuration(objectSize * duration);
                  mParentBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationParentRecyclerView);

                  ResizedHeightAnimation animationBaseListViewHolder = new ResizedHeightAnimation(mBaseListViewHolder.frame,
                          ((mMeasuredHeight * (mParentBaseRecyclerViewHolder.baseRecyclerView.getChildCount() + mBaseListViewHolder.baseRecyclerView.getChildCount() + 1)) + (measuredHeight * (objectSize))));

                  animationBaseListViewHolder.setDuration(objectSize * duration);

                  mBaseListViewHolder.frame.startAnimation(animationBaseListViewHolder);

                  ResizedHeightAnimation animationRecyclerView = new ResizedHeightAnimation(mBaseListViewHolder.baseRecyclerView,
                          ((mMeasuredHeight * (mParentBaseRecyclerViewHolder.baseRecyclerView.getChildCount() + mBaseListViewHolder.baseRecyclerView.getChildCount())) + (measuredHeight * (objectSize))));

                  animationRecyclerView.setDuration(objectSize * duration);

                  mBaseListViewHolder.baseRecyclerView.startAnimation(animationRecyclerView);
                }, 200);
              }

              @Override
              public void onAnimationRepeat(Animation animation) {
              }
            });

            ((TableContentsActivity) activity).viewPager.startAnimation(viewPagerHeightAnimation);

            ResizedHeightAnimation rlContainerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlContainer, ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
            rlContainerHeightAnimation.setDuration(0);
            ((TableContentsActivity) activity).rlContainer.startAnimation(rlContainerHeightAnimation);

            ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(mBaseListAdapter.getBaseListView(), ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size() /*+ 1*/)) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size() /*+ 1*/)) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));

            baseListviewHeightAnimation.setDuration(500);
          } else {

            BaseRecyclerAdapter adapter = mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter();

            if (adapter.holders.size() != 0) {

              for (int i = 0; i < adapter.holders.size(); i++) {

                final BaseRecyclerAdapter.BaseRecyclerViewHolder viewHolder = (BaseRecyclerAdapter.BaseRecyclerViewHolder) adapter.holders.get(i);

                if (viewHolder.isExpanded) {

                  isViewHolderIsExpanded = true;
                  rotate(viewHolder.arrow, 0, 200);
                  viewHolder.isAnimating = true;
                  ResizedHeightAnimation animationFrame = new ResizedHeightAnimation(viewHolder.frame,
                          (mMeasuredHeight));
                  if (viewHolder.object.authorBooks != null) {
                    t = viewHolder.object.authorBooks.size();
                  }

                  if (viewHolder.object.baseEntities != null) {
                    t = viewHolder.object.baseEntities.size();
                  }
                  animationFrame.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                      ResizedHeightAnimation animationParentFrame = new ResizedHeightAnimation(mParentBaseRecyclerViewHolder.frame,
                              (mMeasuredHeight * (mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size() + 1)));
                      animationParentFrame.setDuration(t * duration);
                      mParentBaseRecyclerViewHolder.frame.startAnimation(animationParentFrame);

                      ResizedHeightAnimation animationParentRecyclerView = new ResizedHeightAnimation(mParentBaseRecyclerViewHolder.baseRecyclerView,
                              (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()));
                      animationParentRecyclerView.setDuration(t * duration);
                      mParentBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationParentRecyclerView);

                      ResizedHeightAnimation animationBaseListViewHolder =
                              new ResizedHeightAnimation(mBaseListViewHolder.frame,
                                      ((mMeasuredHeight * (mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()
                                              + mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size() + 1))));
                      animationBaseListViewHolder.setDuration(t * duration);
                      mBaseListViewHolder.frame.startAnimation(animationBaseListViewHolder);

                      ResizedHeightAnimation animationRecyclerView = new ResizedHeightAnimation(mBaseListViewHolder.baseRecyclerView,
                              ((mMeasuredHeight * (mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()
                                      + mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size()))));
                      animationRecyclerView.setDuration(t * duration);
                      mBaseListViewHolder.baseRecyclerView.startAnimation(animationRecyclerView);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                      viewHolder.headerLayout.setBackgroundColor(Color.WHITE);
                      viewHolder.faith.setTextColor(darkerRgb);
                      viewHolder.number.setTextColor(darkerRgb);
                      viewHolder.arrow.setColor(darkerRgb);
//                      viewHolder.isAnimating = false;
                      viewHolder.baseRecyclerView = null;
                      viewHolder.viewStubBaseRecyclerView = null;

                      LayoutInflater inflater = (LayoutInflater) OnelittleAngelApplication.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                      View header = viewHolder.headerLayout;
                      View view = inflater.inflate(R.layout.viewstub_listview, viewHolder.frame, false);

                      viewHolder.frame.removeAllViewsInLayout();
                      viewHolder.frame.addView(header, 0);
                      viewHolder.frame.addView(view, 1);

                      ResizedHeightAnimation rlHeaderHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlHeader, ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height ? height : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
                      rlHeaderHeightAnimation.setDuration(0);
                      ((TableContentsActivity) activity).rlHeader.startAnimation(rlHeaderHeightAnimation);

                      ResizedHeightAnimation viewPagerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).viewPager, ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
                      viewPagerHeightAnimation.setDuration(0);
                      viewPagerHeightAnimation.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                          Handler handler = new Handler();
                          handler.postDelayed(() -> {

                            mBaseRecyclerViewHolder.headerLayout.setBackgroundColor(darkerRgb);
                            mBaseRecyclerViewHolder.faith.setTextColor(Color.WHITE);
                            mBaseRecyclerViewHolder.number.setTextColor(Color.WHITE);
                            mBaseRecyclerViewHolder.arrow.setColor(Color.WHITE);
                            rotate(mBaseRecyclerViewHolder.arrow, -180, 200);

                            ResizedHeightAnimation animationFrame12 = new ResizedHeightAnimation(mBaseRecyclerViewHolder.frame,
                                    (mMeasuredHeight + (measuredHeight * (objectSize))));
                            animationFrame12.setDuration(objectSize * duration);
                            animationFrame12.setAnimationListener(new Animation.AnimationListener() {
                              @Override
                              public void onAnimationStart(Animation animation) {

                              }

                              @Override
                              public void onAnimationEnd(Animation animation) {
                                mBaseRecyclerViewHolder.isAnimating = false;
                                viewHolder.isAnimating = false;
                              }

                              @Override
                              public void onAnimationRepeat(Animation animation) {

                              }
                            });
                            mBaseRecyclerViewHolder.frame.startAnimation(animationFrame12);
                            mBaseRecyclerViewHolder.isExpanded = true;

                            if (duration != 0) {
                              editor.putString(modelsName,
                                      mBaseListViewHolder.position + " - " + mParentBaseRecyclerViewHolder.position
                                              + " - " + mBaseRecyclerViewHolder.position);
                              editor.commit();
                            }

                            ResizedHeightAnimation animationView = new ResizedHeightAnimation(mBaseRecyclerViewHolder.baseRecyclerView,
                                    (measuredHeight * (objectSize)));
                            animationView.setDuration(objectSize * duration);
                            mBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationView);

                            if (mBaseRecyclerViewHolder.baseRecyclerView.getMeasuredHeight() == 0) {
                              ResizedHeightAnimation animationRecyclerView = new ResizedHeightAnimation(mBaseRecyclerViewHolder.baseRecyclerView,
                                      (measuredHeight * (objectSize)));
                              animationRecyclerView.setDuration(objectSize * duration);
                              mBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationRecyclerView);
                            }

                            ResizedHeightAnimation animationParentFrameView = new ResizedHeightAnimation(mParentBaseRecyclerViewHolder.frame,
                                    ((mMeasuredHeight * (mParentBaseRecyclerViewHolder.baseRecyclerView.getChildCount() + 1)) + measuredHeight * (objectSize)));
                            animationParentFrameView.setDuration(objectSize * duration);
                            mParentBaseRecyclerViewHolder.frame.startAnimation(animationParentFrameView);

                            ResizedHeightAnimation animationParentRecyclerView = new ResizedHeightAnimation(mParentBaseRecyclerViewHolder.baseRecyclerView,
                                    (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getChildCount() + measuredHeight * (objectSize)));
                            animationParentRecyclerView.setDuration(objectSize * duration);
                            mParentBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationParentRecyclerView);

                            ResizedHeightAnimation animationBaseListViewHolder = new ResizedHeightAnimation(mBaseListViewHolder.frame,
                                    ((mMeasuredHeight * (mParentBaseRecyclerViewHolder.baseRecyclerView.getChildCount() + mBaseListViewHolder.baseRecyclerView.getChildCount() + 1)) + (measuredHeight * (objectSize))));

                            animationBaseListViewHolder.setDuration(objectSize * duration);
                            mBaseListViewHolder.frame.startAnimation(animationBaseListViewHolder);

                            ResizedHeightAnimation animationRecyclerView = new ResizedHeightAnimation(mBaseListViewHolder.baseRecyclerView,
                                    ((mMeasuredHeight * (mParentBaseRecyclerViewHolder.baseRecyclerView.getChildCount() + mBaseListViewHolder.baseRecyclerView.getChildCount())) + (measuredHeight * (objectSize))));

                            animationRecyclerView.setDuration(objectSize * duration);
                            mBaseListViewHolder.baseRecyclerView.startAnimation(animationRecyclerView);
                          }, 200);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                      });

                      ((TableContentsActivity) activity).viewPager.startAnimation(viewPagerHeightAnimation);

                      ResizedHeightAnimation rlContainerHeightAnimation = new ResizedHeightAnimation(((TableContentsActivity) activity).rlContainer, ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + TableContentsActivity.headerHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));
                      rlContainerHeightAnimation.setDuration(0);
                      ((TableContentsActivity) activity).rlContainer.startAnimation(rlContainerHeightAnimation);

                      ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(mBaseListAdapter.getBaseListView(), ((int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))) < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : (int) ((mBaseListAdapter.getBaseListView().getCount() + 1) * mMeasuredHeight + (mMeasuredHeight * 2 + ((mMeasuredHeight * (mBaseListViewHolder.baseRecyclerView.getAdapter().holders.size())) + (mMeasuredHeight * mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().holders.size()) + (measuredHeight * (objectSize))) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)))));

                      baseListviewHeightAnimation.setDuration(500);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                  });
                  animationFrame.setDuration((t /*+ adapter.holders.size()*/) * duration);
                  viewHolder.frame.startAnimation(animationFrame);
                  viewHolder.isExpanded = false;
                }
              }
            }
          }
        }
      } else if (duration == 0) {

        mBaseRecyclerViewHolder.headerLayout.setBackgroundColor(darkerRgb);
        mBaseRecyclerViewHolder.faith.setTextColor(Color.WHITE);
        mBaseRecyclerViewHolder.number.setTextColor(Color.WHITE);
        mBaseRecyclerViewHolder.arrow.setColor(Color.WHITE);
        rotate(mBaseRecyclerViewHolder.arrow, -180, duration);

        mBaseRecyclerViewHolder.isExpanded = true;

        if (mBaseRecyclerViewHolder.baseRecyclerView != null) {
          ResizedHeightAnimation animationRecyclerFrame = new ResizedHeightAnimation(mBaseRecyclerViewHolder.baseRecyclerView,
                  (objectSize * measuredHeight));
          animationRecyclerFrame.setDuration((objectSize + t) * duration);
          mBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationRecyclerFrame);
          ResizedHeightAnimation animationRecyclerView = new ResizedHeightAnimation(mBaseRecyclerViewHolder.frame,
                  mMeasuredHeight + (objectSize * measuredHeight)
          );
          animationRecyclerView.setDuration((objectSize + t) * duration);
          mBaseRecyclerViewHolder.frame.startAnimation(animationRecyclerView);
        }

        ResizedHeightAnimation animationParentRecyclerFrame = new ResizedHeightAnimation(mParentBaseRecyclerViewHolder.baseRecyclerView,
                ((mMeasuredHeight *
                        mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().objects.size()))
                        + (objectSize * measuredHeight));
        animationParentRecyclerFrame.setDuration((objectSize + t) * duration);
        mParentBaseRecyclerViewHolder.baseRecyclerView.startAnimation(animationParentRecyclerFrame);

        ResizedHeightAnimation animationParentRecyclerView = new ResizedHeightAnimation(mParentBaseRecyclerViewHolder.frame,
                (mMeasuredHeight *
                        (mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().objects.size() + 1))
                        + (objectSize * measuredHeight));
        animationParentRecyclerFrame.setDuration((objectSize + t) * duration);

        mParentBaseRecyclerViewHolder.frame.startAnimation(animationParentRecyclerView);
        ResizedHeightAnimation animationFrame = new ResizedHeightAnimation(mBaseListViewHolder.baseRecyclerView,
                (mMeasuredHeight *
                        (mBaseListViewHolder.baseRecyclerView.getAdapter().objects.size()
                                + mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().objects.size()) +
                        (objectSize * measuredHeight)));
        animationFrame.setDuration((objectSize + t) * duration);
        mBaseListViewHolder.baseRecyclerView.startAnimation(animationFrame);

        ResizedHeightAnimation animationView = new ResizedHeightAnimation(mBaseListViewHolder.frame,
                (mMeasuredHeight *
                        (mBaseListViewHolder.baseRecyclerView.getAdapter().objects.size()
                                + mParentBaseRecyclerViewHolder.baseRecyclerView.getAdapter().objects.size() + 1)
                        +
                        (objectSize * measuredHeight)));
        animationView.setDuration((objectSize + t) * duration);
        mBaseListViewHolder.frame.startAnimation(animationView);
      }
    }
  }
}
