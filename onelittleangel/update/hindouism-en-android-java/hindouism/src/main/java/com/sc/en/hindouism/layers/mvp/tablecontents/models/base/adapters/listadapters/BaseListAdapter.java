package com.sc.en.hindouism.layers.mvp.tablecontents.models.base.adapters.listadapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sc.en.hindouism.OnelittleAngelApplication;
import com.sc.en.hindouism.layers.mvp.common.animations.ActivityAnimator;
import com.sc.en.hindouism.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.hindouism.layers.mvp.common.graphics.Arrow;
import com.sc.en.hindouism.layers.mvp.common.listeners.cardviews.CardViewNativeGestureListener;
import com.sc.en.hindouism.layers.mvp.common.utils.Utils;
import com.sc.en.hindouism.layers.mvp.contents.ContentsActivity;
import com.sc.en.hindouism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.hindouism.layers.mvp.tablecontents.fragments.PresenterInterface;
import com.sc.en.hindouism.layers.mvp.tablecontents.models.BaseEntity;
import com.sc.en.hindouism.layers.mvp.tablecontents.models.base.adapters.recycleradapters.BaseRecyclerAdapter;
import com.sc.en.hindouism.layers.mvp.tablecontents.models.base.views.listviews.BaseListView;
import com.sc.en.hindouism.R;
import com.sc.en.hindouism.layers.mvp.MotherActivity;
import com.sc.en.hindouism.layers.mvp.tablecontents.models.base.views.recyclerviews.BaseRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BaseListAdapter<T extends BaseEntity> extends ArrayAdapter<T> {


  private final PresenterInterface presenter;

  private final int positionInList = 0;
  private final Context context;
  private String openPosition;
  private final String modelsName ;
  private int mRowLayoutId = R.layout.list_card_layout;
  private BaseListView baseListView;
  public final List<BaseListViewHolder> holders;
  private final List<View> views;
  private SharedPreferences settings;
  private SharedPreferences.Editor editor;


  public BaseListAdapter(Context context, PresenterInterface presenter, String modelsName, List<T> objects) {
    super(OnelittleAngelApplication.instance, R.layout.list_card_layout, objects);
    this.context = context;
    this.modelsName =  modelsName;
    this.presenter = presenter;
    holders = new ArrayList<>();
    views = new ArrayList<>();
    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    openPosition = settings.getString(modelsName,"");
  }

  @Override
  public int getCount() {
    return presenter.getMovements().size();
  }

  @Nullable
  @Override
  public T getItem(int position) {
    return (T) presenter.getMovements().get(position);
  }

  // -------------------------------------------------------------
  //  Getters and Setters
  // -------------------------------------------------------------

  public void setRowLayoutId(int rowLayoutId) {
    mRowLayoutId = rowLayoutId;
  }

  public BaseListView getBaseListView() {
    return baseListView;
  }

  public void setBaseListView(BaseListView baseListView) {
    this.baseListView = baseListView;
  }

  @NonNull
  @Override
  public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    editor = settings.edit();
    openPosition = settings.getString(modelsName ,"");

    final BaseListViewHolder holder;
    final View view;

    String[] openPositionArray = openPosition.split(" - ");

    if (convertView == null) {
      LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = mInflater.inflate(this.mRowLayoutId, parent, false);
      setViewHolder(convertView, position);

      final View v = convertView;

      holder = views.size() > position && views.get(position) != null ? (BaseListViewHolder) views.get(position).getTag() : (BaseListViewHolder) convertView.getTag();
      if((openPositionArray[0].equals(String.valueOf(position)) && openPositionArray.length != 0 && !holder.isOtherViewHolderOpened && !holder.isExpanded) || presenter.getMovements().size() == 1 ) {
        inflateViewStubBaseRecyclerView(v, (BaseListViewHolder) convertView.getTag(), position);
        Utils.toggle((Activity) context, positionInList, modelsName, baseListView.mBaseListAdapter, (BaseListViewHolder) convertView.getTag(), null, null, 0);
        // Utils.toggle((Activity) context, positionInList, modelsName, baseListView.mBaseListAdapter, holders.get(position), null, null, 0);
      }
    } else {

      holder = (BaseListViewHolder) views.get(position).getTag();

      if (!openPositionArray[0].equals("") && position == Integer.parseInt(openPositionArray[0]) || presenter.getMovements().size() == 1) return convertView;
    }

    holder.faith.setText(presenter.getMovements().get(position).faith);

    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/SortsMillGoudy-Italic.ttf");
    holder.faith.setTypeface(typeface);
    holder.number.setTypeface(typeface);

    if(presenter.getMovements().get(position).number != 0) holder.number.setText(String.valueOf(presenter.getMovements().get(position).number));

    final View v = convertView;

    if (presenter.getMovements().get(position).baseEntities != null)
      holder.arrow.setVisibility(View.VISIBLE);

    if(presenter.getMovements().get(position).number != 0) {

      holder.headerLayout.setOnTouchListener(new CardViewNativeGestureListener(OnelittleAngelApplication.instance) {

        @Override
        public void onLongPressed() {

          if (!holder.isAnimating) {

            holder.isAnimating = true;
            super.onLongPressed();

            holders.get(position).isOtherViewHolderOpened = false;

            for (BaseListViewHolder viewHolder : holders) {

              if (viewHolder.isExpanded) holder.isOtherViewHolderOpened = true;

              if (viewHolder.position != position) {
                viewHolder.headerLayout.setBackgroundColor(Color.WHITE);
                viewHolder.faith.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                viewHolder.number.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                viewHolder.arrow.setColor(settings.getInt(CardViewNative.DARKERRGB, 0));
              }
            }
            holder.headerLayout.setClickable(false);
            inflateViewStubBaseRecyclerView(v, holder, position);
            Utils.toggle((Activity) context, positionInList, modelsName, baseListView.mBaseListAdapter, holders.get(position), null, null, 200);
          }
        }

        @Override
        public void onSingleTapConfirm() {

          if (!holder.isAnimating) {

            holder.isAnimating = true;
            super.onSingleTapConfirm();

            holders.get(position).isOtherViewHolderOpened = false;

            for (BaseListViewHolder viewHolder : holders) {

              if (viewHolder.isExpanded) holder.isOtherViewHolderOpened = true;

              if (viewHolder.position != position) {
                viewHolder.headerLayout.setBackgroundColor(Color.WHITE);
                viewHolder.faith.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                viewHolder.number.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                viewHolder.arrow.setColor(settings.getInt(CardViewNative.DARKERRGB, 0));
              }
            }

            holder.headerLayout.setClickable(false);
            inflateViewStubBaseRecyclerView(v, holders.get(position), position);
            Utils.toggle((Activity) context, positionInList, modelsName, baseListView.mBaseListAdapter, holders.get(position), null, null, 200);
          }
        }

        @Override
        public void onDoubleTaped() {

          if (!holder.isAnimating) {

            holder.isAnimating = true;
            super.onDoubleTaped();

            holders.get(position).isOtherViewHolderOpened = false;

            for (BaseListViewHolder viewHolder : holders) {
              if (viewHolder.isExpanded) holder.isOtherViewHolderOpened = true;
              if (viewHolder.position != position) {
                viewHolder.headerLayout.setBackgroundColor(Color.WHITE);
                viewHolder.faith.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                viewHolder.number.setTextColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                viewHolder.arrow.setColor(settings.getInt(CardViewNative.DARKERRGB, 0));
              }
            }

            holder.headerLayout.setClickable(false);
            inflateViewStubBaseRecyclerView(v, holders.get(position), position);
            Utils.toggle((Activity) context, positionInList, modelsName, baseListView.mBaseListAdapter, holders.get(position), null, null, 200);
          }
        }
      });
    }

    if (holder.arrow.getVisibility() != View.VISIBLE && modelsName.equals("movements")) {

      if(settings.getString(modelsName, "").equals(String.valueOf(position) )) {
        if(holder.headerLayout != null) holder.headerLayout.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB,0));
        holder.faith.setTextColor(Color.WHITE);
        holder.number.setTextColor(Color.WHITE);
      }

      holder.headerLayout.setOnTouchListener( new CardViewNativeGestureListener(context) {

        @Override
        public void onLongPressed() {
          editor.putString(modelsName, String.valueOf(holders.get(position).position) );
          //editor.commit();
          editor.apply();

          holder.headerLayout.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB,0));
          holder.faith.setTextColor(Color.WHITE);
          holder.number.setTextColor(Color.WHITE);
          holder.arrow.setColor(Color.WHITE);
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
          editor.putString(modelsName, String.valueOf(holder.position));
          editor.commit();

          holder.headerLayout.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB,0));
          holder.faith.setTextColor(Color.WHITE);
          holder.number.setTextColor(Color.WHITE);
          holder.arrow.setColor(Color.WHITE);
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
          editor.putString(modelsName, String.valueOf(holder.position) + " - ");
          editor.commit();

          holder.headerLayout.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB,0));
          holder.faith.setTextColor(Color.WHITE);
          holder.number.setTextColor(Color.WHITE);
          holder.arrow.setColor(Color.WHITE);
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

    holder.frame.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT ,0)));
    if (!holders.contains(holder)) holders.add(holder);
    if (!views.contains(convertView)) views.add(convertView);
    return views.get(position);
  }

  private void inflateViewStubBaseRecyclerView(View view, BaseListViewHolder holder, int position){
    if (presenter.getMovements().get(position).baseEntities != null
            && !holder.isExpanded && holder.baseRecyclerView == null) {
      holder.isContent = false;
      holder.viewStubBaseRecyclerView = (ViewStub) view.findViewById(R.id.cardViewStub);
      holder.baseRecyclerView = (BaseRecyclerView)holder.viewStubBaseRecyclerView.inflate();
      holder.baseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      holder.baseRecyclerView.setAdapter(new BaseRecyclerAdapter(context, positionInList,R.layout.card_layout,modelsName, this, holder, presenter.getMovements().get(position).baseEntities));
    }

    if(presenter.getMovements().get(position).authorBooks != null
            && !holder.isExpanded && holder.baseRecyclerView == null){
      holder.isContent = true;
      holder.viewStubBaseRecyclerView = (ViewStub) view.findViewById(R.id.cardViewStub);
      holder.baseRecyclerView = (BaseRecyclerView)holder.viewStubBaseRecyclerView.inflate();
      holder.baseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      holder.baseRecyclerView.setAdapter(new BaseRecyclerAdapter(context, positionInList,R.layout.content_layout,modelsName, this, holder, presenter.getMovements().get(position).authorBooks));
    }
  }

  private void setViewHolder(View view, int position) {
    BaseListViewHolder holder = new BaseListViewHolder();
    holder.position = position;
    holder.object = presenter.getMovements().get(position);
    holder.faith = (TextView) view.findViewById(R.id.faith);
    holder.number = (TextView) view.findViewById(R.id.count);
    holder.arrow = (Arrow) view.findViewById(R.id.bolt);
    holder.llCardView = (LinearLayout) view.findViewById(R.id.ll_card_view);
    holder.cardView = (CardViewNative) view.findViewById(R.id.mCardViewNative_list);
    holder.headerLayout = (FrameLayout) view.findViewById(R.id.header_layout);
    holder.viewStubBaseRecyclerView = (ViewStub) view.findViewById(R.id.cardViewStub);
    holder.frame = (LinearLayout) view.findViewById(R.id.native_card_xml);
    view.setTag(holder);
  }

  public class BaseListViewHolder<t extends BaseEntity> {
    public TextView faith;
    public TextView number;
    public Arrow arrow;
    public LinearLayout frame;
    public FrameLayout headerLayout;
    public boolean isExpanded;
    public boolean isAnimating = false;
    public LinearLayout llCardView;
    public CardViewNative cardView;
    public ViewStub viewStubBaseRecyclerView;
    public BaseRecyclerView baseRecyclerView;
    public int position;
    public boolean isContent;
    public t object;
    public boolean isOtherViewHolderOpened = false;
  }
}
