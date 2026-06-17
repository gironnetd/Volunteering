package com.sc.fr.confucianisme.layers.mvp.settings.fragments.movements.recycler.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sc.fr.confucianisme.OnelittleAngelApplication;
import com.sc.fr.confucianisme.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.fr.confucianisme.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.fr.confucianisme.layers.mvp.common.listeners.cardviews.CardViewNativeGestureListener;
import com.sc.fr.confucianisme.layers.mvp.common.utils.Constants;
import com.sc.fr.confucianisme.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.fr.confucianisme.R;

import java.util.ArrayList;
import java.util.List;

public class MovementsRecyclerAdapter extends RecyclerView.Adapter<MovementsRecyclerAdapter.mViewHolder> {

  interface ItemCallback {

    void onItemClicked(int itemIndex);
  }

  interface ButtonCallback {

    void onButtonClicked(int buttonIndex);
  }

  public interface Callbacks {

    void changeTypeFace();
  }



  public Context context;
  private  CharSequence[] items;
  private List<mViewHolder> holders;
  private SharedPreferences settings;
  //SharedPreferences.Editor editor;
  private Callbacks mCallbacks;
  public SoundPool soundPool;


  public MovementsRecyclerAdapter(Context context) {
    this(context.getResources().getTextArray(R.array.movements));
    this.items = context.getResources().getStringArray(R.array.movements);
    this.context = context;
    settings = context.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    //editor = settings.edit();
    this.holders = new ArrayList<>();
  }

  private MovementsRecyclerAdapter(CharSequence[] items) {
    this.items = items;
  }

  void setCallbacks(ItemCallback itemCallback, ButtonCallback buttonCallback) {
  }

  public void setCallbacks(Callbacks listener) {
    mCallbacks = listener;
  }

  @Override
  public MovementsRecyclerAdapter.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.typefaces_dialog_row, parent, false);
    mViewHolder vh = new mViewHolder(view, this);
    holders.add(vh);
    return vh;
  }

  @Override
  public void onBindViewHolder(MovementsRecyclerAdapter.mViewHolder holder, int position) {

    holder.position = position;
    holder.movementName.setText(String.format("%s%s", items[position].toString(), context.getResources().getString(R.string.space)));
    holder.fontCb.setTag(position);
    String movementsList = "";
    if(settings.getString(Constants.MOVEMENTSLIST_SELECTED, "") != null) {
      movementsList = settings.getString(Constants.MOVEMENTSLIST_SELECTED, "");
    }

    if(movementsList.contains(items[position])) {
      holder.fontCb.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
      holder.fontCb.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
    }

    if (items[position].equals(Constants.APPLICATION_MOVEMENT)) {
      holder.view.setOnTouchListener(null);
      holder.fontCb.setOnTouchListener(null);
      holder.movementName.setOnTouchListener(null);
    }
  }

  @Override
  public int getItemCount() {
    return items.length;
  }

  class mViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

    public int position;
    final TextViewNative movementName;
    public final ImageView fontCb;
    public View view;
    final MovementsRecyclerAdapter adapter;

    mViewHolder(View itemView, MovementsRecyclerAdapter adapter) {
      super(itemView);
      view = itemView;
      movementName = (TextViewNative) itemView.findViewById(R.id.font_name);
      fontCb = (ImageView)  itemView.findViewById(R.id.font_cb);
      this.adapter = adapter;

      CardViewNativeGestureListener listener = new CardViewNativeGestureListener(context) {

        @Override
        public void onLongPressed() {
          super.onLongPressed();
          toggleCb(position);
        }

        @Override
        public void onSingleTapConfirm() {
          super.onSingleTapConfirm();
          toggleCb(position);
        }

        @Override
        public void onDoubleTaped() {
          super.onDoubleTaped();
          toggleCb(position);
        }
      };

      view.setOnTouchListener(listener);
      fontCb.setOnTouchListener(listener);
      movementName.setOnTouchListener(listener);
    }

    public void toggleCb(int position){
      soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
      int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
      soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> soundPool1.play(soundID, 1f, 1f, 1, 0, 1f));
      String movementsList = "";
      if(settings.getString(Constants.MOVEMENTSLIST, "") != null) {
        movementsList = settings.getString(Constants.MOVEMENTSLIST_SELECTED, "");
      }

      if (!settings.getString("authors","").equals("")) settings.edit().putString("authors","").apply();
      if (!settings.getString("movements","").equals("")) settings.edit().putString("movements","").apply();
      if (!settings.getString("books","").equals("")) settings.edit().putString("books","").apply();

      if(fontCb.getDrawingCacheBackgroundColor() == settings.getInt(CardViewNative.DARKERRGB, 0)) {
        fontCb.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
        fontCb.setBackgroundColor(Color.TRANSPARENT);
        movementsList = movementsList.replace(items[position].toString() + ";", "");

      } else {
        fontCb.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
        fontCb.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
        movementsList += items[position].toString() + ";";
        //    mCallbacks.changeTypeFace(items[position].toString());
      }
      settings.edit().putString(Constants.MOVEMENTSLIST_SELECTED, movementsList).apply();
      mCallbacks.changeTypeFace();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
      switch(event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          if(fontCb.getDrawingCacheBackgroundColor() == settings.getInt(CardViewNative.DARKERRGB, 0)) {
            fontCb.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
            fontCb.setBackgroundColor(Color.TRANSPARENT);
          } else {
            fontCb.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
            fontCb.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
          }
          return false;
      }
      return false;
    }
  }

}
