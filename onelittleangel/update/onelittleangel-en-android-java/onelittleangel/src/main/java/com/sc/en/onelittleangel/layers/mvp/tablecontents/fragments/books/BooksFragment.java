package com.sc.en.onelittleangel.layers.mvp.tablecontents.fragments.books;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.sc.en.onelittleangel.OnelittleAngelApplication;
import com.sc.en.onelittleangel.layers.mvp.tablecontents.fragments.BaseFragment;
import com.sc.en.onelittleangel.injector.PresenterInjector;
import com.sc.en.onelittleangel.R;
import com.sc.en.onelittleangel.layers.mvp.common.customs.cardviews.CardViewNative;
import com.sc.en.onelittleangel.layers.mvp.common.customs.viewpagers.ViewPagerNative;
import com.sc.en.onelittleangel.layers.mvp.common.utils.Constants;
import com.sc.en.onelittleangel.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.onelittleangel.layers.mvp.tablecontents.fragments.PresenterInterface;
import com.sc.en.onelittleangel.layers.mvp.tablecontents.fragments.ViewInterface;
import com.sc.en.onelittleangel.layers.mvp.tablecontents.models.AuthorBook;
import com.sc.en.onelittleangel.layers.mvp.tablecontents.models.base.adapters.listadapters.BaseListAdapter;
import com.sc.en.onelittleangel.layers.mvp.tablecontents.models.base.views.listviews.BaseListView;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BooksFragment extends BaseFragment implements ViewInterface {

  /***********************************************************
   *  Attributes
   **********************************************************/

  private List<AuthorBook> booksList = new ArrayList<>();
  private boolean mHasInflated = false;
  private SharedPreferences settings;
  private SharedPreferences.Editor editor;
  private boolean isLoading = false;
  private boolean isListviewAnimated = false;


    /***********************************************************
     *  Presenter
     **********************************************************/
  /**
   * The Presenter associated with that view
   */
  private PresenterInterface presenter=null;

  /***********************************************************
   *  Managing LifeCycle
   **********************************************************/

  /**
   *
   * @return the fragment instance
   */
  public static BooksFragment newInstance() {
    BooksFragment booksTBFragment = new BooksFragment();
    Bundle args = new Bundle();
    args.putString(TYPE, "books");
    booksTBFragment.setArguments(args);
    return booksTBFragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) type = getArguments().getString(TYPE);
    if(presenter == null) presenter = PresenterInjector.getBooksPresenter(this);

    boolean isOrientationChanged = savedInstanceState != null;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  protected void onCreateViewAfterViewStubInflated(View inflatedView, Bundle savedInstanceState) {
    mHasInflated = true;
    getHasInflated();
    if (inflatedView != null) {

      cvNoContents = (CardView) inflatedView.findViewById(R.id.cv_no_contents);

      baseListView = (BaseListView) inflatedView.findViewById(R.id.base_list_view);
      baseListAdapter = new BaseListAdapter<>(getActivity(), presenter, type, booksList);

      //if (settings == null) settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      //if (editor == null) editor = settings.edit();

      if(TableContentsActivity.isOrientationChanged || TableContentsActivity.navigationIdOpened || TableContentsActivity.isFromOtherActivity) {

        ViewPagerNative viewPagerNative = (ViewPagerNative) getActivity().findViewById(R.id.viewpager);
        if(viewPagerNative != null) viewPagerNative.setPadding(0, 0, 0, 0);
      } else {
        if(!isLoading) {
            resizeView();
            isListviewAnimated = true;
        }
      }
    }
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.base_fragment;
  }

  @Override
  protected int getViewStubLayoutResource() {
    return R.layout.viewstub_fragment;
  }

  @Override
  protected void afterViewStubInflated(View originalViewContainerWithViewStub) {
    super.afterViewStubInflated(originalViewContainerWithViewStub);
    (((TableContentsActivity) getActivity())).mObservableScrollView.smoothScrollTo(0, 0);

    if(presenter.getMovements().isEmpty()){
      isLoading = true;
      presenter.loadMovements();
      isListviewAnimated = false;
    } else isLoading = false;
  }

  @Override
  protected void populateViewForOrientation(LayoutInflater inflater) {
    ViewGroup viewGroup = (ViewGroup) getView();
    viewGroup.removeAllViewsInLayout();
    View view = inflater.inflate(R.layout.viewstub, viewGroup, false);
    viewGroup.addView(view);
    mHasInflated = false;
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
  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(presenter);
  }

  @Override
  public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(presenter);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    presenter = null;
    //baseListView = null;
    //baseListAdapter = null;
    booksList = null;
  }

  /***********************************************************
   *  Implement the ViewInterface
   **********************************************************/

  private void resizeView() {

    //  baseListView.setBackgroundColor(Color.CYAN);

    int heightToApply = 0;

    DisplayMetrics displaymetrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    int height = displaymetrics.heightPixels;
    int width = displaymetrics.widthPixels;

    if (Build.VERSION.SDK_INT >= 17) {
      //new pleasant way to get real metrics
      getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(displaymetrics);

      width = displaymetrics.widthPixels;
      height = displaymetrics.heightPixels;

    } else if (Build.VERSION.SDK_INT >= 14) {
      //reflection for this weird in-between time
      try {
        Method mGetRawH = Display.class.getMethod("getRawHeight");
        Method mGetRawW = Display.class.getMethod("getRawWidth");
        width = (Integer) mGetRawW.invoke(displaymetrics);
        height = (Integer) mGetRawH.invoke(displaymetrics);
      } catch (Exception e) {
        //this may not be 100% accurate, but it's all we've got
        //  realWidth = display.getWidth();
        width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
      }

    } else {
      //This should be close, as lower API devices should not have window navigation bars
      width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
      height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
    }



    LinearLayout.LayoutParams viewPagerParams = (LinearLayout.LayoutParams) ((TableContentsActivity) getActivity()).viewPager.getLayoutParams();

    if (settings == null) settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    if (editor == null) editor = settings.edit();

    if (presenter.getMovements() == null || presenter.getMovements().size() == 0) {
      cvNoContents.setVisibility(View.VISIBLE);
      editor.putInt(Constants.TABLE_CONTENTS_CURRENT_POSITION, 4).apply();

    }
    else cvNoContents.setVisibility(View.GONE);

    String[] openPositionArray = settings.getString(getResources().getString(R.string.books) ,"").split(" - ");

    if (!settings.getString(getResources().getString(R.string.books), "").equals("-1") &&
            !settings.getString(getResources().getString(R.string.books), "").equals("") && presenter.getMovements().size() != 0) {

      if (openPositionArray.length != 0 && openPositionArray.length == 1) {

        if (presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities != null) {
          heightToApply = (int) ((presenter.getMovements().size() + 1) * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.size())) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin));

          if (heightToApply < height - TableContentsActivity.headerHeight)
            heightToApply = height - TableContentsActivity.headerHeight;
        }

        if (presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).authorBooks != null) {

          heightToApply = (int) ((presenter.getMovements().size() + 1) * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + (settings.getInt(CardViewNative.MCONTENTCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).authorBooks.size())) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin));

          if (heightToApply < height - TableContentsActivity.headerHeight) heightToApply = height - TableContentsActivity.headerHeight;
        }
      } else if (openPositionArray.length != 0 && openPositionArray.length == 2) {

        if (presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities != null) {

          heightToApply = (int) ((presenter.getMovements().size() + 1) * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.size())) + ((presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities.size() * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0)) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)));

          if (heightToApply < height - TableContentsActivity.headerHeight)
            heightToApply = height - TableContentsActivity.headerHeight;
        }

        if (presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).authorBooks != null) {

          heightToApply = (int) ((presenter.getMovements().size() + 1) * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.size())) + ((presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).authorBooks.size() * settings.getInt(CardViewNative.MCONTENTCARDVIEWMEASUREDHEIGHT, 0)) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)));

          if (heightToApply < height - TableContentsActivity.headerHeight) heightToApply = height - TableContentsActivity.headerHeight;
        }
      } else if (openPositionArray.length != 0 && openPositionArray.length == 3) {

        if (presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities.get(Integer.parseInt(openPositionArray[2])).baseEntities != null) {

          heightToApply = (int) ((presenter.getMovements().size() + 1) * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.size())) + ((presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities.size() * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0)) + presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities.get(Integer.parseInt(openPositionArray[2])).baseEntities.size() * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)));

          if (heightToApply < height - TableContentsActivity.headerHeight)
            heightToApply = height - TableContentsActivity.headerHeight;
        }

        if (presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities.get(Integer.parseInt(openPositionArray[2])).authorBooks != null) {

          heightToApply = (int) ((presenter.getMovements().size() + 1) * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.size())) + ((presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities.size() * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0)) + presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities.get(Integer.parseInt(openPositionArray[2])).authorBooks.size() * settings.getInt(CardViewNative.MCONTENTCARDVIEWMEASUREDHEIGHT, 0) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)));

          if (heightToApply < height - TableContentsActivity.headerHeight) heightToApply = height - TableContentsActivity.headerHeight;
        }
      }
    } else {

      heightToApply = (int) (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT,0) * (presenter.getMovements().size() + 1) + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin));

      if (heightToApply < height - TableContentsActivity.headerHeight) heightToApply = (int) (height - TableContentsActivity .headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin));

      if (presenter.getMovements().size() == 1) {
        if (presenter.getMovements().get(0).baseEntities != null) {
          heightToApply = (int) ((presenter.getMovements().size() + 1) * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(0).baseEntities.size())) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin));

          if (heightToApply < height - TableContentsActivity.headerHeight)
            heightToApply = height - TableContentsActivity.headerHeight;
        }

        if (presenter.getMovements().get(0).authorBooks != null) {

          heightToApply = (int) ((presenter.getMovements().size() + 1) * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + (settings.getInt(CardViewNative.MCONTENTCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(0).authorBooks.size())) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin));

          if (heightToApply < height - TableContentsActivity.headerHeight) heightToApply = height - TableContentsActivity.headerHeight;
        }
      }
    }

    if (((TableContentsActivity) getActivity()).viewPager.getMeasuredHeight() != heightToApply) {

      if (heightToApply < height - TableContentsActivity.headerHeight) heightToApply = (int) (height - TableContentsActivity .headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin));

      viewPagerParams.height = (heightToApply < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + getResources().getDimension(R.dimen.activity_horizontal_margin)) : heightToApply) ;

      ((TableContentsActivity) getActivity()).viewPager.setLayoutParams(viewPagerParams);

      FrameLayout.LayoutParams baseListviewParams = (FrameLayout.LayoutParams) baseListView.getLayoutParams();
      baseListviewParams.height = (heightToApply < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + getResources().getDimension(R.dimen.activity_horizontal_margin)) : heightToApply) ;
      baseListView.setLayoutParams(baseListviewParams);
      //baseListView.setBackgroundColor(Color.RED);

      FrameLayout.LayoutParams rlHeaderParams = (FrameLayout.LayoutParams) ((TableContentsActivity) getActivity()).rlHeader.getLayoutParams();

      if (!settings.getString(getResources().getString(R.string.books), "").equals("-1") &&
              !settings.getString(getResources().getString(R.string.books), "").equals("")) {

        if (openPositionArray.length != 0 && openPositionArray.length == 1) {

          if (presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities != null) {

            heightToApply = (int) (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * (presenter.getMovements().size() + 1) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * 2 + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.size())) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin));

            //  if (heightToApply < height - TableContentsActivity.headerHeight) heightToApply = height - TableContentsActivity.headerHeight;
          }

          if (presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).authorBooks != null) {

            heightToApply = (int) (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * (presenter.getMovements().size() + 1) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * 2 + (settings.getInt(CardViewNative.MCONTENTCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).authorBooks.size())) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin));

            //  if (heightToApply < height - TableContentsActivity.headerHeight) heightToApply = height - TableContentsActivity.headerHeight;
          }
        } else if (openPositionArray.length != 0 && openPositionArray.length == 2) {

          if (presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities != null) {

            heightToApply = (int) ((presenter.getMovements().size() + 1) * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * 2 + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.size())) + ((presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities.size() * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0)) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)));


            if (heightToApply < height - TableContentsActivity.headerHeight)
              heightToApply = height - TableContentsActivity.headerHeight;
          }

          if (presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).authorBooks != null) {

            heightToApply = (int) ((presenter.getMovements().size() + 1) * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * 2 + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.size())) + ((presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).authorBooks.size() * settings.getInt(CardViewNative.MCONTENTCARDVIEWMEASUREDHEIGHT, 0)) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)));


            if (heightToApply < height - TableContentsActivity.headerHeight) heightToApply = height - TableContentsActivity.headerHeight;
          }
        } else if (openPositionArray.length != 0 && openPositionArray.length == 3) {

          if (presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities.get(Integer.parseInt(openPositionArray[2])).baseEntities != null) {

            heightToApply = (int) ((presenter.getMovements().size() + 1) * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * 2 + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.size())) + ((presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities.size() * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0)) + presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities.get(Integer.parseInt(openPositionArray[2])).baseEntities.size() * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)));

            if (heightToApply < height - TableContentsActivity.headerHeight)
              heightToApply = height - TableContentsActivity.headerHeight;
          }

          if (presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities.get(Integer.parseInt(openPositionArray[2])).authorBooks != null) {

            heightToApply = (int) ((presenter.getMovements().size() + 1) * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * 2 + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.size())) + ((presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities.size() * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0)) + presenter.getMovements().get(Integer.parseInt(openPositionArray[0])).baseEntities.get(Integer.parseInt(openPositionArray[1])).baseEntities.get(Integer.parseInt(openPositionArray[2])).authorBooks.size() * settings.getInt(CardViewNative.MCONTENTCARDVIEWMEASUREDHEIGHT, 0) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)));

            if (heightToApply < height - TableContentsActivity.headerHeight) heightToApply = height - TableContentsActivity.headerHeight;
          }
        }
      } else {

        heightToApply = (int) ((settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT,0) + getResources().getDimension(R.dimen.activity_horizontal_margin)) * (presenter.getMovements().size() + 1) + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) > height ?
                (int) ((settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT,0) + getResources().getDimension(R.dimen.activity_horizontal_margin)) * (presenter.getMovements().size() + 1) + TableContentsActivity.headerHeight + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin)) : height;

        if (presenter.getMovements().size() == 1) {
          if (presenter.getMovements().get(0).baseEntities != null) {
            heightToApply = (int) ((presenter.getMovements().size() + 1) * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(0).baseEntities.size())) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin));

            if (heightToApply < height - TableContentsActivity.headerHeight)
              heightToApply = height - TableContentsActivity.headerHeight;
          }

          if (presenter.getMovements().get(0).authorBooks != null) {

            heightToApply = (int) ((presenter.getMovements().size() + 1) * settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + TableContentsActivity.headerHeight + (settings.getInt(CardViewNative.MCARDVIEWMEASUREDHEIGHT, 0) + (settings.getInt(CardViewNative.MCONTENTCARDVIEWMEASUREDHEIGHT, 0) * presenter.getMovements().get(0).authorBooks.size())) + OnelittleAngelApplication.instance.getResources().getDimension(R.dimen.activity_horizontal_margin));

            if (heightToApply < height - TableContentsActivity.headerHeight) heightToApply = height - TableContentsActivity.headerHeight;
          }
        }
      }
      rlHeaderParams.height = (heightToApply < height ? height : heightToApply);

      ((TableContentsActivity) getActivity()).rlHeader.setLayoutParams(rlHeaderParams);

      FrameLayout.LayoutParams rlContainerParams = (FrameLayout.LayoutParams) ((TableContentsActivity) getActivity()).rlContainer.getLayoutParams();
      rlContainerParams.height = (heightToApply < height - TableContentsActivity.headerHeight ? (int) (height - TableContentsActivity.headerHeight + getResources().getDimension(R.dimen.activity_horizontal_margin)) : heightToApply);
      ((TableContentsActivity) getActivity()).rlContainer.setLayoutParams(rlContainerParams);
    }

    if(TableContentsActivity.isOrientationChanged || TableContentsActivity.navigationIdOpened || TableContentsActivity.isFromOtherActivity) {
      ViewPagerNative viewPagerNative = (ViewPagerNative) getActivity().findViewById(R.id.viewpager);
      if(viewPagerNative != null) viewPagerNative.setPadding(0, 0, 0, 0);
      TableContentsActivity.isOrientationChanged = false;
      TableContentsActivity.navigationIdOpened = false;
      TableContentsActivity.isFromOtherActivity = false;
//        resizeView();

      editor.putBoolean(Constants.IS_ANIMATING_LISTVIEW_ENABLED, true).commit();
      setScaleAdapter();
    } else {

      editor.putBoolean(Constants.IS_ANIMATING_LISTVIEW_ENABLED, true).commit();

      //  if (1 < settings.getInt(Constants.TABLE_CONTENTS_CURRENT_POSITION, 0)) setLeftAdapter();
      if (4 > settings.getInt(Constants.TABLE_CONTENTS_CURRENT_POSITION, 0)) setRightAdapter();
      else setScaleAdapter();

    }
    TableContentsActivity.fromFragment = getResources().getString(R.string.books);
    editor.putInt(Constants.TABLE_CONTENTS_CURRENT_POSITION, 4);
//editor.commit();
    editor.apply();

  }


  @Override
  public void updateMovements() {

    baseListAdapter.notifyDataSetChanged();

      if (!isListviewAnimated) {
          resizeView();
          isListviewAnimated = true;
      }
  }
}
