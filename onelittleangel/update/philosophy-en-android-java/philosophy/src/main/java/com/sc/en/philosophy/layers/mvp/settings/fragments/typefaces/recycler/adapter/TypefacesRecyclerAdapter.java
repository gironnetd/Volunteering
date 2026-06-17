package com.sc.en.philosophy.layers.mvp.settings.fragments.typefaces.recycler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sc.en.philosophy.OnelittleAngelApplication;
import com.sc.en.philosophy.layers.mvp.common.listeners.cardviews.CardViewNativeGestureListener;
import com.sc.en.philosophy.R;
import com.sc.en.philosophy.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.philosophy.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.en.philosophy.layers.mvp.common.utils.Constants;
import com.sc.en.philosophy.layers.mvp.tablecontents.TableContentsActivity;

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

        void changeTypeFace();
    }

    public Context context;
    private  CharSequence[] items;
  private List<mViewHolder> holders;
    private SharedPreferences settings;
    //SharedPreferences.Editor editor;
    private Callbacks mCallbacks;
    public SoundPool soundPool;
    public int height = 0;


    public TypefacesRecyclerAdapter(Context context) {
        this(context.getResources().getTextArray(R.array.fonts));
        this.items = context.getResources().getStringArray(R.array.fonts);
        this.context = context;
        settings = context.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
        //editor = settings.edit();
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
        String typeFacesList = "";
        if(settings.getString(Constants.TYPEFACESLIST, "") != null) {
            typeFacesList = settings.getString(Constants.TYPEFACESLIST, "");
        }

        if(typeFacesList.contains(items[position])) {
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

         public void toggleCb(int position) {

             soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
             int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
             soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> soundPool1.play(soundID, 1f, 1f, 1, 0, 1f));
             String typeFacesList = "";
             if(settings.getString(Constants.TYPEFACESLIST, "") != null) {
                 typeFacesList = settings.getString(Constants.TYPEFACESLIST, "");
             }
             if(fontCb.getDrawingCacheBackgroundColor() == settings.getInt(CardViewNative.DARKERRGB, 0)) {
                 fontCb.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
                 fontCb.setBackgroundColor(Color.TRANSPARENT);
               typeFacesList =  typeFacesList.replace(items[position].toString() + ";", "");
             } else {
                 fontCb.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                 fontCb.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                 typeFacesList += items[position].toString() + ";";
             }
             settings.edit().putString(Constants.TYPEFACESLIST, typeFacesList).apply();
             //settings.edit().commit();
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
