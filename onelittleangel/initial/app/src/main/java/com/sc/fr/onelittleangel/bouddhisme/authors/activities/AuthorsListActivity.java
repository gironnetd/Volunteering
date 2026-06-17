package com.sc.fr.onelittleangel.bouddhisme.authors.activities;

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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.sc.fr.onelittleangel.bouddhisme.R;
import com.sc.fr.onelittleangel.bouddhisme.activities.MainActivity;
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

/* JADX INFO: loaded from: classes.dex */
public class AuthorsListActivity extends Fragment {
    private static Map<Integer, String> listCourants = new LinkedHashMap();
    private static Map<Integer, Boolean> t = new LinkedHashMap();
    static MyViewPager viewPager;
    ActionBarMenusCustom actionBar;
    Activity activity;
    ListAdapter adapter;
    Animation animZoomIn;
    Animation animZoomOut;
    Map<String, List<String>> childChildCollection;
    List<String> childChildList;
    Map<String, List<String>> childCollection;
    List<String> childList;
    TextView comma;
    Context context;
    private Cursor cursorAuthor;
    private Cursor cursorAuthor2;
    private Cursor cursorAuthor3;
    private Cursor cursorCourant;
    private Cursor cursorCourant2;
    ExpandableListView expListView;
    Typeface face;
    List<String> groupChildList;
    List<String> groupList;
    private ImageView help;
    ImageView logo;
    public WindowManager.LayoutParams lp;
    ImageView r;
    String tag;
    TextView text;
    TextView title;
    ImageView tools;
    ToolsHelper toolsHelper;
    private int groupSize = 0;
    int totalQuotes = 0;
    int totalAuthors = 0;
    int previousItem = -1;
    private SQLiteDatabase db = null;
    private Cursor cursorCourant1 = null;
    int totalAuthorsQuotes = 0;
    int totalChildAuthors = 0;
    MainActivity ma = new MainActivity();

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.authors_main_list, container, false);
        this.context = getActivity().getBaseContext();
        this.text = (TextView) v.findViewById(R.id.table);
        this.actionBar = (ActionBarMenusCustom) v.findViewById(R.id.actionbar);
        this.actionBar.setTitle("Auteurs");
        this.actionBar.setBackgroundResource(R.drawable.actionbar_background);
        this.r = (ImageView) v.findViewById(R.id.back);
        this.tools = (ImageView) v.findViewById(R.id.tools);
        this.help = (ImageView) v.findViewById(R.id.help);
        this.logo = (ImageView) this.actionBar.findViewById(R.id.ola_logo);
        View.OnClickListener oc = new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.authors.activities.AuthorsListActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (v2.getTag().equals("logo")) {
                    AuthorsListActivity.this.tag = "logo";
                    AuthorsListActivity.this.animZoomIn = AnimationUtils.loadAnimation(AuthorsListActivity.this.getActivity().getApplicationContext(), R.anim.anim_logo_zoomin);
                    AuthorsListActivity.this.animZoomOut = AnimationUtils.loadAnimation(AuthorsListActivity.this.getActivity().getApplicationContext(), R.anim.anim_logo_zoomout);
                    AuthorsListActivity.this.logo.startAnimation(AuthorsListActivity.this.animZoomIn);
                    AuthorsListActivity.this.logo.startAnimation(AuthorsListActivity.this.animZoomOut);
                    AuthorsListActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.authors.activities.AuthorsListActivity.1.1
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
                    AuthorsListActivity.this.tag = "back";
                    AuthorsListActivity.this.animZoomIn = AnimationUtils.loadAnimation(AuthorsListActivity.this.getActivity().getApplicationContext(), R.anim.anim_back_zoomin);
                    AuthorsListActivity.this.animZoomOut = AnimationUtils.loadAnimation(AuthorsListActivity.this.getActivity().getApplicationContext(), R.anim.anim_back_zoomout);
                    AuthorsListActivity.this.r.startAnimation(AuthorsListActivity.this.animZoomIn);
                    AuthorsListActivity.this.r.startAnimation(AuthorsListActivity.this.animZoomOut);
                    AuthorsListActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.authors.activities.AuthorsListActivity.1.2
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
                    AuthorsListActivity.this.tag = "logo";
                    AuthorsListActivity.this.animZoomIn = AnimationUtils.loadAnimation(AuthorsListActivity.this.getActivity().getApplicationContext(), R.anim.anim_help_zoomin);
                    AuthorsListActivity.this.animZoomOut = AnimationUtils.loadAnimation(AuthorsListActivity.this.getActivity().getApplicationContext(), R.anim.anim_help_zoomout);
                    AuthorsListActivity.this.help.startAnimation(AuthorsListActivity.this.animZoomIn);
                    AuthorsListActivity.this.help.startAnimation(AuthorsListActivity.this.animZoomOut);
                    AuthorsListActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.authors.activities.AuthorsListActivity.1.3
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            AuthorsListActivity.this.onHelp();
                        }
                    });
                }
                if (v2.getTag().equals("tools")) {
                    AuthorsListActivity.this.tag = "tools";
                    AuthorsListActivity.this.animZoomIn = AnimationUtils.loadAnimation(AuthorsListActivity.this.getActivity().getApplicationContext(), R.anim.anim_tools_zoomin);
                    AuthorsListActivity.this.animZoomOut = AnimationUtils.loadAnimation(AuthorsListActivity.this.getActivity().getApplicationContext(), R.anim.anim_tools_zoomout);
                    AuthorsListActivity.this.tools.startAnimation(AuthorsListActivity.this.animZoomIn);
                    AuthorsListActivity.this.tools.startAnimation(AuthorsListActivity.this.animZoomOut);
                    AuthorsListActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.authors.activities.AuthorsListActivity.1.4
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            AuthorsListActivity.this.onTools();
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
        this.title = (TextView) v.findViewById(R.id.title);
        this.title.setText(R.string.author);
        this.title.setTypeface(this.face);
        this.db = new DataBaseHelper(getActivity().getBaseContext()).getReadableDatabase();
        this.toolsHelper = new ToolsHelper(getActivity().getApplicationContext());
        t = ToolsHelper.getCourants();
        listCourants = ToolsHelper.getList();
        initAuthorsAdapter();
        this.expListView = (ExpandableListView) v.findViewById(R.id.authors_main_list);
        AuthorsBooksExpandableListAdapter expListAdapter = new AuthorsBooksExpandableListAdapter(getActivity(), this.expListView, this.groupList, this.childCollection, this.childChildCollection, 0);
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
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.authors.activities.AuthorsListActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (((Boolean) AuthorsListActivity.t.get(Integer.valueOf(arg2))).booleanValue()) {
                    AuthorsListActivity.t.put(Integer.valueOf(arg2), false);
                } else {
                    AuthorsListActivity.t.put(Integer.valueOf(arg2), true);
                }
                AuthorsListActivity.this.updateDataBase(AuthorsListActivity.listCourants, AuthorsListActivity.t);
                AuthorsListActivity.this.initAuthorsAdapter();
                AuthorsBooksExpandableListAdapter expListAdapter = new AuthorsBooksExpandableListAdapter(AuthorsListActivity.this.getActivity(), AuthorsListActivity.this.expListView, AuthorsListActivity.this.groupList, AuthorsListActivity.this.childCollection, AuthorsListActivity.this.childChildCollection, 0);
                AuthorsListActivity.this.expListView.setAdapter(expListAdapter);
                AuthorsListActivity.this.expListView.setGroupIndicator(null);
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
    public void initAuthorsAdapter() {
        this.childCollection = new LinkedHashMap();
        this.childChildCollection = new LinkedHashMap();
        this.cursorCourant = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent,checked From courant where id_parent = 0 and id_courant !=0 and checked=1  ", null);
        this.groupList = new ArrayList();
        this.groupChildList = new ArrayList();
        this.childList = new ArrayList();
        this.cursorCourant.moveToFirst();
        for (int i = 0; i < this.cursorCourant.getCount(); i++) {
            this.groupList.add(this.cursorCourant.getString(1));
            this.cursorAuthor = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant From author where book=0 and id_courant =" + this.cursorCourant.getString(0), null);
            this.cursorAuthor.moveToFirst();
            this.cursorCourant1 = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent From courant where id_parent =" + this.cursorCourant.getString(0), null);
            this.childChildList = new ArrayList();
            this.cursorCourant1.moveToFirst();
            if (this.cursorCourant1.getCount() != 0 && this.cursorAuthor.getCount() != 0) {
                this.childList.add(this.cursorCourant.getString(1));
            }
            for (int j = 0; j < this.cursorCourant1.getCount(); j++) {
                this.cursorAuthor2 = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant From author where book=0 and  id_courant =" + this.cursorCourant1.getString(0), null);
                this.cursorAuthor2.moveToFirst();
                this.groupSize = this.cursorAuthor2.getCount();
                this.cursorCourant2 = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent From courant where id_parent =" + this.cursorCourant1.getString(0), null);
                this.cursorCourant2.moveToFirst();
                if (this.cursorCourant2.getCount() != 0 || this.cursorAuthor2.getCount() != 0) {
                    this.childList.add(this.cursorCourant1.getString(1));
                }
                if (this.cursorCourant2.getCount() != 0 && this.cursorAuthor2.getCount() != 0) {
                    this.childChildList.add(this.cursorCourant1.getString(1));
                }
                for (int l = 0; l < this.cursorCourant2.getCount(); l++) {
                    this.cursorAuthor3 = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant From author where book=0 and id_courant =" + this.cursorCourant2.getString(0), null);
                    this.cursorAuthor3.moveToFirst();
                    this.groupSize += this.cursorAuthor3.getCount();
                    int authorsSize = 0 + this.cursorAuthor3.getCount();
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
                this.groupSize = 0;
                this.cursorCourant1.moveToNext();
            }
            this.childCollection.put(this.cursorCourant.getString(1), this.childList);
            this.childList = new ArrayList();
            this.cursorCourant.moveToNext();
        }
    }
}
