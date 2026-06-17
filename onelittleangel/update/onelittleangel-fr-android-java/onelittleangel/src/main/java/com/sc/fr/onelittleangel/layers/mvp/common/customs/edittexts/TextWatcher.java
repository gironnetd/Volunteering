package com.sc.fr.onelittleangel.layers.mvp.common.customs.edittexts;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sc.fr.onelittleangel.OnelittleAngelApplication;
import com.sc.fr.onelittleangel.R;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.fr.onelittleangel.layers.mvp.tablecontents.TableContentsActivity;

import java.lang.reflect.Field;

public class TextWatcher implements android.text.TextWatcher {

  private final SharedPreferences settings;
  private final Activity activity;
  private final TextInputLayout textInputLayout;
  private final AppCompatEditText appCompatEditText;

  public TextWatcher(Activity activity, TextInputLayout textInputLayout, AppCompatEditText appCompatEditText) {
    this.activity = activity;
    this.textInputLayout = textInputLayout;
    this.appCompatEditText = appCompatEditText;
    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
  }

  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  public void afterTextChanged(Editable editable) {
    switch (appCompatEditText.getId()) {
//        case R.id.input_name:
//          validateName();
//          break;
      case R.id.input_email:
        validateEmail();
        break;
//      case R.id.input_password:
//        validatePassword();
//        break;
    }
  }

  private boolean validateEmail() {

    String email = appCompatEditText.getText().toString().trim();
    if (email.isEmpty() || isValidEmail(email)) {

      if(isValidEmail(email)) {
        textInputLayout.setError(OnelittleAngelApplication.instance.getString(R.string.err_msg_email_not_valid));
      }

      if(email.isEmpty()) {
        textInputLayout.setError(OnelittleAngelApplication.instance.getString(R.string.err_msg_email));
      }

      textInputLayout.setErrorEnabled(true);

      requestFocus(appCompatEditText);
      setCursorColor(appCompatEditText, settings.getInt(CardViewNative.DARKERRGB, 0));
      return false;
    } else {
      textInputLayout.setErrorEnabled(false);
    }
    return true;
  }

  private void requestFocus(View view) {
    if (view.requestFocus()) {
      activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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

//  private boolean validatePassword() {
//    if (appCompatEditText.getText().toString().trim().isEmpty()) {
//      textInputLayout.setError(OnelittleAngelApplication.instance.getString(R.string.err_msg_password));
//      textInputLayout.setErrorEnabled(true);
//
//        setErrorTextColor(textInputLayout, "mtcorsva.ttf");
//
//      requestFocus(appCompatEditText);
//      appCompatEditText.setHighlightColor(settings.getInt(CardViewNative.DARKERRGB, 0));
//
//      ColorStateList colorStateList = ColorStateList.valueOf(settings.getInt(CardViewNative.DARKERRGB, 0));
//
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//        appCompatEditText.setBackgroundTintList(colorStateList);
//      } else {
//        appCompatEditText.setSupportBackgroundTintList(colorStateList);
//      }
//      setCursorColor(appCompatEditText, settings.getInt(CardViewNative.DARKERRGB, 0));
//
//      return false;
//    } else {
//      textInputLayout.setError(null);
//      textInputLayout.setErrorEnabled(false);
//
//      ColorStateList colorStateList = ColorStateList.valueOf(settings.getInt(CardViewNative.DARKERRGB, 0));
//
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//        appCompatEditText.setBackgroundTintList(colorStateList);
//      } else {
//        appCompatEditText.setSupportBackgroundTintList(colorStateList);
//      }
//      setCursorColor(appCompatEditText, settings.getInt(CardViewNative.DARKERRGB, 0));
//    }
//    return true;
//  }


//  public static void setKeyboardFocus(final EditText primaryTextField) {
//    (new Handler()).postDelayed(new Runnable() {
//      public void run() {
//        primaryTextField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
//        primaryTextField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
//      }
//    }, 100);
//  }

  private static boolean isValidEmail(String email) {
    return TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }

  public  void setErrorTextColor(TextInputLayout textInputLayout, String typeFace) {

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
}
