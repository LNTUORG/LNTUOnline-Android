package org.lntu.online.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.lntu.online.R;

import org.lntu.online.model.entity.Grades;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainGradesQueryCourseAdapter extends RecyclerView.Adapter<MainGradesQueryCourseAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    private List<Grades.CourseScore> scoreList;
    private List<Grades.CourseScore> maxList;
    private List<Grades.CourseScore> currentList;

    public MainGradesQueryCourseAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        scoreList = new ArrayList<>();
        maxList = new ArrayList<>();
        currentList = new ArrayList<>();
    }

    public void setScoreList(List<Grades.CourseScore> scoreList) {
        // 源数据
        this.scoreList.clear();
        this.scoreList.addAll(scoreList);
        // 构建最高成绩数据源
        maxList.clear();
        for (Grades.CourseScore score : this.scoreList) {
            Grades.CourseScore currentScore = null;
            for (Grades.CourseScore tmpScore : maxList) {
                if (score.getNum().equals(tmpScore.getNum())) {
                    currentScore = tmpScore;
                    break;
                }
            }
            if (currentScore == null) {
                maxList.add(score);
            } else {
                if (score.getScoreValue() > currentScore.getScoreValue()) {
                    maxList.remove(currentScore);
                    maxList.add(score);
                }
            }
        }
        // 构建当前显示数据源
        currentList.clear();
        for (Grades.CourseScore score : this.scoreList) {
            currentList.add(score);
        }
        // 更新adapter
        notifyDataSetChanged();
    }

    public void updateListView(int year, String term, Grades.Level level, boolean displayMax) {
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
    public int getItemCount() {
        return currentList == null ? 0 : currentList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.activity_main_grades_query_course_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
        holder.iconBlankTop.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        holder.iconBlankBottom.setVisibility(position == currentList.size() - 1 ? View.VISIBLE : View.GONE);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.main_grades_query_course_item_tv_num)
        protected TextView tvNum;

        @InjectView(R.id.main_grades_query_course_item_tv_name)
        protected TextView tvName;

        @InjectView(R.id.main_grades_query_course_item_tv_score)
        protected TextView tvScore;

        @InjectView(R.id.main_grades_query_course_item_tv_credit)
        protected TextView tvCredit;

        @InjectView(R.id.main_grades_query_course_item_tv_test_mode)
        protected TextView tvTestMode;

        @InjectView(R.id.main_grades_query_course_item_tv_select_type)
        protected TextView tvSelectType;

        @InjectView(R.id.main_grades_query_course_item_tv_remarks)
        protected TextView tvRemarks;

        @InjectView(R.id.main_grades_query_course_item_tv_exam_type)
        protected TextView tvExamType;

        @InjectView(R.id.main_grades_query_course_item_tv_semester)
        protected TextView tvSemester;

        @InjectView(R.id.main_grades_query_course_item_icon_very_good)
        protected View iconVeryGood;

        @InjectView(R.id.main_grades_query_course_item_icon_blank_top)
        protected View iconBlankTop;

        @InjectView(R.id.main_grades_query_course_item_icon_blank_bottom)
        protected View iconBlankBottom;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

    }

}
