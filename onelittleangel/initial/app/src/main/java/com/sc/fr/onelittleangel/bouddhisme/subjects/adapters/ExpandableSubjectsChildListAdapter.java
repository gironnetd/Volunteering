package com.sc.fr.onelittleangel.bouddhisme.subjects.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sc.fr.onelittleangel.bouddhisme.activities.QuotesTextActivity;
import com.sc.fr.onelittleangel.bouddhisme.entities.Courant;
import com.sc.fr.onelittleangel.bouddhisme.fragments.MyViewPager;
import com.sc.fr.onelittleangel.bouddhisme.views.ExpChildListView;
import java.util.List;
import java.util.Map;

import com.sc.fr.onelittleangel.bouddhisme.R;

/* JADX INFO: loaded from: classes.dex */
public class ExpandableSubjectsChildListAdapter extends BaseExpandableListAdapter {
    static MyViewPager viewPager;
    Animation animZoomIn;
    Animation animZoomOut;
    Map<String, Integer> childChildQuotesSize;
    private Map<String, List<String>> childCollections;
    Map<String, Integer> childQuotesSize;
    private List<String> childs;
    private Activity context;
    private ExpChildListView expListView;
    int parentChildPosition;
    static int previousItem = -1;
    static int REQUEST_EXIT = 1337;
    int previousItem2 = -1;
    int lastExpandedPosition = -1;

    public ExpandableSubjectsChildListAdapter(Activity context) {
        this.context = context;
    }

