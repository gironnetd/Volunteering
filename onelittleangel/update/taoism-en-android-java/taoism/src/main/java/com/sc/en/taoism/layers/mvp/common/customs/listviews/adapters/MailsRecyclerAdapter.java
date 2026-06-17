package com.sc.en.taoism.layers.mvp.common.customs.listviews.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sc.en.taoism.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.taoism.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.en.taoism.layers.mvp.common.listeners.cardviews.CardViewNativeGestureListener;
import com.sc.en.taoism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.taoism.R;
import com.sc.en.taoism.layers.mvp.common.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple adapter example for custom items in the dialog
 */
public class MailsRecyclerAdapter extends RecyclerView.Adapter<MailsRecyclerAdapter.mViewHolder> {

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
    private final ArrayMap<String,String> contacts;
  private List<mViewHolder> holders;
    private SharedPreferences settings;
    //SharedPreferences.Editor editor;

  public WeakReference<SoundPool> soundPool;


    public MailsRecyclerAdapter(Context context, ArrayMap<String,String> contacts) {
        this.context = context;
        this.contacts = contacts;
        settings = context.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
        //editor = settings.edit();
        this.holders = new ArrayList<>();
    }

    public MailsRecyclerAdapter(ArrayMap<String,String> contacts) {
        this.contacts = contacts;
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
        holder.fontName.setText(contacts.valueAt(position) + " ");

        holder.fontName.setTextColor(Color.BLACK);
        holder.fontCb.setTag(position);
        holder.fontCb.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
        holder.fontCb.setBackgroundColor(Color.TRANSPARENT);
    }

    public void setCallbacks(Callbacks listener) {
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

     class mViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

        public int position;
        final TextViewNative fontName;
        public final ImageView fontCb;
        final MailsRecyclerAdapter adapter;

        mViewHolder(View itemView, MailsRecyclerAdapter adapter) {
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

           String movementsList = settings.getString(Constants.MAILS_LIST_TO_SEND, "");

             if(fontCb.getDrawingCacheBackgroundColor() == settings.getInt(CardViewNative.DARKERRGB, 0)) {
                 fontCb.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
                 fontCb.setBackgroundColor(Color.TRANSPARENT);
                 movementsList = movementsList.replace(contacts.keyAt(position) + ";", "");
             } else {
                 fontCb.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                 fontCb.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                 movementsList += contacts.keyAt(position) + ";";
             }
             settings.edit().putString(Constants.MAILS_LIST_TO_SEND, movementsList).apply();
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
