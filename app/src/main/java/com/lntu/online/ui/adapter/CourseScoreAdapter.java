package com.lntu.online.ui.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lntu.online.R;
import com.lntu.online.model.entityOld.CourseScore;
import com.lntu.online.model.entityOld.CourseScore.Level;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CourseScoreAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<CourseScore> scoreList;

    public CourseScoreAdapter(Context context, List<CourseScore> scoreList) {
        inflater = LayoutInflater.from(context);
        this.scoreList = scoreList;
    }

    @Override
    public int getCount() {
        return scoreList == null ? 0 : scoreList.size();
    }

    @Override
    public Object getItem(int position) {
        return scoreList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_course_score_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CourseScore score = scoreList.get(position);
        holder.tvNum.setText(score.getNum() + "");
        holder.tvName.setText(score.getName() + "");
        holder.tvScore.setText(TextUtils.isEmpty(score.getScore()) ? "无成绩" : score.getScore());
        holder.tvCredit.setText(score.getCredit() + "");
        holder.tvTestMode.setText(score.getTestMode() + "");
        holder.tvSelectType.setText(score.getSelectType() + "");
        holder.tvRemarks.setText(score.getRemarks() + "");
        holder.tvExamType.setText(score.getExamType() + "");
        holder.tvSemester.setText(score.getSemester() + "");
        //得分红色标记
        CourseScore.Level level = score.getLevel();
        if (level == Level.veryGood) {
            holder.tvScore.setTextColor(0xFFFFD700);
        }
        else if (level == Level.bad) {
            holder.tvScore.setTextColor(0xFFF44336);
        }
        else {
            holder.tvScore.setTextColor(0xFF4CAF50);
        }
        holder.iconVeryGood.setVisibility(level == Level.veryGood ? View.VISIBLE : View.GONE);
        return convertView;
    }

    protected static class ViewHolder {

        @InjectView(R.id.course_score_item_tv_num)
        protected TextView tvNum;

        @InjectView(R.id.course_score_item_tv_name)
        protected TextView tvName;

        @InjectView(R.id.course_score_item_tv_score)
        protected TextView tvScore;

        @InjectView(R.id.course_score_item_tv_credit)
        protected TextView tvCredit;

        @InjectView(R.id.course_score_item_tv_test_mode)
        protected TextView tvTestMode;

        @InjectView(R.id.course_score_item_tv_select_type)
        protected TextView tvSelectType;

        @InjectView(R.id.course_score_item_tv_remarks)
        protected TextView tvRemarks;

        @InjectView(R.id.course_score_item_tv_exam_type)
        protected TextView tvExamType;

        @InjectView(R.id.course_score_item_tv_semester)
        protected TextView tvSemester;

        @InjectView(R.id.course_score_item_icon_very_good)
        protected View iconVeryGood;

        public ViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }

    }

}
