package com.lntu.online.adapter;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lntu.online.R;
import com.lntu.online.model.ClientExamPlan;

public class ExamPlanAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ClientExamPlan> planList;
    private Date nowDate;

    public ExamPlanAdapter(Context context, List<ClientExamPlan> planList) {
        inflater = LayoutInflater.from(context);
        this.planList = planList;
        nowDate = new Date();
    }

    @Override
    public int getCount() {
        return planList.size();
    }

    @Override
    public Object getItem(int position) {
        return planList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_exam_plan_item, parent, false);
            holder = new ViewHolder();
            holder.tvCourse = (TextView) convertView.findViewById(R.id.exam_plan_item_tv_course);
            holder.tvTime = (TextView) convertView.findViewById(R.id.exam_plan_item_tv_time);
            holder.tvLocation = (TextView) convertView.findViewById(R.id.exam_plan_item_tv_location);
            holder.iconFinish = convertView.findViewById(R.id.exam_plan_item_icon_finish);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ClientExamPlan plan = planList.get(position);
        holder.tvCourse.setText(plan.getCourse() + "");
        holder.tvTime.setText(plan.getTime() + "");
        holder.tvLocation.setText(plan.getLocation() + "");
        holder.iconFinish.setVisibility(plan.getDateTime().before(nowDate) ? View.VISIBLE : View.GONE);
        return convertView;
    }

    private static class ViewHolder {

        TextView tvCourse;
        TextView tvTime;
        TextView tvLocation;
        View iconFinish;

    }

}
