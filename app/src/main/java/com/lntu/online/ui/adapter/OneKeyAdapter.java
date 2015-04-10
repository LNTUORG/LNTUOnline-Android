package com.lntu.online.ui.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class OneKeyAdapter extends BaseAdapter {

    private List<View> itemViews;

    public OneKeyAdapter(List<View> itemViews) {
        this.itemViews = itemViews;
    }

    @Override
    public int getCount() {
        return itemViews.size();
    }

    @Override
    public Object getItem(int position) {
        return itemViews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemViews.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return itemViews.get(position);
    }

    /**
     * 设置布局，会自动更新ListView
     */
    public void setItemViews(List<View> itemViews) {
        this.itemViews = itemViews;
        this.notifyDataSetChanged();
    }

}
