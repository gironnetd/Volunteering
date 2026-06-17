package com.sc.fr.onelittleangel.layers.mvp.common.customs.textviews;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.widget.TextViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.TextView;

import com.sc.fr.onelittleangel.OnelittleAngelApplication;
import com.sc.fr.onelittleangel.exceptions.PersonalRuntimeException;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.textviews.justifiedtextview.JustifiedTextView;
import com.sc.fr.onelittleangel.layers.mvp.common.utils.Constants;
import com.sc.fr.onelittleangel.layers.mvp.tablecontents.TableContentsActivity;

//import com.uncopt.android.widget.text.justify.JustifiedTextView;

public class ContentTextViewNativeBiography extends JustifiedTextView {

  // Context context;

  private Callbacks mCallbacks;

  private Typeface typeface;
  private  GestureDetector mCommonGestureDetector;
  private  ScaleGestureDetector mScaleGestureDetector;
  private SharedPreferences settings;
  private static float size;
  //public int lineCount = 0;

  public ContentTextViewNativeBiography(Context context) {
    super(context, null);
    //  this.context = context;
    // isInEditMode();

    if (!isInEditMode()) {
    //  init(context);
//      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
//
//      if(settings.getString(Constants.TYPEFACE, "").equals("")) {
//        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/IM_Fell_English.ttf");
//      } else {
//        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + settings.getString(Constants.TYPEFACE, "") + ".ttf");
//      }
//      setTypeface(typeface);
//      setTextColor(Color.BLACK);
//    //      setMaxLines(6000);
//    //      ViewGroup.LayoutParams params = getLayoutParams();
//    //      params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//    //      params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//    //      setLayoutParams(params);
//
//    //  setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//      if(settings.getFloat(Constants.TEXTSIZE, 0) != 0) {
//        setTextSize(TypedValue.COMPLEX_UNIT_PX,settings.getFloat(Constants.TEXTSIZE, 0));
//      }
    //  setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
      init(context);
      mCommonGestureDetector = new GestureDetector(context, new GestureListener());
      mScaleGestureDetector = new ScaleGestureDetector(context, new SimpleOnScaleGestureListener());

      //  scaleGestureDetector = new ScaleGestureDetector(context, new simpleOnScaleGestureListener());
    }
  }

  public ContentTextViewNativeBiography(Context context, String tpString) {
    super(context, null);
  //  this.typeface = typeface;
    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

    if (!isInEditMode()) {
//      if(settings.getString(Constants.TYPEFACE, "").equals("")) {
//        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/IM_Fell_English.ttf");
//      } else {
//        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + settings.getString(Constants.TYPEFACE, "") + ".ttf");
//      }
//      setTypeface(typeface);
//    //  setMaxLines(6000);
//
//      if(settings.getFloat(Constants.TEXTSIZE, 0) != 0) {
//        setTextSize(TypedValue.COMPLEX_UNIT_PX,settings.getFloat(Constants.TEXTSIZE, 0));
//      }
      init(context);
      mCommonGestureDetector = new GestureDetector(context, new GestureListener());
      mScaleGestureDetector = new ScaleGestureDetector(context, new SimpleOnScaleGestureListener());
    }
  }

  public ContentTextViewNativeBiography(Context context, AttributeSet attrs) {
    super(context, attrs);
    // this.context = context;
    // isInEditMode();

    if (!isInEditMode()) {
      init(context);
      mCommonGestureDetector = new GestureDetector(context, new GestureListener());
      mScaleGestureDetector = new ScaleGestureDetector(context, new SimpleOnScaleGestureListener());
    }

  }

  public ContentTextViewNativeBiography(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs);
    // this.context = context;
    // isInEditMode();

