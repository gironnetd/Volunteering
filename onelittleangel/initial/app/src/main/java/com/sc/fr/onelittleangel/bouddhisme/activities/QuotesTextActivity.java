package com.sc.fr.onelittleangel.bouddhisme.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
//import com.google.analytics.tracking.android.EasyTracker;
import com.sc.fr.onelittleangel.bouddhisme.entities.Category;
import com.sc.fr.onelittleangel.bouddhisme.entities.Courant;
import com.sc.fr.onelittleangel.bouddhisme.adapter.SingleListAdapter;
import com.sc.fr.onelittleangel.bouddhisme.fragments.CustomFragment;
import com.sc.fr.onelittleangel.bouddhisme.fragments.MyPageAdapterFragment;
import com.sc.fr.onelittleangel.bouddhisme.fragments.MyViewPager;
import com.sc.fr.onelittleangel.bouddhisme.helpers.DataBaseHelper;
import com.sc.fr.onelittleangel.bouddhisme.helpers.FontsHelper;
import com.sc.fr.onelittleangel.bouddhisme.helpers.ToolsHelper;
import com.sc.fr.onelittleangel.bouddhisme.views.ActionBarCustom;
import com.sc.fr.onelittleangel.bouddhisme.views.MenuLandscapeBarCustom;
import com.sc.fr.onelittleangel.bouddhisme.views.MenuPortraitBarCustom;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class QuotesTextActivity extends FragmentActivity {
    static String fromActivity;
    static String screensize;
    private static int selected;
    static MyViewPager viewPager;
    RelativeLayout action;
    ActionBarCustom actionBar;
    SingleListAdapter adapter;
    Animation animZoomIn;
    Animation animZoomOut;
    private String authorSelected;
    int bookId;
    private ImageView buttonBack;
    private ImageView buttonBackLandscape;
    private ImageView buttonForward;
    private ImageView buttonForwardLandscape;
    private Category category;
    private Courant courant;
    private String courantId;
    private Courant courantIntent;
    private Cursor cursorAuthor;
    private Cursor cursorAuthor2;
    private Cursor cursorAuthor3;
    private Cursor cursorBook;
    private Cursor cursorCategory;
    private Cursor cursorCategory1;
    private Cursor cursorCourant;
    private Cursor cursorCourant2;
    private Cursor cursorCourant3;
    private Cursor cursorFavorites;
    private Cursor cursorFont;
    private Cursor cursorQuoCategory;
    private Cursor cursorQuote;
    private Cursor cursorQuote2;
    private Cursor cursorQuote3;
    private EditText edit;
    ImageView email;
    private Typeface face;
    ImageView favorites;
    FontsHelper fh;
    private ImageView go;
    private ImageView help;
    ListView list;
    ImageView logo;
    private ViewPager mPager;
    private MyPageAdapterFragment mPagerAdapterFragment;
    ImageView menu;
    MenuLandscapeBarCustom menubarlandscape;
    MenuPortraitBarCustom menubarportrait;
    private TextView number;
    View.OnClickListener oc;
    private TextView on;
    int orientation;
    ImageView phone;
    ImageView r;
    private String selectedFont;
    private TextView text;
    private ImageView textFont;
    ImageView textLess;
    ImageView textMore;
    private TextView title;
    private TextView titleActionBar;
    ImageView tools;
    ToolsHelper toolsHelper;
    TextToSpeech tts;
    static int firstLetterSize = 0;
    static int textSize = 0;
    static int firstLetterSizeMinimum = 0;
    static int textSizeMinimum = 0;
    static int firstLetterSizeMaximum = 0;
    static int textSizeMaximum = 0;
    static int heightPortrait = 0;
    static int heightLandscape = 0;
    static boolean option = false;
    private static Map<Integer, String> listCourants = new LinkedHashMap();
    private static Map<Integer, Boolean> t = new LinkedHashMap();
    static boolean restore = false;
    private static Map<Integer, String> fontList = new LinkedHashMap();
    private static Map<Integer, Boolean> fontBool = new LinkedHashMap();
    private SQLiteDatabase db = null;
    private SQLiteDatabase dbw = null;
    int position = 0;
    int totalQuotes = 0;
    int totalAuthors = 0;
    private ArrayList<String> quotes = new ArrayList<>();
    private ArrayList<String> quotesId = new ArrayList<>();
    private ArrayList<String> quotesIdFavorites = new ArrayList<>();
    private ArrayList<String> sources = new ArrayList<>();
    private ArrayList<String> authors = new ArrayList<>();
    private ArrayList<String> authorsDetails = new ArrayList<>();
    private ArrayList<Integer> authorsId = new ArrayList<>();
    int compteur = 0;
    int oldPos = 0;
    private ArrayList<CustomFragment> fragments = new ArrayList<>();
    FragmentTransaction fragMentTra = null;
    MainActivity ma = new MainActivity();
    ArrayList<String> alf = new ArrayList<>();

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle arg0) {
        this.alf.add("Almendra");
        this.alf.add("Cinzel");
        this.alf.add("Eagle Lake");
        this.alf.add("Fredericka the Great");
        this.alf.add("Gabriela");
        this.alf.add("Germania One");
        this.alf.add("IM_Fell_Double_Pica_SC");
        this.alf.add("IM_Fell_Double_Pica");
        this.alf.add("IM_Fell_DW_Pica_SC");
        this.alf.add("IM_Fell_DW_Pica");
        this.alf.add("IM_Fell_English_SC");
        this.alf.add("IM_Fell_English");
        this.alf.add("IM_Fell_Great_Primer_SC");
        this.alf.add("IM_Fell_Great_Primer");
        this.alf.add("Italianno");
        this.alf.add("Jim Nightshade");
        this.alf.add("Junge");
        this.alf.add("Lovers Quarrel");
        this.alf.add("Macondo");
        this.alf.add("Macondo Swash Caps");
        this.alf.add("MateSC");
        this.alf.add("Metal Mania");
        this.alf.add("mtcorsva");
        this.alf.add("Nova Oval");
        this.alf.add("Parisienne");
        this.alf.add("Quintessential");
        this.alf.add("Rosarivo");
        this.alf.add("Sorts Mill Goudy");
        this.alf.add("UnifrakturCook");
        this.alf.add("UnifrakturMaguntia");
        super.onCreate(arg0);
        setContentView(R.layout.quote);
        this.orientation = getResources().getConfiguration().orientation;
        this.mPager = (ViewPager) findViewById(R.id.pager);
        if (arg0 != null) {
            this.fragments = arg0.getParcelableArrayList("fragments");
            this.mPagerAdapterFragment = new MyPageAdapterFragment(getSupportFragmentManager(), this.fragments);
            this.mPager.setAdapter(this.mPagerAdapterFragment);
        }
        this.tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.1
            @Override // android.speech.tts.TextToSpeech.OnInitListener
            public void onInit(int status) {
                if (status != -1) {
                    QuotesTextActivity.this.tts.setLanguage(Locale.FRENCH);
                }
            }
        });
        this.face = Typeface.createFromAsset(getAssets(), "fonts/mtcorsva.ttf");
        this.actionBar = (ActionBarCustom) findViewById(R.id.actionbar);
        this.menubarportrait = (MenuPortraitBarCustom) findViewById(R.id.menubarportrait);
        this.menubarlandscape = (MenuLandscapeBarCustom) findViewById(R.id.menubarlandscape);
        this.actionBar.setBackgroundResource(R.drawable.actionbar_background);
        this.titleActionBar = (TextView) this.actionBar.findViewById(R.id.title_actionBar);
        this.title = (TextView) this.actionBar.findViewById(R.id.title);
        this.menu = (ImageView) this.actionBar.findViewById(R.id.ic_menu);
        this.text = (TextView) this.actionBar.findViewById(R.id.title);
        this.text.setTypeface(this.face);
        this.logo = (ImageView) this.actionBar.findViewById(R.id.ola_logo);
        this.email = (ImageView) this.actionBar.findViewById(R.id.email);
        this.favorites = (ImageView) this.actionBar.findViewById(R.id.favorites);
        if (this.orientation == 1) {
            this.menubarportrait.setVisibility(View.VISIBLE);
            this.menubarlandscape.setVisibility(View.GONE);
            this.title.setVisibility(View.VISIBLE);
            this.titleActionBar.setVisibility(View.GONE);
            this.r = (ImageView) this.menubarportrait.findViewById(R.id.back);
            this.help = (ImageView) this.menubarportrait.findViewById(R.id.help);
            this.buttonBack = (ImageView) this.menubarportrait.findViewById(R.id.button_back);
            this.buttonForward = (ImageView) this.menubarportrait.findViewById(R.id.button_forward);
            this.edit = (EditText) this.menubarportrait.findViewById(R.id.edit);
            this.on = (TextView) this.menubarportrait.findViewById(R.id.on);
            this.go = (ImageView) this.menubarportrait.findViewById(R.id.go);
            this.number = (TextView) this.menubarportrait.findViewById(R.id.number);
        } else if (this.orientation == 2) {
            this.menubarportrait.setVisibility(View.GONE);
            this.menubarlandscape.setVisibility(View.VISIBLE);
            this.title.setVisibility(View.GONE);
            this.titleActionBar.setVisibility(View.VISIBLE);
            this.r = (ImageView) this.menubarlandscape.findViewById(R.id.back);
            this.help = (ImageView) this.menubarlandscape.findViewById(R.id.help);
            this.textLess = (ImageView) this.menubarlandscape.findViewById(R.id.textsizeless);
            this.textMore = (ImageView) this.menubarlandscape.findViewById(R.id.textsizemore);
            this.textFont = (ImageView) this.menubarlandscape.findViewById(R.id.textfont);
            this.buttonBackLandscape = (ImageView) this.menubarlandscape.findViewById(R.id.button_back_landscape);
            this.buttonForwardLandscape = (ImageView) this.menubarlandscape.findViewById(R.id.button_forward_landscape);
            this.phone = (ImageView) this.menubarlandscape.findViewById(R.id.microphone);
            this.edit = (EditText) this.menubarlandscape.findViewById(R.id.edit);
            this.on = (TextView) this.menubarlandscape.findViewById(R.id.on);
            this.go = (ImageView) this.menubarlandscape.findViewById(R.id.go);
            this.number = (TextView) this.menubarlandscape.findViewById(R.id.number);
        }
        this.oc = new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(final View v) {
                if (v.getTag().equals("help")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_help_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_help_zoomout);
                    QuotesTextActivity.this.help.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.help.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.1
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextActivity.this.onHelp();
                        }
                    });
                }
                if (v.getTag().equals("textfont")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_font_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_font_zoomout);
                    QuotesTextActivity.this.textFont.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.textFont.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.2
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextActivity.this.onFonts();
                        }
                    });
                }
                if (v.getTag().equals("logo")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_logo_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_logo_zoomout);
                    QuotesTextActivity.this.logo.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.logo.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.3
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            MainActivity.setFromActivity("");
                            QuotesTextActivity.this.startActivity(new Intent(QuotesTextActivity.this, (Class<?>) MainActivity.class));
                            QuotesTextActivity.this.finish();
                        }
                    });
                }
                if (v.getTag().equals("email")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_email_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_email_zoomout);
                    QuotesTextActivity.this.email.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.email.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.4
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                        }
                    });
                }
                if (v.getTag().equals("favorites_add")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_favorites_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_favorites_zoomout);
                    QuotesTextActivity.this.favorites.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.favorites.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.5
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextActivity.this.addFavorite();
                        }
                    });
                }
                if (v.getTag().equals("favorites_delete")) {
                    TextView t2 = new TextView(QuotesTextActivity.this);
                    t2.setText("Cette citation est dÈj‡ dans vos favoris ");
                    t2.setTextColor(-1);
                    t2.setTextSize(25.0f);
                    AlertDialog.Builder adb = new AlertDialog.Builder(QuotesTextActivity.this);
                    adb.setView(t2);
                    adb.setNeutralButton("Fermer", (DialogInterface.OnClickListener) null);
                    AlertDialog dialog = adb.create();
                    dialog.show();
                    Button bn = dialog.getButton(-3);
                    bn.setHeight(40);
                    bn.setTextSize(20.0f);
                }
                if (v.getTag().equals("ic_menu")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_menu_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_menu_zoomout);
                    QuotesTextActivity.this.menu.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.menu.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.6
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextActivity.this.onMenu();
                        }
                    });
                }
                if (v.getTag().equals("go")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_go_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_go_zoomout);
                    QuotesTextActivity.this.go.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.go.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.7
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextActivity.this.onSearch(v);
                        }
                    });
                }
                if (v.getTag().equals("less")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_less_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_less_zoomout);
                    QuotesTextActivity.this.textLess.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.textLess.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.8
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextActivity.this.onSize(v);
                        }
                    });
                }
                if (v.getTag().equals("more")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_more_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_more_zoomout);
                    QuotesTextActivity.this.textMore.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.textMore.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.9
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextActivity.this.onSize(v);
                        }
                    });
                }
                if (v.getTag().equals("back")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_back_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_back_zoomout);
                    QuotesTextActivity.this.r.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.r.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.10
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            MainActivity.setFromActivity(QuotesTextActivity.fromActivity);
                            QuotesTextActivity.this.startActivity(new Intent(QuotesTextActivity.this, (Class<?>) MainActivity.class));
                            QuotesTextActivity.this.finish();
                        }
                    });
                }
                if (v.getTag().equals("button_back")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_button_back_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_button_back_zoomout);
                    QuotesTextActivity.this.buttonBack.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.buttonBack.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.11
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextActivity.this.onPrevious();
                        }
                    });
                }
                if (v.getTag().equals("button_back_landscape")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_button_back_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_button_back_zoomout);
                    QuotesTextActivity.this.buttonBackLandscape.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.buttonBackLandscape.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.12
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextActivity.this.onPrevious();
                        }
                    });
                }
                if (v.getTag().equals("button_forward")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_button_forward_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_button_forward_zoomout);
                    QuotesTextActivity.this.buttonForward.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.buttonForward.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.13
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextActivity.this.onNext();
                        }
                    });
                }
                if (v.getTag().equals("button_forward_landscape")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_button_forward_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_button_forward_zoomout);
                    QuotesTextActivity.this.buttonForwardLandscape.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.buttonForwardLandscape.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.14
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextActivity.this.onNext();
                        }
                    });
                }
                if (v.getTag().equals("microphone")) {
                    QuotesTextActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_phone_zoomin);
                    QuotesTextActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextActivity.this.getApplicationContext(), R.anim.anim_phone_zoomout);
                    QuotesTextActivity.this.phone.startAnimation(QuotesTextActivity.this.animZoomIn);
                    QuotesTextActivity.this.phone.startAnimation(QuotesTextActivity.this.animZoomOut);
                    QuotesTextActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.2.15
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextActivity.this.onPhone();
                        }
                    });
                }
            }
        };
        this.logo.setOnClickListener(this.oc);
        this.email.setOnClickListener(this.oc);
        this.favorites.setOnClickListener(this.oc);
        this.menu.setOnClickListener(this.oc);
        if (this.orientation == 1) {
            this.r.setOnClickListener(this.oc);
            this.help.setOnClickListener(this.oc);
            this.buttonBack.setOnClickListener(this.oc);
            this.buttonForward.setOnClickListener(this.oc);
            this.go.setOnClickListener(this.oc);
        } else if (this.orientation == 2) {
            this.r.setOnClickListener(this.oc);
            this.help.setOnClickListener(this.oc);
            this.textLess.setOnClickListener(this.oc);
            this.textMore.setOnClickListener(this.oc);
            this.textFont.setOnClickListener(this.oc);
            this.buttonBackLandscape.setOnClickListener(this.oc);
            this.buttonForwardLandscape.setOnClickListener(this.oc);
            this.phone.setOnClickListener(this.oc);
            this.go.setOnClickListener(this.oc);
        }
        this.toolsHelper = new ToolsHelper(getApplicationContext());
        t = ToolsHelper.getCourants();
        listCourants = ToolsHelper.getList();
        this.db = new DataBaseHelper(this).getReadableDatabase();
        this.cursorFavorites = this.db.rawQuery("SELECT id_favorite as _id,id_quote From favorite", null);
        this.cursorFavorites.moveToFirst();
        if (this.cursorFavorites.getCount() != 0) {
            for (int i = 0; i < this.cursorFavorites.getCount(); i++) {
                this.quotesIdFavorites.add(this.cursorFavorites.getString(1));
                this.cursorFavorites.moveToNext();
            }
        }
        this.fh = new FontsHelper(this);
        fontList = FontsHelper.getFontList();
        fontBool = FontsHelper.getBool();
        this.selectedFont = FontsHelper.getSelectedFont();
        for (int i2 = 0; i2 < fontBool.size(); i2++) {
            if (fontBool.get(Integer.valueOf(i2)).booleanValue()) {
                selected = i2;
            }
        }
        this.compteur = 1;
        this.dbw = new DataBaseHelper(this).getWritableDatabase();
        this.cursorFont = this.dbw.rawQuery("SELECT id as _id,firstletter,textsize,heightPortrait,heightLandscape,screensize From font where id = 1", null);
        this.cursorFont.moveToFirst();
        firstLetterSize = Integer.parseInt(this.cursorFont.getString(1));
        textSize = Integer.parseInt(this.cursorFont.getString(2));
        heightPortrait = Integer.parseInt(this.cursorFont.getString(3));
        heightLandscape = Integer.parseInt(this.cursorFont.getString(4));
        screensize = this.cursorFont.getString(5);
        firstLetterSizeMinimum = firstLetterSize - 15;
        textSizeMinimum = textSize - 15;
        initAdapter();
        this.mPager = (ViewPager) findViewById(R.id.pager);
        this.fragments.clear();
        for (int j = 0; j < 3; j++) {
            for (int i3 = 0; i3 < this.quotes.size(); i3++) {
                this.fragments.add(new CustomFragment(this, Integer.parseInt(this.quotesId.get(i3)), this.quotes.get(i3), this.sources.get(i3), this.authors.get(i3), this.authorsId.get(i3).intValue(), this.authorsDetails.get(i3), String.valueOf(this.text.getText()), firstLetterSize, textSize, heightLandscape, this.selectedFont, screensize));
            }
        }
        this.mPagerAdapterFragment = new MyPageAdapterFragment(getSupportFragmentManager(), this.fragments);
        this.mPager.setAdapter(this.mPagerAdapterFragment);
        if (this.oldPos != 0) {
            this.mPager.setCurrentItem(this.oldPos);
            if (this.quotesIdFavorites.contains(this.quotesId.get(this.oldPos))) {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                this.actionBar.getFavorites().setTag("favorites_delete");
            } else {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                this.actionBar.getFavorites().setClickable(true);
                this.actionBar.getFavorites().setTag("favorites_add");
            }
        } else {
            this.mPager.setCurrentItem(this.quotes.size());
            this.oldPos = this.quotes.size();
            if (this.quotesIdFavorites.contains(this.quotesId.get(0))) {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                this.actionBar.getFavorites().setTag("favorites_delete");
            } else {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                this.actionBar.getFavorites().setClickable(true);
                this.actionBar.getFavorites().setTag("favorites_add");
            }
        }
        this.mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.3
            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int arg02) {
                QuotesTextActivity.this.onWindowFocusChanged(true);
                if (arg02 < QuotesTextActivity.this.quotes.size()) {
                    QuotesTextActivity.this.number.setText(String.valueOf(arg02 + 1) + " / " + QuotesTextActivity.this.quotes.size());
                    if (QuotesTextActivity.this.quotesIdFavorites.contains(QuotesTextActivity.this.quotesId.get(arg02))) {
                        QuotesTextActivity.this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                        QuotesTextActivity.this.actionBar.getFavorites().setTag("favorites_delete");
                    } else {
                        QuotesTextActivity.this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                        QuotesTextActivity.this.actionBar.getFavorites().setTag("favorites_add");
                        QuotesTextActivity.this.actionBar.getFavorites().setClickable(true);
                    }
                } else if (QuotesTextActivity.this.quotes.size() - 1 >= arg02 || arg02 >= QuotesTextActivity.this.quotes.size() * 2) {
                    if (arg02 + 1 > QuotesTextActivity.this.quotes.size() * 2) {
                        QuotesTextActivity.this.number.setText(String.valueOf((arg02 + 1) - (QuotesTextActivity.this.quotes.size() * 2)) + " / " + QuotesTextActivity.this.quotes.size());
                        if (QuotesTextActivity.this.quotesIdFavorites.contains(QuotesTextActivity.this.quotesId.get(arg02 - (QuotesTextActivity.this.quotes.size() * 2)))) {
                            QuotesTextActivity.this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                            QuotesTextActivity.this.actionBar.getFavorites().setTag("favorites_delete");
                        } else {
                            QuotesTextActivity.this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                            QuotesTextActivity.this.actionBar.getFavorites().setTag("favorites_add");
                            QuotesTextActivity.this.actionBar.getFavorites().setClickable(true);
                        }
                    }
                } else {
                    QuotesTextActivity.this.number.setText(String.valueOf((arg02 + 1) - QuotesTextActivity.this.quotes.size()) + " / " + QuotesTextActivity.this.quotes.size());
                    if (QuotesTextActivity.this.quotesIdFavorites.contains(QuotesTextActivity.this.quotesId.get(arg02 - QuotesTextActivity.this.quotes.size()))) {
                        QuotesTextActivity.this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                        QuotesTextActivity.this.actionBar.getFavorites().setTag("favorites_delete");
                    } else {
                        QuotesTextActivity.this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                        QuotesTextActivity.this.actionBar.getFavorites().setTag("favorites_add");
                        QuotesTextActivity.this.actionBar.getFavorites().setClickable(true);
                    }
                }
                QuotesTextActivity.this.oldPos = arg02;
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int arg02, float arg1, int arg2) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("fragments", this.fragments);
    }

    public int initAuthorsAdapter(Courant courant, String total) {
        if (total.equals("no")) {
            if (this.bookId != -1 && this.bookId != -2) {
                this.cursorBook = this.db.rawQuery("SELECT id_author as _id, name, surname,details,id_courant From author where id_author =" + this.bookId, null);
            } else {
                this.cursorBook = this.db.rawQuery("SELECT id_author as _id, name, surname,details,id_courant From author where name ='" + courant.getCourant() + "'", null);
            }
            this.cursorBook.moveToFirst();
            this.actionBar.setTitle(this.cursorBook.getString(1));
            this.title.setText(this.cursorBook.getString(1));
            this.titleActionBar.setText(this.cursorBook.getString(1));
            this.cursorQuote = this.db.rawQuery("SELECT id_quote as _id,quote,book From quote where id_author =" + this.cursorBook.getString(0), null);
            if (this.cursorQuote.getCount() != 0) {
                this.cursorQuote.moveToFirst();
                for (int i = 0; i < this.cursorQuote.getCount(); i++) {
                    this.quotes.add(this.cursorQuote.getString(1));
                    this.quotesId.add(this.cursorQuote.getString(0));
                    this.sources.add(this.cursorQuote.getString(2));
                    this.authorsId.add(Integer.valueOf(this.cursorBook.getString(0)));
                    this.authors.add(this.cursorBook.getString(1));
                    this.authorsDetails.add(this.cursorBook.getString(3));
                    this.cursorQuote.moveToNext();
                }
            }
        } else if (total.equals("yes")) {
            this.actionBar.setTitle(courant.getCourant().toString());
            this.title.setText(courant.getCourant().toString());
            this.titleActionBar.setText(courant.getCourant().toString());
            this.cursorCourant = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent From courant where courant ='" + courant.getCourant() + "' ", null);
            this.cursorCourant.moveToFirst();
            this.cursorBook = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant From author where book=0 and id_courant =" + this.cursorCourant.getString(0), null);
            this.cursorBook.moveToFirst();
            if (this.cursorBook.getCount() != 0) {
                for (int i2 = 0; i2 < this.cursorBook.getCount(); i2++) {
                    this.cursorQuote = this.db.rawQuery("SELECT id_quote as _id,quote,book From quote where id_author =" + this.cursorBook.getString(0), null);
                    while (this.cursorQuote.moveToNext()) {
                        this.quotes.add(this.cursorQuote.getString(1));
                        this.quotesId.add(this.cursorQuote.getString(0));
                        this.sources.add(this.cursorQuote.getString(2));
                        this.authors.add(this.cursorBook.getString(1));
                        this.authorsId.add(Integer.valueOf(this.cursorBook.getString(0)));
                        this.authorsDetails.add(this.cursorBook.getString(4));
                    }
                    this.cursorBook.moveToNext();
                }
            }
        }
        this.authorSelected = this.authors.get(this.position);
        this.number.setText(String.valueOf(this.position + 1) + " / " + this.quotes.size());
        this.on.setText(" / " + this.quotes.size());
        return this.quotes.size();
    }

    public int initBooksAdapter(Courant courant, String total) {
        if (total.equals("no")) {
            this.cursorBook = this.db.rawQuery("SELECT id_author as _id, name, surname,details,id_courant From author where id_author = " + this.bookId, null);
            this.cursorBook.moveToFirst();
            this.actionBar.setTitle(this.cursorBook.getString(1));
            this.title.setText(this.cursorBook.getString(1));
            this.titleActionBar.setText(this.cursorBook.getString(1));
            this.cursorQuote = this.db.rawQuery("SELECT id_quote as _id,quote,book From quote where id_author =" + this.cursorBook.getString(0), null);
            if (this.cursorQuote.getCount() != 0) {
                while (this.cursorQuote.moveToNext()) {
                    this.quotes.add(this.cursorQuote.getString(1));
                    this.quotesId.add(this.cursorQuote.getString(0));
                    this.sources.add(this.cursorQuote.getString(2));
                    this.authors.add(this.cursorBook.getString(1));
                    this.authorsId.add(Integer.valueOf(this.cursorBook.getString(0)));
                    this.authorsDetails.add(this.cursorBook.getString(3));
                }
            }
        } else if (total.equals("yes")) {
            this.actionBar.setTitle(courant.getCourant().toString());
            this.title.setText(courant.getCourant().toString());
            this.titleActionBar.setText(courant.getCourant().toString());
            this.cursorCourant = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent From courant where courant ='" + courant.getCourant() + "' ", null);
            this.cursorCourant.moveToFirst();
            this.cursorBook = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant From author where book=1 and id_courant =" + this.cursorCourant.getString(0), null);
            this.cursorBook.moveToFirst();
            if (this.cursorBook.getCount() != 0) {
                for (int i = 0; i < this.cursorBook.getCount(); i++) {
                    this.cursorQuote = this.db.rawQuery("SELECT id_quote as _id,quote,book From quote where id_author =" + this.cursorBook.getString(0), null);
                    while (this.cursorQuote.moveToNext()) {
                        this.quotes.add(this.cursorQuote.getString(1));
                        this.quotesId.add(this.cursorQuote.getString(0));
                        this.sources.add(this.cursorQuote.getString(2));
                        this.authors.add(this.cursorBook.getString(1));
                        this.authorsId.add(Integer.valueOf(this.cursorBook.getString(0)));
                        this.authorsDetails.add(this.cursorBook.getString(4));
                    }
                    this.cursorBook.moveToNext();
                }
            }
        }
        this.authorSelected = this.authors.get(this.position);
        this.number.setText(String.valueOf(this.position + 1) + " / " + this.quotes.size());
        this.on.setText(" / " + this.quotes.size());
        return this.quotes.size();
    }

    public int initSubjectsAdapter(Courant courant, String total) {
        this.actionBar.setTitle(courant.getCourant().toString());
        this.title.setText(courant.getCourant().toString());
        this.titleActionBar.setText(courant.getCourant().toString());
        this.cursorCourant = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent From courant where courant ='" + courant.getCourant().toString() + "' ", null);
        this.cursorCourant.moveToFirst();
        if (total.equals("no")) {
            this.cursorAuthor = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant,nb_quotes From author where id_courant =" + this.cursorCourant.getString(0), null);
            this.totalAuthors += this.cursorAuthor.getCount();
            if (this.cursorAuthor.getCount() != 0) {
                this.cursorAuthor.moveToFirst();
                for (int k = 0; k < this.cursorAuthor.getCount(); k++) {
                    this.cursorAuthor.moveToNext();
                }
                this.cursorAuthor.moveToFirst();
                for (int j = 0; j < this.cursorAuthor.getCount(); j++) {
                    String courantString = this.cursorAuthor.getString(0);
                    this.cursorQuote = this.db.rawQuery("SELECT id_quote as _id,quote,book From quote where id_author =" + courantString, null);
                    this.totalQuotes += this.cursorQuote.getCount();
                    this.cursorQuote.moveToFirst();
                    if (this.cursorQuote.getCount() != 0) {
                        this.cursorQuote.moveToFirst();
                        for (int q = 0; q < this.cursorQuote.getCount(); q++) {
                            this.quotes.add(this.cursorQuote.getString(1));
                            this.quotesId.add(this.cursorQuote.getString(0));
                            this.sources.add(this.cursorQuote.getString(2));
                            this.authors.add(this.cursorAuthor.getString(1));
                            this.authorsId.add(Integer.valueOf(this.cursorAuthor.getString(0)));
                            this.authorsDetails.add(this.cursorAuthor.getString(4));
                            this.cursorQuote.moveToNext();
                        }
                    }
                    this.cursorAuthor.moveToNext();
                }
            }
            this.cursorCourant.moveToNext();
            this.number.setText(String.valueOf(this.position + 1) + " / " + this.quotes.size());
            this.on.setText(" / " + this.quotes.size());
            this.bookId = this.authorsId.get(this.position).intValue();
        } else if (total.equals("yes")) {
            this.cursorCourant.moveToFirst();
            this.courantId = this.cursorCourant.getString(0);
            this.cursorAuthor = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant From author where id_courant =" + this.cursorCourant.getString(0), null);
            this.totalAuthors += this.cursorAuthor.getCount();
            this.cursorAuthor.moveToFirst();
            for (int i = 0; i < this.cursorAuthor.getCount(); i++) {
                String courantString2 = this.cursorAuthor.getString(0);
                this.cursorQuote = this.db.rawQuery("SELECT id_quote as _id,quote,book From quote where id_author =" + courantString2, null);
                this.totalQuotes += this.cursorQuote.getCount();
                if (this.cursorQuote.getCount() != 0) {
                    this.cursorQuote.moveToFirst();
                    for (int j2 = 0; j2 < this.cursorQuote.getCount(); j2++) {
                        this.quotes.add(this.cursorQuote.getString(1));
                        this.quotesId.add(this.cursorQuote.getString(0));
                        this.sources.add(this.cursorQuote.getString(2));
                        this.authors.add(this.cursorAuthor.getString(1));
                        this.authorsId.add(Integer.valueOf(this.cursorAuthor.getString(0)));
                        this.authorsDetails.add(this.cursorAuthor.getString(4));
                        this.cursorQuote.moveToNext();
                    }
                }
                this.cursorAuthor.moveToNext();
            }
            this.cursorCourant2 = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent From courant where id_parent =" + this.courantId, null);
            if (this.cursorCourant2.getCount() != 0) {
                this.cursorCourant2.moveToFirst();
                for (int i2 = 0; i2 < this.cursorCourant2.getCount(); i2++) {
                    this.cursorAuthor2 = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant From author where id_courant =" + this.cursorCourant2.getString(0), null);
                    this.totalAuthors += this.cursorAuthor2.getCount();
                    if (this.cursorAuthor2.getCount() != 0) {
                        this.cursorAuthor2.moveToFirst();
                        for (int j3 = 0; j3 < this.cursorAuthor2.getCount(); j3++) {
                            String courantString3 = this.cursorAuthor2.getString(0);
                            this.cursorQuote2 = this.db.rawQuery("SELECT id_quote as _id,quote,book From quote where id_author =" + courantString3, null);
                            this.totalQuotes += this.cursorQuote2.getCount();
                            if (this.cursorQuote2.getCount() != 0) {
                                this.cursorQuote2.moveToFirst();
                                for (int q2 = 0; q2 < this.cursorQuote2.getCount(); q2++) {
                                    this.quotes.add(this.cursorQuote2.getString(1));
                                    this.quotesId.add(this.cursorQuote2.getString(0));
                                    this.sources.add(this.cursorQuote2.getString(2));
                                    this.authors.add(this.cursorAuthor2.getString(1));
                                    this.authorsId.add(Integer.valueOf(this.cursorAuthor2.getString(0)));
                                    this.authorsDetails.add(this.cursorAuthor2.getString(4));
                                    this.cursorQuote2.moveToNext();
                                }
                            }
                            this.cursorAuthor2.moveToNext();
                        }
                    }
                    this.cursorCourant3 = this.db.rawQuery("SELECT id_courant as _id,courant,id_parent From courant where id_parent =" + this.cursorCourant2.getString(0), null);
                    if (this.cursorCourant3.getCount() != 0) {
                        this.cursorCourant3.moveToFirst();
                        this.cursorCourant3.moveToFirst();
                        for (int g = 0; g < this.cursorCourant3.getCount(); g++) {
                            this.cursorAuthor3 = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant From author where id_courant =" + this.cursorCourant3.getString(0), null);
                            this.totalAuthors += this.cursorAuthor3.getCount();
                            if (this.cursorAuthor3.getCount() != 0) {
                                this.cursorAuthor3.moveToFirst();
                                for (int j4 = 0; j4 < this.cursorAuthor3.getCount(); j4++) {
                                    String courantString4 = this.cursorAuthor3.getString(0);
                                    this.cursorQuote3 = this.db.rawQuery("SELECT id_quote as _id,quote,book From quote where id_author =" + courantString4, null);
                                    this.totalQuotes += this.cursorQuote3.getCount();
                                    this.cursorQuote3.moveToFirst();
                                    if (this.cursorQuote3.getCount() != 0) {
                                        this.cursorQuote3.moveToFirst();
                                        for (int q3 = 0; q3 < this.cursorQuote3.getCount(); q3++) {
                                            this.quotes.add(this.cursorQuote3.getString(1));
                                            this.quotesId.add(this.cursorQuote3.getString(0));
                                            this.sources.add(this.cursorQuote3.getString(2));
                                            this.authors.add(this.cursorAuthor3.getString(1));
                                            this.authorsId.add(Integer.valueOf(this.cursorAuthor3.getString(0)));
                                            this.authorsDetails.add(this.cursorAuthor3.getString(4));
                                            this.cursorQuote3.moveToNext();
                                        }
                                    }
                                    this.cursorAuthor3.moveToNext();
                                }
                            }
                            this.cursorCourant3.moveToNext();
                        }
                    }
                    this.cursorCourant2.moveToNext();
                }
            }
            this.number.setText(String.valueOf(this.position + 1) + " / " + this.quotes.size());
            this.on.setText(" / " + this.quotes.size());
            this.bookId = this.authorsId.get(this.position).intValue();
        }
        return this.quotes.size();
    }

    public int initCategoriesAdapter(Category category, String total) {
        if (total.equals("no")) {
            this.cursorCategory = this.db.rawQuery("SELECT id_category as _id,category,id_parent,nb_quotes From category where id_category =" + category.getIdCategory(), null);
            this.cursorCategory.moveToFirst();
            this.actionBar.setTitle(this.cursorCategory.getString(1));
            this.title.setText(this.cursorCategory.getString(1));
            this.titleActionBar.setText(this.cursorCategory.getString(1));
            this.cursorQuoCategory = this.db.rawQuery("SELECT id_affiliation as _id,id_quote,id_category From quo_category where id_category =" + this.cursorCategory.getString(0), null);
            this.cursorQuoCategory.moveToFirst();
            if (this.cursorQuoCategory.getCount() != 0) {
                for (int i = 0; i < this.cursorQuoCategory.getCount(); i++) {
                    this.cursorQuote = this.db.rawQuery("SELECT id_quote as _id,id_author,quote,book From quote where id_quote =" + this.cursorQuoCategory.getString(1), null);
                    this.cursorQuote.moveToFirst();
                    if (this.cursorQuote.getCount() != 0) {
                        this.quotes.add(this.cursorQuote.getString(2));
                        this.quotesId.add(this.cursorQuote.getString(0));
                        this.sources.add(this.cursorQuote.getString(3));
                        this.cursorAuthor = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant From author where id_author =" + this.cursorQuote.getString(1), null);
                        this.cursorAuthor.moveToFirst();
                        if (this.cursorAuthor.getCount() == 0 || this.cursorAuthor.getString(5).equals("")) {
                            this.quotes.remove(this.cursorQuote.getString(2));
                            this.quotesId.remove(this.cursorQuote.getString(0));
                            this.sources.remove(this.cursorQuote.getString(3));
                        }
                        if (this.cursorAuthor.getCount() != 0 && !this.cursorAuthor.getString(5).equals("")) {
                            this.authorsId.add(Integer.valueOf(this.cursorAuthor.getString(0)));
                            this.authors.add(this.cursorAuthor.getString(1));
                            this.authorsDetails.add(this.cursorAuthor.getString(4));
                        }
                    }
                    this.cursorQuoCategory.moveToNext();
                }
            }
        } else if (total.equals("yes")) {
            this.cursorCategory = this.db.rawQuery("SELECT id_category as _id,category,id_parent,nb_quotes From category where id_category= " + category.getIdCategory(), null);
            this.cursorCategory.moveToFirst();
            this.actionBar.setTitle(this.cursorCategory.getString(1));
            this.title.setText(this.cursorCategory.getString(1));
            this.titleActionBar.setText(this.cursorCategory.getString(1));
            this.cursorCategory1 = this.db.rawQuery("SELECT id_category as _id,category,id_parent,nb_quotes From category where id_parent =" + this.cursorCategory.getString(0), null);
            this.cursorCategory1.moveToFirst();
            for (int l = 0; l < this.cursorCategory1.getCount(); l++) {
                this.cursorQuoCategory = this.db.rawQuery("SELECT id_affiliation as _id,id_quote,id_category From quo_category where id_category =" + this.cursorCategory1.getString(0), null);
                this.cursorQuoCategory.moveToFirst();
                if (this.cursorQuoCategory.getCount() != 0) {
                    for (int i2 = 0; i2 < this.cursorQuoCategory.getCount(); i2++) {
                        this.cursorQuote = this.db.rawQuery("SELECT id_quote as _id,id_author,quote,book From quote where id_quote =" + this.cursorQuoCategory.getString(1), null);
                        this.cursorQuote.moveToFirst();
                        this.quotes.add(this.cursorQuote.getString(2));
                        this.quotesId.add(this.cursorQuote.getString(0));
                        this.sources.add(this.cursorQuote.getString(3));
                        this.cursorAuthor = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant From author where id_author =" + this.cursorQuote.getString(1), null);
                        this.cursorAuthor.moveToFirst();
                        if (this.cursorAuthor.getString(5).equals("")) {
                            this.quotes.remove(this.cursorQuote.getString(2));
                            this.quotesId.remove(this.cursorQuote.getString(0));
                            this.sources.remove(this.cursorQuote.getString(3));
                        }
                        if (this.cursorAuthor.getCount() != 0 && !this.cursorAuthor.getString(5).equals("")) {
                            this.authorsId.add(Integer.valueOf(this.cursorAuthor.getString(0)));
                            this.authors.add(this.cursorAuthor.getString(1));
                            this.authorsDetails.add(this.cursorAuthor.getString(4));
                        }
                        this.cursorQuoCategory.moveToNext();
                    }
                }
                this.cursorCategory1.moveToNext();
            }
        }
        this.number.setText(String.valueOf(this.position + 1) + " / " + this.quotes.size());
        this.on.setText(" / " + this.quotes.size());
        this.bookId = this.authorsId.get(this.position).intValue();
        return this.quotes.size();
    }

    private void initAdapter() {
        if (!getIntent().getExtras().getString("fromActivity").equals("")) {
            fromActivity = getIntent().getExtras().getString("fromActivity");
        }
        String type = getIntent().getExtras().getString("type");
        String total = getIntent().getExtras().getString("total");
        this.bookId = getIntent().getExtras().getInt("book");
        if (type.equals("author")) {
            this.courant = (Courant) getIntent().getSerializableExtra("courant");
            initAuthorsAdapter(this.courant, total);
        }
        if (type.equals("book")) {
            this.courant = (Courant) getIntent().getSerializableExtra("courant");
            initBooksAdapter(this.courant, total);
        }
        if (type.equals("courant")) {
            this.courant = (Courant) getIntent().getSerializableExtra("courant");
            initSubjectsAdapter(this.courant, total);
        }
        if (type.equals("category")) {
            this.category = (Category) getIntent().getSerializableExtra("category");
            initCategoriesAdapter(this.category, total);
        }
    }

    public void onMenu() {
        String message = String.valueOf(this.fragments.get(this.mPager.getCurrentItem()).getQuotes().toString()) + "<br/><br/>" + this.fragments.get(this.mPager.getCurrentItem()).getAuthor().toString() + ",  " + this.fragments.get(this.mPager.getCurrentItem()).getAuthorDetail() + "<br/><br/>" + this.fragments.get(this.mPager.getCurrentItem()).getSources();
        Intent email = new Intent("android.intent.action.SEND");
        email.putExtra("android.intent.extra.EMAIL", new String[]{""});
        email.putExtra("android.intent.extra.SUBJECT", "Citations de OneLittleAngel");
        email.putExtra("android.intent.extra.TEXT", Html.fromHtml(message));
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "choisissez :"));
    }

    public void onPhone() {
        String toSpeak = String.valueOf(this.fragments.get(this.mPager.getCurrentItem()).getAuthor().toString()) + " ... " + this.fragments.get(this.mPager.getCurrentItem()).getAuthorDetail().toString() + " ... " + this.fragments.get(this.mPager.getCurrentItem()).getQuotes().toString();
        this.tts.speak(toSpeak, 0, null);
    }

    public ArrayList<Bitmap> fetchSingle(String authorName) {
        ArrayList<Bitmap> bitmap = null;
        String s = authorName.replace(' ', '_');
        String nfdNormalizedString = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String s2 = pattern.matcher(nfdNormalizedString).replaceAll("");
        Cursor queryCursor = this.db.rawQuery("SELECT id_picture as _id,realpicture from picture where name_small ='" + s2 + "'", null);
        if (queryCursor != null) {
            bitmap = new ArrayList<>();
            queryCursor.moveToFirst();
            for (int i = 0; i < queryCursor.getCount(); i++) {
                byte[] image = queryCursor.getBlob(1);
                bitmap.add(BitmapFactory.decodeByteArray(image, 0, image.length));
                queryCursor.moveToNext();
            }
            queryCursor.close();
        }
        return bitmap;
    }

    public void onEye(View v) {
        String author = this.fragments.get(this.mPager.getCurrentItem()).getAuthor();
        String authorDetail = this.fragments.get(this.mPager.getCurrentItem()).getAuthorDetail();
        this.cursorAuthor = this.db.rawQuery("SELECT id_author as _id,name,mainpicture from author where name='" + author + "'", null);
        this.cursorAuthor.moveToFirst();
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        TextView tv = new TextView(this);
        tv.setText(author);
        tv.setTextSize(20.0f);
        tv.setTextColor(-1);
        tv.setGravity(1);
        TextView t2 = new TextView(this);
        t2.setText(authorDetail);
        t2.setTextColor(-1);
        t2.setTextSize(20.0f);
        adb.setCustomTitle(tv);
        adb.setView(t2);
        adb.setNeutralButton("Ok", (DialogInterface.OnClickListener) null);
        AlertDialog dialog = adb.create();
        dialog.show();
        Button bn = dialog.getButton(-3);
        bn.setHeight(40);
        bn.setTextSize(20.0f);
    }

    public void onSource(View v) {
        String source = this.fragments.get(this.mPager.getCurrentItem()).getSources();
        TextView tv = new TextView(this);
        tv.setText("source :");
        tv.setTextSize(20.0f);
        tv.setTextColor(-1);
        tv.setGravity(1);
        TextView t2 = new TextView(this);
        t2.setText(source);
        t2.setTextColor(-1);
        t2.setTextSize(20.0f);
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setCustomTitle(tv);
        adb.setView(t2);
        adb.setNeutralButton("Ok", (DialogInterface.OnClickListener) null);
        AlertDialog dialog = adb.create();
        dialog.show();
        Button bn = dialog.getButton(-3);
        bn.setHeight(40);
        bn.setTextSize(20.0f);
    }

    public void onHelp() {
        TextView t2 = new TextView(this);
        t2.setText("Astuces :");
        t2.setTextColor(-1);
        t2.setGravity(1);
        t2.setTextSize(25.0f);
        TextView t1 = new TextView(this);
        t1.setText(" - Vous pouvez balayer les citations avec le doigt vers la droite (prochaine citation) ou vers la gauche (citation précédente)\n\n - Tourner votre écran vers le mode paysage pour accéder à plus de menus dans la barre du bas : modification de la fonte, de la taille des caractères,lecteur audio.\n\n - Cliquer sur le logo en haut à gauche ou sur la flèche pour revenir au menu principal\n");
        t1.setTextColor(-1);
        t1.setGravity(3);
        t1.setTextSize(25.0f);
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setCustomTitle(t2);
        adb.setView(t1);
        adb.setNeutralButton("Ok", (DialogInterface.OnClickListener) null);
        AlertDialog dialog = adb.create();
        dialog.show();
        Button bn = dialog.getButton(-3);
        bn.setHeight(40);
        bn.setTextSize(20.0f);
    }

    public void addFavorite() {
        this.db = new DataBaseHelper(this).getWritableDatabase();
        final int i = this.fragments.get(this.mPager.getCurrentItem()).getQuoteId();
        TextView t2 = new TextView(this);
        t2.setText("Ajouter cette citation aux favoris ? ");
        t2.setTextColor(-1);
        t2.setTextSize(25.0f);
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setView(t2);
        adb.setPositiveButton("Oui", new DialogInterface.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                QuotesTextActivity.this.oldPos = QuotesTextActivity.this.mPager.getCurrentItem();
                String sql = "INSERT INTO favorite VALUES (NULL," + i + " )";
                QuotesTextActivity.this.db.execSQL(sql);
                QuotesTextActivity.this.cursorFavorites = QuotesTextActivity.this.db.rawQuery("SELECT id_favorite as _id,id_quote From favorite", null);
                QuotesTextActivity.this.cursorFavorites.moveToFirst();
                if (QuotesTextActivity.this.cursorFavorites.getCount() != 0) {
                    for (int i2 = 0; i2 < QuotesTextActivity.this.cursorFavorites.getCount(); i2++) {
                        QuotesTextActivity.this.quotesIdFavorites.add(QuotesTextActivity.this.cursorFavorites.getString(1));
                        QuotesTextActivity.this.cursorFavorites.moveToNext();
                    }
                }
                if (QuotesTextActivity.this.oldPos >= QuotesTextActivity.this.quotes.size()) {
                    if (QuotesTextActivity.this.quotes.size() - 1 >= QuotesTextActivity.this.oldPos || QuotesTextActivity.this.oldPos >= QuotesTextActivity.this.quotes.size() * 2) {
                        if (QuotesTextActivity.this.oldPos + 1 > QuotesTextActivity.this.quotes.size() * 2) {
                            if (QuotesTextActivity.this.quotesIdFavorites.contains(QuotesTextActivity.this.quotesId.get(QuotesTextActivity.this.oldPos - (QuotesTextActivity.this.quotes.size() * 2)))) {
                                QuotesTextActivity.this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                                QuotesTextActivity.this.actionBar.getFavorites().setTag("favorites_delete");
                                return;
                            } else {
                                QuotesTextActivity.this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                                QuotesTextActivity.this.actionBar.getFavorites().setTag("favorites_add");
                                QuotesTextActivity.this.actionBar.getFavorites().setClickable(true);
                                return;
                            }
                        }
                        return;
                    }
                    if (QuotesTextActivity.this.quotesIdFavorites.contains(QuotesTextActivity.this.quotesId.get(QuotesTextActivity.this.oldPos - QuotesTextActivity.this.quotes.size()))) {
                        QuotesTextActivity.this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                        QuotesTextActivity.this.actionBar.getFavorites().setTag("favorites_delete");
                        return;
                    } else {
                        QuotesTextActivity.this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                        QuotesTextActivity.this.actionBar.getFavorites().setTag("favorites_add");
                        QuotesTextActivity.this.actionBar.getFavorites().setClickable(true);
                        return;
                    }
                }
                if (QuotesTextActivity.this.quotesIdFavorites.contains(QuotesTextActivity.this.quotesId.get(QuotesTextActivity.this.oldPos))) {
                    QuotesTextActivity.this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                    QuotesTextActivity.this.actionBar.getFavorites().setTag("favorites_delete");
                } else {
                    QuotesTextActivity.this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                    QuotesTextActivity.this.actionBar.getFavorites().setTag("favorites_add");
                    QuotesTextActivity.this.actionBar.getFavorites().setClickable(true);
                }
            }
        });
        adb.setNegativeButton("Non", (DialogInterface.OnClickListener) null);
        AlertDialog dialog = adb.create();
        dialog.show();
        Button bn = dialog.getButton(-2);
        bn.setHeight(40);
        bn.setTextSize(20.0f);
        Button bp = dialog.getButton(-1);
        bp.setHeight(40);
        bp.setTextSize(20.0f);
    }

    public void onSearch(View v) {
        int i;
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) v.findViewById(R.id.custom_toast_layout_id));
        TextView t2 = (TextView) layout.findViewById(R.id.textView1);
        if (this.edit.getText().toString().equals("")) {
            i = -1;
        } else {
            i = Integer.parseInt(this.edit.getText().toString());
            this.edit.setText("");
        }
        if ((i < 1 || i > this.quotes.size()) && i != -1) {
            t2.setText("le nombre que vous venez de saisir ne correspond à aucunes citations");
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(16, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            this.edit.setText("");
            return;
        }
        if (i == -1) {
            Toast toast2 = new Toast(getApplicationContext());
            toast2.setGravity(16, 0, 0);
            toast2.setDuration(Toast.LENGTH_LONG);
            toast2.setView(layout);
            toast2.show();
            t2.setText("veuillez saisir le numéro d'une citation pour y accéder");
            toast2.setGravity(17, 0, 0);
            toast2.show();
            return;
        }
        this.mPager.setCurrentItem(this.quotes.size() + (i - 1), true);
        if (this.mPager.getCurrentItem() < this.quotes.size()) {
            this.number.setText(String.valueOf(this.mPager.getCurrentItem() + 1) + " / " + this.quotes.size());
            if (this.quotesIdFavorites.contains(this.quotesId.get(this.mPager.getCurrentItem()))) {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                this.actionBar.getFavorites().setTag("favorites_delete");
                return;
            } else {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                this.actionBar.getFavorites().setTag("favorites_add");
                this.actionBar.getFavorites().setClickable(true);
                return;
            }
        }
        if (this.quotes.size() - 1 < this.mPager.getCurrentItem() && this.mPager.getCurrentItem() < this.quotes.size() * 2) {
            this.number.setText(String.valueOf((this.mPager.getCurrentItem() + 1) - this.quotes.size()) + " / " + this.quotes.size());
            if (this.quotesIdFavorites.contains(this.quotesId.get(this.mPager.getCurrentItem() - this.quotes.size()))) {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                this.actionBar.getFavorites().setTag("favorites_delete");
                return;
            } else {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                this.actionBar.getFavorites().setTag("favorites_add");
                this.actionBar.getFavorites().setClickable(true);
                return;
            }
        }
        if (this.mPager.getCurrentItem() + 1 > this.quotes.size() * 2) {
            this.number.setText(String.valueOf((this.mPager.getCurrentItem() + 1) - (this.quotes.size() * 2)) + " / " + this.quotes.size());
            if (this.quotesIdFavorites.contains(this.quotesId.get((this.mPager.getCurrentItem() + 1) - (this.quotes.size() * 2)))) {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                this.actionBar.getFavorites().setTag("favorites_delete");
            } else {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                this.actionBar.getFavorites().setTag("favorites_add");
                this.actionBar.getFavorites().setClickable(true);
            }
        }
    }

    public void onFonts() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        this.list = (ListView) getLayoutInflater().inflate(R.layout.tools, (ViewGroup) null);
        this.list.setChoiceMode(1);
        this.adapter = new SingleListAdapter(this, fontList, this.alf, fontBool);
        this.list.setChoiceMode(1);
        this.list.setAdapter((ListAdapter) this.adapter);
        this.list.setCacheColorHint(-16777216);
        this.list.setItemChecked(selected, true);
        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity.5
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                for (int i = 0; i < QuotesTextActivity.fontBool.size(); i++) {
                    QuotesTextActivity.fontBool.put(Integer.valueOf(i), false);
                }
                QuotesTextActivity.fontBool.put(Integer.valueOf(arg2), true);
                QuotesTextActivity.selected = arg2;
                QuotesTextActivity.this.updateDataBaseFont(QuotesTextActivity.fontList, QuotesTextActivity.fontBool);
                QuotesTextActivity.fontList = FontsHelper.getFontList();
                QuotesTextActivity.fontBool = FontsHelper.getBool();
                FontsHelper.init();
                QuotesTextActivity.this.selectedFont = FontsHelper.getSelectedFont();
                ArrayList<CustomFragment> fragments1 = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    for (int i2 = 0; i2 < QuotesTextActivity.this.quotes.size(); i2++) {
                        fragments1.add(new CustomFragment(QuotesTextActivity.this, Integer.parseInt((String) QuotesTextActivity.this.quotesId.get(i2)), (String) QuotesTextActivity.this.quotes.get(i2), (String) QuotesTextActivity.this.sources.get(i2), (String) QuotesTextActivity.this.authors.get(i2), ((Integer) QuotesTextActivity.this.authorsId.get(i2)).intValue(), (String) QuotesTextActivity.this.authorsDetails.get(i2), String.valueOf(QuotesTextActivity.this.text.getText()), QuotesTextActivity.firstLetterSize, QuotesTextActivity.textSize, QuotesTextActivity.heightPortrait, QuotesTextActivity.this.selectedFont, QuotesTextActivity.screensize));
                    }
                }
                MyPageAdapterFragment mPagerAdapter1 = new MyPageAdapterFragment(QuotesTextActivity.this.getSupportFragmentManager(), fragments1);
                QuotesTextActivity.this.mPager.setAdapter(mPagerAdapter1);
                QuotesTextActivity.this.mPager.setCurrentItem(QuotesTextActivity.this.oldPos);
                QuotesTextActivity.this.mPager.getAdapter().notifyDataSetChanged();
            }
        });
        TextView title = new TextView(this);
        title.setText("Choix de la police : ");
        title.setBackgroundColor(0);
        title.setGravity(17);
        title.setTextColor(-1);
        if ((getResources().getConfiguration().screenLayout & 15) == 3) {
            title.setTextSize(38.0f);
        } else {
            title.setTextSize(20.0f);
        }
        adb.setCustomTitle(title);
        adb.setView(this.list);
        adb.setPositiveButton("Ok", (DialogInterface.OnClickListener) null);
        AlertDialog dialog = adb.create();
        dialog.show();
        Button bp = dialog.getButton(-1);
        bp.setHeight(40);
        bp.setTextSize(20.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDataBaseFont(Map<Integer, String> fontList2, Map<Integer, Boolean> fontBool2) {
        String bool;
        this.db = new DataBaseHelper(this).getWritableDatabase();
        for (int i = 0; i < fontList2.size(); i++) {
            if (!fontBool2.get(Integer.valueOf(i)).booleanValue()) {
                bool = "0";
            } else {
                bool = "1";
            }
            String sql = "UPDATE textfont SET selected =" + bool + " WHERE  font ='" + fontList2.get(Integer.valueOf(i)) + "'";
            this.db.execSQL(sql);
        }
    }

    public void onCourant(View v) {
        Intent i = new Intent(this, (Class<?>) QuotesTextActivity.class);
        String s = v.getTag().toString();
        String cs = null;
        if (s.equals("details_author1")) {
            cs = this.fragments.get(this.mPager.getCurrentItem()).getCourantSelected1();
        } else if (s.equals("details_author2")) {
            cs = this.fragments.get(this.mPager.getCurrentItem()).getCourantSelected2();
        } else if (s.equals("details_author3")) {
            cs = this.fragments.get(this.mPager.getCurrentItem()).getCourantSelected3();
        }
        this.oldPos = 0;
        this.courantIntent = new Courant(cs);
        i.putExtra("type", "courant");
        i.putExtra("fromActivity", "");
        i.putExtra("courant", this.courantIntent);
        i.putExtra("total", "yes");
        MainActivity.setMAIntent(i);
        startActivity(i);
        finish();
    }

    public void onAuthor(View v) {
        Intent i = new Intent(this, (Class<?>) QuotesTextActivity.class);
        this.authorSelected = this.fragments.get(this.mPager.getCurrentItem()).getAuthor();
        Courant courant = new Courant(this.authorSelected);
        i.putExtra("type", "author");
        i.putExtra("fromActivity", "");
        i.putExtra("author", this.authorSelected);
        i.putExtra("book", this.fragments.get(this.mPager.getCurrentItem()).getAuthorId());
        i.putExtra("courant", courant);
        i.putExtra("total", "no");
        this.oldPos = 0;
        startActivity(i);
        finish();
    }

    public void onPrevious() {
        this.mPager.setCurrentItem(this.mPager.getCurrentItem() - 1, true);
        if (this.mPager.getCurrentItem() < this.quotes.size()) {
            this.number.setText(String.valueOf(this.mPager.getCurrentItem() + 1) + " / " + this.quotes.size());
            if (this.quotesIdFavorites.contains(this.quotesId.get(this.mPager.getCurrentItem()))) {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                this.actionBar.getFavorites().setTag("favorites_delete");
                return;
            } else {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                this.actionBar.getFavorites().setTag("favorites_add");
                this.actionBar.getFavorites().setClickable(true);
                return;
            }
        }
        if (this.quotes.size() - 1 < this.mPager.getCurrentItem() && this.mPager.getCurrentItem() < this.quotes.size() * 2) {
            this.number.setText(String.valueOf((this.mPager.getCurrentItem() + 1) - this.quotes.size()) + " / " + this.quotes.size());
            if (this.quotesIdFavorites.contains(this.quotesId.get(this.mPager.getCurrentItem() - this.quotes.size()))) {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                this.actionBar.getFavorites().setTag("favorites_delete");
                return;
            } else {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                this.actionBar.getFavorites().setTag("favorites_add");
                this.actionBar.getFavorites().setClickable(true);
                return;
            }
        }
        if (this.mPager.getCurrentItem() + 1 > this.quotes.size() * 2) {
            this.number.setText(String.valueOf((this.mPager.getCurrentItem() + 1) - (this.quotes.size() * 2)) + " / " + this.quotes.size());
            if (this.quotesIdFavorites.contains(this.quotesId.get((this.mPager.getCurrentItem() + 1) - (this.quotes.size() * 2)))) {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                this.actionBar.getFavorites().setTag("favorites_delete");
            } else {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                this.actionBar.getFavorites().setTag("favorites_add");
                this.actionBar.getFavorites().setClickable(true);
            }
        }
    }

    public void onNext() {
        this.mPager.setCurrentItem(this.mPager.getCurrentItem() + 1, true);
        if (this.mPager.getCurrentItem() < this.quotes.size()) {
            this.number.setText(String.valueOf(this.mPager.getCurrentItem() + 1) + " / " + this.quotes.size());
            if (this.quotesIdFavorites.contains(this.quotesId.get(this.mPager.getCurrentItem()))) {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                this.actionBar.getFavorites().setTag("favorites_delete");
            } else {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                this.actionBar.getFavorites().setTag("favorites_add");
                this.actionBar.getFavorites().setClickable(true);
            }
        } else if (this.quotes.size() - 1 < this.mPager.getCurrentItem() && this.mPager.getCurrentItem() < this.quotes.size() * 2) {
            this.number.setText(String.valueOf((this.mPager.getCurrentItem() + 1) - this.quotes.size()) + " / " + this.quotes.size());
            if (this.quotesIdFavorites.contains(this.quotesId.get(this.mPager.getCurrentItem() - this.quotes.size()))) {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                this.actionBar.getFavorites().setTag("favorites_delete");
            } else {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                this.actionBar.getFavorites().setTag("favorites_add");
                this.actionBar.getFavorites().setClickable(true);
            }
        } else if (this.mPager.getCurrentItem() + 1 > this.quotes.size() * 2) {
            this.number.setText(String.valueOf((this.mPager.getCurrentItem() + 1) - (this.quotes.size() * 2)) + " / " + this.quotes.size());
            if (this.quotesIdFavorites.contains(this.quotesId.get(this.mPager.getCurrentItem() - (this.quotes.size() * 2)))) {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_delete);
                this.actionBar.getFavorites().setTag("favorites_delete");
            } else {
                this.actionBar.getFavorites().setBackgroundResource(R.drawable.favorites_add);
                this.actionBar.getFavorites().setTag("favorites_add");
                this.actionBar.getFavorites().setClickable(true);
            }
        }
        onWindowFocusChanged(true);
    }

    public void onSize(View v) {
        if (v.getTag().equals("more")) {
            firstLetterSize += 2;
            textSize += 2;
            String sql2 = "UPDATE font SET firstletter =" + firstLetterSize + "  WHERE  id = 1";
            String sql3 = "UPDATE font SET textsize=" + textSize + " WHERE  id = 1";
            this.dbw.execSQL(sql2);
            this.dbw.execSQL(sql3);
            this.oldPos = this.mPager.getCurrentItem();
            ArrayList<CustomFragment> fragments1 = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < this.quotes.size(); i++) {
                    fragments1.add(new CustomFragment(this, Integer.parseInt(this.quotesId.get(i)), this.quotes.get(i), this.sources.get(i), this.authors.get(i), this.authorsId.get(i).intValue(), this.authorsDetails.get(i), String.valueOf(this.text.getText()), firstLetterSize, textSize, heightPortrait, this.selectedFont, screensize));
                }
            }
            MyPageAdapterFragment mPagerAdapter1 = new MyPageAdapterFragment(getSupportFragmentManager(), fragments1);
            this.mPager.setAdapter(mPagerAdapter1);
            this.mPager.setCurrentItem(this.oldPos);
            option = true;
            return;
        }
        if (v.getTag().equals("less")) {
            if (textSize < textSizeMinimum + 1) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast, (ViewGroup) v.findViewById(R.id.custom_toast_layout_id));
                TextView t2 = (TextView) layout.findViewById(R.id.textView1);
                t2.setText("Vous avez atteind la taille minimum");
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(16, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
                return;
            }
            firstLetterSize -= 2;
            textSize -= 2;
            String sql = "UPDATE font SET firstletter =" + firstLetterSize + "  WHERE  id = 1";
            String sql1 = "UPDATE font SET textsize=" + textSize + " WHERE  id = 1";
            this.dbw.execSQL(sql);
            this.dbw.execSQL(sql1);
            this.oldPos = this.mPager.getCurrentItem();
            ArrayList<CustomFragment> fragments12 = new ArrayList<>();
            for (int j2 = 0; j2 < 3; j2++) {
                for (int i2 = 0; i2 < this.quotes.size(); i2++) {
                    fragments12.add(new CustomFragment(this, Integer.parseInt(this.quotesId.get(i2)), this.quotes.get(i2), this.sources.get(i2), this.authors.get(i2), this.authorsId.get(i2).intValue(), this.authorsDetails.get(i2), String.valueOf(this.text.getText()), firstLetterSize, textSize, heightPortrait, this.selectedFont, screensize));
                }
            }
            MyPageAdapterFragment mPagerAdapter12 = new MyPageAdapterFragment(getSupportFragmentManager(), fragments12);
            this.mPager.setAdapter(mPagerAdapter12);
            this.mPager.setCurrentItem(this.oldPos);
            option = true;
        }
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        //EasyTracker.getInstance(this).activityStop(this);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        this.dbw.close();
        this.db.close();
        if (this.tts != null) {
            this.tts.stop();
            this.tts.shutdown();
        }
        super.onDestroy();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        this.orientation = getResources().getConfiguration().orientation;
        super.onStart();
        //EasyTracker.getInstance(this).activityStart(this);
    }
}
