package com.sc.fr.onelittleangel.bouddhisme.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class SingleListAdapter extends BaseAdapter {
    private static Map<Integer, String> fontText = new LinkedHashMap();
    private ArrayList<String> alf;
    Context ctx;
    LayoutInflater lInflater;
    Typeface tf;
    TextView tv;

    public SingleListAdapter(Context mContext, Map<Integer, String> fontList, ArrayList<String> alf, Map<Integer, Boolean> fontBool) {
        this.ctx = mContext;
        fontText = fontList;
        this.alf = alf;
        this.lInflater = (LayoutInflater) this.ctx.getSystemService("layout_inflater");
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return fontText.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return fontText.get(Integer.valueOf(position));
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = this.lInflater.inflate(R.layout.single_choice_items, parent, false);
        }
        this.tv = (TextView) view.findViewById(R.id.singleitemId);
        this.tv.setText(this.alf.get(position));
        this.tf = Typeface.createFromAsset(this.ctx.getAssets(), "fonts/" + fontText.get(Integer.valueOf(position)) + ".ttf");
        this.tv.setTypeface(this.tf);
        view.setLayoutParams(new AbsListView.LayoutParams(-1, 50));
        return view;
    }
}
