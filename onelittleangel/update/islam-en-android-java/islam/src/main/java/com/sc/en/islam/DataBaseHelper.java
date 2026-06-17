package com.sc.en.islam;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


class DataBaseHelper extends SQLiteOpenHelper
{

private static String DB_PATH = "";
private static final String DB_NAME = "onelittleangel.db";


private SQLiteDatabase mDataBase;
private final Context mContext;

private Cursor cursorCourant ;

public DataBaseHelper(Context context)
{

   super(context,DB_NAME, null, 1);// 1? its Database Version

   DB_PATH = context.getDatabasePath(DB_NAME).getPath();
   this.mContext = context;

}




   private void copyDataBase() throws IOException
   {
       InputStream mInput = mContext.getAssets().open(DB_NAME);
      String outFileName = DB_PATH ;
       OutputStream mOutput = new FileOutputStream(outFileName);
       byte[] mBuffer = new byte[2048];
       int mLength;
       while ((mLength = mInput.read(mBuffer))>0)
       {
           mOutput.write(mBuffer, 0, mLength);
       }
       mOutput.flush();
       mOutput.close();
       mInput.close();
   }


   public boolean openDataBase() throws SQLException
   {
       String mPath = DB_PATH ;
       mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

       return mDataBase != null;
   }

   public void deleteDatabase(){
     this.mContext.deleteDatabase(DB_NAME);

   }


   @Override
   public synchronized void close()
   {
       if(mDataBase != null)
           mDataBase.close();
       super.close();
   }

 @Override
 public void onCreate(SQLiteDatabase arg0) {

 }

 @Override
 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

 }

}

 
