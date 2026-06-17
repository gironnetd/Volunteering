package com.sc.fr.onelittleangel.bouddhisme.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

/* JADX INFO: loaded from: classes.dex */
public class CustomListView extends ListView {
    private final Context context;
    private int mPosition;
    private int position;

    public CustomListView(Context context) {
        super(context);
        this.context = context;
        setLayoutParams(new AbsListView.LayoutParams(-2, -2));
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setLayoutParams(new AbsListView.LayoutParams(-2, -2));
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        setLayoutParams(new AbsListView.LayoutParams(-2, -2));
    }
}
