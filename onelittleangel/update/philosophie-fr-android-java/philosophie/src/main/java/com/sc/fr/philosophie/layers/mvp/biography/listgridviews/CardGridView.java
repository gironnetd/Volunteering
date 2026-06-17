/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package com.sc.fr.philosophie.layers.mvp.biography.listgridviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.sc.fr.philosophie.R;
import com.sc.fr.philosophie.layers.mvp.common.animations.listviews.appearance.AnimationAdapter;
import com.sc.fr.philosophie.layers.mvp.common.animations.listviews.appearance.simple.SwingBottomInAnimationAdapter;

public class CardGridView extends GridView  {

    protected static String TAG = "CardGridView";

    /**
     *  Card Grid Array Adapter
     */
    private CardGridArrayAdapter mAdapter;
  //public Callbacks mCallbacks;

//    /**
//     * Card Cursor Adapter
//     */
//    protected CardGridCursorAdapter mCursorAdapter;

    //--------------------------------------------------------------------------
    // Custom Attrs
    //--------------------------------------------------------------------------

    /**
     * Default layout to apply to card
     */
    private int list_card_layout_resourceID = R.layout.gridviewlist_carrousel;

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------


    public CardGridView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CardGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    //--------------------------------------------------------------------------
    // Init
    //--------------------------------------------------------------------------

    /**
     * Initialize
     *
     * @param attrs
     * @param defStyle
     */
    private void init(AttributeSet attrs, int defStyle){
        //Init attrs
        initAttrs(attrs,defStyle);
    }

    /**
     * Init custom attrs.
     *
     * @param attrs
     * @param defStyle
     */
    private void initAttrs(AttributeSet attrs, int defStyle) {

        list_card_layout_resourceID = R.layout.gridviewlist_carrousel;

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.card_options, defStyle, defStyle);

        try {
            list_card_layout_resourceID = a.getResourceId(R.styleable.card_options_list_card_layout_resourceID, this.list_card_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    //--------------------------------------------------------------------------
    // Adapter
    //--------------------------------------------------------------------------

    /**
     * Forces to use a {@link CardGridArrayAdapter}
     *
     * @param adapter
     */
//    @Override
//    public void setAdapter(ListAdapter adapter) {
//        if (adapter instanceof CardGridArrayAdapter){
//            setAdapter((CardGridArrayAdapter)adapter);
//        }else if (adapter instanceof CardGridCursorAdapter){
//            setAdapter((CardGridCursorAdapter)adapter);
//        }else{
//            Log.w(TAG,"You are using a generic adapter. Pay attention: your adapter has to call cardGridArrayAdapter#getView method." );
//            super.setAdapter(adapter);
//        }
//    }

    /**
     * Set {@link CardGridArrayAdapter} and layout used by items in ListView
     *
     * @param adapter {@link CardGridArrayAdapter}
     */
    public void setAdapter(CardGridArrayAdapter adapter) {
        super.setAdapter(adapter);

        //Set Layout used by items
        adapter.setRowLayoutId(list_card_layout_resourceID);
        adapter.setCardGridView(this);
        mAdapter=adapter;
    }

    private void setExternalAdapter(ListAdapter adapter, CardGridArrayAdapter cardGridArrayAdapter) {

        setAdapter(adapter);

        mAdapter=cardGridArrayAdapter;
        mAdapter.setCardGridView(this);
        mAdapter.setRowLayoutId(list_card_layout_resourceID);
    }

    public void setBottomAdapter() {
        AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(mAdapter);
        animCardArrayAdapter.setAbsListView(this);
        if(this != null) setExternalAdapter(animCardArrayAdapter, mAdapter);
    }

    public void setAnimationEnded(boolean animationEnded) {
    }

    public CardGridArrayAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(CardGridArrayAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public int getNumColumnsCompat() {
        if (Build.VERSION.SDK_INT >= 11) {
            return getNumColumnsCompat11();

        } else {
            int columns = 0;
            int children = getChildCount();
            if (children > 0) {
                int width = getChildAt(0).getMeasuredWidth();
                if (width > 0) {
                    columns = getWidth() / width;
                }
            }
            return columns > 0 ? columns : AUTO_FIT;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private int getNumColumnsCompat11() {
        return getNumColumns();
    }


    //--------------------------------------------------------------------------
    // Expand and Collapse animator
    // Don't use this animator in a grid.
    // All cells in the same row should expand/collapse a hidden area of same dimensions.
    //--------------------------------------------------------------------------
}
