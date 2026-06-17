package com.sc.fr.confucianisme.layers.mvp.common.listeners.imageviews;

import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.sc.fr.confucianisme.OnelittleAngelApplication;
import com.sc.fr.confucianisme.R;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class ImageViewListener implements View.OnTouchListener {

  private final View view;
  private final float scale;
  private String settings;
  private Rect rect;
  private Callbacks mCallbacks;
  private SoundPool soundPool;


  public ImageViewListener(View view, float scale) {
    this.view = view;
    this.scale = scale;
  }

  public ImageViewListener(View view, String settings) {
    this.view = view;
    this.scale = 1.3f;
    this.settings = settings;
  }

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        animate(view).setDuration(100).scaleX(scale).scaleY(scale);
        if(view.getId() == R.id.txt_details || view.getId() == R.id.txt_author_book_name || view.getId() == R.id.txt_biography_faiths || view.getId() == R.id.iv_pictures /*|| view.getId() == R.id.iv_back*/ || view.getId() == R.id.iv_home) {
          soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
          int soundID = soundPool.load(OnelittleAngelApplication.instance.getBaseContext(), R.raw.unlock, 1);
          soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> soundPool1.play(soundID, 1f, 1f, 1, 0, 1f));
        }
        return true;
      case MotionEvent.ACTION_MOVE:
        if (!rect.contains(view.getLeft() + (int) event.getX(), view.getTop() + (int) event.getY())) {
          animate(view)
                  .setDuration(100)
                  .scaleX(1)
                  .scaleY(1)
                  .setListener(new AnimatorListenerAdapter() {
                    /**
                     * {@inheritDoc}
                     *
                     * @param animation
                     */
                    @Override
                    public void onAnimationEnd(Animator animation) {
                      super.onAnimationEnd(animation);
                    }
                  });
          return false;
        }
        return true;
      case MotionEvent.ACTION_UP:
        animate(view).setDuration(100).scaleX(1).scaleY(1)
                .setListener(new AnimatorListenerAdapter() {
                  /**
                   * {@inheritDoc}
                   *
                   * @param animation
                   */
                  @Override
                  public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    doAction(view, settings);
                    if(soundPool != null) {
                      soundPool.release();
                      soundPool.setOnLoadCompleteListener(null);
                    }
                  }
                });
        return false;
      default:
        animate(view)
                .setDuration(100)
                .scaleX(1)
                .scaleY(1)
                .setListener(new AnimatorListenerAdapter() {
                  /**
                   * {@inheritDoc}
                   *
                   * @param animation
                   */
                  @Override
                  public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                  }
                });
        return false;
    }
  }

  public interface Callbacks {

    void homeBackPressed();

    void backToHomePage();

    void showAddRemoveFavoritesDialog();

    void confirmAddRemoveDialog();

    void cancelAddRemoveDialog();

    void showSocialNetworksDialog();

    void showTypeFaceDialog();

    void showMicrophoneDialog();

    void openFullScreenMode();

    void startBiographyActivity();

    void startFaithsBiography();

    void arrowPrevious();

    void arrowNext();

    void searchQuote();

    void goToCommentsPlaystore();

    void cancelGoToCommentsPlaystore();

    void confirmTypefaceChoice();

    void cancelTypefaceChoice();

    void gotToTypefaceSettings();

    void goToTwitterActivity();


    void goToMailActivity();


    void goToFacebookActivity();

    void goToGooglePlusActivity();

    void cancelGoToSettingsAccount();

    void goToAccountSettings(String settings);

    void playQuoteMicrophone();

    void stopQuoteMicrophone();

    void pauseQuoteMicrophone();

    void previousQuoteMicrophone();

    void nextQuoteMicrophone();

    void sendMails();

    void goToFavoritesActivity();

    void togglePictures();

    void confirmWallpaperChanging(int resId);

    void cancelWallpaperChanging();

    void addEmail();

    void increaseTextSize();

    void decreaseTextSize();

    void toggleFloatingActionButton();
  }

  private void doAction(View view, String settings) {
    switch(view.getId()) {
      case R.id.fab_type_faces:
        mCallbacks.showTypeFaceDialog();
        break;
      case R.id.ic_ab_search:
        mCallbacks.searchQuote();
        break;
      case R.id.fab_microphone:
        mCallbacks.showMicrophoneDialog();
        break;
      case R.id.fab_social_networks:
        mCallbacks.goToMailActivity();
        break;
      case R.id.fab_txt_increase:
        mCallbacks.increaseTextSize();
        break;
      case R.id.fab_txt_decrease:
        mCallbacks.decreaseTextSize();
        break;
      case R.id.arrow_previous:
        mCallbacks.arrowPrevious();
        break;
      case R.id.arrow_next:
        mCallbacks.arrowNext();
        break;
      case R.id.iv_home:
        mCallbacks.backToHomePage();
        break;
      case R.id.iv_back:
        mCallbacks.homeBackPressed();
        break;
      case R.id.txt_details:
        mCallbacks.startBiographyActivity();
        break;
      case R.id.txt_author_book_name:
        mCallbacks.startBiographyActivity();
        break;
      case R.id.txt_biography_faiths:
        mCallbacks.startFaithsBiography();
        break;
      case R.id.add_to_favorites:
        mCallbacks.showAddRemoveFavoritesDialog();
        break;
      case R.id.remove_from_favorites:
        mCallbacks.showAddRemoveFavoritesDialog();
        break;
      case R.id.confirm_add_to_favorites:
        mCallbacks.confirmAddRemoveDialog();
        break;
      case R.id.cancel_add_to_favorites:
        mCallbacks.cancelAddRemoveDialog();
        break;
      case R.id.confirm_go_to_comments_playstore:
        mCallbacks.goToCommentsPlaystore();
        break;
      case R.id.cancel_go_to_comments_playstore:
        mCallbacks.cancelGoToCommentsPlaystore();
        break;
      case R.id.confirm_typeface_choice:
        mCallbacks.confirmTypefaceChoice();
        break;
      case R.id.cancel_typeface_choice:
        mCallbacks.cancelTypefaceChoice();
        break;
      case R.id.go_to_typeface_settings:
        mCallbacks.gotToTypefaceSettings();
        break;
      case R.id.txtn_go_to_type_face_settings:
        mCallbacks.gotToTypefaceSettings();
        break;
      case R.id.ic_ab_title:
        mCallbacks.homeBackPressed();
        break;
      case R.id.confirm_go_to_account:
        mCallbacks.goToAccountSettings(settings);
        break;
      case R.id.cancel_go_to_account:
        mCallbacks.cancelGoToSettingsAccount();
        break;
      case R.id.ic_microphone_previous:
        mCallbacks.previousQuoteMicrophone();
        break;
      case R.id.ic_microphone_stop:
        mCallbacks.stopQuoteMicrophone();
        break;
      case R.id.ic_microphone_pause:
        mCallbacks.pauseQuoteMicrophone();
        break;
      case R.id.ic_microphone_play:
        mCallbacks.playQuoteMicrophone();
        break;
      case R.id.ic_microphone_next:
        mCallbacks.nextQuoteMicrophone();
        break;
      case R.id.send_mails:
        mCallbacks.sendMails();
        break;
      case R.id.iv_pictures:
        mCallbacks.togglePictures();
        break;
      case R.id.iv_home_screen:
        mCallbacks.confirmWallpaperChanging(R.id.iv_home_screen);
        break;
      case R.id.iv_lock_screen:
        mCallbacks.confirmWallpaperChanging(R.id.iv_lock_screen);
        break;
      case R.id.iv_home_lock_screen:
        mCallbacks.confirmWallpaperChanging(R.id.iv_home_lock_screen);
        break;
      case R.id.iv_add_email:
        mCallbacks.addEmail();
        break;
      case R.id.title_main:
        mCallbacks.homeBackPressed();
        break;
    }
    animate(view).setListener(null);
  }

  public void setCallbacks(Callbacks listener) {
    mCallbacks = listener;
  }

}
