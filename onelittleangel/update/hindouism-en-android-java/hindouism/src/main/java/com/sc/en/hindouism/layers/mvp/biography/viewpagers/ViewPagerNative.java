package com.sc.en.hindouism.layers.mvp.biography.viewpagers;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import com.sc.en.hindouism.layers.mvp.biography.BiographyActivity;
import com.sc.en.hindouism.layers.mvp.biography.viewpagers.adapter.BiographyFragmentPagerAdapter;
import com.sc.en.hindouism.layers.mvp.common.customs.viewpagers.scrollers.ScrollerDuration;

import java.lang.reflect.Field;

public class ViewPagerNative extends ViewPager {

  //  private final Context mContext;
  public int position = 0;
  private boolean isPagingEnabled = true;
  private ScrollerDuration mScroller;
  public BiographyFragmentPagerAdapter adapter;

  public ViewPagerNative(Context context) {
    super(context);
    postInitViewPager();
  }

  public ViewPagerNative(Context context, AttributeSet attrs) {
    super(context, attrs);
    postInitViewPager();
  }

  public void updateCallBacks(BiographyActivity activity) {

  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return isPagingEnabled && super.onTouchEvent(event);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    return isPagingEnabled && super.onInterceptTouchEvent(event);
  }

  //@Override
  public void setAdapter(BiographyFragmentPagerAdapter adapter) {
    super.setAdapter(adapter);
    this.adapter = adapter;
  }

  public void setPagingEnabled() {
    isPagingEnabled = false;
  }

  public boolean isPagingEnabled() {
    return isPagingEnabled;
  }

  /**
     * Override the Scroller instance with our own class so we can change the
     * duration
     */
    private void postInitViewPager() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = viewpager.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new ScrollerDuration(getContext(),
                    (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
        } catch (Exception ignored) {
        }
    }

    /**
     * Set the factor by which the duration will change
     */
    public void setScrollDurationFactor(double scrollFactor) {
        if(mScroller == null){
          postInitViewPager();
        }
        mScroller.setScrollDurationFactor(scrollFactor);

    }

  //@Override
  public BiographyFragmentPagerAdapter getAdapter() {
    return adapter;
  }

}
