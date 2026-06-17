package com.sc.fr.christianisme.layers.mvp.common.customs.listviews.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.annotation.ArrayRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sc.fr.christianisme.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.fr.christianisme.layers.mvp.common.utils.Constants;
import com.sc.fr.christianisme.R;
import com.sc.fr.christianisme.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.fr.christianisme.layers.mvp.common.listeners.cardviews.CardViewNativeGestureListener;
import com.sc.fr.christianisme.layers.mvp.tablecontents.TableContentsActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple adapter example for custom items in the dialog
 */
public class TypefacesRecyclerAdapter extends RecyclerView.Adapter<TypefacesRecyclerAdapter.mViewHolder> {

    interface ItemCallback {

        void onItemClicked(int itemIndex);
    }

    interface ButtonCallback {

        void onButtonClicked(int buttonIndex);
    }

    public interface Callbacks {

        void changeTypeFace(String typeFace);
    }

    private Context context;
    private  CharSequence[] items;
  private List<mViewHolder> holders;
    private SharedPreferences settings;
    private Callbacks mCallbacks;
    public WeakReference<SoundPool> soundPool;

    public TypefacesRecyclerAdapter(Context context, @ArrayRes int arrayResId) {
        this(context.getResources().getTextArray(arrayResId));
        this.items = context.getResources().getStringArray(arrayResId);
        this.context = context;
        settings = context.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
        this.holders = new ArrayList<>();
    }

    public TypefacesRecyclerAdapter(Context context, CharSequence[] items) {
        this.context = context;
        this.items = items;
        settings = context.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
        this.holders = new ArrayList<>();
    }

    private TypefacesRecyclerAdapter(CharSequence[] items) {
        this.items = items;
    }

    void setCallbacks(ItemCallback itemCallback, ButtonCallback buttonCallback) {
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.typefaces_dialog_row, parent, false);
        mViewHolder vh = new mViewHolder(view, this);
        holders.add(vh);
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        holder.position = position;
        holder.fontName.setText(items[position].toString().replace("-Regular","") + " ");
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + items[position] + ".ttf");
        holder.fontName.setTypeface(typeface);
        holder.fontCb.setTag(position);

        String tpString = settings.getString(Constants.TYPEFACE, "");

        if(tpString.equals(items[position])) {
            holder.fontCb.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
            holder.fontCb.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
        }

    }

    public void setCallbacks(Callbacks listener) {
        mCallbacks = listener;
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

     class mViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

        public int position;
        final TextViewNative fontName;
        public final ImageView fontCb;
        final TypefacesRecyclerAdapter adapter;

        mViewHolder(View itemView, TypefacesRecyclerAdapter adapter) {
            super(itemView);
            fontName = (TextViewNative) itemView.findViewById(R.id.font_name);
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
            itemView.setOnTouchListener(listener);
            fontCb.setOnTouchListener(listener);
            fontName.setOnTouchListener(listener);
        }

         public void toggleCb(int position){

             soundPool = new WeakReference<>(new SoundPool(10, AudioManager.STREAM_MUSIC, 0));

             int soundID = soundPool.get().load(context, R.raw.unlock, 1);
             soundPool.get().setOnLoadCompleteListener((soundPool1, sampleId, status) -> soundPool1.play(soundID, 1f, 1f, 1, 0, 1f));

             if(fontCb.getDrawingCacheBackgroundColor() == settings.getInt(CardViewNative.DARKERRGB, 0)) {
                 fontCb.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
                 fontCb.setBackgroundColor(Color.TRANSPARENT);
             } else {
                 for(int i = 0; i < holders.size(); i++) {
                     if(holders.get(i).fontCb.getDrawingCacheBackgroundColor() == settings.getInt(CardViewNative.DARKERRGB, 0)) {
                         holders.get(i).fontCb.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
                         holders.get(i).fontCb.setBackgroundColor(Color.TRANSPARENT);
                     }
                 }
                 fontCb.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                 fontCb.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                 mCallbacks.changeTypeFace(items[position].toString());
             }
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
