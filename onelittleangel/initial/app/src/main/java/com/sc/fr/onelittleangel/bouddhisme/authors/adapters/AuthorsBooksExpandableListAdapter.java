package com.sc.fr.onelittleangel.bouddhisme.authors.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sc.fr.onelittleangel.bouddhisme.R;
import com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity;
import com.sc.fr.onelittleangel.bouddhisme.entities.Courant;
import com.sc.fr.onelittleangel.bouddhisme.helpers.DataBaseHelper;
import com.sc.fr.onelittleangel.bouddhisme.views.ExpChildListView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AuthorsBooksExpandableListAdapter extends BaseExpandableListAdapter {
    static int firstexpand;
    private ListAdapter adapter;
    Animation animZoomIn;
    Animation animZoomOut;
    private int book;
    Map<String, ArrayList<String>> childAuthors;
    Map<String, Integer> childAuthorsId;
    Map<String, Integer> childAuthorsQuotesListSize;
    Map<String, Integer> childAuthorsQuotesSize;
    Map<String, Integer> childAuthorsSize;
    Map<String, ArrayList<String>> childChildAuthors;
    Map<String, Integer> childChildAuthorsId;
    Map<String, Integer> childChildAuthorsQuotesListSize;
    Map<String, Integer> childChildAuthorsQuotesSize;
    Map<String, Integer> childChildAuthorsSize;
    private Map<String, List<String>> childCollections;
    private Activity context;
    private Cursor cursorAuthor;
    private Cursor cursorCourant;
    AuthorsBooksChildExpandableListAdapter expAuthorsChildListAdapter2;
    private ExpChildListView expAuthorsChildListView3;
    private ExpandableListView expListView;
    Map<String, Integer> groupAuthorsQuotesListSize;
    Map<String, Integer> groupAuthorsQuotesSize;
    Map<String, Integer> groupAuthorsSize;
    boolean isVisible;
    TextView item;
    private Map<String, List<String>> laptopCollections;
    private List<String> laptops;
    ListView list;
    private String type;
    static int expListViewHeight = 0;
    static int previousItem = -1;
    static int collapseChild = -1;
    private List<String> group = new ArrayList();
    private int compteur = 0;
    int previousItem2 = -1;
    int childPos = -1;
    int groupCount = 0;
    int groupPosi = -1;
    int groupPos2 = 0;
    boolean isExpanded1 = false;
    int groupCompteur = 0;
    boolean expandGroup = false;
    boolean collapseGroup = true;
    int compteurChild = 0;
    int height = 0;
    int nb_rows = 0;
    private SQLiteDatabase db = null;
    private Map<Integer, String> authors = new LinkedHashMap();
    private Map<Integer, Integer> authorsId = new LinkedHashMap();
    private Map<Integer, Integer> authorsSize = new LinkedHashMap();

    public AuthorsBooksExpandableListAdapter(Activity context, ExpandableListView expListView, List<String> laptops, Map<String, List<String>> laptopCollections, Map<String, List<String>> childCollections, int book) {
        this.context = context;
        this.expListView = expListView;
        this.laptops = laptops;
        this.laptopCollections = laptopCollections;
        this.childCollections = childCollections;
        this.book = book;
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.laptops.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int groupPosition) {
        return this.laptopCollections.get(this.laptops.get(groupPosition)).size() == 0 ? 0 : 1;
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int groupPosition) {
        return this.laptops.get(groupPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int groupPosition, int childPosition) {
        if (this.laptopCollections.get(this.laptops.get(groupPosition)).size() == 0) {
            return null;
        }
        return this.laptopCollections.get(this.laptops.get(groupPosition)).get(childPosition);
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
        ViewHolder holder;
        int i = R.drawable.collapse;
        View view = convertView;
        String laptopName = (String) getGroup(groupPosition);
        this.group.add(laptopName);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
            view = infalInflater.inflate(R.layout.authors_books_group_list, (ViewGroup) null);
            holder = new ViewHolder();
            holder.img = (ImageView) view.findViewById(R.id.expandcollapse);
            holder.item = (TextView) view.findViewById(R.id.laptop);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.img.setImageResource(isExpanded ? R.drawable.collapse : R.drawable.expand);
        if (getChildrenCount(groupPosition) == 0) {
            holder.img.setImageResource(R.drawable.collapse);
        } else {
            ImageView imageView = holder.img;
            if (!isExpanded) {
                i = R.drawable.expand;
            }
            imageView.setImageResource(i);
        }
        holder.item.setText(laptopName);
        this.height = view.getMeasuredHeight();
        View.OnClickListener l = new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.authors.adapters.AuthorsBooksExpandableListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (v.getTag().equals("laptop") || v.getTag().equals("expandcollpase")) {
                    if (AuthorsBooksExpandableListAdapter.this.getChildrenCount(groupPosition) == 0) {
                        AuthorsBooksExpandableListAdapter.this.db = new DataBaseHelper(AuthorsBooksExpandableListAdapter.this.context).getReadableDatabase();
                        AuthorsBooksExpandableListAdapter.this.cursorCourant = AuthorsBooksExpandableListAdapter.this.db.rawQuery("SELECT id_courant as _id,courant,id_parent,checked From courant where courant ='" + AuthorsBooksExpandableListAdapter.this.getGroup(groupPosition) + "'", null);
                        AuthorsBooksExpandableListAdapter.this.cursorCourant.moveToFirst();
                        AuthorsBooksExpandableListAdapter.this.cursorAuthor = AuthorsBooksExpandableListAdapter.this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant,nb_quotes From author where nb_quotes!=0 and book=" + AuthorsBooksExpandableListAdapter.this.book + " and id_courant =" + AuthorsBooksExpandableListAdapter.this.cursorCourant.getString(0), null);
                        AuthorsBooksExpandableListAdapter.this.cursorAuthor.moveToFirst();
                        AuthorsBooksExpandableListAdapter.this.authorsId.clear();
                        AuthorsBooksExpandableListAdapter.this.authorsSize.clear();
                        AuthorsBooksExpandableListAdapter.this.authors.clear();
                        for (int b = 0; b < AuthorsBooksExpandableListAdapter.this.cursorAuthor.getCount(); b++) {
                            AuthorsBooksExpandableListAdapter.this.authorsId.put(Integer.valueOf(b), Integer.valueOf(Integer.parseInt(AuthorsBooksExpandableListAdapter.this.cursorAuthor.getString(0))));
                            AuthorsBooksExpandableListAdapter.this.authorsSize.put(Integer.valueOf(b), Integer.valueOf(Integer.parseInt(AuthorsBooksExpandableListAdapter.this.cursorAuthor.getString(6))));
                            AuthorsBooksExpandableListAdapter.this.authors.put(Integer.valueOf(b), AuthorsBooksExpandableListAdapter.this.cursorAuthor.getString(1));
                            AuthorsBooksExpandableListAdapter.this.cursorAuthor.moveToNext();
                        }
                        AuthorsBooksExpandableListAdapter.this.list = (ListView) AuthorsBooksExpandableListAdapter.this.context.getLayoutInflater().inflate(R.layout.tools, (ViewGroup) null).findViewById(android.R.id.list);
                        AuthorsBooksExpandableListAdapter.this.list.setDivider(null);
                        AuthorsBooksExpandableListAdapter.this.list.setDividerHeight(0);
                        AuthorsBooksExpandableListAdapter.this.list.setSelector(new StateListDrawable());
                        AuthorsBooksExpandableListAdapter.this.list.setCacheColorHint(-16777216);
                        AuthorsBooksExpandableListAdapter.this.list.setChoiceMode(1);
                        AlertDialog.Builder adb = new AlertDialog.Builder(AuthorsBooksExpandableListAdapter.this.context);
                        TextView title = new TextView(AuthorsBooksExpandableListAdapter.this.context);
                        title.setText(AuthorsBooksExpandableListAdapter.this.getGroup(groupPosition) + " : ");
                        title.setBackgroundColor(0);
                        title.setGravity(17);
                        title.setTextColor(-1);
                        title.setTextSize(27.0f);
                        adb.setCustomTitle(title);
                        adb.setView(AuthorsBooksExpandableListAdapter.this.list);
                        adb.setNegativeButton("Retour", (DialogInterface.OnClickListener) null);
                        AuthorsBooksExpandableListAdapter.this.adapter = new AuthorsBooksToolsListAdapter(AuthorsBooksExpandableListAdapter.this.context, AuthorsBooksExpandableListAdapter.this.authors, AuthorsBooksExpandableListAdapter.this.authorsId, AuthorsBooksExpandableListAdapter.this.authorsSize);
                        AuthorsBooksExpandableListAdapter.this.list.setAdapter(AuthorsBooksExpandableListAdapter.this.adapter);
                        AuthorsBooksExpandableListAdapter.this.animZoomIn = AnimationUtils.loadAnimation(AuthorsBooksExpandableListAdapter.this.context.getApplicationContext(), R.anim.anim_zoomin);
                        AuthorsBooksExpandableListAdapter.this.animZoomOut = AnimationUtils.loadAnimation(AuthorsBooksExpandableListAdapter.this.context.getApplicationContext(), R.anim.anim_zoomout);
                        final AlertDialog d = adb.create();
                        d.show();
                        if (AuthorsBooksExpandableListAdapter.this.book == 0) {
                            AuthorsBooksExpandableListAdapter.this.type = "authors";
                        } else {
                            AuthorsBooksExpandableListAdapter.this.type = "books";
                        }
                        AuthorsBooksExpandableListAdapter.this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.authors.adapters.AuthorsBooksExpandableListAdapter.1.1
                            @Override // android.widget.AdapterView.OnItemClickListener
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                Intent i2 = new Intent(AuthorsBooksExpandableListAdapter.this.context, (Class<?>) QuotesTextActivity.class);
                                Courant courant = new Courant("");
                                i2.putExtra("type", "author");
                                i2.putExtra("author", (String) AuthorsBooksExpandableListAdapter.this.authors.get(Integer.valueOf(arg2)));
                                i2.putExtra("book", (Serializable) AuthorsBooksExpandableListAdapter.this.authorsId.get(Integer.valueOf(arg2)));
                                i2.putExtra("courant", courant);
                                i2.putExtra("fromActivity", AuthorsBooksExpandableListAdapter.this.type);
                                i2.putExtra("total", "no");
                                AuthorsBooksExpandableListAdapter.this.context.startActivity(i2);
                                AuthorsBooksExpandableListAdapter.this.db.close();
                                AuthorsBooksExpandableListAdapter.this.context.finish();
                                d.dismiss();
                            }
                        });
                        return;
                    }
                    if (groupPosition != AuthorsBooksExpandableListAdapter.previousItem) {
                        AuthorsBooksExpandableListAdapter.this.expListView.expandGroup(groupPosition);
                        if (AuthorsBooksExpandableListAdapter.previousItem != -1) {
                            AuthorsBooksExpandableListAdapter.this.expListView.collapseGroup(AuthorsBooksExpandableListAdapter.previousItem);
                            AuthorsBooksExpandableListAdapter.this.expandGroup = false;
                            AuthorsBooksExpandableListAdapter.this.groupPosi = -1;
                            AuthorsBooksExpandableListAdapter.this.expListView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
                        }
                        AuthorsBooksExpandableListAdapter.previousItem = groupPosition;
                        AuthorsBooksExpandableListAdapter.this.previousItem2 = -1;
                        return;
                    }
                    if (groupPosition == AuthorsBooksExpandableListAdapter.previousItem) {
                        if (AuthorsBooksExpandableListAdapter.this.expListView.isGroupExpanded(groupPosition)) {
                            AuthorsBooksExpandableListAdapter.this.expListView.collapseGroup(groupPosition);
                            AuthorsBooksExpandableListAdapter.this.expandGroup = false;
                            AuthorsBooksExpandableListAdapter.this.groupPosi = -1;
                            AuthorsBooksExpandableListAdapter.this.expListView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
                            AuthorsBooksExpandableListAdapter.this.previousItem2 = -1;
                            AuthorsBooksExpandableListAdapter.this.childPos = 0;
                        } else if (!AuthorsBooksExpandableListAdapter.this.expListView.isGroupExpanded(groupPosition)) {
                            AuthorsBooksExpandableListAdapter.this.expListView.expandGroup(groupPosition);
                        }
                        AuthorsBooksExpandableListAdapter.previousItem = groupPosition;
                        AuthorsBooksExpandableListAdapter.this.isExpanded1 = false;
                    }
                }
            }
        };
        holder.item.setOnClickListener(l);
        holder.img.setOnClickListener(l);
        view.setOnClickListener(null);
        return view;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parentg) {
        String laptop = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            infalInflater.inflate(R.layout.authors_books_group_list, (ViewGroup) null);
        }
        this.expAuthorsChildListView3 = new ExpChildListView(this.context);
        this.expAuthorsChildListAdapter2 = new AuthorsBooksChildExpandableListAdapter(this.context, this.expListView, this.expAuthorsChildListView3, laptop, this.laptopCollections.get(getGroup(groupPosition)), this.childCollections, Integer.valueOf(this.book));
        this.expAuthorsChildListView3.setAdapter(this.expAuthorsChildListAdapter2);
        this.expAuthorsChildListView3.setDivider(null);
        this.expAuthorsChildListView3.setDividerHeight(0);
        this.expAuthorsChildListView3.setSelector(new StateListDrawable());
        this.expAuthorsChildListView3.setGroupIndicator(null);
        this.expAuthorsChildListView3.setVisibility(0);
        this.expAuthorsChildListView3.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.authors.adapters.AuthorsBooksExpandableListAdapter.2
            @Override // android.widget.ExpandableListView.OnGroupExpandListener
            public void onGroupExpand(int groupPosition2) {
                AuthorsBooksExpandableListAdapter.this.compteur++;
                AuthorsBooksExpandableListAdapter.this.groupPosi = groupPosition2;
                if (!AuthorsBooksExpandableListAdapter.this.expandGroup) {
                    AuthorsBooksExpandableListAdapter.this.expandGroup = true;
                }
                if (AuthorsBooksExpandableListAdapter.this.compteur - 1 == AuthorsBooksExpandableListAdapter.this.expAuthorsChildListView3.getExpandableListAdapter().getGroupCount()) {
                    AuthorsBooksExpandableListAdapter.this.previousItem2 = -1;
                    AuthorsBooksExpandableListAdapter.this.compteur = 0;
                }
            }
        });
        this.expAuthorsChildListView3.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.authors.adapters.AuthorsBooksExpandableListAdapter.3
            @Override // android.widget.ExpandableListView.OnGroupCollapseListener
            public void onGroupCollapse(int groupPosition2) {
                AuthorsBooksExpandableListAdapter.this.compteur++;
                AuthorsBooksExpandableListAdapter.this.groupPosi = groupPosition2;
                if (AuthorsBooksExpandableListAdapter.this.expandGroup) {
                    AuthorsBooksExpandableListAdapter.this.expandGroup = false;
                }
                if (AuthorsBooksExpandableListAdapter.this.compteur - 1 == AuthorsBooksExpandableListAdapter.this.expAuthorsChildListView3.getExpandableListAdapter().getGroupCount()) {
                    AuthorsBooksExpandableListAdapter.this.previousItem2 = -1;
                    AuthorsBooksExpandableListAdapter.this.compteur = 0;
                }
            }
        });
        if (this.expandGroup && this.previousItem2 == -1) {
            this.expAuthorsChildListView3.expandGroup(this.groupPosi);
        }
        if (!this.expandGroup && this.previousItem2 != -1) {
            this.expAuthorsChildListView3.collapseGroup(this.groupPosi);
            if (this.compteur == this.expAuthorsChildListView3.getExpandableListAdapter().getGroupCount()) {
                this.expandGroup = true;
                this.compteur = 0;
            }
        }
        this.expAuthorsChildListView3.setClickable(false);
        return this.expAuthorsChildListView3;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ViewHolder {
        ImageView img;
        TextView item;

        ViewHolder() {
        }
    }
}
