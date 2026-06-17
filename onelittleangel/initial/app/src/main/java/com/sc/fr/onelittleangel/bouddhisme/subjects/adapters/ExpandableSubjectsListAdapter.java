package com.sc.fr.onelittleangel.bouddhisme.subjects.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity;
import com.sc.fr.onelittleangel.bouddhisme.entities.Courant;
import com.sc.fr.onelittleangel.bouddhisme.fragments.MyViewPager;
import com.sc.fr.onelittleangel.bouddhisme.views.ExpChildListView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class ExpandableSubjectsListAdapter extends BaseExpandableListAdapter {
    static MyViewPager viewPager;
    Animation animZoomIn;
    Animation animZoomOut;
    Map<String, Integer> childChildQuotesSize;
    private Map<String, List<String>> childCollections;
    Map<String, Integer> childQuotesSize;
    private Activity context;
    ExpandableSubjectsChildListAdapter expChildListAdapter;
    private ExpChildListView expChildListView;
    private ExpandableListView expListView;
    Map<String, Integer> groupQuotesSize;
    private TextView item;
    private Map<String, List<String>> laptopCollections;
    private List<String> laptops;
    private TextView quotes_size;
    private static int compteur = 0;
    static int REQUEST_EXIT = 1337;
    private List<String> groups = new ArrayList();
    private int previousItem = -1;
    private int previousItem2 = -1;
    private int groupPosi = -1;
    boolean expandGroup = false;

    public ExpandableSubjectsListAdapter(Activity context, ExpandableListView expListView, List<String> laptops, Map<String, Integer> groupQuotesSize, Map<String, List<String>> laptopCollections, Map<String, Integer> childQuotesSize, List<String> childs, Map<String, List<String>> childCollections, Map<String, Integer> childChildQuotesSize, MyViewPager viewPager2) {
        this.context = context;
        this.expListView = expListView;
        this.laptopCollections = laptopCollections;
        this.childCollections = childCollections;
        this.laptops = laptops;
        this.groupQuotesSize = groupQuotesSize;
        this.childQuotesSize = childQuotesSize;
        this.childChildQuotesSize = childChildQuotesSize;
        viewPager = viewPager2;
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.laptops.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int groupPosition) {
        return this.laptopCollections.get(this.laptops.get(groupPosition)).size() == 0 ? 0 : 1;
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int groupPosition) {
        return this.laptops.get(groupPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int groupPosition, int childPosition) {
        if (this.laptopCollections.get(this.laptops.get(groupPosition)).size() == 0) {
            return null;
        }
        return this.laptopCollections.get(this.laptops.get(groupPosition)).get(childPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return true;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        int i = R.drawable.collapse;
        String laptopName = (String) getGroup(groupPosition);
        this.groups.add(laptopName);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
            convertView = infalInflater.inflate(R.layout.subjects_group, (ViewGroup) null);
        }
        this.item = (TextView) convertView.findViewById(R.id.laptop);
        this.item.setTypeface(null, 1);
        this.item.setText(laptopName);
        this.quotes_size = (TextView) convertView.findViewById(R.id.quotes_size);
        ImageView img = (ImageView) convertView.findViewById(R.id.expandcollapse);
        img.setImageResource(isExpanded ? R.drawable.collapse : R.drawable.expand);
        if (getChildrenCount(groupPosition) == 0) {
            img.setImageResource(R.drawable.collapse);
        } else {
            if (!isExpanded) {
                i = R.drawable.expand;
            }
            img.setImageResource(i);
        }
        this.quotes_size.setText(" " + this.groupQuotesSize.get(laptopName) + "  ");
        View.OnClickListener l = new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.adapters.ExpandableSubjectsListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                final Intent i2 = new Intent(ExpandableSubjectsListAdapter.this.context, (Class<?>) QuotesTextActivity.class);
                if (v.getTag().equals("laptop") || v.getTag().equals("expandcollapse")) {
                    if (groupPosition != ExpandableSubjectsListAdapter.this.previousItem) {
                        ExpandableSubjectsListAdapter.this.expListView.expandGroup(groupPosition);
                        if (ExpandableSubjectsListAdapter.this.previousItem != -1) {
                            ExpandableSubjectsListAdapter.this.expListView.collapseGroup(ExpandableSubjectsListAdapter.this.previousItem);
                            ExpandableSubjectsListAdapter.this.expandGroup = false;
                            ExpandableSubjectsListAdapter.this.groupPosi = -1;
                            ExpandableSubjectsListAdapter.this.expListView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                        }
                        ExpandableSubjectsListAdapter.this.previousItem = groupPosition;
                        ExpandableSubjectsListAdapter.this.previousItem2 = -1;
                    } else if (groupPosition == ExpandableSubjectsListAdapter.this.previousItem) {
                        if (ExpandableSubjectsListAdapter.this.expListView.isGroupExpanded(groupPosition)) {
                            ExpandableSubjectsListAdapter.this.expListView.collapseGroup(groupPosition);
                            ExpandableSubjectsListAdapter.this.expandGroup = false;
                            ExpandableSubjectsListAdapter.this.groupPosi = -1;
                            ExpandableSubjectsListAdapter.this.expListView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                            ExpandableSubjectsListAdapter.this.previousItem2 = -1;
                        } else if (!ExpandableSubjectsListAdapter.this.expListView.isGroupExpanded(groupPosition)) {
                            ExpandableSubjectsListAdapter.this.expListView.expandGroup(groupPosition);
                        }
                        ExpandableSubjectsListAdapter.this.previousItem = groupPosition;
                    }
                }
                if (v.getTag().equals("quotes_size")) {
                    ExpandableSubjectsListAdapter.this.animZoomIn = AnimationUtils.loadAnimation(ExpandableSubjectsListAdapter.this.context.getApplicationContext(), R.anim.anim_zoomin);
                    ExpandableSubjectsListAdapter.this.animZoomOut = AnimationUtils.loadAnimation(ExpandableSubjectsListAdapter.this.context.getApplicationContext(), R.anim.anim_zoomout);
                    v.startAnimation(ExpandableSubjectsListAdapter.this.animZoomIn);
                    v.startAnimation(ExpandableSubjectsListAdapter.this.animZoomOut);
                    Animation animation = ExpandableSubjectsListAdapter.this.animZoomOut;
                    final int i3 = groupPosition;
                    animation.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.adapters.ExpandableSubjectsListAdapter.1.1
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation2) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation2) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation2) {
                            if (((List) ExpandableSubjectsListAdapter.this.laptopCollections.get(ExpandableSubjectsListAdapter.this.laptops.get(i3))).size() == 0 || ((List) ExpandableSubjectsListAdapter.this.laptopCollections.get(ExpandableSubjectsListAdapter.this.laptops.get(i3))).size() == 1) {
                                Courant courant = new Courant((String) ExpandableSubjectsListAdapter.this.laptops.get(i3));
                                i2.putExtra("type", "courant");
                                i2.putExtra("fromActivity", "subjects");
                                i2.putExtra("courant", courant);
                                i2.putExtra("total", "no");
                                ExpandableSubjectsListAdapter.this.context.startActivity(i2);
                                ExpandableSubjectsListAdapter.this.context.finish();
                                return;
                            }
                            Courant courant2 = new Courant((String) ExpandableSubjectsListAdapter.this.laptops.get(i3));
                            i2.putExtra("type", "courant");
                            i2.putExtra("fromActivity", "subjects");
                            i2.putExtra("courant", courant2);
                            i2.putExtra("total", "yes");
                            ExpandableSubjectsListAdapter.this.context.startActivity(i2);
                            ExpandableSubjectsListAdapter.this.context.finish();
                        }
                    });
                }
            }
        };
        this.quotes_size.setOnClickListener(l);
        this.item.setOnClickListener(l);
        convertView.setOnClickListener(null);
        img.setOnClickListener(l);
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parentg) {
        String laptop = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = this.context.getLayoutInflater();
        if (convertView == null) {
            inflater.inflate(R.layout.subjects_child, (ViewGroup) null);
        }
        this.expChildListView = new ExpChildListView(this.context);
        this.expChildListAdapter = new ExpandableSubjectsChildListAdapter(this.context, this.expListView, this.expChildListView, laptop, this.laptopCollections.get(this.groups.get(groupPosition)), this.childCollections, this.childQuotesSize, this.childChildQuotesSize, viewPager);
        this.expChildListView.setAdapter(this.expChildListAdapter);
        this.expChildListView.setDivider(null);
        this.expChildListView.setDividerHeight(0);
        this.expChildListView.setSelector(new StateListDrawable());
        this.expChildListView.setGroupIndicator(null);
        this.expChildListView.setVisibility(0);
        this.expChildListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.adapters.ExpandableSubjectsListAdapter.2
            @Override // android.widget.ExpandableListView.OnGroupExpandListener
            public void onGroupExpand(int groupPosition2) {
                ExpandableSubjectsListAdapter.compteur++;
                ExpandableSubjectsListAdapter.this.groupPosi = groupPosition2;
                if (!ExpandableSubjectsListAdapter.this.expandGroup) {
                    ExpandableSubjectsListAdapter.this.expandGroup = true;
                }
                if (ExpandableSubjectsListAdapter.compteur - 1 == ExpandableSubjectsListAdapter.this.expChildListView.getExpandableListAdapter().getGroupCount()) {
                    ExpandableSubjectsListAdapter.this.previousItem2 = -1;
                    ExpandableSubjectsListAdapter.compteur = 0;
                }
            }
        });
        this.expChildListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.adapters.ExpandableSubjectsListAdapter.3
            @Override // android.widget.ExpandableListView.OnGroupCollapseListener
            public void onGroupCollapse(int groupPosition2) {
                ExpandableSubjectsListAdapter.compteur++;
                ExpandableSubjectsListAdapter.this.groupPosi = groupPosition2;
                if (ExpandableSubjectsListAdapter.this.expandGroup) {
                    ExpandableSubjectsListAdapter.this.expandGroup = false;
                }
                if (ExpandableSubjectsListAdapter.compteur - 1 == ExpandableSubjectsListAdapter.this.expChildListView.getExpandableListAdapter().getGroupCount()) {
                    ExpandableSubjectsListAdapter.this.previousItem2 = -1;
                    ExpandableSubjectsListAdapter.compteur = 0;
                }
            }
        });
        if (this.expandGroup && this.previousItem2 == -1) {
            this.expChildListView.expandGroup(this.groupPosi);
        }
        if (!this.expandGroup && this.previousItem2 != -1) {
            this.expChildListView.collapseGroup(this.groupPosi);
            if (compteur == this.expChildListView.getExpandableListAdapter().getGroupCount()) {
                this.expandGroup = true;
                compteur = 0;
            }
        }
        this.expChildListView.setClickable(false);
        return this.expChildListView;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
