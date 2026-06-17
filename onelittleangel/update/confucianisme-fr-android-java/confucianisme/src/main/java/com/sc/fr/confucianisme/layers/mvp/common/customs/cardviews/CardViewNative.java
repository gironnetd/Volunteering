package com.sc.fr.confucianisme.layers.mvp.common.customs.cardviews;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.fr.confucianisme.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.fr.confucianisme.R;

public class CardViewNative extends CardView {

  private static final int UNBOUNDED = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
  public static final String MCARDVIEWMEASUREDHEIGHT = "mCardViewMeasuredHeight";
  public static final String MCONTENTCARDVIEWMEASUREDHEIGHT = "mContentCardViewMeasuredHeight";
  public static final String DARKERRGB = "darkerRgb";
  private int width;
  private int height;
  private int dislpayHeight = 0;
  //SharedPreferences.Editor editor;
  private int widthMeasureSpec;
  private int heightMeasureSpec;
  private Context context;

  @LayoutRes
  private int cardLayoutResourceID = R.layout.card_layout;
  private View mInternalOuterView;
  SharedPreferences settings;
  private SharedPreferences.Editor editor;


  public CardViewNative(Context context) {
    super(context);
    this.context = context;
  }

  public CardViewNative(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0);
  }

  public CardViewNative(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs, defStyleAttr);
  }

  //--------------------------------------------------------------------------
  // Init
  //--------------------------------------------------------------------------

  private void init(AttributeSet attrs, int defStyle) {
    //Init attrs
    initAttrs(attrs, defStyle);

    //Init view
    if (!isInEditMode())
      initView();
  }

  private void initAttrs(AttributeSet attrs, int defStyle) {

    cardLayoutResourceID = R.layout.card_layout;
    TypedArray a = getContext().getTheme().obtainStyledAttributes(
            attrs, R.styleable.card_options, defStyle, defStyle);
    try {
      cardLayoutResourceID = a.getResourceId(R.styleable.card_options_card_layout_resourceID, cardLayoutResourceID);
    } finally {
      a.recycle();
    }
  }

  private void initView() {

    SharedPreferences settings = getContext().getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    editor = settings.edit();
    //Inflate outer view
    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mInternalOuterView = inflater.inflate(cardLayoutResourceID, this, true);



    TextView faith = (TextView) mInternalOuterView.findViewById(R.id.faith);
    TextView number = (TextView) mInternalOuterView.findViewById(R.id.count);

    faith.setTextColor(settings.getInt(DARKERRGB,0));
    number.setTextColor(settings.getInt(DARKERRGB,0));

    LinearLayout frame = (LinearLayout) mInternalOuterView.findViewById(R.id.native_card_xml);
    FrameLayout headerLayout = (FrameLayout) mInternalOuterView.findViewById(R.id.header_layout);

    if(headerLayout != null){
      headerLayout.measure(UNBOUNDED, UNBOUNDED);
      int mMeasuredHeight = headerLayout.getMeasuredHeight();
      editor.putInt(MCARDVIEWMEASUREDHEIGHT, mMeasuredHeight);
//editor.commit();
      //editor.apply();
    }
  }

  public void setCardLayoutResourceID(int cardLayoutResourceID) {
    this.cardLayoutResourceID = cardLayoutResourceID;
    initView();
    if(cardLayoutResourceID == R.layout.content_layout) {
      RelativeLayout headerContainer = (RelativeLayout) mInternalOuterView.findViewById(R.id.header_container);
      TextView faith = (TextView) mInternalOuterView.findViewById(R.id.faith);
      Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/SortsMillGoudy-Italic.ttf");
      faith.setTypeface(typeface);
      headerContainer.measure(UNBOUNDED, UNBOUNDED);
      int mContentMeasuredHeight = headerContainer.getMeasuredHeight();
      editor.putInt(MCARDVIEWMEASUREDHEIGHT, mContentMeasuredHeight);
      editor.putInt(MCONTENTCARDVIEWMEASUREDHEIGHT, mContentMeasuredHeight);
      editor.commit();
    }
  }
}
