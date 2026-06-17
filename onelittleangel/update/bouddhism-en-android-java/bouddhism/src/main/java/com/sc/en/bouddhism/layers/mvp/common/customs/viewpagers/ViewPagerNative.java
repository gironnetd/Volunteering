package com.sc.en.bouddhism.layers.mvp.common.customs.viewpagers;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import com.sc.en.bouddhism.layers.mvp.common.customs.viewpagers.fragmentpageradapter.BaseFragmentAdapter;
import com.sc.en.bouddhism.layers.mvp.common.customs.viewpagers.scrollers.ScrollerDuration;

import java.lang.reflect.Field;

public class ViewPagerNative extends ViewPager {

  //  private final Context mContext;
  public int position = 0;
  private boolean isPagingEnabled = true;
  private ScrollerDuration mScroller;
  public BaseFragmentAdapter adapter;

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
    try {
      return isPagingEnabled && super.onTouchEvent(event);
    } catch (IllegalArgumentException ignored) {

    }
    return isPagingEnabled;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {

    try {
      return isPagingEnabled && super.onInterceptTouchEvent(event);
    } catch (IllegalArgumentException ignored) {

    }
    return isPagingEnabled;
  }

  public void setPagingEnabled(boolean b) {
    isPagingEnabled = b;
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

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        // find the first child view
//        View view = getChildAt(0);
//        if (view != null) {
//            // measure the first child view with the specified measure spec
//            view.measure(widthMeasureSpec, heightMeasureSpec);
//        }
//
//        setMeasuredDimension(getMeasuredWidth(), measureHeight(heightMeasureSpec, view));
//    }
//
//    /**
//     * Determines the height of this view
//     *
//     * @param measureSpec A measureSpec packed into an int
//     * @param view the base view with already measured height
//     *
//     * @return The height of the view, honoring constraints from measureSpec
//     */
//    private int measureHeight(int measureSpec, View view) {
//        int result = 0;
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//
//        if (specMode == MeasureSpec.EXACTLY) {
//            result = specSize;
//        } else {
//            // set the height from the base view if available
//            if (view != null) {
//                result = view.getMeasuredHeight();
//            }
//            if (specMode == MeasureSpec.AT_MOST) {
//                result = Math.min(result, specSize);
//            }
//        }
//        return result;
//    }

}
