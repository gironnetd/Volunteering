package com.sc.en.islam.layers.mvp.tablecontents.fragments.homepage.adapters.listadapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sc.en.islam.OnelittleAngelApplication;
import com.sc.en.islam.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.islam.layers.mvp.common.utils.Constants;
import com.sc.en.islam.layers.mvp.biography.BiographyActivity;
import com.sc.en.islam.layers.mvp.common.animations.ActivityAnimator;
import com.sc.en.islam.layers.mvp.common.animations.listviews.ArrayAdapter;
import com.sc.en.islam.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.en.islam.layers.mvp.common.customs.viewpagers.ViewPagerNative;
import com.sc.en.islam.layers.mvp.common.listeners.cardviews.CardViewNativeGestureListener;
import com.sc.en.islam.layers.mvp.contents.ContentsActivity;
import com.sc.en.islam.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.islam.layers.mvp.tablecontents.fragments.homepage.fragments.HomePageFragment;
import com.sc.en.islam.layers.mvp.tablecontents.fragments.homepage.fragments.HomePagePresenterInterface;
import com.sc.en.islam.R;
import com.sc.en.islam.layers.mvp.common.customs.textviews.ContentTextViewNativeBiography;
import com.sc.en.islam.layers.mvp.tablecontents.fragments.homepage.views.cardviews.HomePageCardView;
import com.sc.en.islam.layers.mvp.tablecontents.fragments.homepage.views.listviews.HomePageListView;

public class HomePageListAdapter extends ArrayAdapter<String> implements ContentTextViewNativeBiography.Callbacks {

  private int homePageListviewHeight;
  private int mRowLayoutId = R.layout.homepage_native_list_card_layout;
  private final SharedPreferences settings;
  SharedPreferences.Editor editor;
  private final Activity context;
  private final HomePagePresenterInterface presenter;
  private Callbacks mCallbacks;
  public static boolean isInit = false;
  private boolean isViewNull;
  private Typeface typeface;
  public SparseArray<View> views;
  public final SparseArray<ViewHolder> holders;
  private final ViewPagerNative viewPagerNative;
  public SoundPool soundPool;

  private float size;
  private int widthMeasureSpec;
  private int heightMeasureSpec;

  public HomePageListAdapter(Activity context, HomePageFragment fragment, HomePagePresenterInterface presenter) {
    this.context = context;
    this.presenter = presenter;
    //if(presenter.getQuoteOfDay() == null) presenter.updateHomePage();
    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    //editor = settings.edit();
    views = new SparseArray<>();
    holders = new SparseArray<>();
    FrameLayout rlContainer = (FrameLayout) context.findViewById(R.id.fl_container);
    viewPagerNative = (ViewPagerNative) context.findViewById(R.id.viewpager);
  }

  public void setCallbacks(Callbacks listener) {
    mCallbacks = listener;
  }

  @Override
  public int getCount() {
    return 3;
  }

  @NonNull
  @Override
  public String getItem(int position) {
    return super.getItem(position);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {



    View view = views.get(position);
    final ViewHolder holder;

    if (convertView == null) {

      ((TableContentsActivity) context).viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

          if (((TableContentsActivity) context).viewPager.getMeasuredHeight() < 10800)
            ((TableContentsActivity) context).viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    10800 + ((int) OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) /*+ TableContentsActivity.headerHeight*//*/*16308*/));

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((TableContentsActivity) context).viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
          } else {
            ((TableContentsActivity) context).viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
          }
        }
      });

      //  Log.v("test", "view == null");
      LayoutInflater inflater =
              (LayoutInflater) OnelittleAngelApplication.instance.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(this.mRowLayoutId, parent, false);

      holder = new ViewHolder();
      holder.contentView = convertView;
      holder.llHomePage = (LinearLayout) convertView.findViewById(R.id.ll_home_page);
      holder.homePageCardView = (HomePageCardView) convertView.findViewById(R.id.mCardViewHomeNative_list);
      holder.mTitle = (TextViewNative) convertView.findViewById(R.id.homepage_card_title);
      holder.name = (TextViewNative) convertView.findViewById(R.id.name);
      holder.biographyOfDay = (ContentTextViewNativeBiography) convertView.findViewById(R.id.biography_of_day);
      holder.biographyOfDay.setCallbacks(this);
      holder.quoteOfDay = (ContentTextViewNativeBiography) convertView.findViewById(R.id.quote_of_day);
      holder.quoteOfDay.setCallbacks(this);

      holder.pictureOfDay = (ImageView) convertView.findViewById(R.id.picture_of_day);
      holder.link = (TextViewNative) convertView.findViewById(R.id.link);
      holder.link.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));

      DisplayMetrics displaymetrics = new DisplayMetrics();
      context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
      int height = displaymetrics.heightPixels;
      int width = displaymetrics.widthPixels;
      widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
      heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

