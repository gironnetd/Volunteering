package com.sc.fr.islam.layers.mvp.common.customs.viewpagers.scrollers;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class ScrollerDuration extends Scroller{

  private double mScrollFactor = 10;

  public ScrollerDuration(Context context) {
    super(context);
  }

  public ScrollerDuration(Context context, Interpolator interpolator) {
    super(context, interpolator);
  }

  public ScrollerDuration(Context context, Interpolator interpolator, boolean flywheel) {
    super(context, interpolator, flywheel);
  }

  /**
   * Set the factor by which the duration will change
   */
  public void setScrollDurationFactor(double scrollFactor) {
    mScrollFactor = scrollFactor;
  }

  @Override
  public void startScroll(int startX, int startY, int dx, int dy, int duration) {
    super.startScroll(startX, startY, dx, dy, (int) (duration * mScrollFactor));
  }

}
