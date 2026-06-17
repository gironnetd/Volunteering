package com.sc.fr.christianisme.layers.mvp.common.customs.typefaces;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.sc.fr.christianisme.OnelittleAngelApplication;
import com.sc.fr.christianisme.R;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

@SuppressLint("ParcelCreator")
public class PersonalTypefaceSpan extends TypefaceSpan implements View.OnTouchListener {

  private final Typeface newType;
  private Rect rect;

  public PersonalTypefaceSpan(Typeface type) {
    super("");
    newType = type;
  }

  @Override
  public void updateDrawState(TextPaint ds) {
    applyCustomTypeFace(ds, newType);
  }

  @Override
  public void updateMeasureState(TextPaint paint) {
    applyCustomTypeFace(paint, newType);
  }

  private static void applyCustomTypeFace(Paint paint, Typeface tf) {
    int oldStyle;
    Typeface old = paint.getTypeface();
    if (old == null) {
      oldStyle = 0;
    } else {
      oldStyle = old.getStyle();
    }

    int fake = oldStyle & ~tf.getStyle();
    if ((fake & Typeface.BOLD) != 0) {
      paint.setFakeBoldText(false);
    }

//    if ((fake & Typeface.ITALIC) != 0) {
//      paint.setTextSkewX(-0.25f);
//    }
    paint.setTextSize(OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.navigation_drawer_text_size));
    paint.setTypeface(tf);
  }

  /**
   * Called when a touch event is dispatched to a view. This allows listeners to
   * get a chance to respond before the target view.
   *
   * @param v     The view the touch event has been dispatched to.
   * @param event The MotionEvent object containing full information about
   *              the event.
   * @return True if the listener has consumed the event, false otherwise.
   */
  @Override
  public boolean onTouch(final View v, MotionEvent event) {

    switch (event.getAction()){
      case MotionEvent.ACTION_DOWN:
        rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        animate(v).setDuration(100).scaleX(1.3f).scaleY(1.3f);
        return true;
      case MotionEvent.ACTION_MOVE:
        if(!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
          animate(v)
            .setDuration(100)
            .scaleX(1)
            .scaleY(1)
            .setListener(new AnimatorListenerAdapter() {
              /**
               * {@inheritDoc}
               *
               * @param animation
               */
              @Override
              public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
              }
            });
          return false;
        }
        return true;
      case MotionEvent.ACTION_UP:
        animate(v)
          .setDuration(100)
          .scaleX(1)
          .scaleY(1)
          .setListener(new AnimatorListenerAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animator animation) {
              super.onAnimationEnd(animation);
            }
          });
        return false;
      default:
        animate(v)
          .setDuration(100)
          .scaleX(1)
          .scaleY(1)
          .setListener(new AnimatorListenerAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animator animation) {
              super.onAnimationEnd(animation);
              switch(v.getId()){
//                    case R.id.text_to_speech:
//                      String text;
//                      if(middlePage == null){
//                        text = quotes.get(1).getQuote();
//                      } else {
//                        text = middlePage.getText();
//                      }
//                      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
//                      } else {
//                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//                      }
//                      break;
              }
            }
          });
        return false;
    }
  }
}
