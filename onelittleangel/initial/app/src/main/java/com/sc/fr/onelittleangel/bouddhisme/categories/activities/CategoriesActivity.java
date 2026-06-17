package com.sc.fr.onelittleangel.bouddhisme.categories.activities;

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
import com.sc.fr.onelittleangel.bouddhisme.categories.adapters.CategoriesExpandableAdapter;
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
public class CategoriesActivity extends Fragment {
    private static Map<Integer, String> listCourants = new LinkedHashMap();
    private static Map<Integer, Boolean> t = new LinkedHashMap();
    static MyViewPager viewPager;
    ActionBarMenusCustom actionBar;
    ListAdapter adapter;
    Animation animZoomIn;
    Animation animZoomOut;
    Map<String, List<String>> childCollection;
    List<String> childList;
    Cursor cursorQuoCategory2;
    Cursor cursorQuote2;
    ExpandableListView expListView;
    Typeface face;
    List<String> groupList;
    private ImageView help;
    ImageView logo;
    ImageView r;
    String tag;
    TextView text;
    ImageView tools;
    ToolsHelper toolsHelper;
    List<String> childChildList = null;
    Map<String, List<String>> childChildCollection = null;
    Map<String, Integer> groupCategoriesId = new LinkedHashMap();
    Map<String, Integer> childCategoriesId = new LinkedHashMap();
    Map<String, Integer> groupCategoriesSize = new LinkedHashMap();
    Map<String, Integer> childCategoriesSize = new LinkedHashMap();
    private SQLiteDatabase db = null;
    private Cursor cursorCategory = null;

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.categories_main_list, container, false);
        this.text = (TextView) v.findViewById(R.id.table);
        this.text.setText(R.string.category);
        this.actionBar = (ActionBarMenusCustom) v.findViewById(R.id.actionbar);
        this.actionBar.setTitle("Thèmes");
        this.actionBar.setBackgroundResource(R.drawable.actionbar_background);
        this.face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mtcorsva.ttf");
        this.animZoomIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.anim_zoomin);
        this.animZoomOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.anim_zoomout);
        this.r = (ImageView) v.findViewById(R.id.back);
        this.tools = (ImageView) v.findViewById(R.id.tools);
        this.help = (ImageView) v.findViewById(R.id.help);
        this.logo = (ImageView) this.actionBar.findViewById(R.id.ola_logo);
        View.OnClickListener oc = new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.categories.activities.CategoriesActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (v2.getTag().equals("logo")) {
                    CategoriesActivity.this.tag = "logo";
                    CategoriesActivity.this.animZoomIn = AnimationUtils.loadAnimation(CategoriesActivity.this.getActivity().getApplicationContext(), R.anim.anim_logo_zoomin);
                    CategoriesActivity.this.animZoomOut = AnimationUtils.loadAnimation(CategoriesActivity.this.getActivity().getApplicationContext(), R.anim.anim_logo_zoomout);
                    CategoriesActivity.this.logo.startAnimation(CategoriesActivity.this.animZoomIn);
                    CategoriesActivity.this.logo.startAnimation(CategoriesActivity.this.animZoomOut);
                    CategoriesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.categories.activities.CategoriesActivity.1.1
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
                    CategoriesActivity.this.tag = "back";
                    CategoriesActivity.this.animZoomIn = AnimationUtils.loadAnimation(CategoriesActivity.this.getActivity().getApplicationContext(), R.anim.anim_back_zoomin);
                    CategoriesActivity.this.animZoomOut = AnimationUtils.loadAnimation(CategoriesActivity.this.getActivity().getApplicationContext(), R.anim.anim_back_zoomout);
                    CategoriesActivity.this.r.startAnimation(CategoriesActivity.this.animZoomIn);
                    CategoriesActivity.this.r.startAnimation(CategoriesActivity.this.animZoomOut);
                    CategoriesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.categories.activities.CategoriesActivity.1.2
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
                    CategoriesActivity.this.tag = "logo";
                    CategoriesActivity.this.animZoomIn = AnimationUtils.loadAnimation(CategoriesActivity.this.getActivity().getApplicationContext(), R.anim.anim_help_zoomin);
                    CategoriesActivity.this.animZoomOut = AnimationUtils.loadAnimation(CategoriesActivity.this.getActivity().getApplicationContext(), R.anim.anim_help_zoomout);
                    CategoriesActivity.this.help.startAnimation(CategoriesActivity.this.animZoomIn);
                    CategoriesActivity.this.help.startAnimation(CategoriesActivity.this.animZoomOut);
                    CategoriesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.categories.activities.CategoriesActivity.1.3
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            CategoriesActivity.this.onHelp();
                        }
                    });
                }
                if (v2.getTag().equals("tools")) {
                    CategoriesActivity.this.tag = "tools";
                    CategoriesActivity.this.animZoomIn = AnimationUtils.loadAnimation(CategoriesActivity.this.getActivity().getApplicationContext(), R.anim.anim_tools_zoomin);
                    CategoriesActivity.this.animZoomOut = AnimationUtils.loadAnimation(CategoriesActivity.this.getActivity().getApplicationContext(), R.anim.anim_tools_zoomout);
                    CategoriesActivity.this.tools.startAnimation(CategoriesActivity.this.animZoomIn);
                    CategoriesActivity.this.tools.startAnimation(CategoriesActivity.this.animZoomOut);
                    CategoriesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.categories.activities.CategoriesActivity.1.4
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            CategoriesActivity.this.onTools();
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
        this.db = new DataBaseHelper(getActivity().getBaseContext()).getReadableDatabase();
        this.toolsHelper = new ToolsHelper(getActivity().getApplicationContext());
        t = ToolsHelper.getCourants();
        listCourants = ToolsHelper.getList();
        initCategoriesAdapter();
        this.expListView = (ExpandableListView) v.findViewById(R.id.categories_main_list);
        CategoriesExpandableAdapter expListAdapter = new CategoriesExpandableAdapter(getActivity(), this.expListView, this.groupList, this.groupCategoriesId, viewPager);
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
        t1.setText(" - Cliquer sur le logo en haut à gauche ou sur la flèche pour revenir au menu principal\n");
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
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.categories.activities.CategoriesActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (((Boolean) CategoriesActivity.t.get(Integer.valueOf(arg2))).booleanValue()) {
                    CategoriesActivity.t.put(Integer.valueOf(arg2), false);
                } else {
                    CategoriesActivity.t.put(Integer.valueOf(arg2), true);
                }
                CategoriesActivity.this.updateDataBase(CategoriesActivity.listCourants, CategoriesActivity.t);
                CategoriesActivity.this.initCategoriesAdapter();
                CategoriesExpandableAdapter expListAdapter = new CategoriesExpandableAdapter(CategoriesActivity.this.getActivity(), CategoriesActivity.this.expListView, CategoriesActivity.this.groupList, CategoriesActivity.this.groupCategoriesId, CategoriesActivity.viewPager);
                CategoriesActivity.this.expListView.setAdapter(expListAdapter);
                CategoriesActivity.this.expListView.setGroupIndicator(null);
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
    public void initCategoriesAdapter() {
        this.childCollection = new LinkedHashMap();
        this.cursorCategory = this.db.rawQuery("SELECT id_category as _id,category,id_parent,nb_quotes From category where id_parent = 0 and id_category!=0 ", null);
        this.cursorCategory.moveToFirst();
        this.groupList = new ArrayList();
        for (int i = 0; i < this.cursorCategory.getCount(); i++) {
            this.groupList.add(this.cursorCategory.getString(1));
            this.groupCategoriesId.put(this.cursorCategory.getString(1), Integer.valueOf(this.cursorCategory.getString(0)));
            this.cursorCategory.moveToNext();
        }
    }
}
