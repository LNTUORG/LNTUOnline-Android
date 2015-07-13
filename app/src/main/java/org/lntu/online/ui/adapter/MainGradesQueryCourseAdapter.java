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

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_TIP = 1;
    private static final int TYPE_BOTTOM = 2;

    private Context context;
    private LayoutInflater inflater;

    private Grades grades;

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

    public void setGrades(Grades grades) {
        // 设置成绩
        this.grades = grades;

        // 源数据
        this.scoreList.clear();
        this.scoreList.addAll(grades.getCourseScores());
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
        return currentList == null ? 0 : currentList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TIP;
        } else if (position == getItemCount() - 1) {
            return TYPE_BOTTOM;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NORMAL:
                return new NormalViewHolder(inflater.inflate(R.layout.activity_main_grades_query_course_item_normal, parent, false));
            case TYPE_TIP:
                return new TipViewHolder(inflater.inflate(R.layout.activity_main_grades_query_course_item_tip, parent, false));
            case TYPE_BOTTOM:
                return new ViewHolder(inflater.inflate(R.layout.activity_main_grades_query_course_item_bottom, parent, false));
            default:
                throw new RuntimeException("Unknow view type.");
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position != 0 && position != getItemCount() - 1) {
            position -= 1;
        } else {
            position = -1;
        }
        holder.update(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void update(int position) {}

    }

    protected class NormalViewHolder extends ViewHolder {

        @InjectView(R.id.main_grades_query_course_item_normal_tv_num)
        protected TextView tvNum;

        @InjectView(R.id.main_grades_query_course_item_normal_tv_name)
        protected TextView tvName;

        @InjectView(R.id.main_grades_query_course_item_normal_tv_score)
        protected TextView tvScore;

        @InjectView(R.id.main_grades_query_course_item_normal_tv_credit)
        protected TextView tvCredit;

        @InjectView(R.id.main_grades_query_course_item_normal_tv_test_mode)
        protected TextView tvTestMode;

        @InjectView(R.id.main_grades_query_course_item_normal_tv_select_type)
        protected TextView tvSelectType;

        @InjectView(R.id.main_grades_query_course_item_normal_tv_remarks)
        protected TextView tvRemarks;

        @InjectView(R.id.main_grades_query_course_item_normal_tv_exam_type)
        protected TextView tvExamType;

        @InjectView(R.id.main_grades_query_course_item_normal_tv_semester)
        protected TextView tvSemester;

        @InjectView(R.id.main_grades_query_course_item_normal_icon_very_good)
        protected View iconVeryGood;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void update(int position) {
            Grades.CourseScore score = currentList.get(position);
            tvNum.setText(score.getNum());
            tvName.setText(score.getName());
            tvScore.setText(score.getScore());
            tvCredit.setText(Float.toString(score.getCredit()));
            tvTestMode.setText(score.getTestMode());
            tvSelectType.setText(score.getSelectType());
            tvRemarks.setText(score.getRemarks());
            tvExamType.setText(score.getExamType());
            tvSemester.setText(score.getYear() + score.getTerm());
            switch (score.getLevel()) { // 得分红色标记
                case GREAT:
                    tvScore.setTextColor(context.getResources().getColor(R.color.score_level_great));
                    break;
                case UNPASS:
                    tvScore.setTextColor(context.getResources().getColor(R.color.score_level_unpass));
                    break;
                case NORMAL:
                default:
                    tvScore.setTextColor(context.getResources().getColor(R.color.score_level_normal));
                    break;
            }
            iconVeryGood.setVisibility(score.getLevel() == Grades.Level.GREAT ? View.VISIBLE : View.GONE);
        }

    }

    protected class TipViewHolder extends ViewHolder {

        @InjectView(R.id.main_grades_query_course_item_tip_tv_ava_credit)
        protected TextView tvAvaCredit;

        public TipViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void update(int position) {
            tvAvaCredit.setText(grades.getAverageCredit().getSummary());
        }

    }

}
