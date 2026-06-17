package com.sc.fr.hindouisme.layers.mvp.common.customs.viewpagers.fragmentpageradapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sc.fr.hindouisme.OnelittleAngelApplication;
import com.sc.fr.hindouisme.R;
import com.sc.fr.hindouisme.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.fr.hindouisme.layers.mvp.common.customs.textviews.ContentTextViewNativeBiography;
import com.sc.fr.hindouisme.layers.mvp.common.customs.viewpagers.ViewPagerNative;
import com.sc.fr.hindouisme.layers.mvp.common.layouts.linearlayouts.PersonalLinearLayout;
import com.sc.fr.hindouisme.layers.mvp.common.models.PageModel;
import com.sc.fr.hindouisme.layers.mvp.common.utils.Constants;
import com.sc.fr.hindouisme.layers.mvp.contents.ContentsPresenterInterface;
import com.sc.fr.hindouisme.layers.mvp.tablecontents.TableContentsActivity;

import java.util.ArrayList;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class ContentsNativePagerAdapter extends PagerAdapter implements ContentTextViewNativeBiography.Callbacks, PersonalLinearLayout.Callbacks {

  private Callbacks mCallbacks;

  //  HashMap<Integer,Fragment> fragmentHashMap = new HashMap<>();
  ViewPagerNative mViewPager;
  private int pos = 0;

  private final PageModel[] mPageModels;
  private SharedPreferences settings;
  SharedPreferences.Editor editor;
  private Typeface typeface;
  private PageModel currentPage;
  private final Activity activity;
  private boolean isWhiteSpaced = false;
  private int widthMeasureSpec;
  private int heightMeasureSpec;
  private int llQuotesHeight;
  //private List<ContentTextViewNativeBiography> currentPage.contentTextViewNatives;
  private float textSize = 0;
  private int uWidth = 0;
  private int uHeight = 0;

  public ContentsNativePagerAdapter(Activity activity,PageModel[] mPageModels, ContentsPresenterInterface presenter) {
    this.activity = activity;
    this.mPageModels = mPageModels;
  }

  @Override
  public int getItemPosition(Object object) {
    return POSITION_NONE;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  @Override
  public int getCount() {
    return mPageModels.length;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {

    LayoutInflater inflater = (LayoutInflater) OnelittleAngelApplication.instance.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    View view = inflater.inflate(R.layout.contents_card, null, false);

    // CardView
    //ContentTextViewNativeBiography textView = (ContentTextViewNativeBiography) view.findViewById(R.id.textview);
    LinearLayout llTxtView = (LinearLayout) view.findViewById(R.id.ll_txt_view);
    ContentTextViewNativeBiography sourceTxtView = (ContentTextViewNativeBiography) view.findViewById(R.id.source);
    CardView cardView = (CardView) view.findViewById(R.id.card_contents);
    PersonalLinearLayout layout = (PersonalLinearLayout) view.findViewById(R.id.contents_card_layout);
    currentPage = mPageModels[position];

    //currentPage.textView = textView;
    currentPage.llTxtView = llTxtView;
    currentPage.sourceTxtView = sourceTxtView;

    DisplayMetrics displaymetrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    //dislpayHeight = displaymetrics.heightPixels;
    int width = displaymetrics.widthPixels;

    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);


    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    //editor = settings.edit();

    String tpString = settings.getString(Constants.TYPEFACE, "");

    if( !tpString.trim().equals(""))
      typeface = Typeface.createFromAsset(OnelittleAngelApplication.instance.getAssets(), "fonts/" + tpString.trim() + ".ttf");
    else
      typeface = Typeface.createFromAsset(OnelittleAngelApplication.instance.getAssets(), "fonts/Monotype-Corsiva-Regular.ttf");

    //currentPage.textView.setTypeface(typeface);
    currentPage.sourceTxtView.setTypeface(typeface);

    if(settings.getFloat(Constants.TEXTSIZE, 0) != 0) {
      //currentPage.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,settings.getFloat(Constants.TEXTSIZE, 0));
      currentPage.sourceTxtView.setTextSize(TypedValue.COMPLEX_UNIT_PX,settings.getFloat(Constants.TEXTSIZE, 0) / 1.5f);
    }

    currentPage.sourceTxtView.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    currentPage.cardView = cardView;
    currentPage.layout = layout;
    currentPage.layout.bringToFront();
    currentPage.layout.setActivity(activity);

    //textView.setText(currentPage.getQuoteText());

    String s = "  ";
    int j = -1;
    int k;

    if (llTxtView.getChildCount() <= 1 || textSize != settings.getFloat(Constants.TEXTSIZE, 0)) {
      llQuotesHeight = 0;

      textSize = settings.getFloat(Constants.TEXTSIZE, 0);

      if(currentPage.contentTextViewNatives != null && currentPage.contentTextViewNatives.size() != 0){
        for(int i = 0; i < currentPage.contentTextViewNatives.size(); i++) {
          llTxtView.removeView(currentPage.contentTextViewNatives.get(i));
        }
      }

      currentPage.contentTextViewNatives = new ArrayList<>();
      ContentTextViewNativeBiography aNative1 = new ContentTextViewNativeBiography(activity);
      aNative1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
      aNative1.setIncludeFontPadding(false);
      aNative1.setText("a");
      aNative1.measure(0, 0);
      llTxtView.measure(widthMeasureSpec, heightMeasureSpec);
      uWidth = (int) ((llTxtView.getMeasuredWidth() - activity.getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) / aNative1.getMeasuredWidth());
      uHeight = aNative1.getMeasuredHeight();

      ContentTextViewNativeBiography aNative = new ContentTextViewNativeBiography(activity);
      k = uWidth * 3;

      for (int i = 0; i < currentPage.getQuoteText().length(); i += k) {
        boolean isLastLine = false;
        if (currentPage.getQuoteText().length() > i + uWidth * 3) {
          s += currentPage.getQuoteText().substring(i, i + uWidth * 3);

        } else {
          s += currentPage.getQuoteText().substring(i, currentPage.getQuoteText().length());
          isLastLine = true;
        }
        isWhiteSpaced = false;

        do {

          if (s.length() != 0 && (!Character.isWhitespace(s.charAt(s.length() - 1)))) {
            s = s.substring(0, s.length() - 1);
          }

          if (s.length() != 0 && (Character.isWhitespace(s.charAt(s.length() - 1)))) {
            llTxtView.removeView(aNative);
            aNative = new ContentTextViewNativeBiography(activity);
            aNative.setIncludeFontPadding(false);

            aNative.setText(s);
            llTxtView.addView(aNative);
            layout.measure(widthMeasureSpec, heightMeasureSpec);
            if (!isLastLine && aNative.lastLinePosition != 0) {
              s = s.substring(0, aNative.lastLinePosition);
            }

            if (s.length() != 0 && s.charAt(s.length() - 1) == '\n') {
              s = s.substring(0, s.length() - 1);

              k = s.length() + 1;
            } else {
              k = s.length();

            }

            if (j == -1) k = s.length() - 2;

            llTxtView.removeView(aNative);

//            if(j % 2 == 0) aNative.setBackgroundColor(Color.CYAN);
//            else aNative.setBackgroundColor(Color.GREEN);

            aNative = new ContentTextViewNativeBiography(activity);
            aNative.setIncludeFontPadding(false);
            aNative.setText(s);
            aNative.setCallbacks(ContentsNativePagerAdapter.this);
            currentPage.contentTextViewNatives.add(aNative);
            isWhiteSpaced = true;
          } else if (isLastLine) {
            s = currentPage.getQuoteText().substring(i, currentPage.getQuoteText().length());
            k = s.length();
            aNative = new ContentTextViewNativeBiography(activity);
            aNative.setIncludeFontPadding(false);
            aNative.setText(s);
            aNative.setCallbacks(ContentsNativePagerAdapter.this);
            llTxtView.addView(aNative);
            layout.measure(widthMeasureSpec, heightMeasureSpec);
            llTxtView.removeView(aNative);
            currentPage.contentTextViewNatives.add(aNative);
            isWhiteSpaced = true;
          }
        }
        while (!isWhiteSpaced);
        if (i == 0) {
          j = 0;
        } else {
          j++;
        }
        s = "";
      }
    }

    if (llTxtView.getChildCount() <= 1) {
      for (int i = 0; i < currentPage.contentTextViewNatives.size(); i++) {
//        if(i % 2 == 0) currentPage.contentTextViewNatives.get(i).setBackgroundColor(Color.GREEN);
//        else currentPage.contentTextViewNatives.get(i).setBackgroundColor(Color.GRAY);
        llTxtView.addView(currentPage.contentTextViewNatives.get(i));
      }
    }

    sourceTxtView.setText(currentPage.getSourceText());

    if (position != 1 && mPageModels.length != 1) {
      animate(llTxtView).alpha(0);
      animate(cardView).alpha(0);
    }

    //currentPage.textView.setCallbacks(this);
    currentPage.sourceTxtView.setCallbacks(this);
    currentPage.layout.setCallbacks(this);

    container.addView(view);
    return view;
  }

  public void setTypeface(Typeface typeface) {
    this.typeface = typeface;
    //currentPage.textView.setTypeface(typeface);
    currentPage.sourceTxtView.setTypeface(typeface);
  }

  public PageModel[] getmPageModels() {
    return mPageModels;
  }

  public void setCallbacks(Callbacks listener) {
    mCallbacks = listener;
  }

  @Override
  public void onDoubleTaped() {
    mCallbacks.showAddRemoveFavoritesDialog();
  }

  @Override
  public void onScaling(float size, int resId) {

    if(size == 0) {
      mCallbacks.onScaling(settings.getFloat(Constants.TEXTSIZE, 0), resId);
    }
    else mCallbacks.onScaling(size, resId);
  }

  @Override
  public void onScaleEnd(float size, int resId) {
    //currentPage.llTxtView.removeAllViews();
    mCallbacks.onScaleEnd();
  }

  @Override
  public void onSingleTapUped() {
  }

  @Override
  public void onSingleTapConfirm() {
    mCallbacks.toggleFloatingActionButton();
  }

  @Override
  public void onFlingGesture() {

  }

  @Override
  public void onLongPressed() {
    mCallbacks.toggleFloatingActionButton();
  }

  public interface Callbacks {

    void showAddRemoveFavoritesDialog();
    void toggleFloatingActionButton();
    void onScaling(float size, int resId);
    void onScaleEnd();
  }

  @Override
  public boolean isViewFromObject(View view, Object obj) {
    return view == obj;
  }

}
