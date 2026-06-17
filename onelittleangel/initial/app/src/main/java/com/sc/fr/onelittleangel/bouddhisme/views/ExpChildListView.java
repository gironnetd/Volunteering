package com.sc.fr.onelittleangel.bouddhisme.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;

/* JADX INFO: loaded from: classes.dex */
public class ExpChildListView extends ExpandableListView {
    public ExpChildListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpChildListView(Context context) {
        super(context);
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(500, Integer.MIN_VALUE);
        int widthMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(500, Integer.MIN_VALUE);
        super.onMeasure(widthMeasureSpec2, heightMeasureSpec2);
    }
}
