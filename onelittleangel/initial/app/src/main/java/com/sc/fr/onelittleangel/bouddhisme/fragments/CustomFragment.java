package com.sc.fr.onelittleangel.bouddhisme.fragments;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sc.fr.onelittleangel.bouddhisme.R;
import com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity;
import com.sc.fr.onelittleangel.bouddhisme.entities.Courant;
import com.sc.fr.onelittleangel.bouddhisme.helpers.DataBaseHelper;
import com.sc.fr.onelittleangel.bouddhisme.helpers.TextJustification;

/* JADX INFO: loaded from: classes.dex */
public class CustomFragment extends Fragment implements Parcelable {
    private static Activity context;
    private static String courantSelected1;
    private static String courantSelected2;
    private static String courantSelected3;
    private static String data;
    private static String data2;
    static float density;
    private static String font;
    static Point size;
    private String author;
    private String authorDetail;
    private int authorId;
    private TextView authorName;
    private RelativeLayout authors;
    private TextView comma;
    private TextView comma1;
    private TextView comma2;
    private RelativeLayout courants;
    private Cursor cursorAuthorDetails1;
    private Cursor cursorCourantDetails1;
    private Cursor cursorCourantDetails2;
    private Cursor cursorCourantDetails3;
    private SQLiteDatabase db = null;
    private TextView details;
    private TextView detailsAuthor1;
    private TextView detailsAuthor2;
    private TextView detailsAuthor3;
    Display display;
    DisplayMetrics dm;
    private ImageView eye;
    private Typeface georgiab;
    int heightView;
    private ImageView image_sources;
    private RelativeLayout mainRelative;
    int newHeight;
    int orientation;
    private int quoteId;
    private String quotes;
    private RelativeLayout quotes_relative;
    private RelativeLayout renseignement;
    int renseignment_height;
    private String screensize;
    private TextView source;
    private String sources;
    ScrollView sv;
    private String title;
    ViewGroup v;
    private TextView view;
    private static int firstLetterSize = 0;
    private static int textSize = 0;
    private static int height = 0;

    public CustomFragment() {
    }

