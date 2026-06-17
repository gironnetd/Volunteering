package com.sc.fr.onelittleangel.bouddhisme.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.CheckBox;

/* JADX INFO: loaded from: classes.dex */
public class InertCheckBox extends CheckBox {
    public InertCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public InertCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InertCheckBox(Context context) {
        super(context);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return false;
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        return false;
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        return false;
    }

    @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTrackballEvent(MotionEvent event) {
        return false;
    }
}
