package com.sc.fr.onelittleangel.bouddhisme.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sc.fr.onelittleangel.bouddhisme.authors.adapters.AuthorsBooksExpandableListAdapter;
import com.sc.fr.onelittleangel.bouddhisme.adapter.MultipleListAdapter;
import com.sc.fr.onelittleangel.bouddhisme.fragments.MyViewPager;
import com.sc.fr.onelittleangel.bouddhisme.helpers.DataBaseHelper;
import com.sc.fr.onelittleangel.bouddhisme.helpers.ToolsHelper;
import com.sc.fr.onelittleangel.bouddhisme.views.ActionBarMenusCustom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class BooksListActivity extends Fragment {
    static MyViewPager viewPager;
    ActionBarMenusCustom actionBar;
    Activity activity;
    ListAdapter adapter;
    Animation animZoomIn;
    Animation animZoomOut;
    Map<String, ArrayList<String>> childBooks;
    Map<String, Integer> childBooksId;
    ArrayList<String> childBooksList;
    Map<String, Integer> childBooksQuotesListSize;
    Map<String, Integer> childBooksQuotesSize;
    Map<String, Integer> childBooksSize;
    Map<String, ArrayList<String>> childChildBooks;
    Map<String, Integer> childChildBooksId;
    ArrayList<String> childChildBooksList;
    Map<String, Integer> childChildBooksQuotesListSize;
    Map<String, Integer> childChildBooksQuotesSize;
    Map<String, Integer> childChildBooksSize;
    Map<String, List<String>> childChildCollection;
    List<String> childChildList;
    Map<String, Integer> childChildQuotesSize;
    Map<String, List<String>> childCollection;
    List<String> childList;
    Map<String, Integer> childQuotesSize;
    TextView comma;
    Context context;
    private Cursor cursorBook;
    private Cursor cursorBook2;
    private Cursor cursorBook3;
    private Cursor cursorCourant;
    private Cursor cursorCourant2;
    ExpandableListView expListView;
    Typeface face;
    Map<String, ArrayList<String>> groupBooks;
    Map<String, Integer> groupBooksId;
    ArrayList<String> groupBooksList;
    Map<String, Integer> groupBooksQuotesListSize;
    Map<String, Integer> groupBooksQuotesSize;
    Map<String, Integer> groupBooksSize;
    List<String> groupChildList;
    List<String> groupList;
    Map<String, Integer> groupQuotesSize;
    private ImageView help;
    ImageView logo;
    ImageView r;
    String tag;
    TextView text;
    ImageView tools;
    ToolsHelper toolsHelper;
    private static Map<Integer, String> listCourants = new LinkedHashMap();
    private static Map<Integer, Boolean> t = new LinkedHashMap();
    static int REQUEST_EXIT = 1337;
    private int groupSize = 0;
    private int childSize = 0;
    int totalQuotes = 0;
    int totalBooks = 0;
    private SQLiteDatabase db = null;
    private Cursor cursorCourant1 = null;
    int totalBooksQuotes = 0;
    int totalChildBookBooks = 0;

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.books_main_list, container, false);
        this.context = getActivity().getBaseContext();
        this.text = (TextView) v.findViewById(R.id.table);
        this.text.setText(R.string.book);
        this.actionBar = (ActionBarMenusCustom) v.findViewById(R.id.actionbar);
        this.actionBar.setTitle("Livres sacrés");
        this.actionBar.setBackgroundResource(R.drawable.actionbar_background);
        this.animZoomIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.anim_zoomin);
        this.animZoomOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.anim_zoomout);
        this.r = (ImageView) v.findViewById(R.id.back);
        this.tools = (ImageView) v.findViewById(R.id.tools);
        this.help = (ImageView) v.findViewById(R.id.help);
        this.logo = (ImageView) this.actionBar.findViewById(R.id.ola_logo);
        View.OnClickListener oc = new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.BooksListActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (v2.getTag().equals("logo")) {
                    BooksListActivity.this.tag = "logo";
                    BooksListActivity.this.animZoomIn = AnimationUtils.loadAnimation(BooksListActivity.this.getActivity().getApplicationContext(), R.anim.anim_logo_zoomin);
                    BooksListActivity.this.animZoomOut = AnimationUtils.loadAnimation(BooksListActivity.this.getActivity().getApplicationContext(), R.anim.anim_logo_zoomout);
                    BooksListActivity.this.logo.startAnimation(BooksListActivity.this.animZoomIn);
                    BooksListActivity.this.logo.startAnimation(BooksListActivity.this.animZoomOut);
                    BooksListActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.BooksListActivity.1.1
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            MainActivity.getPager().setCurrentItem(0);
                        }
                    });
                }
                if (v2.getTag().equals("back")) {
                    BooksListActivity.this.tag = "back";
                    BooksListActivity.this.animZoomIn = AnimationUtils.loadAnimation(BooksListActivity.this.getActivity().getApplicationContext(), R.anim.anim_back_zoomin);
                    BooksListActivity.this.animZoomOut = AnimationUtils.loadAnimation(BooksListActivity.this.getActivity().getApplicationContext(), R.anim.anim_back_zoomout);
                    BooksListActivity.this.r.startAnimation(BooksListActivity.this.animZoomIn);
                    BooksListActivity.this.r.startAnimation(BooksListActivity.this.animZoomOut);
                    BooksListActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.BooksListActivity.1.2
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            MainActivity.getPager().setCurrentItem(0);
                        }
                    });
                }
                if (v2.getTag().equals("help")) {
                    BooksListActivity.this.tag = "logo";
                    BooksListActivity.this.animZoomIn = AnimationUtils.loadAnimation(BooksListActivity.this.getActivity().getApplicationContext(), R.anim.anim_help_zoomin);
                    BooksListActivity.this.animZoomOut = AnimationUtils.loadAnimation(BooksListActivity.this.getActivity().getApplicationContext(), R.anim.anim_help_zoomout);
                    BooksListActivity.this.help.startAnimation(BooksListActivity.this.animZoomIn);
                    BooksListActivity.this.help.startAnimation(BooksListActivity.this.animZoomOut);
                    BooksListActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.BooksListActivity.1.3
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            BooksListActivity.this.onHelp();
                        }
                    });
                }
                if (v2.getTag().equals("tools")) {
                    BooksListActivity.this.tag = "tools";
                    BooksListActivity.this.animZoomIn = AnimationUtils.loadAnimation(BooksListActivity.this.getActivity().getApplicationContext(), R.anim.anim_tools_zoomin);
                    BooksListActivity.this.animZoomOut = AnimationUtils.loadAnimation(BooksListActivity.this.getActivity().getApplicationContext(), R.anim.anim_tools_zoomout);
                    BooksListActivity.this.tools.startAnimation(BooksListActivity.this.animZoomIn);
                    BooksListActivity.this.tools.startAnimation(BooksListActivity.this.animZoomOut);
                    BooksListActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.BooksListActivity.1.4
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            BooksListActivity.this.onTools();
                        }
                    });
                }
            }
        };
        this.logo.setOnClickListener(oc);
        this.r.setOnClickListener(oc);
        this.tools.setOnClickListener(oc);
        this.help.setOnClickListener(oc);
        this.face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mtcorsva.ttf");
        this.text.setTypeface(this.face);
        this.db = new DataBaseHelper(getActivity().getBaseContext()).getReadableDatabase();
        this.toolsHelper = new ToolsHelper(getActivity().getApplicationContext());
        t = ToolsHelper.getCourants();
        listCourants = ToolsHelper.getList();
        initBooksAdapter();
        this.expListView = (ExpandableListView) v.findViewById(R.id.books_main_list);
        AuthorsBooksExpandableListAdapter expListAdapter = new AuthorsBooksExpandableListAdapter(getActivity(), this.expListView, this.groupList, this.childCollection, this.childChildCollection, 1);
        this.expListView.setAdapter(expListAdapter);
        this.expListView.setGroupIndicator(null);
        return v;
    }

    public void onHelp() {
        TextView t2 = new TextView(getActivity());
        t2.setText("Astuces :");
        t2.setTextColor(-1);
        t2.setGravity(1);
        t2.setTextSize(25.0f);
        TextView t1 = new TextView(getActivity());
        t1.setText(" - Cliquer sur le logo en haut à gauche ou sur la flèche pour revenir au menu principal\n\n - Cliquer sur le bouton \"outil\" pour pouvoir ajouter ou supprimer des courants de pensée dans le menu des citations (courants et auteurs).\n");
        t1.setTextColor(-1);
        t1.setGravity(3);
        t1.setTextSize(25.0f);
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        adb.setCustomTitle(t2);
        adb.setView(t1);
        adb.setNeutralButton("Ok", (DialogInterface.OnClickListener) null);
        AlertDialog dialog = adb.create();
        dialog.show();
        Button bn = dialog.getButton(-3);
        bn.setHeight(40);
        bn.setTextSize(20.0f);
    }

    public void onTools() {
        t = ToolsHelper.getCourants();
        listCourants = ToolsHelper.getList();
        ListView list = (ListView) getActivity().getLayoutInflater().inflate(R.layout.tools, (ViewGroup) null);
        list.setChoiceMode(2);
        this.adapter = new MultipleListAdapter(getActivity().getApplicationContext(), listCourants, t);
        list.setAdapter(this.adapter);
        list.setCacheColorHint(-16777216);
        for (int i = 0; i < listCourants.size(); i++) {
            list.setItemChecked(i, t.get(Integer.valueOf(i)).booleanValue());
        }
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        TextView title = new TextView(getActivity());
        title.setText("Ajouter / Supprimer des courants");
        title.setBackgroundColor(0);
        title.setGravity(17);
        title.setTextColor(-1);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.BooksListActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (((Boolean) BooksListActivity.t.get(Integer.valueOf(arg2))).booleanValue()) {
                    BooksListActivity.t.put(Integer.valueOf(arg2), false);
                } else {
                    BooksListActivity.t.put(Integer.valueOf(arg2), true);
                }
                BooksListActivity.this.updateDataBase(BooksListActivity.listCourants, BooksListActivity.t);
                BooksListActivity.this.initBooksAdapter();
                AuthorsBooksExpandableListAdapter expListAdapter = new AuthorsBooksExpandableListAdapter(BooksListActivity.this.getActivity(), BooksListActivity.this.expListView, BooksListActivity.this.groupList, BooksListActivity.this.childCollection, BooksListActivity.this.childChildCollection, 1);
                BooksListActivity.this.expListView.setAdapter(expListAdapter);
                BooksListActivity.this.expListView.setGroupIndicator(null);
            }
        });
        if ((getResources().getConfiguration().screenLayout & 15) == 3) {
            title.setTextSize(38.0f);
        } else {
            title.setTextSize(20.0f);
        }
        adb.setCustomTitle(title);
        adb.setView(list);
        adb.setPositiveButton("OK", (DialogInterface.OnClickListener) null);
        AlertDialog dialog = adb.create();
        dialog.show();
        Button bp = dialog.getButton(-1);
        bp.setHeight(40);
        bp.setTextSize(20.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDataBase(Map<Integer, String> listCourants2, Map<Integer, Boolean> t2) {
        String bool;
        this.db = new DataBaseHelper(getActivity().getBaseContext()).getWritableDatabase();
        for (int i = 0; i < listCourants2.size(); i++) {
            if (!t2.get(Integer.valueOf(i)).booleanValue()) {
                bool = "0";
            } else {
                bool = "1";
            }
            String sql = "UPDATE courant SET checked =" + bool + " WHERE  courant ='" + listCourants2.get(Integer.valueOf(i)) + "'";
            this.db.execSQL(sql);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBooksAdapter() {
        this.childCollection = new LinkedHashMap();
        this.childChildCollection = new LinkedHashMap();
        this.cursorCourant = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent,checked From courant where id_parent = 0 and id_courant !=0 and checked=1  ", null);
        this.groupList = new ArrayList();
        this.groupChildList = new ArrayList();
        this.childList = new ArrayList();
        this.cursorCourant.moveToFirst();
        for (int i = 0; i < this.cursorCourant.getCount(); i++) {
            this.groupList.add(this.cursorCourant.getString(1));
            this.cursorBook = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant,nb_quotes From author where book=1 and id_courant =" + this.cursorCourant.getString(0), null);
            this.cursorBook.moveToFirst();
            int groupTotalSize = this.cursorBook.getCount();
            this.cursorCourant1 = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent From courant where id_parent =" + this.cursorCourant.getString(0), null);
            this.childChildList = new ArrayList();
            this.cursorCourant1.moveToFirst();
            if (this.cursorCourant1.getCount() != 0 && this.cursorBook.getCount() != 0) {
                this.childList.add(this.cursorCourant.getString(1));
            }
            for (int j = 0; j < this.cursorCourant1.getCount(); j++) {
                this.cursorBook2 = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant,nb_quotes From author where book=1 and  id_courant =" + this.cursorCourant1.getString(0), null);
                this.cursorBook2.moveToFirst();
                groupTotalSize += this.cursorBook2.getCount();
                this.groupSize += this.cursorBook2.getCount();
                this.childSize += this.cursorBook2.getCount();
                this.cursorCourant2 = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent From courant where id_parent =" + this.cursorCourant1.getString(0), null);
                this.cursorCourant2.moveToFirst();
                if (this.cursorCourant2.getCount() != 0 || this.cursorBook2.getCount() != 0) {
                    this.childList.add(this.cursorCourant1.getString(1));
                }
                if (this.cursorCourant2.getCount() != 0 && this.cursorBook2.getCount() != 0) {
                    this.childChildList.add(this.cursorCourant1.getString(1));
                }
                for (int l = 0; l < this.cursorCourant2.getCount(); l++) {
                    this.cursorBook3 = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant,nb_quotes From author where book=1 and id_courant =" + this.cursorCourant2.getString(0), null);
                    int authorsSize = 0;
                    this.cursorBook3.moveToFirst();
                    groupTotalSize += this.cursorBook3.getCount();
                    this.groupSize += this.cursorBook3.getCount();
                    this.childSize += this.cursorBook3.getCount();
                    for (int o = 0; o < this.cursorBook3.getCount(); o++) {
                        authorsSize += Integer.parseInt(this.cursorBook3.getString(6));
                        this.cursorBook3.moveToNext();
                    }
                    if (authorsSize != 0) {
                        this.childChildList.add(this.cursorCourant2.getString(1));
                    }
                    this.cursorCourant2.moveToNext();
                }
                this.childChildCollection.put(this.cursorCourant1.getString(1), this.childChildList);
                this.childChildList = new ArrayList();
                if (this.groupSize == 0) {
                    this.childList.remove(this.cursorCourant1.getString(1));
                }
                if (this.childSize == 0) {
                    this.childList.remove(this.cursorCourant.getString(1));
                }
                this.childSize = 0;
                this.groupSize = 0;
                this.cursorCourant1.moveToNext();
            }
            this.childCollection.put(this.cursorCourant.getString(1), this.childList);
            this.childList = new ArrayList();
            if (groupTotalSize == 0) {
                this.groupList.remove(this.cursorCourant.getString(1));
            }
            this.cursorCourant.moveToNext();
        }
    }
}
