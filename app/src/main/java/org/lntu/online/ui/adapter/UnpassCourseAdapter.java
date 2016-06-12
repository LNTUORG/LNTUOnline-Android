package org.lntu.online.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.lntu.online.R;
import org.lntu.online.model.entity.UnpassCourse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnpassCourseAdapter extends BaseExpandableListAdapter {

    private static final int[] flagColorResId = {
            R.color.red_light,
            R.color.green_light,
            R.color.blue_light,
            R.color.orange_light
    };

    private LayoutInflater inflater;
    private List<UnpassCourse> unpassCourseList;

    public UnpassCourseAdapter(Context context, List<UnpassCourse> unpassCourseList) {
        inflater = LayoutInflater.from(context);
        this.unpassCourseList = unpassCourseList;
    }

    @Override
    public int getGroupCount() {
        return unpassCourseList == null ? 0 : unpassCourseList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<UnpassCourse.Record> recordList = unpassCourseList.get(groupPosition).getRecords();
        return recordList == null ? 0 : recordList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return unpassCourseList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return unpassCourseList.get(groupPosition).getRecords().get(childPosition);
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_unpass_course_item_group, parent, false);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        UnpassCourse course = unpassCourseList.get(groupPosition);
        holder.tvName.setText(course.getName());
        holder.tvNum.setText(course.getNum());
        holder.tvCredit.setText(course.getCredit() + "");
        holder.tvSelectType.setText(course.getSelectType());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_unpass_course_item_child, parent, false);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        UnpassCourse.Record record = unpassCourseList.get(groupPosition).getRecords().get(childPosition);
        holder.tvSemester.setText(record.getYear() + record.getTerm());
        holder.tvExamType.setText(record.getExamType());
        holder.tvScore.setText(record.getScore());
        holder.tvRemarks.setText(record.getRemarks());
        holder.iconFlag.setBackgroundResource(flagColorResId[childPosition % flagColorResId.length]);
        holder.iconShadowTop.setVisibility(childPosition == 0 ? View.VISIBLE : View.GONE);
        holder.iconShadowBottom.setVisibility(childPosition == unpassCourseList.get(groupPosition).getRecords().size() - 1 ? View.VISIBLE : View.GONE);
        return convertView;
    }

    protected static class GroupViewHolder {

        @BindView(R.id.tv_name)
        protected TextView tvName;

        @BindView(R.id.tv_num)
        protected TextView tvNum;

        @BindView(R.id.tv_credit)
        protected TextView tvCredit;

        @BindView(R.id.tv_select_type)
        protected TextView tvSelectType;

        public GroupViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }

    }

    protected static class ChildViewHolder {

        @BindView(R.id.icon_flag)
        protected View iconFlag;

        @BindView(R.id.icon_shadow_top)
        protected View iconShadowTop;

        @BindView(R.id.icon_shadow_bottom)
        protected View iconShadowBottom;

        @BindView(R.id.tv_semester)
        protected TextView tvSemester;

        @BindView(R.id.tv_exam_type)
        protected TextView tvExamType;

        @BindView(R.id.tv_score)
        protected TextView tvScore;

        @BindView(R.id.tv_remarks)
        protected TextView tvRemarks;

        public ChildViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }

    }

}
