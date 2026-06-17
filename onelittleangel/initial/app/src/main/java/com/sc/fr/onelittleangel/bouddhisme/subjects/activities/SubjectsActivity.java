package com.sc.fr.onelittleangel.bouddhisme.subjects.activities;

import android.app.AlertDialog;
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

import com.sc.fr.onelittleangel.bouddhisme.activities.MainActivity;
import com.sc.fr.onelittleangel.bouddhisme.adapter.MultipleListAdapter;
import com.sc.fr.onelittleangel.bouddhisme.fragments.MyViewPager;
import com.sc.fr.onelittleangel.bouddhisme.helpers.DataBaseHelper;
import com.sc.fr.onelittleangel.bouddhisme.helpers.ToolsHelper;
import com.sc.fr.onelittleangel.bouddhisme.subjects.adapters.ExpandableSubjectsListAdapter;
import com.sc.fr.onelittleangel.bouddhisme.views.ActionBarMenusCustom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class SubjectsActivity extends Fragment {
    static MyViewPager viewPager;
    ActionBarMenusCustom actionBar;
    ListAdapter adapter;
    Animation animZoomIn;
    Animation animZoomOut;
    Map<String, List<String>> childChildCollection;
    List<String> childChildList;
    Map<String, Integer> childChildQuotesSize;
    Map<String, List<String>> childCollection;
    List<String> childList;
    Map<String, Integer> childQuotesSize;
    ExpandableListView expListView;
    Typeface face;
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
    private int childChildSize = 0;
    private SQLiteDatabase db = null;
    private Cursor cursorCourant = null;
    private Cursor cursorCourant1 = null;
    private Cursor cursorCourant2 = null;

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.subjects_main_list, container, false);
        this.text = (TextView) v.findViewById(R.id.table);
        this.text.setText(R.string.courant);
        this.actionBar = (ActionBarMenusCustom) v.findViewById(R.id.actionbar);
        this.actionBar.setTitle("Courants");
        this.actionBar.setBackgroundResource(R.drawable.actionbar_background);
        this.face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mtcorsva.ttf");
        this.animZoomIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.anim_zoomin);
        this.animZoomOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.anim_zoomout);
        this.r = (ImageView) v.findViewById(R.id.back);
        this.tools = (ImageView) v.findViewById(R.id.tools);
        this.help = (ImageView) v.findViewById(R.id.help);
        this.logo = (ImageView) this.actionBar.findViewById(R.id.ola_logo);
        View.OnClickListener oc = new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.activities.SubjectsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (v2.getTag().equals("logo")) {
                    SubjectsActivity.this.tag = "logo";
                    SubjectsActivity.this.animZoomIn = AnimationUtils.loadAnimation(SubjectsActivity.this.getActivity().getApplicationContext(), R.anim.anim_logo_zoomin);
                    SubjectsActivity.this.animZoomOut = AnimationUtils.loadAnimation(SubjectsActivity.this.getActivity().getApplicationContext(), R.anim.anim_logo_zoomout);
                    SubjectsActivity.this.logo.startAnimation(SubjectsActivity.this.animZoomIn);
                    SubjectsActivity.this.logo.startAnimation(SubjectsActivity.this.animZoomOut);
                    SubjectsActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.activities.SubjectsActivity.1.1
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
                    SubjectsActivity.this.tag = "back";
                    SubjectsActivity.this.animZoomIn = AnimationUtils.loadAnimation(SubjectsActivity.this.getActivity().getApplicationContext(), R.anim.anim_back_zoomin);
                    SubjectsActivity.this.animZoomOut = AnimationUtils.loadAnimation(SubjectsActivity.this.getActivity().getApplicationContext(), R.anim.anim_back_zoomout);
                    SubjectsActivity.this.r.startAnimation(SubjectsActivity.this.animZoomIn);
                    SubjectsActivity.this.r.startAnimation(SubjectsActivity.this.animZoomOut);
                    SubjectsActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.activities.SubjectsActivity.1.2
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
                    SubjectsActivity.this.tag = "logo";
                    SubjectsActivity.this.animZoomIn = AnimationUtils.loadAnimation(SubjectsActivity.this.getActivity().getApplicationContext(), R.anim.anim_help_zoomin);
                    SubjectsActivity.this.animZoomOut = AnimationUtils.loadAnimation(SubjectsActivity.this.getActivity().getApplicationContext(), R.anim.anim_help_zoomout);
                    SubjectsActivity.this.help.startAnimation(SubjectsActivity.this.animZoomIn);
                    SubjectsActivity.this.help.startAnimation(SubjectsActivity.this.animZoomOut);
                    SubjectsActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.activities.SubjectsActivity.1.3
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            SubjectsActivity.this.onHelp();
                        }
                    });
                }
                if (v2.getTag().equals("tools")) {
                    SubjectsActivity.this.tag = "tools";
                    SubjectsActivity.this.animZoomIn = AnimationUtils.loadAnimation(SubjectsActivity.this.getActivity().getApplicationContext(), R.anim.anim_tools_zoomin);
                    SubjectsActivity.this.animZoomOut = AnimationUtils.loadAnimation(SubjectsActivity.this.getActivity().getApplicationContext(), R.anim.anim_tools_zoomout);
                    SubjectsActivity.this.tools.startAnimation(SubjectsActivity.this.animZoomIn);
                    SubjectsActivity.this.tools.startAnimation(SubjectsActivity.this.animZoomOut);
                    SubjectsActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.activities.SubjectsActivity.1.4
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            SubjectsActivity.this.onTools();
                        }
                    });
                }
            }
        };
        this.logo.setOnClickListener(oc);
        this.r.setOnClickListener(oc);
        this.tools.setOnClickListener(oc);
        this.help.setOnClickListener(oc);
        this.text.setTypeface(this.face);
        this.toolsHelper = new ToolsHelper(getActivity().getApplicationContext());
        t = ToolsHelper.getCourants();
        listCourants = ToolsHelper.getList();
        this.db = new DataBaseHelper(getActivity().getBaseContext()).getReadableDatabase();
        initSubjectsAdapter();
        this.expListView = (ExpandableListView) v.findViewById(R.id.subjects_main_list);
        ExpandableSubjectsListAdapter expListAdapter = new ExpandableSubjectsListAdapter(getActivity(), this.expListView, this.groupList, this.groupQuotesSize, this.childCollection, this.childQuotesSize, this.groupChildList, this.childChildCollection, this.childChildQuotesSize, viewPager);
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
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.activities.SubjectsActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (((Boolean) SubjectsActivity.t.get(Integer.valueOf(arg2))).booleanValue()) {
                    SubjectsActivity.t.put(Integer.valueOf(arg2), false);
                } else {
                    SubjectsActivity.t.put(Integer.valueOf(arg2), true);
                }
                SubjectsActivity.this.updateDataBase(SubjectsActivity.listCourants, SubjectsActivity.t);
                SubjectsActivity.this.initSubjectsAdapter();
                ExpandableSubjectsListAdapter expListAdapter = new ExpandableSubjectsListAdapter(SubjectsActivity.this.getActivity(), SubjectsActivity.this.expListView, SubjectsActivity.this.groupList, SubjectsActivity.this.groupQuotesSize, SubjectsActivity.this.childCollection, SubjectsActivity.this.childQuotesSize, SubjectsActivity.this.groupChildList, SubjectsActivity.this.childChildCollection, SubjectsActivity.this.childChildQuotesSize, SubjectsActivity.viewPager);
                SubjectsActivity.this.expListView.setAdapter(expListAdapter);
                SubjectsActivity.this.expListView.setGroupIndicator(null);
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
    public void initSubjectsAdapter() {
        this.childCollection = new LinkedHashMap();
        this.childChildCollection = new LinkedHashMap();
        this.groupQuotesSize = new LinkedHashMap();
        this.childQuotesSize = new LinkedHashMap();
        this.childChildQuotesSize = new LinkedHashMap();
        this.cursorCourant = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent,checked,nb_quotes From courant where id_parent = 0 and id_courant !=0 and checked=1  ", null);
        this.groupList = new ArrayList();
        this.groupChildList = new ArrayList();
        this.childList = new ArrayList();
        this.cursorCourant.moveToFirst();
        for (int i = 0; i < this.cursorCourant.getCount(); i++) {
            this.groupChildList.add(this.cursorCourant.getString(1));
            this.childChildCollection.put(this.cursorCourant.getString(1), new ArrayList());
            this.groupSize += Integer.parseInt(this.cursorCourant.getString(4));
            this.cursorCourant1 = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent,nb_quotes From courant where id_parent =" + this.cursorCourant.getString(0), null);
            this.childChildList = new ArrayList();
            if (this.cursorCourant1.getCount() != 0 && this.groupSize != 0) {
                this.childList.add(this.cursorCourant.getString(1));
                this.childQuotesSize.put(this.cursorCourant.getString(1), Integer.valueOf(Integer.parseInt(this.cursorCourant.getString(4))));
            }
            this.cursorCourant1.moveToFirst();
            for (int j = 0; j < this.cursorCourant1.getCount(); j++) {
                this.childSize += Integer.parseInt(this.cursorCourant1.getString(3));
                this.groupSize += Integer.parseInt(this.cursorCourant1.getString(3));
                this.cursorCourant2 = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent,nb_quotes From courant where id_parent =" + this.cursorCourant1.getString(0), null);
                this.cursorCourant2.moveToFirst();
                if (this.cursorCourant2.getCount() != 0 && this.childSize != 0) {
                    this.childChildList.add(this.cursorCourant1.getString(1));
                    this.childChildQuotesSize.put(this.cursorCourant1.getString(1), Integer.valueOf(this.childSize));
                }
                for (int l = 0; l < this.cursorCourant2.getCount(); l++) {
                    this.childChildSize += Integer.parseInt(this.cursorCourant2.getString(3));
                    this.childSize += Integer.parseInt(this.cursorCourant2.getString(3));
                    this.groupSize += Integer.parseInt(this.cursorCourant2.getString(3));
                    if (this.childChildSize != 0) {
                        this.childChildList.add(this.cursorCourant2.getString(1));
                        this.childChildQuotesSize.put(this.cursorCourant2.getString(1), Integer.valueOf(this.childChildSize));
                    }
                    this.childChildSize = 0;
                    this.cursorCourant2.moveToNext();
                }
                this.childChildCollection.put(this.cursorCourant1.getString(1), this.childChildList);
                this.childChildList = new ArrayList();
                if (this.childSize != 0) {
                    this.childList.add(this.cursorCourant1.getString(1));
                    this.childQuotesSize.put(this.cursorCourant1.getString(1), Integer.valueOf(this.childSize));
                }
                this.childSize = 0;
                this.cursorCourant1.moveToNext();
            }
            if (this.groupSize != 0) {
                this.groupQuotesSize.put(this.cursorCourant.getString(1), Integer.valueOf(this.groupSize));
                this.groupList.add(this.cursorCourant.getString(1));
            }
            this.childCollection.put(this.cursorCourant.getString(1), this.childList);
            this.childList = new ArrayList();
            this.groupSize = 0;
            this.cursorCourant.moveToNext();
        }
    }
}
