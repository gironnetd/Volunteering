package com.sc.fr.onelittleangel.bouddhisme.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Map;

import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class ToolsListAdapter extends BaseAdapter {
    private Context mContext;
    Typeface tf;
    private Map<Integer, Boolean> titles;
    private static Map<Integer, Boolean> radioTable = new LinkedHashMap();
    private static Map<Integer, String> radioString = new LinkedHashMap();

    public ToolsListAdapter(Context mContext, Map<Integer, String> radioTable2, Map<Integer, Boolean> titles) {
        this.mContext = mContext;
        this.titles = titles;
        radioString = radioTable2;
        radioTable = titles;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        if (this.titles != null) {
            return this.titles.size();
        }
        return 0;
    }

    @Override // android.widget.Adapter
    public Object getItem(int arg0) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override // android.widget.Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.row_tools, (ViewGroup) null);
        }
        TextView title = (TextView) view.findViewById(R.id.subject);
        this.tf = Typeface.createFromAsset(this.mContext.getAssets(), "fonts/mtcorsva.ttf");
        title.setText(String.valueOf(radioString.get(Integer.valueOf(position))) + " ");
        final RadioButton radio = (RadioButton) view.findViewById(R.id.radio_button);
        radio.setChecked(radioTable.get(Integer.valueOf(position)).booleanValue());
        radioTable.put(Integer.valueOf(position), Boolean.valueOf(radio.isChecked()));
        view.setOnClickListener(new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.adapter.ToolsListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (radio.isChecked()) {
                    ToolsListAdapter.radioTable.put(Integer.valueOf(position), false);
                    radio.setChecked(false);
                } else {
                    ToolsListAdapter.radioTable.put(Integer.valueOf(position), true);
                    radio.setChecked(true);
                }
                ToolsListAdapter.this.notifyDataSetChanged();
            }
        });
        return view;
    }

    public static Map<Integer, Boolean> getRadioTable() {
        return radioTable;
    }

    public static Map<Integer, String> getRadioString() {
        return radioString;
    }
}
