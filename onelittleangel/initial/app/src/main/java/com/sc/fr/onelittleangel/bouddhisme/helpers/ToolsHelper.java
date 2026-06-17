package com.sc.fr.onelittleangel.bouddhisme.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ToolsHelper {
    Context context;
    private Cursor cursorCourant;
    private static SQLiteDatabase db = null;
    private static Map<Integer, String> list = new LinkedHashMap();
    private static Map<Integer, Boolean> courants = new LinkedHashMap();

    public ToolsHelper(Context context) {
        this.context = null;
        this.context = context;
        init();
    }

    private Map<Integer, Boolean> init() {
        db = new DataBaseHelper(this.context).getReadableDatabase();
        this.cursorCourant = db.rawQuery("SELECT id_courant as _id,courant,id_parent,checked From courant where id_parent = 0 and id_courant !=0  ", null);
        this.cursorCourant.moveToFirst();
        for (int i = 0; i < this.cursorCourant.getCount(); i++) {
            list.put(Integer.valueOf(i), this.cursorCourant.getString(1));
            if (this.cursorCourant.getString(3).equals("0")) {
                courants.put(Integer.valueOf(i), false);
            } else {
                courants.put(Integer.valueOf(i), true);
            }
            this.cursorCourant.moveToNext();
        }
        this.cursorCourant.close();
        db.close();
        return courants;
    }

    public static Map<Integer, Boolean> getCourants() {
        return courants;
    }

    public static void setCourants(Map<Integer, Boolean> courants2) {
        courants = courants2;
    }

    public static Map<Integer, String> getList() {
        return list;
    }

    public static void setList(Map<Integer, String> list2) {
        list = list2;
    }
}
