package com.sc.en.quotes.layers.mvp.common.graphics;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.sc.en.quotes.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.quotes.layers.mvp.tablecontents.TableContentsActivity;

public class Arrow extends View {

  private Paint mPaint;
  private Path mPath;

  private final SharedPreferences settings = getContext().getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

  public Arrow(Context context) {
    super(context);
    create();

  }

  public Arrow(Context context, AttributeSet attrs) {
    super(context, attrs);
    create();
  }

  public void setColor(int color) {
    mPaint.setColor(color);
    invalidate();
  }

  private void create() {
    mPaint = new Paint();
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setColor(settings.getInt(CardViewNative.DARKERRGB,0));
  }

  @Override
  protected void onDraw(Canvas canvas) {
    switch (getTag().toString()) {
      case "NORTH":
        mPath = calculate(Direction.NORTH);
        break;
      case "SOUTH":
        mPath = calculate(Direction.SOUTH);
        break;
      case "EAST":
        mPath = calculate(Direction.EAST);
        break;
      case "WEST":
        mPath = calculate(Direction.WEST);
        break;
    }
    canvas.drawPath(mPath, mPaint);
  }

  private Path calculate(Direction direction) {
    Point p1 = new Point();

    Point p2 = null, p3 = null;
    int width = getWidth();
    int height = getHeight();

    if (direction == Direction.NORTH) {
      p1.x = 0;
      p1.y = height - height / 4;
      p2 = new Point(width, p1.y);
      p3 = new Point(width / 2, height / 4);
    } else if (direction == Direction.SOUTH) {
      p1.x = 0;
      p1.y = height / 4;
      p2 = new Point(p1.x + width, p1.y);
      p3 = new Point(p1.x + width / 2, height - p1.y);
    } else if (direction == Direction.EAST) {
      p1.x = width / 4;
      p1.y = 0;
      p2 = new Point(width - p1.x, p1.y + height / 2);
      p3 = new Point(p1.x, height);
    } else if (direction == Direction.WEST) {
      p1.x = width - width / 4;
      p1.y = 0;
      p2 = new Point(p1.x, p1.y + height);
      p3 = new Point(width / 4, p1.y + height / 2);
    }

    Path path = new Path();
    path.moveTo(p1.x, p1.y);

  //  assert p2 != null;
    if(p2 != null) {
      path.lineTo(p2.x, p2.y);
    }

    if(p3 != null){
    path.lineTo(p3.x, p3.y);
    }

    return path;
  }

  public enum Direction {
    NORTH, SOUTH, EAST, WEST
  }

}
