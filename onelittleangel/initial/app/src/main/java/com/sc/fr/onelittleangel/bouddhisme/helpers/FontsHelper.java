package com.sc.fr.onelittleangel.bouddhisme.helpers;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class FontsHelper {
    private static Cursor cursorCourant;
    private static String selectedFont;
    static Activity context = null;
    private static SQLiteDatabase db = null;
    private static Map<Integer, String> fontList = new LinkedHashMap();
    private static Map<Integer, Boolean> bool = new LinkedHashMap();

    public FontsHelper(Activity context2) {
        context = context2;
        init();
    }

    public static void init() {
        db = new DataBaseHelper(context).getReadableDatabase();
        cursorCourant = db.rawQuery("SELECT id as _id,font,selected From textfont", null);
        cursorCourant.moveToFirst();
        for (int i = 0; i < cursorCourant.getCount(); i++) {
            fontList.put(Integer.valueOf(i), cursorCourant.getString(1));
            if (cursorCourant.getString(2).equals("0")) {
                bool.put(Integer.valueOf(i), false);
            } else {
                bool.put(Integer.valueOf(i), true);
                selectedFont = cursorCourant.getString(1);
            }
            cursorCourant.moveToNext();
        }
        cursorCourant.close();
        db.close();
    }

    public static Map<Integer, String> getFontList() {
        return fontList;
    }

    public static void setFontList(Map<Integer, String> fontList2) {
        fontList = fontList2;
    }

    public static Map<Integer, Boolean> getBool() {
        return bool;
    }

    public static void setBool(Map<Integer, Boolean> bool2) {
        bool = bool2;
    }

    public static String getSelectedFont() {
        return selectedFont;
    }

    public static void setSelectedFont(String selectedFont2) {
        selectedFont = selectedFont2;
    }
}
