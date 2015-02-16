package com.lntu.online.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lntu.online.R;

public class MainAdapter extends BaseAdapter {

    private static final int[] icons = { 
        R.drawable.main_icon_profle,
        R.drawable.main_icon_clock2,
        R.drawable.main_icon_calendar,
        R.drawable.main_icon_right2,
        R.drawable.main_icon_gps,
        R.drawable.main_icon_stop,
        R.drawable.main_icon_trophy,
        R.drawable.main_icon_megaphone,
        R.drawable.main_icon_chat
    };

    private static final String[] titles = { 
        "学籍信息",
        "学期课表",
        "考试安排",
        "成绩查询",
        "一键评课",
        "挂科查询",
        "等级考试",
        "教务公告",
        "牢记校训"
    };

    private LayoutInflater inflater;

    public MainAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return null; //忽略这个实现
    }

    @Override
    public long getItemId(int position) {
        return 0; //忽略这个实现
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_main_gv_item, parent, false);
            holder = new ViewHolder();
            holder.imgIcon  = (ImageView) convertView.findViewById(R.id.main_gv_item_iv_icon);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.main_gv_item_tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imgIcon.setImageResource(icons[position]);
        holder.tvTitle.setText(titles[position]);
        return convertView;
    }

    private static class ViewHolder {

        ImageView imgIcon;
        TextView tvTitle;

    }

}
