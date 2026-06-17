package com.sc.fr.christianisme.layers.mvp.settings.fragments.typefaces;

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
import com.sc.fr.christianisme.OnelittleAngelApplication;
import com.sc.fr.christianisme.R;
import com.sc.fr.christianisme.layers.mvp.settings.SettingsActivity;
import com.sc.fr.christianisme.layers.mvp.settings.fragments.BaseFragment;
import com.sc.fr.christianisme.layers.mvp.settings.fragments.typefaces.recycler.adapter.TypefacesRecyclerAdapter;
import com.sc.fr.christianisme.layers.mvp.settings.viewpager.ViewPagerNative;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class TypefacesFragment extends BaseFragment implements TypefacesViewInterface, TypefacesRecyclerAdapter.Callbacks {

  /***********************************************************
   *  Attributes
   **********************************************************/

  private boolean mHasInflated = false;
  private RecyclerView typeFacesListView;
  public CardView typesFacesCardView;
  public LinearLayout llTypefaces;
  private TypefacesRecyclerAdapter adapter;
  private int height ;
  private int widthMeasureSpec ;
  private int heightMeasureSpec ;

  /***********************************************************
   *  Presenter
   **********************************************************/

  /**
   * The Presenter associated with that view
   */

  /**
   *
   * @return the fragment instance
   */
  public static TypefacesFragment newInstance() {

    TypefacesFragment typefacesFragment = new TypefacesFragment();
    Bundle args = new Bundle();
    args.putString(TYPE, "typefaces");
    typefacesFragment.setArguments(args);
    return typefacesFragment;
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
      typesFacesCardView = null;
      typeFacesListView = null;
      if(adapter != null) {
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

    this.container.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    if(inflatedView != null) {
      llTypefaces = (LinearLayout) inflatedView.findViewById(R.id.ll_type_faces_fragment);
      typesFacesCardView = (CardView) inflatedView.findViewById(R.id.typefaces_card_view);
      ViewHelper.setAlpha(typesFacesCardView, 0);

      typeFacesListView = (RecyclerView) inflatedView.findViewById(R.id.typefaces_dialog_list_view);
      typeFacesListView.setNestedScrollingEnabled(false);
      typeFacesListView.setLayoutManager(new LinearLayoutManager(OnelittleAngelApplication.instance));
      if(adapter == null) {
        adapter = new TypefacesRecyclerAdapter(getActivity());
      }
      adapter.setCallbacks(this);
      typeFacesListView.setAdapter(adapter);

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

          llTypefaces.measure(widthMeasureSpec, heightMeasureSpec);
          LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewPager.getLayoutParams();

          if (!((SettingsActivity) getActivity()).isFromMailOrTypefaceSetings) {

//          if(llTypefaces.getMeasuredHeight() > height - SettingsActivity.headerHeight) {
            lp.height = llTypefaces.getMeasuredHeight() + SettingsActivity.headerHeight;
//          } else {
//            lp.height = (int) (height - SettingsActivity.headerHeight - getResources().getDimension(R.dimen.activity_horizontal_margin));
//          }
            //lp.topMargin = 0;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) typesFacesCardView.getLayoutParams();
            layoutParams.topMargin = SettingsActivity.headerHeight /*- SettingsActivity.rlNumberHeight*/;
            typesFacesCardView.setLayoutParams(layoutParams);
          } else {
            lp.height = (int) (llTypefaces.getMeasuredHeight() + SettingsActivity.rlNumberHeight);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) typesFacesCardView.getLayoutParams();
            layoutParams.topMargin = SettingsActivity.rlNumberHeight /*- SettingsActivity.rlNumberHeight*/;
            typesFacesCardView.setLayoutParams(layoutParams);
          }

          viewPager.setLayoutParams(lp);
          typesFacesCardView.setVisibility(View.VISIBLE);
          animate(typesFacesCardView).alpha(1).setDuration(600).start();
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
  public String getFragmentType() {
    return type;
  }

  @Override
  public boolean getHasInflated() {
    return mHasInflated;
  }

  @Override
  protected int getViewStubLayoutResource() {
    return R.layout.typefaces_viewstub;
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
