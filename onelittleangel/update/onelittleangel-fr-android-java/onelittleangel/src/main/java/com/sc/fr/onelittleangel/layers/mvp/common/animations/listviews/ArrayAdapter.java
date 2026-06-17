package com.sc.fr.onelittleangel.layers.mvp.common.animations.listviews;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;

import com.sc.fr.onelittleangel.layers.mvp.common.animations.listviews.util.Insertable;
import com.sc.fr.onelittleangel.layers.mvp.common.animations.listviews.util.Swappable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public abstract class ArrayAdapter<T> extends BaseAdapter implements Swappable, Insertable<T> {

  @NonNull
  private final List<T> mItems;

  private BaseAdapter mDataSetChangedSlavedAdapter;

  protected ArrayAdapter() {
    this(null);
  }

  protected ArrayAdapter(@Nullable List<T> objects) {
    if (objects != null) {
      mItems = objects;
    } else {
      mItems = new ArrayList<>();
    }
  }

  @Override
  public int getCount() {
    return mItems.size();
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  @NonNull
  public T getItem(int position) {
    return mItems.get(position);
  }

  @NonNull
  public List<T> getItems() {
    return mItems;
  }

  public boolean add(@NonNull T object) {
    boolean result = mItems.add(object);
    notifyDataSetChanged();
    return result;
  }

  @Override
  public void add(int index, @NonNull T item) {
    mItems.add(index, item);
    notifyDataSetChanged();
  }

  public boolean addAll(@NonNull Collection<? extends T> collection) {
    boolean result = mItems.addAll(collection);
    notifyDataSetChanged();
    return result;
  }

  public boolean contains(T object) {
    return mItems.contains(object);
  }

  public void clear() {
    mItems.clear();
    notifyDataSetChanged();
  }

  public boolean remove(@NonNull Object object) {
    boolean result;
    result = mItems.remove(object);
    notifyDataSetChanged();
    return result;
  }

  @NonNull
  public T remove(int location) {
    T result = mItems.remove(location);
    notifyDataSetChanged();
    return result;
  }

  @Override
  public void swapItems(int positionOne, int positionTwo) {
    T firstItem = mItems.set(positionOne, getItem(positionTwo));
    notifyDataSetChanged();
    mItems.set(positionTwo, firstItem);
  }

  public void propagateNotifyDataSetChanged(@NonNull BaseAdapter slavedAdapter) {
    mDataSetChangedSlavedAdapter = slavedAdapter;
  }

  @Override
  public void notifyDataSetChanged() {
    super.notifyDataSetChanged();
    if (mDataSetChangedSlavedAdapter != null) {
      mDataSetChangedSlavedAdapter.notifyDataSetChanged();
    }
  }
}
