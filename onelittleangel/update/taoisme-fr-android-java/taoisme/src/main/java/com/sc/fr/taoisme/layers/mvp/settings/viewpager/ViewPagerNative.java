package com.sc.fr.taoisme.layers.mvp.settings.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import com.sc.fr.taoisme.layers.mvp.settings.fragments.SettingsFragmentAdapter;
import com.sc.fr.taoisme.layers.mvp.common.customs.viewpagers.scrollers.ScrollerDuration;

import java.lang.reflect.Field;

public class ViewPagerNative extends ViewPager {

  //  private final Context mContext;
  public int position = 0;
  private boolean isPagingEnabled = true;
  private ScrollerDuration mScroller;
  public SettingsFragmentAdapter adapter;

  public ViewPagerNative(Context context) {
    super(context);
    postInitViewPager();
  }

  public ViewPagerNative(Context context, AttributeSet attrs) {
    super(context, attrs);
    postInitViewPager();
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return isPagingEnabled && super.onTouchEvent(event);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    return isPagingEnabled && super.onInterceptTouchEvent(event);
  }

  public void setPagingEnabled(boolean b) {
    isPagingEnabled = b;
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
    public void setScrollDurationFactor() {
        if(mScroller == null){
          postInitViewPager();
        }
        mScroller.setScrollDurationFactor((double) 1);

    }

//  @Override
//  public void setCurrentItem(int item) {
//    super.setCurrentItem(item);
//    if(oldPosition == 0) {
//      adapter.getFragments().get(item).baseListView.setVisibility(VISIBLE);
//      adapter.getFragments().get(item).setBottomAdapter();
//      oldPosition++;
//    }
//  }


}
