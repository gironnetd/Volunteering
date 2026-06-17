package com.sc.fr.onelittleangel.layers.service.pictures.interfaces;

import android.graphics.Bitmap;

import com.sc.fr.onelittleangel.layers.service.MotherBusinessServiceInterface;

import java.util.List;

import io.reactivex.Observable;

public interface PicturesForCarouselInterface extends MotherBusinessServiceInterface {

  Observable<List<Bitmap>> loadBitmapsForCarouselAsync(String type, String name);

  void setBitmapsForCarousel();
}
