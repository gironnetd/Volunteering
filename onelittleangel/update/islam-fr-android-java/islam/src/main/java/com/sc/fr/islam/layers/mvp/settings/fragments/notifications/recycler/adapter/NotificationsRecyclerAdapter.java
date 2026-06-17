package com.sc.fr.islam.layers.mvp.settings.fragments.notifications.recycler.adapter;

//import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
//import android.support.v4.app.FragmentTransaction;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
  import android.widget.ImageView;

  import com.sc.fr.islam.OnelittleAngelApplication;
import com.sc.fr.islam.layers.broadcast_receiver.biographyofday.BiographyNotificationEventReceiver;
import com.sc.fr.islam.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.fr.islam.layers.mvp.common.customs.textviews.TextViewNative;
import com.sc.fr.islam.layers.mvp.common.listeners.cardviews.CardViewNativeGestureListener;
import com.sc.fr.islam.layers.mvp.common.utils.Constants;
import com.sc.fr.islam.layers.mvp.settings.SettingsActivity;
import com.sc.fr.islam.R;
import com.sc.fr.islam.layers.broadcast_receiver.pictureofday.PictureNotificationEventReceiver;
import com.sc.fr.islam.layers.broadcast_receiver.quoteofday.QuoteNotificationEventReceiver;
import com.sc.fr.islam.layers.mvp.settings.fragments.notifications.NotificationsFragment;
import com.sc.fr.islam.layers.mvp.settings.fragments.notifications.picker.time.TimePickerDialog;
import com.sc.fr.islam.layers.mvp.tablecontents.TableContentsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by damien on 01/03/2017 for OnelittleAngel Android project.
 */

public class NotificationsRecyclerAdapter extends RecyclerView.Adapter<NotificationsRecyclerAdapter.mViewHolder> {

  public AppCompatActivity context;
  private  CharSequence[] items;
  SharedPreferences settings;
  SharedPreferences.Editor editor;
  public List<mViewHolder> holders;
  NotificationsFragment notificationsfragment;
  public SoundPool soundPool;
  public TimePickerDialog tpd;
  public int idPosition = -1;

  public NotificationsRecyclerAdapter(AppCompatActivity context,NotificationsFragment notificationsfragment, CharSequence[] items) {
    this.context = context;
    settings = context.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    editor = settings.edit();
    this.items = items;
    this.notificationsfragment = notificationsfragment;
    holders = new ArrayList<>();
  }

  public NotificationsRecyclerAdapter(CharSequence[] items) {
    settings = context.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    editor = settings.edit();
    this.items = items;
  }

