package com.sc.en.philosophy.layers.mvp.tablecontents.fragments.homepage.views.listviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ListAdapter;

import com.sc.en.philosophy.layers.mvp.tablecontents.fragments.homepage.adapters.listadapters.HomePageListAdapter;
import com.sc.en.philosophy.layers.mvp.tablecontents.models.base.views.listviews.BaseListView;
import com.sc.en.philosophy.R;
import com.sc.en.philosophy.layers.mvp.common.animations.listviews.swipedismiss.SwipeDismissListViewTouchListener;

public class HomePageListView extends BaseListView {

  public SwipeDismissListViewTouchListener dismissListViewTouchListener;

  private int listCardLayoutResourceID = R.layout.list_card_layout;

  public HomePageListView(Context context) {
    super(context);
    if(!isInEditMode())
      init(null, 0);
  }

  public HomePageListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    if(!isInEditMode())
      init(attrs, 0);
  }

  public HomePageListView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    if(!isInEditMode())
      init(attrs, defStyleAttr);
  }

//  public mHomePageListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//    super(context, attrs, defStyleAttr, defStyleRes);
//    if(!isInEditMode())
//      init(attrs, defStyleAttr);
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

  public void setExternalAdapter(ListAdapter adapter, HomePageListAdapter homePageListAdapter) {
    setAdapter(adapter);
    homePageListAdapter.setHomePageListView(this);
    homePageListAdapter.setRowLayoutId(listCardLayoutResourceID);
  }
}
