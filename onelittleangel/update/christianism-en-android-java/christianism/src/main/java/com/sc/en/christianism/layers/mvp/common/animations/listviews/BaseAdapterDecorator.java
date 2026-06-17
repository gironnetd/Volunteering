package com.sc.en.christianism.layers.mvp.common.animations.listviews;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;

import com.sc.en.christianism.layers.mvp.common.animations.listviews.util.Swappable;
import com.sc.en.christianism.layers.mvp.common.animations.listviews.util.AbsListViewWrapper;
import com.sc.en.christianism.layers.mvp.common.animations.listviews.util.ListViewWrapper;
import com.sc.en.christianism.layers.mvp.common.animations.listviews.util.Insertable;
import com.sc.en.christianism.layers.mvp.common.animations.listviews.util.ListViewWrapperSetter;


public abstract class BaseAdapterDecorator extends BaseAdapter implements SectionIndexer, Swappable, Insertable, ListViewWrapperSetter {

  @NonNull
  private final BaseAdapter mDecoratedBaseAdapter;

  @Nullable
  private ListViewWrapper mListViewWrapper;

  protected BaseAdapterDecorator(@NonNull BaseAdapter baseAdapter) {
    mDecoratedBaseAdapter = baseAdapter;
  }

  @NonNull
  protected BaseAdapter getDecoratedBaseAdapter() {
    return mDecoratedBaseAdapter;
  }

  @NonNull
  protected BaseAdapter getRootAdapter() {
    BaseAdapter adapter = mDecoratedBaseAdapter;
    while (adapter instanceof BaseAdapterDecorator) {
      adapter = ((BaseAdapterDecorator) adapter).getDecoratedBaseAdapter();
    }
    return adapter;
  }

  public void setAbsListView(@NonNull AbsListView absListView) {
    setListViewWrapper(new AbsListViewWrapper(absListView));
  }

  @Nullable
  protected ListViewWrapper getListViewWrapper() {
    return mListViewWrapper;
  }

  @Override
  public void setListViewWrapper(@NonNull ListViewWrapper listViewWrapper) {
    mListViewWrapper = listViewWrapper;

    if (mDecoratedBaseAdapter instanceof ListViewWrapperSetter) {
      ((ListViewWrapperSetter) mDecoratedBaseAdapter).setListViewWrapper(listViewWrapper);
    }
  }

  @Override
  public int getCount() {
    return mDecoratedBaseAdapter.getCount();
  }

  @Override
  public Object getItem(int position) {
    return mDecoratedBaseAdapter.getItem(position);
  }

  @Override
  public long getItemId(int position) {
    return mDecoratedBaseAdapter.getItemId(position);
  }

  @Override
  @NonNull
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    return mDecoratedBaseAdapter.getView(position, convertView, parent);
  }

  @Override
  public boolean areAllItemsEnabled() {
    return mDecoratedBaseAdapter.areAllItemsEnabled();
  }

  @Override
  @NonNull
  public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    return mDecoratedBaseAdapter.getDropDownView(position, convertView, parent);
  }

  @Override
  public int getItemViewType(int position) {
    return mDecoratedBaseAdapter.getItemViewType(position);
  }

  @Override
  public int getViewTypeCount() {
    return mDecoratedBaseAdapter.getViewTypeCount();
  }

  @Override
  public boolean hasStableIds() {
    return mDecoratedBaseAdapter.hasStableIds();
  }

  @Override
  public boolean isEmpty() {
    return mDecoratedBaseAdapter.isEmpty();
  }

  @Override
  public boolean isEnabled(int position) {
    return mDecoratedBaseAdapter.isEnabled(position);
  }

  @Override
  public void notifyDataSetChanged() {
    if (!(mDecoratedBaseAdapter instanceof ArrayAdapter<?>)) {
      // fix #35 dirty trick !
      // leads to an infinite loop when trying because ArrayAdapter triggers notifyDataSetChanged itself

      mDecoratedBaseAdapter.notifyDataSetChanged();
    }
  }

  @SuppressWarnings("UnusedDeclaration")
  public void notifyDataSetChanged(boolean force) {
    if (force || !(mDecoratedBaseAdapter instanceof ArrayAdapter<?>)) {
      // leads to an infinite loop when trying because ArrayAdapter triggers notifyDataSetChanged itself

      mDecoratedBaseAdapter.notifyDataSetChanged();
    }
  }

  @Override
  public void notifyDataSetInvalidated() {
    mDecoratedBaseAdapter.notifyDataSetInvalidated();
  }

  @Override
  public void registerDataSetObserver(@NonNull DataSetObserver observer) {
    mDecoratedBaseAdapter.registerDataSetObserver(observer);
  }

  @Override
  public void unregisterDataSetObserver(@NonNull DataSetObserver observer) {
    mDecoratedBaseAdapter.unregisterDataSetObserver(observer);
  }

  @Override
  public int getPositionForSection(int sectionIndex) {
    int result = 0;
    if (mDecoratedBaseAdapter instanceof SectionIndexer) {
      result = ((SectionIndexer) mDecoratedBaseAdapter).getPositionForSection(sectionIndex);
    }
    return result;
  }

  @Override
  public int getSectionForPosition(int position) {
    int result = 0;
    if (mDecoratedBaseAdapter instanceof SectionIndexer) {
      result = ((SectionIndexer) mDecoratedBaseAdapter).getSectionForPosition(position);
    }
    return result;
  }

  @Override
  @NonNull
  public Object[] getSections() {
    Object[] result = new Object[0];
    if (mDecoratedBaseAdapter instanceof SectionIndexer) {
      result = ((SectionIndexer) mDecoratedBaseAdapter).getSections();
    }
    return result;
  }

  @Override
  public void swapItems(int positionOne, int positionTwo) {
    if (mDecoratedBaseAdapter instanceof Swappable) {
      ((Swappable) mDecoratedBaseAdapter).swapItems(positionOne, positionTwo);
    } else {
      //Log.w("ListViewAnimations", "Warning: swapItems called on an adapter that does not implement Swappable!");
    }
  }

  @Override
  public void add(int index, @NonNull Object item) {
    if (mDecoratedBaseAdapter instanceof Insertable) {
      //noinspection rawtypes
      ((Insertable) mDecoratedBaseAdapter).add(index, item);
    } else {
      //Log.w("ListViewAnimations", "Warning: add called on an adapter that does not implement Insertable!");
    }
  }
}