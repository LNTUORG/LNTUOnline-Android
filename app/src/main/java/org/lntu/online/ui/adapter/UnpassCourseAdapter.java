package org.lntu.online.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.lntu.online.R;

import org.lntu.online.model.entity.CourseScore;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UnpassCourseAdapter extends BaseExpandableListAdapter {

    private LayoutInflater inflater;
    private List<CourseScore> groupList;
    private List<List<CourseScore>> childrenList;

    public UnpassCourseAdapter(Context context, List<CourseScore> scoreList) {
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getGroupCount() {
        return groupList == null ? 0 : groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<CourseScore> childList = childrenList.get(groupPosition);
        return childList == null ? 0 : childList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childrenList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null)  {
            convertView = inflater.inflate(R.layout.activity_unpass_course_item_group, parent, false);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        CourseScore score = groupList.get(groupPosition);
        holder.tvName.setText(score.getName());
        holder.tvNum.setText(score.getNum());
        holder.tvCredit.setText(score.getCredit() + "");
        holder.tvSelectType.setText(score.getSelectType());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null)  {
            convertView = inflater.inflate(R.layout.activity_unpass_course_item_child, parent, false);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        CourseScore score = childrenList.get(groupPosition).get(childPosition);
        holder.tvSemester.setText(score.getYear() + score.getTerm());
        holder.tvExamType.setText(score.getExamType());
        holder.tvScore.setText(score.getScore());
        holder.tvRemarks.setText(score.getRemarks());
        return convertView;
    }

    protected static class GroupViewHolder {

        @InjectView(R.id.unpass_course_item_group_tv_name)
        protected TextView tvName;

        @InjectView(R.id.unpass_course_item_group_tv_num)
        protected TextView tvNum;

        @InjectView(R.id.unpass_course_item_group_tv_credit)
        protected TextView tvCredit;

        @InjectView(R.id.unpass_course_item_group_tv_select_type)
        protected TextView tvSelectType;

        public GroupViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }

    }

    protected static class ChildViewHolder {

        @InjectView(R.id.unpass_course_item_child_tv_semester)
        protected TextView tvSemester;

        @InjectView(R.id.unpass_course_item_child_tv_exam_type)
        protected TextView tvExamType;

        @InjectView(R.id.unpass_course_item_child_tv_score)
        protected TextView tvScore;

        @InjectView(R.id.unpass_course_item_child_tv_remarks)
        protected TextView tvRemarks;

        public ChildViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }

    }

}
