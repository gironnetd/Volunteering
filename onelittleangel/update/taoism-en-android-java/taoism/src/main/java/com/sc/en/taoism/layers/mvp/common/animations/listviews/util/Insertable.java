package com.sc.en.taoism.layers.mvp.common.animations.listviews.util;

import android.support.annotation.NonNull;

public interface Insertable<T> {

  void add(int index, @NonNull T item);
}