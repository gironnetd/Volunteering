package com.sc.fr.onelittleangel.bouddhisme.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class DataBaseHelper extends SQLiteOpenHelper {
    private Cursor cursorCourant;
    private final Context mContext;
    private SQLiteDatabase mDataBase;
    private static String DB_PATH = "";
    private static String DB_NAME = "onelittleangel.db";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.mContext = context;
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
        File dbFile = new File(DB_PATH);
        if (!dbFile.exists()) {
            try {
                // Ensure the directory exists
                File parentDir = dbFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = this.mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[2048];
        while (true) {
            int mLength = mInput.read(mBuffer);
            if (mLength > 0) {
                mOutput.write(mBuffer, 0, mLength);
            } else {
                mOutput.flush();
                mOutput.close();
                mInput.close();
                return;
            }
        }
    }

    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH;
        this.mDataBase = SQLiteDatabase.openDatabase(mPath, null, 268435456);
        return this.mDataBase != null;
    }

    public void deleteDatabase() {
        this.mContext.deleteDatabase(DB_NAME);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper, java.lang.AutoCloseable
    public synchronized void close() {
        if (this.mDataBase != null) {
            this.mDataBase.close();
        }
        super.close();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase arg0) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
