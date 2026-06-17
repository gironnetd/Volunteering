package com.sc.fr.onelittleangel.bouddhisme.favorites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import com.sc.fr.onelittleangel.bouddhisme.activities.MainActivity;
import com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity;
import com.sc.fr.onelittleangel.bouddhisme.entities.Courant;
import com.sc.fr.onelittleangel.bouddhisme.adapter.SingleListAdapter;
import com.sc.fr.onelittleangel.bouddhisme.fragments.CustomFragment;
import com.sc.fr.onelittleangel.bouddhisme.fragments.MyPageAdapterFragment;
import com.sc.fr.onelittleangel.bouddhisme.fragments.MyViewPager;
import com.sc.fr.onelittleangel.bouddhisme.helpers.DataBaseHelper;
import com.sc.fr.onelittleangel.bouddhisme.helpers.FontsHelper;
import com.sc.fr.onelittleangel.bouddhisme.helpers.ToolsHelper;
import com.sc.fr.onelittleangel.bouddhisme.adapter.ToolsListAdapter;
import com.sc.fr.onelittleangel.bouddhisme.views.ActionBarFavoritesCustom;
import com.sc.fr.onelittleangel.bouddhisme.views.MenuLandscapeBarCustom;
import com.sc.fr.onelittleangel.bouddhisme.views.MenuPortraitBarCustom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class QuotesTextFavoritesActivity extends FragmentActivity {
    private static String screensize;
    private static int selected;
    RelativeLayout action;
    ListAdapter adapter;
    Animation animZoomIn;
    Animation animZoomOut;
    private String authorSelected;
    int bookId;
    private ImageView buttonBack;
    private ImageView buttonBackLandscape;
    private ImageView buttonForward;
    private ImageView buttonForwardLandscape;
    private Courant courantIntent;
    private Cursor cursorAuthor;
    private Cursor cursorFavorites;
    private Cursor cursorFont;
    private Cursor cursorQuote;
    private EditText edit;
    ImageView email;
    private Typeface face;
    ImageView favorites;
    FontsHelper fh;
    private ImageView go;
    ImageView help;
    ImageView logo;
    private ViewPager mPager;
    private MyPageAdapterFragment mPagerAdapterFragment;
    private ImageView menu;
    MenuLandscapeBarCustom menubarlandscape;
    MenuPortraitBarCustom menubarportrait;
    private TextView number;
    View.OnClickListener oc;
    private TextView on;
    int orientation;
    ImageView phone;
    ImageView r;
    private String selectedFont;
    String tag;
    private TextView text;
    ImageView textFont;
    ImageView textLess;
    ImageView textMore;
    TextView title;
    ImageView tools;
    ToolsHelper toolsHelper;
    TextToSpeech tts;
    MyViewPager viewPager;
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
    private ArrayList<Integer> quotesId = new ArrayList<>();
    private ArrayList<String> sources = new ArrayList<>();
    private ArrayList<String> authors = new ArrayList<>();
    private ArrayList<String> authorsDetails = new ArrayList<>();
    private ArrayList<Integer> authorsId = new ArrayList<>();
    int compteur = 0;
    int oldPos = 0;
    private ArrayList<CustomFragment> fragments = new ArrayList<>();
    FragmentTransaction fragMentTra = null;
    ArrayList<String> alf = new ArrayList<>();

    public QuotesTextFavoritesActivity() {
    }

    public QuotesTextFavoritesActivity(MyViewPager viewPager) {
        this.viewPager = viewPager;
    }

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
        this.alf.add("MetalMania");
        this.alf.add("mtcorsva");
        this.alf.add("Nova Oval");
        this.alf.add("Parisienne");
        this.alf.add("Quintessential");
        this.alf.add("Rosarivo");
        this.alf.add("Sorts Mill Goudy");
        this.alf.add("UnifrakturCook");
        this.alf.add("UnifrakturMaguntia");
        super.onCreate(arg0);
        setContentView(R.layout.favorites_quote);
        this.orientation = getResources().getConfiguration().orientation;
        this.mPager = (ViewPager) findViewById(R.id.pager);
        if (arg0 != null) {
            this.fragments = arg0.getParcelableArrayList("fragments");
            this.mPagerAdapterFragment = new MyPageAdapterFragment(getSupportFragmentManager(), this.fragments);
            this.mPager.setAdapter(this.mPagerAdapterFragment);
        }
        this.face = Typeface.createFromAsset(getAssets(), "fonts/mtcorsva.ttf");
        this.orientation = getResources().getConfiguration().orientation;
        ActionBarFavoritesCustom actionBar = (ActionBarFavoritesCustom) findViewById(R.id.actionbar);
        this.menubarportrait = (MenuPortraitBarCustom) findViewById(R.id.menubarportrait);
        this.menubarlandscape = (MenuLandscapeBarCustom) findViewById(R.id.menubarlandscape);
        this.logo = (ImageView) actionBar.findViewById(R.id.ola_logo);
        this.email = (ImageView) actionBar.findViewById(R.id.email);
        this.favorites = (ImageView) actionBar.findViewById(R.id.favorites);
        this.menu = (ImageView) actionBar.findViewById(R.id.ic_menu);
        this.text = (TextView) actionBar.findViewById(R.id.title);
        this.text.setTypeface(this.face);
        actionBar.setTitle("Favoris");
        this.tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.1
            @Override // android.speech.tts.TextToSpeech.OnInitListener
            public void onInit(int status) {
                if (status != -1) {
                    QuotesTextFavoritesActivity.this.tts.setLanguage(Locale.FRENCH);
                }
            }
        });
        actionBar.setBackgroundResource(R.drawable.actionbar_background);
        if (this.orientation == 1) {
            this.menubarportrait.setVisibility(0);
            this.menubarlandscape.setVisibility(8);
            this.r = (ImageView) this.menubarportrait.findViewById(R.id.back);
            this.help = (ImageView) this.menubarportrait.findViewById(R.id.help);
            this.buttonBack = (ImageView) this.menubarportrait.findViewById(R.id.button_back);
            this.buttonForward = (ImageView) this.menubarportrait.findViewById(R.id.button_forward);
            this.edit = (EditText) this.menubarportrait.findViewById(R.id.edit);
            this.on = (TextView) this.menubarportrait.findViewById(R.id.on);
            this.go = (ImageView) this.menubarportrait.findViewById(R.id.go);
            this.number = (TextView) this.menubarportrait.findViewById(R.id.number);
        } else if (this.orientation == 2) {
            this.menubarportrait.setVisibility(8);
            this.menubarlandscape.setVisibility(0);
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
        this.oc = new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(final View v) {
                if (QuotesTextFavoritesActivity.this.quotes.size() == 0) {
                    if (v.getTag().equals("help")) {
                        QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_help_zoomin);
                        QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_help_zoomout);
                        QuotesTextFavoritesActivity.this.help.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                        QuotesTextFavoritesActivity.this.help.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                        QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.1
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                QuotesTextFavoritesActivity.this.onHelp();
                            }
                        });
                    }
                    if (v.getTag().equals("logo")) {
                        QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_logo_zoomin);
                        QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_logo_zoomout);
                        QuotesTextFavoritesActivity.this.logo.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                        QuotesTextFavoritesActivity.this.logo.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                        QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.2
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                MainActivity.setFromActivity("");
                                QuotesTextFavoritesActivity.this.startActivity(new Intent(QuotesTextFavoritesActivity.this, (Class<?>) MainActivity.class));
                                QuotesTextFavoritesActivity.this.finish();
                            }
                        });
                    }
                    if (v.getTag().equals("back")) {
                        QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_back_zoomin);
                        QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_back_zoomout);
                        QuotesTextFavoritesActivity.this.r.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                        QuotesTextFavoritesActivity.this.r.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                        QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.3
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                MainActivity.setFromActivity("");
                                QuotesTextFavoritesActivity.this.startActivity(new Intent(QuotesTextFavoritesActivity.this, (Class<?>) MainActivity.class));
                                QuotesTextFavoritesActivity.this.finish();
                            }
                        });
                        return;
                    }
                    return;
                }
                if (v.getTag().equals("ic_menu")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_menu_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_menu_zoomout);
                    QuotesTextFavoritesActivity.this.menu.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.menu.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.4
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextFavoritesActivity.this.onMenu();
                        }
                    });
                }
                if (v.getTag().equals("microphone")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_phone_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_phone_zoomout);
                    QuotesTextFavoritesActivity.this.phone.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.phone.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.5
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextFavoritesActivity.this.onPhone();
                        }
                    });
                }
                if (v.getTag().equals("help")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_help_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_help_zoomout);
                    QuotesTextFavoritesActivity.this.help.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.help.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.6
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextFavoritesActivity.this.onHelp();
                        }
                    });
                }
                if (v.getTag().equals("textfont")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_font_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_font_zoomout);
                    QuotesTextFavoritesActivity.this.textFont.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.textFont.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.7
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextFavoritesActivity.this.onFonts();
                        }
                    });
                }
                if (v.getTag().equals("logo")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_logo_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_logo_zoomout);
                    QuotesTextFavoritesActivity.this.logo.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.logo.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.8
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            MainActivity.setFromActivity("");
                            QuotesTextFavoritesActivity.this.startActivity(new Intent(QuotesTextFavoritesActivity.this, (Class<?>) MainActivity.class));
                            QuotesTextFavoritesActivity.this.finish();
                        }
                    });
                }
                if (v.getTag().equals("email")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_email_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_email_zoomout);
                    QuotesTextFavoritesActivity.this.email.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.email.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.9
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
                if (v.getTag().equals("favorites")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_favorites_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_favorites_zoomout);
                    QuotesTextFavoritesActivity.this.favorites.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.favorites.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.10
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextFavoritesActivity.this.deleteFavorite();
                        }
                    });
                }
                if (v.getTag().equals("go")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_go_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_go_zoomout);
                    QuotesTextFavoritesActivity.this.go.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.go.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.11
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextFavoritesActivity.this.onSearch(v);
                        }
                    });
                }
                if (v.getTag().equals("less")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_less_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_less_zoomout);
                    QuotesTextFavoritesActivity.this.textLess.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.textLess.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.12
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextFavoritesActivity.this.onSize(v);
                        }
                    });
                }
                if (v.getTag().equals("more")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_more_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_more_zoomout);
                    QuotesTextFavoritesActivity.this.textMore.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.textMore.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.13
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextFavoritesActivity.this.onSize(v);
                        }
                    });
                }
                if (v.getTag().equals("back")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_back_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_back_zoomout);
                    QuotesTextFavoritesActivity.this.r.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.r.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.14
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            MainActivity.setFromActivity("");
                            QuotesTextFavoritesActivity.this.startActivity(new Intent(QuotesTextFavoritesActivity.this, (Class<?>) MainActivity.class));
                            QuotesTextFavoritesActivity.this.finish();
                        }
                    });
                }
                if (v.getTag().equals("button_back")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_button_back_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_button_back_zoomout);
                    QuotesTextFavoritesActivity.this.buttonBack.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.buttonBack.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.15
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextFavoritesActivity.this.onPrevious();
                        }
                    });
                }
                if (v.getTag().equals("button_back_landscape")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_button_back_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_button_back_zoomout);
                    QuotesTextFavoritesActivity.this.buttonBackLandscape.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.buttonBackLandscape.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.16
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextFavoritesActivity.this.onPrevious();
                        }
                    });
                }
                if (v.getTag().equals("button_forward")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_button_forward_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_button_forward_zoomout);
                    QuotesTextFavoritesActivity.this.buttonForward.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.buttonForward.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.17
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextFavoritesActivity.this.onNext();
                        }
                    });
                }
                if (v.getTag().equals("button_forward_landscape")) {
                    QuotesTextFavoritesActivity.this.animZoomIn = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_button_forward_zoomin);
                    QuotesTextFavoritesActivity.this.animZoomOut = AnimationUtils.loadAnimation(QuotesTextFavoritesActivity.this.getApplicationContext(), R.anim.anim_button_forward_zoomout);
                    QuotesTextFavoritesActivity.this.buttonForwardLandscape.startAnimation(QuotesTextFavoritesActivity.this.animZoomIn);
                    QuotesTextFavoritesActivity.this.buttonForwardLandscape.startAnimation(QuotesTextFavoritesActivity.this.animZoomOut);
                    QuotesTextFavoritesActivity.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.2.18
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation) {
                            QuotesTextFavoritesActivity.this.onNext();
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
        this.orientation = getResources().getConfiguration().orientation;
        this.fh = new FontsHelper(this);
        fontList = FontsHelper.getFontList();
        fontBool = FontsHelper.getBool();
        this.selectedFont = FontsHelper.getSelectedFont();
        for (int i = 0; i < fontBool.size(); i++) {
            if (fontBool.get(Integer.valueOf(i)).booleanValue()) {
                selected = i;
            }
        }
        this.toolsHelper = new ToolsHelper(getApplicationContext());
        t = ToolsHelper.getCourants();
        listCourants = ToolsHelper.getList();
        this.db = new DataBaseHelper(this).getReadableDatabase();
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
        this.mPager = (ViewPager) findViewById(R.id.pager);
        initFavoritesAdapter();
        this.mPager = (ViewPager) findViewById(R.id.pager);
        this.fragments.clear();
        for (int j = 0; j < 3; j++) {
            for (int i2 = 0; i2 < this.quotes.size(); i2++) {
                this.fragments.add(new CustomFragment(this, this.quotesId.get(i2).intValue(), this.quotes.get(i2), this.sources.get(i2), this.authors.get(i2), this.authorsId.get(i2).intValue(), this.authorsDetails.get(i2), String.valueOf(this.text.getText()), firstLetterSize, textSize, heightLandscape, this.selectedFont, screensize));
            }
        }
        this.mPagerAdapterFragment = new MyPageAdapterFragment(getSupportFragmentManager(), this.fragments);
        this.mPager.setAdapter(this.mPagerAdapterFragment);
        if (this.oldPos != 0) {
            this.mPager.setCurrentItem(this.oldPos);
        } else {
            this.mPager.setCurrentItem(this.quotes.size());
            this.oldPos = this.quotes.size();
        }
        if (this.quotes.size() == 0) {
            LayoutInflater inflater2 = getLayoutInflater();
            View layout = inflater2.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
            TextView t2 = (TextView) layout.findViewById(R.id.textView1);
            t2.setText("La liste de vos favoris est vide");
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(16, 0, 0);
            toast.setDuration(0);
            toast.setView(layout);
            toast.show();
            this.number.setText(String.valueOf(this.mPager.getCurrentItem()) + " / " + this.quotes.size());
        } else if (this.mPager.getCurrentItem() < this.quotes.size()) {
            this.number.setText(String.valueOf(this.mPager.getCurrentItem() + 1) + " / " + this.quotes.size());
        } else if (this.quotes.size() - 1 < this.mPager.getCurrentItem() && this.mPager.getCurrentItem() < this.quotes.size() * 2) {
            this.number.setText(String.valueOf((this.mPager.getCurrentItem() + 1) - this.quotes.size()) + " / " + this.quotes.size());
        } else if (this.mPager.getCurrentItem() + 1 > this.quotes.size() * 2) {
            this.number.setText(String.valueOf((this.mPager.getCurrentItem() + 1) - (this.quotes.size() * 2)) + " / " + this.quotes.size());
        }
        this.mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.3
            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int arg02) {
                if (arg02 < QuotesTextFavoritesActivity.this.quotes.size()) {
                    QuotesTextFavoritesActivity.this.number.setText(String.valueOf(arg02 + 1) + " / " + QuotesTextFavoritesActivity.this.quotes.size());
                } else if (QuotesTextFavoritesActivity.this.quotes.size() - 1 >= arg02 || arg02 >= QuotesTextFavoritesActivity.this.quotes.size() * 2) {
                    if (arg02 + 1 > QuotesTextFavoritesActivity.this.quotes.size() * 2) {
                        QuotesTextFavoritesActivity.this.number.setText(String.valueOf((arg02 + 1) - (QuotesTextFavoritesActivity.this.quotes.size() * 2)) + " / " + QuotesTextFavoritesActivity.this.quotes.size());
                    }
                } else {
                    QuotesTextFavoritesActivity.this.number.setText(String.valueOf((arg02 + 1) - QuotesTextFavoritesActivity.this.quotes.size()) + " / " + QuotesTextFavoritesActivity.this.quotes.size());
                }
                QuotesTextFavoritesActivity.this.oldPos = arg02;
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

    public int initFavoritesAdapter() {
        this.quotes = new ArrayList<>();
        this.quotesId = new ArrayList<>();
        this.sources = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.authorsDetails = new ArrayList<>();
        this.authorsId = new ArrayList<>();
        this.text.setText("Favoris");
        this.cursorFavorites = this.db.rawQuery("SELECT id_favorite as _id,id_quote From favorite", null);
        this.cursorFavorites.moveToFirst();
        for (int i = 0; i < this.cursorFavorites.getCount(); i++) {
            this.cursorQuote = this.db.rawQuery("SELECT id_quote as _id,quote,id_author,book From quote where id_quote =" + this.cursorFavorites.getString(1), null);
            this.cursorQuote.moveToFirst();
            this.cursorAuthor = this.db.rawQuery("SELECT id_author as _id, name, surname,book,details,id_courant,nb_quotes From author where id_author =" + this.cursorQuote.getString(2), null);
            this.cursorAuthor.moveToFirst();
            this.quotes.add(this.cursorQuote.getString(1));
            this.quotesId.add(Integer.valueOf(Integer.parseInt(this.cursorQuote.getString(0))));
            this.sources.add(this.cursorQuote.getString(3));
            this.authors.add(this.cursorAuthor.getString(1));
            this.authorsId.add(Integer.valueOf(this.cursorAuthor.getString(0)));
            this.authorsDetails.add(this.cursorAuthor.getString(4));
            this.cursorFavorites.moveToNext();
        }
        this.fragments.clear();
        for (int j = 0; j < 3; j++) {
            for (int i2 = 0; i2 < this.quotes.size(); i2++) {
                this.fragments.add(new CustomFragment(this, this.quotesId.get(i2).intValue(), this.quotes.get(i2), this.sources.get(i2), this.authors.get(i2), this.authorsId.get(i2).intValue(), this.authorsDetails.get(i2), String.valueOf(this.text.getText()), firstLetterSize, textSize, heightPortrait, FontsHelper.getSelectedFont(), screensize));
            }
        }
        this.mPagerAdapterFragment = new MyPageAdapterFragment(getSupportFragmentManager(), this.fragments);
        this.mPager.setAdapter(this.mPagerAdapterFragment);
        this.mPager.setCurrentItem(this.quotes.size());
        this.oldPos = this.quotes.size();
        this.on.setText(" / " + this.quotes.size());
        this.number.setText(String.valueOf(this.mPager.getCurrentItem()) + " / " + this.quotes.size());
        return this.quotes.size();
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

    public byte[] fetchSingle(int id) {
        byte[] image = null;
        Cursor queryCursor = this.db.rawQuery("SELECT id_picture as _id,realpicture from picture where id_picture =" + id, null);
        if (queryCursor != null) {
            image = null;
            if (queryCursor.moveToFirst()) {
                image = queryCursor.getBlob(1);
            }
            queryCursor.close();
        }
        return image;
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

    public void onFonts() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        ListView list = (ListView) getLayoutInflater().inflate(R.layout.tools, (ViewGroup) null);
        list.setChoiceMode(1);
        this.adapter = new SingleListAdapter(this, fontList, this.alf, fontBool);
        list.setChoiceMode(1);
        list.setAdapter(this.adapter);
        list.setCacheColorHint(-16777216);
        list.setItemChecked(selected, true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.4
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                for (int i = 0; i < QuotesTextFavoritesActivity.fontBool.size(); i++) {
                    QuotesTextFavoritesActivity.fontBool.put(Integer.valueOf(i), false);
                }
                QuotesTextFavoritesActivity.fontBool.put(Integer.valueOf(arg2), true);
                QuotesTextFavoritesActivity.selected = arg2;
                QuotesTextFavoritesActivity.this.updateDataBaseFont(QuotesTextFavoritesActivity.fontList, QuotesTextFavoritesActivity.fontBool);
                QuotesTextFavoritesActivity.fontList = FontsHelper.getFontList();
                QuotesTextFavoritesActivity.fontBool = FontsHelper.getBool();
                FontsHelper.init();
                QuotesTextFavoritesActivity.this.selectedFont = FontsHelper.getSelectedFont();
                ArrayList<CustomFragment> fragments1 = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    for (int i2 = 0; i2 < QuotesTextFavoritesActivity.this.quotes.size(); i2++) {
                        fragments1.add(new CustomFragment(QuotesTextFavoritesActivity.this, ((Integer) QuotesTextFavoritesActivity.this.quotesId.get(i2)).intValue(), (String) QuotesTextFavoritesActivity.this.quotes.get(i2), (String) QuotesTextFavoritesActivity.this.sources.get(i2), (String) QuotesTextFavoritesActivity.this.authors.get(i2), ((Integer) QuotesTextFavoritesActivity.this.authorsId.get(i2)).intValue(), (String) QuotesTextFavoritesActivity.this.authorsDetails.get(i2), String.valueOf(QuotesTextFavoritesActivity.this.text.getText()), QuotesTextFavoritesActivity.firstLetterSize, QuotesTextFavoritesActivity.textSize, QuotesTextFavoritesActivity.heightPortrait, QuotesTextFavoritesActivity.this.selectedFont, QuotesTextFavoritesActivity.screensize));
                    }
                }
                MyPageAdapterFragment mPagerAdapter1 = new MyPageAdapterFragment(QuotesTextFavoritesActivity.this.getSupportFragmentManager(), fragments1);
                QuotesTextFavoritesActivity.this.mPager.setAdapter(mPagerAdapter1);
                QuotesTextFavoritesActivity.this.mPager.setCurrentItem(QuotesTextFavoritesActivity.this.oldPos);
                QuotesTextFavoritesActivity.this.mPager.getAdapter().notifyDataSetChanged();
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
        adb.setView(list);
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

    public void onMenu() {
        String message = String.valueOf(this.fragments.get(this.mPager.getCurrentItem()).getQuotes().toString()) + System.getProperty("line.separator") + "\n\n" + this.fragments.get(this.mPager.getCurrentItem()).getAuthor().toString() + ",  " + this.fragments.get(this.mPager.getCurrentItem()).getAuthorDetail() + System.getProperty("line.separator") + "\n\n" + this.fragments.get(this.mPager.getCurrentItem()).getSources();
        Intent email = new Intent("android.intent.action.SEND");
        email.putExtra("android.intent.extra.EMAIL", new String[]{""});
        email.putExtra("android.intent.extra.SUBJECT", "Citations de OneLittleAngel");
        email.putExtra("android.intent.extra.TEXT", message);
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "choisissez :"));
    }

    public void onPhone() {
        String toSpeak = String.valueOf(this.fragments.get(this.mPager.getCurrentItem()).getAuthor().toString()) + " ... " + this.fragments.get(this.mPager.getCurrentItem()).getAuthorDetail().toString() + " ... " + this.fragments.get(this.mPager.getCurrentItem()).getQuotes().toString();
        this.tts.speak(toSpeak, 0, null);
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
                    fragments1.add(new CustomFragment(this, this.quotesId.get(i).intValue(), this.quotes.get(i), this.sources.get(i), this.authors.get(i), this.authorsId.get(i).intValue(), this.authorsDetails.get(i), String.valueOf(this.text.getText()), firstLetterSize, textSize, heightPortrait, FontsHelper.getSelectedFont(), screensize));
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
                LayoutInflater inflater2 = getLayoutInflater();
                View layout = inflater2.inflate(R.layout.toast, (ViewGroup) v.findViewById(R.id.custom_toast_layout_id));
                TextView t2 = (TextView) layout.findViewById(R.id.textView1);
                t2.setText("Vous avez atteind la taille minimum");
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(16, 0, 0);
                toast.setDuration(0);
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
                    fragments12.add(new CustomFragment(this, this.quotesId.get(i2).intValue(), this.quotes.get(i2), this.sources.get(i2), this.authors.get(i2), this.authorsId.get(i2).intValue(), this.authorsDetails.get(i2), String.valueOf(this.text.getText()), firstLetterSize, textSize, heightPortrait, FontsHelper.getSelectedFont(), screensize));
                }
            }
            MyPageAdapterFragment mPagerAdapter12 = new MyPageAdapterFragment(getSupportFragmentManager(), fragments12);
            this.mPager.setAdapter(mPagerAdapter12);
            this.mPager.setCurrentItem(this.oldPos);
            option = true;
        }
    }

    public void onSearch(View v) {
        int i;
        LayoutInflater inflater2 = getLayoutInflater();
        View layout = inflater2.inflate(R.layout.toast, (ViewGroup) v.findViewById(R.id.custom_toast_layout_id));
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
            toast.setDuration(0);
            toast.setView(layout);
            toast.show();
            this.edit.setText("");
            return;
        }
        if (i == -1) {
            Toast toast2 = new Toast(getApplicationContext());
            toast2.setGravity(16, 0, 0);
            toast2.setDuration(0);
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
            return;
        }
        if (this.quotes.size() - 1 < this.mPager.getCurrentItem() && this.mPager.getCurrentItem() < this.quotes.size() * 2) {
            this.number.setText(String.valueOf((this.mPager.getCurrentItem() + 1) - this.quotes.size()) + " / " + this.quotes.size());
        } else if (this.mPager.getCurrentItem() + 1 > this.quotes.size() * 2) {
            this.number.setText(String.valueOf((this.mPager.getCurrentItem() + 1) - (this.quotes.size() * 2)) + " / " + this.quotes.size());
        }
    }

    public void onTools() {
        ListView list = (ListView) getLayoutInflater().inflate(R.layout.tools, (ViewGroup) null);
        list.setChoiceMode(1);
        this.adapter = new ToolsListAdapter(getApplicationContext(), listCourants, t);
        list.setAdapter(this.adapter);
        list.setCacheColorHint(-16777216);
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        TextView title = new TextView(this);
        title.setText("Ajout et Suppression de courants");
        title.setBackgroundColor(0);
        title.setGravity(17);
        title.setTextColor(-1);
        if ((getResources().getConfiguration().screenLayout & 15) == 3) {
            title.setTextSize(38.0f);
        } else {
            title.setTextSize(20.0f);
        }
        adb.setCustomTitle(title);
        adb.setView(list);
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                QuotesTextFavoritesActivity.t = ToolsListAdapter.getRadioTable();
                QuotesTextFavoritesActivity.listCourants = ToolsListAdapter.getRadioString();
                QuotesTextFavoritesActivity.this.updateDataBase(QuotesTextFavoritesActivity.listCourants, QuotesTextFavoritesActivity.t);
            }
        });
        adb.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDataBase(Map<Integer, String> listCourants2, Map<Integer, Boolean> t2) {
        String bool;
        this.db = new DataBaseHelper(this).getWritableDatabase();
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

    public void onCourant(View view) {
        Intent i = new Intent(this, (Class<?>) QuotesTextActivity.class);
        String s = view.getTag().toString();
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
        startActivity(i);
        finish();
    }

    public void onAuthor(View view) {
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
            return;
        }
        if (this.quotes.size() - 1 < this.mPager.getCurrentItem() && this.mPager.getCurrentItem() < this.quotes.size() * 2) {
            this.number.setText(String.valueOf((this.mPager.getCurrentItem() + 1) - this.quotes.size()) + " / " + this.quotes.size());
        } else if (this.mPager.getCurrentItem() + 1 > this.quotes.size() * 2) {
            this.number.setText(String.valueOf((this.mPager.getCurrentItem() + 1) - (this.quotes.size() * 2)) + " / " + this.quotes.size());
        }
    }

    public void onNext() {
        this.mPager.setCurrentItem(this.mPager.getCurrentItem() + 1, true);
        if (this.mPager.getCurrentItem() < this.quotes.size()) {
            this.number.setText(String.valueOf(this.mPager.getCurrentItem() + 1) + " / " + this.quotes.size());
            return;
        }
        if (this.quotes.size() - 1 < this.mPager.getCurrentItem() && this.mPager.getCurrentItem() < this.quotes.size() * 2) {
            this.number.setText(String.valueOf((this.mPager.getCurrentItem() + 1) - this.quotes.size()) + " / " + this.quotes.size());
        } else if (this.mPager.getCurrentItem() + 1 > this.quotes.size() * 2) {
            this.number.setText(String.valueOf((this.mPager.getCurrentItem() + 1) - (this.quotes.size() * 2)) + " / " + this.quotes.size());
        }
    }

    public void deleteFavorite() {
        final int i;
        this.db = new DataBaseHelper(this).getWritableDatabase();
        if (this.fragments.size() == 0) {
            i = 0;
        } else {
            i = this.fragments.get(this.mPager.getCurrentItem()).getQuoteId();
        }
        TextView t2 = new TextView(this);
        t2.setText("supprimer cette citation des favoris ?");
        t2.setTextColor(-1);
        t2.setTextSize(25.0f);
        if (i != 0) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setView(t2);
            adb.setPositiveButton("Oui", new DialogInterface.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.favorites.QuotesTextFavoritesActivity.6
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    QuotesTextFavoritesActivity.this.oldPos = QuotesTextFavoritesActivity.this.mPager.getCurrentItem();
                    String sql = "DELETE FROM favorite where id_quote=" + i;
                    QuotesTextFavoritesActivity.this.db.execSQL(sql);
                    QuotesTextFavoritesActivity.this.initFavoritesAdapter();
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
    }
}
