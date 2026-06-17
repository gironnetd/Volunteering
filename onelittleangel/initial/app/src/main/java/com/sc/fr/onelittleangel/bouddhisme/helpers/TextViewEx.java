package com.sc.fr.onelittleangel.bouddhisme.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
@TargetApi(16)
public class TextViewEx extends TextView {
    private String block;
    private String[] blocks;
    int bottom;
    private Bitmap cache;
    private boolean cacheEnabled;
    private float dirtyRegionWidth;
    private float horizontalFontOffset;
    private float horizontalOffset;
    int left;
    private String[] lineAsWords;
    private Paint paint;
    int right;
    private float spaceOffset;
    private float strecthOffset;
    int top;
    private float verticalOffset;
    private boolean wrapEnabled;
    private float wrappedEdgeSpace;
    private String wrappedLine;
    private Object[] wrappedObj;

    public TextViewEx(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.paint = new Paint();
        this.spaceOffset = 0.0f;
        this.horizontalOffset = 0.0f;
        this.verticalOffset = 0.0f;
        this.horizontalFontOffset = 0.0f;
        this.dirtyRegionWidth = 0.0f;
        this.wrapEnabled = false;
        this.bottom = 0;
        this.cache = null;
        this.cacheEnabled = false;
        setPadding(10, 0, 10, 0);
    }

    public TextViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint();
        this.spaceOffset = 0.0f;
        this.horizontalOffset = 0.0f;
        this.verticalOffset = 0.0f;
        this.horizontalFontOffset = 0.0f;
        this.dirtyRegionWidth = 0.0f;
        this.wrapEnabled = false;
        this.bottom = 0;
        this.cache = null;
        this.cacheEnabled = false;
        setPadding(10, 0, 10, 0);
    }

    public TextViewEx(Context context) {
        super(context);
        this.paint = new Paint();
        this.spaceOffset = 0.0f;
        this.horizontalOffset = 0.0f;
        this.verticalOffset = 0.0f;
        this.horizontalFontOffset = 0.0f;
        this.dirtyRegionWidth = 0.0f;
        this.wrapEnabled = false;
        this.bottom = 0;
        this.cache = null;
        this.cacheEnabled = false;
        setPadding(10, 0, 10, 0);
    }

    @Override // android.widget.TextView, android.view.View
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
    }

    @Override // android.view.View
    public void setDrawingCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    public void setText(String st, boolean wrap) {
        this.wrapEnabled = wrap;
        super.setText(st);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        Canvas activeCanvas;
        if (!this.wrapEnabled) {
            super.onDraw(canvas);
            return;
        }
        if (this.cacheEnabled) {
            if (this.cache != null) {
                canvas.drawBitmap(this.cache, 0.0f, 0.0f, this.paint);
                return;
            } else {
                this.cache = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
                activeCanvas = new Canvas(this.cache);
            }
        } else {
            activeCanvas = canvas;
        }
        this.paint.setColor(getCurrentTextColor());
        this.paint.setTypeface(getTypeface());
        this.paint.setTextSize(getTextSize());
        this.dirtyRegionWidth = (getWidth() - getPaddingLeft()) - getPaddingRight();
        int maxLines = Integer.MAX_VALUE;
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= 16) {
            maxLines = getMaxLines();
        }
        int lines = 1;
        this.blocks = getText().toString().split("((?<=\n)|(?=\n))");
        float lineHeight = getLineHeight() - 0.5f;
        this.horizontalFontOffset = lineHeight;
        this.verticalOffset = lineHeight;
        this.spaceOffset = this.paint.measureText(" ");
        int i = 0;
        while (i < this.blocks.length && lines <= maxLines) {
            this.block = this.blocks[i];
            this.horizontalOffset = 0.0f;
            if (this.block.length() != 0) {
                if (this.block.equals("\n")) {
                    this.verticalOffset += this.horizontalFontOffset;
                } else {
                    this.block = this.block.trim();
                    if (this.block.length() != 0) {
                        this.wrappedObj = TextJustifyUtils.createWrappedLine(this.block, this.paint, this.spaceOffset, this.dirtyRegionWidth);
                        this.wrappedLine = (String) this.wrappedObj[0];
                        this.wrappedEdgeSpace = ((Float) this.wrappedObj[1]).floatValue();
                        this.lineAsWords = this.wrappedLine.split(" ");
                        this.strecthOffset = this.wrappedEdgeSpace != Float.MIN_VALUE ? this.wrappedEdgeSpace / (this.lineAsWords.length - 1) : 0.0f;
                        for (int j = 0; j < this.lineAsWords.length; j++) {
                            String word = this.lineAsWords[j];
                            if (lines == maxLines && j == this.lineAsWords.length - 1) {
                                activeCanvas.drawText("...", this.horizontalOffset, this.verticalOffset, this.paint);
                            } else if (j == 0) {
                                activeCanvas.drawText(word, getPaddingLeft(), this.verticalOffset, this.paint);
                                this.horizontalOffset += getPaddingLeft();
                            } else {
                                activeCanvas.drawText(word, this.horizontalOffset, this.verticalOffset, this.paint);
                            }
                            this.horizontalOffset += this.paint.measureText(word) + this.spaceOffset + this.strecthOffset;
                        }
                        lines++;
                        if (this.blocks[i].length() > 0) {
                            this.blocks[i] = this.blocks[i].substring(this.wrappedLine.length());
                            this.verticalOffset = (this.blocks[i].length() > 0 ? this.horizontalFontOffset : 0.0f) + this.verticalOffset;
                            i--;
                        }
                    }
                }
            }
            i++;
        }
        if (this.cacheEnabled) {
            canvas.drawBitmap(this.cache, 0.0f, 0.0f, this.paint);
        }
    }
}
