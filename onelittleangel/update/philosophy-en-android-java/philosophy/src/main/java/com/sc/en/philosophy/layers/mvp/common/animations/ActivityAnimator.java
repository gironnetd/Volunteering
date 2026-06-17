package com.sc.en.philosophy.layers.mvp.common.animations;

import android.app.Activity;
import android.content.Context;

import com.sc.en.philosophy.R;

public class ActivityAnimator {

  public void fadeAnimation(Context a) {
//    new Handler().postDelayed(new Runnable()
//    {
//      @Override
//      public void run()
//      {
    ((Activity)a).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//      }
//    }, 1000);
  }

  public void fadeAnimationToTableContents(Context a) {
    ((Activity)a).overridePendingTransition(R.anim.fade_in_to_tablecontents, R.anim.fade_out_to_tablecontents);
  }

  public void zoomAnimation(Context a){
    ((Activity) a).overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
  }

  public void slideAnimation(Context a){
    ((Activity) a).overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
  }
}
