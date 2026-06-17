package com.sc.fr.confucianisme.layers.mvp.common.layouts.linearlayouts;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.LinearLayout;

import com.sc.fr.confucianisme.OnelittleAngelApplication;
import com.sc.fr.confucianisme.layers.mvp.common.customs.textviews.ContentTextViewNative;
import com.sc.fr.confucianisme.layers.mvp.common.utils.Constants;
import com.sc.fr.confucianisme.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.fr.confucianisme.exceptions.PersonalRuntimeException;

public class PersonalLinearLayout extends LinearLayout {

  private Callbacks mCallbacks;

  private  GestureDetector mCommonGestureDetector;
  private ScaleGestureDetector mScaleGestureDetector;
  private SharedPreferences settings;
  private final Context context;
  private Activity activity;

  public PersonalLinearLayout(Context context) {
    super(context);
    this.context = context;
  }

  public PersonalLinearLayout(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    this.context = context;


    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    //editor = settings.edit();

    mCommonGestureDetector = new GestureDetector(context, new GestureListener());
    mScaleGestureDetector = new ScaleGestureDetector(context, new SimpleOnScaleGestureListener());
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public interface Callbacks {

    void onLongPressed();

    void onSingleTapUped();

    void onFlingGesture();

    void onSingleTapConfirm();

    void onDoubleTaped();

    void onScaling(float size, int resId);

    void onScaleEnd(float size, int resId);
  }

  public void setCallbacks(Callbacks listener) {
    mCallbacks = listener;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    // TODO Auto-generated method stub
    boolean result = mScaleGestureDetector.onTouchEvent(event);

    // result is always true here, so I need another way to check for a detected scaling gesture
    boolean isScaling = result = mScaleGestureDetector.isInProgress();
    if (!isScaling) {
      // if no scaling is performed check for other gestures (fling, long tab, etc.)
      result = mCommonGestureDetector.onTouchEvent(event);
    }

    // some irrelevant checks...
    return result ? result : super.onTouchEvent(event);
  }

  private class SimpleOnScaleGestureListener extends
    ScaleGestureDetector.SimpleOnScaleGestureListener {

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
      ContentTextViewNative.size = settings.getFloat(Constants.TEXTSIZE, 0);

      float factor = detector.getScaleFactor();

      float product = ContentTextViewNative.size*factor;
      DisplayMetrics displaymetrics = new DisplayMetrics();
      activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
      ContentTextViewNative.size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, product, displaymetrics);
      settings.edit().putFloat(Constants.TEXTSIZE, ContentTextViewNative.size).apply();
      //settings.edit().commit();

      mCallbacks.onScaling(ContentTextViewNative.size, getId());
      return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
      if(mCallbacks != null) mCallbacks.onScaleEnd(ContentTextViewNative.size,getId());
    }
  }

  public final class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private static final int SLIDE_THRESHOLD = 1;

    private float x;
    private float y;
    private int i;

    @Override
    public boolean onDown(MotionEvent e) {
      x = e.getXPrecision();
      y = e.getYPrecision();
      return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

      try {
        float deltaY = e2.getY() - e1.getY();
        float deltaX = e2.getX() - e1.getX();

        if (Float.floatToRawIntBits(deltaX) != 0 ||
          Float.floatToRawIntBits(deltaY) != 0) {
          // movingDetected = true;
          if (i == 0) {
            i++;
            return false;
          } else {
            i++;
            return false;
          }
        }
      } catch (Exception exception) {
        //Log.e("ERROR", exception.getMessage());
        throw new PersonalRuntimeException(exception);
      }
      return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
      mCallbacks.onSingleTapConfirm();
      return super.onSingleTapConfirmed(e);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
      mCallbacks.onSingleTapUped();
      return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

      mCallbacks.onDoubleTaped();
      return super.onDoubleTap(e);
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
      mCallbacks.onLongPressed();
    }

    // Determines the fling velocity and then fires the appropriate swipe event accordingly
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
      mCallbacks.onFlingGesture();
      return false;
    }
  }
}