    public CustomFragment(Activity context2, int quoteId, String quotes, String sources, String author, int authorId, String authorDetail, String title, int firstLetterSize2, int textSize2, int height2, String font2, String screensize) {
        context = context2;
        this.quotes = quotes;
        this.quoteId = quoteId;
        this.sources = sources;
        this.author = author;
        this.authorId = authorId;
        this.authorDetail = authorDetail;
        this.title = title;
        this.screensize = screensize;
        firstLetterSize = firstLetterSize2;
        textSize = textSize2;
        height = height2;
        font = font2;
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            CustomFragment cf = (CustomFragment) savedInstanceState.getParcelable("fragment");
            this.quotes = cf.getQuotes();
            this.quoteId = cf.getQuoteId();
            this.sources = cf.getSources();
            this.author = cf.getAuthor();
            this.authorId = cf.getAuthorId();
            this.authorDetail = cf.getAuthorDetail();
            this.title = cf.getTitle();
            this.screensize = cf.getScreensize();
            firstLetterSize = getFirstLetterSize();
            textSize = getTextSize();
            height = getHeight();
            font = getFont();
            courantSelected1 = savedInstanceState.getString("courantSelected1");
            courantSelected2 = savedInstanceState.getString("courantSelected2");
            courantSelected3 = savedInstanceState.getString("courantSelected3");
        }
        this.v = (ViewGroup) inflater.inflate(R.layout.fragment_portrait_page, container, false);
        this.georgiab = Typeface.createFromAsset(getActivity().getAssets(), "fonts/" + font + ".ttf");
        this.authorName = (TextView) this.v.findViewById(R.id.author);
        this.authorName.setTypeface(this.georgiab);
        this.authorName.setText(String.valueOf(this.author) + "   ");
        TextView comma3 = (TextView) this.v.findViewById(R.id.comma3);
        TextView comma4 = (TextView) this.v.findViewById(R.id.comma4);
        comma3.setText("( ");
        comma4.setText(" )");
        this.details = (TextView) this.v.findViewById(R.id.details);
        this.details.setText("?");
        this.details.setTypeface(this.georgiab);
        this.details.setTextColor(getResources().getColorStateList(R.color.blue));
        this.details.setOnClickListener(new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.fragments.CustomFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                TextView tv = new TextView(CustomFragment.this.getActivity());
                tv.setText(CustomFragment.this.author);
                tv.setTextColor(-1);
                tv.setGravity(1);
                TextView t = new TextView(CustomFragment.this.getActivity());
                t.setText(CustomFragment.this.authorDetail);
                t.setTextColor(-1);
                t.setTextSize(25.0f);
                AlertDialog.Builder adb = new AlertDialog.Builder(CustomFragment.this.getActivity());
                adb.setCustomTitle(tv);
                adb.setView(t);
                adb.setNeutralButton("Ok", (DialogInterface.OnClickListener) null);
                AlertDialog dialog = adb.create();
                dialog.show();
                Button bn = dialog.getButton(-3);
                bn.setHeight(40);
                bn.setTextSize(20.0f);
            }
        });
        this.detailsAuthor1 = (TextView) this.v.findViewById(R.id.details_author1);
        this.detailsAuthor1.setTypeface(this.georgiab);
        this.detailsAuthor2 = (TextView) this.v.findViewById(R.id.details_author2);
        this.detailsAuthor2.setTypeface(this.georgiab);
        this.detailsAuthor3 = (TextView) this.v.findViewById(R.id.details_author3);
        this.detailsAuthor3.setTypeface(this.georgiab);
        this.comma1 = (TextView) this.v.findViewById(R.id.comma1);
        this.comma1.setTypeface(this.georgiab);
        this.comma2 = (TextView) this.v.findViewById(R.id.comma2);
        this.comma2.setTypeface(this.georgiab);
        this.source = (TextView) this.v.findViewById(R.id.source);
        this.source.setTypeface(this.georgiab);
        this.source.setText(this.sources);
        this.quotes_relative = (RelativeLayout) this.v.findViewById(R.id.quotes_relative);
        this.view = (TextView) this.v.findViewById(R.id.quote_text);
        this.view.setTextSize(textSize);
        this.view.setTypeface(this.georgiab);
        this.eye = (ImageView) this.v.findViewById(R.id.eye);
        this.image_sources = (ImageView) this.v.findViewById(R.id.image_sources);
        this.renseignement = (RelativeLayout) this.v.findViewById(R.id.renseignement);
        this.renseignement.setBackgroundColor(0);
        this.mainRelative = (RelativeLayout) this.v.findViewById(R.id.main_relative);
        this.authors = (RelativeLayout) this.v.findViewById(R.id.authors);
        this.courants = (RelativeLayout) this.v.findViewById(R.id.courants);
        this.db = new DataBaseHelper(getActivity()).getReadableDatabase();
        this.orientation = getResources().getConfiguration().orientation;
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        size = new Point();
        this.dm = new DisplayMetrics();
        display.getMetrics(this.dm);
        density = this.dm.density;
        display.getSize(size);
        this.quotes_relative.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.fragments.CustomFragment.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                CustomFragment.this.heightView = CustomFragment.this.quotes_relative.getMeasuredHeight();
                CustomFragment.this.renseignment_height = CustomFragment.this.authors.getMeasuredHeight() + CustomFragment.this.courants.getMeasuredHeight() + CustomFragment.this.source.getMeasuredHeight();
                if (CustomFragment.this.heightView + CustomFragment.this.renseignment_height <= CustomFragment.this.mainRelative.getMeasuredHeight()) {
                    if (CustomFragment.this.heightView + CustomFragment.this.renseignment_height >= CustomFragment.this.mainRelative.getMeasuredHeight()) {
                        if (CustomFragment.this.heightView + CustomFragment.this.renseignment_height == CustomFragment.this.mainRelative.getMeasuredHeight()) {
                            CustomFragment.this.quotes_relative.setLayoutParams(new RelativeLayout.LayoutParams(-1, CustomFragment.height));
                            TextJustification.justify(CustomFragment.this.view, CustomFragment.this.quotes_relative.getWidth());
                            if (Build.VERSION.SDK_INT < 16) {
                                CustomFragment.this.quotes_relative.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            } else {
                                CustomFragment.this.quotes_relative.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        }
                    } else {
                        CustomFragment.this.newHeight = CustomFragment.this.mainRelative.getMeasuredHeight() - CustomFragment.this.renseignment_height;
                        CustomFragment.this.quotes_relative.setLayoutParams(new RelativeLayout.LayoutParams(-1, CustomFragment.this.newHeight));
                        CustomFragment.height = CustomFragment.this.newHeight;
                    }
                } else {
                    CustomFragment.this.newHeight = CustomFragment.this.heightView - CustomFragment.this.renseignment_height;
                    CustomFragment.this.quotes_relative.setLayoutParams(new RelativeLayout.LayoutParams(-1, CustomFragment.this.newHeight));
                    CustomFragment.height = CustomFragment.this.newHeight;
                }
                CustomFragment.this.view.setText(CustomFragment.this.quotes);
                TextJustification.justify(CustomFragment.this.view, CustomFragment.this.quotes_relative.getWidth());
            }
        });
        initAuthorDetails(this.author);
        this.cursorAuthorDetails1.close();
        this.cursorCourantDetails1.close();
        this.db.close();
        return this.v;
    }

    public void onEye(View v) {
        TextView tv = new TextView(getActivity());
        tv.setText(this.author);
        tv.setTextColor(-1);
        tv.setGravity(1);
        TextView t = new TextView(getActivity());
        t.setText(this.authorDetail);
        t.setTextColor(-1);
        t.setTextSize(25.0f);
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        adb.setCustomTitle(tv);
        adb.setView(t);
        adb.setNeutralButton("Ok", (DialogInterface.OnClickListener) null);
        AlertDialog dialog = adb.create();
        dialog.show();
        Button bn = dialog.getButton(-3);
        bn.setHeight(40);
        bn.setTextSize(20.0f);
    }

    public void onCourant(View view) {
        Intent i = new Intent(context, (Class<?>) QuotesTextActivity.class);
        String s = view.getTag().toString();
        String cs = null;
        if (s.equals("details_author1")) {
            cs = getCourantSelected1();
        } else if (s.equals("details_author2")) {
            cs = getCourantSelected2();
        } else if (s.equals("details_author3")) {
            cs = getCourantSelected3();
        }
        i.putExtra("type", "courant");
        i.putExtra("courant", new Courant(cs));
        i.putExtra("total", "yes");
        startActivity(i);
        context.finish();
    }

    public void onAuthor(View view) {
        Intent i = new Intent(context, (Class<?>) QuotesTextActivity.class);
        Courant courant = new Courant(this.author);
        i.putExtra("type", "author");
        i.putExtra("author", this.author);
        i.putExtra("book", this.authorId);
        i.putExtra("courant", courant);
        i.putExtra("total", "no");
        startActivity(i);
        context.finish();
    }

    private void initAuthorDetails(String author) {
        this.detailsAuthor1.setVisibility(4);
        this.detailsAuthor2.setVisibility(4);
        this.detailsAuthor3.setVisibility(4);
        this.comma1.setVisibility(4);
        this.comma2.setVisibility(4);
        this.cursorAuthorDetails1 = this.db.rawQuery("SELECT id_author as _id, name, surname,details,id_courant From author where id_author =" + this.authorId, null);
        this.cursorAuthorDetails1.moveToFirst();
        this.cursorCourantDetails1 = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent From courant where id_courant =" + this.cursorAuthorDetails1.getString(4), null);
        this.cursorCourantDetails1.moveToFirst();
        courantSelected1 = this.cursorCourantDetails1.getString(1);
        this.detailsAuthor1.setVisibility(0);
        this.detailsAuthor1.setText(this.cursorCourantDetails1.getString(1));
        if (this.detailsAuthor1.getText().equals(this.title)) {
            this.detailsAuthor1.setClickable(false);
            this.detailsAuthor1.setTextColor(getResources().getColorStateList(R.color.black));
        }
        if (!this.cursorCourantDetails1.getString(2).equals("0")) {
            String idParent = this.cursorCourantDetails1.getString(2);
            this.cursorCourantDetails2 = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent From courant where id_courant =" + idParent, null);
            this.cursorCourantDetails2.moveToFirst();
            courantSelected1 = this.cursorCourantDetails2.getString(1);
            courantSelected2 = this.cursorCourantDetails1.getString(1);
            this.detailsAuthor1.setVisibility(0);
            this.detailsAuthor1.setText(this.cursorCourantDetails2.getString(1));
            if (this.detailsAuthor1.getText().equals(this.title)) {
                this.detailsAuthor1.setClickable(false);
                this.detailsAuthor1.setTextColor(getResources().getColorStateList(R.color.black));
            }
            this.detailsAuthor2.setVisibility(0);
            this.detailsAuthor2.setText(this.cursorCourantDetails1.getString(1));
            if (this.detailsAuthor2.getText().equals(this.title)) {
                this.detailsAuthor2.setClickable(false);
                this.detailsAuthor2.setTextColor(getResources().getColorStateList(R.color.black));
            }
            this.comma1.setVisibility(0);
            if (!this.cursorCourantDetails2.getString(2).equals("0")) {
                String idParent1 = this.cursorCourantDetails2.getString(2);
                this.cursorCourantDetails3 = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent From courant where id_courant =" + idParent1, null);
                this.cursorCourantDetails3.moveToFirst();
                courantSelected1 = this.cursorCourantDetails3.getString(1);
                courantSelected2 = this.cursorCourantDetails2.getString(1);
                courantSelected3 = this.cursorCourantDetails1.getString(1);
                this.detailsAuthor1.setVisibility(0);
                this.detailsAuthor1.setText(this.cursorCourantDetails3.getString(1));
                if (this.detailsAuthor1.getText().equals(this.title)) {
                    this.detailsAuthor1.setClickable(false);
                    this.detailsAuthor1.setTextColor(getResources().getColorStateList(R.color.black));
                }
                this.detailsAuthor2.setVisibility(0);
                this.detailsAuthor2.setText(this.cursorCourantDetails2.getString(1));
                if (this.detailsAuthor2.getText().equals(this.title)) {
                    this.detailsAuthor2.setClickable(false);
                    this.detailsAuthor2.setTextColor(getResources().getColorStateList(R.color.black));
                }
                this.detailsAuthor3.setVisibility(0);
                this.detailsAuthor3.setText(this.cursorCourantDetails1.getString(1));
                if (this.detailsAuthor3.getText().equals(this.title)) {
                    this.detailsAuthor3.setClickable(false);
                    this.detailsAuthor3.setTextColor(getResources().getColorStateList(R.color.black));
                }
                this.comma2.setVisibility(0);
                this.cursorCourantDetails3.close();
            }
            this.cursorCourantDetails2.close();
        }
        if (!this.detailsAuthor1.getText().equals(this.title)) {
            this.detailsAuthor1.setTextColor(getResources().getColorStateList(R.color.blue));
            this.detailsAuthor1.setClickable(true);
            this.detailsAuthor1.setTextColor(getResources().getColorStateList(R.drawable.row_color_underline_selected));
        }
        if (!this.detailsAuthor2.getText().equals(this.title)) {
            this.detailsAuthor2.setTextColor(getResources().getColorStateList(R.color.blue));
            this.detailsAuthor2.setClickable(true);
            this.detailsAuthor2.setTextColor(getResources().getColorStateList(R.drawable.row_color_underline_selected));
        }
        if (!this.detailsAuthor3.getText().equals(this.title)) {
            this.detailsAuthor3.setTextColor(getResources().getColorStateList(R.color.blue));
            this.detailsAuthor3.setClickable(true);
            this.detailsAuthor3.setTextColor(getResources().getColorStateList(R.drawable.row_color_underline_selected));
        }
        if (!this.authorName.getText().equals(this.title)) {
            this.authorName.setTextColor(getResources().getColorStateList(R.drawable.row_color_underline_selected));
            this.authorName.setClickable(true);
        }
    }

    public String getHtmlData(Context context2, String data3, String data22, int firstLetterSize2, int textSize2, int height2, String font2) {
        data = data3;
        data2 = data22;
        firstLetterSize = firstLetterSize2;
        textSize = textSize2;
        height = height2;
        font = font2;
        String str = "<head><style>@font-face {font-family: '" + font2 + "';src: url('file://" + context2.getFilesDir().getAbsolutePath() + "/" + font2 + ".ttf');}html,body {margin: 0;padding: 0;width: 100%;height:98%;}html {display:table;}body {font-family: '" + font2 + "';font-weight: bold;color : black;display:table-cell;vertical-align: middle;text-align: justify;}</style></head>";
        String htmlData = "<html><body style=\"vertical-align:middle;display:table;margin-top:10px;text-align:justify;\"><div style=\"text-align:justify;vertical-align:middle;display:table-cell;\"><span style=\"vertical-align:middle;display:inline;line-height:" + textSize2 + "px;margin-top:10px;font-size:" + firstLetterSize2 + "px;text-align:bottom;\">" + data3 + "</span> <span style=\"text-align:justify;font-family:" + font2 + ";color:black;font-weight: bold;font-size:" + textSize2 + "px;vertical-align:middle;display:inline;\">" + data22 + " </span></div></body></html>";
        return htmlData;
    }

    public TextView getAuthorName() {
        return this.authorName;
    }

    public String getCourantSelected1() {
        return courantSelected1;
    }

    public String getCourantSelected2() {
        return courantSelected2;
    }

    public String getCourantSelected3() {
        return courantSelected3;
    }

    public int getAuthorId() {
        return this.authorId;
    }

    public int getQuoteId() {
        return this.quoteId;
    }

    public String getQuotes() {
        return this.quotes;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getAuthorDetail() {
        return this.authorDetail;
    }

    public String getSources() {
        return this.sources;
    }

    public TextView getWebView() {
        return this.view;
    }

    public RelativeLayout getRenseignement() {
        return this.renseignement;
    }

    public RelativeLayout getAuthors() {
        return this.authors;
    }

    public RelativeLayout getCourants() {
        return this.courants;
    }

    public static int getFirstLetterSize() {
        return firstLetterSize;
    }

    public static int getTextSize() {
        return textSize;
    }

    public static int getHeight() {
        return height;
    }

    public static String getFont() {
        return font;
    }

    public String getScreensize() {
        return this.screensize;
    }

    public String getTitle() {
        return this.title;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override // android.support.v4.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("fragment", new CustomFragment(getActivity(), this.quoteId, this.quotes, this.sources, this.author, this.authorId, this.authorDetail, this.title, firstLetterSize, textSize, height, font, this.screensize));
        outState.putString("courantSelected1", courantSelected1);
        outState.putString("courantSelected2", courantSelected2);
        outState.putString("courantSelected3", courantSelected3);
    }
}
