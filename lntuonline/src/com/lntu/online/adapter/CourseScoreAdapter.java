package com.lntu.online.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lntu.online.R;
import com.lntu.online.model.CourseScore;
import com.lntu.online.model.CourseScore.Level;

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
            holder.tvScore.setTextColor(0xFFFF0000);
        }
        else {
            holder.tvScore.setTextColor(0xFF00FF00);
        }
        holder.iconVeryGood.setVisibility(level == Level.veryGood ? View.VISIBLE : View.GONE);
        return convertView;
    }

    private static class ViewHolder {

        public final TextView tvNum;
        public final TextView tvName;
        public final TextView tvScore;
        public final TextView tvCredit;
        public final TextView tvTestMode;
        public final TextView tvSelectType;
        public final TextView tvRemarks;
        public final TextView tvExamType;
        public final TextView tvSemester;
        public final View iconVeryGood;

        public ViewHolder(View convertView) {
            tvNum = (TextView) convertView.findViewById(R.id.course_score_item_tv_num);
            tvName = (TextView) convertView.findViewById(R.id.course_score_item_tv_name);
            tvScore = (TextView) convertView.findViewById(R.id.course_score_item_tv_score);
            tvCredit = (TextView) convertView.findViewById(R.id.course_score_item_tv_credit);
            tvTestMode = (TextView) convertView.findViewById(R.id.course_score_item_tv_test_mode);
            tvSelectType = (TextView) convertView.findViewById(R.id.course_score_item_tv_select_type);
            tvRemarks = (TextView) convertView.findViewById(R.id.course_score_item_tv_remarks);
            tvExamType = (TextView) convertView.findViewById(R.id.course_score_item_tv_exam_type);
            tvSemester = (TextView) convertView.findViewById(R.id.course_score_item_tv_semester);
            iconVeryGood = convertView.findViewById(R.id.course_score_item_icon_very_good);
        }

    }

}
