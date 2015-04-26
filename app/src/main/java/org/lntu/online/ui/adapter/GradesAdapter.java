package org.lntu.online.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lntu.online.R;

import org.lntu.online.model.entity.Grades;
import org.lntu.online.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GradesAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Grades.CourseScore> scoreList;
    private List<Grades.CourseScore> maxList;
    private List<Grades.CourseScore> currentList;

    public GradesAdapter(Context context, List<Grades.CourseScore> scoreList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.scoreList = scoreList;
        // 构建最高成绩数据源
        maxList = new ArrayList<Grades.CourseScore>();
        for (Grades.CourseScore score : scoreList) {
            Grades.CourseScore currentMaxScore = getFirstScoreFormMaxList(score.getNum());
            if (currentMaxScore == null) {
                maxList.add(score);
            } else {
                if (score.getScoreValue() > currentMaxScore.getScoreValue()) {
                    maxList.remove(currentMaxScore);
                    maxList.add(score);
                }
            }
        }
        // 构建当前显示数据源
        currentList = new ArrayList<Grades.CourseScore>();
        for (Grades.CourseScore score : scoreList) {
            currentList.add(score);
        }
    }

    private Grades.CourseScore getFirstScoreFormMaxList(String num) {
        for (Grades.CourseScore score : maxList) {
            if (score.getNum().equals(num)) {
                return score;
            }
        }
        return null;
    }

    public void update(int year, String term, Grades.Level level, boolean displayMax) {
        currentList.clear();
        for (Grades.CourseScore score : (displayMax ? maxList : scoreList)) {
            if (year != 0 && score.getYear() != year) {
                continue;
            }
            if (!TextUtils.isEmpty(term) && !score.getTerm().equals(term)) {
                continue;
            }
            if (level != null && score.getLevel() != level) {
                continue;
            }
            currentList.add(score);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return currentList == null ? 0 : currentList.size();
    }

    @Override
    public Object getItem(int position) {
        return currentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_grades_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Grades.CourseScore score = currentList.get(position);
        holder.tvNum.setText(score.getNum());
        holder.tvName.setText(score.getName());
        holder.tvScore.setText(score.getScore());
        holder.tvCredit.setText(Float.toString(score.getCredit()));
        holder.tvTestMode.setText(score.getTestMode());
        holder.tvSelectType.setText(score.getSelectType());
        holder.tvRemarks.setText(score.getRemarks());
        holder.tvExamType.setText(score.getExamType());
        holder.tvSemester.setText(score.getYear() + score.getTerm());
        switch (score.getLevel()) { // 得分红色标记
            case GREAT:
                holder.tvScore.setTextColor(context.getResources().getColor(R.color.score_level_great));
                break;
            case UNPASS:
                holder.tvScore.setTextColor(context.getResources().getColor(R.color.score_level_unpass));
                break;
            case NORMAL:
            default:
                holder.tvScore.setTextColor(context.getResources().getColor(R.color.score_level_normal));
                break;
        }
        holder.iconVeryGood.setVisibility(score.getLevel() == Grades.Level.GREAT ? View.VISIBLE : View.GONE);
        return convertView;
    }

    protected static class ViewHolder {

        @InjectView(R.id.grades_item_tv_num)
        protected TextView tvNum;

        @InjectView(R.id.grades_item_tv_name)
        protected TextView tvName;

        @InjectView(R.id.grades_item_tv_score)
        protected TextView tvScore;

        @InjectView(R.id.grades_item_tv_credit)
        protected TextView tvCredit;

        @InjectView(R.id.grades_item_tv_test_mode)
        protected TextView tvTestMode;

        @InjectView(R.id.grades_item_tv_select_type)
        protected TextView tvSelectType;

        @InjectView(R.id.grades_item_tv_remarks)
        protected TextView tvRemarks;

        @InjectView(R.id.grades_item_tv_exam_type)
        protected TextView tvExamType;

        @InjectView(R.id.grades_item_tv_semester)
        protected TextView tvSemester;

        @InjectView(R.id.grades_item_icon_very_good)
        protected View iconVeryGood;

        public ViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }

    }

}
