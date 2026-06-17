package com.sc.fr.islam.layers.mvp.common.customs.scrollviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {

  private Callbacks mCallbacks;

  private boolean enableScrolling = true;
  static final int MIN_DISTANCE = 5;
  private float downX, downY, upX, upY;

  private boolean isEnableScrolling() {
    return enableScrolling;
  }

  public void setEnableScrolling(boolean enableScrolling) {
    this.enableScrolling = enableScrolling;
  }

  public ObservableScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }



  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if (mCallbacks != null) {
      mCallbacks.onScrollChanged(t);
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {


    if (mCallbacks != null && isEnableScrolling()) {
      switch (ev.getActionMasked()) {
        case MotionEvent.ACTION_DOWN:
          mCallbacks.onDownMotionEvent();
          break;
        case MotionEvent.ACTION_MOVE:
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
          mCallbacks.onUpOrCancelMotionEvent();
          break;
      }
      return super.onTouchEvent(ev);
    } else {
      return false;
    }
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {

    return isEnableScrolling() && super.onInterceptTouchEvent(ev);
  }

  @Override
  public int computeVerticalScrollRange() {
    return super.computeVerticalScrollRange();
  }

  public void setCallbacks(Callbacks listener) {
    mCallbacks = listener;
  }

  public interface Callbacks {

    void onScroll(float deltaY);

    void onScrollChanged(int scrollY);

    void onDownMotionEvent();

    void onUpOrCancelMotionEvent();
  }

}
