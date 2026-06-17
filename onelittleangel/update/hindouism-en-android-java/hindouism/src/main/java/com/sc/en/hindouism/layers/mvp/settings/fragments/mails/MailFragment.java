package com.sc.en.hindouism.layers.mvp.settings.fragments.mails;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.sc.en.hindouism.OnelittleAngelApplication;
import com.sc.en.hindouism.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.hindouism.layers.mvp.common.customs.edittexts.TextWatcher;
import com.sc.en.hindouism.layers.mvp.common.utils.Constants;
import com.sc.en.hindouism.layers.mvp.settings.SettingsActivity;
import com.sc.en.hindouism.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.en.hindouism.layers.mvp.settings.fragments.BaseFragment;
import com.sc.en.hindouism.layers.mvp.settings.viewpager.ViewPagerNative;
import com.sc.en.hindouism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.hindouism.R;
import com.sc.en.hindouism.injector.PresenterInjector;
//import BaseFragment;

import java.lang.reflect.Field;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class MailFragment extends BaseFragment implements MailViewInterface {

  private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

  private boolean mHasInflated = false;
  private Rect rect;
  private SharedPreferences settings;
  //SharedPreferences.Editor editor;
  public AppCompatEditText inputName;
  private AppCompatEditText inputEmail;
  public TextInputLayout inputLayoutName;
  private TextInputLayout inputLayoutEmail;
  private TextViewNative txtAccountName;
  private TextViewNative txtLabelIdentifier;
  private TextViewNative txtIdentifier;
  private TextViewNative txtConnect;
  private CardView cvActionOnForm;
  public CardView cvAccountContainer;
  private int height ;

  /***********************************************************
   *  Presenter
   **********************************************************/

  /**
   * The Presenter associated with that view
   */
  private MailPresenterInterface presenter=null;

  public static MailFragment newInstance() {

    MailFragment mailFragment = new MailFragment();
    Bundle args = new Bundle();
    args.putString(TYPE, "mails");
    mailFragment.setArguments(args);
    return mailFragment;
  }

  public static MailFragment newInstance(String mail, boolean isForInit) {
    MailFragment mailFragment = new MailFragment();
    Bundle args = new Bundle();
    args.putString(TYPE, mail);
    mailFragment.setArguments(args);
    return mailFragment;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    presenter = null;
    settings = null;
    //editor = null;
    inputEmail = null;
    txtAccountName = txtLabelIdentifier = txtIdentifier = txtConnect = null;
    cvActionOnForm = cvAccountContainer = null;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    type = getArguments().getString(TYPE);

    if(presenter == null)
      presenter = PresenterInjector.getMailPresenter(this);
  }

  @Override
  protected void onCreateViewAfterViewStubInflated(View inflatedView, Bundle savedInstanceState) {
    mHasInflated = true;
    getHasInflated();

    this.container.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
    if (inflatedView != null) {
      cvAccountContainer = (CardView) inflatedView.findViewById(R.id.cv_account_container);
      ViewHelper.setAlpha(cvAccountContainer, 0);
      txtAccountName = (TextViewNative) inflatedView.findViewById(R.id.social_network_name);
      txtAccountName.setText(OnelittleAngelApplication.instance.getResources().getString(R.string.mail));
      txtLabelIdentifier = (TextViewNative) inflatedView.findViewById(R.id.txt_label_identifier);
      txtIdentifier = (TextViewNative) inflatedView.findViewById(R.id.txt_identifer);

      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      //editor = settings.edit();
      txtConnect = (TextViewNative) inflatedView.findViewById(R.id.connect_txtn);
      txtConnect.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
      cvActionOnForm = (CardView) inflatedView.findViewById(R.id.cv_action_on_form);

      ColorStateList colorStateList = ColorStateList.valueOf(settings.getInt(CardViewNative.DARKERRGB, 0));

      inputLayoutEmail = (TextInputLayout) inflatedView.findViewById(R.id.input_layout_email);
      inputEmail = (AppCompatEditText) inflatedView.findViewById(R.id.input_email);
      inputEmail.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
      inputEmail.setHighlightColor(settings.getInt(CardViewNative.DARKERRGB, 0));
      inputEmail.setHintTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
      setCursorColor(inputEmail, settings.getInt(CardViewNative.DARKERRGB, 0));

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        inputEmail.setBackgroundTintList(colorStateList);
      }
      inputEmail.addTextChangedListener(new TextWatcher(getActivity(), inputLayoutEmail, inputEmail));

      txtConnect.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
          switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
              animate(cvActionOnForm).setDuration(100).scaleX(1.12f).scaleY(1.12f);
              animate(txtConnect).setDuration(100).scaleX(1.05f).scaleY(1.05f);

              return true;
            case MotionEvent.ACTION_MOVE:
              return true;
            case MotionEvent.ACTION_UP:
              animate(txtConnect).setDuration(100).scaleX(1).scaleY(1)
                      .setListener(new AnimatorListenerAdapter() {
                        /**
                         * {@inheritDoc}
                         *
                         * @param animation
                         */
                        @Override
                        public void onAnimationEnd(Animator animation) {
                          super.onAnimationEnd(animation);
                          submitForm();
                        }
                      });
              animate(cvActionOnForm).setDuration(100).scaleX(1).scaleY(1)
                      .setListener(new AnimatorListenerAdapter() {
                        /**
                         * {@inheritDoc}
                         *
                         * @param animation
                         */
                        @Override
                        public void onAnimationEnd(Animator animation) {
                          super.onAnimationEnd(animation);
                          submitForm();
                        }
                      });
              return false;
            default:
              animate(txtConnect).setDuration(100).scaleX(1).scaleY(1);
              animate(cvActionOnForm).setDuration(100).scaleX(1).scaleY(1);
              return false;
          }
        }
      });

      setTypefaceToInputLayout(inputLayoutEmail);
      setErrorTextColor(inputLayoutEmail, "Monotype-Corsiva-Regular.ttf");
    }
    updateMailAccount();

    DisplayMetrics displaymetrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    height = displaymetrics.heightPixels;
    int width = displaymetrics.widthPixels;

    int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

    ViewPagerNative viewPager = (ViewPagerNative) getActivity().findViewById(R.id.viewpager);
    LinearLayout headerContainer = (LinearLayout) getActivity().findViewById(R.id.container);
    viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) cvAccountContainer.getLayoutParams();
        if (!((SettingsActivity) getActivity()).isFromMailOrTypefaceSetings) {

          lp.height = (int) (height);

          layoutParams.topMargin = SettingsActivity.headerHeight;
        }
        else {
          lp.height = (int) (height);

          layoutParams.topMargin = SettingsActivity.rlNumberHeight /*- SettingsActivity.rlNumberHeight*/;
        }

