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
public class FontsListAdapter extends BaseAdapter {
    private static Map<Integer, Boolean> fontBool = new LinkedHashMap();
    private static Map<Integer, String> fontText = new LinkedHashMap();
    private static String selectedFont;
    private Context mContext;
    Typeface tf;

    public FontsListAdapter(Context mContext, Map<Integer, String> fontList, Map<Integer, Boolean> fontBool2) {
        this.mContext = mContext;
        fontBool = fontBool2;
        fontText = fontList;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return fontText.size();
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
            view = inflater.inflate(R.layout.row_fonts, (ViewGroup) null);
        }
        TextView title = (TextView) view.findViewById(R.id.subject);
        this.tf = Typeface.createFromAsset(this.mContext.getAssets(), "fonts/mtcorsva.ttf");
        title.setText(String.valueOf(fontText.get(Integer.valueOf(position))) + " ");
        final RadioButton radio = (RadioButton) view.findViewById(R.id.radio_button);
        radio.setChecked(fontBool.get(Integer.valueOf(position)).booleanValue());
        view.setOnClickListener(new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.adapter.FontsListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (radio.isChecked()) {
                    FontsListAdapter.fontBool.put(Integer.valueOf(position), false);
                    radio.setChecked(false);
                } else {
                    FontsListAdapter.fontBool.put(Integer.valueOf(position), true);
                    radio.setChecked(true);
                    FontsListAdapter.selectedFont = (String) FontsListAdapter.fontText.get(Integer.valueOf(position));
                }
                FontsListAdapter.this.notifyDataSetChanged();
            }
        });
        return view;
    }

    public static Map<Integer, Boolean> getFontBool() {
        return fontBool;
    }

    public static Map<Integer, String> getFontText() {
        return fontText;
    }

    public static String getSelectedFont() {
        return selectedFont;
    }
}