    if (!isInEditMode()) {
      init(context);
      mCommonGestureDetector = new GestureDetector(context, new GestureListener());
      mScaleGestureDetector = new ScaleGestureDetector(context, new SimpleOnScaleGestureListener());
    }
  }

  private void init(Context context) {
    if (!isInEditMode()) {
      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

      if (!isInEditMode()) {
        if (settings.getString(Constants.TYPEFACE, "").equals("")) {
          typeface = Typeface.createFromAsset(context.getAssets(), "fonts/IM_Fell_English.ttf");
        } else {
          typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + settings.getString(Constants.TYPEFACE, "") + ".ttf");
        }

        setTypeface(typeface);
        setTextColor(Color.BLACK);
        //setPadding(6, 0, 6, 0);
        //  setMaxLines(6000);
        if (settings.getFloat(Constants.TEXTSIZE, 0) != 0) {
          setTextSize(TypedValue.COMPLEX_UNIT_PX, settings.getFloat(Constants.TEXTSIZE, 0));
        }
      }
    }
  }

  //@Override
  public void setText(CharSequence text, boolean b) {
    setText(text);
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
      // TODO Auto-generated method stub
      size = getTextSize();
      Log.d("TextSizeStart", String.valueOf(size));



      float factor = detector.getScaleFactor();
      Log.d("Factor", String.valueOf(factor));

      if (size >= 180 && factor > 1) return false;

      float product = size*factor;
    //  Log.d("TextSize", String.valueOf(product));
      setTextSize(TypedValue.COMPLEX_UNIT_PX, product);

      size = getTextSize();
    //  Log.d("TextSizeEnd", String.valueOf(size));
    //  if(getId() == R.id.textview || getId() == R.id.source ||  getId() == R.id.txtn_presentation_title)
      mCallbacks.onScaling(size, getId());
      return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    //  super.onScaleEnd(detector);
      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      SharedPreferences.Editor editor = settings.edit();
      editor.putFloat(Constants.TEXTSIZE, size);
//editor.commit();
      editor.apply();      if(mCallbacks != null) mCallbacks.onScaleEnd(size,getId());
    }
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


  public final class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private static final int SLIDE_THRESHOLD = 1;

    private float x;
    private float y;
    private int i;
    // public boolean movingDetected = false;

    @Override
    public boolean onDown(MotionEvent e) {
      //  Toast.makeText(context, "onDown : " + e.getX() + " : " +  e.getY(), Toast.LENGTH_SHORT).show();
      x = e.getXPrecision();
      y = e.getYPrecision();
      //  onDownPressed();
      return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

      try {
        float deltaY = e2.getY() - e1.getY();
        float deltaX = e2.getX() - e1.getX();

        // if (Math.abs(deltaX) > Math.abs(deltaY)) {
        //    if (Math.abs(deltaX) > SLIDE_THRESHOLD) {


        if (Float.floatToRawIntBits(deltaX) != 0 ||
          Float.floatToRawIntBits(deltaY) != 0) {
          // movingDetected = true;
          if (i == 0) {
            //  onLongPressed();
            i++;
            return false;
          } else {
            i++;
            return false;
          }
        }
      } catch (Exception exception) {
        Log.e("ERROR", exception.getMessage());
        throw new PersonalRuntimeException(exception);
      }
      // onClickUp();
      return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

//      if (Float.floatToRawIntBits(e.getXPrecision()) != Float.floatToRawIntBits(x)
//        || Float.floatToRawIntBits(e.getYPrecision()) != Float.floatToRawIntBits(y)) {
//        //  Toast.makeText(context, "deplacement detecte ", Toast.LENGTH_SHORT).show();
//      //  movingDetected = true;
//      } else {
//      //  onShowPressed();
//      }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
      if(mCallbacks != null) mCallbacks.onSingleTapConfirm();
      return super.onSingleTapConfirmed(e);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
      if(mCallbacks != null) mCallbacks.onSingleTapUped();
      return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

      if(mCallbacks != null) mCallbacks.onDoubleTaped();
      return super.onDoubleTap(e);
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
      if(mCallbacks != null) mCallbacks.onLongPressed();
    }

    // Determines the fling velocity and then fires the appropriate swipe event accordingly
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
      if(mCallbacks != null) mCallbacks.onFlingGesture();
      return false;
    }
  }


  public GestureDetector getmCommonGestureDetector() {
    return mCommonGestureDetector;
  }

  public void setmCommonGestureDetector(GestureDetector mCommonGestureDetector) {
    this.mCommonGestureDetector = mCommonGestureDetector;
  }

  public ScaleGestureDetector getmScaleGestureDetector() {
    return mScaleGestureDetector;
  }

  public void setmScaleGestureDetector(ScaleGestureDetector mScaleGestureDetector) {
    this.mScaleGestureDetector = mScaleGestureDetector;
  }
}

