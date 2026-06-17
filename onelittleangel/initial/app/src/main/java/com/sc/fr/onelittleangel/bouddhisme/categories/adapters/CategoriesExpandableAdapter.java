package com.sc.fr.onelittleangel.bouddhisme.categories.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity;
import com.sc.fr.onelittleangel.bouddhisme.entities.Category;
import com.sc.fr.onelittleangel.bouddhisme.fragments.MyViewPager;
import com.sc.fr.onelittleangel.bouddhisme.helpers.DataBaseHelper;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class CategoriesExpandableAdapter extends BaseExpandableListAdapter {
    static MyViewPager viewPager;
    private ListAdapter adapter;
    private Activity context;
    private Cursor cursorAuthor;
    private Cursor cursorCategory;
    private Cursor cursorCategory1;
    private Cursor cursorQuoCategory;
    private Cursor cursorQuote;
    private Map<String, Integer> groupCategoriesId;
    private List<String> laptops;
    private SQLiteDatabase db = null;
    private Map<Integer, String> categories = new LinkedHashMap();
    private Map<Integer, Integer> categoriesId = new LinkedHashMap();
    private Map<Integer, Integer> categoriesSize = new LinkedHashMap();

    public CategoriesExpandableAdapter(Activity context, ExpandableListView expListView, List<String> laptops, Map<String, Integer> groupCategoriesId, MyViewPager viewPager2) {
        this.context = context;
        this.laptops = laptops;
        this.groupCategoriesId = groupCategoriesId;
        viewPager = viewPager2;
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.laptops.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int groupPosition) {
        return this.laptops.get(groupPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return true;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        int i = R.drawable.collapse;
        final String laptopName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
            convertView = infalInflater.inflate(R.layout.categories_group_list, (ViewGroup) null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.laptop);
        item.setTypeface(null, 1);
        item.setText(String.valueOf(laptopName) + " ");
        ImageView img = (ImageView) convertView.findViewById(R.id.expandcollapse);
        if (getChildrenCount(groupPosition) == 0) {
            img.setImageResource(R.drawable.collapse);
        } else {
            if (!isExpanded) {
                i = R.drawable.expand;
            }
            img.setImageResource(i);
        }
        View.OnClickListener l = new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.categories.adapters.CategoriesExpandableAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (v.getTag().equals("laptop") || v.getTag().equals("expandcollapse")) {
                    int m = 0;
                    CategoriesExpandableAdapter.this.categories = new LinkedHashMap();
                    CategoriesExpandableAdapter.this.categoriesId = new LinkedHashMap();
                    CategoriesExpandableAdapter.this.categoriesSize = new LinkedHashMap();
                    CategoriesExpandableAdapter.this.db = new DataBaseHelper(CategoriesExpandableAdapter.this.context).getReadableDatabase();
                    CategoriesExpandableAdapter.this.cursorCategory = CategoriesExpandableAdapter.this.db.rawQuery("SELECT id_category as _id,category,id_parent,nb_quotes From category where id_category =" + CategoriesExpandableAdapter.this.groupCategoriesId.get(laptopName), null);
                    CategoriesExpandableAdapter.this.cursorCategory.moveToFirst();
                    CategoriesExpandableAdapter.this.cursorCategory1 = CategoriesExpandableAdapter.this.db.rawQuery("SELECT id_category as _id,category,id_parent,nb_quotes From category where nb_quotes!=0 and id_parent =" + CategoriesExpandableAdapter.this.cursorCategory.getString(0), null);
                    CategoriesExpandableAdapter.this.cursorCategory1.moveToFirst();
                    for (int b = 0; b < CategoriesExpandableAdapter.this.cursorCategory1.getCount(); b++) {
                        CategoriesExpandableAdapter.this.cursorQuoCategory = CategoriesExpandableAdapter.this.db.rawQuery("SELECT id_affiliation as _id,id_quote,id_category From quo_category where id_category =" + CategoriesExpandableAdapter.this.cursorCategory1.getString(0), null);
                        CategoriesExpandableAdapter.this.cursorQuoCategory.moveToFirst();
                        if (CategoriesExpandableAdapter.this.cursorQuoCategory.getCount() != 0) {
                            boolean ok = false;
                            for (int j = 0; j < CategoriesExpandableAdapter.this.cursorQuoCategory.getCount(); j++) {
                                try {
                                    try {
                                        CategoriesExpandableAdapter.this.cursorQuote = CategoriesExpandableAdapter.this.db.rawQuery("SELECT id_quote as _id,id_author,quote,book From quote where id_quote =" + CategoriesExpandableAdapter.this.cursorQuoCategory.getString(1), null);
                                        CategoriesExpandableAdapter.this.cursorQuote.moveToFirst();
                                        CategoriesExpandableAdapter.this.cursorAuthor = CategoriesExpandableAdapter.this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant From author where id_author =" + CategoriesExpandableAdapter.this.cursorQuote.getString(1), null);
                                        CategoriesExpandableAdapter.this.cursorAuthor.moveToFirst();
                                        if (CategoriesExpandableAdapter.this.cursorAuthor.getCount() != 0 && !CategoriesExpandableAdapter.this.cursorAuthor.getString(5).equals("")) {
                                            ok = true;
                                        }
                                        CategoriesExpandableAdapter.this.cursorQuote.close();
                                        CategoriesExpandableAdapter.this.cursorAuthor.close();
                                        CategoriesExpandableAdapter.this.cursorQuoCategory.moveToNext();
                                    } catch (Throwable th) {
                                        CategoriesExpandableAdapter.this.cursorQuote.close();
                                        CategoriesExpandableAdapter.this.cursorAuthor.close();
                                        throw th;
                                    }
                                } finally {
                                    CategoriesExpandableAdapter.this.cursorQuoCategory.close();
                                }
                            }
                            if (ok && 0 == 0) {
                                CategoriesExpandableAdapter.this.categoriesId.put(Integer.valueOf(m), Integer.valueOf(Integer.parseInt(CategoriesExpandableAdapter.this.cursorCategory1.getString(0))));
                                CategoriesExpandableAdapter.this.categoriesSize.put(Integer.valueOf(m), Integer.valueOf(Integer.parseInt(CategoriesExpandableAdapter.this.cursorCategory1.getString(3))));
                                CategoriesExpandableAdapter.this.categories.put(Integer.valueOf(m), CategoriesExpandableAdapter.this.cursorCategory1.getString(1));
                                m++;
                            }
                        }
                        CategoriesExpandableAdapter.this.cursorCategory1.moveToNext();
                    }
                    CategoriesExpandableAdapter.this.cursorCategory1.close();
                    ListView list = new ListView(CategoriesExpandableAdapter.this.context);
                    list.setDivider(null);
                    list.setDividerHeight(0);
                    list.setSelector(new StateListDrawable());
                    list.setCacheColorHint(-16777216);
                    list.setChoiceMode(1);
                    CategoriesExpandableAdapter.this.adapter = new CategoriesToolsListAdapter(CategoriesExpandableAdapter.this.context, CategoriesExpandableAdapter.this.categories, CategoriesExpandableAdapter.this.categoriesId, CategoriesExpandableAdapter.this.categoriesSize, CategoriesExpandableAdapter.viewPager);
                    list.setAdapter(CategoriesExpandableAdapter.this.adapter);
                    AlertDialog.Builder adb = new AlertDialog.Builder(CategoriesExpandableAdapter.this.context);
                    TextView title = new TextView(CategoriesExpandableAdapter.this.context);
                    title.setText(String.valueOf(laptopName) + " : ");
                    title.setBackgroundColor(0);
                    title.setGravity(17);
                    title.setTextColor(-1);
                    title.setTextSize(27.0f);
                    adb.setCustomTitle(title);
                    adb.setView(list);
                    adb.setNegativeButton("Retour", (DialogInterface.OnClickListener) null);
                    final AlertDialog d = adb.create();
                    d.show();
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.categories.adapters.CategoriesExpandableAdapter.1.1
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            Intent i2 = new Intent(CategoriesExpandableAdapter.this.context, (Class<?>) QuotesTextActivity.class);
                            Category category = new Category(Long.valueOf(String.valueOf(CategoriesExpandableAdapter.this.categoriesId.get(Integer.valueOf(arg2)))));
                            i2.putExtra("type", "category");
                            i2.putExtra("fromActivity", "categories");
                            i2.putExtra("category", category);
                            i2.putExtra("book", (Serializable) CategoriesExpandableAdapter.this.categoriesId.get(Integer.valueOf(arg2)));
                            i2.putExtra("total", "no");
                            CategoriesExpandableAdapter.this.db.close();
                            CategoriesExpandableAdapter.this.context.startActivity(i2);
                            CategoriesExpandableAdapter.this.context.finish();
                            d.dismiss();
                        }
                    });
                }
            }
        };
        item.setOnClickListener(l);
        img.setOnClickListener(l);
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parentg) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        if (convertView == null) {
            return inflater.inflate(R.layout.categories_child_list, (ViewGroup) null);
        }
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
