package com.sc.en.philosophy.layers.mvp.biography.fragments;

import android.graphics.Bitmap;

import com.sc.en.philosophy.OnelittleAngelApplication;
import com.sc.en.philosophy.R;
import com.sc.en.philosophy.layers.mvp.MotherPresenter;

import java.util.ArrayList;
import java.util.List;

public class PresentationPresenter extends MotherPresenter implements PresentationPresenterInterface {

  private boolean picturesAlreadyLoaded = false;
  private List<Bitmap> bitmapsView;

  public PresentationPresenter(PresentationViewInterface presentationViewInterface) {
    this.bitmapsView = new ArrayList<>();
  }

  @Override
  public void loadAuthorPictures(String author) {
    picturesAlreadyLoaded = true;
    OnelittleAngelApplication.instance.getServiceManager().getPicturesForCarouselService().loadBitmapsForCarouselAsync(OnelittleAngelApplication.instance.getResources().getString(R.string.authors), author).subscribe(this::initCarouselView);
  }

  @Override
  public void loadBookPictures(String book) {
    picturesAlreadyLoaded = true;
    OnelittleAngelApplication.instance.getServiceManager().getPicturesForCarouselService().loadBitmapsForCarouselAsync(OnelittleAngelApplication.instance.getResources().getString(R.string.books), book).subscribe(this::initCarouselView);
  }

  @Override
  public void loadMovementPictures(String movement) {
    picturesAlreadyLoaded = true;
    OnelittleAngelApplication.instance.getServiceManager().getPicturesForCarouselService().loadBitmapsForCarouselAsync(OnelittleAngelApplication.instance.getResources().getString(R.string.movements), movement).subscribe(this::initCarouselView);
  }

  @Override
  public List<Bitmap> getBitmapsView() {
    return bitmapsView;
  }

  @Override
  public void setBitmapsView(List<Bitmap> bitmapsView) {
    this.bitmapsView = bitmapsView;
  }

  @Override
  public void releaseBitmapsCarousel() {

  }

  @Override
  public boolean picturesAlreadyLoaded() {
    return picturesAlreadyLoaded;
  }

  private void initCarouselView(List<Bitmap> carouselViews) {
    this.bitmapsView = carouselViews;
  }
}
