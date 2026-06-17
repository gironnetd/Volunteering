package com.sc.en.bouddhism.layers.mvp.common.customs.circle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.View;

import com.sc.en.bouddhism.OnelittleAngelApplication;
import com.sc.en.bouddhism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.bouddhism.layers.mvp.common.customs.cardviews.CardViewNative;

public class Circle extends View {

  private Bitmap bitmap;

  public Circle(Context context) {
    super(context);
  }

  public Circle(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public Circle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }


  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public Circle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }



  @Override
  protected void onDraw(Canvas canvas) {
//    paint = new Paint();
//    paint.setStyle(Paint.Style.FILL);
//    paint.setColor(Color.GREEN);
    SharedPreferences settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

    Paint paint = new Paint();

    if (bitmap != null) {

      if (getTag().equals("LEFT")) {

        Palette palette = Palette.from(bitmap).generate();
        paint.setColor(palette.getLightVibrantColor(palette.getDominantSwatch().getRgb()));
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setShader(new RadialGradient(getWidth(), getHeight(),
                getHeight(), palette.getLightVibrantColor(palette.getDominantSwatch().getRgb()), palette.getLightVibrantColor(palette.getDominantSwatch().getRgb()), Shader.TileMode.MIRROR));

        canvas.drawCircle(0, getHeight(),
                getHeight(), paint);
      }

      if (getTag().equals("RIGHT")) {
        Palette palette = Palette.from(bitmap).generate();
        paint.setColor(palette.getLightVibrantColor(palette.getDominantSwatch().getRgb()));
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setShader(new RadialGradient(getWidth(), getHeight(),
                getHeight(), palette.getLightVibrantColor(palette.getDominantSwatch().getRgb()), palette.getLightVibrantColor(palette.getDominantSwatch().getRgb()), Shader.TileMode.MIRROR));

        canvas.drawCircle(getWidth(), getHeight(),
                getHeight(), paint);
      }
    } else {

      if (getTag().equals("LEFT")) {

        paint.setColor(settings.getInt(CardViewNative.DARKERRGB, 0));
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setShader(new RadialGradient(0, getHeight(),
                getHeight(), settings.getInt(CardViewNative.DARKERRGB, 0), settings.getInt(CardViewNative.DARKERRGB, 0), Shader.TileMode.MIRROR));

        canvas.drawCircle(0, getHeight(),
                getHeight(), paint);
      }

      if (getTag().equals("RIGHT")) {

        paint.setColor(settings.getInt(CardViewNative.DARKERRGB, 0));
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setShader(new RadialGradient(getWidth(), getHeight(),
                getHeight(), settings.getInt(CardViewNative.DARKERRGB, 0), settings.getInt(CardViewNative.DARKERRGB, 0), Shader.TileMode.MIRROR));

        canvas.drawCircle(getWidth(), getHeight(),
                getHeight(), paint);
      }
    }
    //super.onDraw(canvas);
//    canvas.drawCircle(getWidth(), getHeight(),
//            getHeight(), paint);
  }

  public void setBitmap(Bitmap bitmap) {
    this.bitmap = bitmap;
    invalidate();
  }
}
