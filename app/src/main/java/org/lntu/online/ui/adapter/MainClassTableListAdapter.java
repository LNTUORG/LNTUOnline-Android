package org.lntu.online.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.lntu.online.R;

import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.gson.GsonWrapper;
import org.lntu.online.ui.activity.ClassTableCourseActivity;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

public class MainClassTableListAdapter extends RecyclerView.Adapter<MainClassTableListAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    private ClassTable classTable;

    public MainClassTableListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void updateClassTable(ClassTable classTable) {
        this.classTable = classTable;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return classTable == null ? 0 : classTable.getCourses() == null ? 0 : classTable.getCourses().size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.activity_main_class_table_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassTable.Course course = classTable.getCourses().get(position);
        holder.tvNum.setText(course.getNum());
        holder.tvName.setText(course.getName());
        holder.tvTeacher.setText(course.getTeacher());
        holder.iconBlankTop.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        holder.iconBlankBottom.setVisibility(position == classTable.getCourses().size() - 1 ? View.VISIBLE : View.GONE);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.main_class_table_list_item_tv_num)
        protected TextView tvNum;

        @Bind(R.id.main_class_table_list_item_tv_name)
        protected TextView tvName;

        @Bind(R.id.main_class_table_list_item_tv_teacher)
        protected TextView tvTeacher;

        @Bind(R.id.main_class_table_list_item_icon_blank_top)
        protected View iconBlankTop;

        @Bind(R.id.main_class_table_list_item_icon_blank_bottom)
        protected View iconBlankBottom;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.main_class_table_list_item_btn_card)
        public void onBtnCardClick() {
            Intent intent = new Intent(context, ClassTableCourseActivity.class);
            intent.putExtra("course", GsonWrapper.gson.toJson(classTable.getCourses().get(getLayoutPosition())));
            intent.putExtra("year", classTable.getYear());
            intent.putExtra("term", classTable.getTerm());
            context.startActivity(intent);
        }

    }

}
