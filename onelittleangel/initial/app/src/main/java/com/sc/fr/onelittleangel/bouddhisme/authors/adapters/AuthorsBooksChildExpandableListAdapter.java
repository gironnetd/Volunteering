package com.sc.fr.onelittleangel.bouddhisme.authors.adapters;

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
import com.sc.fr.onelittleangel.bouddhisme.R;
import com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity;
import com.sc.fr.onelittleangel.bouddhisme.entities.Courant;
import com.sc.fr.onelittleangel.bouddhisme.helpers.DataBaseHelper;
import com.sc.fr.onelittleangel.bouddhisme.views.ExpChildListView;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AuthorsBooksChildExpandableListAdapter extends BaseExpandableListAdapter {
    static int previousItem = -1;
    private ListAdapter adapter;
    AlertDialog.Builder adb;
    private Map<Integer, String> authors;
    private Map<Integer, Integer> authorsId;
    private Map<Integer, Integer> authorsSize;
    private int book;
    private Map<String, List<String>> childCollections;
    private List<String> childs;
    private Activity context;
    private Cursor cursorAuthor;
    private Cursor cursorAuthor2;
    private Cursor cursorCourant;
    private Cursor cursorCourant2;
    private SQLiteDatabase db;
    private ExpChildListView expListView;
    int lastExpandedPosition;
    int parentChildPosition;
    int previousItem2;
    private String type;

    public AuthorsBooksChildExpandableListAdapter(Activity context) {
        this.previousItem2 = -1;
        this.lastExpandedPosition = -1;
        this.db = null;
        this.authors = new LinkedHashMap();
        this.authorsId = new LinkedHashMap();
        this.authorsSize = new LinkedHashMap();
        this.book = 0;
        this.context = context;
    }

    public AuthorsBooksChildExpandableListAdapter(Activity context, ExpandableListView expParent, ExpChildListView expListView, String child, List<String> childs, Map<String, List<String>> childCollections, Integer book) {
        this.previousItem2 = -1;
        this.lastExpandedPosition = -1;
        this.db = null;
        this.authors = new LinkedHashMap();
        this.authorsId = new LinkedHashMap();
        this.authorsSize = new LinkedHashMap();
        this.book = 0;
        this.context = context;
        this.expListView = expListView;
        this.book = book.intValue();
        this.childs = childs;
        this.childCollections = childCollections;
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        if (this.childs.size() == 0) {
            return 0;
        }
        return this.childs.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int groupPosition) {
        if (this.childs.size() == 0 || this.childCollections.get(this.childs.get(groupPosition)) == null) {
            return 0;
        }
        return this.childCollections.get(this.childs.get(groupPosition)).size();
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int groupPosition) {
        return this.childs.get(groupPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int groupPosition, int childPosition) {
        return this.childCollections.get(this.childs.get(groupPosition)).get(childPosition);
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        int i = R.drawable.collapse;
        final String laptopName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
            convertView = infalInflater.inflate(R.layout.authors_books_child_list, (ViewGroup) null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.child);
        item.setText(String.valueOf(laptopName) + " ");
        ImageView img = (ImageView) convertView.findViewById(R.id.expandcollapse);
        img.setImageResource(isExpanded ? R.drawable.collapse : R.drawable.expand);
        if (getChildrenCount(groupPosition) == 0) {
            img.setImageResource(R.drawable.collapse);
        } else {
            if (!isExpanded) {
                i = R.drawable.expand;
            }
            img.setImageResource(i);
        }
        View.OnClickListener l = new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.authors.adapters.AuthorsBooksChildExpandableListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent i2 = new Intent(AuthorsBooksChildExpandableListAdapter.this.context, (Class<?>) QuotesTextActivity.class);
                if (v.getTag().equals("child") || v.getTag().equals("expandcollapse")) {
                    if (AuthorsBooksChildExpandableListAdapter.this.getChildrenCount(groupPosition) == 0) {
                        AuthorsBooksChildExpandableListAdapter.this.db = new DataBaseHelper(AuthorsBooksChildExpandableListAdapter.this.context).getReadableDatabase();
                        AuthorsBooksChildExpandableListAdapter.this.cursorCourant = AuthorsBooksChildExpandableListAdapter.this.db.rawQuery("SELECT id_courant as _id,courant,id_parent,checked From courant where courant ='" + AuthorsBooksChildExpandableListAdapter.this.getGroup(groupPosition) + "'", null);
                        AuthorsBooksChildExpandableListAdapter.this.cursorCourant.moveToFirst();
                        AuthorsBooksChildExpandableListAdapter.this.cursorAuthor = AuthorsBooksChildExpandableListAdapter.this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant,nb_quotes From author where nb_quotes !=0 and book=" + AuthorsBooksChildExpandableListAdapter.this.book + " and id_courant =" + AuthorsBooksChildExpandableListAdapter.this.cursorCourant.getString(0), null);
                        AuthorsBooksChildExpandableListAdapter.this.cursorAuthor.moveToFirst();
                        AuthorsBooksChildExpandableListAdapter.this.authors = new LinkedHashMap();
                        AuthorsBooksChildExpandableListAdapter.this.authorsId = new LinkedHashMap();
                        AuthorsBooksChildExpandableListAdapter.this.authorsSize = new LinkedHashMap();
                        for (int b = 0; b < AuthorsBooksChildExpandableListAdapter.this.cursorAuthor.getCount(); b++) {
                            AuthorsBooksChildExpandableListAdapter.this.authorsId.put(Integer.valueOf(b), Integer.valueOf(Integer.parseInt(AuthorsBooksChildExpandableListAdapter.this.cursorAuthor.getString(0))));
                            AuthorsBooksChildExpandableListAdapter.this.authorsSize.put(Integer.valueOf(b), Integer.valueOf(Integer.parseInt(AuthorsBooksChildExpandableListAdapter.this.cursorAuthor.getString(6))));
                            AuthorsBooksChildExpandableListAdapter.this.authors.put(Integer.valueOf(b), AuthorsBooksChildExpandableListAdapter.this.cursorAuthor.getString(1));
                            AuthorsBooksChildExpandableListAdapter.this.cursorAuthor.moveToNext();
                        }
                        ListView list = new ListView(AuthorsBooksChildExpandableListAdapter.this.context);
                        list.setDivider(null);
                        list.setDividerHeight(0);
                        list.setSelector(new StateListDrawable());
                        list.setCacheColorHint(-16777216);
                        list.setChoiceMode(1);
                        AuthorsBooksChildExpandableListAdapter.this.adapter = new AuthorsBooksToolsListAdapter(AuthorsBooksChildExpandableListAdapter.this.context, AuthorsBooksChildExpandableListAdapter.this.authors, AuthorsBooksChildExpandableListAdapter.this.authorsId, AuthorsBooksChildExpandableListAdapter.this.authorsSize);
                        list.setAdapter(AuthorsBooksChildExpandableListAdapter.this.adapter);
                        AuthorsBooksChildExpandableListAdapter.this.adb = new AlertDialog.Builder(AuthorsBooksChildExpandableListAdapter.this.context);
                        TextView title = new TextView(AuthorsBooksChildExpandableListAdapter.this.context);
                        title.setText(AuthorsBooksChildExpandableListAdapter.this.getGroup(groupPosition) + " : ");
                        title.setBackgroundColor(0);
                        title.setGravity(17);
                        title.setTextColor(-1);
                        title.setTextSize(27.0f);
                        AuthorsBooksChildExpandableListAdapter.this.adb.setCustomTitle(title);
                        AuthorsBooksChildExpandableListAdapter.this.adb.setView(list);
                        AuthorsBooksChildExpandableListAdapter.this.adb.setNegativeButton("Retour", (DialogInterface.OnClickListener) null);
                        final AlertDialog d = AuthorsBooksChildExpandableListAdapter.this.adb.create();
                        d.show();
                        if (AuthorsBooksChildExpandableListAdapter.this.book == 0) {
                            AuthorsBooksChildExpandableListAdapter.this.type = "authors";
                        } else {
                            AuthorsBooksChildExpandableListAdapter.this.type = "books";
                        }
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.authors.adapters.AuthorsBooksChildExpandableListAdapter.1.1
                            @Override // android.widget.AdapterView.OnItemClickListener
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                Intent i3 = new Intent(AuthorsBooksChildExpandableListAdapter.this.context, (Class<?>) QuotesTextActivity.class);
                                Courant courant = new Courant("");
                                i3.putExtra("type", "author");
                                i3.putExtra("author", (String) AuthorsBooksChildExpandableListAdapter.this.authors.get(Integer.valueOf(arg2)));
                                i3.putExtra("book", (Serializable) AuthorsBooksChildExpandableListAdapter.this.authorsId.get(Integer.valueOf(arg2)));
                                i3.putExtra("courant", courant);
                                i3.putExtra("fromActivity", AuthorsBooksChildExpandableListAdapter.this.type);
                                i3.putExtra("total", "no");
                                AuthorsBooksChildExpandableListAdapter.this.db.close();
                                AuthorsBooksChildExpandableListAdapter.this.context.startActivity(i3);
                                AuthorsBooksChildExpandableListAdapter.this.context.finish();
                                d.dismiss();
                            }
                        });
                    } else if (groupPosition != AuthorsBooksChildExpandableListAdapter.previousItem && AuthorsBooksChildExpandableListAdapter.previousItem != -1) {
                        AuthorsBooksChildExpandableListAdapter.this.expListView.collapseGroup(AuthorsBooksChildExpandableListAdapter.previousItem);
                        AuthorsBooksChildExpandableListAdapter.this.expListView.expandGroup(groupPosition);
                        AuthorsBooksChildExpandableListAdapter.previousItem = groupPosition;
                    } else if (AuthorsBooksChildExpandableListAdapter.previousItem == groupPosition) {
                        if (AuthorsBooksChildExpandableListAdapter.this.expListView.isGroupExpanded(groupPosition)) {
                            AuthorsBooksChildExpandableListAdapter.this.expListView.collapseGroup(groupPosition);
                        } else {
                            AuthorsBooksChildExpandableListAdapter.this.expListView.expandGroup(groupPosition);
                        }
                    } else if (AuthorsBooksChildExpandableListAdapter.previousItem == -1) {
                        AuthorsBooksChildExpandableListAdapter.previousItem = groupPosition;
                        AuthorsBooksChildExpandableListAdapter.this.expListView.expandGroup(groupPosition);
                    }
                }
                if (v.getTag().equals("quotes_size_child")) {
                    Courant courant = new Courant(laptopName);
                    i2.putExtra("type", "author");
                    i2.putExtra("courant", courant);
                    i2.putExtra("book", -1);
                    i2.putExtra("total", "yes");
                    AuthorsBooksChildExpandableListAdapter.this.context.startActivity(i2);
                }
            }
        };
        img.setOnClickListener(l);
        item.setOnClickListener(l);
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parentg) {
        int i = R.drawable.collapse;
        final String laptop = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = this.context.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.authors_books_child_child_list, (ViewGroup) null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.child_child);
        item.setText(String.valueOf(laptop) + " ");
        ImageView img = (ImageView) convertView.findViewById(R.id.expandcollapse);
        img.setImageResource(this.expListView.isGroupExpanded(groupPosition) ? R.drawable.collapse : R.drawable.expand);
        if (getChildrenCount(groupPosition) == 0) {
            img.setImageResource(R.drawable.collapse);
        } else {
            if (!this.expListView.isGroupExpanded(groupPosition)) {
                i = R.drawable.expand;
            }
            img.setImageResource(i);
        }
        View.OnClickListener l = new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.authors.adapters.AuthorsBooksChildExpandableListAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (v.getTag().equals("child_child") || v.getTag().equals("expandcollapse")) {
                    AuthorsBooksChildExpandableListAdapter.this.db = new DataBaseHelper(AuthorsBooksChildExpandableListAdapter.this.context).getReadableDatabase();
                    AuthorsBooksChildExpandableListAdapter.this.cursorCourant2 = AuthorsBooksChildExpandableListAdapter.this.db.rawQuery("SELECT id_courant as _id,courant,id_parent,checked From courant where courant ='" + laptop + "'", null);
                    AuthorsBooksChildExpandableListAdapter.this.cursorCourant2.moveToFirst();
                    AuthorsBooksChildExpandableListAdapter.this.cursorAuthor2 = AuthorsBooksChildExpandableListAdapter.this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant,nb_quotes From author where nb_quotes !=0 and book=" + AuthorsBooksChildExpandableListAdapter.this.book + " and id_courant =" + AuthorsBooksChildExpandableListAdapter.this.cursorCourant2.getString(0), null);
                    AuthorsBooksChildExpandableListAdapter.this.cursorAuthor2.moveToFirst();
                    AuthorsBooksChildExpandableListAdapter.this.authors = new LinkedHashMap();
                    AuthorsBooksChildExpandableListAdapter.this.authorsId = new LinkedHashMap();
                    AuthorsBooksChildExpandableListAdapter.this.authorsSize = new LinkedHashMap();
                    for (int b = 0; b < AuthorsBooksChildExpandableListAdapter.this.cursorAuthor2.getCount(); b++) {
                        AuthorsBooksChildExpandableListAdapter.this.authorsId.put(Integer.valueOf(b), Integer.valueOf(Integer.parseInt(AuthorsBooksChildExpandableListAdapter.this.cursorAuthor2.getString(0))));
                        AuthorsBooksChildExpandableListAdapter.this.authorsSize.put(Integer.valueOf(b), Integer.valueOf(Integer.parseInt(AuthorsBooksChildExpandableListAdapter.this.cursorAuthor2.getString(6))));
                        AuthorsBooksChildExpandableListAdapter.this.authors.put(Integer.valueOf(b), AuthorsBooksChildExpandableListAdapter.this.cursorAuthor2.getString(1));
                        AuthorsBooksChildExpandableListAdapter.this.cursorAuthor2.moveToNext();
                    }
                    AuthorsBooksChildExpandableListAdapter.this.cursorAuthor2.close();
                    ListView list = new ListView(AuthorsBooksChildExpandableListAdapter.this.context);
                    list.setDivider(null);
                    list.setDividerHeight(0);
                    list.setSelector(new StateListDrawable());
                    list.setCacheColorHint(-16777216);
                    list.setChoiceMode(1);
                    AuthorsBooksChildExpandableListAdapter.this.adapter = new AuthorsBooksToolsListAdapter(AuthorsBooksChildExpandableListAdapter.this.context, AuthorsBooksChildExpandableListAdapter.this.authors, AuthorsBooksChildExpandableListAdapter.this.authorsId, AuthorsBooksChildExpandableListAdapter.this.authorsSize);
                    list.setAdapter(AuthorsBooksChildExpandableListAdapter.this.adapter);
                    AuthorsBooksChildExpandableListAdapter.this.adb = new AlertDialog.Builder(AuthorsBooksChildExpandableListAdapter.this.context);
                    TextView title = new TextView(AuthorsBooksChildExpandableListAdapter.this.context);
                    title.setText(AuthorsBooksChildExpandableListAdapter.this.getChild(groupPosition, childPosition) + " : ");
                    title.setBackgroundColor(0);
                    title.setGravity(17);
                    title.setTextColor(-1);
                    title.setTextSize(27.0f);
                    AuthorsBooksChildExpandableListAdapter.this.adb.setCustomTitle(title);
                    AuthorsBooksChildExpandableListAdapter.this.adb.setView(list);
                    AuthorsBooksChildExpandableListAdapter.this.adb.setNegativeButton("Retour", (DialogInterface.OnClickListener) null);
                    final AlertDialog d = AuthorsBooksChildExpandableListAdapter.this.adb.create();
                    d.show();
                    if (AuthorsBooksChildExpandableListAdapter.this.book == 0) {
                        AuthorsBooksChildExpandableListAdapter.this.type = "authors";
                    } else {
                        AuthorsBooksChildExpandableListAdapter.this.type = "books";
                    }
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.authors.adapters.AuthorsBooksChildExpandableListAdapter.2.1
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            Intent i2 = new Intent(AuthorsBooksChildExpandableListAdapter.this.context, (Class<?>) QuotesTextActivity.class);
                            Courant courant = new Courant("");
                            i2.putExtra("type", "author");
                            i2.putExtra("author", (String) AuthorsBooksChildExpandableListAdapter.this.authors.get(Integer.valueOf(arg2)));
                            i2.putExtra("book", (Serializable) AuthorsBooksChildExpandableListAdapter.this.authorsId.get(Integer.valueOf(arg2)));
                            i2.putExtra("courant", courant);
                            i2.putExtra("fromActivity", AuthorsBooksChildExpandableListAdapter.this.type);
                            i2.putExtra("total", "no");
                            AuthorsBooksChildExpandableListAdapter.this.db.close();
                            AuthorsBooksChildExpandableListAdapter.this.context.startActivity(i2);
                            AuthorsBooksChildExpandableListAdapter.this.context.finish();
                            d.dismiss();
                        }
                    });
                }
            }
        };
        img.setOnClickListener(l);
        item.setOnClickListener(l);
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override // android.widget.BaseExpandableListAdapter, android.widget.ExpandableListAdapter
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override // android.widget.BaseExpandableListAdapter, android.widget.ExpandableListAdapter
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }
}
