package com.sc.en.christianism.layers.mvp.biography.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.sc.en.christianism.OnelittleAngelApplication;
import com.sc.en.christianism.layers.mvp.biography.listgridviews.CardGridArrayAdapter;
import com.sc.en.christianism.layers.mvp.biography.viewpagers.ViewPagerNative;
import com.sc.en.christianism.layers.mvp.common.customs.imagezoom.ImageViewTouch;
import com.sc.en.christianism.layers.mvp.common.customs.scrollviews.ObservableScrollView;
import com.sc.en.christianism.layers.mvp.common.customs.spinkits.SpinKitView;
import com.sc.en.christianism.layers.mvp.common.customs.spinkits.SpriteFactory;
import com.sc.en.christianism.layers.mvp.common.customs.spinkits.Style;
import com.sc.en.christianism.layers.mvp.common.customs.spinkits.sprite.Sprite;
import com.sc.en.christianism.layers.mvp.common.listeners.cardviews.CardViewNativeGestureListener;
import com.sc.en.christianism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.christianism.layers.mvp.biography.BiographyActivity;
import com.sc.en.christianism.R;
import com.sc.en.christianism.injector.PresenterInjector;
import com.sc.en.christianism.layers.mvp.biography.BiographyPresenterInterface;
import com.sc.en.christianism.layers.mvp.biography.listgridviews.CardGridView;
import com.sc.en.christianism.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.christianism.layers.mvp.common.customs.textviews.ContentTextViewNativeBiography;
import com.sc.en.christianism.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.en.christianism.layers.mvp.common.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class PresentationFragment extends BaseFragment implements PresentationViewInterface,ContentTextViewNativeBiography.Callbacks, ImageViewTouch.CallBacks {

  public static final String PRESENTATION_TITLE = "PRESENTATION_TITLE";
  private static final String PRESENTATION = "PRESENTATION";
  private static final String NAME = "NAME";

  private boolean mHasInflated = false;
  private CardView cvPresentation;
  public ContentTextViewNativeBiography presentationTitle;
  private String titleText;
  private String presentationText;
  private LinearLayout llBiography;
  private int height;
  private int dislpayHeight = 0;
  private SharedPreferences settings;
  //SharedPreferences.Editor editor;
  private int widthMeasureSpec;
  private int heightMeasureSpec;
  private LinearLayout llPresentation;
  //private final LinearLayout llPresentation1;

  private BiographyActivity activity;
  public BiographyPresenterInterface presenter;
  private CardGridView gvCarrousel;
  public CardGridArrayAdapter gridArrayAdapter;
  private ImageView ivQuoteLeft;
  private ImageView ivQuoteRight;

  public PresentationPresenterInterface presenterInterface = null;
  private Dialog picturesLoadingDialog;
  private CardView cvBiographyDialog;
  private String name;
  private ImageViewTouch ivExpandedPictures;
  private int uWidth = 0;
  private int uHeight = 0;
  public int h = 0;
  public int lineCount = 0;
  private Handler handler;
  private float textSize = 0;
  private List<ContentTextViewNativeBiography> contentTextViewNatives;
  private int llBiographyHeight;
  public int totalLineCount = 0;
  public int presentationTitleHeight = 0;
  private boolean isWhiteSpaced = false;
  public View inflatedView;
  private int width;
  private ViewPagerNative viewPagerNative;

  @Override
  public void updateCarousel() {
    gvCarrousel.setVisibility(View.VISIBLE);
    animate(gvCarrousel).alpha(1).setDuration(0).start();
    gvCarrousel.getmAdapter().notifyDataSetChanged();

    int itemCount = gvCarrousel.getmAdapter().getCount();
    int columns = 0;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
      columns = gvCarrousel.getNumColumns();
    } else {
      columns = gvCarrousel.getNumColumnsCompat();
    }

    int lines = (itemCount / columns);
    if (itemCount % columns != 0) {
      lines++;
    }
    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) gvCarrousel.getLayoutParams();
    lp.topMargin = (int) (BiographyActivity.headerHeight+ (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin));
    lp.height = (int) (lines * gvCarrousel.getmAdapter().itemHeight + ((int) (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) + BiographyActivity.headerHeight + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_double_margin));

    if (lp.height > dislpayHeight) {
      height = lp.height;
    } else {
      height = dislpayHeight;
      lp.height = height;
    }

    DisplayMetrics displaymetrics = new DisplayMetrics();
    (activity == null ? getActivity() : activity).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    height = displaymetrics.heightPixels;
    int width = displaymetrics.widthPixels;

    gvCarrousel.setLayoutParams(lp);
    gvCarrousel.requestLayout();

    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewPagerNative.getLayoutParams();
    params.height = lp.height;
    viewPagerNative.setLayoutParams(params);
    if(!((BiographyActivity) (activity == null ? getActivity() : activity)).isImageCommentDisplaying && !((BiographyActivity) (activity == null ? getActivity() : activity)).isExpandedImageDipslaying) {

      if (settings == null) settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      //if (editor == null) editor = settings.edit();

      settings.edit().putBoolean(Constants.IS_ANIMATING_LISTVIEW_ENABLED, true).commit();
      gvCarrousel.setVisibility(View.VISIBLE);
      gvCarrousel.setBottomAdapter();
    }
    //else gvCarrousel.setAdapter(gridArrayAdapter);
    gvCarrousel.bringToFront();
    ivExpandedPictures.setCallbacks(PresentationFragment.this);


    gvCarrousel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {

        int itemCount = gvCarrousel.getmAdapter().getCount();
        int columns = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
          columns = gvCarrousel.getNumColumns();
        } else {
          columns = gvCarrousel.getNumColumnsCompat();
        }

        int lines = (itemCount / columns);
        if (itemCount % columns != 0) {
          lines++;
        }
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) gvCarrousel.getLayoutParams();
        lp.topMargin = (int) (BiographyActivity.headerHeight+ (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin));
        lp.height = (int) (lines * gvCarrousel.getmAdapter().itemHeight + ((int) (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) + BiographyActivity.headerHeight + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_double_margin));

        if (lp.height > dislpayHeight) {
          height = lp.height;
        } else {
          height = dislpayHeight;
          lp.height = height;
        }

        DisplayMetrics displaymetrics = new DisplayMetrics();
        (activity == null ? getActivity() : activity).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        gvCarrousel.setLayoutParams(lp);
        gvCarrousel.requestLayout();

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewPagerNative.getLayoutParams();
        params.height = lp.height;
        viewPagerNative.setLayoutParams(params);

        if (settings == null) settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
        //if (editor == null) editor = settings.edit();

        settings.edit().putBoolean(Constants.IS_ANIMATING_LISTVIEW_ENABLED, true).commit();

        gvCarrousel.setBottomAdapter();
        gvCarrousel.bringToFront();
        ivExpandedPictures.setCallbacks(PresentationFragment.this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
          gvCarrousel.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
          gvCarrousel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
      }
    });
  }

  @Override
  public void showWallpaperDialog() {
    if(!(activity == null ? getActivity() : activity).isFinishing()) BiographyActivity.wallPapersDialog.show();
  }

  @Override
  public void returnToCardGridView() {
    gridArrayAdapter.zoomImageFromThumb2(null, false, false);
  }

  public interface Callbacks {

    void toggleFloatingActionButton();

    void onScaling(float size, int resId);
  }

  public void setActivity(BiographyActivity activity) {
    this.activity = activity;
  }

  public void setCallbacks(Callbacks listener) {
  }

  /**
   * @param presentationTitle describe the fragment type
   * @return the fragment instance
   */
  public static PresentationFragment newInstance(String presentationTitle, String presentation) {

    PresentationFragment presentationFragment = new PresentationFragment();
    Bundle args = new Bundle();
    args.putString(PRESENTATION_TITLE, presentationTitle);
    args.putString(PRESENTATION, presentation);
    presentationFragment.setArguments(args);
    return presentationFragment;
  }

  public static PresentationFragment newInstance(BiographyPresenterInterface presenter, String name) {
    PresentationFragment presentationFragment = new PresentationFragment();
    presentationFragment.presenter = presenter;
    Bundle args = new Bundle();
    args.putString(NAME, name);
    presentationFragment.setArguments(args);
    return presentationFragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    BiographyActivity biographyActivity = (BiographyActivity) getActivity();
  }

  @Override
  public void onAttachFragment(Fragment childFragment) {
    super.onAttachFragment(childFragment);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);
  }


  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    outState.putString("name", name);
    outState.putString("titleText", titleText);
    outState.putString("presentationText", presentationText);
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onStop() {
    super.onStop();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    //name = null;
    titleText = null;
    presentationText = null;
  }

  @Override
  public void onDestroy() {

    try {
      if (picturesLoadingDialog != null && picturesLoadingDialog.isShowing()) {
        picturesLoadingDialog.dismiss();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    //name = null;
    if (!BiographyActivity.isOrientationChanged && gridArrayAdapter != null){
      //  CardGridArrayAdapter.thumbViewList = null;
    }
    super.onDestroy();
    //titleText = null;
    //  presentationTitle.setText(null);
    //  presentationTitle.setCallbacks(null);
    //  Utils.clearTextLineCache();
    //  presentationTitle = null;
    //  presentationText = null;
    //  presentation.setText(null);
    //  presentation.setCallbacks(null);
    Utils.clearTextLineCache();
    //  presentation = null;
    //  llBiography = null;
    //  cvPresentation = null;
    //  settings = null;
    //  editor = null;
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      titleText = getArguments().getString(PRESENTATION_TITLE);
      presentationText = getArguments().getString(PRESENTATION);
      name = getArguments().getString(NAME);

    }

    if(savedInstanceState != null) {
      name = savedInstanceState.getString("name");
      titleText = savedInstanceState.getString("titleText");
      presentationText = savedInstanceState.getString("presentationText");
    }

    DisplayMetrics displaymetrics = new DisplayMetrics();
    (activity == null ? getActivity() : activity).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    dislpayHeight = displaymetrics.heightPixels;
    width = displaymetrics.widthPixels;

    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    //editor = settings.edit();

    if(name != null && presenter == null) presenter = ((BiographyActivity) (activity == null ? getActivity() : activity)).presenter;

    if (presenterInterface == null) presenterInterface = PresenterInjector.getPresentationInterface(this);
  }

  public int dpToPx(int dp) {
    DisplayMetrics displayMetrics = (activity == null ? getActivity() : activity).getResources().getDisplayMetrics();
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
  }

  //@Override
  protected void onCreateViewAfterViewStubInflated(View inflatedView, Bundle savedInstanceState) {

    mHasInflated = true;
    getHasInflated();

    if (inflatedView != null) {

      viewPagerNative = (ViewPagerNative) (activity == null ? getActivity() : activity).findViewById(R.id.viewpager);
      ivExpandedPictures = (ImageViewTouch) (activity == null ? getActivity() : activity).findViewById(R.id.expanded_image);
      ivQuoteLeft = (ImageView) (activity == null ? getActivity() : activity).findViewById(R.id.quote_left);
      ivQuoteRight = (ImageView) (activity == null ? getActivity() : activity).findViewById(R.id.quote_right);

      llBiography = (LinearLayout) inflatedView.findViewById(R.id.ll_biography);
      cvPresentation = (CardView) inflatedView.findViewById(R.id.cv_presentation);

      animate(cvPresentation).alpha(0).setDuration(0).start();

      if (llPresentation == null) llPresentation = (LinearLayout) inflatedView.findViewById(R.id.ll_presentation);

      gvCarrousel = (CardGridView) inflatedView.findViewById(R.id.gv_carrousel);

      if (name == null) {

        gvCarrousel.setVisibility(View.GONE);
        ivQuoteRight.setImageResource(R.drawable.quote_right_text);
        ivQuoteLeft.setImageResource(R.drawable.quote_left_text);

        if(((BiographyActivity) (activity == null ? getActivity() : activity)).isMailDialogShowing && !((BiographyActivity) (activity == null ? getActivity() : activity)).mailsDialog.isShowing()) ((BiographyActivity) getActivity()).goToMailActivity();
        else if(((BiographyActivity) (activity == null ? getActivity() : activity)).isTypefaceDialogShowing && ((BiographyActivity) (activity == null ? getActivity() : activity)).typeFacesDialog != null && !((BiographyActivity) (activity == null ? getActivity() : activity)).typeFacesDialog.isShowing()) {
          if(!(activity == null ? getActivity() : activity).isFinishing()) ((BiographyActivity) (activity == null ? getActivity() : activity)).showTypeFaceDialog();
        } else if(((BiographyActivity) (activity == null ? getActivity() : activity)).isTypefaceDialogShowing && ((BiographyActivity) (activity == null ? getActivity() : activity)).typeFacesDialog != null && ((BiographyActivity) (activity == null ? getActivity() : activity)).typeFacesDialog.isShowing()) {}
        else if (((BiographyActivity) (activity == null ? getActivity() : activity)).isMicrophoneDialogShowing && ((BiographyActivity) (activity == null ? getActivity() : activity)).microphonesDialog != null && !BiographyActivity.microphonesDialog.isShowing()) {
          if(!(activity == null ? getActivity() : activity).isFinishing()) ((BiographyActivity) (activity == null ? getActivity() : activity)).showMicrophoneDialog();
        }
        else if (((BiographyActivity) (activity == null ? getActivity() : activity)).isMicrophoneDialogShowing && ((BiographyActivity) (activity == null ? getActivity() : activity)).microphonesDialog != null && BiographyActivity.microphonesDialog.isShowing()) {

        } else {
          if (BiographyActivity.dBiographyLoading == null) {
            BiographyActivity.dBiographyLoading = new Dialog((activity == null ? getActivity() : activity), R.style.myDialogSlideUpAndDown);
            BiographyActivity.dBiographyLoading.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            BiographyActivity.dBiographyLoading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            BiographyActivity.dBiographyLoading.setContentView(R.layout.biography_dialog);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
              SpinKitView spinKitView = (SpinKitView) BiographyActivity.dBiographyLoading.findViewById(R.id.spin_kit);
              Style style = Style.FADING_CIRCLE;
              Sprite drawable = SpriteFactory.create(style);
              spinKitView.setIndeterminateDrawable(drawable);
              spinKitView.setVisibility(View.VISIBLE);
              cvBiographyDialog = (CardView) BiographyActivity.dBiographyLoading.findViewById(R.id.cv_biography_dialog);
              cvBiographyDialog.setVisibility(View.GONE);
            }
          }
          if (!BiographyActivity.dBiographyLoading.isShowing()) {

            if(!(activity == null ? getActivity() : activity).isFinishing()) {

              if ((activity == null ? getActivity() : activity).getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_0)
                (activity == null ? getActivity() : activity).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
              if ((activity == null ? getActivity() : activity).getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_90)
                (activity == null ? getActivity() : activity).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
              if ((activity == null ? getActivity() : activity).getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_270)
                (activity == null ? getActivity() : activity).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);

              TextViewNative aNative = (TextViewNative) BiographyActivity.dBiographyLoading.findViewById(R.id.txtn_waiting);
              aNative.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

              if (!((BiographyActivity) (activity == null ? getActivity() : activity)).isFromMailOrTypefaceSetings) {
                BiographyActivity.dBiographyLoading.show();
                BiographyActivity.dBiographyLoading.setCancelable(false);
              }
            }
          }
        }

        handler = new Handler();

        handler.postDelayed(new Runnable() {
          @Override
          public void run() {

            llBiography.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
              @Override
              public void onGlobalLayout() {

                String s = "  ";
                int j = -1;
                int k;

                if (llPresentation.getChildCount() <= 1 || textSize != settings.getFloat(Constants.TEXTSIZE, 0)) {
                  llBiographyHeight = 0;

                  if(textSize != settings.getFloat(Constants.TEXTSIZE, 0)){

                  }
                  textSize = settings.getFloat(Constants.TEXTSIZE, 0);


                  if(contentTextViewNatives != null && contentTextViewNatives.size() != 0){
                    for(int i = 0; i < contentTextViewNatives.size(); i++) {
                      llPresentation.removeView(contentTextViewNatives.get(i));
                    }
                  }

                  contentTextViewNatives = new ArrayList<>();
                  ContentTextViewNativeBiography aNative1 = new ContentTextViewNativeBiography(getContext());
                  aNative1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                  aNative1.setIncludeFontPadding(false);
                  aNative1.setText("a");
                  aNative1.measure(0, 0);
                  llPresentation.measure(widthMeasureSpec, heightMeasureSpec);
                  uWidth = (int) ((llPresentation.getMeasuredWidth() - (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) / aNative1.getMeasuredWidth());
                  uHeight = aNative1.getMeasuredHeight();

                  ContentTextViewNativeBiography aNative = new ContentTextViewNativeBiography((activity == null ? getActivity() : activity));
                  k = uWidth * 3;

                  if (!titleText.equals("")) {
                    aNative.setText(String.format("%s ", titleText));
                    aNative.setGravity(Gravity.CENTER_HORIZONTAL);

                    llPresentation.addView(aNative);

                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) aNative.getLayoutParams();
                    params.topMargin = (int) (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin);
                    params.bottomMargin = (int) (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin);
                    aNative.setLayoutParams(params);
                    llBiography.measure(widthMeasureSpec, heightMeasureSpec);
                    llPresentation.removeView(aNative);
                    contentTextViewNatives.add(aNative);
                  }

                  for (int i = 0; i < presentationText.length(); i += k) {
                    boolean isLastLine = false;
                    if (presentationText.length() > i + uWidth * 3) {
                      s += presentationText.substring(i, i + uWidth * 3);

                    } else {
                      s += presentationText.substring(i, presentationText.length());
                      isLastLine = true;
                    }
                    isWhiteSpaced = false;

                    do {

                      if (s.length() != 0 && (!Character.isWhitespace(s.charAt(s.length() - 1)))) {
                        s = s.substring(0, s.length() - 1);
                      }

                      if (s.length() != 0 && (Character.isWhitespace(s.charAt(s.length() - 1)))) {
                        llPresentation.removeView(aNative);
                        aNative = new ContentTextViewNativeBiography((activity == null ? getActivity() : activity));
                        aNative.setIncludeFontPadding(false);

                        aNative.setText(s);
                        llPresentation.addView(aNative);
                        llBiography.measure(widthMeasureSpec, heightMeasureSpec);
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

                        llPresentation.removeView(aNative);

                        aNative = new ContentTextViewNativeBiography((activity == null ? getActivity() : activity));
                        aNative.setIncludeFontPadding(false);
                        aNative.setText(s);
                        aNative.setCallbacks(PresentationFragment.this);
                        contentTextViewNatives.add(aNative);
                        isWhiteSpaced = true;
                      } else if (isLastLine) {
                        s = presentationText.substring(i, presentationText.length());
                        k = s.length();
                        aNative = new ContentTextViewNativeBiography((activity == null ? getActivity() : activity));
                        aNative.setIncludeFontPadding(false);
                        aNative.setText(s);
                        aNative.setCallbacks(PresentationFragment.this);
                        llPresentation.addView(aNative);
                        llBiography.measure(widthMeasureSpec, heightMeasureSpec);
                        llPresentation.removeView(aNative);
                        contentTextViewNatives.add(aNative);
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

                } else {
                }

                if (llPresentation.getChildCount() <= 1) {
                  for (int i = 0; i < contentTextViewNatives.size(); i++) {
                    llPresentation.addView(contentTextViewNatives.get(i));
                  }
                }

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cvPresentation.getLayoutParams();
                lp.topMargin = (int) (BiographyActivity.headerHeight + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin));
                cvPresentation.setLayoutParams(lp);

                animate(viewPagerNative).alpha(1).setDuration(200).start();

                if( !((BiographyActivity) (activity == null ? getActivity() : activity)).isTypefaceDialogShowing) {

                  animate(cvPresentation).alpha(1).setDuration(200).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                      super.onAnimationEnd(animation);
                      if (BiographyActivity.dBiographyLoading == null) {
                        BiographyActivity.dBiographyLoading = new Dialog((activity == null ? getActivity() : activity), R.style.myDialogSlideUpAndDown);
                        BiographyActivity.dBiographyLoading.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        BiographyActivity.dBiographyLoading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        BiographyActivity.dBiographyLoading.setContentView(R.layout.biography_dialog);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                          SpinKitView spinKitView = (SpinKitView) BiographyActivity.dBiographyLoading.findViewById(R.id.spin_kit);
                          Style style = Style.FADING_CIRCLE;
                          Sprite drawable = SpriteFactory.create(style);
                          spinKitView.setIndeterminateDrawable(drawable);
                          spinKitView.setVisibility(View.VISIBLE);
                          cvBiographyDialog = (CardView) BiographyActivity.dBiographyLoading.findViewById(R.id.cv_biography_dialog);
                          cvBiographyDialog.setVisibility(View.GONE);
                        }
                      }

                      llBiographyHeight = 0;
                      for (int i = 0; i < llPresentation.getChildCount(); i++) {

                        if (((ContentTextViewNativeBiography) llPresentation.getChildAt(i)).getText().toString().charAt(((ContentTextViewNativeBiography) llPresentation.getChildAt(i)).getText().toString().length() - 1) == '\n')
                          llBiographyHeight += uHeight * (((ContentTextViewNativeBiography) llPresentation.getChildAt(i)).lineCount + 1);
                        else if (((ContentTextViewNativeBiography) llPresentation.getChildAt(i)).getTextView().getText().toString().contains("\n\n")) {
                          if (llPresentation.getChildAt(i).getMeasuredHeight() == 0) {
                            llPresentation.getChildAt(i).measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST),
                                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                          }

                          if (llPresentation.getChildAt(i).getMeasuredHeight() > ((ContentTextViewNativeBiography) llPresentation.getChildAt(i)).lineCount)
                            llBiographyHeight += llPresentation.getChildAt(i).getMeasuredHeight();
                          else
                            llBiographyHeight += uHeight * ((ContentTextViewNativeBiography) llPresentation.getChildAt(i)).lineCount;
                        } else
                          llBiographyHeight += uHeight * ((ContentTextViewNativeBiography) llPresentation.getChildAt(i)).lineCount;
                      }

                      LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewPagerNative.getLayoutParams();
                      lp.topMargin = 0;

                      if (titleText != null && !titleText.equals((activity == null ? getActivity() : activity).getResources().getString(R.string.bibliography))) {

                        if (!titleText.equals("")) {
                          height = (int) (((llBiographyHeight) + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.card_elevation)) + ((int) ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin) * 5) /*+ ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_double_margin) * 2)*/));
                        } else {

                          height = (int) (((llBiographyHeight) + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.card_elevation)) + ((int) ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin) * 3) /*+ ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_double_margin) * 2)*/));
                        }
                        if (((BiographyActivity) (activity == null ? getActivity() : activity)).mObservableScrollView == null)
                          ((BiographyActivity) (activity == null ? getActivity() : activity)).mObservableScrollView = (ObservableScrollView) ((BiographyActivity) (activity == null ? getActivity() : activity)).findViewById(R.id.scroll_view);

                        ((BiographyActivity) (activity == null ? getActivity() : activity)).mObservableScrollView.setVerticalScrollBarEnabled(true);
                        if(height < dislpayHeight - llBiography.getTop()) height = dislpayHeight - llBiography.getTop();

                        lp.height = (int) (height + BiographyActivity.headerHeight + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin));
                      } else {
                        if (((BiographyActivity) (activity == null ? getActivity() : activity)).mObservableScrollView == null)
                          ((BiographyActivity) (activity == null ? getActivity() : activity)).mObservableScrollView = (ObservableScrollView) ((BiographyActivity) (activity == null ? getActivity() : activity)).findViewById(R.id.scroll_view);
                        ((BiographyActivity) (activity == null ? getActivity() : activity)).mObservableScrollView.setVerticalScrollBarEnabled(false);

                        llBiography.measure(widthMeasureSpec, heightMeasureSpec);
                        if ((int) (((llBiographyHeight) + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.card_elevation)) + ((int) ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin) * 4) /*+ ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_double_margin) * 2)*/)) > dislpayHeight - llBiography.getTop()) {
                          height = (int) (((llBiographyHeight) + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.card_elevation)) + ((int) ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin) * 4) /*+ ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_double_margin) * 2)*/));
                          lp.height = (int) (height + BiographyActivity.headerHeight + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin));
                        } else {
                          height = dislpayHeight - llBiography.getTop();
                          lp.height = (int) (height + BiographyActivity.headerHeight + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin));
                        }
                      }

                      viewPagerNative.setLayoutParams(lp);
                      ((BiographyActivity) (activity == null ? getActivity() : activity)).mObservableScrollView.smoothScrollTo(0, 0);

                      //BiographyActivity.dBiographyLoading.dismiss();
                      if (BiographyActivity.dBiographyLoading != null && BiographyActivity.dBiographyLoading.isShowing()) {
                        BiographyActivity.dBiographyLoading.dismiss();
                        if(BiographyActivity.dBiographyLoading.isShowing()) BiographyActivity.dBiographyLoading.dismiss();
                        (activity == null ? getActivity() : activity).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

                      }
                      animate(cvPresentation).setListener(null);

                      if (((BiographyActivity) (activity == null ? getActivity() : activity)).isMicrophoneNextOrPrevious && ((BiographyActivity) (activity == null ? getActivity() : activity)).plays) {
                        Handler handler1 = new Handler();
                        handler1.postDelayed(() -> {
                          BiographyActivity.isForMicrophone = false;
                          ((BiographyActivity) (activity == null ? getActivity() : activity)).isMicrophoneNextOrPrevious = false;
                          ((BiographyActivity) (activity == null ? getActivity() : activity)).playQuoteMicrophone();
                        }, 1000);
                      }
                    }
                  }).start();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                  llPresentation.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                  llPresentation.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
              }
            });

            if (llPresentation.getChildCount() > 1) {
              //cvPresentation.removeViewInLayout(llPresentation1);
              ((ViewGroup) llPresentation.getParent()).removeView(llPresentation);
              cvPresentation.addView(llPresentation);
            }

            llBiography.setOnTouchListener(
                    new CardViewNativeGestureListener(getContext()) {
                      @Override
                      public void onLongPressed() {
                        super.onLongPressed();
                        ((BiographyActivity) (activity == null ? getActivity() : activity)).toggleFloatingActionButton();
                      }

                      @Override
                      public void onSingleTapConfirm() {
                        super.onSingleTapConfirm();
                        ((BiographyActivity) (activity == null ? getActivity() : activity)).toggleFloatingActionButton();
                      }

                      @Override
                      public void onDoubleTaped() {
                        super.onDoubleTaped();
                        ((BiographyActivity) (activity == null ? getActivity() : activity)).toggleFloatingActionButton();
                      }
                    }
            );

            cvPresentation.setOnTouchListener(
                    new CardViewNativeGestureListener(getContext()) {
                      @Override
                      public void onLongPressed() {
                        super.onLongPressed();
                        ((BiographyActivity) (activity == null ? getActivity() : activity)).toggleFloatingActionButton();
                      }

                      @Override
                      public void onSingleTapConfirm() {
                        super.onSingleTapConfirm();
                        ((BiographyActivity) (activity == null ? getActivity() : activity)).toggleFloatingActionButton();
                      }

                      @Override
                      public void onDoubleTaped() {
                        super.onDoubleTaped();
                        ((BiographyActivity) (activity == null ? getActivity() : activity)).toggleFloatingActionButton();
                      }
                    }
            );
          }
        }, 1000);
      } else {

        if (((BiographyActivity) (activity == null ? getActivity() : activity)).mObservableScrollView == null)
          ((BiographyActivity) (activity == null ? getActivity() : activity)).mObservableScrollView = (ObservableScrollView) ((BiographyActivity) (activity == null ? getActivity() : activity)).findViewById(R.id.scroll_view);

        ((BiographyActivity) (activity == null ? getActivity() : activity)).mObservableScrollView.setVerticalScrollBarEnabled(false);
        cvPresentation.setVisibility(View.GONE);
        ivQuoteRight.setImageResource(R.drawable.quote_right_pictures);
        ivQuoteLeft.setImageResource(R.drawable.quote_left_pictures);

        if (((BiographyActivity) (activity == null ? getActivity() : activity)).isFabOpen)
          ((BiographyActivity) (activity == null ? getActivity() : activity)).toggleFloatingActionButton();

        if (BiographyActivity.microphonesDialog != null && BiographyActivity.microphonesDialog.isShowing()) {
          BiographyActivity.isForMicrophone = false;
          BiographyActivity.microphonesDialog.dismiss();
        }

        if (BiographyActivity.dBiographyLoading != null && BiographyActivity.dBiographyLoading.isShowing()) {
          BiographyActivity.dBiographyLoading.dismiss();
          (activity == null ? getActivity() : activity).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }

        if (gridArrayAdapter == null) {
          gridArrayAdapter = new CardGridArrayAdapter((activity == null ? getActivity() : activity), presenterInterface, presenter);
          gvCarrousel.setVisibility(View.INVISIBLE);
          gvCarrousel.setAdapter(gridArrayAdapter);

          picturesLoadingDialog = new Dialog((activity == null ? getActivity() : activity), R.style.myDialogSlideUpAndDown);
          picturesLoadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
          picturesLoadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

          picturesLoadingDialog.setContentView(R.layout.biography_dialog);

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SpinKitView spinKitView = (SpinKitView) picturesLoadingDialog.findViewById(R.id.spin_kit);
            Style style = Style.FADING_CIRCLE;
            Sprite drawable = SpriteFactory.create(style);
            spinKitView.setIndeterminateDrawable(drawable);
            spinKitView.setVisibility(View.VISIBLE);
            cvBiographyDialog = (CardView) picturesLoadingDialog.findViewById(R.id.cv_biography_dialog);
            cvBiographyDialog.setVisibility(View.GONE);
          }

          TextViewNative aNative = (TextViewNative) picturesLoadingDialog.findViewById(R.id.txtn_waiting);
          aNative.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
          aNative.setText((activity == null ? getActivity() : activity).getResources().getString(R.string.loading_pictures));
          if(!(activity == null ? getActivity() : activity).isFinishing()) picturesLoadingDialog.show();
          picturesLoadingDialog.setCancelable(false);

          LoadPictures loadPictures = new LoadPictures();
          loadPictures.execute();

        } else {

          gridArrayAdapter = new CardGridArrayAdapter((activity == null ? getActivity() : activity), presenterInterface, presenter);
          gvCarrousel.setVisibility(View.INVISIBLE);
          gvCarrousel.setAdapter(gridArrayAdapter);
          if (picturesLoadingDialog != null) picturesLoadingDialog.dismiss();

          gvCarrousel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

              {

                int itemCount = gvCarrousel.getmAdapter().getCount();
                int columns = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                  columns = gvCarrousel.getNumColumns();
                } else {
                  columns = gvCarrousel.getNumColumnsCompat();
                }

                int lines = (itemCount / columns);
                if (itemCount % columns != 0) {
                  lines++;
                }
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) gvCarrousel.getLayoutParams();
//                lp.height = lines * gvCarrousel.getmAdapter().itemHeight + ((int) (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin) * 2);
                lp.topMargin = (int) (BiographyActivity.headerHeight+ (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin));
                lp.height = (int) (lines * gvCarrousel.getmAdapter().itemHeight + ((int) (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) + BiographyActivity.headerHeight + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin));

                if (lp.height > dislpayHeight) {
                  height = lp.height;
                } else {
                  height = dislpayHeight;
                  lp.height = height;
                }

                DisplayMetrics displaymetrics = new DisplayMetrics();
                (activity == null ? getActivity() : activity).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                height = displaymetrics.heightPixels;
                int width = displaymetrics.widthPixels;

                gvCarrousel.setLayoutParams(lp);
                gvCarrousel.requestLayout();

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewPagerNative.getLayoutParams();
                params.height = lp.height;
                viewPagerNative.setLayoutParams(params);

                if (settings == null) settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
                //if (editor == null) editor = settings.edit();

                settings.edit().putBoolean(Constants.IS_ANIMATING_LISTVIEW_ENABLED, true).commit();

                gvCarrousel.setVisibility(View.VISIBLE);
                gvCarrousel.setBottomAdapter();
                gvCarrousel.bringToFront();
                ivExpandedPictures.setCallbacks(PresentationFragment.this);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                  gvCarrousel.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                  gvCarrousel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
              }
            }
          });
        }

        ivExpandedPictures.setCallbacks(this);
      }
    }
  }

  @Override
  protected void afterViewStubInflated(View originalViewContainerWithViewStub) {
    super.afterViewStubInflated(originalViewContainerWithViewStub);
  }

  @Override
  protected void populateViewForOrientation(LayoutInflater inflater) {
    ViewGroup viewGroup = (ViewGroup) getView();
    viewGroup.removeAllViewsInLayout();
    View view = inflater.inflate(R.layout.viewstub, viewGroup, false);
    viewGroup.addView(view);
    mHasInflated = false;
    ViewTreeObserver.OnGlobalLayoutListener listener = null;
    getHasInflated();
  }

  private class LoadPictures extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      (activity == null ? getActivity() : activity).runOnUiThread(() -> {
        // runs on UI thread
        updateCarousel();
        picturesLoadingDialog.dismiss();

      });
    }

    @Override
    protected Void doInBackground(Void... params) {
      // runs on UI thread

      if (name.endsWith(" ")) {
        name = name.substring(0, name.length() - 1);
      }

      if (presenter == null) presenter = ((BiographyActivity) getActivity()).presenter;

      if (presenter.getMovement() != null) {
        presenterInterface.loadMovementPictures(name);
      } else if (presenter.getAuthor() != null) {
        presenterInterface.loadAuthorPictures(name);
      } else {
        presenterInterface.loadBookPictures(name);
      }
      return null;
    }
  }




  // @Override
  public String getFragmentType() {
    return null;
  }

  //  @Override
  protected int getLayoutResource() {
    return R.layout.base_fragment;
  }

  //  @Override
  protected int getViewStubLayoutResource() {
    return R.layout.biography_fragment_layout;
  }


  //@Override
  public boolean getHasInflated() {
    return mHasInflated;
  }

  @Override
  public void onLongPressed() {
    ((BiographyActivity) (activity == null ? getActivity() : activity)).toggleFloatingActionButton();
  }

  @Override
  public void onSingleTapUped() {
  }

  @Override
  public void onFlingGesture() {
  }

  @Override
  public void onSingleTapConfirm() {

    ((BiographyActivity) (activity == null ? getActivity() : activity)).toggleFloatingActionButton();
  }

  @Override
  public void onDoubleTaped() {

    ((BiographyActivity) (activity == null ? getActivity() : activity)).toggleFloatingActionButton();
  }

  @Override
  public void onScaling(float size, int resId) {

    for (int i = 0; i < llPresentation.getChildCount(); i++) {
      ContentTextViewNativeBiography aNative = (ContentTextViewNativeBiography) llPresentation.getChildAt(i);
      aNative.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }
  }

  @Override
  public void onScaleEnd(float size, int resId) {

    if (((BiographyActivity) (activity == null ? getActivity() : activity)).isTypefaceDialogShowing && ((BiographyActivity) (activity == null ? getActivity() : activity)).isTypefaceChanged) {
      ((BiographyActivity) (activity == null ? getActivity() : activity)).isTypefaceDialogShowing = false;
      ((BiographyActivity) (activity == null ? getActivity() : activity)).isTypefaceChanged = false;
    }

    if (getArguments() != null) {
      titleText = getArguments().getString(PRESENTATION_TITLE);
      presentationText = getArguments().getString(PRESENTATION);
      name = getArguments().getString(NAME);
    }

    if (BiographyActivity.dBiographyLoading == null) {
      BiographyActivity.dBiographyLoading = new Dialog((activity == null ? getActivity() : activity), R.style.myDialogSlideUpAndDown);
      BiographyActivity.dBiographyLoading.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
      BiographyActivity.dBiographyLoading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

      BiographyActivity.dBiographyLoading.setContentView(R.layout.biography_dialog);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        SpinKitView spinKitView = (SpinKitView) BiographyActivity.dBiographyLoading.findViewById(R.id.spin_kit);
        Style style = Style.FADING_CIRCLE;
        Sprite drawable = SpriteFactory.create(style);
        spinKitView.setIndeterminateDrawable(drawable);
        spinKitView.setVisibility(View.VISIBLE);
        cvBiographyDialog = (CardView) BiographyActivity.dBiographyLoading.findViewById(R.id.cv_biography_dialog);
        cvBiographyDialog.setVisibility(View.GONE);
      }
    }
    if(!(activity == null ? getActivity() : activity).isFinishing()){

      if ((activity == null ? getActivity() : activity).getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_0)
        (activity == null ? getActivity() : activity).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      if ((activity == null ? getActivity() : activity).getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_90)
        (activity == null ? getActivity() : activity).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
      if ((activity == null ? getActivity() : activity).getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_270)
        (activity == null ? getActivity() : activity).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);


      BiographyActivity.dBiographyLoading.show();
    }

    if(settings == null)     settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

    if(viewPagerNative == null) viewPagerNative = (ViewPagerNative) (activity == null ? getActivity() : activity).findViewById(R.id.viewpager);
    if(ivExpandedPictures == null) ivExpandedPictures = (ImageViewTouch) (activity == null ? getActivity() : activity).findViewById(R.id.expanded_image);
    if(ivQuoteLeft == null) ivQuoteLeft = (ImageView) (activity == null ? getActivity() : activity).findViewById(R.id.quote_left);
    if(ivQuoteRight == null) ivQuoteRight = (ImageView) (activity == null ? getActivity() : activity).findViewById(R.id.quote_right);

    if(llBiography == null) llBiography = (LinearLayout) (activity == null ? getActivity() : activity).findViewById(R.id.ll_biography);
    if(cvPresentation == null) cvPresentation = (CardView) (activity == null ? getActivity() : activity).findViewById(R.id.cv_presentation);
    if (llPresentation == null) llPresentation = (LinearLayout) (activity == null ? getActivity() : activity).findViewById(R.id.ll_presentation);

    TextViewNative aNative = (TextViewNative) BiographyActivity.dBiographyLoading.findViewById(R.id.txtn_waiting);
    aNative.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    BiographyActivity.dBiographyLoading.setCancelable(false);
    animate(cvPresentation).alpha(0).setDuration(300).setListener(null).start();

    if(handler == null) handler = new Handler();

    handler.postDelayed(() -> {

      if (llPresentation.getChildCount() > 1) llPresentation.removeAllViews();

      llBiography.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
          String s = "  ";
          int j = -1;
          int k;

          if (llPresentation.getChildCount() <= 1 || textSize != settings.getFloat(Constants.TEXTSIZE, 0)) {
            llBiographyHeight = 0;
            textSize = settings.getFloat(Constants.TEXTSIZE, 0);

            if(contentTextViewNatives != null && contentTextViewNatives.size() != 0){
              for(int i = 0; i < contentTextViewNatives.size(); i++) {
                llPresentation.removeView(contentTextViewNatives.get(i));
              }
            }

            contentTextViewNatives = new ArrayList<>();
            ContentTextViewNativeBiography aNative1 = new ContentTextViewNativeBiography((activity == null ? getActivity() : activity));
            aNative1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            aNative1.setIncludeFontPadding(false);
            aNative1.setText("a");
            aNative1.measure(0, 0);
            llPresentation.addView(aNative1);

            if(width == 0) {
              DisplayMetrics displaymetrics = new DisplayMetrics();
              (activity == null ? getActivity() : activity).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
              dislpayHeight = displaymetrics.heightPixels;
              width = displaymetrics.widthPixels;

              widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
              heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            }
            llPresentation.measure(widthMeasureSpec, heightMeasureSpec);
            uWidth = (int) ((llPresentation.getMeasuredWidth() - (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin) * 2) / aNative1.getMeasuredWidth());
            uHeight = aNative1.getMeasuredHeight();
            llPresentation.removeView(aNative1);
            ContentTextViewNativeBiography aNative = new ContentTextViewNativeBiography((activity == null ? getActivity() : activity));
            k = uWidth * 3;

            if (titleText != null && !titleText.equals("")) {
              aNative.setText(String.format("%s ", titleText));
              aNative.setGravity(Gravity.CENTER_HORIZONTAL);

              llPresentation.addView(aNative);

              LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) aNative.getLayoutParams();
              params.topMargin = (int) (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin);
              params.bottomMargin = (int) (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin);
              aNative.setLayoutParams(params);
              llBiography.measure(widthMeasureSpec, heightMeasureSpec);
              llPresentation.removeView(aNative);
              contentTextViewNatives.add(aNative);
            }

            for (int i = 0; i < presentationText.length(); i += k) {
              boolean isLastLine = false;
              if (presentationText.length() > i + uWidth * 3) {
                s += presentationText.substring(i, i + uWidth * 3);

              } else {
                s += presentationText.substring(i, presentationText.length());
                isLastLine = true;
              }
              isWhiteSpaced = false;

              do {
                if (s.length() != 0 && (!Character.isWhitespace(s.charAt(s.length() - 1)))) {
                  s = s.substring(0, s.length() - 1);
                }

                if ((Character.isWhitespace(s.charAt(s.length() - 1)))) {
                  llPresentation.removeView(aNative);
                  aNative = new ContentTextViewNativeBiography((activity == null ? getActivity() : activity));
                  aNative.setIncludeFontPadding(false);

                  aNative.setText(s);
                  llPresentation.addView(aNative);
                  llBiography.measure(widthMeasureSpec, heightMeasureSpec);
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

                  llPresentation.removeView(aNative);

                  aNative = new ContentTextViewNativeBiography((activity == null ? getActivity() : activity));
                  aNative.setIncludeFontPadding(false);
                  aNative.setText(s);
                  aNative.setCallbacks(PresentationFragment.this);
                  contentTextViewNatives.add(aNative);
                  isWhiteSpaced = true;
                } else if (isLastLine) {
                  s = presentationText.substring(i, presentationText.length());
                  k = s.length();
                  aNative = new ContentTextViewNativeBiography((activity == null ? getActivity() : activity));
                  aNative.setIncludeFontPadding(false);
                  aNative.setText(s);
                  aNative.setCallbacks(PresentationFragment.this);
                  llPresentation.addView(aNative);
                  llBiography.measure(widthMeasureSpec, heightMeasureSpec);
                  llPresentation.removeView(aNative);
                  contentTextViewNatives.add(aNative);
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

          } else {
          }

          if (llPresentation.getChildCount() <= 1) {
            for (int i = 0; i < contentTextViewNatives.size(); i++) {
              llPresentation.addView(contentTextViewNatives.get(i));
            }
          }

          animate(viewPagerNative).alpha(1).setDuration(200).start();

          LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cvPresentation.getLayoutParams();
          lp.topMargin = (int) (BiographyActivity.headerHeight + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin));
          cvPresentation.setLayoutParams(lp);

          if( !((BiographyActivity) (activity == null ? getActivity() : activity)).isTypefaceDialogShowing) {

            animate(cvPresentation).alpha(1).setDuration(200).setListener(new AnimatorListenerAdapter() {
              @Override
              public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (BiographyActivity.dBiographyLoading == null) {
                  BiographyActivity.dBiographyLoading = new Dialog((activity == null ? getActivity() : activity), R.style.myDialogSlideUpAndDown);
                  BiographyActivity.dBiographyLoading.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                  BiographyActivity.dBiographyLoading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                  BiographyActivity.dBiographyLoading.setContentView(R.layout.biography_dialog);

                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    SpinKitView spinKitView = (SpinKitView) BiographyActivity.dBiographyLoading.findViewById(R.id.spin_kit);
                    Style style = Style.FADING_CIRCLE;
                    Sprite drawable = SpriteFactory.create(style);
                    spinKitView.setIndeterminateDrawable(drawable);
                    spinKitView.setVisibility(View.VISIBLE);
                    cvBiographyDialog = (CardView) BiographyActivity.dBiographyLoading.findViewById(R.id.cv_biography_dialog);
                    cvBiographyDialog.setVisibility(View.GONE);
                  }
                }

                llBiographyHeight = 0;
                for (int i = 0; i < llPresentation.getChildCount(); i++) {

                  if (((ContentTextViewNativeBiography) llPresentation.getChildAt(i)).getText().toString().charAt(((ContentTextViewNativeBiography) llPresentation.getChildAt(i)).getText().toString().length() - 1) == '\n')
                    llBiographyHeight += uHeight * (((ContentTextViewNativeBiography) llPresentation.getChildAt(i)).lineCount + 1);
                  else if (((ContentTextViewNativeBiography) llPresentation.getChildAt(i)).getTextView().getText().toString().contains("\n\n")) {
                    if (llPresentation.getChildAt(i).getMeasuredHeight() == 0) {
                      llPresentation.getChildAt(i).measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST),
                              View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    }

                    if (llPresentation.getChildAt(i).getMeasuredHeight() > ((ContentTextViewNativeBiography) llPresentation.getChildAt(i)).lineCount)
                      llBiographyHeight += llPresentation.getChildAt(i).getMeasuredHeight();
                    else
                      llBiographyHeight += uHeight * ((ContentTextViewNativeBiography) llPresentation.getChildAt(i)).lineCount;
                  } else
                    llBiographyHeight += uHeight * ((ContentTextViewNativeBiography) llPresentation.getChildAt(i)).lineCount;
                }

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewPagerNative.getLayoutParams();
                lp.topMargin = 0;


                if (titleText != null && !titleText.equals((activity == null ? getActivity() : activity).getResources().getString(R.string.bibliography))) {

                  if (!titleText.equals("")) {
                    height = (int) (((llBiographyHeight) + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.card_elevation)) + ((int) ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin) * 5) /*+ ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_double_margin) * 2)*/));
                  } else {

                    height = (int) (((llBiographyHeight) + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.card_elevation)) + ((int) ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin) * 3) /*+ ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_double_margin) * 2)*/));
                  }

                  if(height < dislpayHeight - llBiography.getTop()) height = dislpayHeight - llBiography.getTop();

                  lp.height = (int) (height + BiographyActivity.headerHeight + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin));
                } else {
                  llBiography.measure(widthMeasureSpec, heightMeasureSpec);
                  if ((int) (((llBiographyHeight) + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.card_elevation)) + ((int) ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin) * 4) /*+ ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_double_margin) * 2)*/)) > dislpayHeight - llBiography.getTop()) {
                    height = (int) (((llBiographyHeight) + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.card_elevation)) + ((int) ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin) * 4) /*+ ((activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_double_margin) * 2)*/));
                    lp.height = (int) (height + BiographyActivity.headerHeight + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin));
                  } else {
                    height = dislpayHeight - llBiography.getTop();
                    lp.height = (int) (height + BiographyActivity.headerHeight + (activity == null ? getActivity() : activity).getResources().getDimension(R.dimen.activity_horizontal_margin));
                  }
                }

                viewPagerNative.setLayoutParams(lp);
                //BiographyActivity.dBiographyLoading.dismiss();
                if (BiographyActivity.dBiographyLoading != null && BiographyActivity.dBiographyLoading.isShowing()) {

                  BiographyActivity.dBiographyLoading.dismiss();
                  (activity == null ? getActivity() : activity).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
                }
                animate(cvPresentation).setListener(null);
              }
            }).start();
          }

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            llPresentation.getViewTreeObserver().removeOnGlobalLayoutListener(this);
          } else {
            llPresentation.getViewTreeObserver().removeGlobalOnLayoutListener(this);
          }
        }
      });
    }, 1000);
    //  ((BiographyActivity) (activity == null ? getActivity() : activity)).mObservableScrollView.smoothScrollTo(0, 0);

  }
//  public String getTitleText() {
//    return titleText;
//  }
//
//  public String getPresentationText() {
//    return presentationText;
//  }

  public String getName() {
    return name;
  }
}