    public ExpandableSubjectsChildListAdapter(Activity context, ExpandableListView expParent, ExpChildListView expListView, String child, List<String> childs, Map<String, List<String>> childCollections, Map<String, Integer> childQuotesSize, Map<String, Integer> childChildQuotesSize, MyViewPager viewPager2) {
        this.context = context;
        this.expListView = expListView;
        this.childs = childs;
        this.childCollections = childCollections;
        this.childQuotesSize = childQuotesSize;
        this.childChildQuotesSize = childChildQuotesSize;
        viewPager = viewPager2;
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        if (this.childs.size() == 0) {
            return 0;
        }
        return this.childs.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int groupPosition) {
        if (this.childs.size() == 0 || this.childCollections.get(this.childs.get(groupPosition)) == null) {
            return 0;
        }
        return this.childCollections.get(this.childs.get(groupPosition)).size();
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int groupPosition) {
        return this.childs.get(groupPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int groupPosition, int childPosition) {
        return this.childCollections.get(this.childs.get(groupPosition)).get(childPosition);
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
        final String laptopName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
            convertView = infalInflater.inflate(R.layout.subjects_child, (ViewGroup) null);
        }
        TextView quotes_child_size = (TextView) convertView.findViewById(R.id.quotes_size_child);
        quotes_child_size.setText(" " + this.childQuotesSize.get(laptopName) + " ");
        TextView item = (TextView) convertView.findViewById(R.id.child);
        item.setText(String.valueOf(laptopName) + " ");
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
        View.OnClickListener l = new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.adapters.ExpandableSubjectsChildListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                final Intent i2 = new Intent(ExpandableSubjectsChildListAdapter.this.context, (Class<?>) QuotesTextActivity.class);
                if (v.getTag().equals("child") || v.getTag().equals("expandcollapse")) {
                    if (groupPosition != ExpandableSubjectsChildListAdapter.previousItem && ExpandableSubjectsChildListAdapter.previousItem != -1) {
                        ExpandableSubjectsChildListAdapter.this.expListView.collapseGroup(ExpandableSubjectsChildListAdapter.previousItem);
                        ExpandableSubjectsChildListAdapter.this.expListView.expandGroup(groupPosition);
                        ExpandableSubjectsChildListAdapter.previousItem = groupPosition;
                    } else if (ExpandableSubjectsChildListAdapter.previousItem == groupPosition) {
                        if (ExpandableSubjectsChildListAdapter.this.expListView.isGroupExpanded(groupPosition)) {
                            ExpandableSubjectsChildListAdapter.this.expListView.collapseGroup(groupPosition);
                        } else {
                            ExpandableSubjectsChildListAdapter.this.expListView.expandGroup(groupPosition);
                        }
                    } else if (ExpandableSubjectsChildListAdapter.previousItem == -1) {
                        ExpandableSubjectsChildListAdapter.previousItem = groupPosition;
                        ExpandableSubjectsChildListAdapter.this.expListView.expandGroup(groupPosition);
                    }
                }
                if (v.getTag().equals("quotes_size_child")) {
                    ExpandableSubjectsChildListAdapter.this.animZoomIn = AnimationUtils.loadAnimation(ExpandableSubjectsChildListAdapter.this.context.getApplicationContext(), R.anim.anim_zoomin);
                    ExpandableSubjectsChildListAdapter.this.animZoomOut = AnimationUtils.loadAnimation(ExpandableSubjectsChildListAdapter.this.context.getApplicationContext(), R.anim.anim_zoomout);
                    v.startAnimation(ExpandableSubjectsChildListAdapter.this.animZoomIn);
                    v.startAnimation(ExpandableSubjectsChildListAdapter.this.animZoomOut);
                    Animation animation = ExpandableSubjectsChildListAdapter.this.animZoomOut;
                    final String str = laptopName;
                    animation.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.adapters.ExpandableSubjectsChildListAdapter.1.1
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation2) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation2) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation2) {
                            if (((List) ExpandableSubjectsChildListAdapter.this.childCollections.get(str)).size() == 0) {
                                Courant courant = new Courant(str);
                                i2.putExtra("type", "courant");
                                i2.putExtra("fromActivity", "subjects");
                                i2.putExtra("courant", courant);
                                i2.putExtra("total", "no");
                                ExpandableSubjectsChildListAdapter.this.context.startActivity(i2);
                                ExpandableSubjectsChildListAdapter.this.context.finish();
                                return;
                            }
                            Courant courant2 = new Courant(str);
                            i2.putExtra("type", "courant");
                            i2.putExtra("fromActivity", "subjects");
                            i2.putExtra("courant", courant2);
                            i2.putExtra("total", "yes");
                            ExpandableSubjectsChildListAdapter.this.context.startActivity(i2);
                            ExpandableSubjectsChildListAdapter.this.context.finish();
                        }
                    });
                }
            }
        };
        quotes_child_size.setOnClickListener(l);
        img.setOnClickListener(l);
        item.setOnClickListener(l);
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parentg) {
        final String laptop = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = this.context.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.subjects_child_child, (ViewGroup) null);
        }
        convertView.setOnClickListener(new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.adapters.ExpandableSubjectsChildListAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
            }
        });
        TextView item = (TextView) convertView.findViewById(R.id.child_child);
        item.setText(String.valueOf(laptop) + " ");
        TextView quotes_size = (TextView) convertView.findViewById(R.id.quotes_size_child_child);
        quotes_size.setText(" " + this.childChildQuotesSize.get(laptop) + " ");
        View.OnClickListener l = new View.OnClickListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.adapters.ExpandableSubjectsChildListAdapter.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                final Intent i = new Intent(ExpandableSubjectsChildListAdapter.this.context, (Class<?>) QuotesTextActivity.class);
                v.getTag().equals("child_child");
                if (v.getTag().equals("quotes_size_child_child")) {
                    ExpandableSubjectsChildListAdapter.this.animZoomIn = AnimationUtils.loadAnimation(ExpandableSubjectsChildListAdapter.this.context.getApplicationContext(), R.anim.anim_zoomin);
                    ExpandableSubjectsChildListAdapter.this.animZoomOut = AnimationUtils.loadAnimation(ExpandableSubjectsChildListAdapter.this.context.getApplicationContext(), R.anim.anim_zoomout);
                    v.startAnimation(ExpandableSubjectsChildListAdapter.this.animZoomIn);
                    v.startAnimation(ExpandableSubjectsChildListAdapter.this.animZoomOut);
                    Animation animation = ExpandableSubjectsChildListAdapter.this.animZoomOut;
                    final String str = laptop;
                    animation.setAnimationListener(new Animation.AnimationListener() { // from class: com.sc.fr.onelittleangel.bouddhisme.subjects.adapters.ExpandableSubjectsChildListAdapter.3.1
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation2) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation2) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation2) {
                            Courant courant = new Courant(str);
                            i.putExtra("type", "courant");
                            i.putExtra("fromActivity", "subjects");
                            i.putExtra("courant", courant);
                            i.putExtra("total", "no");
                            ExpandableSubjectsChildListAdapter.this.context.startActivity(i);
                            ExpandableSubjectsChildListAdapter.this.context.finish();
                        }
                    });
                }
            }
        };
        quotes_size.setOnClickListener(l);
        item.setOnClickListener(l);
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override // android.widget.BaseExpandableListAdapter, android.widget.ExpandableListAdapter
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override // android.widget.BaseExpandableListAdapter, android.widget.ExpandableListAdapter
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }
}
