package com.sc.fr.onelittleangel.bouddhisme.authors.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Map;
import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class AuthorsBooksToolsListAdapter extends BaseAdapter {
    private Map<Integer, String> authors;
    private Map<Integer, Integer> authorsSize;
    private Activity mContext;
    Typeface tf;

    public AuthorsBooksToolsListAdapter(Activity mContext, Map<Integer, String> authors, Map<Integer, Integer> authorsId, Map<Integer, Integer> authorsSize) {
        this.authors = new LinkedHashMap();
        this.authorsSize = new LinkedHashMap();
        this.mContext = mContext;
        this.authors = authors;
        this.authorsSize = authorsSize;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.authors.size();
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
        title.setText(String.valueOf(this.authors.get(Integer.valueOf(position))) + " ");
        TextView author_size = (TextView) view.findViewById(R.id.authors_size);
        author_size.setText(" " + this.authorsSize.get(Integer.valueOf(position)) + " ");
        return view;
    }
}
