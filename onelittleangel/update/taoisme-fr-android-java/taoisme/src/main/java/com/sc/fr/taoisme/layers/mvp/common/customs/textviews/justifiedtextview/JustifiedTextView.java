/*
 * Copyright (C) 2013 UNCOPT LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sc.fr.taoisme.layers.mvp.common.customs.textviews.justifiedtextview;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * A TextView with justified text.<br>
 * The TextView has a ScrollingMovementMethod by default. You can change the MovementMethod,
 * but you should not set it to null.
 */
public class JustifiedTextView extends android.support.v7.widget.AppCompatTextView implements Justify.Justified {

  private String lastLine;
  public int lastLinePosition;
  public int lineCount;
  private boolean isInit = false;

  @SuppressWarnings("unused")
  public JustifiedTextView(final @NotNull Context context) {
    super(context);
    super.setMovementMethod(new LinkMovementMethod());
  }

  @SuppressWarnings("unused")
  public JustifiedTextView(final @NotNull Context context, final AttributeSet attrs) {
    super(context, attrs);
    if (getMovementMethod() == null) super.setMovementMethod(new LinkMovementMethod());
  }

  @SuppressWarnings("unused")
  public JustifiedTextView(final @NotNull Context context,
                           final AttributeSet attrs, final int defStyle) {
    super(context, attrs, defStyle);
    if (getMovementMethod() == null) super.setMovementMethod(new LinkMovementMethod());
  }

  @Override
  public void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    // Make sure we don't call setupScaleSpans again if the measure was triggered
    // by setupScaleSpans itself.
    if (!mMeasuring) {
      final Typeface typeface = getTypeface();
      final float textSize = getTextSize();
      final float textScaleX = getTextScaleX();
      final boolean fakeBold = getPaint().isFakeBoldText();
      if (mTypeface != typeface ||
          mTextSize != textSize ||
          mTextScaleX != textScaleX ||
          mFakeBold != fakeBold) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        if (width > 0 && width != mWidth && !isInit) {
          //if(mTypeface != typeface) setTypeface(typeface);
          mTypeface = typeface;
          mTextSize = textSize;
          mTextScaleX = textScaleX;
          mFakeBold = fakeBold;
          mWidth = width;
          mMeasuring = true;

          try {
            //  Log.i("10", " onMeasure JustifiedTextView ");
            // Setup ScaleXSpans on whitespaces to justify the text.
            isInit = true;
            Justify.setupScaleSpans(this, mSpanStarts, mSpanEnds, mSpans);
            lastLine = Justify.getLastLine();
            lastLinePosition = Justify.getLastLinePosition();
            lineCount = Justify.getRealLineCount();
          }
          finally {
            mMeasuring = false;
          }
        }
      }
    }
  }

  @Override
  protected void onTextChanged(final CharSequence text,
                               final int start, final int lengthBefore, final int lengthAfter) {
    super.onTextChanged(text, start, lengthBefore, lengthAfter);
    final Layout layout = getLayout();
    if (layout != null) {
    //    Log.i("10", " onMeasure onTextChanged ");

      Justify.setupScaleSpans(this, mSpanStarts, mSpanEnds, mSpans);
      lastLine = Justify.getLastLine();
      lastLinePosition = Justify.getLastLinePosition();
      lineCount = Justify.getRealLineCount();
    }
  }

  public boolean isInit() {
    return isInit;
  }

  public void setInit() {
    isInit = false;
  }

  @Override
  @NotNull
  public TextView getTextView() {
    return this;
  }

  @Override
  public float getMaxProportion() {
    return Justify.DEFAULT_MAX_PROPORTION;
  }

  private static final int MAX_SPANS = 5124;

  private boolean mMeasuring = false;

  private Typeface mTypeface = null;
  private float mTextSize = 0f;
  private float mTextScaleX = 0f;
  private boolean mFakeBold = false;
  private int mWidth = 0;

  private final int[] mSpanStarts = new int[MAX_SPANS];
  private final int[] mSpanEnds = new int[MAX_SPANS];
  private final Justify.ScaleSpan[] mSpans = new Justify.ScaleSpan[MAX_SPANS];

}
