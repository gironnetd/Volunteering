package com.sc.en.taoism.layers.mvp.tablecontents.fragments.homepage.views.cardviews;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.sc.en.taoism.OnelittleAngelApplication;
import com.sc.en.taoism.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.taoism.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.en.taoism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.taoism.R;
import com.sc.en.taoism.layers.mvp.common.customs.textviews.ContentTextViewNativeBiography;

public class HomePageCardView extends CardView {

  @LayoutRes
  private
  int cardLayoutResourceID = R.layout.homepage_card_layout;


  private Drawable mSampleImage;
  private final Context mContext;
  private float mPosX;
  private float mPosY;
  protected float mPosX0 = 0;     // initial displacement values
  protected float mPosY0 = 0;

  private float mLastTouchX;
  private float mLastTouchY;

  private static final int INVALID_POINTER_ID = -1;

  // The ‘active pointer’ is the one currently moving our object.
  private int mActivePointerId = INVALID_POINTER_ID;

  private ScaleGestureDetector mScaleDetector ;

  private float mScaleFactor = 1.f;

  private final boolean mSupportsZoom = true;
  protected boolean mSupportsScaleAtFocus = true;

  public HomePageCardView(Context context) {
    super(OnelittleAngelApplication.instance.getApplicationContext());
    mContext = context;
    init(null, 0);
  }

  public HomePageCardView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;
    init(attrs, 0);

  }

  public HomePageCardView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mContext = context;
    init(attrs, defStyleAttr);
  }

  //--------------------------------------------------------------------------
  // Init
  //--------------------------------------------------------------------------

  private void init(AttributeSet attrs, int defStyle) {
    //Init attrs
    initAttrs(attrs, defStyle);
  }

  private void initAttrs(AttributeSet attrs, int defStyle) {

    cardLayoutResourceID = R.layout.homepage_card_layout;

    TypedArray a = getContext().getTheme().obtainStyledAttributes(
      attrs, R.styleable.card_options, defStyle, defStyle);

    try {
      cardLayoutResourceID = a.getResourceId(R.styleable.card_options_card_layout_resourceID, cardLayoutResourceID);
    } finally {
      a.recycle();
      initView();
    }
  }

  private void initView() {

    //Inflate outer view
    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View mInternalOuterView = inflater.inflate(cardLayoutResourceID, this, true);

    TextViewNative title = (TextViewNative) mInternalOuterView.findViewById(R.id.homepage_card_title);
    ImageView pictureOfDay = (ImageView) mInternalOuterView.findViewById(R.id.picture_of_day);
    ContentTextViewNativeBiography biographyOfDay = (ContentTextViewNativeBiography) mInternalOuterView.findViewById(R.id.biography_of_day);
    TextViewNative name = (TextViewNative) mInternalOuterView.findViewById(R.id.name);
    ContentTextViewNativeBiography quoteOfDay = (ContentTextViewNativeBiography) mInternalOuterView.findViewById(R.id.quote_of_day);
    TextViewNative link = (TextViewNative) mInternalOuterView.findViewById(R.id.link);

    SharedPreferences settings = getContext().getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

    link.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

  }

  public void setPosition(int position) {
    initView();
  }

  private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
      if (!mSupportsZoom) return true;
      mScaleFactor *= detector.getScaleFactor();

      // Don't let the object get too small or too large.
      mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
      float mFocusX = detector.getFocusX();
      float mFocusY = detector.getFocusY();

      invalidate();
      return true;
    }
  }

  public void drawOnCanvas (Canvas canvas) {
    mSampleImage.draw(canvas);
  }

  /**
   * Handle touch and multitouch events so panning and zooming can be supported.
   *
   */

  @Override public boolean onTouchEvent(MotionEvent ev) {

    // If we are not supporting either zoom or pan, return early.
    boolean mSupportsPan = true;
    if (!mSupportsZoom && !mSupportsPan) return false;
    if(mScaleDetector == null)
      mScaleDetector = new ScaleGestureDetector(mContext, new ScaleListener());
    // Let the ScaleGestureDetector inspect all events.
    mScaleDetector.onTouchEvent(ev);

    final int action = ev.getAction();
    switch (action & MotionEvent.ACTION_MASK) {
      case MotionEvent.ACTION_DOWN: {
        final float x = ev.getX();
        final float y = ev.getY();

        mLastTouchX = x;
        mLastTouchY = y;
        mActivePointerId = ev.getPointerId(0);
        break;
      }

      case MotionEvent.ACTION_MOVE: {
        final int pointerIndex = ev.findPointerIndex(mActivePointerId);
        final float x = ev.getX(pointerIndex);
        final float y = ev.getY(pointerIndex);

        // Only move if the view supports panning and
        // ScaleGestureDetector isn't processing a gesture.
        if (mSupportsPan && !mScaleDetector.isInProgress()) {
          final float dx = x - mLastTouchX;
          final float dy = y - mLastTouchY;

          mPosX += dx;
          mPosY += dy;
          invalidate();
        }

        mLastTouchX = x;
        mLastTouchY = y;

        break;
      }

      case MotionEvent.ACTION_UP: {
        mActivePointerId = INVALID_POINTER_ID;
        break;
      }

      case MotionEvent.ACTION_CANCEL: {
        mActivePointerId = INVALID_POINTER_ID;
        break;
      }

      case MotionEvent.ACTION_POINTER_UP: {
        final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
          >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == mActivePointerId) {
          // This was our active pointer going up. Choose a new
          // active pointer and adjust accordingly.
          final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
          mLastTouchX = ev.getX(newPointerIndex);
          mLastTouchY = ev.getY(newPointerIndex);
          mActivePointerId = ev.getPointerId(newPointerIndex);
        }
        break;
      }
    }

    return true;
  }

}
