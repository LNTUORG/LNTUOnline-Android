package org.lntu.online.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.lntu.online.R;
import org.lntu.online.model.entity.ExamPlan;

import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

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
            convertView = inflater.inflate(R.layout.activity_main_exam_plan_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ExamPlan plan = planList.get(position);
        holder.tvCourse.setText(plan.getCourse());
        holder.tvTime.setText(plan.getShowTime());
        holder.tvLocation.setText(plan.getLocation());
        if (plan.getEndTime().before(nowDate)) {
            holder.iconFinish.setVisibility(View.VISIBLE);
            holder.iconCountdown.setVisibility(View.INVISIBLE);
        } else {
            holder.iconFinish.setVisibility(View.INVISIBLE);
            holder.iconCountdown.setVisibility(View.VISIBLE);
            long diff = plan.getStartTime().getTime() - nowDate.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            if (days == 0) {
                holder.tvDayLeft.setText("< 1");
            } else {
                holder.tvDayLeft.setText(days + "");
            }
            if (days <= 7) {
                holder.tvDayLeft.setTextColor(0xFFF44336);
            } else {
                holder.tvDayLeft.setTextColor(0xFF4CAF50);
            }
        }
        return convertView;
    }

    protected class ViewHolder {

        @InjectView(R.id.main_exam_plan_item_tv_course)
        protected TextView tvCourse;
        
        @InjectView(R.id.main_exam_plan_item_tv_time)
        protected TextView tvTime;
        
        @InjectView(R.id.main_exam_plan_item_tv_location)
        protected TextView tvLocation;

        @InjectView(R.id.main_exam_plan_item_icon_finish)
        protected View iconFinish;

        @InjectView(R.id.main_exam_plan_item_icon_countdown)
        protected View iconCountdown;
        
        @InjectView(R.id.main_exam_plan_item_tv_day_left)
        protected TextView tvDayLeft;

        public ViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }

    }

}
