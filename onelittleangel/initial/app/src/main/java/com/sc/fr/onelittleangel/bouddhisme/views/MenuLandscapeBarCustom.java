package com.sc.fr.onelittleangel.bouddhisme.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class MenuLandscapeBarCustom extends RelativeLayout {
    private ImageView button_back_landscape;
    private ImageView button_forward_landscape;
    Typeface face;
    private ImageView help;
    private RelativeLayout mBarView;
    private LayoutInflater mInflater;
    private ImageView r;
    private ImageView textFont;
    private ImageView textLess;
    private ImageView textMore;

    public MenuLandscapeBarCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mBarView = (RelativeLayout) this.mInflater.inflate(R.layout.menubarlandscape, (ViewGroup) null);
        addView(this.mBarView);
        this.r = (ImageView) findViewById(R.id.back);
        this.textFont = (ImageView) findViewById(R.id.textfont);
        this.button_back_landscape = (ImageView) findViewById(R.id.button_back_landscape);
        this.button_forward_landscape = (ImageView) findViewById(R.id.button_forward_landscape);
        this.textLess = (ImageView) findViewById(R.id.textsizeless);
        this.textMore = (ImageView) findViewById(R.id.textsizemore);
        this.help = (ImageView) findViewById(R.id.help);
    }

    public MenuLandscapeBarCustom(Context context) {
        super(context);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mBarView = (RelativeLayout) this.mInflater.inflate(R.layout.menubarlandscape, (ViewGroup) null);
        addView(this.mBarView);
        this.r = (ImageView) findViewById(R.id.back);
        this.textFont = (ImageView) findViewById(R.id.textfont);
        this.button_back_landscape = (ImageView) findViewById(R.id.button_back_landscape);
        this.button_forward_landscape = (ImageView) findViewById(R.id.button_forward_landscape);
        this.textLess = (ImageView) findViewById(R.id.textsizeless);
        this.textMore = (ImageView) findViewById(R.id.textsizemore);
        this.help = (ImageView) findViewById(R.id.help);
    }

    public MenuLandscapeBarCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mBarView = (RelativeLayout) this.mInflater.inflate(R.layout.menubarlandscape, (ViewGroup) null);
        addView(this.mBarView);
        this.r = (ImageView) findViewById(R.id.back);
        this.textFont = (ImageView) findViewById(R.id.textfont);
        this.button_back_landscape = (ImageView) findViewById(R.id.button_back_landscape);
        this.button_forward_landscape = (ImageView) findViewById(R.id.button_forward_landscape);
        this.textLess = (ImageView) findViewById(R.id.textsizeless);
        this.textMore = (ImageView) findViewById(R.id.textsizemore);
        this.help = (ImageView) findViewById(R.id.help);
    }
}
