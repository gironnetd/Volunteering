package com.sc.fr.onelittleangel;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.sc.fr.onelittleangel.injector.Injector;
import com.sc.fr.onelittleangel.layers.dao.authors.AuthorsDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.pictures.PicturesDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.themes.ThemesDaoInterface;
import com.sc.fr.onelittleangel.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.fr.onelittleangel.layers.mvp.common.utils.Constants;
import com.sc.fr.onelittleangel.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.fr.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.fr.onelittleangel.transverse.eventbus.events.ConnectivityChangeEvent;
import com.sc.fr.onelittleangel.transverse.orms.realm.Migration;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Author;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Picture;
import com.sc.fr.onelittleangel.transverse.orms.realm.models.Theme;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;

public class OnelittleAngelApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    //private static final String TWITTER_KEY = "my9D54TO9C94ZqbpG0KRWQwPt";
    //private static final String TWITTER_SECRET = "Ht6SKvO1QByTP30qvnLSIeuJqSlppKittLVV88c9UUUQXmxc05";

  private static final String TAG = "Application";

  //public static int darkerRgb = 0;

  //private SharedPreferences settings = getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

  /**
   * The instance
   */
  public static OnelittleAngelApplication instance;

  public OnelittleAngelApplication() {
  }

  @Override
  public void onCreate() {
    super.onCreate();
//   if (LeakCanary.isInAnalyzerProcess(this)) {
//      // This process is dedicated to LeakCanary for heap analysis.
//      // You should not init your app in this process.
//      return;
//    }
//    LeakCanary.install(this);

    instance = this;
    EventBus.builder().throwSubscriberException(false).installDefaultEventBus();
    //manage connectivity state
    manageConnectivityState();

    connectivityChangedReceiever = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        manageConnectivityState();
      }
    };

    // Registers BroadcastReceiver to track network connection changes.
    IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    registerReceiver(connectivityChangedReceiever, filter);

    Realm.init(this);
  //    RealmConfiguration config = new RealmConfiguration.Builder(this).build();
  //  RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
    //  Realm.setDefaultConfiguration(config);
    File file = new File(getFilesDir() + "/default.realm");

    if (!file.exists())
      copyBundledRealmFile(this.getResources().openRawResource(R.raw.default0), "default.realm");

    RealmConfiguration config0 = new RealmConfiguration.Builder()
      .name("default.realm")
      //  .encryptionKey(key)
      .schemaVersion(3)
      .build();

    try {
      Realm.migrateRealm(config0, new Migration());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    Realm.setDefaultConfiguration(config0);

    SharedPreferences settings = getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    final SharedPreferences.Editor editor = settings.edit();

    if(settings.getString(Constants.MOVEMENTSLIST_SELECTED, "").equals("")) {
      String movementsSelected = "" ;
      for(int i = 0; i < getResources().getStringArray(R.array.movements).length; i++) {
        String s = getResources().getStringArray(R.array.movements)[i].replaceAll("&amp;","&") + ";";
        movementsSelected += s;
      }

      editor.putString(Constants.MOVEMENTSLIST_SELECTED, movementsSelected);
//editor.commit();
      editor.apply();
    }

    if (settings.getInt(CardViewNative.DARKERRGB, 0) == 0) {
      editor.putInt(CardViewNative.DARKERRGB, -16748392);
//editor.commit();
      editor.apply();

    }

    CardViewNative view = new CardViewNative(getBaseContext());
    view.setCardLayoutResourceID(R.layout.content_layout);
    view.setCardLayoutResourceID(R.layout.card_layout);

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
      .connectTimeout(5, TimeUnit.SECONDS)
      .readTimeout(5, TimeUnit.SECONDS)
      //. writeTimeout(10, TimeUnit.SECONDS)
      .build();

    AndroidNetworking.initialize(getApplicationContext(), okHttpClient);
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inPurgeable = true;
    AndroidNetworking.setBitmapDecodeOptions(options);
    AndroidNetworking.enableLogging();
    AndroidNetworking.setConnectionQualityChangeListener((currentConnectionQuality, currentBandwidth) -> Log.d(TAG, "onChange: currentConnectionQuality : " + currentConnectionQuality + " currentBandwidth : " + currentBandwidth));

  //  Log.d(TAG, "onChange: currentConnectionQuality : " + AndroidNetworking.getCurrentConnectionQuality() + " currentBandwidth : " + AndroidNetworking.getCurrentBandwidth());

    String DB_NAME = "onelittleangel.db";
    String DB_PATH = getDatabasePath(DB_NAME).getPath();

    File dbFile = new File(DB_PATH);

    if(dbFile.exists()) {

      SQLiteDatabase mDataBase = new DataBaseHelper(this).getReadableDatabase();
      Cursor cursorFavorites = mDataBase.rawQuery("SELECT id_favorite as _id,id_quote From favorite", null);

      cursorFavorites.moveToFirst();

      for (int i = 0; i < cursorFavorites.getCount(); i ++){

        OnelittleAngelApplication.instance.getServiceManager().getUpdateQuoteService().toggleQuoteIsFavoritesAsync(Integer.parseInt(cursorFavorites.getString(1)));

        cursorFavorites.moveToNext();
      }
      cursorFavorites.close();
      dbFile.delete();
    }

    Realm realm = Realm.getDefaultInstance();

    realm.beginTransaction();
    AuthorsDaoInterface authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
    Author roshiSuzuki = authorsDaoInterface.findAuthorByName("Roshi Suzuki");

    for(Picture picture : roshiSuzuki.getPictures()) {

      if (picture.getNameSmall().equals("Roshi Yamada")) picture.setNameSmall("Roshi Suzuki");
      realm.copyToRealmOrUpdate(picture);
    }

    PicturesDaoInterface picturesDaoInterface = Injector.getDaoManager().getPicturesDao();
    Picture roueTibetaine392 = picturesDaoInterface.findPictureByIdPicture(392);
    if (roueTibetaine392 != null) roueTibetaine392.deleteFromRealm();

    ThemesDaoInterface themesDaoInterface = Injector.getDaoManager().getThemesDao();
    Theme unexpectedTheme = themesDaoInterface.findThemeByIdTheme(74);

    if (unexpectedTheme != null) unexpectedTheme.deleteFromRealm();

    Theme personnelTheme = themesDaoInterface.findThemeByIdTheme(66);

    if (personnelTheme != null) {
      personnelTheme.getThemes().deleteAllFromRealm();
      personnelTheme.deleteFromRealm();
    }

    realm.commitTransaction();
    realm.close();

    try {
      String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
      Log.i(TAG, " : " + versionName);
      if(!versionName.equals(settings.getString(Constants.VERSION_NAME, ""))) {
        editor.putString(getResources().getString(R.string.authors),"");
        editor.apply();
        editor.putString(getResources().getString(R.string.movements),"");
        editor.apply();
        editor.putString(getResources().getString(R.string.themes),"");
        editor.apply();
        editor.putString(getResources().getString(R.string.books),"");
        editor.apply();
        editor.putString(Constants.VERSION_NAME,versionName);
        editor.apply();
        Log.i(TAG, "version_name changed : " + versionName);
      }
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
  //  Log.e("OnelittleAngelApplication", "onTerminate is called");
  //  SugarContext.terminate();
  }
  /******************************************************************************************/
  /** Managing ServiceManager **************************************************************************/
  /******************************************************************************************/
  /**
   * The service manager used to manage the services
   */
  private ServiceManagerInterface serviceManagerInterface;

  /**
   * To know if the service manager already exist
   */

  private boolean servcieManagerAlreadyExist = false;
  /**
   * @return the serviceManager
   */
  public final ServiceManagerInterface getServiceManager() {
  //  Log.d(TAG, "getServiceManager() called with: " + "");
    if (null == serviceManagerInterface) {
      serviceManagerInterface = Injector.getServiceManager(this);
      servcieManagerAlreadyExist = true;
    }
    return serviceManagerInterface;
  }

  /**
   * @return true if the ServiceManager is already instantiate
   */
  public final boolean serviceManagerAlreadyExist() {
    return servcieManagerAlreadyExist;
  }
  /******************************************************************************************/
  /** Managing destruction : the 1 second pattern**************************************************************************/
  /******************************************************************************************/
  //Listening for activities life cycle to trigger the serviceManager death
  /**
   * The AtomicBoolean to know if there is an active activity
   */
  private final AtomicInteger isActivityAlive=new AtomicInteger(0);
  /**
   * To be called by activities when they go in their onStop method
   */
  public void onStartActivity() {
  //  Log.e(TAG, "onStartActivity() called with: " + "");
    isActivityAlive.set(isActivityAlive.get()+1);
    // launch the Runnable in 2 seconds
    mServiceKillerHandler.postDelayed(mServiceKiller, 1000);
  }
  /**
   * To be called by activities when they go in their onResume method
   */
  public void onStopActivity() {
  //  Log.e(TAG, "onStopActivity() called with: " + "");
    isActivityAlive.set(isActivityAlive.get()-1);
    // launch the Runnable in 2 seconds
    mServiceKillerHandler.postDelayed(mServiceKiller, 1000);
  }
  //The 1 second pattern to kill activityManager in 1 second
  /** * The Runnable that will look if there are no activity alive and launch the serviceManager death*/
  private Runnable mServiceKiller;
  /** * The handler that manages the runnable */
  private final Handler mServiceKillerHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
  //    Log.e(TAG, "in the Handler isActivityAlive==" + isActivityAlive.get());
      if(isActivityAlive.get()==0) {
        //What you should do when application should die
        applicationShouldDie();
      }
    }

  };

  private void applicationShouldDie(){
  ///  Log.e(TAG,"applicationShouldDie is called");
    //first unregister broadcast
    if(connectivityChangedReceiever!=null) {
      unregisterReceiver(connectivityChangedReceiever);
      connectivityChangedReceiever = null;
    }
    //kill you serviceManager
    //call unbind and die
    killServiceManager();
    //Kill your Dao manager
    Injector.getDaoManager().releaseMemory();
    //Kill your DataCommunication
  //  Injector.getDataCommunication().releaseMemory();
    //die
  }

  /** * initialize the runnable */
  private void initializeServiceKiller() {
    mServiceKiller = () -> {
      //one second later still no activity alive, so kill ServiceManager
      mServiceKillerHandler.dispatchMessage(mServiceKillerHandler.obtainMessage());
    };

  }
  //Now the Killing method
	/*
	 * (non-Javadoc)
	 *
	 * @see android.app.Application#onLowMemory()
	 */
  @Override
  public void onLowMemory() {
    super.onLowMemory();
    killServiceManager();
  }

  /**
   * Kill the service manager and all the services managed by it
   */
  private void killServiceManager() {
  //  Log.e(TAG, "killServiceManager is called");
    if (null != serviceManagerInterface) {
      serviceManagerInterface.unbindAndDie();
      serviceManagerInterface =null;
      servcieManagerAlreadyExist=false;
    }
  }
  /***********************************************************
   *  Manage Connectivity
   **********************************************************/
  /***
   * BroadCastr Receiver to listen to connectivity changes
   */
  private BroadcastReceiver connectivityChangedReceiever;
  /**
   * To know if there is a connection (wifi or GPRS)
   */
  private boolean isConnected = false;
  /**
   * To know if the connection is Wifi
   */
  private boolean isWifi = false;
  /**
   * To know the GRPS connectivity
   */
  private int telephonyType = 0;

  /**
   * Manage the connectivity state of the device
   */
  public void manageConnectivityState() {
    Log.e(TAG, "manageConnectivityState() called with: " + "");
    // Here we are because we receive either the boot completed event
    // either the connection changed event
    // either the wifi state changed event
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
    if (null == networkInfo) {
      // This is the airplane mode
      isConnected = false;
      isWifi = false;
      telephonyType = 0;
    } else {
      switch (networkInfo.getType()) {
        case ConnectivityManager.TYPE_WIFI:
          isConnected = true;
          isWifi = true;
          break;
        case ConnectivityManager.TYPE_MOBILE:
          isConnected = true;
          telephonyType = telephonyManager.getNetworkType();
          // For information TelephonyType is one of the following
          // switch (telephonyManager.getNetworkType()) {
          // case TelephonyManager.NETWORK_TYPE_LTE:// 150Mb/s
          // case TelephonyManager.NETWORK_TYPE_HSDPA:// 42Mb/s
          // break;
          // case TelephonyManager.NETWORK_TYPE_EDGE:// 215kb/s
          // break;
          // case TelephonyManager.NETWORK_TYPE_GPRS:// 45kb/s
          // break;
          // default:
          // break;
          // }
          break;
        default:
          break;
      }
    }
    notifyConnectivityChanged();
    Log.e("MyApplication", "manageConnectivityState called and return isConnected=" + isConnected + ", isWifi="
            + isWifi + ", telephonyType=" + telephonyType);
  }


