package com.sc.fr.bouddhisme.layers.mvp.tablecontents.models.base.views.listviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.sc.fr.bouddhisme.R;
import com.sc.fr.bouddhisme.layers.mvp.tablecontents.models.base.adapters.listadapters.BaseListAdapter;

public class BaseListView extends ListView {

  public BaseListAdapter mBaseListAdapter;

  private int listCardLayoutResourceID = R.layout.list_card_layout;

  public BaseListView(Context context) {
    super(context);
    init(null, 0);
  }

  public BaseListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0);
  }

  public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs, defStyleAttr);
  }

//  public mBaseListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//    super(context, attrs, defStyleAttr, defStyleRes);
//    init(attrs, defStyleAttr);
//  }

  //--------------------------------------------------------------------------
  // Init
  //--------------------------------------------------------------------------

  private void init(AttributeSet attrs, int defStyle) {

    //Init attrs
    initAttrs(attrs, defStyle);

    //Set divider to 0dp
    setDividerHeight(0);

  }

  private void initAttrs(AttributeSet attrs, int defStyle) {

    listCardLayoutResourceID = R.layout.list_card_layout;

    TypedArray a = getContext().getTheme().obtainStyledAttributes(
      attrs, R.styleable.card_options, defStyle, defStyle);

    try {
      listCardLayoutResourceID = a.getResourceId(R.styleable.card_options_list_card_layout_resourceID,
        listCardLayoutResourceID);
    } finally {
      a.recycle();
    }
  }

  public void setAdapter(BaseListAdapter adapter) {
    super.setAdapter(adapter);
    //Set Layout used by items
    if(adapter != null) {
      adapter.setRowLayoutId(listCardLayoutResourceID);
      adapter.setBaseListView(this);
    }
    mBaseListAdapter = adapter;
  }

  public void setExternalAdapter(ListAdapter adapter, BaseListAdapter mBaseListAdapter) {
    setAdapter(adapter);
    if(mBaseListAdapter != null) {
      this.mBaseListAdapter = mBaseListAdapter;
      mBaseListAdapter.setBaseListView(this);
      mBaseListAdapter.setRowLayoutId(listCardLayoutResourceID);
    }
  }


}
