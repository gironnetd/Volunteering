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
public class MenuPortraitBarCustom extends RelativeLayout {
    private ImageView button_back;
    private ImageView button_forward;
    private ImageView email;
    Typeface face;
    private ImageView favorites;
    private ImageView help;
    private ImageView logo;
    private RelativeLayout mBarView;
    private LayoutInflater mInflater;
    private ImageView r;
    private TextView title;
    private TextView title_actionBar;

    public MenuPortraitBarCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mBarView = (RelativeLayout) this.mInflater.inflate(R.layout.menubarportrait, (ViewGroup) null);
        addView(this.mBarView);
        this.button_back = (ImageView) findViewById(R.id.button_back);
        this.button_forward = (ImageView) findViewById(R.id.button_forward);
        this.r = (ImageView) findViewById(R.id.back);
        this.help = (ImageView) findViewById(R.id.help);
    }

    public MenuPortraitBarCustom(Context context) {
        super(context);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mBarView = (RelativeLayout) this.mInflater.inflate(R.layout.menubarportrait, (ViewGroup) null);
        addView(this.mBarView);
        this.button_back = (ImageView) findViewById(R.id.button_back);
        this.button_forward = (ImageView) findViewById(R.id.button_forward);
        this.r = (ImageView) findViewById(R.id.back);
        this.help = (ImageView) findViewById(R.id.help);
    }

    public MenuPortraitBarCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mBarView = (RelativeLayout) this.mInflater.inflate(R.layout.menubarportrait, (ViewGroup) null);
        addView(this.mBarView);
        this.button_back = (ImageView) findViewById(R.id.button_back);
        this.button_forward = (ImageView) findViewById(R.id.button_forward);
        this.r = (ImageView) findViewById(R.id.back);
        this.help = (ImageView) findViewById(R.id.help);
    }
}
