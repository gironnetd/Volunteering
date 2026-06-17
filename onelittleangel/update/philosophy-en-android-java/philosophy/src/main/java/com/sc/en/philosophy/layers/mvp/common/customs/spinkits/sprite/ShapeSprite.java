package com.sc.en.philosophy.layers.mvp.common.customs.spinkits.sprite;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;

import com.sc.en.philosophy.OnelittleAngelApplication;
import com.sc.en.philosophy.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.philosophy.layers.mvp.tablecontents.TableContentsActivity;

/**
 * Created by ybq.
 */
public abstract class ShapeSprite extends Sprite {

    private final Paint mPaint;
    private int mUseColor;
    private int mBaseColor;
    private final SharedPreferences settings;


    ShapeSprite() {
        settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
        setColor(settings.getInt(CardViewNative.DARKERRGB, 0));
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    }

    @Override
    public void setColor(int color) {
        mBaseColor = color;
        updateUseColor();
    }

    @Override
    public int getColor() {
        return settings.getInt(CardViewNative.DARKERRGB, 0);
    }

    @SuppressWarnings("unused")
    public int getUseColor() {
        return settings.getInt(CardViewNative.DARKERRGB, 0);
    }

    @Override
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
        updateUseColor();
    }

    private void updateUseColor() {
        int alpha = getAlpha();
        alpha += alpha >> 7;
        final int baseAlpha = mBaseColor >>> 24;
        final int useAlpha = baseAlpha * alpha >> 8;
        mUseColor = (mBaseColor << 8 >>> 8) | (useAlpha << 24);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    protected final void drawSelf(Canvas canvas) {
        mPaint.setColor(mUseColor);
        drawShape(canvas, mPaint);
    }

    protected abstract void drawShape(Canvas canvas, Paint paint);
}
