package com.sc.en.hindouism.layers.mvp.tablecontents.models.base.views.recyclerviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.sc.en.hindouism.layers.mvp.tablecontents.models.base.adapters.recycleradapters.BaseRecyclerAdapter;
import com.sc.en.hindouism.R;

public class BaseRecyclerView extends RecyclerView {

  /**
   * Card Adapter
   */
  private BaseRecyclerAdapter adapter;

  //--------------------------------------------------------------------------
  // Custom Attrs
  //--------------------------------------------------------------------------

  //--------------------------------------------------------------------------
  // Constructors
  //--------------------------------------------------------------------------

  public BaseRecyclerView(Context context) {
    super(context);
    if (!isInEditMode()) {
      init(context, null, 0);
    }
  }

  public BaseRecyclerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    if (!isInEditMode()) {
      init(context, attrs, 0);
    }
  }

  public BaseRecyclerView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    if (!isInEditMode()) {
      init(context, attrs, defStyle);
    }
  }

  //--------------------------------------------------------------------------
  // Init
  //--------------------------------------------------------------------------

  private void init(Context context, AttributeSet attrs, int defStyle) {

    if (!isInEditMode()) {
      //Init attrs
      initAttrs(context, attrs, defStyle);
    }
  }

  private void initAttrs(Context context, AttributeSet attrs, int defStyle) {

    /*
    Default layout to apply to card
   */

    TypedArray a = context.getTheme().obtainStyledAttributes(
      attrs, R.styleable.card_options, defStyle, defStyle);

    try {

      int arrayIds = a.getResourceId(R.styleable.card_options_list_card_layout_resourceIDs, 0);
      if (arrayIds > 0) {
        TypedArray layouts = context.getResources().obtainTypedArray(arrayIds);

        int[] listCardLayoutResourceIDs = new int[layouts.length()];
        for (int i = 0; i < layouts.length(); i++) {
          listCardLayoutResourceIDs[i] = layouts.getResourceId(i, R.layout.list_card_layout);
        }
        layouts.recycle();
      }
    } finally {
      a.recycle();
    }
  }

  //--------------------------------------------------------------------------
  // Adapter
  //--------------------------------------------------------------------------


  public BaseRecyclerAdapter getAdapter() {
    return adapter;
  }

  public void setAdapter(BaseRecyclerAdapter adapter) {
    super.setAdapter(adapter);
    this.adapter = adapter;
  }
}
