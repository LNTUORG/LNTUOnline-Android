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
import com.lntu.online.model.ExamPlan;

public class ExamPlanAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ExamPlan> planList;
    private Date nowDate;

    public ExamPlanAdapter(Context context, List<ExamPlan> planList) {
        inflater = LayoutInflater.from(context);
        this.planList = planList;
        nowDate = new Date();
    }

    @Override
    public int getCount() {
        return planList == null ? 0 : planList.size();
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
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ExamPlan plan = planList.get(position);
        holder.tvCourse.setText(plan.getCourse() + "");
        holder.tvTime.setText(plan.getTime().replace("--", "-") + "");
        holder.tvLocation.setText(plan.getLocation() + "");
        if (plan.getDateTime().before(nowDate)) {
            holder.iconFinish.setVisibility(View.VISIBLE);
            holder.iconCountdown.setVisibility(View.INVISIBLE);
        } else {
            holder.iconFinish.setVisibility(View.INVISIBLE);
            holder.iconCountdown.setVisibility(View.VISIBLE);
            long diff = plan.getDateTime().getTime() - nowDate.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            if (days == 0) {
                holder.tvDayLeft.setText("< 1");
            } else {
                holder.tvDayLeft.setText(days + "");
            }
            if (days <= 7) {
                holder.tvDayLeft.setTextColor(0xFFFF0000);
            } else {
                holder.tvDayLeft.setTextColor(0xFF00FF00);
            }
        }
        return convertView;
    }

    private static class ViewHolder {

        public final TextView tvCourse;
        public final TextView tvTime;
        public final TextView tvLocation;
        public final View iconFinish;
        public final View iconCountdown;
        public final TextView tvDayLeft;

        public ViewHolder(View convertView) {
            tvCourse = (TextView) convertView.findViewById(R.id.exam_plan_item_tv_course);
            tvTime = (TextView) convertView.findViewById(R.id.exam_plan_item_tv_time);
            tvLocation = (TextView) convertView.findViewById(R.id.exam_plan_item_tv_location);
            iconCountdown = convertView.findViewById(R.id.exam_plan_item_icon_countdown);
            iconFinish = convertView.findViewById(R.id.exam_plan_item_icon_finish);
            tvDayLeft = (TextView) convertView.findViewById(R.id.exam_plan_item_tv_day_left);
        }

    }

}
