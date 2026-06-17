package com.sc.fr.onelittleangel.bouddhisme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sc.fr.onelittleangel.bouddhisme.views.InertCheckBox;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class MultipleListAdapter extends BaseAdapter {
    private static Map<Integer, Boolean> fontBool = new LinkedHashMap();
    private static Map<Integer, String> fontText = new LinkedHashMap();
    Context ctx;
    LayoutInflater lInflater;
    InertCheckBox radio;

    public MultipleListAdapter(Context mContext, Map<Integer, String> fontList, Map<Integer, Boolean> fontBool2) {
        this.ctx = mContext;
        fontBool = fontBool2;
        fontText = fontList;
        this.lInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view = this.lInflater.inflate(R.layout.multiple_choice_items, parent, false);
        }
        ((TextView) view.findViewById(R.id.singleitemId)).setText(fontText.get(Integer.valueOf(position)));
        this.radio = (InertCheckBox) view.findViewById(R.id.singleitemCheckBox);
        this.radio.setChecked(fontBool.get(Integer.valueOf(position)).booleanValue());
        return view;
    }
}
