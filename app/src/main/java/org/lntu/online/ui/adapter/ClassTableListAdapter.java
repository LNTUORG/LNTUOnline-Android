package org.lntu.online.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lntu.online.R;

import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.entity.Grades;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ClassTableListAdapter extends RecyclerView.Adapter<ClassTableListAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    private List<ClassTable.Course> courseList;

    public ClassTableListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setCourseList(List<ClassTable.Course> courseList) {
        this.courseList = courseList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return courseList == null ? 0 : courseList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.activity_class_table_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassTable.Course course = courseList.get(position);
        holder.tvNum.setText(course.getNum());
        holder.tvName.setText(course.getName());
        holder.tvTeacher.setText(course.getTeacher());
        holder.iconBlankTop.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        holder.iconBlankBottom.setVisibility(position == courseList.size() - 1 ? View.VISIBLE : View.GONE);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.class_table_list_item_tv_num)
        protected TextView tvNum;

        @InjectView(R.id.class_table_list_item_tv_name)
        protected TextView tvName;

        @InjectView(R.id.class_table_list_item_tv_teacher)
        protected TextView tvTeacher;

        @InjectView(R.id.class_table_list_item_icon_blank_top)
        protected View iconBlankTop;

        @InjectView(R.id.class_table_list_item_icon_blank_bottom)
        protected View iconBlankBottom;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

    }

}