  @Override
  public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.notifications_row, parent, false);
    mViewHolder vh = new mViewHolder(view);
    holders.add(vh);
    return new mViewHolder(view);
  }

  @Override
  public void onBindViewHolder(mViewHolder holder, int position) {

    holder.position = position;
    holder.notifcationsName.setText(String.format("%s ", items[position]));

    if(settings.getBoolean(Constants.SETTINGS_NOTIFICATION_TPD_OPENED, false) && settings.getInt(Constants.SETTINGS_NOTIFICATION_TPD_OPENED_POSITION, -1) == holder.position && SettingsActivity.isOrientationChanged) {
      //holder.toggleCb();
      //holder.isOrientationChanged = true;
      //editor.putInt(Constants.SETTINGS_NOTIFICATION_TPD_OPENED_POSITION, -1);
      //editor.commit();
    }
    else {
      holder.isOrientationChanged = false;

      TimePickerDialog dialog;

      if (position == 0) {
        if (!android.text.format.DateFormat.is24HourFormat(OnelittleAngelApplication.instance)) {

          int hour = Integer.parseInt(settings.getString(Constants.QUOTE_NOTIFICATION_HOUR, "16"));
          hour = hour % 12;
          if (hour == 0) {
            hour = 12;
          }

          holder.notificationTime.setText(hour + ":" + settings.getString(Constants.QUOTE_NOTIFICATION_MINUTE, "05") + " " + settings.getString(Constants.QUOTE_NOTIFICATION_AM_PM_VALUE, "AM"));
        }
        else {
          String hourString;

          if(Integer.parseInt(settings.getString(Constants.QUOTE_NOTIFICATION_HOUR, "16")) < 10)
            hourString = "0" + settings.getString(Constants.QUOTE_NOTIFICATION_HOUR, "16");
          else
            hourString = settings.getString(Constants.QUOTE_NOTIFICATION_HOUR, "16");

          holder.notificationTime.setText(hourString + ":" + settings.getString(Constants.QUOTE_NOTIFICATION_MINUTE, "00") + " ");
        }
        holder.hour = settings.getString(Constants.QUOTE_NOTIFICATION_HOUR, "12");
        holder.minutes = settings.getString(Constants.QUOTE_NOTIFICATION_MINUTE, "00");

        if (!settings.getBoolean(Constants.QUOTE_RECEIVE_NOTIFICATION, false)) {
          holder.notificationTime.setVisibility(View.GONE);
        } else {
          holder.notificationsCb.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
          holder.notificationsCb.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
        }
      }

      if (position == 1) {
        if (!android.text.format.DateFormat.is24HourFormat(OnelittleAngelApplication.instance)) {

          int hour = Integer.parseInt(settings.getString(Constants.PICTURE_NOTIFICATION_HOUR, "16"));
          hour = hour % 12;
          if (hour == 0) {
            hour = 12;
          }

          holder.notificationTime.setText(hour + ":" + settings.getString(Constants.PICTURE_NOTIFICATION_MINUTE, "05") + " " + settings.getString(Constants.PICTURE_NOTIFICATION_AM_PM_VALUE, "AM"));
        }
        else {
          String hourString;

          if(Integer.parseInt(settings.getString(Constants.PICTURE_NOTIFICATION_HOUR, "16")) < 10)
            hourString = "0" + settings.getString(Constants.PICTURE_NOTIFICATION_HOUR, "16");
          else
            hourString = settings.getString(Constants.PICTURE_NOTIFICATION_HOUR, "16");


          holder.notificationTime.setText(hourString + ":" + settings.getString(Constants.PICTURE_NOTIFICATION_MINUTE, "30") + " ");
        }
        holder.hour = settings.getString(Constants.PICTURE_NOTIFICATION_HOUR, "12");
        holder.minutes = settings.getString(Constants.PICTURE_NOTIFICATION_MINUTE, "00");

        if (!settings.getBoolean(Constants.PICTURE_RECEIVE_NOTIFICATION, false)) {
          holder.notificationTime.setVisibility(View.GONE);
        } else {
          holder.notificationsCb.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
          holder.notificationsCb.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
        }
      }
      if (position == 2) {

        if (!android.text.format.DateFormat.is24HourFormat(OnelittleAngelApplication.instance)) {

          int hour = Integer.parseInt(settings.getString(Constants.BIOGRAPHY_NOTIFICATION_HOUR, "16"));
          hour = hour % 12;
          if (hour == 0) {
            hour = 12;
          }

          holder.notificationTime.setText(hour + ":" + settings.getString(Constants.BIOGRAPHY_NOTIFICATION_MINUTE, "05") + " " + settings.getString(Constants.BIOGRAPHY_NOTIFICATION_AM_PM_VALUE, "AM"));
        }
        else {

          String hourString;

          if(Integer.parseInt(settings.getString(Constants.BIOGRAPHY_NOTIFICATION_HOUR, "16")) < 10)
            hourString = "0" + settings.getString(Constants.BIOGRAPHY_NOTIFICATION_HOUR, "16");
          else
            hourString = settings.getString(Constants.BIOGRAPHY_NOTIFICATION_HOUR, "16");

          holder.notificationTime.setText(hourString + ":" + settings.getString(Constants.BIOGRAPHY_NOTIFICATION_MINUTE, "05") + " ");
        }
        holder.hour = settings.getString(Constants.BIOGRAPHY_NOTIFICATION_HOUR, "12");
        holder.minutes = settings.getString(Constants.BIOGRAPHY_NOTIFICATION_MINUTE, "00");

        if (!settings.getBoolean(Constants.BIOGRAPHY_RECEIVE_NOTIFICATION, false)) {
          holder.notificationTime.setVisibility(View.GONE);
        } else {
          holder.notificationsCb.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
          holder.notificationsCb.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
        }
      }
    }
  }

  @Override
  public int getItemCount() {
    return items.length;
  }

  public class mViewHolder extends RecyclerView.ViewHolder {

    int position;
    public final TextViewNative notifcationsName;
    public final TextViewNative notificationTime;
    public final ImageView notificationsCb;
    public String hour = "12" ;
    public String minutes = "0" ;
    public String amPmValue;
    public boolean isOrientationChanged = false;
    public boolean isOkButtonPressed = false;

    public mViewHolder(View itemView) {
      super(itemView);
      notifcationsName = (TextViewNative) itemView.findViewById(R.id.notification_name);
      notificationTime = (TextViewNative) itemView.findViewById(R.id.txt_notification_time);
      notificationsCb = (ImageView) itemView.findViewById(R.id.notification_cb);

      CardViewNativeGestureListener listener = new CardViewNativeGestureListener(context) {

        @Override
        public void onLongPressed() {
          super.onLongPressed();
          toggleCb();
        }

        @Override
        public void onSingleTapConfirm() {
          super.onSingleTapConfirm();
          toggleCb();
        }

        @Override
        public void onDoubleTaped() {
          super.onDoubleTaped();
          toggleCb();
        }
      };

      itemView.setOnTouchListener(listener);
      notifcationsName.setOnTouchListener(listener);
      notificationsCb.setOnTouchListener(listener);

      notificationTime.setOnTouchListener((v, event) -> {
        switch(event.getAction()) {
          case MotionEvent.ACTION_DOWN:

            if(position == 0) {
              hour = settings.getString(Constants.QUOTE_NOTIFICATION_HOUR, "12");
              minutes = settings.getString(Constants.QUOTE_NOTIFICATION_MINUTE, "00");

              if (!android.text.format.DateFormat.is24HourFormat(OnelittleAngelApplication.instance)) {
                int hour = Integer.parseInt(this.hour);
                hour = hour % 12;
                if (hour == 0) {
                  if (settings.getString(Constants.QUOTE_NOTIFICATION_AM_PM_VALUE, "").equals("PM ")) hour = 12;
                  if (settings.getString(Constants.QUOTE_NOTIFICATION_AM_PM_VALUE, "").equals("AM ")) hour = 0;
                  this.hour = String.valueOf(hour);
                }

              }
            }

            if(position == 1) {
              hour = settings.getString(Constants.PICTURE_NOTIFICATION_HOUR, "12");
              minutes = settings.getString(Constants.PICTURE_NOTIFICATION_MINUTE, "00");

              if (!android.text.format.DateFormat.is24HourFormat(OnelittleAngelApplication.instance)) {
                int hour = Integer.parseInt(this.hour);
                hour = hour % 12;
                if (hour == 0) {
                  if (settings.getString(Constants.PICTURE_NOTIFICATION_AM_PM_VALUE, "").equals("PM ")) hour = 12;
                  if (settings.getString(Constants.PICTURE_NOTIFICATION_AM_PM_VALUE, "").equals("AM ")) hour = 0;
                  this.hour = String.valueOf(hour);
                }
              }
            }

            if(position == 2) {
              hour = settings.getString(Constants.BIOGRAPHY_NOTIFICATION_HOUR, "12");
              minutes = settings.getString(Constants.BIOGRAPHY_NOTIFICATION_MINUTE, "00");

              if (!android.text.format.DateFormat.is24HourFormat(OnelittleAngelApplication.instance)) {
                int hour = Integer.parseInt(this.hour);
                hour = hour % 12;
                if (hour == 0) {
                  if (settings.getString(Constants.BIOGRAPHY_NOTIFICATION_AM_PM_VALUE, "").equals("PM ")) hour = 12;
                  if (settings.getString(Constants.BIOGRAPHY_NOTIFICATION_AM_PM_VALUE, "").equals("AM ")) hour = 0;
                  this.hour = String.valueOf(hour);
                }

              }
            }

            //Calendar now = Calendar.getInstance();
            TimePickerDialog tpd = TimePickerDialog.newInstance(
                    new NotificationsFragment(),
                    Integer.valueOf(hour),
                    Integer.valueOf(minutes),
                    android.text.format.DateFormat.is24HourFormat(OnelittleAngelApplication.instance));

            tpd.dismissOnPause(true);
            tpd.setmUnselectedColor(settings.getInt(CardViewNative.DARKERRGB, 0));
            tpd.setAccentColor(settings.getInt(CardViewNative.DARKERRGB, 0));
            if(!context.isFinishing()) tpd.show(context.getSupportFragmentManager(), "Timepickerdialog");

            editor.putBoolean(Constants.SETTINGS_NOTIFICATION_TPD_OPENED, true);
            editor.commit();
            idPosition = position;

            tpd.setOnTimeSetListener((view, hourOfDay, minute, second) -> {
              isOkButtonPressed = true;
              hour = String.valueOf(hourOfDay);
              minutes = String.valueOf(minute);

              if (minute < 10)
                minutes = "0" + minute;

              if (position == 0) {
                editor.putString(Constants.QUOTE_NOTIFICATION_HOUR, hour);
                editor.commit();

                editor.putString(Constants.QUOTE_NOTIFICATION_MINUTE, minutes);
                editor.commit();

                if (!tpd.is24HourMode()) {
                  if (hourOfDay >= 12) {
                    editor.putString(Constants.QUOTE_NOTIFICATION_AM_PM_VALUE, "PM ");
                    amPmValue = "PM ";
                  } else {
                    editor.putString(Constants.QUOTE_NOTIFICATION_AM_PM_VALUE, "AM ");
                    amPmValue = "AM ";
                  }
                  editor.commit();
                  notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " " + amPmValue);
                } else notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " ");
              }

              if (position == 1) {
                editor.putString(Constants.PICTURE_NOTIFICATION_HOUR, hour);
                editor.commit();

                editor.putString(Constants.PICTURE_NOTIFICATION_MINUTE, minutes);
                editor.commit();

                if (!tpd.is24HourMode()) {
                  if (hourOfDay >= 12) {
                    editor.putString(Constants.PICTURE_NOTIFICATION_AM_PM_VALUE, "PM ");
                    amPmValue = "PM ";
                  } else {
                    editor.putString(Constants.PICTURE_NOTIFICATION_AM_PM_VALUE, "AM ");
                    amPmValue = "AM ";
                  }
                  editor.commit();
                  notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " " + amPmValue);
                } else notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " ");
              }

              if (position == 2) {
                editor.putString(Constants.BIOGRAPHY_NOTIFICATION_HOUR, hour);
                editor.commit();
                editor.putString(Constants.BIOGRAPHY_NOTIFICATION_MINUTE, minutes);
                editor.commit();

                if (!tpd.is24HourMode()) {
                  if (hourOfDay >= 12) {
                    editor.putString(Constants.BIOGRAPHY_NOTIFICATION_AM_PM_VALUE, "PM ");
                    amPmValue = "PM ";
                  } else {
                    editor.putString(Constants.BIOGRAPHY_NOTIFICATION_AM_PM_VALUE, "AM ");
                    amPmValue = "AM ";
                  }
                  editor.commit();
                  notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " " + amPmValue);
                } else notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " ");
              }
            });

            tpd.setOnDismissListener(dialog -> {

              idPosition = position;

              if (isOkButtonPressed) {

                if (position == 0) {
                  QuoteNotificationEventReceiver.setupAlarm(OnelittleAngelApplication.instance.getApplicationContext(), false, Integer.valueOf(hour), Integer.valueOf(minutes));
                  editor.putBoolean(Constants.QUOTE_RECEIVE_NOTIFICATION, true);
                }

                if (position == 1) {
                  PictureNotificationEventReceiver.setupAlarm(OnelittleAngelApplication.instance.getApplicationContext(), false, Integer.valueOf(hour), Integer.valueOf(minutes));
                  editor.putBoolean(Constants.PICTURE_RECEIVE_NOTIFICATION, true);
                }

                if (position == 2) {
                  BiographyNotificationEventReceiver.setupAlarm(OnelittleAngelApplication.instance.getApplicationContext(), false, Integer.valueOf(hour), Integer.valueOf(minutes));
                  editor.putBoolean(Constants.BIOGRAPHY_RECEIVE_NOTIFICATION, true);
                }

                notificationsCb.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                notificationsCb.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                notificationTime.setVisibility(View.VISIBLE);

                notificationsfragment.ivReceiveNotifications.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                notificationsfragment.ivReceiveNotifications.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
                editor.putBoolean(Constants.RECEIVE_NOTIFICATION, true);
                editor.commit();
                editor.putBoolean(Constants.SETTINGS_NOTIFICATION_TPD_OPENED, false);
                editor.commit();
                editor.putInt(Constants.SETTINGS_NOTIFICATION_TPD_OPENED_POSITION, -1);
                editor.commit();
                isOkButtonPressed = false;

                if(position == 0) {
                  editor.putString(Constants.QUOTE_NOTIFICATION_HOUR, hour);
                  editor.commit();

                  editor.putString(Constants.QUOTE_NOTIFICATION_MINUTE, tpd.mMinuteView.getText().toString());
                  editor.commit();

                  if (!tpd.is24HourMode()) {
                    //Log.i("55", "hour : " + hour);
                    if (Integer.parseInt(hour) >= 12) {
                      editor.putString(Constants.QUOTE_NOTIFICATION_AM_PM_VALUE, "PM ");
                      amPmValue = "PM ";
                    } else {
                      editor.putString(Constants.QUOTE_NOTIFICATION_AM_PM_VALUE, "AM ");
                      amPmValue = "AM ";
                    }
                    editor.commit();
                  }
                }

                if(position == 1) {
                  editor.putString(Constants.PICTURE_NOTIFICATION_HOUR, hour);
                  editor.commit();

                  editor.putString(Constants.PICTURE_NOTIFICATION_MINUTE, tpd.mMinuteView.getText().toString());
                  editor.commit();

                  if (!tpd.is24HourMode()) {
                    if (Integer.parseInt(hour) >= 12) {
                      editor.putString(Constants.PICTURE_NOTIFICATION_AM_PM_VALUE, "PM ");
                      amPmValue = "PM ";
                    } else {
                      editor.putString(Constants.PICTURE_NOTIFICATION_AM_PM_VALUE, "AM ");
                      amPmValue = "AM ";
                    }
                    editor.commit();
                  }

                }

                if(position == 2) {
                  editor.putString(Constants.BIOGRAPHY_NOTIFICATION_HOUR, hour);
                  editor.commit();
                  editor.putString(Constants.BIOGRAPHY_NOTIFICATION_MINUTE, tpd.mMinuteView.getText().toString());
                  editor.commit();

                  if (!tpd.is24HourMode()) {
                    if (Integer.parseInt(hour) >= 12) {
                      editor.putString(Constants.BIOGRAPHY_NOTIFICATION_AM_PM_VALUE, "PM ");
                      amPmValue = "PM ";
                    } else {
                      editor.putString(Constants.BIOGRAPHY_NOTIFICATION_AM_PM_VALUE, "AM ");
                      amPmValue = "AM ";
                    }
                    editor.commit();
                  }
                }

                if (!tpd.is24HourMode()) {
                  notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " " + amPmValue);
                } else {
                  notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " ");
                }
              }

              isOrientationChanged = false;



              dialog.cancel();
            });
        }
        return false;
      });
    }

    public void toggleCb() {

      soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
      int soundID = soundPool.load(context, R.raw.unlock, 1);
      soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> soundPool1.play(soundID, 1f, 1f, 1, 0, 1f));
      if (notificationsCb.getDrawingCacheBackgroundColor() == settings.getInt(CardViewNative.DARKERRGB, 0)) {
        notificationsCb.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
        notificationsCb.setBackgroundColor(Color.TRANSPARENT);

        idPosition = position;
        editor.putBoolean(Constants.SETTINGS_NOTIFICATION_TPD_OPENED, false);

        if (position == 0) {
          QuoteNotificationEventReceiver.cancelAlarm(OnelittleAngelApplication.instance.getApplicationContext());
          editor.putBoolean(Constants.QUOTE_RECEIVE_NOTIFICATION, false);
        }

        if (position == 1) {
          PictureNotificationEventReceiver.cancelAlarm(OnelittleAngelApplication.instance.getApplicationContext());
          editor.putBoolean(Constants.PICTURE_RECEIVE_NOTIFICATION, false);
        }

        if (position == 2) {
          BiographyNotificationEventReceiver.cancelAlarm(OnelittleAngelApplication.instance.getApplicationContext());
          editor.putBoolean(Constants.BIOGRAPHY_RECEIVE_NOTIFICATION, false);
        }
        notificationTime.setVisibility(View.GONE);
        editor.commit();
      } else {

        if(position == 0) {
          hour = settings.getString(Constants.QUOTE_NOTIFICATION_HOUR, "12");
          minutes = settings.getString(Constants.QUOTE_NOTIFICATION_MINUTE, "00");

          if (!android.text.format.DateFormat.is24HourFormat(OnelittleAngelApplication.instance)) {
            int hour = Integer.parseInt(this.hour);
            hour = hour % 12;
            if (hour == 0) {
              if (settings.getString(Constants.QUOTE_NOTIFICATION_AM_PM_VALUE, "").equals("PM ")) hour = 12;
              if (settings.getString(Constants.QUOTE_NOTIFICATION_AM_PM_VALUE, "").equals("AM ")) hour = 0;
              this.hour = String.valueOf(hour);
            }

          }
        }

        if(position == 1) {
          hour = settings.getString(Constants.PICTURE_NOTIFICATION_HOUR, "12");
          minutes = settings.getString(Constants.PICTURE_NOTIFICATION_MINUTE, "00");

          if (!android.text.format.DateFormat.is24HourFormat(OnelittleAngelApplication.instance)) {
            int hour = Integer.parseInt(this.hour);
            hour = hour % 12;
            if (hour == 0) {
              if (settings.getString(Constants.PICTURE_NOTIFICATION_AM_PM_VALUE, "").equals("PM ")) hour = 12;
              if (settings.getString(Constants.PICTURE_NOTIFICATION_AM_PM_VALUE, "").equals("AM ")) hour = 0;
              this.hour = String.valueOf(hour);
            }
          }
        }

        if(position == 2) {
          hour = settings.getString(Constants.BIOGRAPHY_NOTIFICATION_HOUR, "12");
          minutes = settings.getString(Constants.BIOGRAPHY_NOTIFICATION_MINUTE, "00");

          if (!android.text.format.DateFormat.is24HourFormat(OnelittleAngelApplication.instance)) {
            int hour = Integer.parseInt(this.hour);
            hour = hour % 12;
            if (hour == 0) {
              if (settings.getString(Constants.BIOGRAPHY_NOTIFICATION_AM_PM_VALUE, "").equals("PM ")) hour = 12;
              if (settings.getString(Constants.BIOGRAPHY_NOTIFICATION_AM_PM_VALUE, "").equals("AM ")) hour = 0;
              this.hour = String.valueOf(hour);
            }
          }
        }

          //Calendar now = Calendar.getInstance();
          TimePickerDialog tpd = TimePickerDialog.newInstance(
            new NotificationsFragment(),
            Integer.valueOf(hour),
            Integer.valueOf(minutes),
            android.text.format.DateFormat.is24HourFormat(OnelittleAngelApplication.instance));

          tpd.dismissOnPause(true);
          tpd.setmUnselectedColor(settings.getInt(CardViewNative.DARKERRGB, 0));
          tpd.setAccentColor(settings.getInt(CardViewNative.DARKERRGB, 0));
        if(!context.isFinishing()) tpd.show(context.getSupportFragmentManager(), "Timepickerdialog");

          editor.putBoolean(Constants.SETTINGS_NOTIFICATION_TPD_OPENED, true);
          editor.commit();
          idPosition = position;

          tpd.setOnTimeSetListener((view, hourOfDay, minute, second) -> {
            isOkButtonPressed = true;
            hour = String.valueOf(hourOfDay);
            minutes = String.valueOf(minute);

            if (minute < 10)
              minutes = "0" + minute;

            if (position == 0) {
              editor.putString(Constants.QUOTE_NOTIFICATION_HOUR, hour);
              editor.commit();

              editor.putString(Constants.QUOTE_NOTIFICATION_MINUTE, minutes);
              editor.commit();

              if (!tpd.is24HourMode()) {
                if (hourOfDay >= 12) {
                  editor.putString(Constants.QUOTE_NOTIFICATION_AM_PM_VALUE, "PM ");
                  amPmValue = "PM ";
                } else {
                  editor.putString(Constants.QUOTE_NOTIFICATION_AM_PM_VALUE, "AM ");
                  amPmValue = "AM ";
                }
                editor.commit();
                notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " " + amPmValue);
              } else notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " ");
            }

            if (position == 1) {
              editor.putString(Constants.PICTURE_NOTIFICATION_HOUR, hour);
              editor.commit();

              editor.putString(Constants.PICTURE_NOTIFICATION_MINUTE, minutes);
              editor.commit();

              if (!tpd.is24HourMode()) {
                if (hourOfDay >= 12) {
                  editor.putString(Constants.PICTURE_NOTIFICATION_AM_PM_VALUE, "PM ");
                  amPmValue = "PM ";
                } else {
                  editor.putString(Constants.PICTURE_NOTIFICATION_AM_PM_VALUE, "AM ");
                  amPmValue = "AM ";
                }
                editor.commit();
                notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " " + amPmValue);
              } else notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " ");
            }

            if (position == 2) {
              editor.putString(Constants.BIOGRAPHY_NOTIFICATION_HOUR, hour);
              editor.commit();
              editor.putString(Constants.BIOGRAPHY_NOTIFICATION_MINUTE, minutes);
              editor.commit();

              if (!tpd.is24HourMode()) {
                if (hourOfDay >= 12) {
                  editor.putString(Constants.BIOGRAPHY_NOTIFICATION_AM_PM_VALUE, "PM ");
                  amPmValue = "PM ";
                } else {
                  editor.putString(Constants.BIOGRAPHY_NOTIFICATION_AM_PM_VALUE, "AM ");
                  amPmValue = "AM ";
                }
                editor.commit();
                notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " " + amPmValue);
              } else notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " ");
            }
          });

          tpd.setOnDismissListener(dialog -> {

            idPosition = position;

            if (isOkButtonPressed) {

              if (position == 0) {
                QuoteNotificationEventReceiver.setupAlarm(OnelittleAngelApplication.instance.getApplicationContext(), false, Integer.valueOf(hour), Integer.valueOf(minutes));
                editor.putBoolean(Constants.QUOTE_RECEIVE_NOTIFICATION, true);
              }

              if (position == 1) {
                PictureNotificationEventReceiver.setupAlarm(OnelittleAngelApplication.instance.getApplicationContext(), false, Integer.valueOf(hour), Integer.valueOf(minutes));
                editor.putBoolean(Constants.PICTURE_RECEIVE_NOTIFICATION, true);
              }

              if (position == 2) {
                BiographyNotificationEventReceiver.setupAlarm(OnelittleAngelApplication.instance.getApplicationContext(), false, Integer.valueOf(hour), Integer.valueOf(minutes));
                editor.putBoolean(Constants.BIOGRAPHY_RECEIVE_NOTIFICATION, true);
              }

              notificationsCb.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
              notificationsCb.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
              notificationTime.setVisibility(View.VISIBLE);

              notificationsfragment.ivReceiveNotifications.setDrawingCacheBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
              notificationsfragment.ivReceiveNotifications.setBackgroundColor(settings.getInt(CardViewNative.DARKERRGB, 0));
              editor.putBoolean(Constants.RECEIVE_NOTIFICATION, true);
              editor.commit();
              editor.putBoolean(Constants.SETTINGS_NOTIFICATION_TPD_OPENED, false);
              editor.commit();
              editor.putInt(Constants.SETTINGS_NOTIFICATION_TPD_OPENED_POSITION, -1);
              editor.commit();
              isOkButtonPressed = false;

              if(position == 0) {
                editor.putString(Constants.QUOTE_NOTIFICATION_HOUR, hour);
                editor.commit();

                editor.putString(Constants.QUOTE_NOTIFICATION_MINUTE, tpd.mMinuteView.getText().toString());
                editor.commit();

                if (!tpd.is24HourMode()) {
                  //Log.i("55", "hour : " + hour);
                  if (Integer.parseInt(hour) >= 12) {
                    editor.putString(Constants.QUOTE_NOTIFICATION_AM_PM_VALUE, "PM ");
                    amPmValue = "PM ";
                  } else {
                    editor.putString(Constants.QUOTE_NOTIFICATION_AM_PM_VALUE, "AM ");
                    amPmValue = "AM ";
                  }
                  editor.commit();
                }
              }

              if(position == 1) {
                editor.putString(Constants.PICTURE_NOTIFICATION_HOUR, hour);
                editor.commit();

                editor.putString(Constants.PICTURE_NOTIFICATION_MINUTE, tpd.mMinuteView.getText().toString());
                editor.commit();

                if (!tpd.is24HourMode()) {
                  if (Integer.parseInt(hour) >= 12) {
                    editor.putString(Constants.PICTURE_NOTIFICATION_AM_PM_VALUE, "PM ");
                    amPmValue = "PM ";
                  } else {
                    editor.putString(Constants.PICTURE_NOTIFICATION_AM_PM_VALUE, "AM ");
                    amPmValue = "AM ";
                  }
                  editor.commit();
                }

              }

              if(position == 2) {
                editor.putString(Constants.BIOGRAPHY_NOTIFICATION_HOUR, hour);
                editor.commit();
                editor.putString(Constants.BIOGRAPHY_NOTIFICATION_MINUTE, tpd.mMinuteView.getText().toString());
                editor.commit();

                if (!tpd.is24HourMode()) {
                  if (Integer.parseInt(hour) >= 12) {
                    editor.putString(Constants.BIOGRAPHY_NOTIFICATION_AM_PM_VALUE, "PM ");
                    amPmValue = "PM ";
                  } else {
                    editor.putString(Constants.BIOGRAPHY_NOTIFICATION_AM_PM_VALUE, "AM ");
                    amPmValue = "AM ";
                  }
                  editor.commit();
                }
              }

              if (!tpd.is24HourMode()) {
                notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " " + amPmValue);
              } else {
                notificationTime.setText(tpd.mHourView.getText().toString() + ":" + tpd.mMinuteView.getText().toString() + " ");
              }
            }

            isOrientationChanged = false;



            dialog.cancel();
          });
      }
    }
  }
}
