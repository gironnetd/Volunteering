package com.sc.fr.onelittleangel.bouddhisme.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class ActionBarCustom extends RelativeLayout {
    Typeface face;
    private ImageView favorites;
    private RelativeLayout mBarView;
    private LayoutInflater mInflater;
    private TextView title;
    private TextView title_actionBar;

    public ActionBarCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mBarView = (RelativeLayout) this.mInflater.inflate(R.layout.actionbar, (ViewGroup) null);
        addView(this.mBarView);
        this.face = Typeface.createFromAsset(getResources().getAssets(), "fonts/mtcorsva.ttf");
        this.title = (TextView) this.mBarView.findViewById(R.id.title);
        this.title.setTypeface(this.face);
        this.title_actionBar = (TextView) this.mBarView.findViewById(R.id.title_actionBar);
        this.title_actionBar.setTypeface(this.face);
        this.favorites = (ImageView) this.mBarView.findViewById(R.id.favorites);
    }

    public ActionBarCustom(Context context) {
        super(context);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mBarView = (RelativeLayout) this.mInflater.inflate(R.layout.actionbar, (ViewGroup) null);
        addView(this.mBarView);
        this.face = Typeface.createFromAsset(getResources().getAssets(), "fonts/mtcorsva.ttf");
        this.title = (TextView) this.mBarView.findViewById(R.id.title);
        this.title.setTypeface(this.face);
        this.title_actionBar = (TextView) this.mBarView.findViewById(R.id.title_actionBar);
        this.title_actionBar.setTypeface(this.face);
        this.favorites = (ImageView) this.mBarView.findViewById(R.id.favorites);
    }

    public ActionBarCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mBarView = (RelativeLayout) this.mInflater.inflate(R.layout.actionbar, (ViewGroup) null);
        addView(this.mBarView);
        this.face = Typeface.createFromAsset(getResources().getAssets(), "fonts/mtcorsva.ttf");
        this.title = (TextView) this.mBarView.findViewById(R.id.title);
        this.title.setTypeface(this.face);
        this.title_actionBar = (TextView) this.mBarView.findViewById(R.id.title_actionBar);
        this.title_actionBar.setTypeface(this.face);
        this.favorites = (ImageView) this.mBarView.findViewById(R.id.favorites);
    }

    public TextView getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public ImageView getFavorites() {
        return this.favorites;
    }

    public void setFavorites(ImageView favorites) {
        this.favorites = favorites;
    }
}
