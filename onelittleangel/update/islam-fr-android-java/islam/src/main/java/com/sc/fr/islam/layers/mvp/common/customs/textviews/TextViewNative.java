package com.sc.fr.islam.layers.mvp.common.customs.textviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ScaleGestureDetector;

public class TextViewNative extends android.support.v7.widget.AppCompatTextView {

  // Context context;

  public Typeface typeface;
  ScaleGestureDetector scaleGestureDetector;

  public TextViewNative(Context context) {
    super(context, null);
    //  this.context = context;
    // isInEditMode();

    if (!isInEditMode()) {
      init(context);
    //  scaleGestureDetector = new ScaleGestureDetector(context, new simpleOnScaleGestureListener());
    }
  }

  public TextViewNative(Context context, String tpString) {
    super(context, null);
  //  this.typeface = typeface;

    if (!isInEditMode()) {
      if(tpString == null) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Monotype-Corsiva-Regular.ttf");
      } else {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + tpString + ".ttf");
      }
      setTypeface(typeface);
     // scaleGestureDetector = new ScaleGestureDetector(context, new simpleOnScaleGestureListener());
    }
  }

  public TextViewNative(Context context, AttributeSet attrs) {
    super(context, attrs);
    // this.context = context;
    // isInEditMode();

    if (!isInEditMode()) {
      init(context);
    //  scaleGestureDetector = new ScaleGestureDetector(context, new simpleOnScaleGestureListener());
    }

  }

  public TextViewNative(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs);
    // this.context = context;
    // isInEditMode();

    if (!isInEditMode()) {
      init(context);
    //  scaleGestureDetector = new ScaleGestureDetector(context, new simpleOnScaleGestureListener());
    }

  }

  private void init(Context context) {
    if (!isInEditMode()) {
      if(typeface == null) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Monotype-Corsiva-Regular.ttf");
      }
      setTypeface(typeface);
    }
  }

//  @Override
//  public boolean onTouchEvent(MotionEvent event) {
//    // TODO Auto-generated method stub
//    scaleGestureDetector.onTouchEvent(event);
//    return true;
//  }

  private class simpleOnScaleGestureListener extends
    ScaleGestureDetector.SimpleOnScaleGestureListener {

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
      // TODO Auto-generated method stub
      float size = getTextSize();
      //Log.d("TextSizeStart", String.valueOf(size));

      float factor = detector.getScaleFactor();
      //Log.d("Factor", String.valueOf(factor));


      float product = size*factor;
      //Log.d("TextSize", String.valueOf(product));
      setTextSize(TypedValue.COMPLEX_UNIT_PX, product);

      size = getTextSize();
      //Log.d("TextSizeEnd", String.valueOf(size));
      return true;
    }
  }

}
