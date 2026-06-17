package com.sc.en.taoism.layers.mvp.biography.carousel.panel;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;
import com.sc.en.taoism.R;

public class ImagePanel extends BasePanel {

    private ImageView mImageViewHolder;

  //Constructors
    public ImagePanel(Context context) {
        this(context, null);
    }

    public ImagePanel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImagePanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initImagePanel();
    }

    public void setImageBitmap(Bitmap bm) {
        mImageViewHolder = new ImageView(getContext());
        mImageViewHolder.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageViewHolder.setImageResource(R.mipmap.ic_launcher);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    //    LayoutParams lp = new LayoutParams(bm.getWidth() * 3, bm.getHeight() * 3);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        mImageViewHolder.setLayoutParams(lp);
        mPanelContainer.addView(mImageViewHolder);
        mImageViewHolder.setImageBitmap(bm);
        ViewHelper.setAlpha(mImageViewHolder, 1);
    }

    public void setImageResId(int resId) {
        mImageViewHolder.setImageResource(resId);
    }

    /* ***************************************************************************** */
    /* ******************************** Utility API ******************************** */
    /* ***************************************************************************** */

    private void initImagePanel() {
//        mImageViewHolder = new ImageView(getContext());
//        mImageViewHolder.setScaleType(ImageView.ScaleType.FIT_XY);
//        mImageViewHolder.setImageResource(R.mipmap.ic_launcher);
////        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        LayoutParams lp = new LayoutParams(bm.getWidth(), bm.getHeight());
//        mImageViewHolder.setLayoutParams(lp);
//        mPanelContainer.addView(mImageViewHolder);
    }
}
