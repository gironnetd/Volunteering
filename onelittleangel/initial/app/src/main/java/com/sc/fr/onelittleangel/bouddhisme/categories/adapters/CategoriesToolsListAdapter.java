package com.sc.fr.onelittleangel.bouddhisme.categories.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sc.fr.onelittleangel.bouddhisme.fragments.MyViewPager;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class CategoriesToolsListAdapter extends BaseAdapter {
    static MyViewPager viewPager;
    private Map<Integer, String> categories;
    private Map<Integer, Integer> categoriesSize;
    private Activity mContext;
    Typeface tf;

    public CategoriesToolsListAdapter(Activity mContext, Map<Integer, String> categories, Map<Integer, Integer> categoriesId, Map<Integer, Integer> categoriesSize, MyViewPager viewPager2) {
        this.categories = new LinkedHashMap();
        this.categoriesSize = new LinkedHashMap();
        this.mContext = mContext;
        this.categories = categories;
        this.categoriesSize = categoriesSize;
        viewPager = viewPager2;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.categories.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.authors_row_tools, (ViewGroup) null);
        }
        TextView title = (TextView) view.findViewById(R.id.author);
        this.tf = Typeface.createFromAsset(this.mContext.getAssets(), "fonts/mtcorsva.ttf");
        title.setText(String.valueOf(this.categories.get(Integer.valueOf(position))) + " ");
        TextView author_size = (TextView) view.findViewById(R.id.authors_size);
        author_size.setText(" " + this.categoriesSize.get(Integer.valueOf(position)) + " ");
        return view;
    }
}
