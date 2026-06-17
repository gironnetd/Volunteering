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
public class ActionBarMenusCustom extends RelativeLayout {
    Typeface face;
    private ImageView logo;
    private RelativeLayout mBarView;
    private LayoutInflater mInflater;
    private TextView title;

    public ActionBarMenusCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mBarView = (RelativeLayout) this.mInflater.inflate(R.layout.actionbar_menus, (ViewGroup) null);
        addView(this.mBarView);
        this.face = Typeface.createFromAsset(getResources().getAssets(), "fonts/mtcorsva.ttf");
        this.title = (TextView) this.mBarView.findViewById(R.id.title);
        this.title.setText("OneLittleAngel");
        this.title.setTypeface(this.face);
    }

    public ActionBarMenusCustom(Context context) {
        super(context);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mBarView = (RelativeLayout) this.mInflater.inflate(R.layout.actionbar_menus, (ViewGroup) null);
        addView(this.mBarView);
        this.face = Typeface.createFromAsset(getResources().getAssets(), "fonts/mtcorsva.ttf");
        this.title = (TextView) this.mBarView.findViewById(R.id.title);
        this.title.setText("OneLittleAngel");
        this.title.setTypeface(this.face);
    }

    public ActionBarMenusCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mBarView = (RelativeLayout) this.mInflater.inflate(R.layout.actionbar_menus, (ViewGroup) null);
        addView(this.mBarView);
        this.face = Typeface.createFromAsset(getResources().getAssets(), "fonts/mtcorsva.ttf");
        this.title = (TextView) this.mBarView.findViewById(R.id.title);
        this.title.setText("OneLittleAngel");
        this.title.setTypeface(this.face);
    }

    public TextView getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public ImageView getLogo() {
        return this.logo;
    }

    public void setLogo(ImageView logo) {
        this.logo = logo;
    }
}
