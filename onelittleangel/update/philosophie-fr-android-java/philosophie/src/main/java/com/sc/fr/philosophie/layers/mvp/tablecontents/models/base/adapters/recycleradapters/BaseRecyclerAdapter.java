package com.sc.fr.philosophie.layers.mvp.tablecontents.models.base.adapters.recycleradapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.fr.philosophie.OnelittleAngelApplication;
import com.sc.fr.philosophie.R;
import com.sc.fr.philosophie.layers.mvp.MotherActivity;
import com.sc.fr.philosophie.layers.mvp.common.animations.ActivityAnimator;
import com.sc.fr.philosophie.layers.mvp.common.animations.ResizedHeightAnimation;
import com.sc.fr.philosophie.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.fr.philosophie.layers.mvp.common.graphics.Arrow;
import com.sc.fr.philosophie.layers.mvp.common.listeners.cardviews.CardViewNativeGestureListener;
import com.sc.fr.philosophie.layers.mvp.common.utils.Utils;
import com.sc.fr.philosophie.layers.mvp.contents.ContentsActivity;
import com.sc.fr.philosophie.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.fr.philosophie.layers.mvp.tablecontents.models.BaseEntity;
import com.sc.fr.philosophie.layers.mvp.tablecontents.models.base.adapters.listadapters.BaseListAdapter;
import com.sc.fr.philosophie.layers.mvp.tablecontents.models.base.views.recyclerviews.BaseRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BaseRecyclerAdapter<T extends BaseEntity> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseRecyclerViewHolder> {

  public static final String SHAREDPREFERENCES_FORMAT = "%d - %d - %d";

  private final Context context;
  public final List<T> objects;
  private final int mRowLayoutId;
  private final int positionInList;
  private String openPosition ;
  private final String modelsName ;
  private final BaseListAdapter baseListAdapter;
  private final BaseListAdapter.BaseListViewHolder mBaseListViewHolder;
  private BaseRecyclerViewHolder mParentBaseRecyclerViewHolder;
  public final List<BaseRecyclerViewHolder> holders = new ArrayList<>();
  private SharedPreferences settings;
  private SharedPreferences.Editor editor;

  private BaseRecyclerAdapter(Context context, int positionInList,
                              String modelsName,
                              BaseListAdapter baseListAdapter,
                              BaseListAdapter.BaseListViewHolder mBaseListViewHolder,
                              BaseRecyclerViewHolder mParentBaseRecyclerViewHolder, List<T> objects) {
    this.context = context;
    this.mRowLayoutId = R.layout.card_layout;
    this.positionInList = ++positionInList;
    this.modelsName = modelsName ;
    this.baseListAdapter = baseListAdapter;
    this.mBaseListViewHolder = mBaseListViewHolder;
    this.mParentBaseRecyclerViewHolder = mParentBaseRecyclerViewHolder;
    this.objects = objects;
    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    openPosition = settings.getString(modelsName,"");
  }

  public BaseRecyclerAdapter(Context context,
                             int positionInList,
                             int mRowLayoutId,
                             String modelsName,
                             BaseListAdapter baseListAdapter,
                             BaseListAdapter.BaseListViewHolder mBaseListViewHolder, List<T> objects) {
    this.context = context;
    this.mRowLayoutId = mRowLayoutId;
    this.positionInList = ++positionInList;
    this.modelsName = modelsName;
    this.baseListAdapter = baseListAdapter;
    this.mBaseListViewHolder = mBaseListViewHolder;
    this.objects = objects;
    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    openPosition = settings.getString(modelsName,"");
  }

  @Override
  public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
            .inflate(mRowLayoutId, parent, false);
    return new BaseRecyclerViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(final BaseRecyclerViewHolder holder,final int position) {

    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    editor = settings.edit();
    openPosition = settings.getString(modelsName,"");

    T object = objects.get(position);
    holder.faith.setTextColor(settings.getInt(CardViewNative.DARKERRGB,0));
    holder.faith.setText(object.faith);

    if(positionInList == 1){
      holder.faith.setPadding((int) context.getResources().getDimension(R.dimen.tc_padding_position_1), 0, 0, 0);
    }

    if(positionInList == 2 && mRowLayoutId == R.layout.card_layout){
      holder.faith.setPadding((int) context.getResources().getDimension(R.dimen.tc_padding_position_2), 0, 0, 0);
    }

    holder.number.setText(String.valueOf(object.number));
    holder.number.setTextColor(settings.getInt(CardViewNative.DARKERRGB,0));
    holder.position = position;
    holder.object = object;

    if (object.number == 0){
      holder.number.setVisibility(View.INVISIBLE);
    }

    if (object.name != null) {
      holder.faith.setText(object.name);
      holder.number.setText(String.valueOf(object.number));
    }

    if (objects.get(position).baseEntities != null) {
      holder.arrow.setVisibility(View.VISIBLE);
    }

    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/SortsMillGoudy-Italic.ttf");
    holder.faith.setTypeface(typeface);
    holder.number.setTypeface(typeface);

    String[] openPositionArray = openPosition.split(" - ");

    if (settings.getString(modelsName + "_lastposition", "").equals(holder.faith.getText().toString())) {

      if(holder.headerLayout != null) holder.headerLayout.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB,0));
      if(holder.headerContainer != null) holder.headerContainer.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB,0));
      holder.faith.setTextColor(Color.WHITE);
      holder.number.setTextColor(Color.WHITE);
      holder.arrow.setColor(Color.WHITE);
    }

    if(object.baseEntities == null && object.authorBooks == null && holder.headerLayout != null) {

      holder.headerLayout.setOnTouchListener( new CardViewNativeGestureListener(context) {

        @Override
        public void onLongPressed() {

          for(BaseRecyclerViewHolder viewHolder : holders) {

            if(viewHolder.position != position) {
              viewHolder.headerLayout.setBackgroundColor(Color.WHITE);
              viewHolder.faith.setTextColor(settings.getInt(CardViewNative.DARKERRGB,0));
              viewHolder.number.setTextColor(settings.getInt(CardViewNative.DARKERRGB,0));
              viewHolder.arrow.setColor(settings.getInt(CardViewNative.DARKERRGB,0));
            }
          }

          holder.headerLayout.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB,0));
          holder.faith.setTextColor(Color.WHITE);
          holder.number.setTextColor(Color.WHITE);
          holder.arrow.setColor(Color.WHITE);

          editor.putString(modelsName + "_lastposition",holder.faith.getText().toString());
          //editor.commit();
          editor.apply();
          Intent intent = new Intent(OnelittleAngelApplication.instance.getBaseContext(),ContentsActivity.class);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          intent.putExtra(context.getResources().getString(R.string.from),modelsName);
          intent.putExtra(context.getResources().getString(R.string.fromFragment),modelsName);
          intent.putExtra(modelsName,holder.object.faith);
          OnelittleAngelApplication.instance.startActivity(intent);

          try {
            ActivityAnimator anim = new ActivityAnimator();
            anim.fadeAnimation(context);
          } catch (Exception ignored) {
          }
          ((MotherActivity) context).finish();

        }

        @Override
        public void onSingleTapConfirm() {

          for(BaseRecyclerViewHolder viewHolder : holders) {

            if(viewHolder.position != position) {
              viewHolder.headerLayout.setBackgroundColor(Color.WHITE);
              viewHolder.faith.setTextColor(settings.getInt(CardViewNative.DARKERRGB,0));
              viewHolder.number.setTextColor(settings.getInt(CardViewNative.DARKERRGB,0));
              viewHolder.arrow.setColor(settings.getInt(CardViewNative.DARKERRGB,0));
            }
          }

          holder.headerLayout.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB,0));
          holder.faith.setTextColor(Color.WHITE);
          holder.number.setTextColor(Color.WHITE);
          holder.arrow.setColor(Color.WHITE);

          editor.putString(modelsName + "_lastposition",holder.faith.getText().toString());
          editor.commit();

          Intent intent = new Intent(OnelittleAngelApplication.instance.getBaseContext(),ContentsActivity.class);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          intent.putExtra(context.getResources().getString(R.string.from),modelsName);
          intent.putExtra(context.getResources().getString(R.string.fromFragment),modelsName);
          intent.putExtra(modelsName,holder.object.faith);
          OnelittleAngelApplication.instance.startActivity(intent);

          try {
            ActivityAnimator anim = new ActivityAnimator();
            anim.fadeAnimation(context);
          } catch (Exception ignored) {
          }
          ((MotherActivity) context).finish();

        }

        @Override
        public void onDoubleTaped() {

          for(BaseRecyclerViewHolder viewHolder : holders) {

            if(viewHolder.position != position) {
              viewHolder.headerLayout.setBackgroundColor(Color.WHITE);
              viewHolder.faith.setTextColor(settings.getInt(CardViewNative.DARKERRGB,0));
              viewHolder.number.setTextColor(settings.getInt(CardViewNative.DARKERRGB,0));
              viewHolder.arrow.setColor(settings.getInt(CardViewNative.DARKERRGB,0));
            }
          }

          holder.headerLayout.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB,0));
          holder.faith.setTextColor(Color.WHITE);
          holder.number.setTextColor(Color.WHITE);
          holder.arrow.setColor(Color.WHITE);

          editor.putString(modelsName + "_lastposition",holder.faith.getText().toString());
          editor.commit();

          Intent intent = new Intent(OnelittleAngelApplication.instance.getBaseContext(),ContentsActivity.class);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          intent.putExtra(context.getResources().getString(R.string.from),modelsName);
          intent.putExtra(context.getResources().getString(R.string.fromFragment),modelsName);
          intent.putExtra(modelsName,holder.object.faith);
          OnelittleAngelApplication.instance.startActivity(intent);

          try {
            ActivityAnimator anim = new ActivityAnimator();
            anim.fadeAnimation(context);
          } catch (Exception ignored) {
          }
          ((MotherActivity) context).finish();

        }
      });
    }

    if (mRowLayoutId == R.layout.content_layout) {

      holder.headerContainer.setOnTouchListener( new CardViewNativeGestureListener(context) {

        @Override
        public void onLongPressed() {

          for (BaseRecyclerViewHolder viewHolder : holders) {

            if (viewHolder.position != position) {
              viewHolder.headerContainer.setBackgroundColor(Color.WHITE);
              viewHolder.faith.setTextColor(settings.getInt(CardViewNative.DARKERRGB,0));
              viewHolder.number.setTextColor(settings.getInt(CardViewNative.DARKERRGB,0));
              viewHolder.arrow.setColor(settings.getInt(CardViewNative.DARKERRGB,0));
            }
          }

          holder.headerContainer.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB,0));
          holder.faith.setTextColor(Color.WHITE);
          holder.number.setTextColor(Color.WHITE);
          holder.arrow.setColor(Color.WHITE);

          editor.putString(modelsName + "_lastposition", holder.faith.getText().toString());
          editor.commit();

          Intent intent = new Intent(OnelittleAngelApplication.instance.getBaseContext(),ContentsActivity.class);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          intent.putExtra(context.getResources().getString(R.string.from),modelsName);
          intent.putExtra(context.getResources().getString(R.string.fromFragment),modelsName);
          intent.putExtra(modelsName,holder.object.name);
          OnelittleAngelApplication.instance.startActivity(intent);

          try {
            ActivityAnimator anim = new ActivityAnimator();
            anim.fadeAnimation(context);
          } catch (Exception ignored) {
          }
          ((MotherActivity) context).finish();

        }

        @Override
        public void onSingleTapConfirm() {

          for (BaseRecyclerViewHolder viewHolder : holders) {

            if (viewHolder.position != position) {
              viewHolder.headerContainer.setBackgroundColor(Color.WHITE);
              viewHolder.faith.setTextColor(settings.getInt(CardViewNative.DARKERRGB,0));
              viewHolder.number.setTextColor(settings.getInt(CardViewNative.DARKERRGB,0));
              viewHolder.arrow.setColor(settings.getInt(CardViewNative.DARKERRGB,0));
            }
          }

          holder.headerContainer.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB,0));
          holder.faith.setTextColor(Color.WHITE);
          holder.number.setTextColor(Color.WHITE);
          holder.arrow.setColor(Color.WHITE);

          editor.putString(modelsName + "_lastposition", holder.faith.getText().toString());
          editor.commit();

          Intent intent = new Intent(OnelittleAngelApplication.instance.getBaseContext(),ContentsActivity.class);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          intent.putExtra(context.getResources().getString(R.string.from),modelsName);
          intent.putExtra(context.getResources().getString(R.string.fromFragment),modelsName);
          intent.putExtra(modelsName,holder.object.name);
          OnelittleAngelApplication.instance.startActivity(intent);

          try {
            ActivityAnimator anim = new ActivityAnimator();
            anim.fadeAnimation(context);
          } catch (Exception ignored) {
          }
          ((MotherActivity) context).finish();

        }

        @Override
        public void onDoubleTaped() {

          for (BaseRecyclerViewHolder viewHolder : holders) {

            if (viewHolder.position != position) {
              viewHolder.headerContainer.setBackgroundColor(Color.WHITE);
              viewHolder.faith.setTextColor(settings.getInt(CardViewNative.DARKERRGB,0));
              viewHolder.number.setTextColor(settings.getInt(CardViewNative.DARKERRGB,0));
              viewHolder.arrow.setColor(settings.getInt(CardViewNative.DARKERRGB,0));
            }
          }

          holder.headerContainer.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB,0));
          holder.faith.setTextColor(Color.WHITE);
          holder.number.setTextColor(Color.WHITE);
          holder.arrow.setColor(Color.WHITE);

          editor.putString(modelsName + "_lastposition", holder.faith.getText().toString());
          editor.commit();

          Intent intent = new Intent(OnelittleAngelApplication.instance.getBaseContext(),ContentsActivity.class);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          intent.putExtra(context.getResources().getString(R.string.from),modelsName);
          intent.putExtra(context.getResources().getString(R.string.fromFragment),modelsName);
          intent.putExtra(modelsName,holder.object.name);
          OnelittleAngelApplication.instance.startActivity(intent);

          try {
            ActivityAnimator anim = new ActivityAnimator();
            anim.fadeAnimation(context);
          } catch (Exception ignored) {
          }
          ((MotherActivity) context).finish();

        }
      });
    }

    if (objects.get(position).baseEntities != null || objects.get(position).authorBooks != null) {

      if (holder.headerLayout != null) {

        final View v = holder.itemView;

        openPositionArray = openPosition.split(" - ");

        if (openPositionArray.length != 1 && openPositionArray[0].equals(String.valueOf(mBaseListViewHolder.position))) {
          if (mParentBaseRecyclerViewHolder == null && openPositionArray[1].equals(String.valueOf(position))) {

            inflateViewStubBaseRecyclerView(v, holder, position);
            Utils.toggle((Activity) context,positionInList, modelsName, null, mBaseListViewHolder, null, holder, 0);
          }

          if(mParentBaseRecyclerViewHolder != null  && openPositionArray.length == 3
                  && openPositionArray[1].equals(String.valueOf(mParentBaseRecyclerViewHolder.position))
                  && openPositionArray[2].equals(String.valueOf(position))) {
            inflateViewStubBaseRecyclerView(v, holder, position);
            Utils.toggle((Activity) context, positionInList, modelsName, null, mBaseListViewHolder, mParentBaseRecyclerViewHolder, holder, 0);
          }
        }

        holder.headerLayout.setOnTouchListener(new CardViewNativeGestureListener(OnelittleAngelApplication.instance) {

          @Override
          public void onLongPressed() {

            if (!holder.isAnimating) {

              holder.isAnimating = true;
              super.onLongPressed();

              holder.isOtherViewHolderOpened = false;

              for (BaseRecyclerViewHolder viewHolder : holders) if (viewHolder.isExpanded) holder.isOtherViewHolderOpened = true;

              inflateViewStubBaseRecyclerView(v, holder,position);
              if (mParentBaseRecyclerViewHolder != null) {

                ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(baseListAdapter.getBaseListView(), 8900);

                baseListviewHeightAnimation.setDuration(0);
                baseListAdapter.getBaseListView().startAnimation(baseListviewHeightAnimation);

                Handler handler = new Handler();
                handler.postDelayed(() -> {
                  //holder.isAnimating = true;
                  inflateViewStubBaseRecyclerView(v, holder, position);
                  Utils.toggle((Activity) context, positionInList, modelsName, baseListAdapter, mBaseListViewHolder, mParentBaseRecyclerViewHolder, holder, 200);
                }, 200);

              } else {
                ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(baseListAdapter.getBaseListView(), 8900);

                baseListviewHeightAnimation.setDuration(0);
                baseListAdapter.getBaseListView().startAnimation(baseListviewHeightAnimation);

                Handler handler = new Handler();
                handler.postDelayed(() -> {
                  //holder.isAnimating = true;
                  inflateViewStubBaseRecyclerView(v, holder, position);
//                Utils.toggle((Activity) context, positionInList, modelsName, null, mBaseListViewHolder, null, holder, 0);
                  //holder.isAnimating = true;
                  Utils.toggle((Activity) context, positionInList, modelsName, baseListAdapter, mBaseListViewHolder, null, holder, 200);
                }, 200);
              }
            }
          }

          @Override
          public void onSingleTapConfirm() {

            if (!holder.isAnimating) {

              holder.isAnimating = true;

              super.onSingleTapConfirm();

              holder.isOtherViewHolderOpened = false;

              for (BaseRecyclerViewHolder viewHolder : holders) if (viewHolder.isExpanded) holder.isOtherViewHolderOpened = true;

              inflateViewStubBaseRecyclerView(v, holder,position);
              if(mParentBaseRecyclerViewHolder != null) {

                ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(baseListAdapter.getBaseListView(), 8900);

                baseListviewHeightAnimation.setDuration(0);
                baseListAdapter.getBaseListView().startAnimation(baseListviewHeightAnimation);

                Handler handler = new Handler();
                handler.postDelayed(() -> {

                  inflateViewStubBaseRecyclerView(v, holder, position);
                  Utils.toggle((Activity) context, positionInList, modelsName, baseListAdapter, mBaseListViewHolder, mParentBaseRecyclerViewHolder, holder, 200);
                }, 200);
              }
              else {
                ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(baseListAdapter.getBaseListView(), 8900);
                baseListviewHeightAnimation.setDuration(0);
                baseListAdapter.getBaseListView().startAnimation(baseListviewHeightAnimation);

                Handler handler = new Handler();
                handler.postDelayed(() -> {
                  inflateViewStubBaseRecyclerView(v, holder, position);
                  Utils.toggle((Activity) context, positionInList, modelsName, baseListAdapter, mBaseListViewHolder, null, holder, 200);
                }, 200);
              }
            }
          }

          @Override
          public void onDoubleTaped() {

            if (!holder.isAnimating) {

              holder.isAnimating = true;

              super.onDoubleTaped();

              holder.isOtherViewHolderOpened = false;

              for (BaseRecyclerViewHolder viewHolder : holders)
                if (viewHolder.isExpanded) holder.isOtherViewHolderOpened = true;

              inflateViewStubBaseRecyclerView(v, holder, position);
              if (mParentBaseRecyclerViewHolder != null) {

                ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(baseListAdapter.getBaseListView(), 8900);

                baseListviewHeightAnimation.setDuration(0);
                baseListAdapter.getBaseListView().startAnimation(baseListviewHeightAnimation);

                Handler handler = new Handler();
                handler.postDelayed(() -> {

                  inflateViewStubBaseRecyclerView(v, holder, position);
                  Utils.toggle((Activity) context, positionInList, modelsName, baseListAdapter, mBaseListViewHolder, mParentBaseRecyclerViewHolder, holder, 200);
                }, 200);
              } else {
                ResizedHeightAnimation baseListviewHeightAnimation = new ResizedHeightAnimation(baseListAdapter.getBaseListView(), 8900);

                baseListviewHeightAnimation.setDuration(0);
                baseListAdapter.getBaseListView().startAnimation(baseListviewHeightAnimation);

                Handler handler = new Handler();
                handler.postDelayed(() -> {

                  inflateViewStubBaseRecyclerView(v, holder, position);
//                  Utils.toggle((Activity) context, positionInList, modelsName, null, mBaseListViewHolder, null, holder, 0);
                  Utils.toggle((Activity) context, positionInList, modelsName, baseListAdapter, mBaseListViewHolder, null, holder, 200);
                }, 200);
              }
            }
          }
        });
      }
    }

    if (holder.frame != null)
      holder.frame.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT,0)));

    holders.add(holder);
  }

  private void inflateViewStubBaseRecyclerView(View view, BaseRecyclerViewHolder holder, int position){
    if (objects.get(position).baseEntities != null
            && !holder.isExpanded && holder.baseRecyclerView == null) {
      holder.isContent = false;
      holder.viewStubBaseRecyclerView = (ViewStub) view.findViewById(R.id.cardViewStub);
      holder.baseRecyclerView = (BaseRecyclerView)holder.viewStubBaseRecyclerView.inflate();
      holder.baseRecyclerView.setLayoutManager(new LinearLayoutManager(OnelittleAngelApplication.instance));
      holder.baseRecyclerView.setAdapter(new BaseRecyclerAdapter(context, positionInList, modelsName, baseListAdapter, mBaseListViewHolder,holder, objects.get(position).baseEntities));
    }

    if(objects.get(position).authorBooks != null
            && !holder.isExpanded && holder.baseRecyclerView == null){
      holder.isContent = true;
      holder.viewStubBaseRecyclerView = (ViewStub) view.findViewById(R.id.cardViewStub);
      holder.baseRecyclerView = (BaseRecyclerView)holder.viewStubBaseRecyclerView.inflate();
      holder.baseRecyclerView.setLayoutManager(new LinearLayoutManager(OnelittleAngelApplication.instance));
      holder.baseRecyclerView.setAdapter(new BaseRecyclerAdapter(context, positionInList, R.layout.content_layout, modelsName, baseListAdapter, mBaseListViewHolder, objects.get(position).authorBooks));
    }
  }

  @Override
  public int getItemCount() {
    if(objects == null)
      return 0;
    else
      return objects.size();
  }

  public static class BaseRecyclerViewHolder<T extends BaseEntity> extends RecyclerView.ViewHolder {

    public final View itemView;
    public final TextView faith;
    public final TextView number;
    public final Arrow arrow;
    public final LinearLayout frame;
    public final FrameLayout headerLayout;
    public final RelativeLayout headerContainer;
    public boolean isExpanded;
    public ViewStub viewStubBaseRecyclerView;
    public BaseRecyclerView baseRecyclerView;
    public int position;
    public boolean isContent;
    public T object;
    public boolean isOtherViewHolderOpened = false;
    public boolean isAnimating = false;


    public BaseRecyclerViewHolder(View itemView) {
      super(itemView);
      this.itemView = itemView;
      faith = (TextView) itemView.findViewById(R.id.faith);
      number = (TextView) itemView.findViewById(R.id.count);
      arrow = (Arrow) itemView.findViewById(R.id.bolt);
      headerLayout = (FrameLayout) itemView.findViewById(R.id.header_layout);
      headerContainer = (RelativeLayout) itemView.findViewById(R.id.header_container);
      viewStubBaseRecyclerView = (ViewStub) itemView.findViewById(R.id.cardViewStub) ;
      frame = (LinearLayout) itemView.findViewById(R.id.native_card_xml);
    }
  }
}
