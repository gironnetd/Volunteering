package com.sc.fr.onelittleangel.bouddhisme.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity;
import com.sc.fr.onelittleangel.bouddhisme.adapter.MultipleListAdapter;
import com.sc.fr.onelittleangel.bouddhisme.fragments.MyViewPager;
import com.sc.fr.onelittleangel.bouddhisme.helpers.DataBaseHelper;
import com.sc.fr.onelittleangel.bouddhisme.helpers.ToolsHelper;
import com.sc.fr.onelittleangel.bouddhisme.menu.adapters.MenuListAdapter;
import com.sc.fr.onelittleangel.bouddhisme.views.ActionBarMenusCustom;
import com.sc.fr.onelittleangel.bouddhisme.views.CustomListView;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class MenuActivity extends ListFragment {
    static MyViewPager viewPager;
    ActionBarMenusCustom actionBar;
    ListAdapter adapter;
    AnimationDrawable animFavorites;
    Animation animZoomIn;
    Animation animZoomOut;
    private Cursor cursorFont;
    Typeface face;
    ImageView favorites;
    private AnimationDrawable frame_menu;
    View.OnTouchListener gestureListener;
    ImageView help;
    long id;
    TextView laptop;
    private int layoutMargin;
    ListView list;
    private ImageView loading_menu;
    private RelativeLayout menugroup;
    private RelativeLayout menuheader;
    ListView parent;
    int position;
    ImageView progress;
    private int row;
    TextView table;
    TextView text;
    ImageView tools;
    ToolsHelper toolsHelper;
    View view;
    private static SQLiteDatabase db = null;
    static int firstLetterSize = 0;
    static int textSize = 0;
    static int heightPortrait = 0;
    static int heightLandscape = 0;
    private static Map<Integer, String> listCourants = new LinkedHashMap();
    private static Map<Integer, Boolean> t = new LinkedHashMap();
    private ArrayList<String> tables_fr = new ArrayList<>();
    private SQLiteDatabase dbw = null;
    ArrayList<String> fontList = new ArrayList<>();

    @Override // android.support.v4.app.ListFragment, android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu, container, false);
        this.fontList.add("Almendra-Regular.ttf");
        this.fontList.add("Cinzel-Regular.ttf");
        this.fontList.add("EagleLake-Regular.ttf");
        this.fontList.add("FrederickatheGreat-Regular.ttf");
        this.fontList.add("Gabriela-Regular.ttf");
        this.fontList.add("GermaniaOne-Regular.ttf");
        this.fontList.add("IM_Fell_Double_Pica_SC.ttf");
        this.fontList.add("IM_Fell_Double_Pica.ttf");
        this.fontList.add("IM_Fell_DW_Pica_SC.ttf");
        this.fontList.add("IM_Fell_DW_Pica.ttf");
        this.fontList.add("IM_Fell_English_SC.ttf");
        this.fontList.add("IM_Fell_English.ttf");
        this.fontList.add("IM_Fell_Great_Primer_SC.ttf");
        this.fontList.add("IM_Fell_Great_Primer.ttf");
        this.fontList.add("Italianno-Regular.ttf");
        this.fontList.add("JimNightshade-Regular.ttf");
        this.fontList.add("Junge-Regular.ttf");
        this.fontList.add("LoversQuarrel-Regular.ttf");
        this.fontList.add("Macondo-Regular.ttf");
        this.fontList.add("MacondoSwashCaps-Regular.ttf");
        this.fontList.add("MateSC-Regular.ttf");
        this.fontList.add("MetalMania-Regular.ttf");
        this.fontList.add("mtcorsva.ttf");
        this.fontList.add("NovaOval.ttf");
        this.fontList.add("Parisienne-Regular.ttf");
        this.fontList.add("Quintessential-Regular.ttf");
        this.fontList.add("Rosarivo-Regular.ttf");
        this.fontList.add("SortsMillGoudy-Italic.ttf");
        this.fontList.add("UnifrakturCook-Bold.ttf");
        this.fontList.add("UnifrakturMaguntia-Book.ttf");
        this.text = (TextView) v.findViewById(R.id.table);
        this.face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mtcorsva.ttf");
        this.actionBar = new ActionBarMenusCustom(getActivity());
        this.actionBar = (ActionBarMenusCustom) v.findViewById(R.id.actionbar);
        this.actionBar.setTitle("Bouddhisme");
        this.actionBar.setBackgroundResource(R.drawable.actionbar_background);
        this.animZoomIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.anim_zoomin);
        this.animZoomOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.anim_zoomout);
        this.favorites = (ImageView) v.findViewById(R.id.favorites);
        this.help = (ImageView) v.findViewById(R.id.help);
        this.favorites.setOnClickListener(new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.menu.MenuActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                v2.startAnimation(MenuActivity.this.animZoomIn);
                v2.startAnimation(MenuActivity.this.animZoomOut);
                MenuActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.menu.MenuActivity.1.1
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        MenuActivity.this.startActivity(new Intent(MenuActivity.this.getActivity(), (Class<?>) QuotesTextFavoritesActivity.class));
                        MenuActivity.db.close();
                        MenuActivity.this.dbw.close();
                        MenuActivity.this.getActivity().finish();
                    }
                });
            }
        });
        this.text.setTypeface(this.face);
        this.tools = (ImageView) v.findViewById(R.id.tools);
        this.tools.setOnClickListener(new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.menu.MenuActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                MenuActivity.this.animZoomIn = AnimationUtils.loadAnimation(MenuActivity.this.getActivity().getApplicationContext(), R.anim.anim_tools_zoomin);
                MenuActivity.this.animZoomOut = AnimationUtils.loadAnimation(MenuActivity.this.getActivity().getApplicationContext(), R.anim.anim_tools_zoomout);
                v2.startAnimation(MenuActivity.this.animZoomIn);
                v2.startAnimation(MenuActivity.this.animZoomOut);
                MenuActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.menu.MenuActivity.2.1
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        MenuActivity.this.onTools();
                    }
                });
            }
        });
        this.help.setOnClickListener(new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.menu.MenuActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (v2.getTag().equals("help")) {
                    MenuActivity.this.animZoomIn = AnimationUtils.loadAnimation(MenuActivity.this.getActivity().getApplicationContext(), R.anim.anim_help_zoomin);
                    MenuActivity.this.animZoomOut = AnimationUtils.loadAnimation(MenuActivity.this.getActivity().getApplicationContext(), R.anim.anim_help_zoomout);
                    v2.startAnimation(MenuActivity.this.animZoomIn);
                    v2.startAnimation(MenuActivity.this.animZoomOut);
                    MenuActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.menu.MenuActivity.3.1
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            MenuActivity.this.onHelp();
                        }
                    });
                }
            }
        });
        this.menuheader = (RelativeLayout) v.findViewById(R.id.menuheader);
        db = new DataBaseHelper(getActivity().getBaseContext()).getReadableDatabase();
        this.dbw = new DataBaseHelper(getActivity().getBaseContext()).getWritableDatabase();
        this.cursorFont = this.dbw.rawQuery("SELECT id as _id,firstletter,textsize From font where id = 1", null);
        this.cursorFont.moveToFirst();
        firstLetterSize = Integer.parseInt(this.cursorFont.getString(1));
        textSize = Integer.parseInt(this.cursorFont.getString(2));
        DisplayMetrics metrics1 = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics1);
        int i = metrics1.densityDpi;
        if (textSize <= 0) {
            for (int i2 = 0; i2 < this.fontList.size(); i2++) {
                copyFile(getActivity(), this.fontList.get(i2));
            }
            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int i3 = metrics.densityDpi;
            if ((getResources().getConfiguration().screenLayout & 15) == 4) {
                heightPortrait = 550;
                heightLandscape = 150;
                firstLetterSize = 85;
                textSize = 30;
                String sql = "UPDATE font SET firstletter =" + firstLetterSize + "  WHERE  id = 1";
                String sql1 = "UPDATE font SET textsize=" + textSize + " WHERE  id = 1";
                String sql2 = "UPDATE font SET heightPortrait =" + heightPortrait + "  WHERE  id = 1";
                String sql3 = "UPDATE font SET heightLandscape=" + heightLandscape + " WHERE  id = 1";
                this.dbw.execSQL(sql);
                this.dbw.execSQL(sql1);
                this.dbw.execSQL(sql2);
                this.dbw.execSQL(sql3);
                this.dbw.execSQL("UPDATE font SET screensize='xlarge' WHERE  id = 1");
            } else if ((getResources().getConfiguration().screenLayout & 15) == 3) {
                heightPortrait = 550;
                heightLandscape = 150;
                firstLetterSize = 85;
                textSize = 30;
                String sql4 = "UPDATE font SET firstletter =" + firstLetterSize + "  WHERE  id = 1";
                String sql12 = "UPDATE font SET textsize=" + textSize + " WHERE  id = 1";
                String sql22 = "UPDATE font SET heightPortrait =" + heightPortrait + "  WHERE  id = 1";
                String sql32 = "UPDATE font SET heightLandscape=" + heightLandscape + " WHERE  id = 1";
                this.dbw.execSQL(sql4);
                this.dbw.execSQL(sql12);
                this.dbw.execSQL(sql22);
                this.dbw.execSQL(sql32);
                this.dbw.execSQL("UPDATE font SET screensize='large' WHERE  id = 1");
            } else if ((getResources().getConfiguration().screenLayout & 15) == 2) {
                heightPortrait = 150;
                heightLandscape = 70;
                firstLetterSize = 50;
                textSize = 15;
                String sql5 = "UPDATE font SET firstletter =" + firstLetterSize + "  WHERE  id = 1";
                String sql13 = "UPDATE font SET textsize=" + textSize + " WHERE  id = 1";
                String sql23 = "UPDATE font SET heightPortrait =" + heightPortrait + "  WHERE  id = 1";
                String sql33 = "UPDATE font SET heightLandscape=" + heightLandscape + " WHERE  id = 1";
                this.dbw.execSQL(sql5);
                this.dbw.execSQL(sql13);
                this.dbw.execSQL(sql23);
                this.dbw.execSQL(sql33);
                this.dbw.execSQL("UPDATE font SET screensize='normal' WHERE  id = 1");
            } else if ((getResources().getConfiguration().screenLayout & 15) == 1) {
                heightPortrait = 150;
                heightLandscape = 70;
                firstLetterSize = 40;
                textSize = 12;
                String sql6 = "UPDATE font SET firstletter =" + firstLetterSize + "  WHERE  id = 1";
                String sql14 = "UPDATE font SET textsize=" + textSize + " WHERE  id = 1";
                String sql24 = "UPDATE font SET heightPortrait =" + heightPortrait + "  WHERE  id = 1";
                String sql34 = "UPDATE font SET heightLandscape=" + heightLandscape + " WHERE  id = 1";
                this.dbw.execSQL(sql6);
                this.dbw.execSQL(sql14);
                this.dbw.execSQL(sql24);
                this.dbw.execSQL(sql34);
                this.dbw.execSQL("UPDATE font SET screensize='small' WHERE  id = 1");
            }
        }
        this.tables_fr = new ArrayList<>();
        this.tables_fr.add(" Auteurs ");
        this.tables_fr.add(" Courants ");
        this.tables_fr.add(" Thèmes ");
        this.tables_fr.add(" Livres sacrés ");
        this.toolsHelper = new ToolsHelper(getActivity().getApplicationContext());
        t = ToolsHelper.getCourants();
        listCourants = ToolsHelper.getList();
        this.list = (ListView) v.findViewById(android.R.id.list);
        this.adapter = new MenuListAdapter(getActivity(), this.tables_fr, (CustomListView) this.list);
        setListAdapter(this.adapter);
        db.close();
        this.dbw.close();
        return v;
    }

    public void onHelp() {
        TextView t2 = new TextView(getActivity());
        t2.setText("Astuces :");
        t2.setTextColor(-1);
        t2.setGravity(1);
        t2.setTextSize(25.0f);
        TextView t1 = new TextView(getActivity());
        t1.setText(" - Cliquer sur le bouton \"outil\" pour pouvoir ajouter ou supprimer des courants de pensée dans le menu des citations (courants et auteurs).\n");
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
        this.list = (ListView) getActivity().getLayoutInflater().inflate(R.layout.tools, (ViewGroup) null);
        this.list.setChoiceMode(2);
        this.adapter = new MultipleListAdapter(getActivity().getApplicationContext(), listCourants, t);
        this.list.setAdapter(this.adapter);
        this.list.setCacheColorHint(-16777216);
        for (int i = 0; i < listCourants.size(); i++) {
            this.list.setItemChecked(i, t.get(Integer.valueOf(i)).booleanValue());
        }
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        TextView title = new TextView(getActivity());
        title.setText("Ajouter / Supprimer des courants");
        title.setBackgroundColor(0);
        title.setGravity(17);
        title.setTextColor(-1);
        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.menu.MenuActivity.4
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (((Boolean) MenuActivity.t.get(Integer.valueOf(arg2))).booleanValue()) {
                    MenuActivity.t.put(Integer.valueOf(arg2), false);
                } else {
                    MenuActivity.t.put(Integer.valueOf(arg2), true);
                }
                MenuActivity.this.updateDataBase(MenuActivity.listCourants, MenuActivity.t);
            }
        });
        if ((getResources().getConfiguration().screenLayout & 15) == 3) {
            title.setTextSize(38.0f);
        } else {
            title.setTextSize(20.0f);
        }
        adb.setCustomTitle(title);
        adb.setView(this.list);
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
        db = new DataBaseHelper(getActivity().getBaseContext()).getWritableDatabase();
        for (int i = 0; i < listCourants2.size(); i++) {
            if (!t2.get(Integer.valueOf(i)).booleanValue()) {
                bool = "0";
            } else {
                bool = "1";
            }
            String sql = "UPDATE courant SET checked =" + bool + " WHERE  courant ='" + listCourants2.get(Integer.valueOf(i)) + "'";
            db.execSQL(sql);
        }
    }

    private boolean copyFile(Context context, String fileName) {
        try {
            FileOutputStream out = context.openFileOutput(fileName, 0);
            InputStream in = context.getAssets().open("fonts/" + fileName);
            byte[] buf = new byte[1024];
            while (true) {
                int len = in.read(buf);
                if (len > 0) {
                    out.write(buf, 0, len);
                } else {
                    out.close();
                    in.close();
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }
}
