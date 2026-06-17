package com.sc.fr.hindouisme.layers.mvp.common.players;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.sc.fr.hindouisme.OnelittleAngelApplication;

/* SoundPoolPlayer: 

   custom extention from SoundPool with setOnCompletionListener
   without the low-efficiency drawback of MediaPlayer

   author: kenliu
*/
public class SoundPoolPlayer extends SoundPool {
    public Context context;
    private int soundId;
    private int streamId;
    private int resId;
    private String destFileName;
    private long duration;
    long mpDuration;
    private boolean isPlaying = false;
    private boolean loaded = false;
    public MediaPlayer player;
    private MediaPlayer.OnCompletionListener onCompletionListener;
  private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isPlaying) {
                isPlaying = false;
                //Log.d("debug", "ending..");
                if (onCompletionListener != null) {
                    onCompletionListener.onCompletion(null);
                //    onPreparedListener.onPrepared(null);
                }
            }
        }
    };

    //timing related
    private Handler handler;
    private long startTime;
  private long timeSinceStart = 0;

    public void pause() {
        if (streamId > 0) {
          long endTime = System.currentTimeMillis();
            timeSinceStart += endTime - startTime;
            super.pause(streamId);
            if (handler != null) {
                handler.removeCallbacks(runnable);
            }
            isPlaying = false;
        }
    }

    public void stop() {
        if (streamId > 0) {
            timeSinceStart = 0;
            super.stop(streamId);
            if (handler != null) {
                handler.removeCallbacks(runnable);
            }
            isPlaying = false;
        }
    }

    public void play() {
        if (!loaded) {
            loadAndPlay();
        } else {
            playIt();
        }
    }

    public static SoundPoolPlayer create(Context context, int resId) {
        SoundPoolPlayer player = new SoundPoolPlayer();
        player.context = context;
        player.resId = resId;
        return player;
    }

    public static SoundPoolPlayer create(Context context, String destFileName) {
        SoundPoolPlayer player = new SoundPoolPlayer();
        player.context = context;
        player.destFileName = destFileName;
        return player;
    }

    private SoundPoolPlayer() {
        super(1, AudioManager.STREAM_MUSIC, 0);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setOnPreparedListener(MediaPlayer.OnPreparedListener onPreparedListener){
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        this.onCompletionListener = onCompletionListener;

    }

    public MediaPlayer.OnCompletionListener getOnCompletionListener() {
        return onCompletionListener;
    }

    private void loadAndPlay() {
        duration = getSoundDuration(destFileName);
        soundId = super.load(/*context,*/ destFileName, 1);
        //Log.v("loaded", " : " + loaded);
        //Log.v("duration", " : " + duration);

        setOnLoadCompleteListener((soundPool, sampleId, status) -> {
        //    if(status == 0) {
                loaded = true;
                //Log.v("loaded", " : " + loaded);
                playIt();
        //    }
        });
    }

    private void playIt() {
        if (loaded && !isPlaying) {
            //Log.d("debug", "start playing..");
            if (timeSinceStart == 0) {
                streamId = super.play(soundId, 1f, 1f, 1, 0, 1f);
            } else {
                super.resume(streamId);
            }
            startTime = System.currentTimeMillis();
            handler = new Handler();
            handler.postDelayed(runnable, duration - timeSinceStart);
            isPlaying = true;
        }
    }

//    private long getSoundDuration(int rawId) {
//        MediaPlayer player = MediaPlayer.create(context, rawId);
//        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//            @Override
//            public boolean onError(MediaPlayer mp, int what, int extra) {
//                if (what == 100)
//                    mp.stop();
//                return false;
//            }
//        });
//        int duration = player.getDuration();
//        return duration;
//    }

    private long getSoundDuration(String destFileName) {

        Uri uri = Uri.parse("file://" + destFileName);
        MediaPlayer player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        try {
//            player.setDataSource(context, uri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        player = MediaPlayer.create(OnelittleAngelApplication.instance.getApplicationContext(), uri);

//        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                duration = mp.getDuration();
//                Log.v("duration onPrepared", " : " + duration);
//
//                soundId = SoundPoolPlayer.super.load(/*context,*/ destFileName, 1);
//                Log.v("loaded", " : " + loaded);
//                Log.v("duration", " : " + duration);
//
//            }
//        });
////
//        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//            @Override
//            public boolean onError(MediaPlayer mp, int what, int extra) {
//                if(what == 100)
//                //    duration = mp.getDuration();
//                    mp.release();
//                return false;
//            }
//        });
//
//
//        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                duration = mp.getDuration();
//                Log.v("duration onCompletion", " : " + duration);
//
//            }
//        });

        if (player != null) {
          duration = player.getDuration();
          player.release();
          player.setOnCompletionListener(null);
          player = null;
        }
        return duration;

    }
}