//      if(presenter == null) presenter = fragment.presenter;


      if (position == 0) {
        convertView.setVisibility(View.VISIBLE);

        holder.mTitle.setText(OnelittleAngelApplication.instance.getResources().getString(R.string.quotes_of_the_day));
        holder.biographyOfDay.setVisibility(View.GONE);
        holder.name.setVisibility(View.GONE);
        //  holder.quoteOfDay.setText("    " + presenter.getQuoteOfDay().getQuote());
        holder.quoteOfDay.append("  " + presenter.getQuoteOfDay().getQuote());
        size = holder.quoteOfDay.getTextSize();

        holder.quoteOfDay.setOnTouchListener(new CardViewNativeGestureListener(context) {

          @Override
          public void onDoubleTaped() {
            super.onDoubleTaped();
            if (!presenter.getQuoteOfDay().isFavorites()) ((TableContentsActivity) context).addRemoveFromDialog.show();
          }

          @Override
          public void onLongPressed() {
            super.onLongPressed();
            if (!presenter.getQuoteOfDay().isFavorites()) ((TableContentsActivity) context).addRemoveFromDialog.show();
          }

          @Override
          public void onSingleTapConfirm() {
            super.onSingleTapConfirm();

            if (settings.getBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, false)) {
              holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
            } else {
              holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
            }

            settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.quotes_of_the_day)).apply();
            //editor.commit();
            Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
            mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
            mainIntent.putExtra("fromActivity", "TableContentsActivity");
            mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, false));
            mainIntent.putExtra(holder.type, settings.getString(Constants.QUOTE_OF_DAY_NAME, ""));

            OnelittleAngelApplication.instance.startActivity(mainIntent);
            try {
              ActivityAnimator anim = new ActivityAnimator();
              anim.fadeAnimation(context);
            } catch (Exception ignored) {
            }
            context.finish();
          }
        });

        holder.link.setText(presenter.getQuoteOfDay().getAuthor() != null ? "[ " + OnelittleAngelApplication.instance.getResources().getString(R.string.more_quotes) + (presenter.getQuoteOfDay().getAuthor().getMcc1() != null ? " " +presenter.getQuoteOfDay().getAuthor().getMcc1() : "") + " " + presenter.getQuoteOfDay().getAuthor().getName() + " ] " : " [ " + OnelittleAngelApplication.instance.getResources().getString(R.string.more_quotes) + (presenter.getQuoteOfDay().getBook().getMcc1() != null ? " " + presenter.getQuoteOfDay().getBook().getMcc1() : "") + " " + presenter.getQuoteOfDay().getBook().getName() + " ] ");

        holder.link.setOnTouchListener(new CardViewNativeGestureListener(context) {

          @Override
          public void onLongPressed() {

            //  super.onLongPressed();
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);

            soundPool.setOnLoadCompleteListener((soundPool116, sampleId, status) -> soundPool116.play(soundID, 1f, 1f, 1, 0, 1f));
            if (settings.getBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, false)) {
              holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
            } else {
              holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
            }

            settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.quotes_of_the_day)).apply();
            //editor.commit();
            Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
            mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
            mainIntent.putExtra("fromActivity", "TableContentsActivity");
            mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, false));
            mainIntent.putExtra(holder.type, settings.getString(Constants.QUOTE_OF_DAY_NAME, ""));

            OnelittleAngelApplication.instance.startActivity(mainIntent);
            try {
              ActivityAnimator anim = new ActivityAnimator();
              anim.fadeAnimation(context);
            } catch (Exception ignored) {
            }
            context.finish();

          }

          @Override
          public void onSingleTapConfirm() {
            //  super.onSingleTapConfirm();
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);

            soundPool.setOnLoadCompleteListener((soundPool115, sampleId, status) -> soundPool115.play(soundID, 1f, 1f, 1, 0, 1f));
            if (settings.getBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, false)) {
              holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
            } else {
              holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
            }

            settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.quotes_of_the_day)).apply();
            //editor.commit();
            Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
            mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
            mainIntent.putExtra("fromActivity", "TableContentsActivity");
            mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, false));
            mainIntent.putExtra(holder.type, settings.getString(Constants.QUOTE_OF_DAY_NAME, ""));

            OnelittleAngelApplication.instance.startActivity(mainIntent);
            try {
              ActivityAnimator anim = new ActivityAnimator();
              anim.fadeAnimation(context);
            } catch (Exception ignored) {
            }
            context.finish();

          }

          @Override
          public void onDoubleTaped() {
            //  super.onDoubleTaped();
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);

            soundPool.setOnLoadCompleteListener((soundPool114, sampleId, status) -> soundPool114.play(soundID, 1f, 1f, 1, 0, 1f));
            if (settings.getBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, false)) {
              holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
            } else {
              holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
            }

            settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.quotes_of_the_day)).apply();
            //editor.commit();
            Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
            mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
            mainIntent.putExtra("fromActivity", "TableContentsActivity");
            mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, false));
            mainIntent.putExtra(holder.type, settings.getString(Constants.QUOTE_OF_DAY_NAME, ""));

            OnelittleAngelApplication.instance.startActivity(mainIntent);
            try {
              ActivityAnimator anim = new ActivityAnimator();
              anim.fadeAnimation(context);
            } catch (Exception ignored) {
            }
            context.finish();

          }
        });

        holder.llHomePage.measure(widthMeasureSpec, heightMeasureSpec);
        homePageListviewHeight += convertView.getMeasuredHeight();
      }

      if (position == 1) {
        if (presenter.getPictureOfDay() == null) {
          convertView = inflater.inflate(R.layout.view_empty, parent, false);
          return convertView;
        } else {
          convertView.setVisibility(View.VISIBLE);

          holder.mTitle.setText(OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day));
//          holder.name.setText(String.format("%s ", presenter.getNamePictureOfDay()));
          holder.pictureOfDay.setVisibility(View.VISIBLE);
          holder.quoteOfDay.setVisibility(View.GONE);
          holder.biographyOfDay.setVisibility(View.GONE);
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

              holder.pictureOfDay.setBackground(new BitmapDrawable(OnelittleAngelApplication.instance.getResources(), scaleBitmap(presenter.getPictureOfDay(), width, height / 2)));
            } else {
              holder.pictureOfDay.setBackground(new BitmapDrawable(OnelittleAngelApplication.instance.getResources(), scaleBitmap(presenter.getPictureOfDay(), width / 2, height)));
            }
          } else {
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
              holder.pictureOfDay.setBackgroundDrawable(new BitmapDrawable(OnelittleAngelApplication.instance.getResources(), scaleBitmap(presenter.getPictureOfDay(), width, height / 2)));
            } else {
              holder.pictureOfDay.setBackgroundDrawable(new BitmapDrawable(OnelittleAngelApplication.instance.getResources(), scaleBitmap(presenter.getPictureOfDay(), width / 2, height)));
            }
          }

          if (presenter.getAuthorPictureOfDay() != null  && presenter.getAuthorPictureOfDay().getName() != null) {

            holder.name.setText(String.format("%s ", presenter.getAuthorPictureOfDay().getName()));

            if (presenter.getAuthorPictureOfDay().getQuotes().size() != 0) {
              holder.link.setText(" [ " + OnelittleAngelApplication.instance.getResources().getString(R.string.quotes_of) +  (presenter.getAuthorPictureOfDay().getMcc1() != null ? " " + presenter.getAuthorPictureOfDay().getMcc1() : "") + " " + presenter.getAuthorPictureOfDay().getName() + " ] ");


              holder.link.setOnTouchListener(new CardViewNativeGestureListener(context) {

                @Override
                public void onLongPressed() {
                  soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                  int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);

                  soundPool.setOnLoadCompleteListener((soundPool113, sampleId, status) -> soundPool113.play(soundID, 1f, 1f, 1, 0, 1f));

                  if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
                  } else {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
                  }
                  settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day)).apply();
                  //editor.commit();
                  Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
                  mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                  mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
                  mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  mainIntent.putExtra("fromActivity", "TableContentsActivity");
                  mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false));

                  //  Log.v("gggg", settings.getString(Constants.PICTURE_OF_DAY_NAME, "") + "i");
                  mainIntent.putExtra(holder.type, settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
                  OnelittleAngelApplication.instance.startActivity(mainIntent);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onSingleTapConfirm() {
                  //  super.onSingleTapConfirm();
                  soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                  int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);

                  soundPool.setOnLoadCompleteListener((soundPool112, sampleId, status) -> soundPool112.play(soundID, 1f, 1f, 1, 0, 1f));

                  if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
                  } else {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
                  }
                  settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day)).apply();
                  //editor.commit();
                  Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
                  mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                  mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
                  mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  mainIntent.putExtra("fromActivity", "TableContentsActivity");
                  mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false));

                  //  Log.v("gggg", settings.getString(Constants.PICTURE_OF_DAY_NAME, "") + "i");
                  mainIntent.putExtra(holder.type, settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
                  OnelittleAngelApplication.instance.startActivity(mainIntent);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onDoubleTaped() {
                  //  super.onDoubleTaped();
                  soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                  int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);

                  soundPool.setOnLoadCompleteListener((soundPool111, sampleId, status) -> soundPool111.play(soundID, 1f, 1f, 1, 0, 1f));

                  if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
                  } else {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
                  }
                  settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day)).apply();
                  //editor.commit();
                  Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
                  mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                  mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
                  mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  mainIntent.putExtra("fromActivity", "TableContentsActivity");
                  mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false));

                  //  Log.v("gggg", settings.getString(Constants.PICTURE_OF_DAY_NAME, "") + "i");
                  mainIntent.putExtra(holder.type, settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
                  OnelittleAngelApplication.instance.startActivity(mainIntent);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }
              });

              holder.pictureOfDay.setOnTouchListener(new CardViewNativeGestureListener(context) {

                @Override
                public void onLongPressed() {
                  //  super.onLongPressed();
                  if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
                  } else {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
                  }

                  settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day)).apply();
                  //editor.commit();
                  Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
                  mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                  mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
                  mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  mainIntent.putExtra("fromActivity", "TableContentsActivity");
                  mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false));

                  //  Log.v("gggg", settings.getString(Constants.PICTURE_OF_DAY_NAME, "") + "i");
                  mainIntent.putExtra(holder.type, settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
                  OnelittleAngelApplication.instance.startActivity(mainIntent);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onSingleTapConfirm() {
                  //  super.onSingleTapConfirm();
                  if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
                  } else {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
                  }

                  settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day)).apply();
                  //editor.commit();
                  Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
                  mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                  mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
                  mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  mainIntent.putExtra("fromActivity", "TableContentsActivity");
                  mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false));

                  //  Log.v("gggg", settings.getString(Constants.PICTURE_OF_DAY_NAME, "") + "i");
                  mainIntent.putExtra(holder.type, settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
                  OnelittleAngelApplication.instance.startActivity(mainIntent);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onDoubleTaped() {
                  //  super.onDoubleTaped();
                  if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
                  } else {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
                  }

                  settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day)).apply();
                  //editor.commit();
                  Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
                  mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                  mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
                  mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  mainIntent.putExtra("fromActivity", "TableContentsActivity");
                  mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false));

                  //  Log.v("gggg", settings.getString(Constants.PICTURE_OF_DAY_NAME, "") + "i");
                  mainIntent.putExtra(holder.type, settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
                  OnelittleAngelApplication.instance.startActivity(mainIntent);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }
              });
            } else if (presenter.getAuthorPictureOfDay().getPresentation() != null) {
              holder.link.setText(" [ " + OnelittleAngelApplication.instance.getResources().getString(R.string.biography_of) + (presenter.getAuthorPictureOfDay().getMcc1() != null ? " " + presenter.getAuthorPictureOfDay().getMcc1() : "") + " " + presenter.getAuthorPictureOfDay().getName() + " ] ");


              holder.link.setOnTouchListener(new CardViewNativeGestureListener(context) {

                @Override
                public void onLongPressed() {
                  //  super.onLongPressed();
                  soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                  int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
                  soundPool.setOnLoadCompleteListener((soundPool110, sampleId, status) -> soundPool110.play(soundID, 1f, 1f, 1, 0, 1f));

                  Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
                  iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getAuthorPictureOfDay().getName() + " ");

                  iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromActivity", "TableContentsActivity");
                  iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

                  OnelittleAngelApplication.instance.startActivity(iBiography);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onSingleTapConfirm() {
                  //  super.onSingleTapConfirm();
                  soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                  int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
                  soundPool.setOnLoadCompleteListener((soundPool19, sampleId, status) -> soundPool19.play(soundID, 1f, 1f, 1, 0, 1f));

                  Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
                  iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getAuthorPictureOfDay().getName() + " ");

                  iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromActivity", "TableContentsActivity");
                  iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

                  OnelittleAngelApplication.instance.startActivity(iBiography);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onDoubleTaped() {
                  //  super.onDoubleTaped();
                  soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                  int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
                  soundPool.setOnLoadCompleteListener((soundPool18, sampleId, status) -> soundPool18.play(soundID, 1f, 1f, 1, 0, 1f));

                  Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
                  iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getAuthorPictureOfDay().getName() + " ");

                  iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromActivity", "TableContentsActivity");
                  iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

                  OnelittleAngelApplication.instance.startActivity(iBiography);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }
              });

              holder.pictureOfDay.setOnTouchListener(new CardViewNativeGestureListener(context) {

                @Override
                public void onLongPressed() {
                  //  super.onLongPressed();
                  Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
                  iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getAuthorPictureOfDay().getName() + " ");

                  iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromActivity", "TableContentsActivity");
                  iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

                  OnelittleAngelApplication.instance.startActivity(iBiography);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onSingleTapConfirm() {
                  //  super.onSingleTapConfirm();
                  Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
                  iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getAuthorPictureOfDay().getName() + " ");

                  iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromActivity", "TableContentsActivity");
                  iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

                  OnelittleAngelApplication.instance.startActivity(iBiography);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onDoubleTaped() {
                  //  super.onDoubleTaped();
                  Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
                  iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getAuthorPictureOfDay().getName() + " ");

                  iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromActivity", "TableContentsActivity");
                  iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

                  OnelittleAngelApplication.instance.startActivity(iBiography);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }
              });
            } else {
              holder.link.setVisibility(View.INVISIBLE);
            }
          }

          if (presenter.getBookPictureOfDay().getName() != null) {

            holder.name.setText(String.format("%s ", presenter.getBookPictureOfDay().getName()));

            if (presenter.getBookPictureOfDay().getQuotes().size() != 0) {
              holder.link.setText(" [ " + OnelittleAngelApplication.instance.getResources().getString(R.string.quotes_of) + (presenter.getBookPictureOfDay().getMcc1() != null ? " " + presenter.getBookPictureOfDay().getMcc1() : "") + " " + presenter.getBookPictureOfDay().getName() + " ] ");

              holder.link.setOnTouchListener(new CardViewNativeGestureListener(context) {

                @Override
                public void onLongPressed() {
                  //  super.onLongPressed();
                  soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                  int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
                  soundPool.setOnLoadCompleteListener((soundPool17, sampleId, status) -> soundPool17.play(soundID, 1f, 1f, 1, 0, 1f));

                  if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
                  } else {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
                  }
                  settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day)).apply();
                  //editor.commit();
                  Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
                  mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                  mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
                  mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  mainIntent.putExtra("fromActivity", "TableContentsActivity");
                  mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false));

                  //  Log.v("gggg", settings.getString(Constants.PICTURE_OF_DAY_NAME, "") + "i");
                  mainIntent.putExtra(holder.type, settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
                  OnelittleAngelApplication.instance.startActivity(mainIntent);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onSingleTapConfirm() {
                  //  super.onSingleTapConfirm();
                  soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                  int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
                  soundPool.setOnLoadCompleteListener((soundPool16, sampleId, status) -> soundPool16.play(soundID, 1f, 1f, 1, 0, 1f));

                  if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
                  } else {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
                  }
                  settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day)).apply();
                  //editor.commit();
                  Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
                  mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                  mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
                  mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  mainIntent.putExtra("fromActivity", "TableContentsActivity");
                  mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false));

                  //  Log.v("gggg", settings.getString(Constants.PICTURE_OF_DAY_NAME, "") + "i");
                  mainIntent.putExtra(holder.type, settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
                  OnelittleAngelApplication.instance.startActivity(mainIntent);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onDoubleTaped() {
                  //  super.onDoubleTaped();
                  soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                  int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
                  soundPool.setOnLoadCompleteListener((soundPool15, sampleId, status) -> soundPool15.play(soundID, 1f, 1f, 1, 0, 1f));

                  if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
                  } else {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
                  }
                  settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day)).apply();
                  //editor.commit();
                  Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
                  mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                  mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
                  mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  mainIntent.putExtra("fromActivity", "TableContentsActivity");
                  mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false));

                  //  Log.v("gggg", settings.getString(Constants.PICTURE_OF_DAY_NAME, "") + "i");
                  mainIntent.putExtra(holder.type, settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
                  OnelittleAngelApplication.instance.startActivity(mainIntent);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }
              });

              holder.pictureOfDay.setOnTouchListener(new CardViewNativeGestureListener(context) {

                @Override
                public void onLongPressed() {
                  //  super.onLongPressed();
                  if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
                  } else {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
                  }

                  settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day)).apply();
                  //editor.commit();
                  Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
                  mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                  mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
                  mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  mainIntent.putExtra("fromActivity", "TableContentsActivity");
                  mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false));

                  //  Log.v("gggg", settings.getString(Constants.PICTURE_OF_DAY_NAME, "") + "i");
                  mainIntent.putExtra(holder.type, settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
                  OnelittleAngelApplication.instance.startActivity(mainIntent);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onSingleTapConfirm() {
                  //  super.onSingleTapConfirm();
                  if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
                  } else {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
                  }

                  settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day)).apply();
                  //editor.commit();
                  Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
                  mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                  mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
                  mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  mainIntent.putExtra("fromActivity", "TableContentsActivity");
                  mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false));

                  //  Log.v("gggg", settings.getString(Constants.PICTURE_OF_DAY_NAME, "") + "i");
                  mainIntent.putExtra(holder.type, settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
                  OnelittleAngelApplication.instance.startActivity(mainIntent);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onDoubleTaped() {
                  //  super.onDoubleTaped();
                  if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.authors);
                  } else {
                    holder.type = OnelittleAngelApplication.instance.getResources().getString(R.string.books);
                  }

                  settings.edit().putString(Constants.CALL_FROM_HOMEPAGE, OnelittleAngelApplication.instance.getResources().getString(R.string.picture_of_the_day)).apply();
                  //editor.commit();
                  Intent mainIntent = new Intent(OnelittleAngelApplication.instance.getBaseContext(), ContentsActivity.class);
                  mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                  mainIntent.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), holder.type);
                  mainIntent.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  mainIntent.putExtra("fromActivity", "TableContentsActivity");
                  mainIntent.putExtra("isAuthor", settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false));

                  //  Log.v("gggg", settings.getString(Constants.PICTURE_OF_DAY_NAME, "") + "i");
                  mainIntent.putExtra(holder.type, settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
                  OnelittleAngelApplication.instance.startActivity(mainIntent);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }
              });
            } else if (presenter.getBookPictureOfDay().getPresentation() != null) {
              holder.link.setText(" [ " + OnelittleAngelApplication.instance.getResources().getString(R.string.history_of) + (presenter.getBookPictureOfDay().getMcc1() != null ? " " + presenter.getBookPictureOfDay().getMcc1() : "") + " " + presenter.getBookPictureOfDay().getName() + " ] ");


              holder.link.setOnTouchListener(new CardViewNativeGestureListener(context) {

                @Override
                public void onLongPressed() {
                  //  super.onLongPressed();
                  soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                  int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
                  soundPool.setOnLoadCompleteListener((soundPool14, sampleId, status) -> soundPool14.play(soundID, 1f, 1f, 1, 0, 1f));

                  Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
                  iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getBookPictureOfDay().getName() + " ");

                  iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromActivity", "TableContentsActivity");
                  iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

                  OnelittleAngelApplication.instance.startActivity(iBiography);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onSingleTapConfirm() {
                  //  super.onSingleTapConfirm();
                  soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                  int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
                  soundPool.setOnLoadCompleteListener((soundPool13, sampleId, status) -> soundPool13.play(soundID, 1f, 1f, 1, 0, 1f));

                  Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
                  iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getBookPictureOfDay().getName() + " ");

                  iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromActivity", "TableContentsActivity");
                  iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

                  OnelittleAngelApplication.instance.startActivity(iBiography);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onDoubleTaped() {
                  //  super.onDoubleTaped();
                  soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                  int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
                  soundPool.setOnLoadCompleteListener((soundPool12, sampleId, status) -> soundPool12.play(soundID, 1f, 1f, 1, 0, 1f));

                  Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
                  iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getBookPictureOfDay().getName() + " ");

                  iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromActivity", "TableContentsActivity");
                  iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

                  OnelittleAngelApplication.instance.startActivity(iBiography);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }
              });

              holder.pictureOfDay.setOnTouchListener(new CardViewNativeGestureListener(context) {

                @Override
                public void onLongPressed() {
                  //  super.onLongPressed();
                  Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
                  iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getBookPictureOfDay().getName() + " ");

                  iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromActivity", "TableContentsActivity");
                  iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

                  OnelittleAngelApplication.instance.startActivity(iBiography);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onSingleTapConfirm() {
                  //  super.onSingleTapConfirm();
                  Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
                  iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getBookPictureOfDay().getName() + " ");

                  iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromActivity", "TableContentsActivity");
                  iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

                  OnelittleAngelApplication.instance.startActivity(iBiography);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }

                @Override
                public void onDoubleTaped() {
                  //  super.onDoubleTaped();
                  Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
                  iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getBookPictureOfDay().getName() + " ");

                  iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
                  iBiography.putExtra("fromActivity", "TableContentsActivity");
                  iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

                  OnelittleAngelApplication.instance.startActivity(iBiography);
                  try {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.fadeAnimation(context);
                  } catch (Exception ignored) {
                  }
                  context.finish();

                }
              });
            } else {
              holder.link.setVisibility(View.INVISIBLE);
            }
          }

          holder.llHomePage.measure(widthMeasureSpec, heightMeasureSpec);
          homePageListviewHeight += convertView.getMeasuredHeight();
        }
      }

      if (position == 2) {
        convertView.setVisibility(View.VISIBLE);
        holder.mTitle.setText(OnelittleAngelApplication.instance.getResources().getString(R.string.biography_of_the_day));
        holder.quoteOfDay.setVisibility(View.GONE);

        if (presenter.getAuthorPresentationOfDay() != null) {
          holder.name.setText(String.format("%s ", presenter.getAuthorPresentationOfDay().getName()));

        } else {
          holder.name.setText(String.format("%s ", presenter.getBookPresentationOfDay().getName()));
        }

        if (presenter.getAuthorPresentationOfDay() != null) {

          holder.biographyOfDay.setOnTouchListener(new CardViewNativeGestureListener(context) {

            @Override
            public void onLongPressed() {
              super.onSingleTapConfirm();

              //  super.onLongPressed();

              Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
              iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getAuthorPresentationOfDay().getName() + "");

              iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromActivity", "TableContentsActivity");
              iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

              OnelittleAngelApplication.instance.startActivity(iBiography);
              try {
                ActivityAnimator anim = new ActivityAnimator();
                anim.fadeAnimation(context);
              } catch (Exception ignored) {
              }
              context.finish();
            }

            @Override
            public void onDoubleTaped() {

              super.onSingleTapConfirm();

              //  super.onLongPressed();

              Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
              iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getAuthorPresentationOfDay().getName() + "");

              iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromActivity", "TableContentsActivity");
              iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

              OnelittleAngelApplication.instance.startActivity(iBiography);
              try {
                ActivityAnimator anim = new ActivityAnimator();
                anim.fadeAnimation(context);
              } catch (Exception ignored) {
              }
              context.finish();

            }

            @Override
            public void onSingleTapConfirm() {
              super.onSingleTapConfirm();

              //  super.onLongPressed();

              Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
              iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getAuthorPresentationOfDay().getName() + "");

              iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromActivity", "TableContentsActivity");
              iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

              OnelittleAngelApplication.instance.startActivity(iBiography);
              try {
                ActivityAnimator anim = new ActivityAnimator();
                anim.fadeAnimation(context);
              } catch (Exception ignored) {
              }
              context.finish();

            }
          });

          holder.link.setText(" [ " + OnelittleAngelApplication.instance.getResources().getString(R.string.biography_of) + (presenter.getAuthorPresentationOfDay().getMcc1() != null ? " " + presenter.getAuthorPresentationOfDay().getMcc1() : "") + " " + presenter.getAuthorPresentationOfDay().getName() + " ] ");


          //  holder.link.setText(" [ " + OnelittleAngelApplication.instance.getResources().getString(R.string.biography_of) + presenter.getAuthorPresentationOfDay().getMcc1() + " " + presenter.getAuthorPresentationOfDay().getName() + " ] ");

          holder.link.setOnTouchListener(new CardViewNativeGestureListener(context) {

            @Override
            public void onLongPressed() {
              //  super.onLongPressed();
              soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
              int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
              soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> soundPool1.play(soundID, 1f, 1f, 1, 0, 1f));

              Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
              iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getAuthorPresentationOfDay().getName() + "");

              iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromActivity", "TableContentsActivity");
              iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

              OnelittleAngelApplication.instance.startActivity(iBiography);
              try {
                ActivityAnimator anim = new ActivityAnimator();
                anim.fadeAnimation(context);
              } catch (Exception ignored) {
              }
              context.finish();

            }

            @Override
            public void onSingleTapConfirm() {
              //  super.onSingleTapConfirm();
              soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
              int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
              soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> soundPool1.play(soundID, 1f, 1f, 1, 0, 1f));

              Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
              iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getAuthorPresentationOfDay().getName() + "");

              iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromActivity", "TableContentsActivity");
              iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

              OnelittleAngelApplication.instance.startActivity(iBiography);
              try {
                ActivityAnimator anim = new ActivityAnimator();
                anim.fadeAnimation(context);
              } catch (Exception ignored) {
              }
              context.finish();

            }

            @Override
            public void onDoubleTaped() {
              //  super.onDoubleTaped();
              soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
              int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
              soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> soundPool1.play(soundID, 1f, 1f, 1, 0, 1f));

              Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
              iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getAuthorPresentationOfDay().getName() + "");

              iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromActivity", "TableContentsActivity");
              iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

              OnelittleAngelApplication.instance.startActivity(iBiography);
              try {
                ActivityAnimator anim = new ActivityAnimator();
                anim.fadeAnimation(context);
              } catch (Exception ignored) {
              }
              context.finish();


            }
          });
        } else {
          holder.link.setText(" [ " + OnelittleAngelApplication.instance.getResources().getString(R.string.biography_of) + (presenter.getBookPresentationOfDay().getMcc1() != null ? " " + presenter.getBookPresentationOfDay().getMcc1() : "") + " " + presenter.getBookPresentationOfDay().getName() + " ] ");

          holder.biographyOfDay.setOnTouchListener(new CardViewNativeGestureListener(context) {

            @Override
            public void onLongPressed() {

              super.onLongPressed();

              Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
              iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getBookPresentationOfDay().getName());

              iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromActivity", "TableContentsActivity");
              iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

              OnelittleAngelApplication.instance.startActivity(iBiography);
              try {
                ActivityAnimator anim = new ActivityAnimator();
                anim.fadeAnimation(context);
              } catch (Exception ignored) {
              }
              context.finish();

            }

            @Override
            public void onDoubleTaped() {

              super.onDoubleTaped();

              Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
              iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getBookPresentationOfDay().getName());

              iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromActivity", "TableContentsActivity");
              iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

              OnelittleAngelApplication.instance.startActivity(iBiography);
              try {
                ActivityAnimator anim = new ActivityAnimator();
                anim.fadeAnimation(context);
              } catch (Exception ignored) {
              }
              context.finish();

            }

            @Override
            public void onSingleTapConfirm() {
              super.onSingleTapConfirm();

              Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
              iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getBookPresentationOfDay().getName());

              iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromActivity", "TableContentsActivity");
              iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

              OnelittleAngelApplication.instance.startActivity(iBiography);
              try {
                ActivityAnimator anim = new ActivityAnimator();
                anim.fadeAnimation(context);
              } catch (Exception ignored) {
              }
              context.finish();
            }
          });

          holder.link.setOnTouchListener(new CardViewNativeGestureListener(context) {

            @Override
            public void onLongPressed() {
              //  super.onLongPressed();
              soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
              int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
              soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> soundPool1.play(soundID, 1f, 1f, 1, 0, 1f));

              Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
              iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getBookPresentationOfDay().getName());

              iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromActivity", "TableContentsActivity");
              iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

              OnelittleAngelApplication.instance.startActivity(iBiography);
              try {
                ActivityAnimator anim = new ActivityAnimator();
                anim.fadeAnimation(context);
              } catch (Exception ignored) {
              }
              context.finish();

            }

            @Override
            public void onSingleTapConfirm() {
              //  super.onSingleTapConfirm();
              soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
              int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
              soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> soundPool1.play(soundID, 1f, 1f, 1, 0, 1f));

              Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
              iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getBookPresentationOfDay().getName());

              iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromActivity", "TableContentsActivity");
              iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

              OnelittleAngelApplication.instance.startActivity(iBiography);
              try {
                ActivityAnimator anim = new ActivityAnimator();
                anim.fadeAnimation(context);
              } catch (Exception ignored) {
              }
              context.finish();

            }

            @Override
            public void onDoubleTaped() {
              //  super.onDoubleTaped();
              soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
              int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
              soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> soundPool1.play(soundID, 1f, 1f, 1, 0, 1f));

              Intent iBiography = new Intent(OnelittleAngelApplication.instance.getBaseContext(), BiographyActivity.class);
              iBiography.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              iBiography.putExtra(OnelittleAngelApplication.instance.getResources().getString(R.string.from), presenter.getBookPresentationOfDay().getName());

              iBiography.putExtra("type", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromFragment", OnelittleAngelApplication.instance.getResources().getString(R.string.homepage));
              iBiography.putExtra("fromActivity", "TableContentsActivity");
              iBiography.putExtra("isAuthor", settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false));

              OnelittleAngelApplication.instance.startActivity(iBiography);
              try {
                ActivityAnimator anim = new ActivityAnimator();
                anim.fadeAnimation(context);
              } catch (Exception ignored) {
              }
              context.finish();

            }
          });
        }

        holder.biographyOfDayTxt = "   ";

        if (presenter.getAuthorPresentationOfDay() != null) {
          if (presenter.getAuthorPresentationOfDay().getPresentation().getPresentation().length() > 700) {
            holder.biographyOfDayTxt += presenter.getAuthorPresentationOfDay().getPresentation().getPresentation().substring(0, 700);

            if (!holder.biographyOfDayTxt.substring(0, holder.biographyOfDayTxt.length()).equals("\\s")) {
              do {
                holder.biographyOfDayTxt = holder.biographyOfDayTxt.substring(0, holder.biographyOfDayTxt.length() - 1);
              }
              while (!Character.isWhitespace(holder.biographyOfDayTxt.charAt(holder.biographyOfDayTxt.length() - 1)));
            }
          } else {
            holder.biographyOfDayTxt += presenter.getAuthorPresentationOfDay().getPresentation().getPresentation();
          }
        } else {
          if (presenter.getBookPresentationOfDay().getPresentation().getPresentation().length() > 700) {
            holder.biographyOfDayTxt += presenter.getBookPresentationOfDay().getPresentation().getPresentation().substring(0, 700);

            if (!holder.biographyOfDayTxt.substring(0, holder.biographyOfDayTxt.length()).equals("\\s")) {
              do {
                holder.biographyOfDayTxt = holder.biographyOfDayTxt.substring(0, holder.biographyOfDayTxt.length() - 1);
              }
              while (!Character.isWhitespace(holder.biographyOfDayTxt.charAt(holder.biographyOfDayTxt.length() - 1)));
            }
          } else {
            holder.biographyOfDayTxt += presenter.getBookPresentationOfDay().getPresentation().getPresentation();
          }
        }

        holder.biographyOfDay.setText(String.format("%s ...", holder.biographyOfDayTxt));
        holder.llHomePage.measure(widthMeasureSpec, heightMeasureSpec);
        homePageListviewHeight += convertView.getMeasuredHeight();

        if (TableContentsActivity.isOrientationChanged) notifyDataSetChanged();

        //if (TableContentsActivity.homePageDialog != null) TableContentsActivity.homePageDialog.dismiss();

        viewPagerNative.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override
          public void onGlobalLayout() {
            TableContentsActivity.isFromOtherActivity = false;
            //  viewPagerNative.setPadding(0, 10, 0, 0);

            //viewPagerNative.setPadding(0, 0, 0, 0);

            viewPagerNative.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, homePageListviewHeight + ((int) OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin) * (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 3 : 8)) /*+ TableContentsActivity.headerHeight*//*/*16308*/));
            LinearLayout rlHeader = (LinearLayout) context.findViewById(R.id.header);
            rlHeader.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, homePageListviewHeight + TableContentsActivity.headerHeight + ((int) OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin) * (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 3 : 8))));
            isInit = true;

            settings.edit().putInt(Constants.HOMEPAGE_HEIGHT, homePageListviewHeight).apply();
            //editor.commit();
            homePageListviewHeight = 0;

            //if (TableContentsActivity.homePageDialog != null) TableContentsActivity.homePageDialog.dismiss();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
              viewPagerNative.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } else {
              viewPagerNative.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }

            isInit = false;
            homePageListviewHeight = 0;
          }
        });
      }
      convertView.setTag(holder);
      views.put(position, view);
      holders.put(position, holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
      views.put(position, convertView);

      if (!isInit) {
        if (holder != null) homePageListviewHeight += holder.contentView.getHeight();
        if (position == 2 /*&& homePageListviewHeight != 0*/) {
          homePageListviewHeight += /*TableContentsActivity.headerHeight +*/ OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin);

          viewPagerNative.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
              viewPagerNative.setPadding(0, 0, 0, 0);

              viewPagerNative.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, homePageListviewHeight + ((int) OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) /*+ TableContentsActivity.headerHeight*//*/*16308*/));
              LinearLayout rlHeader = (LinearLayout) context.findViewById(R.id.header);
              rlHeader.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, homePageListviewHeight + TableContentsActivity.headerHeight /*+ ((int) OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin))*/));
              isInit = true;

              settings.edit().putInt(Constants.HOMEPAGE_HEIGHT, homePageListviewHeight).apply();
              //editor.commit();
              homePageListviewHeight = 0;

              //if (TableContentsActivity.homePageDialog != null) TableContentsActivity.homePageDialog.dismiss();

              if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                viewPagerNative.getViewTreeObserver().removeGlobalOnLayoutListener(this);
              } else {
                viewPagerNative.getViewTreeObserver().removeOnGlobalLayoutListener(this);
              }
            }
          });
        }
      }
    }

    return convertView;
  }

  private void resizeView() {

    //setBottomAdapter();
    int heightToApply = 0;
    for(int i = 0;i < holders.size();i++) {
      holders.get(i).llHomePage.measure(widthMeasureSpec, heightMeasureSpec);
      heightToApply += holders.get(i).llHomePage.getMeasuredHeight();
    }
    //  Log.i("33", "heightToApply : " + heightToApply);

    if (heightToApply != 0){
      LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ((TableContentsActivity) context).viewPager.getLayoutParams();
      layoutParams.height = heightToApply;
      ((TableContentsActivity) context).viewPager.setLayoutParams(layoutParams);
      ((TableContentsActivity) context).viewPager.setBackgroundColor(Color.BLUE);
      //  setBottomAdapter();
    }
  }

  private Bitmap scaleBitmap(Bitmap bm, int originWidth, int originHeight) {
    int width = bm.getWidth();
    int height = bm.getHeight();

    if (width > height) {
      // landscape
      float ratio = (float) width / originWidth;
      width = originWidth;
      height = (int) (height / ratio);
    } else if (height > width) {
      // portrait
      float ratio = (float) height / originHeight;
      height = originHeight;
      width = (int) (width / ratio);
    } else {
      // square
      height = originHeight;
      width = originWidth;
    }

    bm = Bitmap.createScaledBitmap(bm, width, height, true);
    return bm;
  }

  @Override
  public void onLongPressed() {

  }

  @Override
  public void onSingleTapUped() {

  }

  @Override
  public void onFlingGesture() {

  }

  @Override
  public void onSingleTapConfirm() {

  }

  @Override
  public void onDoubleTaped() {

  }

  @Override
  public void onScaling(float size, int resId) {

    //  this.size = size;
    //  this.resId = resId;
    //    mCallbacks.onScaling(size);
  }

  @Override
  public void onScaleEnd(float size, int resId) {
    this.size = size;

    if (holders.get(0).quoteOfDay != null) {
      holders.get(0).quoteOfDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
      holders.get(0).quoteOfDay.setInit();
      holders.get(0).llHomePage.measure(widthMeasureSpec, heightMeasureSpec);
    }

    if (holders.get(2).biographyOfDay != null) {
      holders.get(2).biographyOfDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
      holders.get(2).biographyOfDay.setInit();
      holders.get(2).llHomePage.measure(widthMeasureSpec, heightMeasureSpec);
    }

    notifyDataSetChanged();
    mCallbacks.onScaling(size, resId);
  }

  public void setHomePageListView(HomePageListView homePageListView) {
  }

  public void setRowLayoutId(int rowLayoutId) {
    mRowLayoutId = rowLayoutId;
  }

  public interface Callbacks {
    void onScaling(float size, int resId);
  }

  public static class ViewHolder {

    public View contentView;
    String type = "";
    public TextViewNative mTitle;
    public ContentTextViewNativeBiography biographyOfDay;
    public String biographyOfDayTxt;
    public ContentTextViewNativeBiography quoteOfDay;
    public TextViewNative name;
    public TextViewNative link;
    public ImageView pictureOfDay;
    public HomePageCardView homePageCardView;
    public LinearLayout llHomePage;
  }


}