//  public void manageConnectivityState() {
//    //  Log.e(TAG, "manageConnectivityState() called with: " + "");
//    // Here we are because we receive either the boot completed event
//    // either the connection changed event
//    // either the wifi state changed event
//    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//
//    if (networkInfo == null || !networkInfo.isConnected()) {
//      isConnected = false;
//      isWifi = false;
//      telephonyType = 0;
//    } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//      isConnected = true;
//      isWifi = true;
//    } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
//      int networkType = networkInfo.getSubtype();
//    if (null == networkInfo) {
//      // This is the airplane mode
//      isConnected = false;
//      isWifi = false;
//      telephonyType = 0;
//    } else {
//      switch (networkInfo.getSubtype()) {
//
//        case TelephonyManager.NETWORK_TYPE_GPRS:
//        case TelephonyManager.NETWORK_TYPE_EDGE:
//        case TelephonyManager.NETWORK_TYPE_CDMA:
//        case TelephonyManager.NETWORK_TYPE_1xRTT:
//        case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
//          isConnected = true;
//          telephonyType = telephonyManager.getNetworkType();
//          break;
//        case TelephonyManager.NETWORK_TYPE_UMTS:
//        case TelephonyManager.NETWORK_TYPE_EVDO_0:
//        case TelephonyManager.NETWORK_TYPE_EVDO_A:
//        case TelephonyManager.NETWORK_TYPE_HSDPA:
//        case TelephonyManager.NETWORK_TYPE_HSUPA:
//        case TelephonyManager.NETWORK_TYPE_HSPA:
//        case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
//        case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
//        case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
//          isConnected = true;
//          telephonyType = telephonyManager.getNetworkType();
//          break;
//        case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
//          isConnected = true;
//          telephonyType = telephonyManager.getNetworkType();
//          break;
//        default:
//          break;
//      }
//    }
//  }
//    notifyConnectivityChanged();
//  //  Log.e(TAG, "manageConnectivityState called and return isConnected=" + isConnected + ", isWifi="
//  //    + isWifi + ", telephonyType=" + telephonyType);
//  }

  /**
   * This method is called when we switch from no connectivity to connected to the internet
   */
  private void notifyConnectivityChanged() {
  //  Log.d(TAG, "notifyConnectivityChanged() called with: " + "");
    // notify the listeners (if there is some because this method can be called even if no
    // activity alived)
    EventBus.getDefault().post(new ConnectivityChangeEvent(telephonyType,isConnected,isWifi));
  }
  /**
   * Return if the device is connected to internet
   *
   * @return the isConnected
   */
  public final boolean isConnected() {
    Log.i(TAG,"isConnected : "+isConnected);
    return isConnected;
  }

  /**
   * Return if the device is connected to internet using WIFI
   *
   * @return the isWifi
   */
  public final boolean isWifi() {
  //  Log.d(TAG, "isWifi() called with: " + "");
    return isWifi;
  }

  /**
   * Return the connectivity type
   * (NETWORK_TYPE_LTE,NETWORK_TYPE_HSDPA,NETWORK_TYPE_EDGE,NETWORK_TYPE_GPRS)
   * when the device is connected to internet using GPRS
   *
   * @return the telephonyType
   */
  public final int getTelephonyType() {
  //  Log.d(TAG, "getTelephonyType() called with: " + "");
    return telephonyType;
  }

  private String copyBundledRealmFile(InputStream inputStream, String outFileName) {

    try {
      File file = new File(this.getFilesDir(), outFileName);
      FileOutputStream outputStream = new FileOutputStream(file);
      byte[] buf = new byte[1024];
      int bytesRead;
      while ((bytesRead = inputStream.read(buf)) > 0) {
        outputStream.write(buf, 0, bytesRead);
      }
      outputStream.close();
      return file.getAbsolutePath();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
