package com.sc.fr.taoisme.layers.mvp.settings.fragments.movements;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.sc.fr.taoisme.OnelittleAngelApplication;
import com.sc.fr.taoisme.R;
import com.sc.fr.taoisme.layers.mvp.common.utils.Constants;
import com.sc.fr.taoisme.layers.mvp.settings.SettingsActivity;
import com.sc.fr.taoisme.layers.mvp.settings.fragments.BaseFragment;
import com.sc.fr.taoisme.layers.mvp.settings.fragments.movements.recycler.adapter.MovementsRecyclerAdapter;
import com.sc.fr.taoisme.layers.mvp.settings.viewpager.ViewPagerNative;
import com.sc.fr.taoisme.layers.mvp.tablecontents.TableContentsActivity;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class MovementsFragment extends BaseFragment implements MovementsViewInterface,MovementsRecyclerAdapter.Callbacks {

  /***********************************************************
   *  Attributes
   **********************************************************/

  private boolean mHasInflated = false;
  public LinearLayout llMovements;
  private CardView cardViewContainer;
  private RecyclerView movementsListView;
  private SharedPreferences settings;
  private SharedPreferences.Editor editor;
  private int height ;
  private int widthMeasureSpec ;
  private int heightMeasureSpec ;
  private MovementsRecyclerAdapter adapter;


  /***********************************************************
   *  Presenter
   **********************************************************/

  /**
   * The Presenter associated with that view
   */
  MovementsPresenterInterface presenter=null;

  /**
   *
   * @return the fragment instance
   */
  public static MovementsFragment newInstance() {
    MovementsFragment movementsFragment = new MovementsFragment();
    Bundle args = new Bundle();
    args.putString(TYPE, "movements");
    movementsFragment.setArguments(args);
    return movementsFragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    type = getArguments().getString(TYPE);
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  public void onStop() {
    super.onStop();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if(!SettingsActivity.isOrientationChanged) {

      movementsListView = null;
      cardViewContainer = null;
      settings = null;
      editor = null;
      if (adapter != null) {
        if (adapter.soundPool != null) {
          adapter.soundPool.release();
          adapter.soundPool.setOnLoadCompleteListener(null);
          adapter.soundPool = null;
        }
        adapter.context = null;
        adapter = null;
      }
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }


  @Override
  protected void onCreateViewAfterViewStubInflated(View inflatedView, Bundle savedInstanceState) {
    mHasInflated = true;

    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    editor = settings.edit();

    String movementList = settings.getString(Constants.MOVEMENTSLIST, "");

    if(movementList == null || movementList.equals("")) {
      String[] movementsName  = getResources().getStringArray(R.array.movements);
      for(int i = 0; i < getResources().getStringArray(R.array.movements).length; i++) {
        movementList += movementsName[i] + ";";
      }
      editor.putString(Constants.MOVEMENTSLIST, movementList);
//editor.commit();
      editor.apply();    }
    this.container.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

    if(inflatedView != null) {
      llMovements = (LinearLayout) inflatedView.findViewById(R.id.ll_movements_fragment);

      cardViewContainer = (CardView) inflatedView.findViewById(R.id.movements_container);
      ViewHelper.setAlpha(cardViewContainer, 0);
      movementsListView = (RecyclerView) inflatedView.findViewById(R.id.movements_dialog_list_view);
      movementsListView.setNestedScrollingEnabled(false);

      movementsListView.setLayoutManager(new LinearLayoutManager(OnelittleAngelApplication.instance));

      adapter = new MovementsRecyclerAdapter(OnelittleAngelApplication.instance);
      adapter.setCallbacks(this);
      movementsListView.setAdapter(adapter);

      DisplayMetrics displaymetrics = new DisplayMetrics();
      getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
      height = displaymetrics.heightPixels;
      int width = displaymetrics.widthPixels;

      widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
      heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

      ViewPagerNative viewPager = (ViewPagerNative) getActivity().findViewById(R.id.viewpager);
      LinearLayout headerContainer = (LinearLayout) getActivity().findViewById(R.id.container);

      viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

          llMovements.measure(widthMeasureSpec, heightMeasureSpec);

          LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewPager.getLayoutParams();

          if(llMovements.getMeasuredHeight() > height - SettingsActivity.headerHeight) {
            lp.height = llMovements.getMeasuredHeight() + SettingsActivity.headerHeight;
          } else {
            lp.height = (int) (height);
          }
          //lp.topMargin = SettingsActivity.headerHeight;

          viewPager.setLayoutParams(lp);

          LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) cardViewContainer.getLayoutParams();
          layoutParams.topMargin = SettingsActivity.headerHeight;
          cardViewContainer.setLayoutParams(layoutParams);

          cardViewContainer.setVisibility(View.VISIBLE);
          animate(cardViewContainer).alpha(1).setDuration(600).start();
          headerContainer.bringToFront();
          if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
          } else {
            viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
          }
        }
      });
    }
  }

  @Override
  protected void afterViewStubInflated(View originalViewContainerWithViewStub) {
    super.afterViewStubInflated(originalViewContainerWithViewStub);
  }

  @Override
  public String getFragmentType() {
    return type;
  }

  @Override
  public boolean getHasInflated() {
    return mHasInflated;
  }

  @Override
  protected int getViewStubLayoutResource() {
    return R.layout.movements_viewstub;
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.base_fragment;
  }

  @Override
  protected void populateViewForOrientation(LayoutInflater inflater) {
    ViewGroup viewGroup = (ViewGroup) getView();
    viewGroup.removeAllViewsInLayout();
    View view = inflater.inflate(R.layout.viewstub, viewGroup, false);
    viewGroup.addView(view);
    mHasInflated = false;
    getHasInflated();
  }

  @Override
  public void changeTypeFace() {

    if(adapter != null) {
      if(adapter.soundPool != null) {
        adapter.soundPool.release();
        adapter.soundPool.setOnLoadCompleteListener(null);
        adapter.soundPool = null;
      }
      adapter.context = null;
      adapter = null;
    }
  }

  /***********************************************************
   *  Implement the ViewInterface
   **********************************************************/
}