//        if (!((SettingsActivity) getActivity()).isFromMailOrTypefaceSetings) layoutParams.topMargin = SettingsActivity.headerHeight;
//        else layoutParams.topMargin = SettingsActivity.headerHeight - SettingsActivity.rlNumberHeight;
//        cvAccountContainer.setLayoutParams(layoutParams);layoutParams.topMargin = SettingsActivity.headerHeight;
//        else layoutParams.topMargin = SettingsActivity.headerHeight - SettingsActivity.rlNumberHeight;
        cvAccountContainer.setLayoutParams(layoutParams);

        viewPager.setLayoutParams(lp);
        headerContainer.bringToFront();
        cvAccountContainer.setVisibility(View.VISIBLE);
        animate(cvAccountContainer).alpha(1).setDuration(600).start();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
          viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
          viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
      }
    });
  }

  @Override
  protected void afterViewStubInflated(View originalViewContainerWithViewStub) {
    super.afterViewStubInflated(originalViewContainerWithViewStub);
  }

  private void setErrorTextColor(TextInputLayout textInputLayout, String typeFace) {

    final Typeface tf = Typeface.createFromAsset(OnelittleAngelApplication.instance.getAssets(),"fonts/" + typeFace);

    try {
      Field fErrorView = TextInputLayout.class.getDeclaredField("mErrorView");
      fErrorView.setAccessible(true);
      TextView mErrorView = (TextView) fErrorView.get(textInputLayout);
      Field fCurTextColor = TextView.class.getDeclaredField("mCurTextColor");
      fCurTextColor.setAccessible(true);
      fCurTextColor.set(mErrorView, settings.getInt(CardViewNative.DARKERRGB, 0));
      mErrorView.setTypeface(tf);
      mErrorView.setTextSize(20);
      mErrorView.requestLayout();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setTypefaceToInputLayout(TextInputLayout inputLayout){

    final Typeface tf = Typeface.createFromAsset(OnelittleAngelApplication.instance.getAssets(),"fonts/" + "Monotype-Corsiva-Regular.ttf");

    inputLayout.getEditText().setTypeface(tf);
    inputLayout.setTypeface(tf);
    try {
      // Retrieve the CollapsingTextHelper Field
      final Field collapsingTextHelperField = inputLayout.getClass().getDeclaredField("mCollapsingTextHelper");
      collapsingTextHelperField.setAccessible(true);

      // Retrieve an instance of CollapsingTextHelper and its TextPaint
      final Object collapsingTextHelper = collapsingTextHelperField.get(inputLayout);
      final Field tpf = collapsingTextHelper.getClass().getDeclaredField("mTextPaint");
      tpf.setAccessible(true);

      // Apply your Typeface to the CollapsingTextHelper TextPaint
      ((TextPaint) tpf.get(collapsingTextHelper)).setTypeface(tf);
      PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(settings.getInt(CardViewNative.DARKERRGB, 0), PorterDuff.Mode.SRC_ATOP);

      ((TextPaint) tpf.get(collapsingTextHelper)).setColorFilter(colorFilter);
    } catch (Exception ignored) {
      // Nothing to do
    }
  }

  private static void setCursorColor(AppCompatEditText view, @ColorInt int color) {
    try {
      // Get the cursor resource id
      Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
      field.setAccessible(true);
      int drawableResId = field.getInt(view);

      // Get the editor
      field = TextView.class.getDeclaredField("mEditor");
      field.setAccessible(true);
      Object editor = field.get(view);

      // Get the drawable and set a color filter
      Drawable drawable = ContextCompat.getDrawable(view.getContext(), drawableResId);
      drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
      Drawable[] drawables = {drawable, drawable};

      // Set the drawables
      field = editor.getClass().getDeclaredField("mCursorDrawable");
      field.setAccessible(true);
      field.set(editor, drawables);
    } catch (Exception ignored) {
    }
  }

  private boolean validateEmail() {
    String email = inputEmail.getText().toString().trim();
    if (email.isEmpty() || !isValidEmail(email)) {
      inputLayoutEmail.setError(getString(R.string.err_msg_email));
      inputLayoutEmail.setErrorEnabled(true);
      if(settings.getString(Constants.TYPEFACE, "") != null && !settings.getString(Constants.TYPEFACE, "").equals("")) {
        setErrorTextColor(inputLayoutEmail, "" + settings.getString(Constants.TYPEFACE, "") + ".ttf");
      } else {
        setErrorTextColor(inputLayoutEmail, "Monotype-Corsiva-Regular.ttf");
      }
      requestFocus(inputEmail);
      setCursorColor(inputEmail, settings.getInt(CardViewNative.DARKERRGB, 0));

      return false;
    } else {
      inputLayoutEmail.setErrorEnabled(false);
    }
    return true;
  }

  /**
   * Validating form
   */
  private void submitForm() {
    if (!validateEmail()) {
      return;
    }
    settings.edit().putString(Constants.MAIL_ACCOUNT,inputEmail.getText().toString().trim()).apply();
    //settings.edit().commit();
    updateMailAccount();
  }

  private static boolean isValidEmail(String email) {
    return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }

  private void requestFocus(View view) {
    if (view.requestFocus()) {
      getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
  }

  @Override
  public String getFragmentType() {
    return type;
  }

  @Override
  public boolean getHasInflated() {
    return mHasInflated;
  }

  @Override
  protected int getViewStubLayoutResource() {
    return R.layout.account_viewstub;
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.base_fragment;
  }

  @Override
  protected void populateViewForOrientation(LayoutInflater inflater) {
    ViewGroup viewGroup = (ViewGroup) getView();
    viewGroup.removeAllViewsInLayout();
    View view = inflater.inflate(R.layout.viewstub, viewGroup, false);
    viewGroup.addView(view);
    mHasInflated = false;
    getHasInflated();
  }

  @Override
  public void updateMailAccount() {

    if(settings.getString(Constants.MAIL_ACCOUNT, "") != null && !settings.getString(Constants.MAIL_ACCOUNT, "").equals("")) {

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
      }

      inputEmail.setVisibility(View.GONE);
      inputLayoutEmail.setVisibility(View.GONE);
      txtLabelIdentifier.setVisibility(View.VISIBLE);
      txtIdentifier.setVisibility(View.VISIBLE);
      txtIdentifier.setText(String.format("%s ", settings.getString(Constants.MAIL_ACCOUNT, "")));
      txtConnect.setText(OnelittleAngelApplication.instance.getResources().getString(R.string.change_account));

      txtConnect.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
          switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
              animate(cvActionOnForm).setDuration(100).scaleX(1.12f).scaleY(1.12f);
              animate(txtConnect).setDuration(100).scaleX(1.05f).scaleY(1.05f);

              return true;
            case MotionEvent.ACTION_MOVE:
              return true;
            case MotionEvent.ACTION_UP:
              animate(txtConnect).setDuration(100).scaleX(1).scaleY(1)
                      .setListener(new AnimatorListenerAdapter() {
                        /**
                         * {@inheritDoc}
                         *
                         * @param animation
                         */
                        @Override
                        public void onAnimationEnd(Animator animation) {
                          super.onAnimationEnd(animation);
                          inputEmail.setVisibility(View.VISIBLE);
                          inputLayoutEmail.setVisibility(View.VISIBLE);
                          txtLabelIdentifier.setVisibility(View.GONE);
                          txtIdentifier.setVisibility(View.GONE);
                          txtConnect.setText(OnelittleAngelApplication.instance.getResources().getString(R.string.record_account));
                          inputLayoutEmail.requestFocus();
                          txtConnect.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                              switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                  animate(cvActionOnForm).setDuration(100).scaleX(1.12f).scaleY(1.12f);
                                  animate(txtConnect).setDuration(100).scaleX(1.05f).scaleY(1.05f);

                                  return true;
                                case MotionEvent.ACTION_MOVE:
                                  return true;
                                case MotionEvent.ACTION_UP:
                                  animate(txtConnect).setDuration(100).scaleX(1).scaleY(1)
                                          .setListener(new AnimatorListenerAdapter() {
                                            /**
                                             * {@inheritDoc}
                                             *
                                             * @param animation
                                             */
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                              super.onAnimationEnd(animation);
                                              submitForm();
                                            }
                                          });
                                  animate(cvActionOnForm).setDuration(100).scaleX(1).scaleY(1)
                                          .setListener(new AnimatorListenerAdapter() {
                                            /**
                                             * {@inheritDoc}
                                             *
                                             * @param animation
                                             */
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                              super.onAnimationEnd(animation);
                                              submitForm();
                                            }
                                          });
                                  return false;
                                default:
                                  animate(txtConnect).setDuration(100).scaleX(1).scaleY(1);
                                  animate(cvActionOnForm).setDuration(100).scaleX(1).scaleY(1);
                                  return false;
                              }
                            }
                          });

                        }
                      });
              animate(cvActionOnForm).setDuration(100).scaleX(1).scaleY(1)
                      .setListener(new AnimatorListenerAdapter() {
                        /**
                         * {@inheritDoc}
                         *
                         * @param animation
                         */
                        @Override
                        public void onAnimationEnd(Animator animation) {
                          super.onAnimationEnd(animation);
                          inputEmail.setVisibility(View.VISIBLE);
                          inputLayoutEmail.setVisibility(View.VISIBLE);
                          txtLabelIdentifier.setVisibility(View.GONE);
                          txtIdentifier.setVisibility(View.GONE);
                          txtConnect.setText(OnelittleAngelApplication.instance.getResources().getString(R.string.record_account));
                          inputLayoutEmail.requestFocus();
                          txtConnect.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                              switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                  animate(cvActionOnForm).setDuration(100).scaleX(1.12f).scaleY(1.12f);
                                  animate(txtConnect).setDuration(100).scaleX(1.05f).scaleY(1.05f);

                                  return true;
                                case MotionEvent.ACTION_MOVE:
                                  return true;
                                case MotionEvent.ACTION_UP:
                                  animate(txtConnect).setDuration(100).scaleX(1).scaleY(1)
                                          .setListener(new AnimatorListenerAdapter() {
                                            /**
                                             * {@inheritDoc}
                                             *
                                             * @param animation
                                             */
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                              super.onAnimationEnd(animation);
                                              submitForm();
                                            }
                                          });
                                  animate(cvActionOnForm).setDuration(100).scaleX(1).scaleY(1)
                                          .setListener(new AnimatorListenerAdapter() {
                                            /**
                                             * {@inheritDoc}
                                             *
                                             * @param animation
                                             */
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                              super.onAnimationEnd(animation);
                                              submitForm();
                                            }
                                          });
                                  return false;
                                default:
                                  animate(txtConnect).setDuration(100).scaleX(1).scaleY(1);
                                  animate(cvActionOnForm).setDuration(100).scaleX(1).scaleY(1);
                                  return false;
                              }
                            }
                          });

                        }
                      });
              return false;
            default:
              animate(txtConnect).setDuration(100).scaleX(1).scaleY(1);
              animate(cvActionOnForm).setDuration(100).scaleX(1).scaleY(1);
              return false;
          }
        }
      });

      txtConnect.setOnClickListener(v -> {
        inputEmail.setVisibility(View.VISIBLE);
        inputLayoutEmail.setVisibility(View.VISIBLE);
        txtLabelIdentifier.setVisibility(View.GONE);
        txtIdentifier.setVisibility(View.GONE);
        txtConnect.setText(OnelittleAngelApplication.instance.getResources().getString(R.string.record_account));
        inputLayoutEmail.requestFocus();
        txtConnect.setOnClickListener(v1 -> submitForm());
      });

    } else if(settings.getString(Constants.MAIL_ACCOUNT, "") == null || settings.getString(Constants.MAIL_ACCOUNT, "").equals("")) {
      inputEmail.setVisibility(View.VISIBLE);
      inputLayoutEmail.setVisibility(View.VISIBLE);
      inputLayoutEmail.requestFocus();
      txtLabelIdentifier.setVisibility(View.GONE);
      txtIdentifier.setVisibility(View.GONE);
      txtConnect.setText(OnelittleAngelApplication.instance.getResources().getString(R.string.record_account));
    }
  }
}
