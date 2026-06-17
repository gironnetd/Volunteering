package com.sc.fr.onelittleangel.bouddhisme.menu.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sc.fr.onelittleangel.bouddhisme.activities.MainActivity;
import com.sc.fr.onelittleangel.bouddhisme.fragments.MyViewPager;
import com.sc.fr.onelittleangel.bouddhisme.views.CustomListView;
import java.util.ArrayList;

import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class MenuListAdapter extends BaseAdapter {
    static MyViewPager viewPager;
    Animation animZoomIn;
    Animation animZoomOut;
    private Activity mContext;
    Typeface tf;
    private ArrayList<String> titles;

    public MenuListAdapter(Activity mContext, ArrayList<String> titles, CustomListView customListview) {
        this.mContext = mContext;
        this.titles = titles;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        if (this.titles != null) {
            return this.titles.size();
        }
        return 0;
    }

    @Override // android.widget.Adapter
    public Object getItem(int arg0) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override // android.widget.Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        View view = convertView;
        this.animZoomIn = AnimationUtils.loadAnimation(this.mContext, R.anim.anim_zoomin);
        this.animZoomOut = AnimationUtils.loadAnimation(this.mContext, R.anim.anim_zoomout);
        if (view == null) {
            try {
                view = inflater.inflate(R.layout.menu_group, (ViewGroup) null);
            } catch (Exception e) {
            }
        }
        TextView title = (TextView) view.findViewById(R.id.menu_text);
        this.tf = Typeface.createFromAsset(this.mContext.getAssets(), "fonts/mtcorsva.ttf");
        title.setText(this.titles.get(position).toString());
        title.setGravity(17);
        title.setOnClickListener(new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.menu.adapters.MenuListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        v.startAnimation(MenuListAdapter.this.animZoomIn);
                        v.startAnimation(MenuListAdapter.this.animZoomOut);
                        MenuListAdapter.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.menu.adapters.MenuListAdapter.1.1
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                MainActivity.getPager().setCurrentItem(2);
                            }
                        });
                        break;
                    case 1:
                        v.startAnimation(MenuListAdapter.this.animZoomIn);
                        v.startAnimation(MenuListAdapter.this.animZoomOut);
                        MenuListAdapter.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.menu.adapters.MenuListAdapter.1.2
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                MainActivity.getPager().setCurrentItem(3);
                            }
                        });
                        break;
                    case 2:
                        v.startAnimation(MenuListAdapter.this.animZoomIn);
                        v.startAnimation(MenuListAdapter.this.animZoomOut);
                        MenuListAdapter.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.menu.adapters.MenuListAdapter.1.3
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                MainActivity.getPager().setCurrentItem(4);
                            }
                        });
                        break;
                    case 3:
                        v.startAnimation(MenuListAdapter.this.animZoomIn);
                        v.startAnimation(MenuListAdapter.this.animZoomOut);
                        MenuListAdapter.this.animZoomOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.menu.adapters.MenuListAdapter.1.4
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                MainActivity.getPager().setCurrentItem(5);
                            }
                        });
                        break;
                }
            }
        });
        return view;
    }
}
